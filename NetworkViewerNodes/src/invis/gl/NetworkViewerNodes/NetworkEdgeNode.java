package invis.gl.NetworkViewerNodes;

import invis.gl.NetworkViewerNodes.CustomPropertyEditors.MultiValueMapPropertyEditor;
import invis.gl.networkapi.NetworkElementApi;
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
public class NetworkEdgeNode extends AbstractNode implements PropertyChangeListener
{

    private NetworkElementApi mNEA;

    public NetworkEdgeNode(Children children, NetworkElementApi data)
    {
        super(children, Lookups.singleton(data));
        this.setCaseModelData(data);
        this.setDisplayName(data.getSimpleValue());
        //data.getValue();
        this.AddModelListeners();
    }

    public NetworkElementApi getData()
    {
        return (mNEA);
    }

    private void AddModelListeners()
    {
        mNEA.addPropertyChangeListener(this);
    }

    private void setCaseModelData(NetworkElementApi data)
    {
        this.mNEA = data;
    }

   

    @Override
    protected Sheet createSheet()
    {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        NetworkElementApi obj = getLookup().lookup(NetworkElementApi.class);
        try
        {
            Property<String> valueProp = new PropertySupport.Reflection<String>(obj, String.class, "getSimpleValue", null);
            Property<Integer> caseIdListSizeProp = new PropertySupport.Reflection<Integer>(obj, Integer.class, "getCaseFrequency", null);
            Property<String> caseIdListProp = new PropertySupport.Reflection<String>(obj, String.class, "getCaseIdListAsString", null);
            Property<NetworkElementApi.NetworkElementType> elementTypeProp = new PropertySupport.Reflection<NetworkElementApi.NetworkElementType>(obj, NetworkElementApi.NetworkElementType.class, "getNetworkElementType", null);

            Property<Integer> uniqueCaseSizeProp = new PropertySupport.Reflection<Integer>(obj, Integer.class, "getUniqueFrequency", null);
            Property<String> uniqueCaseIdListProp = new PropertySupport.Reflection<String>(obj, String.class, "getUniqueCaseIdSetAsString", null);

            Property<Double> MDPValueProp = new PropertySupport.Reflection<Double>(obj, Double.class, "getMDPValue", null);

            //Optional properties:
            Property<Boolean> errorProp = new PropertySupport.Reflection<Boolean>(obj, Boolean.class, "getErrorValue", null);
            Property<Boolean> goalProp = new PropertySupport.Reflection<Boolean>(obj, Boolean.class, "getGoalValue", null);
            Property<String> preConditionProp = new PropertySupport.Reflection<String>(obj, String.class, "getPreConditionStringList", null);
            //Property<String> postConditionProp = new PropertySupport.Reflection<String>(obj, String.class, "getPostConditionStringList", null);

            PropertySupport.Reflection<MultiValueMap> postConditionProp = new PropertySupport.Reflection<MultiValueMap>(obj, MultiValueMap.class, "getPostCondition", null);
            postConditionProp.setPropertyEditorClass(MultiValueMapPropertyEditor.class);

            valueProp.setName("Value");
            caseIdListProp.setName("Case IDs");
            elementTypeProp.setName("Type");
            caseIdListSizeProp.setName("Case Count");
            uniqueCaseSizeProp.setName("Unique Case Count");
            uniqueCaseIdListProp.setName("Unique Case IDs");

            MDPValueProp.setName("MDP Value");
            //Optional properties:
            errorProp.setName("Error");
            goalProp.setName("Goal");
            preConditionProp.setName("Pre Condition");
            postConditionProp.setName("Post Condition");
            //postConditionProp.setName("PostConditions Map");

            set.put(valueProp);
            set.put(caseIdListProp);
            set.put(elementTypeProp);
            set.put(caseIdListSizeProp);
            set.put(uniqueCaseSizeProp);
            set.put(uniqueCaseIdListProp);

            set.put(MDPValueProp);

            if (mNEA.getOptionalData().contains("ERROR"))
            {
                set.put(errorProp);
            }
            if (mNEA.getOptionalData().contains("GOAL"))
            {
                set.put(goalProp);
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

            //if ((Boolean)evt.getNewValue() == true)    
            {
                ArrayList<Node> activeNodes = new ArrayList<Node>();
                activeNodes.add(this);

                /*WindowManager.getDefault().findTopComponent("NetworkViewerTopComponent").setActivatedNodes(
                        activeNodes.toArray(new Node[activeNodes.size()]));
                WindowManager.getDefault().findTopComponent("SequenceViewTopComponent").setActivatedNodes(
                        activeNodes.toArray(new Node[activeNodes.size()]));*/
                
                
                Node[] activatedNodes = activeNodes.toArray(new Node[activeNodes.size()]);
                WindowManager.getDefault().findTopComponent("NetworkViewerTopComponent").setActivatedNodes(activatedNodes);
                WindowManager.getDefault().findTopComponent("SequenceViewTopComponent").setActivatedNodes(activatedNodes);
                //WindowManager.getDefault().findTopComponent("EntropyViewerTopComponent").setActivatedNodes(activatedNodes);
            }
        }
    }
}
