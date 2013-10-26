package invis.gl.stepbasednodes;

import invis.gl.sequenceapi.NetworkInteractionSequenceApi;
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
public class StepbasedSequenceNode extends AbstractNode
{

    private NetworkInteractionSequenceApi mNSA;

    public StepbasedSequenceNode(Children children, NetworkInteractionSequenceApi data) //StepbasedSequenceApi
    {
        super(children, Lookups.singleton(data));
        this.setSequenceData(data);
        this.setDisplayName("Stepbased Sequence: " + data.getSequenceName());
    }

    private void setSequenceData(NetworkInteractionSequenceApi data)
    {
        this.mNSA = data;
    }

    public NetworkInteractionSequenceApi getData()
    {
        return (mNSA);
    }

    @Override
    protected Sheet createSheet()
    {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        NetworkInteractionSequenceApi obj = getLookup().lookup(NetworkInteractionSequenceApi.class);

        try
        {
            Node.Property<String> SequenceNameProp = new PropertySupport.Reflection<String>(obj, String.class, "getSequenceName", null);
            Node.Property<Integer> SequenceFrequencyProp = new PropertySupport.Reflection<Integer>(obj, Integer.class, "getFrequency", null);
            Node.Property<Integer> SequenceSizeProp = new PropertySupport.Reflection<Integer>(obj, Integer.class, "getStepbasedSequenceSetSize", null);
            Node.Property<Integer> SequenceRatingProp = new PropertySupport.Reflection<Integer>(obj, Integer.class, "getRating", null);
            Node.Property<Integer> InteractionCountProp = new PropertySupport.Reflection<Integer>(obj, Integer.class, "getInteractionCount", null);
            //Node.Property<Boolean> SelectedProp = new PropertySupport.Reflection<Boolean>(obj, Boolean.class, "getSelected", null);

            SequenceNameProp.setName("Sequence Name");
            SequenceFrequencyProp.setName("Frequency");
            SequenceSizeProp.setName("Size");
            SequenceRatingProp.setName("Rating");
            InteractionCountProp.setName("Interaction Count");
            //SelectedProp.setName("Selected");

            set.put(SequenceNameProp);
            set.put(SequenceFrequencyProp);
            set.put(SequenceSizeProp);
            set.put(SequenceRatingProp);
            set.put(InteractionCountProp);
            //set.put(SelectedProp);

        } catch (NoSuchMethodException ex)
        {
        }
        sheet.put(set);
        return (sheet);
    }
}
