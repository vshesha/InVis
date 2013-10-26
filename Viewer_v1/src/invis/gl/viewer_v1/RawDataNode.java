package invis.gl.viewer_v1;

import invis.gl.rawinput.RawDataEntry;
import org.openide.ErrorManager;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;

/**
 *
 * @author Matt
 */
public class RawDataNode extends AbstractNode
{
    private RawDataEntry mRDE;

    /*
    public RawDataNode(Children children)
    {
        super(children);
        mRDE = new RawDataEntry();
        this.setDisplayName("FileName");
    }*/
    public RawDataNode(Children children , RawDataEntry rde)
    {
        super(children);
        mRDE = rde;
        this.setDisplayName("FileName");
    }
/*
    RawDataNode(Children children, Lookup singleton, RawDataEntry key)
    {
        super(children);
        mRDE = key;
        this.setDisplayName("FileName_lookups?");
    }*/
    @Override
    protected Sheet createSheet()
    {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();

        try
        {
            Property<String> Student_ID_Property = new PropertySupport.Reflection<String>(mRDE,String.class,"getStudentID",null);
            Property<String> PreState_Property = new PropertySupport.Reflection<String>(mRDE, String.class, "getPreState",null);
            Property<String> Action_Property = new PropertySupport.Reflection<String>(mRDE, String.class, "getAction",null);
            Property<String> PostState_Property = new PropertySupport.Reflection<String>(mRDE, String.class, "getPostState",null);

            Student_ID_Property.setName("Student ID");
            PreState_Property.setName("PreState");
            Action_Property.setName("Action");
            PostState_Property.setName("PostState");
            
            set.put(Student_ID_Property);
            set.put(PreState_Property);
            set.put(Action_Property);
            set.put(PostState_Property);

        } catch (NoSuchMethodException ex)
        {
            ErrorManager.getDefault();
        }
        sheet.put(set);
        return (sheet);
    }
}
