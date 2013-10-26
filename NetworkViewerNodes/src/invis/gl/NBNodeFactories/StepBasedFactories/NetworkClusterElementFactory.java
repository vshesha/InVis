
package invis.gl.NBNodeFactories.StepBasedFactories;

import invis.gl.NBNodeFactories.NetworkFactories.NetworkElementSetNodeFactory;
import invis.gl.NetworkClusterApi.NetworkClusterElementApi;
import invis.gl.stepbasednodes.StepBasedEdgeNode;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;

/**
 *
 * @author Matt
 */
public class NetworkClusterElementFactory extends ChildFactory<NetworkClusterElementApi>
{

    private List<NetworkClusterElementApi> mData;

    public NetworkClusterElementFactory(List<NetworkClusterElementApi> data)
    {
        mData = data;
    }

    @Override
    protected boolean createKeys(List<NetworkClusterElementApi> list)
    {
        //For every piece of data, make a node.
        for (int i = 0; i < mData.size(); i++)
        {
            list.add(mData.get(i));
        }
        return (true);
    }

    @Override
    public StepBasedEdgeNode createNodeForKey(NetworkClusterElementApi data)
    {
        StepBasedEdgeNode node = new StepBasedEdgeNode(Children.create(
                new NetworkElementSetNodeFactory(
                data.getNetworkElementSet()), true), data);
        return (node);
    }
}

