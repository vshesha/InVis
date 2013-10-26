package invis.gl.NBNodeFactories.StepBasedFactories;

import invis.gl.NBNodeFactories.NetworkFactories.NetworkElementSetNodeFactory;
import invis.gl.NetworkClusterApi.NetworkClusterVertexApi;
import invis.gl.stepbasednodes.StepBasedVertexNode;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;

/**
 *
 * @author Matt
 */
@Deprecated
public class StepBasedVertexNodeFactory extends ChildFactory<NetworkClusterVertexApi>
{

    private List<NetworkClusterVertexApi> mData;

    public StepBasedVertexNodeFactory(List<NetworkClusterVertexApi> data)
    {
        mData = data;
    }

    @Override
    protected boolean createKeys(List<NetworkClusterVertexApi> list)
    {
        //For every piece of data, make a node.
        for (int i = 0; i < mData.size(); i++)
        {
            list.add(mData.get(i));
        }
        return (true);
    }

    @Override
    public StepBasedVertexNode createNodeForKey(NetworkClusterVertexApi data)
    {
        StepBasedVertexNode node = new StepBasedVertexNode(
                Children.create(new NetworkElementSetNodeFactory(
                data.getNetworkElementSet()), true), data);
        return (node);

    }
}