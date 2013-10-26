package invis.gl.NetworkVisualizationViewer;

/*
 * Copyright (c) 2005, the JUNG Project and the Regents of the University 
 * of California
 * All rights reserved.
 *
 * This software is open-source under the BSD license; see either
 * "license.txt" or
 * http://jung.sourceforge.net/license.txt for a description.
 * Created on Mar 8, 2005
 *
 */
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractGraphMousePlugin;
import edu.uci.ics.jung.visualization.transform.MutableTransformer;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

/**
 * TranslatingGraphMousePlugin uses a MouseButtonOne press and drag gesture to
 * translate the graph display in the x and y direction. The default
 * MouseButtonOne modifier can be overridden to cause a different mouse gesture
 * to translate the display.
 *
 *
 * @author Tom Nelson
 */
public class TranslatingGraphMousePlugin_mwj extends AbstractGraphMousePlugin
        implements MouseListener, MouseMotionListener
{

    /**
     */
    public TranslatingGraphMousePlugin_mwj()
    {
        this(MouseEvent.BUTTON1_MASK);
    }

    /**
     * create an instance with passed modifer value
     *
     * @param modifiers the mouse event modifier to activate this function
     */
    public TranslatingGraphMousePlugin_mwj(int modifiers)
    {
        super(modifiers);
        this.cursor = Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
    }

    /**
     * Check the event modifiers. Set the 'down' point for later use. If this
     * event satisfies the modifiers, change the cursor to the system 'move
     * cursor'
     *
     * @param e the event
     */
    @Override
    public void mousePressed(MouseEvent e)
    {
        VisualizationViewer vv = (VisualizationViewer) e.getSource();
        boolean accepted = checkModifiers(e);
        down = e.getPoint();
        if (accepted)
        {
            vv.setCursor(cursor);
        }
    }

    /**
     * unset the 'down' point and change the cursoe back to the system default
     * cursor
     */
    @Override
    public void mouseReleased(MouseEvent e)
    {
        VisualizationViewer vv = (VisualizationViewer) e.getSource();
        down = null;
        vv.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    /**
     * check the modifiers. If accepted, translate the graph according to the
     * dragging of the mouse pointer
     *
     * @param e the event
     */
    @Override
    public void mouseDragged(MouseEvent e)
    {
        VisualizationViewer<String, String> vv = (VisualizationViewer<String, String>) e.getSource();
        boolean accepted = checkModifiers(e);
        if (accepted)
        {
            MutableTransformer modelTransformer = vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.LAYOUT);
            vv.setCursor(cursor);
            try
            {
                if (down != null)
                {
                    Point2D q = vv.getRenderContext().getMultiLayerTransformer().inverseTransform(down);
                    Point2D p = vv.getRenderContext().getMultiLayerTransformer().inverseTransform(e.getPoint());
                    float dx = (float) (p.getX() - q.getX());
                    float dy = (float) (p.getY() - q.getY());

                    modelTransformer.translate(dx, dy);
                    down.x = e.getX();
                    down.y = e.getY();
                }
            } catch (RuntimeException ex)
            {
                System.err.println("down = " + down + ", e = " + e);
                throw ex;
            }

            e.consume();
            vv.repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        // TODO Auto-generated method stub
    }
}
