
package NetworkSequenceNodes;

import NetworkApi.NetworkSequenceApi;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author GameLab
 */
public class NetworkSequenceNode extends AbstractNode{
    private NetworkSequenceApi mNSA;
    
    public NetworkSequenceNode(Children children, NetworkSequenceApi data)
    {
        super(children, Lookups.singleton(data)); //what the hell does this do?
        mNSA = data;
        setDisplayName(mNSA.getInteractionCount() + " Name: " + data.getSequenceName() + " Count: " + data.getClass() + " Rating: " + data.getRating());
    }
    
    //ignoring properties sheets for now...
    
}
