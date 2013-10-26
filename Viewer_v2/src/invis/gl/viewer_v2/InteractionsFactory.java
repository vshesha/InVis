package invis.gl.viewer_v2;

import java.util.List;
import java.util.StringTokenizer;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 *
 * @author Matt
 */
class InteractionsFactory extends ChildFactory<String>
{
    private String mData;

    public InteractionsFactory(String key)
    {
        mData = key;
    }
    
    @Override
    protected boolean createKeys(List<String> list)
    {
        StringTokenizer st = new StringTokenizer(mData, ",");
        while (st.hasMoreTokens())
        {
            list.add(st.nextToken().trim());
        }
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
