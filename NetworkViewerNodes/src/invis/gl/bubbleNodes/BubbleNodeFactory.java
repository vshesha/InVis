package invis.gl.bubbleNodes;

import invis.gl.networkapi.BubbleApi;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 *
 * @author Matt
 */
public class BubbleNodeFactory extends ChildFactory<BubbleApi>
{

    private List<BubbleApi> mBubbleList;

    public BubbleNodeFactory(List<BubbleApi> bubbleList)
    {
        mBubbleList = bubbleList;
    }

    @Override
    protected boolean createKeys(List<BubbleApi> list)
    {
        for (int i = 0; i < mBubbleList.size(); i++)
        {
            list.add(mBubbleList.get(i));
        }
        return true;
    }

    @Override
    public Node createNodeForKey(BubbleApi data)
    {
        Node node = new BubbleNode(Children.create(new BubbleElementFactory(data), true),data);
        return (node);
    }
}