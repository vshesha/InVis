package invis.gl.Transformers;

import invis.gl.networkapi.NetworkElementApi;
import java.util.HashMap;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author Matt
 */
@Deprecated // SEE: NodeLabelTransformer
public class nodeLabeller implements Transformer<String, String>
{

    private HashMap<String, NetworkElementApi> mNodeDataTable;
    private boolean mHasStateLabel;

    // It is necessary to provide our hashTable to this function so that we can access our class specific data.
    public nodeLabeller(HashMap<String, NetworkElementApi> nodeDataTable, boolean hasStateLabel)
    {
        mNodeDataTable = nodeDataTable;
        mHasStateLabel = hasStateLabel;
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
    // A second option that exists is to use the toString() method defined by the NetworkNodeModel object, rather than  one defined
    // by the nodeLabeller. This code is provided here, and in NetworkNodeModel. The advantage is that only one call is necessary to
    // to the hashTable, rather than multiple calls, however both are sufficiently fast and the difference is not likely noticed
    // in practice.
    @Override
    public String transform(String s)
    {
        if (mNodeDataTable.containsKey(s))
        {
            if (mNodeDataTable.get(s).getLabelVisibility())
            {
                if (mHasStateLabel)
                {
                    return (mNodeDataTable.get(s).getElementLabel());
                } else
                {
                    StringBuilder Label = new StringBuilder();
                    Label.append("<html>nodeData:<br> Desc: " + mNodeDataTable.get(s).getValue());
                    Label.append("<br>ID: " + mNodeDataTable.get(s).getInteractionIndex());
                    Label.append("<br>ClusterID: " + mNodeDataTable.get(s).getClusterID());
                    double val = mNodeDataTable.get(s).getMDPValue();
                    Label.append("<br>MDP-Score: ");

                    //Double.MIN_VALUE is the smallest positive non-zero value for type double.
                    if (val == Double.MIN_VALUE)
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
                    }

                    Label.append("</html>");
                    return (Label.toString());

                    //return (mNodeDataTable.get(s).toString());
                }
            } else
            {
                return "";
            }
        }else
        {
            return "";
        }
    }
}
