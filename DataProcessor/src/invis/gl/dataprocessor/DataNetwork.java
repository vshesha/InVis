package invis.gl.dataprocessor;

import NetworkModels.NetworkEdgeModel;
import NetworkModels.NetworkNodeModel;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.visualization.LayeredIcon;
import invis.gl.api.CaseSetModelApi;
import invis.gl.networkapi.NetworkElementApi;
import invis.gl.networkapi.NetworkElementApi.NetworkElementType;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 *
 * @author Matt
 */
public class DataNetwork implements Lookup.Provider
{

    private HashMap<String, NetworkElementApi> mNodeTable = new HashMap<String, NetworkElementApi>();
    private HashMap<String, NetworkElementApi> mEdgeTable = new HashMap<String, NetworkElementApi>();
    private DirectedSparseMultigraph<String, String> mGraph;
    private DataParser mDataParser;
    private Lookup mLookup;
    private InstanceContent mInstanceContent;

    /**
     * Empty constructor for DataNetwork. Creates an empty Jung Graph,
     * DirectedSparseMultigraph of type <String, String>. Instantiates a new
     * InstanceContent for Lookup purposes. Creates a new AbstractLookup which
     * takes the InstanceContent as an argument. Also for Lookup purposes.
     */
    public DataNetwork()
    {
        mGraph = new DirectedSparseMultigraph<String, String>();
        mInstanceContent = new InstanceContent();
        mLookup = new AbstractLookup(mInstanceContent);
    }

    /**
     * Assigns mDataParser from the input data, data. Populates the data tables,
     * EdgeTable and NodeTable. Then populates the graph of the Network.
     *
     * @param data the input data, of type DataParser.
     */
    public void BuildData(DataParser data)
    {
        mDataParser = data;
        PopulateDataTables();
        PopulateGraph();
        //this.loadImage(mGraph.getVertices(), new HashMap<String,Icon>());
    }

    private void loadImage(Collection<String> vertices, Map<String, Icon> imageMap)
    {
        for (String v : vertices)
        {
            Icon icon =
                    new LayeredIcon(
                    new ImageIcon("iconOutput\\"
                    + v.toString() + ".png").getImage());
            imageMap.put(v, icon);
        }
    }

    /**
     * Returns the EdgeTable of type HashMap <String, NetworkElementApi>
     *
     * @return mEdgeTable, a HashMap<String, NetworkElementApi>.
     */
    public HashMap<String, NetworkElementApi> getEdgeTable()
    {
        return (mEdgeTable);
    }

    /**
     * Returns the NodeTable of type HashMap<String, NetworkElementApi>
     *
     * @return mNodeTable, a HashMap<String, NetworkElementApi>.
     */
    public HashMap<String, NetworkElementApi> getNodeTable()
    {
        return (mNodeTable);
    }

    /**
     * Returns the graph of Network.
     *
     * @return mGraph of type DirectedSparseMultigraph<String, String>
     */
    public DirectedSparseMultigraph<String, String> getGraph()
    {
        return (mGraph);
    }
    
    public void UpdateGraph(DirectedSparseMultigraph<String, String> graph)
    {
        mGraph = graph;
    }
    
    //<editor-fold defaultstate="collapsed" desc="comment">
    /*
     * private int BuildPreState(String PreState, int interactionIndex, int NodeID, String caseName, HashMap<String, String> NameStateHashMap)
     * {
     * //If the prestate doesn't exist build it.
     * 
     * if (!mNodeTable.containsKey(PreState))
     * {
     * mNodeTable.put(PreState, new NetworkNodeModel(
     * PreState, NodeID++, NetworkElementType.NODE,
     * caseName,
     * interactionIndex,
     * mDataParser.getOptionalDataItemsList()));
     * 
     * mInstanceContent.add(mNodeTable.get(PreState));
     * NameStateHashMap.put(caseName, PreState);
     * } else
     * {
     * //Check the last state of the current student. If the Last state of the current student
     * //is equal to the current Pre-State, it means the student is still in that state, so we
     * //should NOT add them as a case to the current Pre-State. This is done to avoid double
     * //counting cases.
     * if (!NameStateHashMap.get(caseName).equals(PreState))
     * {
     * mNodeTable.get(PreState).AddCaseById(caseName);
     * NameStateHashMap.put(caseName, PreState);
     * }
     * }
     * return (NodeID);
     * }*/
    //</editor-fold>

    private int BuildState(String State, int interactionIndex, int NodeID, String caseName, HashMap<String, String> NameStateHashMap)
    {
        if (!mNodeTable.containsKey(State))
        {
            mNodeTable.put(State, new NetworkNodeModel(
                    State, NodeID++, NetworkElementType.NODE,
                    caseName,
                    interactionIndex,
                    mDataParser.getOptionalDataItemsList()));

            mInstanceContent.add(mNodeTable.get(State));
            NameStateHashMap.put(caseName, State);
        } else
        {
            //Check the last state of the current student. If the Last state of the current student
            //is equal to the current Pre-State, it means the student is still in that state, so we
            //should NOT add them as a case to the current Pre-State. This is done to avoid double
            //counting cases.
            if (!NameStateHashMap.get(caseName).equals(State))
            {

                mNodeTable.get(State).AddCaseById(caseName);
                NameStateHashMap.put(caseName, State);
            }
        }
        return (NodeID);
    }

