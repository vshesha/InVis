package invis.gl.NBNodeFactories.StepBasedFactories;

import invis.gl.NetworkClusterApi.NetworkClusterEdgeApi;
import invis.gl.NetworkClusterApi.NetworkClusterElementApi;
import invis.gl.NetworkClusterApi.NetworkClusterVertexApi;
import invis.gl.stepbasednodes.NetworkClusterElementSetNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 *
 * @author Matt
 */
public class StepBasedRootNodeFactory extends ChildFactory<String>
{

    private HashMap<String, NetworkClusterVertexApi> mNodeData;
    private HashMap<String, NetworkClusterElementApi> mEdgeData;

    public StepBasedRootNodeFactory(HashMap<String, NetworkClusterVertexApi> nodeData, HashMap<String, NetworkClusterElementApi> edgeData)
    {
        mNodeData = nodeData;
        mEdgeData = edgeData;
    }

    @Override
    protected boolean createKeys(List<String> list)
    {
        //For each collection of data, nodes and edges, we need to make a parent node.
        list.add("Vertices");
        list.add("Edges");
        return (true);
    }

    @Override
    public Node createNodeForKey(String data)
    {
        if (data.compareTo("Vertices") == 0)
        {
            List<NetworkClusterElementApi> list = new ArrayList<NetworkClusterElementApi>(mNodeData.values());
            NetworkClusterElementSetNode node = new NetworkClusterElementSetNode(Children.LEAF, list, "Vertices");
            return (node);
        }
        if (data.compareTo("Edges") == 0)
        {
            List<NetworkClusterElementApi> list = new ArrayList<NetworkClusterElementApi>(mEdgeData.values());
            NetworkClusterElementSetNode node = new NetworkClusterElementSetNode(Children.LEAF, list, "Edges");
            return (node);
        }
        return null;
    }
}
