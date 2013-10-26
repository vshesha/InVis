package invis.gl.dataprocessor;

import NetworkModels.NetworkCaseSetModel;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import invis.gl.api.CaseSetModelApi;
import invis.gl.clustermodel.NetworkClusterVertexModel;
import invis.gl.deriveddata.DerivedData;
import invis.gl.fileio.FileIO;
import invis.gl.networkVVLookup.NetworkVVLookup;
import invis.gl.networkapi.NetworkCaseApi;
import invis.gl.networkapi.NetworkCaseSetApi;
import invis.gl.networkapi.NetworkElementApi;
import invis.gl.NetworkClusterApi.NetworkClusterVertexApi;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Matt
 */
@ServiceProvider(service = DataParser.class)
public class DataParser
{

    private FileObject mFileObject;
    private CaseSetModelApi mCaseSet;
    private DataNetwork mDataNetwork;
    private ArrayList<String> mOptionalDataItems;
    //private Map<String, Icon> mImageMap;
    private boolean mLoadImages;
    private NetworkCaseSetApi mNetworkCaseSet;
    private HashMap<NetworkElementApi, Boolean> mGoalStatePathHashTable;
    private HashMap<NetworkCaseApi, Boolean> mGoalCasePathHashTable;
    private DerivedData mDerivedData;
    private NetworkVVLookup mNetworkVVLookup = new NetworkVVLookup();
    //Test Code:
    private HashMap<String, Double> ExtraDataTable = new HashMap<String, Double>();

    /**
     * An empty constructor, sets the FileObject to null, and instantiates a new
     * DataNetwork.
     */
    public DataParser()
    {
        mFileObject = null;
        mDataNetwork = new DataNetwork();
        mLoadImages = false;
        mGoalStatePathHashTable = new HashMap<NetworkElementApi, Boolean>();
        mGoalCasePathHashTable = new HashMap<NetworkCaseApi, Boolean>();
        mDerivedData = new DerivedData(this);

        //mClusterCount = 0;
    }

    public NetworkVVLookup getNetworkVVLookup()
    {
        return (mNetworkVVLookup);
    }

    /**
     * Returns the DataNetwork.
     *
     * @return mDataNetwork.
     */
    public DataNetwork GetDataNetwork()
    {
        return (mDataNetwork);
    }

    /**
     * Returns the graph of the DataNetwork.
     *
     * @return mDataNetwork's graph.
     */
    public DirectedSparseMultigraph<String, String> getGraph()
    {
        return (mDataNetwork.getGraph());
    }

    public void UpdateGraph(DirectedSparseMultigraph<String, String> graph)
    {
        mDataNetwork.UpdateGraph(graph);
    }

    /**
     *
     */
    public NetworkClusterVertexApi getVertexSet()
    {
        Collection VertexSet;
        VertexSet = this.getNodeTable().values();
        NetworkClusterVertexApi networkCluster = new NetworkClusterVertexModel("Complete", VertexSet);
        return (networkCluster);
    }

    public void SetExtraDataTable(HashMap<String, Double> extradata)
    {
        ExtraDataTable = extradata;
    }

    public HashMap<String, Double> getExtraDatatable()
    {
        return (ExtraDataTable);
    }

    /**
     * Checks if the FileName is null, if so it returns false, else true.
     *
     * @return boolean based on the value returned by getFileName().
     */
    public boolean hasData()
    {
        if (this.getFileName() == null)
        {
            return (false);
        } else
        {
            return (true);
        }
    }

    /**
     * If the FileObject is not null it returns the FileObject's name. Otherwise
     * it returns null.
     *
     * @return mFileObject's Name as a String. Can be null.
     */
    public String getFileName()
    {
        if (mFileObject != null)
        {
            return (mFileObject.getName());
        } else
        {
            return (null);
        }
    }

    /**
     * Returns the CaseSet, the set of all cases.
     *
     * @return mCaseSet.
     */
    public CaseSetModelApi getCaseSet()
    {
        return (mCaseSet);
    }

