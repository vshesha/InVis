package invis.gl.nodes;

import invis.gl.api.RawCaseModelApi;
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
public class CaseNode extends AbstractNode
{
    private RawCaseModelApi mCMA;
    
    public CaseNode(Children children, RawCaseModelApi data)
    {
        super(children, Lookups.singleton(data));
        this.setCaseModelData(data);
        this.setDisplayName(data.getCaseName());
    }
    private void setCaseModelData(RawCaseModelApi data)
    {
        this.mCMA = data;
    }
    
    @Override
    protected Sheet createSheet()
    {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        RawCaseModelApi obj = getLookup().lookup(RawCaseModelApi.class);
        try
        {
            Property<String> caseProp = new PropertySupport.Reflection<String>
                    (obj, String.class, "getCaseName", null);
            //Property<String> interactionProp = new PropertySupport.Reflection<String>(obj,String.class, "InteractionsToString", null);
            Property<Integer> interactionCountProp = new PropertySupport.Reflection<Integer>
                    (obj,Integer.class, "getInteractionCount", null);

            caseProp.setName("Case Name");
            //interactionProp.setName("Interactions");
            interactionCountProp.setName("Interaction Count");

            set.put(caseProp);
            //set.put(interactionProp);
            set.put(interactionCountProp);

        } catch (NoSuchMethodException ex)
        {
            ErrorManager.getDefault();
        }
        sheet.put(set);
        return (sheet);
    }
}
