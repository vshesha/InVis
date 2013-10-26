package invis.gl.NetworkVisualizationViewer;

import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.VisualizationServer.Paintable;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractGraphMousePlugin;
import edu.uci.ics.jung.visualization.picking.PickedState;
import invis.gl.dataprocessor.DataParser;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import org.openide.util.Lookup;

/**
 *
 * @author Matt
 */
public class InVisGraphMousePlugin<V, E> extends AbstractGraphMousePlugin implements MouseListener, MouseMotionListener
{

    NetworkVisualizationViewer mNVV;
    DataParser mDataParser;

    //TODO: Ideally this wouldn't work the way it does, but the events don't get fired in a managable order.
    //So for now it is necessary to call this at the end of the mouse clicked event.
    /**
     * This is an important function for getting the selection of JUNG-Vertices
     * to trigger the properties values.
     */
    public void InVisMousePressed()
    {


        /*
         for (int i = 0; i < mNVV.getGraphLayout().getGraph().getVertices().size(); i++)
         {
         if (mDataParser.getNodeTable().containsKey(mNVV.getGraphLayout().getGraph().getVertices().toArray()[i].toString()))
         {
         mDataParser.getNodeTable().get(mNVV.getGraphLayout().getGraph().getVertices().toArray()[i].toString()).setSelected(false);
         }
         }
        
         for (int i = 0; i < mNVV.getGraphLayout().getGraph().getEdges().size(); i++)
         {
         if (mDataParser.getEdgeTable().containsKey(mNVV.getGraphLayout().getGraph().getEdges().toArray()[i].toString()))
         {
         mDataParser.getEdgeTable().get(mNVV.getGraphLayout().getGraph().getEdges().toArray()[i].toString()).setSelected(false);
         }
         }

         for (int i = 0; i < mNVV.getPickedVertexState().getPicked().size(); i++)
         {
         if (mDataParser.getNodeTable().containsKey(mNVV.getPickedVertexState().getPicked().toArray()[i].toString()))
         {
         mDataParser.getNodeTable().get(mNVV.getPickedVertexState().getPicked().toArray()[i].toString()).setSelected(false);
         }
         }*/

        for (int i = 0; i < mNVV.getPickedVertexState().getPicked().size(); i++)
        {
            String localvertex = mNVV.getPickedVertexState().getPicked().toArray()[i].toString();
            if (mDataParser.getNodeTable().containsKey(localvertex))
            {
                mDataParser.getNodeTable().get(localvertex).setSelected(true);
            }
            if (mDataParser.getDerivedData().getNodeTable().containsKey(localvertex))
            {
                mDataParser.getDerivedData().getNodeTable().get(localvertex).setSelected(true);
            }

        }
        for (int i = 0; i < mNVV.getPickedEdgeState().getPicked().size(); i++)
        {
            String localedge = mNVV.getPickedEdgeState().getPicked().toArray()[i].toString();
            if (mDataParser.getEdgeTable().containsKey(localedge))
            {
                mDataParser.getEdgeTable().get(localedge).setSelected(true);
            }
            if (mDataParser.getDerivedData().getEdgeTable().containsKey(localedge))
            {
                mDataParser.getDerivedData().getEdgeTable().get(localedge).setSelected(true);
            }
        }/*


         for (int i = 0; i < mNVV.getGraphLayout().getGraph().getVertices().size(); i++)
         {
         if (mDataParser.getDerivedData().getNodeTable().containsKey(mNVV.getGraphLayout().getGraph().getVertices().toArray()[i].toString()))
         {
         mDataParser.getDerivedData().getNodeTable().get(mNVV.getGraphLayout().getGraph().getVertices().toArray()[i].toString()).setSelected(false);
         }
         }
         for (int i = 0; i < mNVV.getGraphLayout().getGraph().getEdges().size(); i++)
         {
         if (mDataParser.getDerivedData().getEdgeTable().containsKey(mNVV.getGraphLayout().getGraph().getEdges().toArray()[i].toString()))
         {
         mDataParser.getDerivedData().getEdgeTable().get(mNVV.getGraphLayout().getGraph().getEdges().toArray()[i].toString()).setSelected(false);
         }
         }

         for (int i = 0; i < mNVV.getPickedVertexState().getPicked().size(); i++)
         {
         if (mDataParser.getDerivedData().getNodeTable().containsKey(mNVV.getPickedVertexState().getPicked().toArray()[i].toString()))
         {
         mDataParser.getDerivedData().getNodeTable().get(mNVV.getPickedVertexState().getPicked().toArray()[i].toString()).setSelected(true);
         }
         }
         for (int i = 0; i < mNVV.getPickedEdgeState().getPicked().size(); i++)
         {
         if (mDataParser.getDerivedData().getEdgeTable().containsKey(mNVV.getPickedEdgeState().getPicked().toArray()[i].toString()))
         {
         mDataParser.getDerivedData().getEdgeTable().get(mNVV.getPickedEdgeState().getPicked().toArray()[i].toString()).setSelected(true);
         }
         }*/


        Lookup nvvLookup = mDataParser.getNetworkVVLookup().getNVVLookup();
        Collection<? extends NetworkVisualizationViewer> VVSet = nvvLookup.lookupAll(NetworkVisualizationViewer.class);
        for (int i = 0; i < VVSet.size(); i++)
        {
            ((NetworkVisualizationViewer) VVSet.toArray()[i]).repaint();
            //((NetworkVisualizationViewer) VVSet.toArray()[i]).firePropertyChange("Selected", false, false);
        }

        //mNVV.repaint();
    }

