package invis.gl.viewer_v1;

import invis.gl.rawinput.RawDataEntry;
import java.util.List;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 *
 * @author Matt
 */
public class RawDataElementFactory extends ChildFactory<String>
{
    private RawDataEntry mParentRawDataEntry;
    
    /*
    RawDataElementFactory()
    {
        mParentRawDataEntry = new RawDataEntry();
    }*/
    RawDataElementFactory(RawDataEntry key)
    {
        mParentRawDataEntry = key;
    }

    @Override
    protected boolean createKeys(List<String> list)
    {
        list.add("sID: "+mParentRawDataEntry.getStudentID());
        list.add("Pre: "+mParentRawDataEntry.getPreState());
        list.add("Act: "+mParentRawDataEntry.getAction());
        list.add("Pst: "+mParentRawDataEntry.getPostState());
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
