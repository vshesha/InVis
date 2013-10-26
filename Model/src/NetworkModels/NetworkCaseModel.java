package NetworkModels;

import invis.gl.networkapi.NetworkCaseApi;
import invis.gl.networkapi.NetworkInteractionApi;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

/**
 *
 * @author Matt
 */
public class NetworkCaseModel implements NetworkCaseApi//, Comparable<NetworkCaseApi>
{
    private ArrayList<NetworkInteractionApi> mInteractionList;
    private String mCaseName;
    private PropertyChangeSupport mPCSupport;
    private String mCaseGroupID;
    
    private boolean mGoalCase;

    public NetworkCaseModel(String mCaseName)
    {
        this.mCaseName = mCaseName;
        this.mInteractionList = new ArrayList<NetworkInteractionApi>();
        mPCSupport = new PropertyChangeSupport(this);
    }
    
    /*public NetworkCaseModel(String caseName, ArrayList<NetworkInteractionApi> interactionList)
    {
        this.mCaseName = caseName;
        this.mInteractionList = interactionList;
    }*/
    
    // add interested listeners here
    
    /*@Override
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        mPCSupport.addPropertyChangeListener(listener);
    }*/
    // don't forget to remove them

    /*@Override
    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        mPCSupport.addPropertyChangeListener(listener);
    }*/
    
    @Override
    public void setGoalCase(boolean value)
    {
        mGoalCase = value;
    }
    
    @Override
    public boolean getGoalCase()
    {
        return (mGoalCase);
    }

    @Override
    public String getCaseName()
    {
        return (mCaseName);
    }

    public void setCaseName(String CaseName)
    {
        this.mCaseName = CaseName;
    }

    @Override
    public ArrayList<NetworkInteractionApi> getInteractionsList()
    {
        return mInteractionList;
    }

    public void setInteractionsList(ArrayList<NetworkInteractionApi> InteractionsList)
    {
        this.mInteractionList = InteractionsList;
    }

    public void addInteraction(NetworkInteractionApi interactionsModel)
    {
        mInteractionList.add(interactionsModel);
    }

    public String ToString()
    {
        return (this.getCaseName());
    }

    @Override
    public Integer getInteractionCount()
    {
        return (mInteractionList.size());
    }

    @Override
    public void setCaseGroupID(String caseGroupID)
    {
        mCaseGroupID = caseGroupID;
    }
    
    @Override
    public String getCaseGroupID()
    {
        return (mCaseGroupID);
    }

    /*
    @Override
    public int compareTo(NetworkCaseApi other)
    {

        if (this.getInteractionCount() > other.getInteractionCount())
        {
            return (1);
        }
        else if (this.getInteractionCount() < other.getInteractionCount())
        {
            return (-1);
        }
        else
        {
            return (0);
        }
    }*/

    
}
