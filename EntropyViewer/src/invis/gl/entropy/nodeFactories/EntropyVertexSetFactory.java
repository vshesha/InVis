package invis.gl.entropy.nodeFactories;

import invis.gl.NetworkViewerNodes.NetworkVertexNode;
import invis.gl.networkapi.NetworkCaseSetApi;
import invis.gl.networkapi.NetworkElementApi;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
/**
 *
 * @author Matt
 */
public class EntropyVertexSetFactory extends ChildFactory<NetworkElementApi>
{

    private List<NetworkElementApi> mVertexSetData;
    private NetworkCaseSetApi mNetworkCaseSetData;

    public EntropyVertexSetFactory(List<NetworkElementApi> vertexSet, NetworkCaseSetApi networkCaseSet)
    {
        mVertexSetData = vertexSet;
        mNetworkCaseSetData = networkCaseSet;
    }

    @Override
    protected boolean createKeys(List<NetworkElementApi> list)
    {
        for (int i = 0; i < mVertexSetData.size(); i++)
        {
            list.add(mVertexSetData.get(i));
        }
        return (true);
    }

    @Override
    public NetworkVertexNode createNodeForKey(NetworkElementApi key)
    {
        key.getCaseIdList();
        
        NetworkVertexNode node = new NetworkVertexNode(
                Children.create(
                new GoalTypeNodeFactory(key.getCaseList()),
                true),
                key, 
                mNetworkCaseSetData);
        int FailedCount = key.getUniqueFrequency() - key.getGoalCaseCount();
        node.setDisplayName(key.getGoalCaseCount()+" / "+key.getUniqueFrequency()+" "+"FC: "+FailedCount+" "+key.getValue());
        return (node);
    }
    
    
}
