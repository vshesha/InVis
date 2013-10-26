package invis.gl.Transformers;

import edu.uci.ics.jung.graph.Graph;
import invis.gl.networkapi.NetworkElementApi;
import java.util.HashMap;
import org.apache.commons.collections15.Transformer;



/**
 *
 * @author Matt
 */
public class NodeLabelTransformer<V> implements Transformer<V, String>
{

    private HashMap<String, NetworkElementApi> mNodeDataTable;
    private boolean mHasStateLabel;
    private boolean mStateDescriptionFlag;
    private boolean mParametersFlag;
    private boolean mClusterIDFlag;
    private boolean mMDPValueFlag;
    private boolean mLabelTitle;
    private boolean mInteractionIndexFlag;

    // It is necessary to provide our hashTable to this function so that we can access our class specific data.
    public NodeLabelTransformer(HashMap<String, NetworkElementApi> nodeDataTable, boolean hasStateLabel)
    {
        mNodeDataTable = nodeDataTable;
        mHasStateLabel = hasStateLabel;
        mStateDescriptionFlag = true;
        mParametersFlag = true;
        mClusterIDFlag = true;
        mMDPValueFlag = true;
        mLabelTitle = true;
        mInteractionIndexFlag = true;
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
    public void setStateDescriptionFlag(boolean value)
    {
        this.mStateDescriptionFlag = value;
    }

    public void setParametersFlag(boolean value)
    {
        this.mParametersFlag = value;
    }

    public void setClusterIDFlag(boolean value)
    {
        this.mClusterIDFlag = value;
    }

    public void setMDPValueFlag(boolean value)
    {
        this.mMDPValueFlag = value;
    }

    public void setLabelTitleFlag(boolean value)
    {
        this.mLabelTitle = value;
    }

    public String x()
    {
        StringBuilder x = new StringBuilder();
         x.append("asdf");
         return (x.toString());
         
    }
    public void setInteractionIndexFlag(boolean value)
    {
        this.mInteractionIndexFlag = value;
    }

    // A second option that exists is to use the toString() method defined by the NetworkNodeModel object, rather than  one defined
    // by the nodeLabeller. This code is provided here, and in NetworkNodeModel. The advantage is that only one call is necessary to
    // to the hashTable, rather than multiple calls, however both are sufficiently fast and the difference is not likely noticed
    // in practice.

    @Override
    public String transform(V s)
    {
        //This is used when a set of nodes is collapsed into a graph.
        if (s instanceof Graph)
        {
            Integer size;
            size = ((Graph) s).getVertexCount();
            StringBuilder label;
            label = new StringBuilder();
            label.append("Collapsed Graph: ");
            label.append(size);
            return (label.toString());
        } else
        {
            if (mNodeDataTable.containsKey(s))
            {
                if (mNodeDataTable.get(s).getLabelVisibility())
                {
                    if (mHasStateLabel)
                    {
                        return ((String)mNodeDataTable.get(s).getElementLabel().toString());
                    } else
                    {
                        StringBuilder Label = new StringBuilder();
                        Label.append("<html>");

                        if (mLabelTitle)
                        {
                            Label.append("Node Data:<br>");
                        }

                        //TODO: Need to make a data-properties object and then make some
                        // simple checks for the types of data that are stored in the input file.
                        //That way we can check these various flags better, like mHasStateLabel,
                        // or Parameters, etc.

                        if (mParametersFlag)
                        {
                            //Label.append(mNodeDataTable.get(s).getPostConditionStringList()).append("<br>");
                            Label.append(mNodeDataTable.get(s).getMostFrequentPostCondition().toString()).append("<br>");
                            //Label.append("Pre-Condition: ").append(mNodeDataTable.get(s).getPreCondition()).append("<br>");
                            //Label.append("Post-Condition: ").append(mNodeDataTable.get(s).getPostCondition()).append("<br>");
                        }
                        if (mStateDescriptionFlag)
                        {
                            Label.append("Desc: ").append(mNodeDataTable.get(s).getValue()).append("<br>");
                        }

                        if (mInteractionIndexFlag)
                        {
                            Label.append("Interaction Index: ").append(mNodeDataTable.get(s).getInteractionIndex()).append("<br>");
                        }
                        if (mClusterIDFlag)
                        {
                            Label.append("ClusterID: ").append(mNodeDataTable.get(s).getClusterID()).append("<br>");
                        }

                        if (mMDPValueFlag)
                        {
                            Label.append("MDP-Score: ").append(mNodeDataTable.get(s).getMDPValue()).append("<br>");
                        }
                        //double val = mNodeDataTable.get(s).getMDPValue();
                        //Double.MIN_VALUE is the smallest positive non-zero value for type double.
                /*if (val == Double.MIN_VALUE)
                         {
                         if (mNodeDataTable.get(s).getGoalValue() == true)
                         {
                         Label.append("+INF-Goal");
                         } else
                         {
                         Label.append("-INF");
                         }
                         } else
                         {
                         Label.append(mNodeDataTable.get(s).getMDPValue());
                         }*/

                        Label.append("</html>");
                        return (Label.toString());

                        //return (mNodeDataTable.get(s).toString());
                    }
                } else
                {
                    return ("");
                }
            }
        }
        return ("");
    }
}
