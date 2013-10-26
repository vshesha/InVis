package invis.gl.bubbleNodes;

import invis.gl.NBNodeFactories.NetworkFactories.NetworkElementSetNodeFactory;
import invis.gl.networkapi.BubbleApi;
import invis.gl.networkapi.NetworkElementApi;
import java.util.ArrayList;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 *
 * @author Matt
 */
class BubbleElementFactory extends ChildFactory<List<NetworkElementApi>>
{

    private BubbleApi mBubble;

    public BubbleElementFactory(BubbleApi data)
    {
        mBubble = data;
    }

    @Override
    protected boolean createKeys(List<List<NetworkElementApi>> list)
    {
        List<NetworkElementApi> SourceTargetHackList = new ArrayList<NetworkElementApi>();
        SourceTargetHackList.add(mBubble.getSource());
        SourceTargetHackList.add(mBubble.getTarget());

        list.add(SourceTargetHackList);
        for (int i = 0; i < mBubble.getPaths().size(); i++)
        {
            list.add(mBubble.getPaths().get(i));
        }
        return true;
    }

    @Override
    public Node createNodeForKey(List<NetworkElementApi> data)
    {
        //Node node = new BubbleElementNode(Children.create(new PathNodeFactory(data), true), data);
        Node node = new PathNode(Children.create(new NetworkElementSetNodeFactory(data), true), data);
        return (node);
    }
}
