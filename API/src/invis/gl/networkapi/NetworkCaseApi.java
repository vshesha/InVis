package invis.gl.networkapi;

import java.util.ArrayList;

/**
 *
 * @author Matt
 */
public interface NetworkCaseApi
{

    public String getCaseName();

    public ArrayList<NetworkInteractionApi> getInteractionsList();

    public Integer getInteractionCount();

    //public void addPropertyChangeListener(PropertyChangeListener listener);

    //public void removePropertyChangeListener(PropertyChangeListener listener);

    public String getCaseGroupID();

    void setCaseGroupID(String caseGroupID);
    
    public boolean getGoalCase();
    
    public void setGoalCase(boolean value);
}
