package invis.gl.Transformers;

import invis.gl.networkapi.NetworkElementApi;
import java.util.HashMap;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author Matt
 */
public class EdgeLabelTransformer implements Transformer<String, String>
{

    private HashMap<String, NetworkElementApi> mEdgeDataTable;
    private final boolean mHasActionLabel;
    private boolean mLabelTitle;
    private boolean mParametersFlag;
    private boolean mActionDescriptionFlag;
    private boolean mUniqueFrequencyFlag;
    private boolean mFrequencyFlag;
    private boolean mBetweennessFlag;

    // It is necessary to provide our hashTable to this function so that we can access our class specific data.
    public EdgeLabelTransformer(HashMap<String, NetworkElementApi> edgeDataTable, boolean hasActionLabel)
    {
        mEdgeDataTable = edgeDataTable;
        mHasActionLabel = hasActionLabel;
        mLabelTitle = true;
        mParametersFlag = true;
        mActionDescriptionFlag = true;
        mUniqueFrequencyFlag = true;
        mFrequencyFlag = true;
        mBetweennessFlag = true;
    }

    // Note here we are accessing the nodeTable to get our data and not just the node object, this is because our
    // specific data is contained within our hashTable, and not the node object used by Jung. The node object in the
    // Jung graph is merely a String, but that String is a key to our hashTable which contains all of our detailed
    // information.
    // Also note that we use html tags, this is a nice feature of the Jung library that lets you make use of html
    // within the vertex label, enabling a wider range of formatting functionality.
    /*
     public String transform(String s)
     {
     return ("<html>NodeLabeller:<br>"+mNodeDataTable.get(s).getID()+"<br>"+mNodeDataTable.get(s).getColor()+"</html>");
     }
     */
    public void setLabelTitle(boolean value)
    {
        this.mLabelTitle = value;
    }

    public void setActionDescriptionFlag(boolean value)
    {
        this.mActionDescriptionFlag = value;
    }

    public void setUniqueFrequencyFlag(boolean value)
    {
        this.mUniqueFrequencyFlag = value;
    }

    public void setFrequencyFlag(boolean value)
    {
        this.mFrequencyFlag = value;
    }

    public void setParametersFlag(boolean value)
    {
        this.mParametersFlag = value;
    }

    public void setBetweennessFlag(boolean value)
    {
        this.mBetweennessFlag = value;
    }
    
    // A second option that exists is to use the toString() method defined by the NetworkNodeModel object, rather than  one defined
    // by the nodeLabeller. This code is provided here, and in NetworkNodeModel. The advantage is that only one call is necessary to
    // to the hashTable, rather than multiple calls, however both are sufficiently fast and the difference is not likely noticed
    // in practice.

    @Override
    public String transform(String s)
    {

        if (mEdgeDataTable.containsKey(s))
        {
            if (mEdgeDataTable.get(s).getLabelVisibility())
            {
                if (mHasActionLabel)
                {
                    return (mEdgeDataTable.get(s).getElementLabel());
                } else
                {
                    StringBuilder Label = new StringBuilder();
                    Label.append("<html>");

                    if (mLabelTitle)
                    {
                        Label.append("Edge Data:<br>");
                    }
                    if (mParametersFlag)
                    {
                        Label.append("Pre-Condition: ").append(mEdgeDataTable.get(s).getPreCondition()).append("<br>");
                        
                        //TODO: This needs to show the most frequent PostCondition in the set from the MultiMap.
                        //Label.append("Post-Condition: ").append(mEdgeDataTable.get(s).getPostCondition()).append("<br>");
                    }

                    if (mActionDescriptionFlag)
                    {
                        Label.append(mEdgeDataTable.get(s).getValue()).append("<br>");
                    }
                    if (mBetweennessFlag)
                    {
                        Label.append("Betweenness: ").append(mEdgeDataTable.get(s).getBetweennessValue()).append("<br>");
                    }
                    if (mFrequencyFlag)
                    {
                        Label.append("Case Freq: ").append(mEdgeDataTable.get(s).getCaseFrequency()).append("<br>");
                    }
                    if (mUniqueFrequencyFlag)
                    {
                        Label.append("Unique Freq: ").append(mEdgeDataTable.get(s).getUniqueFrequency()).append("<br>");
                    }

                    Label.append("</html>");
                    return (Label.toString());
                }
            } else
            {
                return "";
            }
        } else
        {
            return "";
        }
    }
}
