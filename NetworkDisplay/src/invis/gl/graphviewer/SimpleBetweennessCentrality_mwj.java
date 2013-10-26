package invis.gl.graphviewer;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.Graph;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Matt
 */
public class SimpleBetweennessCentrality_mwj<V, E>
{

    private Graph<V, E> mGraph;
    private HashMap<E, Integer> mEdgeBetweennessValues;
    private DijkstraShortestPath<V,E> myDijkstra;

    public SimpleBetweennessCentrality_mwj(Graph<V, E> graph)
    {
        mGraph = graph;
        mEdgeBetweennessValues = new HashMap<E, Integer>();
        myDijkstra = new DijkstraShortestPath<V,E>(mGraph);
    }

    public void CalcualtePaths()
    {
        for (int i = 0; i < mGraph.getVertexCount(); i++)
        {

            mGraph.getVertices();
            for (int j = 0; j < mGraph.getVertexCount(); j++)
            {
                LinkedList<V> myPath = (LinkedList<V>) myDijkstra.getPath((V)mGraph.getVertices().toArray()[i], (V)mGraph.getVertices().toArray()[j]);
                for (int k = 0; k < myPath.size(); k++)
                {
                    E edge = mGraph.findEdge((V)(myPath.toArray()[k]), (V)(myPath.toArray()[k + 1]));
                    if (mEdgeBetweennessValues.containsKey(edge))
                    {
                        //Increase an already existing item.
                        int elementCount = mEdgeBetweennessValues.get(edge);
                        elementCount++;
                        mEdgeBetweennessValues.put(edge, elementCount);
                    } else
                    {
                        //else add a new item, with frequency 1.

                        //uniqueList.add(mCaseGroupIdList.get(networkCaseIndex); //This will make the list of uniqueCaseGroupIds....
                        //Which could be a more efficient method than making mUniqueCaseGroupIdSet (HashSet) in GenerateGroupIdContainers()
                        // However I think calling the HashSet constructor is pretty fast.
                        mEdgeBetweennessValues.put(edge, 1);
                    }

                    /*
                     V v = (V) myPath.toArray()[k];
                     Set<E> edgeList = (Set<E>) mGraph.getOutEdges(v);
                     for (int m = 0; m < edgeList.size(); m++)
                     {
                     if (k < myPath.size())
                     {
                     V dest = (V) myPath.toArray()[k + 1];
                     if (mGraph.getDest((E) edgeList.toArray()[m]) == dest)
                     {
                     if (mEdgeBetweennessValues.containsKey((edgeList.toArray()[m])))
                     {
                     //Increase an already existing item.
                     int elementCount = Integer.parseInt(mEdgeBetweennessValues.get((edgeList.toArray()[m])).toString());
                     elementCount++;
                     mEdgeBetweennessValues.put((E) (edgeList.toArray()[m]), elementCount);
                     } else
                     {
                     //else add a new item, with frequency 1.

                     //uniqueList.add(mCaseGroupIdList.get(networkCaseIndex); //This will make the list of uniqueCaseGroupIds....
                     //Which could be a more efficient method than making mUniqueCaseGroupIdSet (HashSet) in GenerateGroupIdContainers()
                     // However I think calling the HashSet constructor is pretty fast.
                     mEdgeBetweennessValues.put((E) (edgeList.toArray()[m]), 1);
                     }
                     }
                     }
                     }*/
                }
            }
        }
    }

    public Integer getEdgeRankScore(E edge)
    {
        return (mEdgeBetweennessValues.get(edge));
    }

    public HashMap<E, Integer> getEdgeBetweennessValues()
    {
        return (mEdgeBetweennessValues);
    }
}
