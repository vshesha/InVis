package invis.gl.NetworkViewerNodes;

import invis.gl.NBNodeFactories.NetworkFactories.NetworkSequenceNodeFactory;
import invis.gl.sequenceapi.NetworkInteractionSequenceSetApi;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
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
public class NetworkSequenceSetNode extends AbstractNode
{

    private NetworkInteractionSequenceSetApi mNISSA;

    public NetworkSequenceSetNode(Children children, NetworkInteractionSequenceSetApi data)
    {
        super(children, Lookups.singleton(data));
        this.setChildren(Children.create(new NetworkSequenceNodeFactory(data), true));
        this.setSequenceSetModelData(data);

        if (data.getSequenceSetName() != null)
        {
            this.setDisplayName(data.getSequenceSetName() + " Sz: " + data.getSequenceSetSize());
        } else
        {
            this.setDisplayName("Top Sequences");
        }
    }

    private void setSequenceSetModelData(NetworkInteractionSequenceSetApi data)
    {
        this.mNISSA = data;
    }

    public NetworkInteractionSequenceSetApi getData()
    {
        return (mNISSA);
    }

    @Override
    protected Sheet createSheet()
    {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        NetworkInteractionSequenceSetApi obj = getLookup().lookup(NetworkInteractionSequenceSetApi.class);

        try
        {
            mNISSA.getSequenceSetName();
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

    @Override
    public Action[] getActions(boolean popup)
    {
        return (new Action[]
                {
                    new MyAction(), new FrequencySortAction()
                });
    }

    private class MyAction extends AbstractAction
    {

        public MyAction()
        {
            putValue(NAME, "Sort By Interaction");
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            JOptionPane.showMessageDialog(null, "Interaction Sort " + mNISSA.getSequenceSetName());
        }
    }

    public class FrequencySortAction extends AbstractAction
    {

        private FrequencySortAction()
        {
            putValue(NAME, "Sort By Interaction");
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            /*mDataParser = Lookup.getDefault().lookup(DataParser.class);
             ExplorerManager mgr = mDataParser.getNetworkVVLookup().getNVVLookup().lookup(ExplorerManager.class);
        
             NetworkSequenceSetNode x = (NetworkSequenceSetNode) mgr.getRootContext();
             ArrayList<NetworkInteractionSequenceApi> networkSequenceSet = x.getData().getNetworkSequenceSet();*/

            /*SequenceViewTopComponent svtc = (SequenceViewTopComponent) WindowManager.getDefault().findTopComponent("SequenceViewTopComponent");
            if (svtc != null && svtc.isOpened())
            {
                svtc.RefreshStepbasedSequenceContents(NetworkInteractionSequenceBuilder.SortModelFrequency(networkSequenceSet));
            }*/

            //JOptionPane.showMessageDialog(null, "Interaction Sort " + mNISSA.getSequenceSetName());
        }
    }
}