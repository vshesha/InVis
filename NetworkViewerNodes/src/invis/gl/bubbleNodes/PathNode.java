package invis.gl.bubbleNodes;

import invis.gl.networkapi.NetworkElementApi;
import java.util.List;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author Matt
 */
public class PathNode extends AbstractNode
{

    private List<NetworkElementApi> mEdges;

    public PathNode(Children children, List<NetworkElementApi> edges)
    {
        super(children, Lookups.singleton(edges));
        this.setPathModel(edges);
        if (edges.get(0).getNetworkElementType() == NetworkElementApi.NetworkElementType.NODE)
        {
            this.setDisplayName("End Points");
        }
        if (edges.get(0).getNetworkElementType() == NetworkElementApi.NetworkElementType.EDGE)
        {
            this.setDisplayName("Path-Size: " + mEdges.size());
        }

    }

    public List<NetworkElementApi> getPathModel()
    {
        return (mEdges);
    }

    private void setPathModel(List<NetworkElementApi> edges)
    {
        mEdges = edges;
    }
}
