package invis.gl.model;

import invis.gl.api.RawInteractionApi;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Matt
 */
/**
 * The RawInteractionModel is the base structure for how this program reads and
 * works with data. This data structure has a pre and post state, and the action
 * that connects them, much like an edge table of a graph. We also include the
 * line of the file and the number of interaction for the current case.
 */
@ServiceProvider(service = RawInteractionModel.class)
public class RawInteractionModel implements RawInteractionApi
{

    private String mPreState;
    private String mAction;
    private String mPostState;
    private Integer mFileLineIndex;
    private final Integer mInteractionCount;
    private boolean mErrorValue;
    private boolean mGoalValue;
    private String mPostStateLabel;
    private String mActionLabel;
    private String mActionPreCondition;
    private String mActionPostCondition;

    /**
     * An empty constructor.
     */
    public RawInteractionModel()
    {
        this.mAction = "";
        this.mFileLineIndex = -1;
        this.mInteractionCount = -1;
    }

    /**
     * The constructor for the RawInteractionModel.
     *
     * @param PreState - The state which the interaction starts.
     * @param Action - The action that was performed to result in the post state
     * @param PostState - The state in which interaction ends.
     * @param FileLineIndex - The line of the input file the interaction came
     * from.
     * @param InteractionCount - The ordinal number of the interaction for the
     * "case" that performed the interaction.
     */
    public RawInteractionModel(String PreState, String Action, String PostState,
            Integer FileLineIndex, int InteractionCount)
    {
        this.mPreState = PreState;
        this.mAction = Action;
        this.mPostState = PostState;
        this.mFileLineIndex = FileLineIndex;
        this.mInteractionCount = InteractionCount;
        this.mErrorValue = false;
        this.mGoalValue = false;
        this.mPostStateLabel = "";
        this.mActionLabel = "";

    }

    /**
     *
     * @param PreState - The state which the interaction starts.
     * @param Action - The action that was performed to result in the post state
     * @param PostState - The state in which interaction ends.
     * @param FileLineIndex - The line of the input file the interaction came
     * from.
     * @param InteractionCount - The ordinal number of the interaction for the
     * "case" that performed the interaction.
     * @param errorValue - The error value relates to the PostState of the
     * interaction. If the PostState is an error, this is true.
     * @param goalValue - The goal value relates to the PostState of the
     * interaction. If the PostState is the goal, this is true.
     */
    public RawInteractionModel(String PreState, String Action, String PostState,
            Integer FileLineIndex, int InteractionCount, boolean errorValue,
            boolean goalValue)
    {
        this.mPreState = PreState;
        this.mAction = Action;
        this.mPostState = PostState;
        this.mFileLineIndex = FileLineIndex;
        this.mInteractionCount = InteractionCount;
        this.mErrorValue = errorValue;
        this.mGoalValue = goalValue;
        this.mPostStateLabel = "";
        this.mActionLabel = "";
    }

    /**
     * The RawInteraction stores the post-State label, which is used gained from
     * the optional header item, STATE_LABEL.
     *
     * @param stateLabel the label to be placed on the post-state of this
     * interaction.
     */
    @Override
    public void setPostStateLabel(String stateLabel)
    {
        mPostStateLabel = stateLabel;
    }

    /**
     * This is the post-state label. Assigned by the optionalDataItem
     * STATE_LABEL.
     *
     * @return the post-state label of the interaction.
     */
    @Override
    public String getPostStateLabel()
    {
        return (mPostStateLabel);
    }

    @Override
    public void setActionLabel(String actionLabel)
    {
        mActionLabel = actionLabel;
    }

    @Override
    public String getActionLabel()
    {
        return (mActionLabel);
    }

    @Override
    public boolean getErrorValue()
    {
        return (mErrorValue);
    }

    @Override
    public boolean getGoalValue()
    {
        return (mGoalValue);
    }

    @Override
    public void setErrorValue(boolean value)
    {
        this.mErrorValue = value;
    }

    @Override
    public void setGoalValue(boolean value)
    {
        this.mGoalValue = value;
    }

    @Override
    public String toString()
    {
        return (getPreState() + "->" + getAction() + "->" + getPostState());
    }

    @Override
    public String getAction()
    {
        return mAction;
    }

    public void setAction(String Action)
    {
        this.mAction = Action;
    }

    @Override
    public Integer getInteractionCount()
    {
        return (mInteractionCount);
    }

    @Override
    public Integer getFileLineIndex()
    {
        return mFileLineIndex;
    }

    public void setFileLineIndex(Integer FileLineIndex)
    {
        this.mFileLineIndex = FileLineIndex;
    }

    @Override
    public String getPostState()
    {
        return mPostState;
    }
    
    @Override
    public void updatePostStateGoalValue(boolean goalValue)
    {
        if (goalValue)
        {
            mPostState = mPostState.concat("_G_");
        }
    }

    public void setPostState(String PostState)
    {
        this.mPostState = PostState;
    }

    @Override
    public String getPreState()
    {
        return mPreState;
    }

    public void setPreState(String PreState)
    {
        this.mPreState = PreState;
    }

    @Override
    public void setActionPostCondition(String postcondition)
    {
        mActionPostCondition = postcondition;
    }

    @Override
    public String getActionPostCondition()
    {
        return (mActionPostCondition);
    }

    @Override
    public void setActionPreCondition(String precondition)
    {
        mActionPreCondition = precondition;
    }

    @Override
    public String getActionPreCondition()
    {
        return (mActionPreCondition);
    }
}
