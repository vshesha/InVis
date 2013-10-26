package invis.gl.sequencemodel;

import invis.gl.NetworkClusterApi.NetworkClusterVertexApi;
import invis.gl.networkapi.NetworkInteractionApi;
import invis.gl.sequenceapi.NetworkInteractionSequenceApi;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author GameLab
 */
public class NetworkInteractionSequenceModel implements NetworkInteractionSequenceApi
{

    private int count;
    private String mName;
    private ArrayList<NetworkInteractionApi> interactionList;
    private ArrayList<NetworkClusterVertexApi> mNCVList;
    private PropertyChangeSupport mPCSupport;
    private int rating;
    private int frequency;
    private SequenceType mType;

    /*
     public NetworkInteractionSequenceModel(String initName, int initRank, ArrayList<NetworkInteractionApi> interactions) {
     mName = initName;
     count = initRank;
     interactionList = interactions;
     }
     */
    public NetworkInteractionSequenceModel(ArrayList<NetworkInteractionApi> interactions, SequenceType type)
    {
        //name = "";
        //count = -1;
        //interactionList = interactions;
        //mType = type;

        this(interactions, null, type);
    }

    public NetworkInteractionSequenceModel(ArrayList<NetworkInteractionApi> interactions, ArrayList<NetworkClusterVertexApi> stepbasedList, SequenceType type)
    {
        mName = "";
        count = -1;
        interactionList = interactions;
        mType = type;
        mNCVList = stepbasedList;
    }

    public void setSequenceName(String name)
    {
        mName = name;
    }

    @Override
    public ArrayList<NetworkClusterVertexApi> getStepbasedSequenceSet()
    {

        return (mNCVList);
    }

    @Override
    public int getStepbasedSequenceSetSize()
    {
        return (mNCVList.size());
    }

    @Override
    public void setCount(int finalCount)
    {
        count = finalCount;
    }

    @Override
    public int getCount()
    {
        return count;
    }

    @Override
    public int hashCode()
    {
        if (mType == SequenceType.STATE)
        {
            return (interactionList.hashCode());
        }
        if (mType == SequenceType.STEPBASED && mNCVList != null)
        {
            return (mNCVList.hashCode());
        }
        return (interactionList.hashCode());
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == null)
        {
            return (false);
        }
        if (this.getClass() != other.getClass())
        {
            return (false);
        }
        NetworkInteractionSequenceModel otherSequenceModel = (NetworkInteractionSequenceModel) other;
        if (this.getInteractionsList().size() != otherSequenceModel.getInteractionsList().size())
        {
            return (false);
        } else
        {


            ArrayList<NetworkInteractionApi> SequenceInteractionList = otherSequenceModel.getInteractionsList();

            if (mType == SequenceType.ACTION)
            {
                for (int interactionIndex = 0; interactionIndex < this.getInteractionCount(); interactionIndex++)
                {
                    //Note this is NOT eqal. 
                    if (this.getInteractionsList().get(interactionIndex).getAction().getValue().compareTo(SequenceInteractionList.get(interactionIndex).getAction().getValue()) != 0)
                    {
                        return (false);
                    }
                }
                return (true);
            }

            if (mType == SequenceType.STATE)
            {
                for (int interactionIndex = 0; interactionIndex < this.getInteractionsList().size(); interactionIndex++)
                {
                    //If the pre-states and the post-states are identical.
                    if (this.getInteractionsList().get(interactionIndex).getPreState().getValue().compareTo(SequenceInteractionList.get(interactionIndex).getPreState().getValue()) == 0
                            && this.getInteractionsList().get(interactionIndex).getPostState().getValue().compareTo(SequenceInteractionList.get(interactionIndex).getPostState().getValue()) == 0)
                    {
                        //then we keep the value true.
                    } else //if that ever fails, we return false.
                    {
                        return (false);
                    }
                }
                return (true); //if we haven't returned false, and iterated through the loop, we return true.
            }


            if (mType == SequenceType.STEPBASED)
            {
                //We manage the case for post-conditions for the interaction.
                for (int interactionIndex = 0; interactionIndex < this.getInteractionCount(); interactionIndex++)
                {
                    //Note this is NOT equal. 
                    if (this.getInteractionsList().get(interactionIndex).getActionPostCondition().compareTo(SequenceInteractionList.get(interactionIndex).getActionPostCondition()) != 0)
                    {
                        return (false);
                    }
                }
                return (true);
            }
            try
            {
                throw new Exception("mType of sequence null or compared to non-existant type.");
            } catch (Exception ex)
            {
                Logger.getLogger(NetworkInteractionSequenceModel.class.getName()).log(Level.SEVERE, null, ex);
            }

            return (false); //this is a default case, which means the type was not set or was not recoginized.
        }
        //We should never get here.
        /*if (other != null)
         {
         if (this.hashCode() == other.hashCode())
         {
         return true;
         }
         }
         return false;*/
    }

    @Override
    public void setFrequency(int newFreq)
    {
        frequency = newFreq;
    }

    @Override
    public int getFrequency()
    {
        return frequency;
    }

    @Override
    public String toString()
    {
        double freq = 0;
        for (int i = 0; i < this.getInteractionCount(); i++)
        {
            freq += this.getInteractionsList().get(i).getAction().getUniqueFrequency();
        }
        double avgFreq = freq / (double) this.getInteractionCount();
        return ("Int: " + this.getInteractionCount() + ", Rank: " + rating + ", AvgFreq: " + avgFreq + ", SeqFreq: " + frequency);
    }

    @Override
    public boolean contains(NetworkInteractionApi interaction)
    {
        for (NetworkInteractionApi nia : interactionList)
        {
            if (nia.equals(interaction))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getSequenceName()
    {
        return mName;
    }

    @Override
    public int getSequenceRank()
    {
        return count;
    }

    @Override
    public ArrayList<NetworkInteractionApi> getInteractionsList()
    {
        return interactionList;
    }

    @Override
    public int getInteractionCount()
    {
        return interactionList.size();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        mPCSupport.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        mPCSupport.removePropertyChangeListener(listener);
    }

    @Override
    public int getRating()
    {
        return rating;
    }

    @Override
    public void setRating(int newRating)
    {
        rating = newRating;
    }
}
