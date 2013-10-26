package invis.gl.stepbasednodes;

import invis.gl.NBNodeFactories.StepBasedFactories.NetworkClusterElementFactory;
import invis.gl.NetworkClusterApi.NetworkClusterElementApi;
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
import org.openide.util.lookup.Lookups;
import org.openide.windows.WindowManager;


/**
 *
 * @author Matt
 */
public class NetworkClusterElementSetNode extends AbstractNode
{
    private List<NetworkClusterElementApi> mData;

    public NetworkClusterElementSetNode(Children children, List<NetworkClusterElementApi> data, String title)
    {
        super(children, Lookups.singleton(data));
        this.setChildren(Children.create(new NetworkClusterElementFactory(data), true));
        this.setDisplayName(title + data.size());
        this.setModel(data);
    }

    private void setModel(List<NetworkClusterElementApi> data)
    {
        mData = data;
    }

    public List<NetworkClusterElementApi> getData()
    {
        return (mData);
    }

    @Override
    public Action[] getActions(boolean popup)
    {
        Action[] actionArray = new Action[4];
        actionArray[0] = new ElementSetSizeSortAction();
        actionArray[1] = new AlphabeticalSortAction();
        actionArray[2] = new TotalUniqueFrequencySortAction();
        actionArray[3] = new ReverseOrderAction();
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
            NetworkClusterElementSetNode node = (WindowManager.getDefault().findTopComponent("StepbasedViewerTopComponent")).getLookup().lookup(NetworkClusterElementSetNode.class);
            List<NetworkClusterElementApi> list = new ArrayList<NetworkClusterElementApi>(node.getData());
            Collections.sort(list, new Comparator<NetworkClusterElementApi>()
            {
                @Override
                public int compare(NetworkClusterElementApi o1, NetworkClusterElementApi o2)
                {

                    return (o1.getValue().compareTo(o2.getValue()));
                }
            });
            node.setModel(list);
            node.setChildren(Children.create(new NetworkClusterElementFactory(list), true));
            StatusDisplayer.getDefault().setStatusText("Alphabetical Sort action Performed.");
        }
    }

    private class ElementSetSizeSortAction extends AbstractAction
    {

        public ElementSetSizeSortAction()
        {
            putValue(NAME, "Element Set Size Sort");
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            NetworkClusterElementSetNode node = (WindowManager.getDefault().findTopComponent("StepbasedViewerTopComponent")).getLookup().lookup(NetworkClusterElementSetNode.class);
            List<NetworkClusterElementApi> list = new ArrayList<NetworkClusterElementApi>(node.getData());
            Collections.sort(list, new Comparator<NetworkClusterElementApi>()
            {
                @Override
                public int compare(NetworkClusterElementApi o1, NetworkClusterElementApi o2)
                {

                    return (o2.getElementCount().compareTo(o1.getElementCount()));
                }
            });
            node.setModel(list);
            node.setChildren(Children.create(new NetworkClusterElementFactory(list), true));
            StatusDisplayer.getDefault().setStatusText("Alphabetical Sort action Performed.");
        }
    }

    private class TotalUniqueFrequencySortAction extends AbstractAction
    {

        public TotalUniqueFrequencySortAction()
        {
            putValue(NAME, "Total Unique Frequency Sort");
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            NetworkClusterElementSetNode node = (WindowManager.getDefault().findTopComponent("StepbasedViewerTopComponent")).getLookup().lookup(NetworkClusterElementSetNode.class);
            List<NetworkClusterElementApi> list = new ArrayList<NetworkClusterElementApi>(node.getData());
            Collections.sort(list, new Comparator<NetworkClusterElementApi>()
            {
                @Override
                public int compare(NetworkClusterElementApi o1, NetworkClusterElementApi o2)
                {

                    return (o2.getTotalUniqueFrequency().compareTo(o1.getTotalUniqueFrequency()));
                }
            });
            node.setModel(list);
            node.setChildren(Children.create(new NetworkClusterElementFactory(list), true));
            StatusDisplayer.getDefault().setStatusText("Alphabetical Sort action Performed.");
        }
    }

    private class ReverseOrderAction extends AbstractAction
    {

        public ReverseOrderAction()
        {
            putValue(NAME, "Reverse Order");
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            NetworkClusterElementSetNode node = (WindowManager.getDefault().findTopComponent("StepbasedViewerTopComponent")).getLookup().lookup(NetworkClusterElementSetNode.class);
            List<NetworkClusterElementApi> list = new ArrayList<NetworkClusterElementApi>(node.getData());
            Collections.reverse(list);
            node.setModel(list);
            node.setChildren(Children.create(new NetworkClusterElementFactory(list), true));
            StatusDisplayer.getDefault().setStatusText("Order Reversed.");
        }
    }
}
