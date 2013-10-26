package invis.gl.clustermodel;



import invis.gl.NetworkClusterApi.NetworkClusterElementApi;
import invis.gl.networkapi.NetworkElementApi;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

/**
 *
 * @author Matt
 */
public abstract class NetworkClusterElementModel implements NetworkClusterElementApi
{

    protected String mID;
    protected PropertyChangeSupport mPCSupport;
    protected boolean mSelected;
    protected LinkedList<NetworkElementApi> mNetworkElements = new LinkedList<NetworkElementApi>();

    public NetworkClusterElementModel(String ID)
    {
        mID = ID;
        mSelected = false;
        mPCSupport = new PropertyChangeSupport(this);
    }

    public NetworkClusterElementModel(String ID, NetworkElementApi element)
    {
                mID = ID;
        mSelected = false;
        mPCSupport = new PropertyChangeSupport(this);
        mNetworkElements = new LinkedList<NetworkElementApi>();
        mNetworkElements.add(element);
    }

    public NetworkClusterElementModel(String ID, Collection<NetworkElementApi> elements)
    {
                mID = ID;
        mSelected = false;
        mPCSupport = new PropertyChangeSupport(this);
        mNetworkElements = new LinkedList<NetworkElementApi>();
        mNetworkElements.addAll(elements);
    }

    @Override
    public Integer getElementCount()
    {
        return (mNetworkElements.size());
    }

    @Override
    public void setNetworkElementSet(LinkedList<NetworkElementApi> networkElementSet)
    {
        mNetworkElements = networkElementSet;
    }

    @Override
    public LinkedList<NetworkElementApi> getNetworkElementSet()
    {
        return (mNetworkElements);
    }

    @Override
    public String getValue()
    {
        return (mID);
    }

    @Override
    public String getDisplayData()
    {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < mNetworkElements.size(); i++)
        {
            sb.append("Value: ").append(mNetworkElements.get(i).getValue());
            sb.append("   Cases: ").append(mNetworkElements.get(i).getUniqueCaseIdSetAsString());
            sb.append("\n");
        }
        return (sb.toString());
    }

    @Override
    public Integer getTotalUniqueFrequency()
    {
        Integer TotalFrequency = 0;

        Collection<NetworkElementApi> networkNodes = mNetworkElements;
        HashSet<NetworkElementApi> networkNodesHashSet = new HashSet<NetworkElementApi>(networkNodes);
        for (int i = 0; i < networkNodesHashSet.size(); i++)
        {
            TotalFrequency += ((NetworkElementApi) (networkNodesHashSet.toArray()[i])).getUniqueFrequency();
        }

        /*for (int i = 0; i < mNetworkNodes.size(); i++)
         {
         TotalFrequency += mNetworkNodes.get(i).getUniqueFrequency();
         }*/
        return (TotalFrequency);
    }

    @Override
    public String getUniqueCaseIdSetAsString()
    {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < mNetworkElements.size(); i++)
        {
            sb.append("Cases: ").append(mNetworkElements.get(i).getUniqueCaseIdSetAsString());
            sb.append("\n");
        }
        return (sb.toString());
    }

    @Override
    public void setSelected(boolean value)
    {
        for (int i = 0; i < mNetworkElements.size(); i++)
        {
            mNetworkElements.get(i).setSelected(value);
        }
        mPCSupport.firePropertyChange("Selected", mSelected, value);
        mSelected = value;
    }

    @Override
    public boolean getSelected()
    {
        return (mSelected);
    }

    @Override
    public void AddNewNetworkElement(NetworkElementApi NetworkElement)
    {
        mNetworkElements.add(NetworkElement);
    }

    // add interested listeners here
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        mPCSupport.addPropertyChangeListener(listener);
    }

    // don't forget to remove them
    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        mPCSupport.addPropertyChangeListener(listener);
    }
}