    private void BuildAction(String EdgeKey, String PreState, String Action, String PostState, Integer interactionIndex, String CaseName)
    {
        if (!mEdgeTable.containsKey(EdgeKey))
        {
            mEdgeTable.put(PreState + Action + PostState, new NetworkEdgeModel(
                    interactionIndex, Action, NetworkElementType.EDGE,
                    CaseName,
                    mNodeTable.get(PreState),
                    mNodeTable.get(PostState),
                    mDataParser.getOptionalDataItemsList()));
            mInstanceContent.add(mEdgeTable.get(EdgeKey));
        } else
        {
            mEdgeTable.get(EdgeKey).AddCaseById(CaseName);
        }
    }

    /**
     * This goes through the CaseSet from the DataParser and iterates through
     * all the interactions. For each interaction it builds a NetworkNodeModel
     * for PreStates. If the PreState already exists it adds the current Case to
     * the CaseList of that State. The same is done for PostStates. Lastly, this
     * is done for Actions, in which NetworkEdgeModels are instantiated.
     *
     * All of the newly created items are also added to the InstanceContent so
     * they can be accessed via Lookups.
     */
    private void PopulateDataTables()
    {

        //We use a hashmap for cases, to keep track of the last state the student was in.
        //When we look at a pre-state, we do not add the case, if it was equal to the student's
        //last post-state.
        HashMap<String, String> NameStateHashMap = new HashMap<String, String>();

        CaseSetModelApi CSM = mDataParser.getCaseSet();
        int NodeID = 0;
        for (int caseIndex = 0; caseIndex < CSM.getCaseList().size(); caseIndex++)
        {
            NameStateHashMap.put(CSM.getCaseList().get(caseIndex).getCaseName(), "");

            for (Integer interactionIndex = 0; interactionIndex < CSM.getCaseList().get(caseIndex).getInteractionsList().size(); interactionIndex++)
            {
                String caseName = CSM.getCaseList().get(caseIndex).getCaseName();

                String PreState = CSM.getCaseList().get(caseIndex).getInteractionsList().get(interactionIndex).getPreState();
                NodeID = BuildState(PreState, interactionIndex, NodeID, caseName, NameStateHashMap);

                //If the post-state doesn't exist, build it.
                String PostState = CSM.getCaseList().get(caseIndex).getInteractionsList().get(interactionIndex).getPostState();
                String PreCondition = CSM.getCaseList().get(caseIndex).getInteractionsList().get(interactionIndex).getActionPreCondition();
                String PostCondition = CSM.getCaseList().get(caseIndex).getInteractionsList().get(interactionIndex).getActionPostCondition();
                NodeID = BuildState(PostState, interactionIndex, NodeID, caseName, NameStateHashMap);



                //Build the Action
                String Action = CSM.getCaseList().get(caseIndex).getInteractionsList().get(interactionIndex).getAction();// + "-" +

                //CSM.getCaseList().get(caseIndex).getInteractionsList().get(interactionIndex).getFileLineIndex();
                //interactionIndex is the index of the current action for this particular Case.
                String EdgeKey = PreState + Action + PostState;
                BuildAction(EdgeKey, PreState, Action, PostState, interactionIndex, caseName);

                if (mDataParser.DataContainsParameters())
                {
                    this.ManagePrePostStateConditions(PostState, PreCondition, PostCondition, caseName);
                    this.ManagePrePostActionConditions(EdgeKey, PreCondition, PostCondition, caseName);
                }

                //There is a special case, that needs to be handled, aside from the NameStateHashMap.
                //If there was an error, we still need to add the case at least once.
                if (PreState.equals(PostState))
                {
                    mNodeTable.get(PreState).AddCaseById(CSM.getCaseList().get(caseIndex).getCaseName());
                }
            }
        }
    }

    private void ManagePrePostActionConditions(String action, String PreCondition, String PostCondition, String caseName)
    {
        mEdgeTable.get(action).AddPreCondition(PreCondition, caseName);
        mEdgeTable.get(action).AddPostCondition(PostCondition, caseName);
    }

    private void ManagePrePostStateConditions(String State, String PreCondition, String PostCondition, String caseName)
    {
        mNodeTable.get(State).AddPreCondition(PreCondition, caseName);
        mNodeTable.get(State).AddPostCondition(PostCondition, caseName);
    }

    /**
     * This function iterates through all of the cases, then their interactions
     * and grabs the PreStates, Actions, PostStates. The graph is made out of
     * Strings. By Adding edges, nodes are automatically generated when needed.
     */
    private void PopulateGraph()
    {
        CaseSetModelApi CSM = mDataParser.getCaseSet();
        for (int caseIndex = 0; caseIndex < CSM.getCaseList().size(); caseIndex++)
        {
            for (int interactionsIndex = 0; interactionsIndex < CSM.getCaseList().get(caseIndex).getInteractionsList().size(); interactionsIndex++)
            {
                String PreState = CSM.getCaseList().get(caseIndex).getInteractionsList().get(interactionsIndex).getPreState();
                String Action = CSM.getCaseList().get(caseIndex).getInteractionsList().get(interactionsIndex).getAction();
                String PostState = CSM.getCaseList().get(caseIndex).getInteractionsList().get(interactionsIndex).getPostState();

                mGraph.addEdge(PreState + Action + PostState, PreState, PostState);
            }
        }
    }

    /**
     * returns the lookup of the DataNetwork. Override because this implements
     * Lookup.Provider
     *
     * @return mLookup.
     */
    @Override
    public Lookup getLookup()
    {
        return (mLookup);
    }
}
