package invis.gl.NBNodeFactories.StepBasedFactories;

import invis.gl.NBNodeFactories.NetworkFactories.NetworkElementSetNodeFactory;
import invis.gl.NetworkClusterApi.NetworkClusterVertexApi;
import invis.gl.sequenceapi.NetworkInteractionSequenceApi;
import invis.gl.stepbasednodes.StepBasedVertexNode;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 *
 * @author Matt
 */
public class StepbasedSequenceNodeFactory extends ChildFactory<NetworkClusterVertexApi>
{

    private NetworkInteractionSequenceApi mSSA;

    public StepbasedSequenceNodeFactory(NetworkInteractionSequenceApi key)
    {
        mSSA = key;
    }

    @Override
    public boolean createKeys(List<NetworkClusterVertexApi> list)
    {
        for (int sequenceIdx = 0; sequenceIdx < mSSA.getInteractionsList().size(); sequenceIdx++)
        {
            list.add(mSSA.getStepbasedSequenceSet().get(sequenceIdx));
        }
        return true;
    }

    @Override
    public Node createNodeForKey(NetworkClusterVertexApi key)
    {
        StepBasedVertexNode node = new StepBasedVertexNode(
                Children.create(new NetworkElementSetNodeFactory(
                key.getNetworkElementSet()), true), key);
        return (node);
    }
}