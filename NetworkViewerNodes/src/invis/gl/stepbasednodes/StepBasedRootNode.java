package invis.gl.stepbasednodes;

import invis.gl.networkapi.NetworkCaseSetApi;
import invis.gl.NetworkClusterApi.NetworkClusterEdgeApi;
import invis.gl.NetworkClusterApi.NetworkClusterElementApi;
import invis.gl.NetworkClusterApi.NetworkClusterVertexApi;
import java.util.HashMap;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author Matt
 */
public class StepBasedRootNode extends AbstractNode
{

    private NetworkCaseSetApi mData;
    private HashMap<String, NetworkClusterVertexApi> mNodeData;
    private HashMap<String, NetworkClusterElementApi> mEdgeData;

    public StepBasedRootNode(Children children, NetworkCaseSetApi data, 
            HashMap<String, NetworkClusterVertexApi> nodeData, 
            HashMap<String, NetworkClusterElementApi> edgeData)
    {
        super(children, Lookups.singleton(data));
        //this.setChildren(Children.create(new StepBasedRootNodeFactory(data), true));
        this.setChildren(children);
        this.setModel(data, nodeData, edgeData);
    }

    private void setModel(NetworkCaseSetApi data, HashMap<String, NetworkClusterVertexApi> nodeData, HashMap<String, NetworkClusterElementApi> edgeData)
    {
        mData = data;
        mNodeData = nodeData;
        mEdgeData = edgeData;
    }

    public NetworkCaseSetApi getData()
    {
        return (mData);
    }
    
    public  HashMap<String, NetworkClusterVertexApi> getNodeData()
    {
        return (mNodeData);
    }
    
    public  HashMap<String, NetworkClusterElementApi> getEdgeData()
    {
        return (mEdgeData);
    }
    

    //The True Root node won't have a property sheet I don't think.
    // The StepBasedVertex (Vertices [node]) will probably have something...
    // return the size of the mNetworkNodesList...?
    /*
    @Override
    protected Sheet createSheet()
    {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        String obj = getLookup().lookup(String.class); //This is the data class.
        try
        {
            Property<String> someProp = new PropertySupport.Reflection<String>(obj, String.class, mData.toString(), null);
            someProp.setName("HashMap->ToString()...");
            set.put(someProp);
        } catch (NoSuchMethodException ex)
        {
        }
        sheet.put(set);
        return (sheet);
    }*/


}
