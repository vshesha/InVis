
package invis.gl.NBNodeFactories.NetworkFactories;

import invis.gl.NetworkClusterApi.NetworkClusterVertexApi;
import invis.gl.NetworkViewerNodes.NetworkVertexNode;
import invis.gl.networkapi.NetworkElementApi;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 *
 * @author Matt
 */
@Deprecated
public class NetworkStepbasedVertexNodeFactory extends ChildFactory<NetworkElementApi>
{

    private NetworkClusterVertexApi mData;

    public NetworkStepbasedVertexNodeFactory(NetworkClusterVertexApi data)
    {
        mData = data;
    }
    
    @Override
    protected boolean createKeys(List<NetworkElementApi> list)
    {
/*        for (int i=0;i<mData.getNetworkElementSet().size();i++)
        {
            list.add(mData.getNetworkElementSet().get(i));
        }*/
        return (true);
    }
    
    @Override
    public Node createNodeForKey(NetworkElementApi key)
    {
        Node node = new NetworkVertexNode(Children.LEAF, key);
        //node.setDisplayName(key.getValue());
        return (node);
        
    }
    
}