    /**
     * This constructs the NetworkCaseSet and returns it.
     *
     * @return
     */
    public NetworkCaseSetApi getNetworkCaseSet()
    {

        /*if (mNetworkCaseSet == null)
         {
         NetworkCaseSetApi networkCaseSet = new NetworkCaseSetModel(this.getCaseSet(),
         this.getCaseSet().getCaseSetName(),
         this.GetDataNetwork().getNodeTable(),
         this.GetDataNetwork().getEdgeTable());

         // We inform the networkCaseSet whether the data contains CaseGroupIds
         networkCaseSet.setDataContainsCaseGroupIds(this.DataContainsCaseGroupIds());
         }*/
        return (mNetworkCaseSet);
    }

    /**
     * Returns the number of Cases in the Network. This is equal to the size of
     * the CaseSet.
     *
     * @return CaseSet size.
     */
    public Integer getNetworkCaseSetSize()
    {
        return (this.getNetworkCaseSet().getNetworkCaseSetSize());
    }

    /**
     * This is just a quick check for other components for determining if the
     * currently loaded dataset includes ERROR and GOAL data. (See the MDP
     * Calculator for an example).
     *
     * @return true if the DataParser has ERROR and GOAL optional data items
     * loaded in.
     */
    public boolean DataContainsGoalStates()
    {
        if (this.getOptionalDataItemsList().contains("GOAL"))
        {
            return (true);
        }
        return (false);
    }

    public boolean DataContainsErrorStates()
    {
        if (this.getOptionalDataItemsList().contains("ERROR"))
        {
            return (true);
        }
        return (false);
    }

    public boolean DataContainsCaseGroupIds()
    {
        if (this.getOptionalDataItemsList().contains("CASE_GROUP_ID"))
        {
            return (true);
        }
        return (false);
    }

    public boolean DataContainsParameters()
    {
        if (this.getOptionalDataItemsList().contains("PRE_CONDITION") && this.getOptionalDataItemsList().contains("POST_CONDITION"))
        {
            return (true);
        }
        return (false);
    }

    public boolean DataContainsActionLabel()
    {
        if (this.getOptionalDataItemsList().contains("ACTION_LABEL"))
        {
            return (true);
        }
        return (false);

    }

    public boolean DataContainsStateLabel()
    {
        if (this.getOptionalDataItemsList().contains("STATE_LABEL"))
        {
            return (true);
        }
        return (false);
    }

    public boolean DataContainsImagesToLoad()
    {
        return (mLoadImages);
    }

    /**
     * Returns the number of States in the Network. This is equal to the size of
     * the NodeTable.
     *
     * @return NodeTable size.
     */
    public Integer getNetworkStateCount()
    {
        return (this.getNodeTable().size());
    }

    /**
     * Returns the number of Actions in the Network. This is equal to the size
     * of the EdgeTable.
     *
     * @return EdgeTable size.
     */
    public Integer getNetworkActionCount()
    {
        return (this.getEdgeTable().size());
    }

    /**
     * Returns the list of states visited to from the index of the case that you
     * supply. The index corresponds to the index of mCaseSet
     *
     * @param index - the index of Case, whose state list you want returned.
     * @return List of states of type String
     */
    public ArrayList<String> GetCaseStates(int index)
    {
        String PreState;
        String PostState;
        ArrayList<String> stateList = new ArrayList<String>();

        /*
         * Could do an iterator here to get all states from the set
         */
        for (int InteractionIterator = 0; InteractionIterator < this.getCaseSet().getCaseList().get(index).getInteractionsList().size(); InteractionIterator++)
        {
            PreState = this.getCaseSet().getCaseList().get(index).getInteractionsList().get(InteractionIterator).getPreState();
            PostState = this.getCaseSet().getCaseList().get(index).getInteractionsList().get(InteractionIterator).getPostState();

            if (!stateList.contains(PreState))
            {
                stateList.add(PreState);
            }
            if (!stateList.contains(PostState))
            {
                stateList.add(PostState);
            }
        }
        return (stateList);
    }

