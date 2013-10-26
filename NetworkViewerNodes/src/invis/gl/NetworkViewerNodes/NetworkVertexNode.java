package invis.gl.NetworkViewerNodes;

import invis.gl.NetworkViewerNodes.CustomPropertyEditors.MultiValueMapPropertyEditor;
import invis.gl.networkapi.NetworkCaseSetApi;
import invis.gl.networkapi.NetworkElementApi;
import invis.gl.networkapi.NetworkElementApi.NetworkElementType;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import org.apache.commons.collections.map.MultiValueMap;
import org.openide.nodes.*;
import org.openide.util.lookup.Lookups;
import org.openide.windows.WindowManager;

/**
 *
 * @author Matt
 */
public class NetworkVertexNode extends AbstractNode implements PropertyChangeListener
{

    private NetworkElementApi mNEA;
    private NetworkCaseSetApi mNCSA;

    public NetworkVertexNode(Children children, NetworkElementApi data)
    {
        super(children, Lookups.singleton(data));
        this.setVertexModelData(data);
        this.setDisplayName(data.getValue());
        this.AddModelListeners();
    }

    public NetworkVertexNode(Children children, NetworkElementApi data, NetworkCaseSetApi caseData)
    {
        super(children, Lookups.singleton(data));
        this.setVertexModelData(data);
        this.setCaseModelData(caseData);
        this.setDisplayName(data.getValue());
        this.AddModelListeners();

    }

    public NetworkElementApi getData()
    {
        return (mNEA);
    }
    
    public NetworkCaseSetApi getCaseData()
    {
        return (mNCSA);
    }

    private void AddModelListeners()
    {
        mNEA.addPropertyChangeListener(this);
    }

    private void setVertexModelData(NetworkElementApi data)
    {
        this.mNEA = data;
    }

    private void setCaseModelData(NetworkCaseSetApi data)
    {
        this.mNCSA = data;
    }

    @Override
    protected Sheet createSheet()
    {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        NetworkElementApi obj = getLookup().lookup(NetworkElementApi.class);
        try
        {
            Property<String> valueProp = new PropertySupport.Reflection<String>(obj, String.class, "getValue", null);
            Property<String> caseIdListProp = new PropertySupport.Reflection<String>(obj, String.class, "getCaseIdListAsString", null);
            Property<NetworkElementType> elementTypeProp = new PropertySupport.Reflection<NetworkElementType>(obj, NetworkElementType.class, "getNetworkElementType", null);
            Property<Integer> caseIdListSizeProp = new PropertySupport.Reflection<Integer>(obj, Integer.class, "getCaseFrequency", null);

            Property<Double> MDPValueProp = new PropertySupport.Reflection<Double>(obj, Double.class, "getMDPValue", null);

            Property<Integer> ClusterIDProp = new PropertySupport.Reflection<Integer>(obj, int.class, "getClusterID", null);
            Property<Integer> uniqueCaseSizeProp = new PropertySupport.Reflection<Integer>(obj, Integer.class, "getUniqueFrequency", null);
            Property<String> uniqueCaseIdListProp = new PropertySupport.Reflection<String>(obj, String.class, "getUniqueCaseIdSetAsString", null);

            //Optional properties:
            Property<Boolean> errorProp = new PropertySupport.Reflection<Boolean>(obj, Boolean.class, "getErrorValue", null);
            Property<Boolean> goalProp = new PropertySupport.Reflection<Boolean>(obj, Boolean.class, "getGoalValue", null);
            Property<String> preConditionProp = new PropertySupport.Reflection<String>(obj, String.class, "getPreConditionStringList", null);
            //Property<String> postConditionProp = new PropertySupport.Reflection<String>(obj, String.class, "getPostConditionStringList", null);
            
            PropertySupport.Reflection<MultiValueMap> postConditionProp = new PropertySupport.Reflection<MultiValueMap>(obj, MultiValueMap.class, "getPostCondition", null);
            postConditionProp.setPropertyEditorClass(MultiValueMapPropertyEditor.class);

            Property<Boolean> goalPathValueProp = new PropertySupport.Reflection<Boolean>(obj, Boolean.class, "getGoalPathValue", null);
            Property<Integer> goalCaseCountProp = new PropertySupport.Reflection<Integer>(obj, Integer.class, "getGoalCaseCount", null);

            valueProp.setName("Value");
            caseIdListProp.setName("Case IDs");
            elementTypeProp.setName("Type");
            caseIdListSizeProp.setName("Case Count");
            uniqueCaseSizeProp.setName("Unique Case Count");
            uniqueCaseIdListProp.setName("Unique Case IDs");

            MDPValueProp.setName("MDP Value");
            ClusterIDProp.setName("ClusterID");

            //TODO: it would be nice to have the list of the actions for the
            //currently selected node...

            //Optional properties:
            errorProp.setName("Error");
            goalProp.setName("Goal");
            preConditionProp.setName("Pre Condition");
            postConditionProp.setName("Post Condition");
            goalPathValueProp.setName("Goal Path");
            goalCaseCountProp.setName("Goal Cases Count");


            set.put(valueProp);
            set.put(caseIdListProp);
            set.put(elementTypeProp);
            set.put(caseIdListSizeProp);
            set.put(uniqueCaseSizeProp);
            set.put(uniqueCaseIdListProp);
            set.put(MDPValueProp);
            set.put(ClusterIDProp);

            if (mNEA.getOptionalData().contains("ERROR"))
            {
                set.put(errorProp);
            }
            if (mNEA.getOptionalData().contains("GOAL"))
            {
                set.put(goalProp);
                set.put(goalPathValueProp);
                set.put(goalCaseCountProp);
            }
            if (mNEA.getOptionalData().contains("PRE_CONDITION"))
            {
                set.put(preConditionProp);
            }
            if (mNEA.getOptionalData().contains("POST_CONDITION"))
            {
                set.put(postConditionProp);
            }
        } catch (NoSuchMethodException ex)
        {
        }
        sheet.put(set);
        return (sheet);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        if ("Selected".equals(evt.getPropertyName()))
        {
            if (((NetworkElementApi) evt.getSource()).getSelected())
            {
                ArrayList<Node> activeNodes = new ArrayList<Node>();
                activeNodes.add(this);

                Node[] activatedNodes = activeNodes.toArray(new Node[activeNodes.size()]);
                WindowManager.getDefault().findTopComponent("NetworkViewerTopComponent").setActivatedNodes(activatedNodes);
                WindowManager.getDefault().findTopComponent("SequenceViewTopComponent").setActivatedNodes(activatedNodes);
                WindowManager.getDefault().findTopComponent("EntropyViewerTopComponent").setActivatedNodes(activatedNodes);
            }
        }
    }
}
