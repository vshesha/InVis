package invis.gl.graphlayouts;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.Graph;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.map.LazyMap;
/*
 * Copyright (c) 2005, the JUNG Project and the Regents of the University of
 * California All rights reserved.
 *
 * This software is open-source under the BSD license; see either "license.txt"
 * or http://jung.sourceforge.net/license.txt for a description.
 *
 * Created on Jul 9, 2005
 */

/**
 * @author Michael Eagle
 * @author Matthew Johnson
 *
 */
/**
 * Created by IntelliJ IDEA. User: Matt Date: 2/1/11 Time: 1:36 PM
 */
public final class DAGTreeLayout_VarY<V, E> implements Layout<V, E>
{

    protected Dimension size = new Dimension(600, 600);
    protected DirectedGraph<V, E> graph;
    protected Map<V, Integer> basePositions = new HashMap<V, Integer>();
    protected Map<V, Point2D> locations =
            LazyMap.decorate(new HashMap<V, Point2D>(),
            new Transformer<V, Point2D>()
            {
                @Override
                public Point2D transform(V arg0)
                {
                    return new Point2D.Double();
                }
            });
    protected transient Set<V> alreadyDone = new HashSet<V>();
    /**
     * The default horizontal vertex spacing. Initialized to 50.
     */
    public static int DEFAULT_DISTX = 100;
    /**
     * The default vertical vertex spacing. Initialized to 50.
     */
    public static int DEFAULT_DISTY = 100;
    /**
     * The horizontal vertex spacing. Defaults to {@code DEFAULT_XDIST}.
     */
    protected int distX = 100;
    /**
     * The vertical vertex spacing. Defaults to {@code DEFAULT_YDIST}.
     */
    protected int distY = 100;
    protected transient Point m_currentPoint = new Point();

    /**
     * Creates an instance for the specified graph with default X and Y
     * distances.
     */
    public DAGTreeLayout_VarY(DirectedGraph<V, E> g)
    {
        this(g, DEFAULT_DISTX, DEFAULT_DISTY);
    }

    /**
     * Creates an instance for the specified graph and X distance with default Y
     * distance.
     */
    public DAGTreeLayout_VarY(DirectedGraph<V, E> g, int distx)
    {
        this(g, distx, DEFAULT_DISTY);
    }

    /**
     * Creates an instance for the specified graph, X distance, and Y distance.
     */
    public DAGTreeLayout_VarY(DirectedGraph<V, E> g, int distx, int disty)
    {
        if (g == null)
        {
            throw new IllegalArgumentException("Graph must be non-null");
        }
        if (distx < 1 || disty < 1)
        {
            throw new IllegalArgumentException("X and Y distances must each be positive");
        }
        this.graph = g;
        this.distX = distx;
        this.distY = disty;

        buildTree();
    }

    private Collection<V> GetDAGTreeRoots()
    {
        java.util.List<V> roots;
        roots = new ArrayList<V>();
        for (V vertex : this.graph.getVertices())
        {
            
            if (this.graph.inDegree(vertex) == 0)
            {
                roots.add(vertex);
            }
            
            //We will check if all the edges are loops.
            boolean loopEdgesOnly = true;
            //We get all the edges CONNECTED to the current vertex. (that is vertex is the destination of these edges).
            for (E edge : this.graph.getInEdges(vertex))
                
            {
                //if the source of the edge is not the source vertex.
                if (this.graph.getSource(edge) != vertex)
                {
                    //Then it is some other vertex, meaning the edge is not a loop.
                    loopEdgesOnly = false;
                }
            }
            //if we iterator through all the incoming edges, and loopEdgesOnly is still true...
            //then for all edges where vertex is the desintation, it is also the source, ie. all incoming edges are loop edges.
            if (loopEdgesOnly)
            {
                //We can treat the vertex as a root. Because the 
                roots.add(vertex);
            }
                    
        }
        if (roots.isEmpty())
        {
            V semiRoot = null;
            //TODO: Get the vertex with the lowest ratio if InEdges / OutEdges.
            float lowestRatio = 10000;
            for (V vertex : this.graph.getVertices())
            {
                float ratio;
                int indegree = this.graph.getInEdges(vertex).size();
                int outdegree = this.graph.getOutEdges(vertex).size();
                
                if (outdegree == 0)
                {
                    ratio = 1000;
                }
                else
                {
                    ratio = indegree/outdegree;
                }
                if (ratio < lowestRatio)
                {
                    lowestRatio = ratio;
                    semiRoot = vertex;
                }
            }
            roots.add(semiRoot);
            
            //throw new IllegalArgumentException("DAGTreeLayout must have at least 1 node with inDegree equal to 0.");
        }
        return (roots);
    }

