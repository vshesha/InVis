package invis.gl.api;

import java.util.ArrayList;

/**
 *
 * @author Matt
 */
public interface CaseSetModelApi
{

    public ArrayList<RawCaseModelApi> getCaseList();

    public String getCaseSetName();

    public void setCaseSetName(String CaseSetName);

    public Integer getCaseSetSize();
    //Methods for Property Sheets:

    public String CasesToString();
}
