package invis.gl.nodes;

import invis.gl.api.RawInteractionApi;
import org.openide.ErrorManager;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author Matt
 */
public class InteractionsNode extends AbstractNode
{
    private RawInteractionApi mData;
    
    public InteractionsNode(Children children, RawInteractionApi data)
    {
        super(children, Lookups.singleton(data));
        this.setData(data);
        this.setDisplayName(mData.getPreState()+mData.getAction()+mData.getPostState());
    }

    private void setData(RawInteractionApi data)
    {
        this.mData = data;
    }
    
        
    @Override
    protected Sheet createSheet()
    {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        RawInteractionApi obj = getLookup().lookup(RawInteractionApi.class);
        try
        {
            Property<String> preStateProp = new PropertySupport.Reflection<String>(obj, String.class, "getPreState", null);
            Property<String> actionProp = new PropertySupport.Reflection<String>(obj,String.class, "getAction", null);
            Property<String> postStateProp = new PropertySupport.Reflection<String>(obj,String.class, "getPostState", null);
            Property<Integer> fileLineProp = new PropertySupport.Reflection<Integer>(obj, Integer.class, "getFileLineIndex", null);
            Property<Integer> interactionCountProp = new PropertySupport.Reflection<Integer>(obj, Integer.class, "getInteractionCount", null);
                                                                                                                  
            preStateProp.setName("Pre-State");
            actionProp.setName("Action");
            postStateProp.setName("Post-State");
            fileLineProp.setName("Input File Line");
            interactionCountProp.setName("Interaction #");

            set.put(preStateProp);
            set.put(actionProp);
            set.put(postStateProp);
            set.put(fileLineProp);
            set.put(interactionCountProp);

        } catch (NoSuchMethodException ex)
        {
            ErrorManager.getDefault();
        }
        sheet.put(set);
        return (sheet);
    }
}

