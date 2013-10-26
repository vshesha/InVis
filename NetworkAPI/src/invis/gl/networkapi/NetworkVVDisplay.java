package invis.gl.networkapi;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import invis.gl.NetworkVisualizationViewer.NetworkVisualizationViewer;
import invis.gl.dataprocessor.DataParser;
import invis.gl.graphvisualapi.NetworkDisplayApi.DisplayType;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.openide.util.Lookup;
import org.openide.windows.TopComponent;

/**
 * This is an API like class for topcomponents that hold a
 * NetworkVisualizationViewer. It allows other topcomponents to get these
 * class's NetworkVisualizationViewers, which is useful for changing display
 * transformers; namely edge width, vertex painters, etc.
 *
 * @author Matt
 */
public abstract class NetworkVVDisplay extends TopComponent
{

    protected NetworkVisualizationViewer mNVV;
    protected DataParser mDataParser;
    protected DirectedGraph<String, String> mGraph;

    public abstract void RebroadcastNetworkVisualizationViewer();

    public void RefreshVisualizationViewer()
    {
        mNVV.getGraphLayout().setGraph(mGraph);
        mNVV.repaint();
        this.repaint();
        this.revalidate();
    }

    @Override
    public void componentClosed()
    {
        if (mNVV != null)
        {
            mDataParser.getNetworkVVLookup().RemoveNetworkVV(mNVV);
            this.remove(mNVV);
            mNVV = null;
        }
        this.repaint();
    }
    protected abstract void sizeVisualizationViewer();

    public void RefreshGraph()
    {
        mDataParser = Lookup.getDefault().lookup(DataParser.class);
        mGraph = mDataParser.getGraph();
    }

    public NetworkVisualizationViewer getNVV()
    {
        return (mNVV);
    }

    public VisualizationViewer getVV()
    {
        return (mNVV);
    }
    
}




