package NetworkModels;

import invis.gl.api.RawInteractionApi;
import invis.gl.networkapi.NetworkElementApi;
import invis.gl.networkapi.NetworkInteractionApi;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;

/**
 * This is the NetworkInteractionModel, which is a more complex and specific
 * model of the data which correlates to the RawInteractionModel. This item is
 * used in the NetworkViewer and NetworkDisplay modules.
 *
 * @author Matt
 */
public class NetworkInteractionModel implements NetworkInteractionApi
{

    private NetworkElementApi mPreState;
    private NetworkElementApi mAction;
    private NetworkElementApi mPostState;
    private Integer mFileLineIndex;
    private Integer mInteractionCount;
    private PropertyChangeSupport mPCSupport;
    private String mActionPreCondition;
    private String mActionPostCondition;

    /**
     *
     * @param inputRI This is the input RawInteractionApi object. We read in
     * this simple data in the data-processor package. We translate that data
     * into our more specific and complex data structure here.
     * @param caseName This is the name of the case that performed the
     * interaction.
     * @param nodeTable The HashMap that contains the NetworkElementApi objects
     * for states.
     * @param edgeTable The HashMap that contains the NetworkElementApi objects
     * for actions.
     */
    NetworkInteractionModel(RawInteractionApi inputRI, String caseName, HashMap<String, NetworkElementApi> nodeTable, HashMap<String, NetworkElementApi> edgeTable)
    {
        mPreState = nodeTable.get(inputRI.getPreState());
        mAction = edgeTable.get(inputRI.getPreState() + inputRI.getAction() + inputRI.getPostState());
        mPostState = nodeTable.get(inputRI.getPostState());
        this.mFileLineIndex = inputRI.getFileLineIndex();
        this.mInteractionCount = inputRI.getInteractionCount();

        mPCSupport = new PropertyChangeSupport(this);

        //The error and goal values are from the RawInteractionApi. So we get
        // those values and add associate those values with the NetworkInteractionModel.
        nodeTable.get(inputRI.getPostState()).setErrorValue(inputRI.getErrorValue());
        nodeTable.get(inputRI.getPostState()).setGoalValue(inputRI.getGoalValue());

        mPostState.setElementLabel(inputRI.getPostStateLabel());

        //mPostState.setPreCondition(inputRI.getActionPreCondition());
        //mPostState.setPostCondition(inputRI.getActionPostCondition());

        //We apply the same attributes to the preceding action.
        mAction.setErrorValue(inputRI.getErrorValue());
        edgeTable.get(inputRI.getPreState() + inputRI.getAction() + inputRI.getPostState()).setErrorValue(inputRI.getErrorValue());
        mAction.setGoalValue(inputRI.getGoalValue());

        //edgeTable.get(inputRI.getAction()).setGoalValue(inputRI.getGoalValue());
        mAction.setElementLabel(inputRI.getActionLabel());
        //mAction.setPreCondition(inputRI.getActionPreCondition());
        //mAction.setPostCondition(inputRI.getActionPostCondition());
        
        this.setActionPreCondition(inputRI.getActionPreCondition());
        this.setActionPostCondition(inputRI.getActionPostCondition());
    }

    //THIS ADDS SUPPORT SO THAT UNIQUESET CALL IN DATA PARSER CAN RETURN THE RIGHT SEQUENCES
    @Override
    public String toString()
    {
        return mPreState.getSimpleValue() + mAction.getSimpleValue() + mPostState.getSimpleValue();
    }

    @Override
    public void setActionPreCondition(String actionPreCondition)
    {
        mActionPreCondition = actionPreCondition;
    }

    @Override
    public String getActionPreCondition()
    {
        return (mActionPreCondition);
    }

    @Override
    public void setActionPostCondition(String actionPostCondition)
    {
        mActionPostCondition = actionPostCondition;
    }

    @Override
    public String getActionPostCondition()
    {
        if (mActionPostCondition != null)
        {
            return (mActionPostCondition);
        }
        else
        {
            return ("null");
        }
    }

    @Override
    public int hashCode()
    {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object other)
    {
        if (other != null)
        {
            if (this.toString().equals(other.toString()))
            {
                return true;
            }
        }
        return false;
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
        mPCSupport.addPropertyChangeListener(listener);
    }

    //TODO: An option should allow the user to switch between this and the next getDisplayData().
    /*@Override
    public String getDisplayData()
    {
        return (mPreState.getValue() + " - " + mAction.getValue() + " - " + mPostState.getValue());
    }*/
    
    @Override
    public String getDisplayData()
    {
        return (mAction.getPreConditionStringList() + " - " + mAction.getSimpleValue() + " - " + mAction.getPostConditionStringList());
    }

    @Override
    public NetworkElementApi getAction()
    {
        return mAction;
    }

    public void setAction(NetworkElementApi action)
    {
        this.mAction = action;
    }

    @Override
    public Integer getFileLineIndex()
    {
        return mFileLineIndex;
    }

    public void setFileLineIndex(Integer fileLineIndex)
    {
        this.mFileLineIndex = fileLineIndex;
    }

    @Override
    public NetworkElementApi getPostState()
    {
        return mPostState;
    }

    public void setPostState(NetworkElementApi postState)
    {
        this.mPostState = postState;
    }

    @Override
    public NetworkElementApi getPreState()
    {
        return mPreState;
    }

    public void setPreState(NetworkElementApi preState)
    {
        this.mPreState = preState;
    }

    @Override
    public Integer getInteractionCount()
    {
        return mInteractionCount;
    }

    public void setInteractionCount(Integer interactionCount)
    {
        this.mInteractionCount = interactionCount;
    }
}
