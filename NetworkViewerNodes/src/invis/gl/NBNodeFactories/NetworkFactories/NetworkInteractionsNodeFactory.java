package invis.gl.NBNodeFactories.NetworkFactories;

import invis.gl.networkapi.NetworkCaseApi;
import invis.gl.networkapi.NetworkInteractionApi;
import invis.gl.sequenceapi.NetworkInteractionSequenceApi;
import invis.gl.NetworkViewerNodes.NetworkInteractionsNode;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 *
 * @author Matt
 */
public class NetworkInteractionsNodeFactory extends ChildFactory<NetworkInteractionApi> {

    private NetworkCaseApi mCMA;
    private NetworkInteractionSequenceApi mNSA;

    public NetworkInteractionsNodeFactory(NetworkCaseApi key) {
        mCMA = key;
        mNSA = null;
    }

    public NetworkInteractionsNodeFactory(NetworkInteractionSequenceApi key) {
        mNSA = key;
        mCMA = null;
    }

    @Override
    public boolean createKeys(List<NetworkInteractionApi> list) 
    {
        if (mCMA != null) 
        {
            for (int i = 0; i < mCMA.getInteractionsList().size(); i++) 
            {
                list.add(mCMA.getInteractionsList().get(i));
            }
            return true;
        } 
        else if(mNSA != null) 
        {
            for (int i = 0; i < mNSA.getInteractionCount(); i++) 
            {
                list.add(mNSA.getInteractionsList().get(i));
            }
            return true;
        }
            
        return (false); //should never reach this case
    }

    @Override
    public Node createNodeForKey(NetworkInteractionApi key) {
        NetworkInteractionsNode node = new NetworkInteractionsNode(
                Children.create(
                new NetworkInteractionElementNodeFactory(key),
                true),
                key);
//        node.setDisplayName(key.getDisplayData());//   key.getPreState() + "->" + key.getAction() + "->" + key.getPostState() + ": Ln:" + key.getFileLineIndex());
        return (node);
    }
}
