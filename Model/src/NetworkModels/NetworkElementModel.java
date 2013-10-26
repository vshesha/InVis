package NetworkModels;

import invis.gl.networkapi.NetworkCaseApi;
import invis.gl.networkapi.NetworkElementApi;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import org.apache.commons.collections.map.MultiValueMap;

/**
 *
 * @author Matt
 */
public abstract class NetworkElementModel implements NetworkElementApi
{

    protected Integer mInteractionIndex;
    //private HashMap<String, String> mActionPostCondition;//The key is the PostCondition, the object is the Case who made used that Post-Condition.
    protected MultiValueMap mActionPostCondition;
    protected HashMap<String, String> mActionPreCondition; //The key is the PreCondition, the object is the Case who made used that Pre-Condition.
    protected ArrayList<String> mCaseIdList;
    protected ArrayList<NetworkCaseApi> mCaseList;
    protected boolean mErrorValue;
    protected int mGoalCaseCount;
    protected boolean mGoalPathValue;
    protected boolean mGoalValue;
    protected String mIdentifier;
    protected boolean mLabelVisible;
    protected Double mMdpValue;
    protected NetworkElementType mNetworkElementType;
    protected ArrayList<String> mOptionalData;
    protected PropertyChangeSupport mPCSupport;
    protected boolean mSelected;
    private int mClusterID;
    protected String mElementLabel;
    protected MultiValueMap mUniqueActionPostCondition;
    protected double mElementBetweennessValue;

    public NetworkElementModel(Integer interactionIndex, NetworkElementType networkElementType, String caseName, ArrayList<String> optionalData)
    {
        mInteractionIndex = interactionIndex;
        mNetworkElementType = networkElementType;

        mCaseIdList = new ArrayList<String>();
        mCaseIdList.add(caseName);
        mCaseList = new ArrayList<NetworkCaseApi>();

        mElementLabel = "";
        mLabelVisible = true;

        mErrorValue = false;
        mGoalValue = false;

        mOptionalData = optionalData;
        mMdpValue = 0.0;
        mElementBetweennessValue = 0.0f;
        mClusterID = 0;

        mActionPreCondition = new HashMap<String, String>();
        mActionPostCondition = new MultiValueMap();
        mUniqueActionPostCondition = new MultiValueMap();
        mGoalCaseCount = 0;

        mPCSupport = new PropertyChangeSupport(this);
    }

    @Override
    public void AddCaseById(String CaseId)
    {
        mCaseIdList.add(CaseId);
    }

    @Override
    public void AddPostCondition(String postCondition, String Case)
    {
        //We can limit this to Unique postcondition case sets.
        //If the same person performs the same action, like delete or same error,
        // we only store their behavior once...
        //if (!mActionPostCondition.containsValue(postCondition, Case))
        //{
        mActionPostCondition.put(postCondition, Case);
        //}
        if (!mUniqueActionPostCondition.containsValue(postCondition, Case))
        {
            if (!postCondition.isEmpty())
            {
                mUniqueActionPostCondition.put(postCondition, Case);
            }
        }
    }

    @Override
    public void AddPreCondition(String preCondition, String Case)
    {
        mActionPreCondition.put(preCondition, Case);
    }

    @Override
    public void addCase(NetworkCaseApi _Case)
    {
        mCaseList.add(_Case);
    }

