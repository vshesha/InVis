package invis.gl.nodes;


import invis.gl.api.RawInteractionApi;
import java.util.List;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 *
 * @author Matt
 */
class InteractionsElementNodeFactory extends ChildFactory<String>
{

    private RawInteractionApi mIMA;


    public InteractionsElementNodeFactory(RawInteractionApi key)
    {
        mIMA = key;
    }

    @Override
    protected boolean createKeys(List<String> list)
    {
        list.add("Pre-State: "+mIMA.getPreState());
        list.add("Action: "+mIMA.getAction());
        list.add("Post-State: "+mIMA.getPostState());
        list.add("Line: "+mIMA.getFileLineIndex().toString());
        list.add("Interaction#: "+mIMA.getInteractionCount().toString());
        
        return (true);
    }

    @Override
    protected Node createNodeForKey(String key)
    {
        Node node = new AbstractNode(Children.LEAF);
        node.setDisplayName(key);
        return (node);
    }
}
