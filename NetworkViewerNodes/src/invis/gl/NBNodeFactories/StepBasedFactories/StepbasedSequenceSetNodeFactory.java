package invis.gl.NBNodeFactories.StepBasedFactories;

import invis.gl.sequenceapi.NetworkInteractionSequenceApi;
import invis.gl.sequenceapi.NetworkInteractionSequenceSetApi;
import invis.gl.stepbasednodes.StepbasedSequenceNode;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;

/**
 *
 * @author Matt
 */
public class StepbasedSequenceSetNodeFactory extends ChildFactory<NetworkInteractionSequenceApi>//<StepbasedSequenceApi>
{

    private NetworkInteractionSequenceSetApi mSSA;

    public StepbasedSequenceSetNodeFactory(NetworkInteractionSequenceSetApi key)
    {
        mSSA = key;
    }

    @Override
    public boolean createKeys(List<NetworkInteractionSequenceApi> list)
    {
        for (int sequenceIdx = 0; sequenceIdx < mSSA.getSequenceSetSize(); sequenceIdx++)
        {
                list.add(mSSA.getNetworkSequenceSet().get(sequenceIdx));
        }
        return true;
    }

    @Override
    public StepbasedSequenceNode createNodeForKey(NetworkInteractionSequenceApi key)
    {
        StepbasedSequenceNode node = new StepbasedSequenceNode(
                Children.create(
                new StepbasedSequenceNodeFactory(key),
                true),
                key);
        //node.setDisplayName(key.toString());
        return (node);
    }
}