package invis.gl.sequenceapi;

import invis.gl.networkapi.NetworkInteractionApi;
import invis.gl.NetworkClusterApi.NetworkClusterVertexApi;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

/**
 *
 * @author GameLab
 */
public interface NetworkInteractionSequenceApi
{

    public String getSequenceName();
    
    public void setSequenceName(String name);

    public void setFrequency(int newFreq);

    public int getFrequency();

    public void setRating(int newRating);

    public int getRating();

    public boolean contains(NetworkInteractionApi interaction);

    public void setCount(int finalCount);

    public int getCount();

    public int getSequenceRank();

    public ArrayList<NetworkInteractionApi> getInteractionsList();
    
    public ArrayList<NetworkClusterVertexApi> getStepbasedSequenceSet();
    
    public int getStepbasedSequenceSetSize();

    public int getInteractionCount();

    public void addPropertyChangeListener(PropertyChangeListener listener);

    public void removePropertyChangeListener(PropertyChangeListener listener);

    public enum SequenceType
    {

        ACTION, STATE, STEPBASED;
    }
}
