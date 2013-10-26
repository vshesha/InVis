package invis.gl.NetworkViewerNodes;

import invis.gl.NBNodeFactories.NetworkFactories.NetworkInteractionsNodeFactory;
import invis.gl.networkapi.NetworkInteractionApi;
import invis.gl.sequenceapi.NetworkInteractionSequenceApi;
import java.util.ArrayList;
import java.util.List;
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
public class NetworkSequenceNode extends AbstractNode
{

    private NetworkInteractionSequenceApi mNSA;

    public NetworkSequenceNode(Children children, NetworkInteractionSequenceApi data)
    {
        super(children, Lookups.singleton(data)); //what the hell does this do?

        this.setSequenceData(data);

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

        //this.setSequenceData(data);
        //The display name is defined in SequenceFactory
        //setDisplayName(mNSA.getInteractionCount() + " Name: " + data.getSequenceName() + " Count: " + data.getClass() + " Rating: " + data.getRating());
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
            //Node.Property<Integer> SequenceSizeProp = new PropertySupport.Reflection<Integer>(obj, Integer.class, "getStepbasedSequenceSetSize", null);
            Node.Property<Integer> SequenceRatingProp = new PropertySupport.Reflection<Integer>(obj, Integer.class, "getRating", null);
            Node.Property<Integer> InteractionCountProp = new PropertySupport.Reflection<Integer>(obj, Integer.class, "getInteractionCount", null);
            //Node.Property<Boolean> SelectedProp = new PropertySupport.Reflection<Boolean>(obj, Boolean.class, "getSelected", null);


            mNSA.getInteractionsList().size();

            SequenceNameProp.setName("Sequence Name");
            SequenceFrequencyProp.setName("Frequency");
            //SequenceSizeProp.setName("Size");
            SequenceRatingProp.setName("Rating");
            InteractionCountProp.setName("Interaction Count");
            //SelectedProp.setName("Selected");

            set.put(SequenceNameProp);
            set.put(SequenceFrequencyProp);
            //set.put(SequenceSizeProp);
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