package invis.gl.bubbleNodes;

import invis.gl.networkapi.BubbleApi;
import invis.gl.networkapi.NetworkElementApi;
import java.util.List;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author Matt
 */
public class BubbleNode extends AbstractNode
{

    private BubbleApi mBubble;

    public BubbleNode(Children children, BubbleApi bubble)
    {
        super(children, Lookups.singleton(bubble));
        this.setModel(bubble);
        this.setDisplayName("Src: " + mBubble.getSource().getMostFrequentUniquePostCondition()
                + ", Trgt: " + mBubble.getTarget().getMostFrequentUniquePostCondition()
                + ", Path-Count: " + mBubble.getPaths().size());
    }

    public List<List<NetworkElementApi>> getPaths()
    {
        return (mBubble.getPaths());
    }

    public NetworkElementApi getSource()
    {
        return (mBubble.getSource());
    }

    public NetworkElementApi getTarget()
    {
        return (mBubble.getTarget());
    }

    private void setModel(BubbleApi bubble)
    {
        mBubble = bubble;
    }
}
