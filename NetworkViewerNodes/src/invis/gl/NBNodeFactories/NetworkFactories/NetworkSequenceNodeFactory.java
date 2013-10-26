package invis.gl.NBNodeFactories.NetworkFactories;

import invis.gl.NetworkViewerNodes.NetworkSequenceNode;
import invis.gl.sequenceapi.NetworkInteractionSequenceApi;
import invis.gl.sequenceapi.NetworkInteractionSequenceSetApi;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;

/**
 *
 * @author Matt
 */
public class NetworkSequenceNodeFactory extends ChildFactory<NetworkInteractionSequenceApi>
{

    private NetworkInteractionSequenceSetApi mNSS;

    public NetworkSequenceNodeFactory(NetworkInteractionSequenceSetApi key)
    {
        mNSS = key;
    }

    @Override
    public boolean createKeys(List<NetworkInteractionSequenceApi> list)
    {
        for (int sequenceIdx = 0; sequenceIdx < mNSS.getSequenceSetSize(); sequenceIdx++)
        {
            list.add(mNSS.getNetworkSequenceSet().get(sequenceIdx));
        }
        return true;
    }

    @Override
    public NetworkSequenceNode createNodeForKey(NetworkInteractionSequenceApi key)
    {
        NetworkSequenceNode node = new NetworkSequenceNode(
                Children.create(
                new NetworkInteractionsNodeFactory(key),
                true),
                key);
        return (node);
    }
}