    private void ClearSelected()
    {
        if (!mNVV.getPickedVertexState().getPicked().isEmpty())
        {
            for (int i = 0; i < mNVV.getPickedVertexState().getPicked().size(); i++)
            {
//                if (mDataParser.getNodeTable().get(mNVV.getPickedVertexState().getPicked().toArray()[i].toString()) != null)
//                {
                String localvertex = mNVV.getPickedVertexState().getPicked().toArray()[i].toString();
                if (mDataParser.getNodeTable().containsKey(localvertex))
                {
                    mDataParser.getNodeTable().get(localvertex).setSelected(false);
                }
//                }
//                if (mDataParser.getDerivedData().getNodeTable().get(mNVV.getPickedVertexState().getPicked().toArray()[i].toString()) != null)
//                {
                if (mDataParser.getDerivedData().getNodeTable().containsKey(localvertex))
                {
                    mDataParser.getDerivedData().getNodeTable().get(localvertex).setSelected(false);
                }
//                }
            }
        }
        if (!mNVV.getPickedEdgeState().getPicked().isEmpty())
        {
            for (int i = 0; i < mNVV.getPickedEdgeState().getPicked().size(); i++)
            {
                String localedge = mNVV.getPickedEdgeState().getPicked().toArray()[i].toString();
//                if (mDataParser.getEdgeTable().get(edge) != null)
//                {
                if (mDataParser.getEdgeTable().containsKey(localedge))
                {
                    mDataParser.getEdgeTable().get(localedge).setSelected(false);
                }
//                }
//                if (mDataParser.getDerivedData().getEdgeTable().get(edge) != null)
//                {
                if (mDataParser.getDerivedData().getEdgeTable().containsKey(localedge))
                {
                    mDataParser.getDerivedData().getEdgeTable().get(localedge).setSelected(false);
                }
//                }
            }
        }
    }

