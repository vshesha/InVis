package invis.gl.stepbasednodes;

import invis.gl.NetworkClusterApi.NetworkClusterVertexApi;
import invis.gl.sequenceapi.NetworkInteractionSequenceSetApi;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author Matt
 */
public class StepbasedSequenceSetNode extends AbstractNode
{

    //private StepbasedSequenceSetApi mSSA;
    private NetworkInteractionSequenceSetApi mSSA;

    //public StepbasedSequenceSetNode(Children children, StepbasedSequenceSetApi data) NetworkInteractionSequenceSetModel
    public StepbasedSequenceSetNode(Children children, NetworkInteractionSequenceSetApi data)
    {
        super(children, Lookups.singleton(data));
        //this.setChildren(Children.create(new StepbasedSequenceNodeFactory(data), true));
        this.setSequenceSetModelData(data);

        if (data.getSequenceSetName() != null)
        {
            this.setDisplayName(data.getSequenceSetName());
        } else
        {
            this.setDisplayName("Top Sequences");
        }
    }

    //private void setSequenceSetModelData(StepbasedSequenceSetApi data)
    private void setSequenceSetModelData(NetworkInteractionSequenceSetApi data)
    {
        this.mSSA = data;
    }

    //public StepbasedSequenceSetApi getData()
    public NetworkInteractionSequenceSetApi getData()
    {
        return (mSSA);
    }

    @Override
    protected Sheet createSheet()
    {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        NetworkInteractionSequenceSetApi obj = getLookup().lookup(NetworkInteractionSequenceSetApi.class);

        try
        {
            
            Node.Property<String> SequenceSetNameProp = new PropertySupport.Reflection<String>(obj, String.class, "getSequenceSetName", null);
            Node.Property<Integer> SequenceSetSizeProp = new PropertySupport.Reflection<Integer>(obj, Integer.class, "getSequenceSetSize", null);


            //Node.Property<Boolean> SelectedProp = new PropertySupport.Reflection<Boolean>(obj, Boolean.class, "getSelected", null);

            SequenceSetNameProp.setName("Sequence Set Name");

            SequenceSetSizeProp.setName("Sequence Set Size");
            //SelectedProp.setName("Selected");

            set.put(SequenceSetNameProp);

            set.put(SequenceSetSizeProp);

            //set.put(SelectedProp);

        } catch (NoSuchMethodException ex)
        {
        }
        sheet.put(set);
        return (sheet);
    }
}