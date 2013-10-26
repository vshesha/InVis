package invis.gl.Transformers;

import invis.gl.NetworkClusterApi.NetworkClusterEdgeApi;
import invis.gl.NetworkClusterApi.NetworkClusterElementApi;
import java.util.HashMap;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author Matt
 */
public class StepBasedEdgeLabelTransformer implements Transformer<String, String>
{

    private HashMap<String, NetworkClusterElementApi> mEdgeDataTable;

    public StepBasedEdgeLabelTransformer(HashMap<String, NetworkClusterElementApi> edgeDataTable)
    {
        mEdgeDataTable = edgeDataTable;
    }

    @Override
    public String transform(String s)
    {
        if (mEdgeDataTable.containsKey(s))
        {
            if (mEdgeDataTable.get(s) instanceof NetworkClusterEdgeApi)
            {
                String output = ((NetworkClusterEdgeApi) mEdgeDataTable.get(s)).getAction();
                return (output);
            }
            else
            {
                return "NetworkClusterEdgeMixedModel";
            }
        } else
        {
            return ("");
        }
    }
}
