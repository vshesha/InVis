package invis.gl.NBNodeFactories.NetworkFactories;

import invis.gl.NetworkViewerNodes.NetworkEdgeNode;
import invis.gl.NetworkViewerNodes.NetworkVertexNode;
import invis.gl.networkapi.NetworkElementApi;
import invis.gl.networkapi.NetworkElementApi.NetworkElementType;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 *
 * @author Matt
 *
 * This lets you look at the set of NetworkEdges that make up a step
 * based-graph-edge.
 */
public class NetworkElementSetNodeFactory extends ChildFactory<NetworkElementApi>
{

    private List<NetworkElementApi> mData;

    public NetworkElementSetNodeFactory(List<NetworkElementApi> data)
    {
        mData = data;
    }

    @Override
    protected boolean createKeys(List<NetworkElementApi> list)
    {
        for (int i = 0; i < mData.size(); i++)
        {
            list.add(mData.get(i));
        }
        return (true);
    }

    @Override
    public Node createNodeForKey(NetworkElementApi key)
    {
        Node node = null;
        if (key.getNetworkElementType() == NetworkElementType.EDGE)
        {
            node = new NetworkEdgeNode(Children.LEAF, key);
        }

        if (key.getNetworkElementType() == NetworkElementType.NODE)
        {
            node = new NetworkVertexNode(Children.LEAF, key);
        }

        //node.setDisplayName(key.getValue());
        return (node);

    }
}
