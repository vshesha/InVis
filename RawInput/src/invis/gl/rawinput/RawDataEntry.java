package invis.gl.rawinput;

import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Matt
 */

public class RawDataEntry
{
    private HashMap<String, String> mDataEntry;
    private List listeners = Collections.synchronizedList(new LinkedList());
    
    public void addPropertyChangeListener(PropertyChangeListener pcl)
    {
        listeners.add(pcl);
    }
    public void removePropertyChangeListener(PropertyChangeListener pcl)
    {
        listeners.remove(pcl);
    }
    public RawDataEntry()
    {
        mDataEntry = new HashMap<String, String>();
    }
    
    public RawDataEntry(HashMap<String, String> DataMap)
    {
        mDataEntry = DataMap;
    }    
    public String getStudentID()
    {
        return (mDataEntry.get("STUDENT_ID"));
    }
    public String getPreState()
    {
        return (mDataEntry.get("PRE_STATE"));
    }
    public String getPostState()
    {
        return (mDataEntry.get("POST_STATE"));
    }
    public String getAction()
    {
        return (mDataEntry.get("ACTION"));
    }
    public String getData()
    {
        return (this.getStudentID()+this.getPreState()+this.getAction()+this.getPostState());
    }
}
