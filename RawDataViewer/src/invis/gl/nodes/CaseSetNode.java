
package invis.gl.nodes;

import invis.gl.api.CaseSetModelApi;
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
public class CaseSetNode extends AbstractNode
{
    private CaseSetModelApi mCMA;
    
    public CaseSetNode(Children children, CaseSetModelApi data)
    {
        //private ArrayList<CaseModelApi> mCaseList;
        //private String mCaseSetName;
        super(children, Lookups.singleton(data));
        this.setCaseSetModelData(data);
        this.setDisplayName(data.getCaseSetName());
    }

    private void setCaseSetModelData(CaseSetModelApi data)
    {
        this.mCMA = data;
    }
    
    @Override
    protected Sheet createSheet()
    {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        CaseSetModelApi obj = getLookup().lookup(CaseSetModelApi.class);
        
        try
        {
            Property<String> caseSetProp = new PropertySupport.Reflection<String>
                    (obj, String.class, "getCaseSetName", null);
            Property<String> casesProp = new PropertySupport.Reflection<String>
                    (obj, String.class, "CasesToString", null);
            Property<Integer> caseSetSizeProp = new PropertySupport.Reflection<Integer>
                    (obj, Integer.class, "getCaseSetSize", null);

            caseSetProp.setName("CaseSet Name");
            casesProp.setName("Cases");
            caseSetSizeProp.setName("Case Count");

            set.put(caseSetProp);
            set.put(casesProp);
            set.put(caseSetSizeProp);
            
        } catch (NoSuchMethodException ex)
        {
            ErrorManager.getDefault();
        }
        sheet.put(set);
        return (sheet);
    }
}
