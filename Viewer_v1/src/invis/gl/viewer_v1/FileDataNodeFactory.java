package invis.gl.viewer_v1;

import invis.gl.rawinput.RawInputData;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 *
 * @author Matt
 */
public class FileDataNodeFactory extends ChildFactory<RawInputData> 
{
    private List<RawInputData> mResultList;
    
    public FileDataNodeFactory(List<RawInputData> list)
    {
        mResultList = list;
    }

    @Override
    protected boolean createKeys(List<RawInputData> list)
    {
        for (RawInputData rid : mResultList)
        {
            list.add(rid);
        }
        return (true);
    }
    @Override
    protected Node createNodeForKey(RawInputData ri)
    {
        FileDataNode node = new FileDataNode(Children.create(new RawDataNodeFactory(ri.getRawDataEntryList()), true), ri);
        node.setDisplayName(ri.getFileName());
        return (node);
    }
}
