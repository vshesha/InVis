package invis.gl.deriveddata;

import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import invis.gl.NetworkClusterApi.NetworkClusterEdgeApi;
import invis.gl.NetworkClusterApi.NetworkClusterElementApi;
import invis.gl.NetworkClusterApi.NetworkClusterVertexApi;
import invis.gl.clustermodel.NetworkClusterEdgeMixedModel;
import invis.gl.clustermodel.NetworkClusterEdgeModel;
import invis.gl.clustermodel.NetworkClusterVertexModel;
import invis.gl.dataprocessor.DataParser;
import invis.gl.networkapi.NetworkElementApi;
import invis.gl.networkapi.NetworkInteractionApi;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 *
 * @author Matt
 */
public class DerivedData implements Lookup.Provider
{

    private DataParser mSourceDataParser;
    private HashMap<String, NetworkClusterVertexApi> mNodeTable = new HashMap<String, NetworkClusterVertexApi>();
    private HashMap<String, NetworkClusterElementApi> mEdgeTable = new HashMap<String, NetworkClusterElementApi>();
    private DirectedSparseMultigraph<String, String> mGraph = new DirectedSparseMultigraph<String, String>();
    private Lookup mLookup;
    private InstanceContent mInstanceContent;

    public DerivedData(DataParser data)
    {
        mSourceDataParser = data;
        mInstanceContent = new InstanceContent();
        mLookup = new AbstractLookup(mInstanceContent);
    }

    public DataParser getSourceDataParser()
    {
        return mSourceDataParser;
    }

    public HashMap<String, NetworkClusterVertexApi> getNodeTable()
    {
        if (mNodeTable != null)
        {
            return (mNodeTable);
        } else
        {
            try
            {
                throw new Exception("Derived Data, mNodeTable is null.");
            } catch (Exception ex)
            {
                Logger.getLogger(DerivedData.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public HashMap<String, NetworkClusterElementApi> getEdgeTable()
    {
        return mEdgeTable;
    }

    public DirectedSparseMultigraph<String, String> getGraph()
    {
        return mGraph;
    }

    public void GenerateStepBasedGraphNodes()
    {
        Set<String> NetworkNodeKeys = mSourceDataParser.getNodeTable().keySet();

        //Build new nodes out of like PostConditions.
        for (int i = 0; i < NetworkNodeKeys.size(); i++)
        {
            //For each NetworkNode we get the set of postConditions.
            String nodeKey = NetworkNodeKeys.toArray()[i].toString();
            Set PostConditionKeys = mSourceDataParser.getNodeTable().get(nodeKey).getPostCondition().keySet();

            //For each PostCondition, we build a a node.
            for (int j = 0; j < PostConditionKeys.size(); j++)
            {
                String postCondKey = PostConditionKeys.toArray()[j].toString();

                //If the PostCondition Node already exists we add the latest NetworkNode.
                if (mNodeTable.containsKey(postCondKey))
                {
                    NetworkElementApi NetworkNode = mSourceDataParser.getNodeTable().get(nodeKey);
                    mNodeTable.get(postCondKey).AddNewNetworkElement(NetworkNode);
                } else //If it doesn't exist we create it.
                {
                    mNodeTable.put(postCondKey, new NetworkClusterVertexModel(postCondKey, mSourceDataParser.getNodeTable().get(nodeKey)));
                    mInstanceContent.add(mNodeTable.get(postCondKey));
                }
            }
        }
        try
        {
            GenerateStepBasedGraphEdges();
        } catch (Exception ex)
        {
            Logger.getLogger(DerivedData.class.getName()).log(Level.SEVERE, null, ex);
        }
        CreateGraphEdges();
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

    //
    /**
     * I can do this one of two ways. 1) Iterate through the interactions and
     * build the edges based on the postConditions of the states. 2) Iterate
     * through the mGraph edges of the SourceDataParser and build the edges out
     * of the postConditions of the states.
     *
     * This is based on method 1, using the interactionLists from the cases.
     *
     * @throws Exception if the postconditions are null.
     */
    private void GenerateStepBasedGraphEdges() throws Exception
    {
        for (int i = 0; i < mSourceDataParser.getNetworkCaseSetSize(); i++)
        {
            ArrayList<NetworkInteractionApi> interactionsList = mSourceDataParser.getNetworkCaseSet().getNetworkCaseList().get(i).getInteractionsList();

            //Notice we skip the first Interaction, the root node has no postCondition.
            for (int j = 1; j < interactionsList.size(); j++)
            {
                String PreviousInteractionPostCond = interactionsList.get(j - 1).getActionPostCondition();
                String CurrentInteractionPostCond = interactionsList.get(j).getActionPostCondition();
                String action = interactionsList.get(j).getAction().getSimpleValue();

                if (PreviousInteractionPostCond == null && CurrentInteractionPostCond == null)
                {
                    throw new Exception("Pre and Post conditions unexpectedly set to null, double check headers in input file: 'PRE_CONDITION' and 'POST_CONDITION'");
                }

                String EdgeKey = PreviousInteractionPostCond + " " + action + " " + CurrentInteractionPostCond;
                if (!mEdgeTable.containsKey(EdgeKey)) //if the edge does NOT exist, let's make it.
                {
                    mEdgeTable.put(EdgeKey, new NetworkClusterEdgeModel(
                            interactionsList.get(j).getAction(),
                            mNodeTable.get(PreviousInteractionPostCond),
                            mNodeTable.get(CurrentInteractionPostCond),
                            EdgeKey));
                } else
                {
                    //If it does exist, we add the original-edge from which it is derived.
                    mEdgeTable.get(EdgeKey).AddNewNetworkElement(interactionsList.get(j).getAction());
                }
            }
        }
    }

    private void CreateGraphEdges() //This also automatically generates the associated nodes.
    {
        Set<String> EdgeKeys = mEdgeTable.keySet();
        for (int i = 0; i < EdgeKeys.size(); i++)
        {
            String Edge = EdgeKeys.toArray()[i].toString();
            String Source = ((NetworkClusterEdgeApi)mEdgeTable.get(Edge)).getSource().getValue();
            String Target = ((NetworkClusterEdgeApi)mEdgeTable.get(Edge)).getTarget().getValue();

            mGraph.addEdge(Edge, Source, Target);
        }
    }

    public void BuildNewClusterNode(String newNodeID, Collection<String> combined)
    {
        Collection<NetworkElementApi> elements = new ArrayList<NetworkElementApi>();
        for (String NetworkElementKey : combined)
        {
            elements.add(mSourceDataParser.getNodeTable().get(NetworkElementKey));
        }
        mNodeTable.put(newNodeID, new NetworkClusterVertexModel(newNodeID, elements));
    }

    public void BuildNewClusterEdge(String newEdgeID, Collection<String> combined)
    {
        Collection<NetworkElementApi> elements = new ArrayList<NetworkElementApi>();
        for (String NetworkElementKey : combined)
        {
            elements.add(mSourceDataParser.getEdgeTable().get(NetworkElementKey));
        }
        mEdgeTable.put(newEdgeID, new NetworkClusterEdgeMixedModel(newEdgeID, elements));
    }
    
    public void BuildNewClusterEdge(String newEdgeID, LinkedList<String> combined)
    {
        Collection<NetworkElementApi> elements = new ArrayList<NetworkElementApi>();
        for (String NetworkElementKey : combined)
        {
            elements.add(mSourceDataParser.getEdgeTable().get(NetworkElementKey));
        }
        mEdgeTable.put(newEdgeID, new NetworkClusterEdgeMixedModel(newEdgeID, elements));
    }
}
