package invis.gl.viewer_v1;


import invis.gl.rawinput.RawInputData;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 *
 * @author Matt
 */
public class FileDataNode extends AbstractNode
{

    /*
    public FileDataNode(Children children)
    {
        super(children);
        mRI = new RawInputData();
        this.setDisplayName(mRI.getFileName());
    }*/
    public FileDataNode(Children children, RawInputData rid)
    {
        super(children);
        this.setDisplayName(rid.getFileName());
    }

    /*
    public FileDataNode(Children children, Lookup singleton, RawInputData key)
    {
        super(children);
        this.setDisplayName(key.getFileName()+"3inputs");
    }*/
}
