package invis.gl.networkapi;

import edu.uci.ics.jung.graph.DirectedGraph;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Matt
 */
public class AllPathDetector<V, E extends Comparable>
{

    private List<List<E>> allPaths;

    public List<List<E>> getAllPathsBetweenNodes(DirectedGraph<V, E> graph,
            V startNode, V endNode, int maxDepth)
    {
        allPaths = new ArrayList<List<E>>();

        List<E> currentPath = new ArrayList<E>();

        findAllPaths(startNode, startNode, endNode, currentPath, graph, maxDepth, 0);

        return allPaths;
    }

    public List<List<E>> getAllUniqePathsBetweenNodes(DirectedGraph<V, E> graph,
            V startNode, V endNode, int maxDepth)
    {
        allPaths = new ArrayList<List<E>>();

        List<E> currentPath = new ArrayList<E>();

        findAllUniquePaths(startNode, startNode, endNode, currentPath, graph, maxDepth, 0);

        return allPaths;
    }

    private void findAllPaths(V currentNode, V startNode, V endNode,
            List<E> currentPath, DirectedGraph<V, E> graph,
            int maxDepth, int currentDepth)
    {
        Collection<E> outgoingEdges = graph.getOutEdges(currentNode);

        if (currentDepth < maxDepth)
        {
            for (E outEdge : outgoingEdges)
            {
                V outNode = graph.getDest(outEdge);
                //String outNode = outEdge.getSupertype();

                if (outNode.equals(startNode))
                {
                    List<E> cyclePath = new ArrayList<E>(currentPath);
                    cyclePath.add(outEdge);
                    System.out.println("Found cycle provoked by path " + cyclePath);
                    continue;
                }

                List<E> newPath = new ArrayList<E>(currentPath);
                newPath.add(outEdge);

                if (outNode.equals(endNode))
                {
                    allPaths.add(newPath);
                    continue;
                }

                findAllPaths(outNode, startNode, endNode, newPath, graph, maxDepth, currentDepth + 1);
            }
        }
    }

    private void findAllUniquePaths(V currentNode, V startNode, V endNode,
            List<E> currentPath, DirectedGraph<V, E> graph,
            int maxDepth, int currentDepth)
    {
        Collection<E> outgoingEdges = graph.getOutEdges(currentNode);

        if (currentDepth < maxDepth)
        {
            for (E outEdge : outgoingEdges)
            {
                V outNode = graph.getDest(outEdge);
                //String outNode = outEdge.getSupertype();

                if (outNode.equals(startNode))
                {
                    List<E> cyclePath = new ArrayList<E>(currentPath);
                    cyclePath.add(outEdge);
                    System.out.println("Found cycle provoked by path " + cyclePath);
                    continue;
                }

                List<E> newPath = new ArrayList<E>(currentPath);
                newPath.add(outEdge);

                if (outNode.equals(endNode))
                {
                    //Check for unique-ness before adding.
                    boolean unique = true;
                    //Check each existing path.
                    for (int pathItr = 0; pathItr < allPaths.size(); pathItr++)
                    {
                        //This next 9 lines of code, gets the smaller of the two paths, if their lengths differ,
                        //then compares edges up to the MIN(path1, path2).
                        int pathLength = 0;
                        if (allPaths.get(pathItr).size() <= newPath.size())
                        {
                            pathLength = allPaths.get(pathItr).size();
                        }
                        else
                        {
                            pathLength = newPath.size();
                        }

                        //Compare the edges of the paths.
                        for (int edgeItr = 0; edgeItr < pathLength; edgeItr++)
                        {
                            //If the edges are the same, this path is not unique.
                            if (allPaths.get(pathItr).get(edgeItr).compareTo(newPath.get(edgeItr)) == 0)
                            {
                                unique = false;
                            }
                        }
                    }
                    //After we check all the edges, in all the paths,
                    //if it is still unique, we add it.
                    if (unique)
                    {
                        allPaths.add(newPath);
                    }
                    continue;
                }
                findAllUniquePaths(outNode, startNode, endNode, newPath, graph, maxDepth, currentDepth + 1);
            }
        }
    }
}
