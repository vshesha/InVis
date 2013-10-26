package NetworkModels;

import invis.gl.networkapi.NetworkElementApi;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.util.lookup.ServiceProvider;

/**
 * TODO: Goal Paths for edges Goal Case Count for edges
 *
 * @author Matt
 */
@ServiceProvider(service = NetworkEdgeModel.class)
public class NetworkEdgeModel extends NetworkElementModel
{

    private String mAction;
    private NetworkElementApi mPreState;
    private NetworkElementApi mPostState;

    public NetworkEdgeModel()
    {
        super(null, null, null, null);
    }

    /**
     * The constructor for the NetworkEdgeModel. This is the model of the object
     * that is displayed as an edge in the NetworkDisplay window.
     *
     * @param interactionIndex - this corresponds to the InteractionCount in the
     * RawInteractionModel and is the ordinal number of the interaction for the
     * "case" that performed the interaction. This number is also used as pat of
     * the key in the EdgeTable found in the DataNetwork class.
     * @param value - The actual value of the action.
     * @param networkElementType - An enum defining whether the obj is an edge
     * or a node. This is necessary due to the fact both items implement the
     * same interface, the NetworkElementApi.
     * @param caseName - This is the identifier for the case that has performed
     * the action and thus has the related NetworkEdge in the NetworkDisplay.
     */
    public NetworkEdgeModel(Integer interactionIndex, String value,
            NetworkElementType networkElementType, String caseName,
            NetworkElementApi preState, NetworkElementApi postState,
            ArrayList<String> optionalData)
    {
        super(interactionIndex, networkElementType, caseName, optionalData);

        mIdentifier = preState.getSimpleValue() + value + postState.getSimpleValue();

        mAction = value;
        mPreState = preState;
        mPostState = postState;
    }

    @Override
    public String getSimpleValue()
    {
        return (mAction);
    }

    public void setSimpleValue(String action)
    {
        this.mAction = action;
    }

    public NetworkElementApi getPreState()
    {
        return (mPreState);
    }

    public NetworkElementApi getPostState()
    {
        return (mPostState);
    }

