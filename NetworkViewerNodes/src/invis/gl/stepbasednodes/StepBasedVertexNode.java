package invis.gl.stepbasednodes;

import invis.gl.NetworkClusterApi.NetworkClusterElementApi;
import invis.gl.NetworkClusterApi.NetworkClusterVertexApi;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.lookup.Lookups;
import org.openide.windows.WindowManager;

/**
 *
 * @author Matt
 */
public class StepBasedVertexNode extends AbstractNode implements PropertyChangeListener
{

    private NetworkClusterVertexApi mNCVA;

    public StepBasedVertexNode(Children children, NetworkClusterVertexApi data)
    {
        super(children, Lookups.singleton(data));
        //this.setChildren(Children.create(new NetworkVertexNodeFactory(data), true));
        this.setModel(data);
        String dataValue = data.getValue();
        this.setDisplayName(dataValue + " States: " + data.getNetworkElementSet().size() + " Freq: " + data.getTotalUniqueFrequency());
        this.AddModelListeners();
    }

    /*public StepBasedVertexNode(NetworkClusterVertexApi data)
    {
        this(Children.create(new NetworkVertexNodeFactory(data), true), data);
    }*/

    public NetworkClusterVertexApi getData()
    {
        return (mNCVA);
    }

    private void AddModelListeners()
    {
        mNCVA.addPropertyChangeListener(this);
    }

    private void setModel(NetworkClusterVertexApi data)
    {
        this.mNCVA = data;
    }

    @Override
    protected Sheet createSheet()
    {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
       NetworkClusterElementApi obj = getLookup().lookup(NetworkClusterElementApi.class);
        try
        {
            Node.Property<String> valueProp = new PropertySupport.Reflection<String>(obj, String.class, "getValue", null);
            //Node.Property<String> elementTypeProp = new PropertySupport.Reflection<String>(obj, String.class, "getNetworkType", null);
            Node.Property<String> displayProp = new PropertySupport.Reflection<String>(obj, String.class, "getDisplayData", null);
            Node.Property<Integer> uniqueCaseSizeProp = new PropertySupport.Reflection<Integer>(obj, Integer.class, "getTotalUniqueFrequency", null);
            Node.Property<String> uniqueCaseIdListProp = new PropertySupport.Reflection<String>(obj, String.class, "getUniqueCaseIdSetAsString", null);
            Node.Property<Integer> elementSizeProp = new PropertySupport.Reflection<Integer>(obj, Integer.class, "getElementCount", null);
            Node.Property<Boolean> SelectedProp = new PropertySupport.Reflection<Boolean>(obj, Boolean.class, "getSelected", null);

            //Optional properties:
            //Node.Property<Boolean> errorProp = new PropertySupport.Reflection<Boolean>(obj, Boolean.class, "getErrorValue", null);
            //Node.Property<Boolean> goalProp = new PropertySupport.Reflection<Boolean>(obj, Boolean.class, "getGoalValue", null);
            //Node.Property<HashMap> preConditionProp = new PropertySupport.Reflection<HashMap>(obj, HashMap.class, "getPreCondition", null);
            //Node.Property<HashMap> postConditionProp = new PropertySupport.Reflection<HashMap>(obj, HashMap.class, "getPostCondition", null);

            valueProp.setName("Value");
            displayProp.setName("Nodes & Cases");
            //elementTypeProp.setName("Type");
            uniqueCaseSizeProp.setName("Sum of Unique Cases");
            uniqueCaseIdListProp.setName("Unique Cases");
            elementSizeProp.setName("Element Count");
            SelectedProp.setName("Selected");

            

            //TODO: it would be nice to have the list of the actions for the
            //currently selected node...

            //Optional properties:
      

            set.put(valueProp);
            //set.put(elementTypeProp);
            set.put(displayProp);
            set.put(elementSizeProp);
            set.put(uniqueCaseSizeProp);
            set.put(uniqueCaseIdListProp);
            set.put(SelectedProp);



            /*
             if (mNCVA.getOptionalData().contains("ERROR"))
             {
             set.put(errorProp);
             }
             if (mNCVA.getOptionalData().contains("GOAL"))
             {
             set.put(goalProp);
             }
             if (mNCVA.getOptionalData().contains("PRE_CONDITION"))
             {
             set.put(preConditionProp);
             }
             if (mNCVA.getOptionalData().contains("POST_CONDITION"))
             {
             set.put(postConditionProp);
             }*/
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
            if (((NetworkClusterVertexApi) evt.getSource()).getSelected())
            {
                ArrayList<Node> activeNodes = new ArrayList<Node>();
                activeNodes.add(this);

                Node[] activatedNodes = activeNodes.toArray(new Node[activeNodes.size()]);
                //WindowManager.getDefault().findTopComponent("NetworkViewerTopComponent").setActivatedNodes(activatedNodes);
                WindowManager.getDefault().findTopComponent("StepbasedViewerTopComponent").setActivatedNodes(activatedNodes);
                WindowManager.getDefault().findTopComponent("SequenceViewTopComponent").setActivatedNodes(activatedNodes);
            }
        }
    }
}
