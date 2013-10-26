package invis.gl.api;

/**
 *
 * @author Matt
 */
public interface RawInteractionApi
{

    public String getAction();

    public Integer getInteractionCount();

    public Integer getFileLineIndex();

    public String getPostState();

    public String getPreState();

    public boolean getErrorValue();

    public boolean getGoalValue();

    public void setErrorValue(boolean value);

    public void setGoalValue(boolean value);

    public void updatePostStateGoalValue(boolean goalValue);

    @Override
    public String toString();

    public void setPostStateLabel(String stateLabel);

    public String getPostStateLabel();

    public void setActionLabel(String actionLabel);

    public String getActionLabel();

    public void setActionPreCondition(String actionPreCondition);

    public String getActionPreCondition();

    public void setActionPostCondition(String actionPostCondition);

    public String getActionPostCondition();
}
