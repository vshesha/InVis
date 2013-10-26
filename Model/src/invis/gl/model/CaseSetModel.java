package invis.gl.model;

import invis.gl.api.CaseSetModelApi;
import invis.gl.api.RawCaseModelApi;
import java.util.ArrayList;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Matt
 */
@ServiceProvider(service = CaseSetModel.class)

public class CaseSetModel implements CaseSetModelApi
{
    private ArrayList<RawCaseModelApi> mCaseList;
    private String mCaseSetName;

    public CaseSetModel()
    {
        this.mCaseList = new ArrayList<RawCaseModelApi>();
        this.mCaseSetName = "";
    }
    public CaseSetModel(ArrayList<RawCaseModelApi> CaseList)
    {
        this.mCaseList = CaseList;
        this.mCaseSetName = "";
    }
    public CaseSetModel(ArrayList<RawCaseModelApi> CaseList, String CaseSetName)
    {
        this.mCaseList = CaseList;
        this.mCaseSetName = CaseSetName;
    }
    
    @Override
    public Integer getCaseSetSize()
    {
        return (mCaseList.size());
    }
    @Override
    public ArrayList<RawCaseModelApi> getCaseList()
    {
        return mCaseList;
    }

    public void setCaseList(ArrayList<RawCaseModelApi> CaseList)
    {
        this.mCaseList = CaseList;
    }

    @Override
    public String getCaseSetName()
    {
        return mCaseSetName;
    }
    
    @Override
    public void setCaseSetName(String CaseSetName)
    {
        this.mCaseSetName = CaseSetName;
    }

    @Override
    public String CasesToString()
    {
        StringBuilder propertyValue = new StringBuilder();
        
        //Offset by one, so the first interaciton reads 1 rather than 0.
        for (int i=1;i <= this.getCaseList().size();i++)
        {
            propertyValue.append(i+": "+this.getCaseList().get(i-1).getCaseName()+" \n");
        }
        return (propertyValue.toString());
    }
}
