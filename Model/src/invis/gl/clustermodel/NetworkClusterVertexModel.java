package invis.gl.clustermodel;

import invis.gl.NetworkClusterApi.NetworkClusterVertexApi;
import invis.gl.networkapi.NetworkElementApi;
import java.util.Collection;

/**
 *
 * @author Matt
 */
public class NetworkClusterVertexModel extends NetworkClusterElementModel implements NetworkClusterVertexApi
{

    public NetworkClusterVertexModel(String ID, NetworkElementApi node)
    {
        super(ID, node);
    }

    public NetworkClusterVertexModel(String ID, Collection<NetworkElementApi> nodes)
    {
        super(ID, nodes);
    }

    @Override
    public boolean getContainsGoal()
    {
        for (int i = 0; i < mNetworkElements.size(); i++)
        {
            if (mNetworkElements.get(i).getGoalValue())
            {
                return (true);
            }
        }
        return (false);
    }

    @Override
    public boolean getContainsError()
    {
        for (int i = 0; i < mNetworkElements.size(); i++)
        {
            if (mNetworkElements.get(i).getErrorValue())
            {
                return (true);
            }
        }
        return (false);
    }
}
