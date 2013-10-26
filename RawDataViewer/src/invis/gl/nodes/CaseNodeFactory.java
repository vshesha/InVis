package invis.gl.nodes;

import invis.gl.api.CaseSetModelApi;
import invis.gl.api.RawCaseModelApi;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 *
 * @author Matt
 */
public class CaseNodeFactory extends ChildFactory<RawCaseModelApi>
{
    private CaseSetModelApi mCSMA;
    
    public CaseNodeFactory(CaseSetModelApi key)
    {
        mCSMA = key;
    }

    @Override
    protected boolean createKeys(List<RawCaseModelApi> list)
    {
        for (int i = 0; i < mCSMA.getCaseList().size();i++)
        {
            list.add(mCSMA.getCaseList().get(i));
        }
        return (true);
    }
    
    @Override
    protected Node createNodeForKey(RawCaseModelApi key)
    {
        Node node = new CaseNode(Children.create(new InteractionsNodeFactory(key), true), key);
        node.setDisplayName(key.getCaseName());
        return (node);
    }
    
}