    // add interested listeners here
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        mPCSupport.addPropertyChangeListener(listener);
    }

    @Override
    public Integer getInteractionIndex()
    {
        return mInteractionIndex;
    }

    @Override
    public double getBetweennessValue()
    {
        return mElementBetweennessValue;
    }

    @Override
    public Integer getCaseFrequency()
    {
        return mCaseIdList.size();
    }

    @Override
    public ArrayList<String> getCaseIdList()
    {
        return mCaseIdList;
    }

    @Override
    public String getCaseIdListAsString()
    {
        return mCaseIdList.toString();
    }

    @Override
    public ArrayList<NetworkCaseApi> getCaseList()
    {
        return mCaseList;
    }

    @Override
    public String getElementLabel()
    {
        return mElementLabel;
    }

    @Override
    public boolean getErrorValue()
    {
        return mErrorValue;
    }

    @Override
    public void setErrorValue(boolean value)
    {
        mErrorValue = value;
    }

    @Override
    public int getGoalCaseCount()
    {
        return mGoalCaseCount;
    }

    @Override
    public boolean getGoalPathValue()
    {
        return mGoalPathValue;
    }

    @Override
    public boolean getGoalValue()
    {
        return mGoalValue;
    }

    @Override
    public boolean getLabelVisibility()
    {
        return mLabelVisible;
    }

    @Override
    public int getClusterID()
    {
        return (mClusterID);
    }

    @Override
    public void setClusterID(int id)
    {
        mClusterID = id;
    }

    @Override
    public Double getMDPValue()
    {
        return mMdpValue;
    }

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
        return MostFrequentPostConditions.toString();
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
        return MostFrequentUniquePostConditions.toString();
    }

    @Override
    public NetworkElementType getNetworkElementType()
    {
        return mNetworkElementType;
    }

    @Override
    public ArrayList<String> getOptionalData()
    {
        return mOptionalData;
    }

    //public HashMap<String, String> getPostCondition()
    @Override
    public MultiValueMap getPostCondition()
    {
        return mActionPostCondition;
    }

    @Override
    public String getPostConditionStringList()
    {
        StringBuilder PostConditions = new StringBuilder();
        HashMap<String, Integer> PostConditionFrequencyMap = new HashMap<String, Integer>();
        for (int i = 0; i < mActionPostCondition.keySet().size(); i++)
        {
            String currentKey = mActionPostCondition.keySet().toArray()[i].toString();
            //PostConditions.append(currentKey).append(" - ").append(mActionPostCondition.getCollection(currentKey).size()).append("\n");
            PostConditionFrequencyMap.put(currentKey, mActionPostCondition.getCollection(currentKey).size());
        }
        ValueComparator bvc = new ValueComparator(PostConditionFrequencyMap);
        TreeMap<String, Integer> sorted_map = new TreeMap<String, Integer>(bvc);
        sorted_map.putAll(PostConditionFrequencyMap);
        for (int i = 0; i < sorted_map.keySet().size(); i++)
        {
            String currentKey = sorted_map.keySet().toArray()[i].toString();
            PostConditions.append(currentKey).append(" - ").append(mActionPostCondition.getCollection(currentKey).size()).append(", ");
        }
        return PostConditions.toString();
    }

    @Override
    public HashMap<String, String> getPreCondition()
    {
        return mActionPreCondition;
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
        return PreConditions.toString();
    }

    @Override
    public PropertyChangeListener[] getPropertyChangeListener()
    {
        return mPCSupport.getPropertyChangeListeners();
    }

    @Override
    public boolean getSelected()
    {
        return mSelected;
    }

    @Override
    public String getSimpleValue()
    {
        return mIdentifier;
    }

    @Override
    public Set<String> getUniqueCaseIdSet()
    {
        Set<String> UniqueSet = new HashSet<String>(this.getCaseIdList());
        return UniqueSet;
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
        return uniqueNameSet.toString();
    }

    @Override
    public Integer getUniqueFrequency()
    {
        return this.getUniqueCaseIdSet().size();
    }

    @Override
    public MultiValueMap getUniquePostCondition()
    {
        return mUniqueActionPostCondition;
    }

    /**
     * This returns the identifier for the NodeModel.
     *
     * @return
     */
    @Override
    public String getValue()
    {
        return mIdentifier;
    }

    @Override
    public void removeCase(NetworkCaseApi _Case)
    {
        mCaseList.remove(_Case);
    }

    // don't forget to remove them
    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        mPCSupport.removePropertyChangeListener(listener);
    }

    @Override
    public void setBetweennessValue(double value)
    {
        mElementBetweennessValue = value;
    }

    @Override
    public void setElementLabel(String label)
    {
        mElementLabel = label;
    }

    @Override
    public void setGoalValue(boolean goal)
    {
        mGoalValue = goal;
    }

    @Override
    public void setLabelVisibility(boolean visibility)
    {
        this.mLabelVisible = visibility;
    }

    @Override
    public void setMdpValue(Double value)
    {
        mMdpValue = value;
    }

    public void setNetworkElementType(NetworkElementType networkElementType)
    {
        mNetworkElementType = networkElementType;
    }

    @Override
    public void setSelected(boolean value)
    {
        boolean previousValue = mSelected;
        mSelected = value;
        mPCSupport.firePropertyChange("Selected", previousValue, value);
    }
}