    /*    public void InVisMouseDragged() //MouseEvent e if we want to force a specific button.
     {
        
     for (int i = 0; i < mNVV.getGraphLayout().getGraph().getVertices().size(); i++)
     {
     mDataParser.getNodeTable().get(mNVV.getGraphLayout().getGraph().getVertices().toArray()[i].toString()).setSelected(false);
     }
     for (int i = 0; i < mNVV.getGraphLayout().getGraph().getEdges().size(); i++)
     {
     mDataParser.getEdgeTable().get(mNVV.getGraphLayout().getGraph().getEdges().toArray()[i].toString()).setSelected(false);
     }

     for (int i = 0; i < mNVV.getPickedVertexState().getPicked().size(); i++)
     {
     mDataParser.getNodeTable().get(mNVV.getPickedVertexState().getPicked().toArray()[i].toString()).setSelected(true);
     }
     for (int i = 0; i < mNVV.getPickedEdgeState().getPicked().size(); i++)
     {
     mDataParser.getEdgeTable().get(mNVV.getPickedEdgeState().getPicked().toArray()[i].toString()).setSelected(true);
     }
     if (mDataParser.HasDerivedData())
     {
     for (int i = 0; i < mNVV.getPickedVertexState().getPicked().size(); i++)
     {
     mDataParser.getDerivedData().getNodeTable().get(mNVV.getPickedVertexState().getPicked().toArray()[i].toString()).setSelected(true);
     }
     for (int i = 0; i < mNVV.getPickedEdgeState().getPicked().size(); i++)
     {
     mDataParser.getDerivedData().getEdgeTable().get(mNVV.getPickedEdgeState().getPicked().toArray()[i].toString()).setSelected(true);
     }
     }

     mNVV.repaint();
     }*/
    /*
     * Copyright (c) 2005, the JUNG Project and the Regents of the University of
     * California All rights reserved.
     *
     * This software is open-source under the BSD license; see either
     * "license.txt" or http://jung.sourceforge.net/license.txt for a
     * description. Created on Mar 8, 2005
     *
     */
    /**
     * PickingGraphMousePlugin supports the picking of graph elements with the
     * mouse. MouseButtonOne picks a single vertex or edge, and MouseButtonTwo
     * adds to the set of selected Vertices or EdgeType. If a Vertex is selected
     * and the mouse is dragged while on the selected Vertex, then that Vertex
     * will be repositioned to follow the mouse until the button is released.
     *
     * @author Tom Nelson
     */
    /**
     * the picked Vertex, if any
     */
    protected V vertex;
    /**
     * the picked Edge, if any
     */
    protected E edge;
    /**
     * the x distance from the picked vertex center to the mouse point
     */
    protected double offsetx;
    /**
     * the y distance from the picked vertex center to the mouse point
     */
    protected double offsety;
    /**
     * controls whether the Vertices may be moved with the mouse
     */
    protected boolean locked;
    /**
     * additional modifiers for the action of adding to an existing selection
     */
    protected int addToSelectionModifiers;
    /**
     * used to draw a rectangle to contain picked vertices
     */
    protected Rectangle2D rect = new Rectangle2D.Float();
    /**
     * the Paintable for the lens picking rectangle
     */
    protected Paintable lensPaintable;
    /**
     * color for the picking rectangle
     */
    protected Color lensColor = Color.cyan;

    /**
     * create an instance with default settings
     */
    public InVisGraphMousePlugin(NetworkVisualizationViewer vv)
    {
        this(InputEvent.BUTTON1_MASK, InputEvent.BUTTON1_MASK | InputEvent.SHIFT_MASK);
        mNVV = vv;
        mDataParser = Lookup.getDefault().lookup(DataParser.class);


    }

    /**
     * create an instance with overides
     *
     * @param selectionModifiers for primary selection
     * @param addToSelectionModifiers for additional selection
     */
    public InVisGraphMousePlugin(int selectionModifiers, int addToSelectionModifiers)
    {
        super(selectionModifiers);
        this.addToSelectionModifiers = addToSelectionModifiers;
        this.lensPaintable = new LensPaintable();
        this.cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    }

    /**
     * @return Returns the lensColor.
     */
    public Color getLensColor()
    {
        return lensColor;
    }

