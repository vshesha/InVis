package invis.gl.networkapi;

import java.beans.PropertyChangeListener;

/**
 *
 * @author Matt
 */
public interface NetworkInteractionApi
{

    public String getDisplayData();

    public NetworkElementApi getAction();

    public NetworkElementApi getPreState();

    public NetworkElementApi getPostState();

    public Integer getFileLineIndex();

    public Integer getInteractionCount();

    public void setActionPreCondition(String actionPreCondition);

    public String getActionPreCondition();

    public void setActionPostCondition(String actionPostCondition);

    public String getActionPostCondition();

    public void addPropertyChangeListener(PropertyChangeListener listener);

    public void removePropertyChangeListener(PropertyChangeListener listener);
}
