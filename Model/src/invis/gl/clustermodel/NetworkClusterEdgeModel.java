package invis.gl.clustermodel;

import invis.gl.NetworkClusterApi.NetworkClusterEdgeApi;
import invis.gl.NetworkClusterApi.NetworkClusterVertexApi;
import invis.gl.networkapi.NetworkElementApi;



/**
 *
 * @author Matt
 */
public class NetworkClusterEdgeModel extends NetworkClusterElementModel implements NetworkClusterEdgeApi
{

    private NetworkClusterVertexApi mSource;
    private NetworkClusterVertexApi mTarget;

    public NetworkClusterEdgeModel(NetworkElementApi edge, NetworkClusterVertexApi source, NetworkClusterVertexApi target, String ID)
    {
        super(ID, edge);
        mSource = source;
        mTarget = target;
    }

    @Override
    public String getAction()
    {
        return (mNetworkElements.getFirst().getSimpleValue());
    }

    @Override
    public NetworkClusterVertexApi getSource()
    {
        return (mSource);
    }

    @Override
    public NetworkClusterVertexApi getTarget()
    {
        return (mTarget);
    }
}
