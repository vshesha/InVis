package invis.gl.NetworkClusterApi;

import invis.gl.networkapi.NetworkElementApi;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;

/**
 *
 * @author Matt
 */
public interface NetworkClusterElementApi
{

    public Integer getElementCount();

    public void setNetworkElementSet(LinkedList<NetworkElementApi> networkElementSet);

    public LinkedList<NetworkElementApi> getNetworkElementSet();
    
    public String getValue();

    public String getDisplayData();

    public Integer getTotalUniqueFrequency();

    public String getUniqueCaseIdSetAsString();

    public void setSelected(boolean value);

    public boolean getSelected();

    public void AddNewNetworkElement(NetworkElementApi NetworkElement);

    public void addPropertyChangeListener(PropertyChangeListener listener);

    public void removePropertyChangeListener(PropertyChangeListener listener);
}
