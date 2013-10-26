package invis.gl.NetworkClusterApi;

/**
 *
 * @author Matt
 */
public interface NetworkClusterEdgeApi extends NetworkClusterElementApi
{
    public NetworkClusterVertexApi getTarget(); //edge

    public NetworkClusterVertexApi getSource(); //edge

    public String getAction();
    
}