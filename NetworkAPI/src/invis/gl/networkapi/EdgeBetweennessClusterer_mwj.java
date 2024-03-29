package invis.gl.networkapi;

import edu.uci.ics.jung.algorithms.cluster.WeakComponentClusterer;
import edu.uci.ics.jung.algorithms.importance.BetweennessCentrality;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author Matt
 */
/*
 * Copyright (c) 2003, the JUNG Project and the Regents of the University 
 * of California
 * All rights reserved.
 *
 * This software is open-source under the BSD license; see either
 * "license.txt" or
 * http://jung.sourceforge.net/license.txt for a description.
 */
/**
 * An algorithm for computing clusters (community structure) in graphs based on
 * edge betweenness. The betweenness of an edge is defined as the extent to
 * which that edge lies along shortest paths between all pairs of nodes.
 *
 * This algorithm works by iteratively following the 2 step process: <ul> <li>
 * Compute edge betweenness for all edges in current graph <li> Remove edge with
 * highest betweenness </ul> <p> Running time is: O(kmn) where k is the number
 * of edges to remove, m is the total number of edges, and n is the total number
 * of vertices. For very sparse graphs the running time is closer to O(kn^2) and
 * for graphs with strong community structure, the complexity is even lower. <p>
 * This algorithm is a slight modification of the algorithm discussed below in
 * that the number of edges to be removed is parameterized.
 *
 * @author Scott White
 * @author Tom Nelson (converted to jung2)
 * @see "Community structure in social and biological networks by Michelle
 * Girvan and Mark Newman"
 */
public class EdgeBetweennessClusterer_mwj<V, E> implements Transformer<Graph<V, E>, Set<Set<V>>>
{

    private int mNumEdgesToRemove;
    private Map<E, Pair<V>> edges_removed;

    /**
     * Constructs a new clusterer for the specified graph.
     *
     * @param numEdgesToRemove the number of edges to be progressively removed
     * from the graph
     */
    public EdgeBetweennessClusterer_mwj(/*int numEdgesToRemove*/)
    {
        //mNumEdgesToRemove = numEdgesToRemove;
        edges_removed = new LinkedHashMap<E, Pair<V>>();
    }

    /**
     * Finds the set of clusters which have the strongest "community structure".
     * The more edges removed the smaller and more cohesive the clusters.
     *
     * @param graph the graph
     */
    @Override
    public Set<Set<V>> transform(Graph<V, E> graph)
    {

        /*if (mNumEdgesToRemove < 0 || mNumEdgesToRemove > graph.getEdgeCount())
         {
         throw new IllegalArgumentException("Invalid number of edges passed in.");
         }*/

        edges_removed.clear();

        int vcount = graph.getVertexCount();
        vcount = (int) Math.sqrt(vcount);



        UndirectedSparseMultigraph<V, E> undirectedVersion = new UndirectedSparseMultigraph<V, E>();

        for (int i = 0; i < graph.getEdges().size(); i++)
        {
            E edge = (E) graph.getEdges().toArray()[i];

            undirectedVersion.addEdge(edge, graph.getSource(edge), graph.getDest(edge), EdgeType.UNDIRECTED);
        }

        WeakComponentClusterer<V, E> wcSearch = new WeakComponentClusterer<V, E>();
        Set<Set<V>> clusterSet = wcSearch.transform(undirectedVersion);


        while (clusterSet.size() < vcount)
        {
            //for (int k = 0; k < mNumEdgesToRemove; k++)
            //{

            BetweennessCentrality<V, E> bc = new BetweennessCentrality<V, E>(undirectedVersion);
            E to_remove = null;
            double score = 0;
            bc.setRemoveRankScoresOnFinalize(false);

            bc.evaluate();
            //bc.CalcualtePaths();

            for (E e : undirectedVersion.getEdges())
            {
                if (bc.getEdgeRankScore(e) > score)
                {
                    to_remove = e;
                    score = bc.getEdgeRankScore(e);
                }
            }
            edges_removed.put(to_remove, undirectedVersion.getEndpoints(to_remove));
            undirectedVersion.removeEdge(to_remove);
            //}


            clusterSet = wcSearch.transform(undirectedVersion);

            for (Map.Entry<E, Pair<V>> entry : edges_removed.entrySet())
            {
                Pair<V> endpoints = entry.getValue();
                undirectedVersion.addEdge(entry.getKey(), endpoints.getFirst(), endpoints.getSecond());
            }

            //mNumEdgesToRemove += 10;
        }
        return clusterSet;
    }

    /**
     * Retrieves the list of all edges that were removed (assuming extract(...)
     * was previously called). The edges returned are stored in order in which
     * they were removed.
     *
     * @return the edges in the original graph
     */
    public List<E> getEdgesRemoved()
    {
        return new ArrayList<E>(edges_removed.keySet());
    }
}