    protected void buildTree()
    {
        this.m_currentPoint = new Point(0, 20);
        Collection<V> roots = GetDAGTreeRoots();
        if (roots.size() > 0 && graph != null)
        {
            calculateDimensionX(roots);
            for (V v : roots)
            {
                calculateDimensionX(v, new HashSet<V>());
                m_currentPoint.x += this.basePositions.get(v) / 2;// + this.distX;
                buildTree(v, this.m_currentPoint.x);
            }
        }
        
        
        for (V v : roots)
        {
            basePositions.get(v);
        }
    }

    protected void buildTree(V v, int x)
    {

        if (!alreadyDone.contains(v))
        {
            alreadyDone.add(v);

            //go one level further down
            this.m_currentPoint.y += this.distY;
            this.m_currentPoint.x = x;

            this.setCurrentPositionFor(v);

            int sizeXofCurrent = basePositions.get(v);

            int lastX = x - sizeXofCurrent / 2;

            int sizeXofChild;
            int startXofChild;

            for (V element : graph.getSuccessors(v))
            {
                sizeXofChild = this.basePositions.get(element);
                startXofChild = lastX + sizeXofChild / 2;
                buildTree(element, startXofChild);
                lastX = lastX + sizeXofChild + distX;
            }
            this.m_currentPoint.y -= this.distY;
        }
    }

    private int calculateDimensionX(V v, HashSet<V> Visited)
    {
        int size = 0;
        int childrenNum = graph.getSuccessors(v).size();

        if (childrenNum != 0)
        {
            for (V element : graph.getSuccessors(v))
            {
                if (!Visited.contains(element))
                {
                    Visited.add(element);
                    size += calculateDimensionX(element, Visited) + distX;
                }
            }
        }
        size = Math.max(0, size - distX);
        basePositions.put(v, size);

        return size;
    }

    private int calculateDimensionX(Collection<V> roots)
    {

        int size = 0;
        for (V v : roots)
        {
            int childrenNum = graph.getSuccessors(v).size();

            if (childrenNum != 0)
            {
                for (V element : graph.getSuccessors(v))
                {
                    size += calculateDimensionX(element, new HashSet<V>()) + distX;
                }
            }
            size = Math.max(0, size - distX);
            basePositions.put(v, size);
        }

        return size;
    }

    /**
     * This method is not supported by this class. The size of the layout is
     * determined by the topology of the tree, and by the horizontal and
     * vertical spacing (optionally set by the constructor).
     */
    @Override
    public void setSize(Dimension size)
    {
        throw new UnsupportedOperationException("Size of DAGTreeLayout is set"
                + " by vertex spacing in constructor");
    }

    protected void setCurrentPositionFor(V vertex)
    {
        if (m_currentPoint.x < 0)
        {
            size.width -= m_currentPoint.x;
        }

        if (m_currentPoint.x > size.width - distX)
        {
            size.width = m_currentPoint.x + distX;
        }

        if (m_currentPoint.y < 0)
        {
            size.height -= m_currentPoint.y;
        }
        if (m_currentPoint.y > size.height - distY)
        {
            size.height = m_currentPoint.y + distY;
        }
        locations.get(vertex).setLocation(m_currentPoint);

    }

    @Override
    public Graph<V, E> getGraph()
    {
        return graph;
    }

    @Override
    public Dimension getSize()
    {
        return size;
    }

    @Override
    public void initialize()
    {
    }

    @Override
    public boolean isLocked(V v)
    {
        return false;
    }

    @Override
    public void lock(V v, boolean state)
    {
    }

    @Override
    public void reset()
    {
    }

    @Override
    public void setGraph(Graph<V, E> graph)
    {
        if (graph instanceof DirectedGraph)
        {
            this.graph = (DirectedGraph<V, E>) graph;
            buildTree();
        } else
        {
            throw new IllegalArgumentException("graph must be a DirectedGraph");
        }
    }

    @Override
    public void setInitializer(Transformer<V, Point2D> initializer)
    {
    }

    /**
     * Returns the center of this layout's area.
     */
    public Point2D getCenter()
    {
        return new Point2D.Double(size.getWidth() / 2, size.getHeight() / 2);
    }

    @Override
    public void setLocation(V v, Point2D location)
    {
        locations.get(v).setLocation(location);
    }

    @Override
    public Point2D transform(V v)
    {
        return locations.get(v);
    }
}
