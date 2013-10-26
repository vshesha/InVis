package NetworkModels;

import invis.gl.api.CaseSetModelApi;
import invis.gl.api.RawInteractionApi;
import invis.gl.networkapi.NetworkCaseApi;
import invis.gl.networkapi.NetworkCaseSetApi;
import invis.gl.networkapi.NetworkElementApi;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Matt
 */
@ServiceProvider(service = NetworkCaseSetModel.class)
public class NetworkCaseSetModel implements NetworkCaseSetApi
{

    private ArrayList<NetworkCaseApi> mNetworkCaseList;
    private String mCaseSetName;
    private ArrayList<String> mCaseGroupIdList;
    private HashSet<String> mUniqueCaseGroupIdSet;
    private boolean mCasesHaveGroupIds;

    public NetworkCaseSetModel()
    {
        this.mCaseSetName = "";
        this.mCasesHaveGroupIds = false;
    }

    public NetworkCaseSetModel(CaseSetModelApi caseSet, String CaseSetName, HashMap<String, NetworkElementApi> nodeTable, HashMap<String, NetworkElementApi> edgeTable)
    {
        this.mCasesHaveGroupIds = false;
        this.mUniqueCaseGroupIdSet = new HashSet<String>(); //Create an empty HashSet for UniqueCaseGroupIds.
        this.mCaseGroupIdList = new ArrayList<String>(); //Create an empty ArrayList for CaseGroupIdList.

        this.mNetworkCaseList = new ArrayList<NetworkCaseApi>();
        NetworkCaseModel newNetworkCase;
        for (int i = 0; i < caseSet.getCaseList().size(); i++)
        {
            String caseName = caseSet.getCaseList().get(i).getCaseName();
            newNetworkCase = new NetworkCaseModel(caseName);
            newNetworkCase.setCaseGroupID(caseSet.getCaseList().get(i).getCaseGroupID());
            for (int j = 0; j < caseSet.getCaseList().get(i).getInteractionsList().size(); j++)
            {
                RawInteractionApi relatedRawInteraction = caseSet.getCaseList().get(i).getInteractionsList().get(j);
                NetworkInteractionModel newNetworkInteraction = new NetworkInteractionModel(relatedRawInteraction, caseName, nodeTable, edgeTable);
                newNetworkCase.addInteraction(newNetworkInteraction);
            }
            this.mNetworkCaseList.add(newNetworkCase);
        }
        this.mCaseSetName = CaseSetName;
    }
    
    //CSM.getCaseList().get(caseIndex).getInteractionsList().get(interactionIndex).getFileLineIndex();

    @Override
    public Integer getNetworkCaseSetSize()
    {
        return (mNetworkCaseList.size());
    }
    
    public void setNetworkCaseList(List<NetworkCaseApi> list)
    {
        mNetworkCaseList = (ArrayList<NetworkCaseApi>) list;
    }

    @Override
    public ArrayList<NetworkCaseApi> getNetworkCaseList()
    {
        return mNetworkCaseList;
    }

    /**
     * This is called directly after the constructor and is used for populating
     * data fields in GenerageGroupIdContainers.
     *
     * @param value true when the input data contains CaseGroupIds, otherwise
     * false.
     */
    @Override
    public void setDataContainsCaseGroupIds(boolean value)
    {
        mCasesHaveGroupIds = value;
        if (mCasesHaveGroupIds)
        {
            this.GenerateGroupIdContainers();
        }
    }

    /**
     * This creates an ArrayList of all the CaseGroupIds, and also a HashSet of
     * all the CaseGroupIds. This allows us to get frequencies for both, each
     * group, and the total of unique groups.
     */
    private void GenerateGroupIdContainers()
    {
        mCaseGroupIdList = new ArrayList<String>();
        for (int index = 0; index < mNetworkCaseList.size(); index++)
        {
            mCaseGroupIdList.add(mNetworkCaseList.get(index).getCaseGroupID());
        }
        mUniqueCaseGroupIdSet = new HashSet<String>(mCaseGroupIdList);
    }

    @Override
    public int getUniqueCaseGroupIdCount()
    {
        if (mUniqueCaseGroupIdSet != null)
        {
            return (mUniqueCaseGroupIdSet.size());
        } else
        {
            return (0);
        }
    }

    @Override
    public String getUniqueCaseGroupIdAsString()
    {
        if (mUniqueCaseGroupIdSet != null)
        {
            return (mUniqueCaseGroupIdSet.toString());
        } else
        {
            return ("");
        }
    }

