package invis.gl.NetworkViewerNodes;

import invis.gl.networkapi.NetworkElementApi;
import invis.gl.networkapi.NetworkInteractionApi;
import invis.gl.NBNodeFactories.NetworkFactories.NetworkInteractionElementNodeFactory;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import org.openide.nodes.*;
import org.openide.util.lookup.Lookups;
import org.openide.windows.WindowManager;

/**
 *
 * @author Matt
 */
public class NetworkInteractionsNode extends AbstractNode implements PropertyChangeListener
{

    private NetworkInteractionApi mNIA;

    public NetworkInteractionsNode(Children children, NetworkInteractionApi data)
    {
        super(children, Lookups.singleton(data));
        //this.setChildren(Children.create(new NetworkInteractionElementNodeFactory(data), true));
        NetworkInteractionElementNodeFactory NIENF = new NetworkInteractionElementNodeFactory(data);

        List<NetworkElementApi> list = new ArrayList<NetworkElementApi>();

        list.add(data.getPreState());
        list.add(data.getAction());
        list.add(data.getPostState());
        NIENF.createKeys(list);
        for (int i = 0; i < list.size(); i++)
        {
            NIENF.createNodeForKey(list.get(i));
        }
        this.setData(data);
        this.setDisplayName(mNIA.getDisplayData());
        this.AddModelListeners();
    }

    private void AddModelListeners()
    {
        mNIA.addPropertyChangeListener(this);
        mNIA.getPreState().addPropertyChangeListener(this);
        mNIA.getAction().addPropertyChangeListener(this);
        mNIA.getPostState().addPropertyChangeListener(this);
    }

    private void setData(NetworkInteractionApi data)
    {
        this.mNIA = data;
    }

    public NetworkInteractionApi getData()
    {
        
        return (mNIA);
    }

    @Override
    protected Sheet createSheet()
    {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        NetworkInteractionApi obj = getLookup().lookup(NetworkInteractionApi.class);
        try
        {
            Property<NetworkElementApi> preStateProp = new PropertySupport.Reflection<NetworkElementApi>(obj, NetworkElementApi.class, "getPreState", null);
            Property<NetworkElementApi> actionProp = new PropertySupport.Reflection<NetworkElementApi>(obj, NetworkElementApi.class, "getAction", null);
            Property<NetworkElementApi> postStateProp = new PropertySupport.Reflection<NetworkElementApi>(obj, NetworkElementApi.class, "getPostState", null);
            Property<Integer> fileLineProp = new PropertySupport.Reflection<Integer>(obj, Integer.class, "getFileLineIndex", null);
            Property<Integer> interactionCountProp = new PropertySupport.Reflection<Integer>(obj, Integer.class, "getInteractionCount", null);
            
            Property<String> PostConditionProp = new PropertySupport.Reflection<String>(obj, String.class, "getActionPostCondition", null);

            preStateProp.setName("Pre-State");
            actionProp.setName("Action");
            postStateProp.setName("Post-State");
            fileLineProp.setName("Input File Line");
            interactionCountProp.setName("Interaction #");
            PostConditionProp.setName("Action Post Condition");
            

            set.put(preStateProp);
            set.put(actionProp);
            set.put(postStateProp);
            set.put(fileLineProp);
            set.put(interactionCountProp);
            set.put(PostConditionProp);

        } catch (NoSuchMethodException ex)
        {
        }
        sheet.put(set);
        return (sheet);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        if ("Selected".equals(evt.getPropertyName()))
        {
            if (this.mNIA.getPreState().getSelected() && this.mNIA.getAction().getSelected() && this.mNIA.getPostState().getSelected())
            {
                ArrayList<Node> activeNodes = new ArrayList<Node>();
                activeNodes.add(this);
                WindowManager.getDefault().findTopComponent("NetworkViewerTopComponent").setActivatedNodes(
                        activeNodes.toArray(new Node[activeNodes.size()]));
                WindowManager.getDefault().findTopComponent("SequenceViewTopComponent").setActivatedNodes(
                        activeNodes.toArray(new Node[activeNodes.size()]));
            }
        }
    }
}