    /**
     * This method returns the list of actions as an ArrayList of type String
     * for a specific Case Index. It iterates through the interaction list of a
     * particular case in the case list, and adds all of that case's actions to
     * a list and returns it.
     *
     * @param index the index of the case the user is interested in. This is the
     * index of mCaseList.
     * @return the list of actions in ArrayList<String> format.
     */
    public ArrayList<String> getCaseActions(int index)
    {
        ArrayList<String> actionList = new ArrayList<String>();
        String action;

        for (int interactionIterator = 0; interactionIterator < this.getCaseSet().getCaseList().get(index).getInteractionsList().size(); interactionIterator++)
        {
            action = this.getCaseSet().getCaseList().get(index).getInteractionsList().get(interactionIterator).getAction();
            if (!actionList.contains(action) && action != null)
            {
                actionList.add(action);
            }
        }
        return (actionList);
    }

    /**
     * Opens a file-open dialog. Sets mFileObject as the result of the file-open
     * dialog.
     *
     * @throws Exception
     */
    public void OpenFile() throws Exception
    {
        File file = FileIO.OpenFile();
        if (file != null)
        {
            mFileObject = FileUtil.toFileObject(file);
        }
    }

    /**
     * Parses the data from the input file. If the FileObject is null it calls
     * OpeFile(). If it is not null, it creates a new DataPopulator that takes
     * the file object as an argument. Next it instantiates the CaseSet, from
     * calling the DataPopulator's getCaseSetModel(). Lastly, it sets the
     * CaseSet's Name which is equal to the name of the fileObject. Afterwards,
     * based on the data, it calls the DataNetwork's BuildData.
     *
     * @throws Exception
     */
    public void ParseData() throws Exception
    {
        if (mFileObject == null)
        {
            this.OpenFile();
        }
        if (mFileObject != null)
        {
            DataPopulator dp = new DataPopulator();
            try
            {
                dp.ProcessData(mFileObject.asText());
            } catch (IOException e)
            {

                throw new Exception("Error loading file.");
            }

            mCaseSet = dp.getCaseSetModel();
            mCaseSet.setCaseSetName(mFileObject.getName());
            mOptionalDataItems = dp.getValidOptionalHeaderItems();
            mDataNetwork.BuildData(this);
            this.GenerateNetworkCaseSet();
            this.MatchNetworkCaseToNetworkElements();
            this.GenerateGoalStateFlags(); //TODO: Bug test this code. Make sure it works.

            //this.IncorporateGoalStateDescriptions();
        }
    }

    /*private void IncorporateGoalStateDescriptions()
    {
        if (this.DataContainsGoalStates())
        {
            Set<String> keys = mDataNetwork.getNodeTable().keySet();

            //mDataNetwork.getGraph().add
        }
    }*/

    public void BuildStepBasedData()
    {
        mDerivedData = new DerivedData(this);
        mDerivedData.GenerateStepBasedGraphNodes();
    }

    public boolean HasDerivedData()
    {

        if (mDerivedData != null && this.hasData())
        {
            return (true);
        }
        return (false);
    }

    /**
     * This method is used for generating two different Hashtables. These
     * hashtables store an Action or State, and a Case. They also store the
     * boolean value of whether or not that vertex is on a goal path.
     */
    private void GenerateGoalStateFlags()
    {
        
        if (this.DataContainsGoalStates())
        {
            for (int i = 0; i < mNetworkCaseSet.getNetworkCaseList().size(); i++)
            {
                boolean pushTrue = false;
                for (int j = 0; j < mNetworkCaseSet.getNetworkCaseList().get(i).getInteractionsList().size(); j++)
                {
                    //Handle the preState
                    if (mNetworkCaseSet.getNetworkCaseList().get(i).getInteractionsList().get(j).getPreState().getGoalValue())
                    {
                        this.PushAllStatesToGoal(i, true);
                        this.PushCaseToGoal(i, true);
                        pushTrue = true;
                    }

                    //Handle the post-state.
                    if (mNetworkCaseSet.getNetworkCaseList().get(i).getInteractionsList().get(j).getPostState().getGoalValue())
                    {
                        this.PushAllStatesToGoal(i, true);
                        this.PushCaseToGoal(i, true);
                        pushTrue = true;
                    }
                }
                if (!pushTrue)
                {
                    this.PushAllStatesToGoal(i, false);
                    this.PushCaseToGoal(i, false);
                }
            }
            this.UpdateGoalCases();
            this.UpdateGoalStates();
            this.UpdateStateGoalCaseRatio();
        }
    }

