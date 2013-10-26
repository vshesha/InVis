
package invis.gl.clustermodel;

import invis.gl.networkapi.NetworkElementApi;
import java.util.Collection;

/**
 *
 * @author Matt
 */
public class NetworkClusterEdgeMixedModel extends NetworkClusterElementModel 
{

    public NetworkClusterEdgeMixedModel(String id, Collection<NetworkElementApi> elements)
    {
        super(id, elements);
    }

    public String getActionSet()
    {
        StringBuilder ActionSet = new StringBuilder();
        for (NetworkElementApi element : mNetworkElements)
        {
            ActionSet.append(element.getSimpleValue()).append(" ");
        }
        return (mNetworkElements.getFirst().getSimpleValue());
    }
}
