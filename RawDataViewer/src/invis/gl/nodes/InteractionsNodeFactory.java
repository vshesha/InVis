package invis.gl.nodes;

import invis.gl.api.RawCaseModelApi;
import invis.gl.api.RawInteractionApi;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 *
 * @author Matt
 */
public class InteractionsNodeFactory extends ChildFactory<RawInteractionApi>
{
    private RawCaseModelApi mCMA;

    InteractionsNodeFactory(RawCaseModelApi key)
    {
        mCMA = key;
    }

    @Override
    protected boolean createKeys(List<RawInteractionApi> list)
    {
        for (int i = 0; i < mCMA.getInteractionsList().size(); i++)
        {
            list.add(mCMA.getInteractionsList().get(i));
        }
        return (true);
    }

    @Override
    protected Node createNodeForKey(RawInteractionApi key)
    {
        Node node = new InteractionsNode(Children.create(new InteractionsElementNodeFactory(key), true), key);
        node.setDisplayName(key.getPreState() + "->" + key.getAction() + "->" + key.getPostState() + ": Ln:" + key.getFileLineIndex());
        return (node);
    }
}
