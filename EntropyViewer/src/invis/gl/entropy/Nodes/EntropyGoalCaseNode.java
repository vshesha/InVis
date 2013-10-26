package invis.gl.entropy.Nodes;

import invis.gl.networkapi.NetworkCaseApi;
import invis.gl.networkapi.NetworkCaseSetApi;
import java.util.ArrayList;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 *
 * @author Matt
 */
public class EntropyGoalCaseNode extends AbstractNode
{
    
    NetworkCaseSetApi mNetworkCaseSetData;
    
    public EntropyGoalCaseNode(Children children, ArrayList<NetworkCaseApi> caseList, boolean isGoal, int goalVarCount)
    {
        /* mNetworkCaseSetData = networkCaseSetData;
         NetworkCaseNode node = new NetworkCaseNode(
         Children.LEAF,
         mNetworkCaseSetData);*/
        //super(children, Lookups.singleton(networkCaseSetData));
        super(children);
        if (isGoal)
        {
            this.setDisplayName("Goal Cases: "+goalVarCount);
        } else
        {
            this.setDisplayName("Non-Goal Cases: "+goalVarCount);
        }
    }
}
