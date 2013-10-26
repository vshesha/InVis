package invis.gl.NetworkViewerNodes;

import invis.gl.NBNodeFactories.NetworkFactories.NetworkInteractionsNodeFactory;
import invis.gl.networkapi.NetworkCaseApi;
import invis.gl.networkapi.NetworkInteractionApi;
import java.util.ArrayList;
import java.util.List;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.Exceptions;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author Matt
 */
public class NetworkCaseNode extends AbstractNode
{

    private NetworkCaseApi mCMA;

    public NetworkCaseNode(Children children, NetworkCaseApi data)
    {
        super(children, Lookups.singleton(data));
        NetworkInteractionsNodeFactory NINF = new NetworkInteractionsNodeFactory(data);
        List<NetworkInteractionApi> list = new ArrayList<NetworkInteractionApi>();
        for (int i = 0; i < data.getInteractionsList().size(); i++)
        {
            list.add(data.getInteractionsList().get(i));
        }
        NINF.createKeys(list);
        for (int i = 0; i < list.size(); i++)
        {
            NINF.createNodeForKey(list.get(i));
        }
        this.setCaseData(data);
        this.setDisplayName(data.getCaseName() + " - Int:" + data.getInteractionCount() + " G:" + data.getGoalCase());
    }

    private void setCaseData(NetworkCaseApi data)
    {
        this.mCMA = data;
    }

    public NetworkCaseApi getData()
    {
        return (mCMA);
    }

    @Override
    protected Sheet createSheet()
    {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        NetworkCaseApi obj = getLookup().lookup(NetworkCaseApi.class);

        try
        {
            Property<String> caseProp = new PropertySupport.Reflection<String>(obj, String.class, "getCaseName", null);
            //Property<String> interactionProp = new PropertySupport.Reflection<String>
            //        (obj, String.class, "InteractionsToString", null);
            Property<Integer> interactionCountProp = new PropertySupport.Reflection<Integer>(obj, Integer.class, "getInteractionCount", null);

            Property<String> caseGroupIdProp = new PropertySupport.Reflection<String>(obj, String.class, "getCaseGroupID", null);

            Property<Boolean> goalCaseProp = new PropertySupport.Reflection<Boolean>(obj, Boolean.class, "getGoalCase", null);

            caseProp.setName("Case Name");
            //interactionProp.setName("Interactions");
            interactionCountProp.setName("Interaction Count");
            caseGroupIdProp.setName("Group ID");
            goalCaseProp.setName("Goal Case");

            set.put(caseProp);
            //set.put(interactionProp);
            set.put(interactionCountProp);
            set.put(caseGroupIdProp);
            set.put(goalCaseProp);

        } catch (NoSuchMethodException ex)
        {
            Exceptions.attachMessage(ex, "Property values of NetworkCaseNode contains errors.");
        }
        sheet.put(set);
        return (sheet);
    }
}