    /**
     * @param lensColor The lensColor to set.
     */
    public void setLensColor(Color lensColor)
    {
        this.lensColor = lensColor;
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * a Paintable to draw the rectangle used to pick multiple Vertices
     *
     * @author Tom Nelson
     *
     */
    class LensPaintable implements Paintable
    {

        @Override
        public void paint(Graphics g)
        {
            Color oldColor = g.getColor();
            g.setColor(lensColor);
            ((Graphics2D) g).draw(rect);
            g.setColor(oldColor);
        }

        @Override
        public boolean useTransform()
        {
            return false;
        }
    }

    /**
     * For primary modifiers (default, MouseButton1): pick a single Vertex or
     * Edge that is under the mouse pointer. If no Vertex or edge is under the
     * pointer, unselect all picked Vertices and edges, and set up to draw a
     * rectangle for multiple selection of contained Vertices. For additional
     * selection (default Shift+MouseButton1): Add to the selection, a single
     * Vertex or Edge that is under the mouse pointer. If a previously picked
     * Vertex or Edge is under the pointer, it is un-picked. If no vertex or
     * Edge is under the pointer, set up to draw a multiple selection rectangle
     * (as above) but do not unpick previously picked elements.
     *
     * @param e the event
     */
    @SuppressWarnings("unchecked")
    @Override
    public void mousePressed(MouseEvent e)
    {
        down = e.getPoint();
        VisualizationViewer<V, E> vv = (VisualizationViewer) e.getSource();
        GraphElementAccessor<V, E> pickSupport = vv.getPickSupport();
        PickedState<V> pickedVertexState = vv.getPickedVertexState();
        PickedState<E> pickedEdgeState = vv.getPickedEdgeState();
        if (pickSupport != null && pickedVertexState != null)
        {
            Layout<V, E> layout = vv.getGraphLayout();
            if (e.getModifiers() == modifiers)
            {
                rect.setFrameFromDiagonal(down, down);
                // p is the screen point for the mouse event
                Point2D ip = e.getPoint();

                vertex = pickSupport.getVertex(layout, ip.getX(), ip.getY());
                edge = pickSupport.getEdge(layout, ip.getX(), ip.getY());
                if (vertex != null)
                {
                    if (pickedVertexState.isPicked(vertex) == false)
                    {
                        this.ClearSelected();
                        pickedVertexState.clear();
                        pickedEdgeState.clear();
                        pickedVertexState.pick(vertex, true);
                        /*if (mDataParser.getNodeTable().containsKey(vertex))
                         {
                         mDataParser.getNodeTable().get(vertex).setSelected(true);
                         }
                         if (mDataParser.HasDerivedData())
                         {
                         if (mDataParser.getDerivedData().getNodeTable().containsKey(vertex))
                         {
                         mDataParser.getDerivedData().getNodeTable().get(vertex).setSelected(true);
                         }
                         }*/
                    }
                    // layout.getLocation applies the layout transformer so
                    // q is transformed by the layout transformer only
                    Point2D q = layout.transform(vertex);
                    // transform the mouse point to graph coordinate system
                    Point2D gp = vv.getRenderContext().getMultiLayerTransformer().inverseTransform(Layer.LAYOUT, ip);

                    offsetx = (float) (gp.getX() - q.getX());
                    offsety = (float) (gp.getY() - q.getY());
                } else if (edge != null)
                {
                    if (pickedEdgeState.isPicked(edge) == false)
                    {
                        this.ClearSelected();
                        pickedEdgeState.clear();
                        pickedVertexState.clear();
                        pickedEdgeState.pick(edge, true);
                        /*if (mDataParser.getEdgeTable().containsKey(edge))
                         {
                         mDataParser.getEdgeTable().get(edge).setSelected(true);
                         }
                         if (mDataParser.HasDerivedData())
                         {
                         if (mDataParser.getDerivedData().getEdgeTable().containsKey(edge))
                         {
                         mDataParser.getDerivedData().getEdgeTable().get(edge).setSelected(true);
                         }
                         }*/
                    }
                } else
                {
                    vv.addPostRenderPaintable(lensPaintable);
                    this.ClearSelected();
                    pickedEdgeState.clear();
                    pickedVertexState.clear();
                }

            } else if (e.getModifiers() == addToSelectionModifiers)
            {
                vv.addPostRenderPaintable(lensPaintable);
                rect.setFrameFromDiagonal(down, down);
                Point2D ip = e.getPoint();
                //vertex = pickSupport.getVertex(layout, ip.getX(), ip.getY());
                //edge = pickSupport.getEdge(layout, ip.getX(), ip.getY());
                if (vertex != null)
                {
                    boolean wasThere = pickedVertexState.pick(vertex, !pickedVertexState.isPicked(vertex));
                    if (wasThere)
                    {
                        vertex = null;
                    } else
                    {

                        // layout.getLocation applies the layout transformer so
                        // q is transformed by the layout transformer only
                        Point2D q = layout.transform(vertex);
                        // translate mouse point to graph coord system
                        Point2D gp = vv.getRenderContext().getMultiLayerTransformer().inverseTransform(Layer.LAYOUT, ip);

                        offsetx = (float) (gp.getX() - q.getX());
                        offsety = (float) (gp.getY() - q.getY());
                    }
                } else if (edge != null)
                {
                    pickedEdgeState.pick(edge, !pickedEdgeState.isPicked(edge));

                    /*mDataParser.getEdgeTable().get(edge).setSelected(true);
                     if (mDataParser.HasDerivedData())
                     {
                     if (mDataParser.getDerivedData().getEdgeTable().containsKey(edge))
                     {
                     mDataParser.getDerivedData().getEdgeTable().get(edge).setSelected(true);
                     }
                     }*/
                }
            }
        }
        if (SwingUtilities.isLeftMouseButton(e))//MouseEvent.BUTTON1)
        {
            InVisMousePressed();
        }
        if (vertex != null || edge != null)
        {
            e.consume();
        }
    }

    /**
     * If the mouse is dragging a rectangle, pick the Vertices contained in that
     * rectangle
     *
     * clean up settings from mousePressed
     */
    @SuppressWarnings("unchecked")
    @Override
    public void mouseReleased(MouseEvent e)
    {
        VisualizationViewer<V, E> vv = (VisualizationViewer) e.getSource();
        if (e.getModifiers() == modifiers)
        {
            if (down != null)
            {
                Point2D out = e.getPoint();

                if (vertex == null && heyThatsTooClose(down, out, 5) == false)
                {
                    pickContainedVertices(vv, down, out, true);
                }
            }
        } else if (e.getModifiers() == this.addToSelectionModifiers)
        {
            if (down != null)
            {
                Point2D out = e.getPoint();

                if (vertex == null && heyThatsTooClose(down, out, 5) == false)
                {
                    pickContainedVertices(vv, down, out, false);
                }
            }
        }
        down = null;
        vertex = null;
        edge = null;
        rect.setFrame(0, 0, 0, 0);
        if (SwingUtilities.isLeftMouseButton(e))//MouseEvent.BUTTON1)
        {
            InVisMousePressed();
        }
        vv.removePostRenderPaintable(lensPaintable);
        vv.repaint();
    }

    /**
     * If the mouse is over a picked vertex, drag all picked vertices with the
     * mouse. If the mouse is not over a Vertex, draw the rectangle to select
     * multiple Vertices
     *
     */
    @SuppressWarnings("unchecked")
    @Override
    public void mouseDragged(MouseEvent e)
    {
        if (locked == false)
        {
            VisualizationViewer<V, E> vv = (VisualizationViewer) e.getSource();
            if (vertex != null)
            {
                Point p = e.getPoint();
                Point2D graphPoint = vv.getRenderContext().getMultiLayerTransformer().inverseTransform(p);
                Point2D graphDown = vv.getRenderContext().getMultiLayerTransformer().inverseTransform(down);
                Layout<V, E> layout = vv.getGraphLayout();
                double dx = graphPoint.getX() - graphDown.getX();
                double dy = graphPoint.getY() - graphDown.getY();
                PickedState<V> ps = vv.getPickedVertexState();

                for (V v : ps.getPicked())
                {
                    Point2D vp = layout.transform(v);
                    vp.setLocation(vp.getX() + dx, vp.getY() + dy);
                    layout.setLocation(v, vp);
                }
                down = p;

            } else
            {
                Point2D out = e.getPoint();
                if (e.getModifiers() == this.addToSelectionModifiers
                        || e.getModifiers() == modifiers)
                {
                    if (down != null && out != null)
                    {
                        /*                        InputOutput io = IOProvider.getDefault().getIO("win", false);
                         io.getOut().println(down.toString());
                         io.getOut().println(out.toString());
                         io.getOut().close();
                         */
                        rect.setFrameFromDiagonal(down, out);
                    }

                }
            }
            //InVisMousePressed();
            if (vertex != null)
            {
                e.consume();
            }

            vv.repaint();
        }
    }

    /**
     * rejects picking if the rectangle is too small, like if the user meant to
     * select one vertex but moved the mouse slightly
     *
     * @param p
     * @param q
     * @param min
     * @return
     */
    private boolean heyThatsTooClose(Point2D p, Point2D q, double min)
    {
        return Math.abs(p.getX() - q.getX()) < min
                && Math.abs(p.getY() - q.getY()) < min;
    }

    /**
     * pick the vertices inside the rectangle created from points 'down' and
     * 'out'
     *
     */
    protected void pickContainedVertices(VisualizationViewer<V, E> vv, Point2D down, Point2D out, boolean clear)
    {

        Layout<V, E> layout = vv.getGraphLayout();
        PickedState<V> pickedVertexState = vv.getPickedVertexState();

        Rectangle2D pickRectangle = new Rectangle2D.Double();
        pickRectangle.setFrameFromDiagonal(down, out);

        if (pickedVertexState != null)
        {
            if (clear)
            {
                pickedVertexState.clear();
            }
            GraphElementAccessor<V, E> pickSupport = vv.getPickSupport();

            Collection<V> picked = pickSupport.getVertices(layout, pickRectangle);
            for (V v : picked)
            {
                pickedVertexState.pick(v, true);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
        JComponent c = (JComponent) e.getSource();
        c.setCursor(cursor);
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        JComponent c = (JComponent) e.getSource();
        c.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    /**
     * @return Returns the locked.
     */
    public boolean isLocked()
    {
        return locked;
    }

    /**
     * @param locked The locked to set.
     */
    public void setLocked(boolean locked)
    {
        this.locked = locked;
    }
}
