package Transformers;

import invis.gl.networkapi.NetworkElementApi;
import java.util.HashMap;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author Matt
 */
public class FrequencyTransformer implements Transformer<String, Number>
{
    HashMap<String, NetworkElementApi> mEdgeTable;
    
    public FrequencyTransformer(HashMap<String, NetworkElementApi> edgeTable)
    {
        mEdgeTable = edgeTable;
    }

    @Override
    public Number transform(String i)
    {
        Double val;
        val = mEdgeTable.get(i).getCaseFrequency().doubleValue();
        return(val);
    }
}
