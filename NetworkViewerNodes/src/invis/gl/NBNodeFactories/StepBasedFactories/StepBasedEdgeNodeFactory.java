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
 *
 * This is used by the Stepbased Node viewer that lets you look at edges in the
 * step-based graph.
 *
 */
@Deprecated
public class StepBasedEdgeNodeFactory extends ChildFactory<NetworkClusterElementApi>
{

    private List<NetworkClusterElementApi> mData;

    public StepBasedEdgeNodeFactory(List<NetworkClusterElementApi> data)
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