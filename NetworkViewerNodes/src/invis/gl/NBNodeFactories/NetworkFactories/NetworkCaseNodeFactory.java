package invis.gl.NBNodeFactories.NetworkFactories;

import invis.gl.NetworkViewerNodes.NetworkCaseNode;
import invis.gl.networkapi.NetworkCaseApi;
import invis.gl.networkapi.NetworkCaseSetApi;
import java.util.ArrayList;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;

/**
 *
 * @author Matt
 */
public class NetworkCaseNodeFactory extends ChildFactory<NetworkCaseApi>
{

    private NetworkCaseSetApi mNCS;
    
    private ArrayList<NetworkCaseApi> mData;

    /*public NetworkCaseNodeFactory(NetworkCaseSetApi key)
    {
        mNCS = key;
    }*/
    
    public NetworkCaseNodeFactory(ArrayList<NetworkCaseApi> data)
    {
        mData = data;
    }
    
    @Override
    public boolean createKeys(List<NetworkCaseApi> list)
    {
        /*for (int networkCaseIdx = 0; networkCaseIdx < mNCS.getNetworkCaseSetSize(); networkCaseIdx++)
        {
            list.add(mNCS.getNetworkCaseList().get(networkCaseIdx));
        }*/
        for (int i = 0; i < mData.size();i++)
        {
            list.add(mData.get(i));
        }
        return (true);
    }

    @Override
    public  NetworkCaseNode createNodeForKey(NetworkCaseApi key)
    {
        NetworkCaseNode node = new NetworkCaseNode(
                Children.create(
                new NetworkInteractionsNodeFactory(key),
                true),
                key);
        return (node);
    }
}