    @Override
    public NetworkCaseApi findCaseByCaseId(String caseId)
    {
        for (int networkCaseIndex = 0; networkCaseIndex < this.getNetworkCaseSetSize(); networkCaseIndex++)
        {
            if (this.getNetworkCaseList().get(networkCaseIndex).getCaseName() == caseId)
            {
                return (this.getNetworkCaseList().get(networkCaseIndex));
            }
        }
        try
        {
            throw new Exception("The Case Id: "+ caseId.toString()+" does not exist in the NetworkCaseSet.");
        } catch (Exception ex)
        {
            Logger.getLogger(NetworkCaseSetModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        JOptionPane.showMessageDialog(new JFrame(), "The Case Id: "+ caseId.toString()+" does not exist in the NetworkCaseSet.");
        return (null);
    }

    @Override
    public HashSet<String> getUniqueCaseGroupIdSet()
    {
        return (mUniqueCaseGroupIdSet);
    }

    @Override
    public String getCaseGroupIdFrequencyMapAsString()
    {
        //This is only useful if cases in the data have group-Ids so we check that first.
        
        //An empty HashMap, returned from getCaseGroupIdFrequencyMap() can probably still
        //produce a String, when the tostring funciton is called, but we'll be extra careful.
        if (mCasesHaveGroupIds)
        {
            return (this.getCaseGroupIdFrequencyMap().toString());
        } else
        {
            return ("");
        }

    }

    /**
     * This generates a hashMap of each CaseGroupId as the key, and the
     * frequency of that caseGroupId, based on the caseGroupID that is assigned
     * to each Case.
     *
     * if Cases have Group-Ids, then we iterate through the mCaseGroupIdList.
     * The mCaseGroupIdList is populated from the private function
     * GenerateGroupIdContainers(). GenerateGroupIdContainers is called by
     * setDataContainsCaseGroupIds(). setDataContainsCaseGroupIds also sets
     * mCasesHaveGroupIds to true.
     *
     * @return a HashMap where the String is the CaseGroupID and the Integer is the number
     * of cases which belong to that group.
     */
    @Override
    public HashMap<String, Integer> getCaseGroupIdFrequencyMap()
    {
        HashMap<String, Integer> CaseGroupIdFrequencyMap = new HashMap<String, Integer>();

        //This is only useful if cases in the data have group-Ids so we check that first.
        if (mCasesHaveGroupIds)
        {
            //if they do have Group-Ids, then we iterate through the mCaseGroupIdList.
            //The mCaseGroupIdList is populated from the private function GenerateGroupIdContainers().
            //Which is called by setDataContainsCaseGroupIds(). setDataContainsCaseGroupIds also sets mCasesHaveGroupIds to true.
            for (int i = 0; i < mCaseGroupIdList.size(); i++)
            {
                if (CaseGroupIdFrequencyMap.containsKey(mCaseGroupIdList.get(i)))
                {
                    //Increase an already existing item.
                    int elementCount = Integer.parseInt(CaseGroupIdFrequencyMap.get(mCaseGroupIdList.get(i)).toString());
                    elementCount++;
                    CaseGroupIdFrequencyMap.put(mCaseGroupIdList.get(i), elementCount);
                } else
                {
                    //else add a new item, with frequency 1.
                    
                    //uniqueList.add(mCaseGroupIdList.get(networkCaseIndex); //This will make the list of uniqueCaseGroupIds....
                    //Which could be a more efficient method than making mUniqueCaseGroupIdSet (HashSet) in GenerateGroupIdContainers()
                    // However I think calling the HashSet constructor is pretty fast.
                    CaseGroupIdFrequencyMap.put(mCaseGroupIdList.get(i), 1);
                }
            }
        }
        return (CaseGroupIdFrequencyMap);
    }

    public void setNetworkCaseList(ArrayList<NetworkCaseApi> networkCaseList)
    {
        this.mNetworkCaseList = networkCaseList;
    }

    @Override
    public String getCaseSetName()
    {
        return mCaseSetName;
    }

    @Override
    public void setCaseSetName(String CaseSetName)
    {
        this.mCaseSetName = CaseSetName;
    }

    @Override
    public String CasesToString()
    {
        StringBuilder propertyValue = new StringBuilder();

        //Offset by one, so the first interaciton reads 1 rather than 0.
        for (int i = 1; i <= this.getNetworkCaseSetSize(); i++)
        {
            propertyValue.append(i).append(": ").append(this.getNetworkCaseList().get(i - 1).getCaseName()).append(" \n");
        }
        return (propertyValue.toString());
    }
}
