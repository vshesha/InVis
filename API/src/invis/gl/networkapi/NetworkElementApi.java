package invis.gl.networkapi;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import org.apache.commons.collections.map.MultiValueMap;

/**
 *
 * @author Matt
 */
public interface NetworkElementApi
{
    
    public void addCase(NetworkCaseApi _Case);
    
    public void removeCase(NetworkCaseApi _Case);
    
    public ArrayList<NetworkCaseApi> getCaseList();

    public Integer getUniqueFrequency();

    public Set<String> getUniqueCaseIdSet();

    public String getUniqueCaseIdSetAsString();

    public String getCaseIdListAsString();

    public void setBetweennessValue(double value);

    public double getBetweennessValue();

    public Integer getCaseFrequency();

    public void setMdpValue(Double value);

    public Double getMDPValue();

    public void addPropertyChangeListener(PropertyChangeListener listener);
    
    public void removePropertyChangeListener(PropertyChangeListener listener);
    
    public PropertyChangeListener[] getPropertyChangeListener();

    public String getValue();
    
    public String getSimpleValue();

    public Integer getInteractionIndex();

    public ArrayList<String> getCaseIdList();

    public NetworkElementType getNetworkElementType();

    public void AddCaseById(String caseName);

    public boolean getGoalValue();

    public boolean getErrorValue();
    
    public void setClusterID(int clusterID);

    public int getClusterID();

    public boolean getLabelVisibility();

    public void setLabelVisibility(boolean val);

    public ArrayList<String> getOptionalData();

    public void setErrorValue(boolean errorValue);

    public void setGoalValue(boolean goalValue);

    public void setSelected(boolean b);

    public boolean getSelected();

    public String getElementLabel();

    public void setElementLabel(String label);

    public void AddPreCondition(String preCondition, String Case);

    public HashMap<String, String> getPreCondition();

    public void AddPostCondition(String postCondition, String Case);
    
    public MultiValueMap getUniquePostCondition();

    //public HashMap<String, String> getPostCondition();
    
    public MultiValueMap getPostCondition();
    
    public String getPostConditionStringList();
    
    public String getPreConditionStringList();

    public void setGoalPathValue(boolean goalPathValue);
    
    public boolean getGoalPathValue();

    public void setGoalCaseCount(int goalCaseCount);
    
    public int getGoalCaseCount();

    public String getMostFrequentPostCondition();
    
    public String getMostFrequentUniquePostCondition();

    public enum NetworkElementType
    {

        NODE, EDGE;
    }


    @Override
    public String toString();
}
