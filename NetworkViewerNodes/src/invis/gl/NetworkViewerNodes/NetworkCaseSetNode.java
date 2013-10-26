package invis.gl.NetworkViewerNodes;

import invis.gl.NBNodeFactories.NetworkFactories.NetworkCaseNodeFactory;
import invis.gl.networkapi.NetworkCaseApi;
import invis.gl.networkapi.NetworkCaseSetApi;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.openide.awt.StatusDisplayer;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.lookup.Lookups;
import org.openide.windows.WindowManager;

/**
 *
 * @author Matt
 */
public class NetworkCaseSetNode extends AbstractNode
{

    private NetworkCaseSetApi mNCSA;

    public NetworkCaseSetNode(Children children, NetworkCaseSetApi data)
    {
        //private ArrayList<CaseModelApi> mCaseList;
        //private String mCaseSetName;
        super(children, Lookups.singleton(data));
        this.setChildren(Children.create(new NetworkCaseNodeFactory(data.getNetworkCaseList()), true));
        this.setCaseSetModelData(data);
        this.setDisplayName(data.getCaseSetName());
    }

    private void setCaseSetModelData(NetworkCaseSetApi data)
    {
        this.mNCSA = data;
    }

    public NetworkCaseSetApi getData()
    {

        mNCSA.getUniqueCaseGroupIdCount();
        return (mNCSA);
    }

    @Override
    public Action[] getActions(boolean popup)
    {
        Action[] actionArray = new Action[3];
        actionArray[0] = new InteractionCountSortAction();
        actionArray[1] = new AlphabeticalSortAction();
        actionArray[2] = new ReverseOrderAction();
        return (actionArray);
    }

    private class AlphabeticalSortAction extends AbstractAction
    {
        public AlphabeticalSortAction()
        {
            putValue(NAME, "Alphabetical Sort");
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            NetworkCaseSetNode node = (WindowManager.getDefault().findTopComponent("NetworkViewerTopComponent")).getLookup().lookup(NetworkCaseSetNode.class);
            List<NetworkCaseApi> networkCaseList = node.getData().getNetworkCaseList();
            Collections.sort(networkCaseList, new Comparator<NetworkCaseApi>()
            {
                @Override
                public int compare(NetworkCaseApi o1, NetworkCaseApi o2)
                {
                    return(o1.getCaseName().compareTo(o2.getCaseName()));
                }
            });

            node.getData().setNetworkCaseList(networkCaseList);
            node.setChildren(Children.create(new NetworkCaseNodeFactory(node.getData().getNetworkCaseList()), true));
            StatusDisplayer.getDefault().setStatusText("Alphabetical Sort action Performed.");
        }
    }

    private class ReverseOrderAction extends AbstractAction
    {

        public ReverseOrderAction()
        {
            //putValue(NAME, "My Action Right Click Action");
            putValue(NAME, "Reverse Order");
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            NetworkCaseSetNode node = (WindowManager.getDefault().findTopComponent("NetworkViewerTopComponent")).getLookup().lookup(NetworkCaseSetNode.class);
            ArrayList<NetworkCaseApi> networkCaseList = node.getData().getNetworkCaseList();
            Collections.reverse(networkCaseList);

            node.getData().setNetworkCaseList(networkCaseList);
            node.setChildren(Children.create(new NetworkCaseNodeFactory(node.getData().getNetworkCaseList()), true));

            StatusDisplayer.getDefault().setStatusText("Order Reversed.");
        }
    }

    private class InteractionCountSortAction extends AbstractAction
    {

        public InteractionCountSortAction()
        {
            putValue(NAME, "Sort by Interaction Count");
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            NetworkCaseSetNode node = (WindowManager.getDefault().findTopComponent("NetworkViewerTopComponent")).getLookup().lookup(NetworkCaseSetNode.class);
            
            List<NetworkCaseApi> networkCaseList = node.getData().getNetworkCaseList();
            Collections.sort(networkCaseList, new Comparator<NetworkCaseApi>()
            {
                @Override
                public int compare(NetworkCaseApi o1, NetworkCaseApi o2)
                {
                    if (o1.getInteractionCount() < o2.getInteractionCount())
                    {
                        return (1);
                    } else if (o1.getInteractionCount() > o2.getInteractionCount())
                    {
                        return (-1);
                    } else
                    {
                        return 0;
                    }
                }
            });


            
            node.getData().setNetworkCaseList(networkCaseList);
            node.setChildren(Children.create(new NetworkCaseNodeFactory(node.getData().getNetworkCaseList()), true));
            //JOptionPane.showMessageDialog(null,"Hello " + mNCSA.getCaseSetName());
            StatusDisplayer.getDefault().setStatusText("Interaction count sort action Performed.");
        }
    }

    @Override
    protected Sheet createSheet()
    {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        NetworkCaseSetApi obj = getLookup().lookup(NetworkCaseSetApi.class);
        try
        {
            Property<String> caseSetProp = new PropertySupport.Reflection<String>(obj, String.class, "getCaseSetName", null);
            Property<String> casesProp = new PropertySupport.Reflection<String>(obj, String.class, "CasesToString", null);
            Property<Integer> caseSetSizeProp = new PropertySupport.Reflection<Integer>(obj, Integer.class, "getNetworkCaseSetSize", null);

            Property<String> uniqueCaseGroupSetProp = new PropertySupport.Reflection<String>(obj, String.class, "getUniqueCaseGroupIdAsString", null);
            Property<Integer> uniqueCaseGroupIdCountProp = new PropertySupport.Reflection<Integer>(obj, Integer.class, "getUniqueCaseGroupIdCount", null);

            //getCaseGroupIdFrequencyMapAsString
            Property<String> caseGroupIdFrequencyMapProp = new PropertySupport.Reflection<String>(obj, String.class, "getCaseGroupIdFrequencyMapAsString", null);

            caseSetProp.setName("CaseSet Name");
            casesProp.setName("Cases");
            caseSetSizeProp.setName("Case Count");
            uniqueCaseGroupSetProp.setName("Unique Case Group-ID List");
            uniqueCaseGroupIdCountProp.setName("Unique Case Group-ID Count");
            caseGroupIdFrequencyMapProp.setName("Case Group-ID Frequency Set");

            set.put(caseSetProp);
            set.put(casesProp);
            set.put(caseSetSizeProp);
            set.put(uniqueCaseGroupSetProp);
            set.put(uniqueCaseGroupIdCountProp);
            set.put(caseGroupIdFrequencyMapProp);

        } catch (NoSuchMethodException ex)
        {
        }
        sheet.put(set);
        return (sheet);
    }
}