    private void UpdateGoalCases()
    {
        for (int i = 0; i < mNetworkCaseSet.getNetworkCaseList().size(); i++)
        {
            boolean goalValue = mGoalCasePathHashTable.get(mNetworkCaseSet.getNetworkCaseList().get(i));
            if (this.DataContainsGoalStates())
            {
                mNetworkCaseSet.getNetworkCaseList().get(i).setGoalCase(goalValue);
            }
        }
    }

    private void UpdateGoalStates()
    {
        Set<String> Keys = mDataNetwork.getNodeTable().keySet();
        for (int i = 0; i < Keys.size(); i++)
        {
            String key = Keys.toArray()[i].toString();
            NetworkElementApi NEA = mDataNetwork.getNodeTable().get(key);
            if (mGoalStatePathHashTable.containsKey(NEA)) //boolean goalPathValue = mGoalStatePathHashTable.get(NEA);
            {
                mDataNetwork.getNodeTable().get(key).setGoalPathValue(true);
            } else
            {
                mDataNetwork.getNodeTable().get(key).setGoalPathValue(false);
            }
            //mDataNetwork.getNodeTable().get(Keys.toArray()[i]).setGoalPathValue(goalPathValue);
        }
    }

    private void UpdateStateGoalCaseRatio()
    {
        Set<String> Keys = mDataNetwork.getNodeTable().keySet();
        for (int i = 0; i < Keys.size(); i++)
        {
            String stateKey = Keys.toArray()[i].toString();
            Set<String> CaseSet = mDataNetwork.getNodeTable().get(stateKey).getUniqueCaseIdSet();
            int goalCaseCount = 0;

            for (int j = 0; j < CaseSet.size(); j++)
            {
                String caseID = CaseSet.toArray()[j].toString();
                NetworkCaseApi NCA = mNetworkCaseSet.findCaseByCaseId(caseID);
                if (mGoalCasePathHashTable.get(NCA))
                {
                    goalCaseCount++;
                }
            }
            mDataNetwork.getNodeTable().get(stateKey).setGoalCaseCount(goalCaseCount);
        }
    }

    /**
     * This method receives a caseIndex and a boolean value and populates the
     * mGoalCasePathHashTable accordingly.
     *
     * @param caseIndex the index of the case in which we want to set a value.
     * @param value the value of whether or not that case made it to the goal.
     * true means this case contains a goal-state in their set of states. false
     * means this case does NOT contain a goal-state in their set of states.
     */
    private void PushCaseToGoal(int caseIndex, boolean value)
    {
        mGoalCasePathHashTable.put(mNetworkCaseSet.getNetworkCaseList().get(caseIndex), value);
    }

    /**
     * This method populates a hashtable, mGoalStatePathHashTable, which stores
     * a boolean value for all states. If the
     *
     * @param CaseIndex
     * @param value
     */
    private void PushAllStatesToGoal(int CaseIndex, boolean value)
    {
        for (int i = 0; i < mNetworkCaseSet.getNetworkCaseList().get(CaseIndex).getInteractionCount(); i++)
        {
            //If the value is false, we don't want to overwrite the goal-value of the state.
            if (value
                    || (mGoalStatePathHashTable.containsKey(mNetworkCaseSet.getNetworkCaseList().get(CaseIndex).getInteractionsList().get(i).getPreState())
                    && value))
            {
                mGoalStatePathHashTable.put(mNetworkCaseSet.getNetworkCaseList().get(CaseIndex).getInteractionsList().get(i).getPreState(), value);
            }
        }

    }