    @Override
    public void setGoalPathValue(boolean value)
    {
        try
        {
            //mGoalPathValue = (value);
            throw new Exception("Should not be calling this function for edges, current not implemented.");
        } catch (Exception ex)
        {
            Logger.getLogger(NetworkEdgeModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean getGoalPathValue()
    {
        try
        {
            //return (mGoalPathValue);
            throw new Exception("Should not be calling this function for edges, current not implemented.");
        } catch (Exception ex)
        {
            Logger.getLogger(NetworkEdgeModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (false);
    }

    @Override
    public void setGoalCaseCount(int goalCaseCount)
    {
        try
        {
            //mGoalCaseCount = goalCaseCount;
            throw new Exception("Should not be calling this function for edges, current not implemented.");
        } catch (Exception ex)
        {
            Logger.getLogger(NetworkEdgeModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int getGoalCaseCount()
    {
        try
        {
            //return (mGoalCaseCount);
            throw new Exception("Should not be calling this function for edges, current not implemented.");
        } catch (Exception ex)
        {
            Logger.getLogger(NetworkEdgeModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (0);
    }
    /*@Override
     public ArrayList<NetworkCaseApi> getCaseList()
     {
     return (mCaseList);
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
     mPCSupport.removePropertyChangeListener(listener);
     }

     @Override
     public PropertyChangeListener[] getPropertyChangeListener()
     {
     return (mPCSupport.getPropertyChangeListeners());
     }

     @Override
     public String getElementLabel()
     {
     return (mActionLabel);
     }

     @Override
     public void setElementLabel(String label)
     {
     mActionLabel = label;
     }*/
    /*
     @Override
     public ArrayList<String> getOptionalData()
     {
     return (mOptionalData);
     }

     @Override
     public double getBetweennessValue()
     {
     return (mEdgeBetweennessValue);
     }

     @Override
     public void setBetweennessValue(double value)
     {
     mEdgeBetweennessValue = value;
     }
     */
    /*
     @Override
     public boolean getGoalValue()
     {
     return (mGoalValue);
     }

     @Override
     public boolean getErrorValue()
     {
     return (mErrorValue);
     }

     @Override
     public void setSelected(boolean value)
     {
     boolean previousValue = mSelected;
     mSelected = value;
     mPCSupport.firePropertyChange("Selected", previousValue, value);

     }

     @Override
     public boolean getSelected()
     {
     return (mSelected);
     }

     @Override
     public void setGoalValue(boolean value)
     {
     mGoalValue = value;
     }
     */
    /*
     @Override
     public NetworkElementType getNetworkElementType()
     {
     return (mNetworkElementType);
     }
     */
    /*
     @Override
     public void setNetworkElementType(NetworkElementType networkElementType)
     {
     mNetworkElementType = networkElementType;
     }

     @Override
     public Integer getUniqueFrequency()
     {
     return (this.getUniqueCaseIdSet().size());
     }

     @Override
     public Set<String> getUniqueCaseIdSet()
     {
     Set<String> UniqueSet = new HashSet<String>(this.getCaseIdList());
     return (UniqueSet);
     }

     @Override
     public ArrayList<String> getCaseIdList()
     {
     return (mCaseIdList);
     }

     @Override
     public String getUniqueCaseIdSetAsString()
     {
     StringBuilder uniqueNameSet = new StringBuilder();
     Set<String> uniqueCaseSet = this.getUniqueCaseIdSet();
     for (int idx = 0; idx < uniqueCaseSet.size(); idx++)
     {
     uniqueNameSet.append(uniqueCaseSet.toArray()[idx].toString());
     uniqueNameSet.append(", ");
     }

     return (uniqueNameSet.toString());
     }

     @Override
     public String getCaseIdListAsString()
     {
     StringBuilder nameList = new StringBuilder();
     for (int idx = 0; idx < this.getCaseIdList().size(); idx++)
     {
     nameList.append(mCaseIdList.get(idx));
     nameList.append(", ");
     }

     return (nameList.toString());
     }

     @Override
     public void AddCaseById(String CaseId)
     {
     mCaseIdList.add(CaseId);
     }

     @Override
     public Integer getCaseFrequency()
     {
     return (mCaseIdList.size());
     }
     */
    /**
     * This returns the identifier for the EdgeModel.
     *
     * @return
     */
    /*
     @Override
     public String getValue()
     {
     return (mIdentifier);
     }

     @Override
     public String getSimpleValue()
     {
     return (mAction);
     }

     @Override
     public void setLabelVisibility(boolean val)
     {
     this.mLabelVisible = val;
     }

     @Override
     public boolean getLabelVisibility()
     {
     return (mLabelVisible);
     }
     */
    /*
     @Override
     public void setMdpValue(Double value)
     {
     mMdpValue = value;
     }

     @Override
     public Double getMDPValue()
     {
     return (mMdpValue);
     }

     @Override
     public void AddPreCondition(String preCondition, String Case)
     {
     mActionPreCondition.put(preCondition, Case);
     }

     @Override
     public HashMap<String, String> getPreCondition()
     {
     return (mActionPreCondition);
     }

     @Override
     public void AddPostCondition(String postCondition, String Case)
     {
     mActionPostCondition.put(postCondition, Case);
     if (!mActionPostCondition.containsValue(postCondition, Case))
     {
     mUniqueActionPostCondition.put(postCondition, Case);
     }
     }

     @Override
     //public HashMap<String, String> getPostCondition()
     public MultiValueMap getPostCondition()
     {
     return (mActionPostCondition);
     }
     */

    /*
     @Override
     public MultiValueMap getUniquePostCondition()
     {
     return (mUniqueActionPostCondition);
     }*/
    /*
     @Override
     public String getMostFrequentPostCondition()
     {
     StringBuilder MostFrequentPostConditions = new StringBuilder();
     HashMap<String, Integer> PostConditionFrequencyMap = new HashMap<String, Integer>();

     //Add the post Conditions to a new map, along with their frequency.
     for (int i = 0; i < mActionPostCondition.keySet().size(); i++)
     {
     String currentKey = mActionPostCondition.keySet().toArray()[i].toString();
     PostConditionFrequencyMap.put(currentKey, mActionPostCondition.getCollection(currentKey).size());
     }

     ValueComparator bvc = new ValueComparator(PostConditionFrequencyMap);
     TreeMap<String, Integer> sorted_map = new TreeMap<String, Integer>(bvc);
     sorted_map.putAll(PostConditionFrequencyMap);
     if (!sorted_map.isEmpty())
     {
     MostFrequentPostConditions.append(sorted_map.firstKey()).append(" - ").append(mActionPostCondition.getCollection(sorted_map.firstKey()).size());
     }
     return (MostFrequentPostConditions.toString());
     }

     @Override
     public String getMostFrequentUniquePostCondition()
     {
     StringBuilder MostFrequentUniquePostConditions = new StringBuilder();
     HashMap<String, Integer> UniquePostConditionFrequencyMap = new HashMap<String, Integer>();

     //Add the post Conditions to a new map, along with their frequency.
     for (int i = 0; i < mUniqueActionPostCondition.keySet().size(); i++)
     {
     String currentKey = mUniqueActionPostCondition.keySet().toArray()[i].toString();
     UniquePostConditionFrequencyMap.put(currentKey, mUniqueActionPostCondition.getCollection(currentKey).size());
     }

     ValueComparator bvc = new ValueComparator(UniquePostConditionFrequencyMap);
     TreeMap<String, Integer> sorted_map = new TreeMap<String, Integer>(bvc);
     sorted_map.putAll(UniquePostConditionFrequencyMap);
     if (!sorted_map.isEmpty())
     {
     MostFrequentUniquePostConditions.append(sorted_map.firstKey()).append(" - ").append(mUniqueActionPostCondition.getCollection(sorted_map.firstKey()).size());
     }
     return (MostFrequentUniquePostConditions.toString());
     }
     */
    /*@Override
     public String getPostConditionStringList()
     {
     StringBuilder PostConditions = new StringBuilder();

     for (int i = 0; i < mActionPostCondition.keySet().size(); i++)
     {
     PostConditions.append(mActionPostCondition.keySet().toArray()[i].toString());
     PostConditions.append("\n");
     }


     return (PostConditions.toString());
     }*/
    /*
     @Override
     public String getPostConditionStringList()
     {
     StringBuilder PostConditions = new StringBuilder();

     HashMap<String, Integer> PostConditionFrequencyMap = new HashMap<String, Integer>();

     for (int i = 0; i < mActionPostCondition.keySet().size(); i++)
     {
     String currentKey = mActionPostCondition.keySet().toArray()[i].toString();
     PostConditionFrequencyMap.put(currentKey, mActionPostCondition.getCollection(currentKey).size());
     }
     ValueComparator bvc = new ValueComparator(PostConditionFrequencyMap);

     TreeMap<String, Integer> sorted_map = new TreeMap<String, Integer>(bvc);
     sorted_map.putAll(PostConditionFrequencyMap);

     for (int i = 0; i < sorted_map.keySet().size(); i++)
     {
     String currentKey = sorted_map.keySet().toArray()[i].toString();
     PostConditions.append(currentKey).append(" - ").append(mActionPostCondition.getCollection(currentKey).size()).append("\n");
     }
     return (PostConditions.toString());
     }

     @Override
     public String getPreConditionStringList()
     {
     StringBuilder PreConditions = new StringBuilder();

     for (int i = 0; i < mActionPreCondition.keySet().size(); i++)
     {
     PreConditions.append(mActionPreCondition.keySet().toArray()[i].toString());
     PreConditions.append("\n");
     }

     return (PreConditions.toString());
     }*/
}
