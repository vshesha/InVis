package invis.gl.entropy.Nodes;

import invis.gl.NetworkClusterApi.NetworkClusterVertexApi;
import invis.gl.entropy.nodeFactories.EntropyVertexSetFactory;
import invis.gl.networkapi.NetworkCaseSetApi;
import invis.gl.networkapi.NetworkElementApi;
import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.openide.awt.StatusDisplayer;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.windows.WindowManager;

/**
 *
 * @author Matt
 */
public class EntropyVertexSetNode extends AbstractNode
{

    NetworkClusterVertexApi mClusterVertexData;
    NetworkCaseSetApi mCaseSetData;

    public EntropyVertexSetNode(NetworkClusterVertexApi x, NetworkCaseSetApi y)
    {
        super(Children.create(new EntropyVertexSetFactory(
                x.getNetworkElementSet(),
                y), true));

        mClusterVertexData = x;
        mCaseSetData = y;
    }

    public NetworkClusterVertexApi getClusterVertexData()
    {
        return (mClusterVertexData);
    }

    public NetworkCaseSetApi getCaseSetData()
    {
        return (mCaseSetData);
    }

    @Override
    public Action[] getActions(boolean popup)
    {
        Action[] actionArray = new Action[2];
        actionArray[0] = new GoalCaseCountSortAction();
        actionArray[1] = new StudentDifficultyAction();
        //actionArray[2] = new ReverseOrderAction();
        return (actionArray);
    }

    private class StudentDifficultyAction extends AbstractAction
    {

        public StudentDifficultyAction()
        {
            putValue(NAME, "Sort by Student Difficulty");
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            EntropyVertexSetNode node = (WindowManager.getDefault().findTopComponent("EntropyViewerTopComponent")).getLookup().lookup(EntropyVertexSetNode.class);
            LinkedList<NetworkElementApi> networkElementSet = mClusterVertexData.getNetworkElementSet();
            Collections.sort(networkElementSet, new Comparator<NetworkElementApi>()
            {
                @Override
                public int compare(NetworkElementApi o1, NetworkElementApi o2)
                {
                    Integer failCount1 = (o1.getUniqueFrequency() - o1.getGoalCaseCount());
                    Integer failCount2 = (o2.getUniqueFrequency() - o2.getGoalCaseCount());

                    int order = failCount2.compareTo(failCount1);
                    //if the fail counts are equal, sort on overall frequency.
                    if (order == 0)
                    {
                        order = o2.getUniqueFrequency().compareTo(o1.getUniqueFrequency());
                    }
                    return (order);
                }
            });

            node.getClusterVertexData().setNetworkElementSet(networkElementSet);
            node.setChildren(Children.create(new EntropyVertexSetFactory(
                    networkElementSet,
                    mCaseSetData), true));
            StatusDisplayer.getDefault().setStatusText("Student difficulty sort action Performed.");
        }
    }

    private class GoalCaseCountSortAction extends AbstractAction
    {

        public GoalCaseCountSortAction()
        {
            putValue(NAME, "Sort by Goal Case Count");
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            EntropyVertexSetNode node = (WindowManager.getDefault().findTopComponent("EntropyViewerTopComponent")).getLookup().lookup(EntropyVertexSetNode.class);
            LinkedList<NetworkElementApi> networkElementSet = mClusterVertexData.getNetworkElementSet();
            Collections.sort(networkElementSet, new Comparator<NetworkElementApi>()
            {
                @Override
                public int compare(NetworkElementApi o1, NetworkElementApi o2)
                {
                    Integer gc1 = o1.getGoalCaseCount();
                    Integer gc2 = o2.getGoalCaseCount();
                    int order = gc2.compareTo(gc1);
                    if (order == 0)
                    {
                        order = o2.getUniqueFrequency().compareTo(o1.getUniqueFrequency());
                    }
                    return (order);
                }
            });

            node.getClusterVertexData().setNetworkElementSet(networkElementSet);
            node.setChildren(Children.create(new EntropyVertexSetFactory(
                    networkElementSet,
                    mCaseSetData), true));
            StatusDisplayer.getDefault().setStatusText("Goal case count sort action Performed.");
        }
    }
}
