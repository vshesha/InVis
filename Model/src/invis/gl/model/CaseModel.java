package invis.gl.model;

import invis.gl.api.RawCaseModelApi;
import invis.gl.api.RawInteractionApi;
import java.util.ArrayList;

/**
 *
 * @author Matt
 */
//@ServiceProvider(service = CaseModel.class)
public class CaseModel implements RawCaseModelApi
{

    private ArrayList<RawInteractionApi> mInteractionList;
    private String mCaseName;
    private String mGroupID;

    public CaseModel()
    {
        this.mInteractionList = new ArrayList<RawInteractionApi>();
        this.mCaseName = "";
        this.mGroupID = "";
    }

    public CaseModel(String CaseName)
    {
        this.mInteractionList = new ArrayList<RawInteractionApi>();
        this.mCaseName = CaseName;
        this.mGroupID = "";
    }

    public CaseModel(ArrayList<RawInteractionApi> InteractionsList, String CaseName)
    {
        this.mInteractionList = InteractionsList;
        this.mCaseName = CaseName;
        this.mGroupID = "";
    }

    public Integer getInteractionCount()
    {
        return (mInteractionList.size());
    }

    /*@Override
     public String InteractionsToString()
     {
     StringBuilder propertyValue = new StringBuilder();
        
     //Offset by one, so the first interaciton reads 1 rather than 0.
     for (int i=1;i <= this.getInteractionsList().size();i++)
     {
     propertyValue.append(i+": "+this.getInteractionsList().get(i-1).ToString()+" \n");
     }
     return (propertyValue.toString());
     }*/
    @Override
    public String getCaseName()
    {
        return mCaseName;
    }

    public void setCaseName(String CaseName)
    {
        this.mCaseName = CaseName;
    }

    @Override
    public void setCaseGroupID(String groupID)
    {
        mGroupID = groupID;
    }

    @Override
    public String getCaseGroupID()
    {
        return (mGroupID);
    }

    @Override
    public ArrayList<RawInteractionApi> getInteractionsList()
    {
        return mInteractionList;
    }

    public void setInteractionsList(ArrayList<RawInteractionApi> InteractionsList)
    {
        this.mInteractionList = InteractionsList;
    }

    @Override
    public void addInteraction(RawInteractionApi interactionsModel)
    {
        mInteractionList.add(interactionsModel);
    }

    @Override
    public String ToString()
    {
        return (this.getCaseName());
    }
}
