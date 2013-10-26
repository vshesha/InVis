package invis.gl.viewer_v1;

import invis.gl.rawinput.RawDataEntry;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author Matt
 */
//public class RawDataNodeFactory extends AbstractNode 
public class RawDataNodeFactory extends ChildFactory<RawDataEntry> 
{
    private List<RawDataEntry> mResultList;
    
    public RawDataNodeFactory(List<RawDataEntry> list)
    {
        mResultList = list;
    }
   
    //@Override
    @Override
    protected boolean createKeys(List<RawDataEntry> list)
    {
        for (RawDataEntry rde : mResultList)
        {
            list.add(rde);
        }
        return (true);
    }

    @Override
    protected Node createNodeForKey(RawDataEntry rde)
    {
        RawDataNode node = new RawDataNode(Children.create(new RawDataElementFactory(rde), true), rde);
        node.setDisplayName(rde.getData());
        return (node);
    }
}
