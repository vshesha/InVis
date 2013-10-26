package invis.gl.api;

import java.util.ArrayList;

/**
 *
 * @author Matt
 */
public interface RawCaseModelApi
{

    public String getCaseName();

    public ArrayList<RawInteractionApi> getInteractionsList();

    public void addInteraction(RawInteractionApi interactionsModel);

    public String ToString();

    public void setCaseGroupID(String GroupID);

    public String getCaseGroupID();
}
