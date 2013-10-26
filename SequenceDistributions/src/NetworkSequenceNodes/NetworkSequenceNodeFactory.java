package NetworkSequenceNodes;

import NetworkApi.NetworkSequenceApi;
import NetworkApi.NetworkSequenceSetApi;
import invis.gl.NetworkNodeFactories.NetworkInteractionsNodeFactory;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;

/**
 *
 * @author GameLab
 */
public class NetworkSequenceNodeFactory extends ChildFactory<NetworkSequenceApi> {

    private NetworkSequenceSetApi mNSS;

    public NetworkSequenceNodeFactory(NetworkSequenceSetApi key) {
        mNSS = key;
    }

    @Override
    protected boolean createKeys(List<NetworkSequenceApi> list) {
        for (int sequenceIdx = 0; sequenceIdx < mNSS.getSequenceSetSize(); sequenceIdx++) {
            list.add(mNSS.getNetworkSequenceSet().get(sequenceIdx));
        }
        return true;
    }

    @Override
    protected NetworkSequenceNode createNodeForKey(NetworkSequenceApi key) {
        NetworkSequenceNode node = new NetworkSequenceNode(
                Children.create(
                new NetworkInteractionsNodeFactory(key),
                true),
                key);
        node.setDisplayName(key.getInteractions().toString() + "Count: " + key.getCount());
        return node;
    }
}