    private void GenerateNetworkCaseSet()
    {
        mNetworkCaseSet = new NetworkCaseSetModel(this.getCaseSet(),
                this.getCaseSet().getCaseSetName(),
                this.GetDataNetwork().getNodeTable(),
                this.GetDataNetwork().getEdgeTable());

        // We inform the networkCaseSet whether the data contains CaseGroupIds
        mNetworkCaseSet.setDataContainsCaseGroupIds(this.DataContainsCaseGroupIds());
    }

    public ArrayList<String> getOptionalDataItemsList()
    {
        return (mOptionalDataItems);
    }

    /**
     * Sets the mFileObject to Null, and creates a new empty DataNetwork.
     *
     * @throws IOException
     */
    public void CloseFile() throws IOException
    {
        mFileObject = null;
        mDataNetwork = new DataNetwork();
    }

    /**
     * This returns the EdgeTable, the HashMap that contains all of the
     * Edge-data in the DataNetwork.
     *
     * @return HashMap EdgeTable, the HashMap containing all of the Edge-data.
     */
    public HashMap<String, NetworkElementApi> getEdgeTable()
    {
        return (mDataNetwork.getEdgeTable());
    }

    /**
     * This returns the NodeTable, the HashMap that contains all of the
     * Node-data in the DataNetwork.
     *
     * @return HashMap NodeTable, the HashMap containing all of the Node-data.
     */
    public HashMap<String, NetworkElementApi> getNodeTable()
    {
        return (mDataNetwork.getNodeTable());
    }

    public DerivedData getDerivedData()
    {
        return (mDerivedData);
    }

    private void MatchNetworkCaseToNetworkElements()
    {
        this.MatchNetworkCaseToNetworkNode();
        this.MatchNetworkCaseToNetworkEdge();
    }

    private void MatchNetworkCaseToNetworkNode()
    {
        //Get all the keys from the NodeTable.
        Set<String> keys = mDataNetwork.getNodeTable().keySet();

        //For each key, we will convert the String list of ID's to NetworkCaseApi objects that they correspond to.
        for (int i = 0; i < mDataNetwork.getNodeTable().size(); i++)
        {

            NetworkElementApi currentNode = mDataNetwork.getNodeTable().get(keys.toArray()[i]);
            //We have a number of Cases per Node, and we need to add them all.
            //So let's iterate throught them.
            for (int j = 0; j < currentNode.getCaseIdList().size(); j++)
            {
                //Find each NetworkCaseApi via the String stored on the node.
                //And add it to the Node's NetworkCaseApi List.
                currentNode.addCase(mNetworkCaseSet.findCaseByCaseId(
                        currentNode.getCaseIdList().get(j)));
            }
        }
    }

    private void MatchNetworkCaseToNetworkEdge()
    {
        //Get all the keys from the NodeTable.
        Set<String> keys = mDataNetwork.getEdgeTable().keySet();

        //For each key, we will convert the String list of ID's to NetworkCaseApi objects that they correspond to.
        for (int i = 0; i < mDataNetwork.getEdgeTable().size(); i++)
        {

            NetworkElementApi currentEdge = mDataNetwork.getEdgeTable().get(keys.toArray()[i]);
            //We have a number of Cases per Node, and we need to add them all.
            //So let's iterate throught them.
            for (int j = 0; j < currentEdge.getCaseIdList().size(); j++)
            {
                //Find each NetworkCaseApi via the String stored on the node.
                //And add it to the Node's NetworkCaseApi List.
                currentEdge.addCase(mNetworkCaseSet.findCaseByCaseId(
                        currentEdge.getCaseIdList().get(j)));
            }
        }
    }
}
