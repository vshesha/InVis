package invis.gl.networkapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author Matt
 */
public interface NetworkCaseSetApi
{

    public Integer getNetworkCaseSetSize();

    public ArrayList<NetworkCaseApi> getNetworkCaseList();
    
    public void setNetworkCaseList(List<NetworkCaseApi> list);

    public String getCaseSetName();
    
    public NetworkCaseApi findCaseByCaseId(String caseId);            

    public void setCaseSetName(String CaseSetName);

    public void setDataContainsCaseGroupIds(boolean value);
    
    public int getUniqueCaseGroupIdCount();
    
    public HashSet<String> getUniqueCaseGroupIdSet();
    
    public String getUniqueCaseGroupIdAsString();
    
    public String getCaseGroupIdFrequencyMapAsString();
    
    public HashMap<String, Integer> getCaseGroupIdFrequencyMap();

    public String CasesToString();
}
