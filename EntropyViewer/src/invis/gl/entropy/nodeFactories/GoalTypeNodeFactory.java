package invis.gl.entropy.nodeFactories;

import invis.gl.NBNodeFactories.NetworkFactories.NetworkCaseNodeFactory;
import invis.gl.entropy.Nodes.EntropyGoalCaseNode;
import invis.gl.networkapi.NetworkCaseApi;
import java.util.ArrayList;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;

/**
 *
 * @author Matt
 */
public class GoalTypeNodeFactory extends ChildFactory<String>
{

    //private NetworkElementApi mData;
    //private ArrayList<NetworkCaseApi> mInputList = new ArrayList<NetworkCaseApi>();
    private ArrayList<NetworkCaseApi> mGoalCaseList = new ArrayList<NetworkCaseApi>();
    private ArrayList<NetworkCaseApi> mNonGoalCaseList = new ArrayList<NetworkCaseApi>();

    //public GoalTypeNodeFactory(NetworkElementApi key, NetworkCaseSetApi networkCaseSetData)
    public GoalTypeNodeFactory(ArrayList<NetworkCaseApi> caseList)
    {
        //mData = key;
        //mNetworkCaseSetData = networkCaseSetData;

        //mInputList = caseList;

        //for (int i = 0; i < mNetworkCaseSetData.getNetworkCaseList().size(); i++)
        for (int i = 0; i < caseList.size(); i++)
        {
            //if (mNetworkCaseSetData.getNetworkCaseList().get(i).getGoalCase())
            if (caseList.get(i).getGoalCase())
            {
                //Don't add the same student twice...
                //We will keep the list to unique student entries only.
                if (!mGoalCaseList.contains(caseList.get(i)))
                {
                    mGoalCaseList.add(caseList.get(i));
                }
            } else
            {
                //Don't add the same student twice...
                //We will keep the list to unique student entries only.
                if (!mNonGoalCaseList.contains(caseList.get(i)))
                {
                    mNonGoalCaseList.add(caseList.get(i));
                }
            }
        }
    }

    @Override
    protected boolean createKeys(List<String> list)
    {
        list.add("Goal Cases");
        list.add("Non-Goal Cases");

        return true;

    }

    @Override
    public EntropyGoalCaseNode createNodeForKey(String data)
    {
        //for (int i = 0; i < mNetworkCaseSetData.getNetworkCaseList().size(); i++)
        //{
        //if (mNetworkCaseSetData.getNetworkCaseList().get(i).getGoalCase() && data.compareTo("Goal Cases") == 0)

        if (data.compareTo("Goal Cases") == 0)
        {
            //TODO: THIS DOES NOT WORK TO SET THE CASE LIST. IT OVERWRITES.
            //mNetworkCaseSetData.setNetworkCaseList(mGoalCaseList);

            EntropyGoalCaseNode node = new EntropyGoalCaseNode(
                    Children.create(
                    new NetworkCaseNodeFactory(mGoalCaseList), true),
                    mGoalCaseList, true, mGoalCaseList.size());
            //NetworkCaseNode node = new NetworkCaseNode(Children.LEAF, mNetworkCaseSetData.getNetworkCaseList().get(i));
            return (node);
        }

        //if (mNetworkCaseSetData.getNetworkCaseList().get(i).getGoalCase() && data.compareTo("Non-Goal Cases") == 0)
        if (data.compareTo("Non-Goal Cases") == 0)
        {
            //mNetworkCaseSetData.setNetworkCaseList(mNonGoalCaseList);

            EntropyGoalCaseNode node = new EntropyGoalCaseNode(
                    Children.create(
                    new NetworkCaseNodeFactory(mNonGoalCaseList), true),
                    mNonGoalCaseList, false, mNonGoalCaseList.size());
            //NetworkCaseNode node = new NetworkCaseNode(Children.LEAF, mNetworkCaseSetData.getNetworkCaseList().get(i));
            return (node);
        }
        //}
        return null;
    }
}
