package invis.gl.NBNodeFactories.NetworkFactories;

import invis.gl.networkapi.NetworkElementApi;
import invis.gl.networkapi.NetworkElementApi.NetworkElementType;
import invis.gl.networkapi.NetworkInteractionApi;
import invis.gl.NetworkViewerNodes.NetworkEdgeNode;
import invis.gl.NetworkViewerNodes.NetworkVertexNode;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 *
 * @author Matt
 */
public class NetworkInteractionElementNodeFactory extends ChildFactory<NetworkElementApi>
{
    private NetworkInteractionApi mNIA;

    public NetworkInteractionElementNodeFactory(NetworkInteractionApi key)
    {
        mNIA = key;
    }

    @Override
    public boolean createKeys(List<NetworkElementApi> list)
    {
        list.add(mNIA.getPreState());
        list.add(mNIA.getAction());
        list.add(mNIA.getPostState());
        return (true);
    }

    @Override
    public Node createNodeForKey(NetworkElementApi key)
    {
        Node node = null;
        if (key.getNetworkElementType() == NetworkElementType.NODE)
        {
            /*
             * NetworkNodeNode
             */ node = new NetworkVertexNode(Children.LEAF, key);
             //node.setDisplayName(key.getValue());
        }
        if (key.getNetworkElementType() == NetworkElementType.EDGE)
        {
            /*
             * NetworkEdgeNode
             */ node = new NetworkEdgeNode(Children.LEAF, key);
             //node.setDisplayName(key.getValue());
        }
        //node.setDisplayName(key.getValue());
        return (node);
    }
}