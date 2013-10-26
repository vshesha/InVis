package invis.gl.Transformers.MixedDisplay;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.decorators.EllipseVertexShapeTransformer;
import invis.gl.NetworkClusterApi.NetworkClusterElementApi;
import invis.gl.NetworkClusterApi.NetworkClusterVertexApi;
import invis.gl.Transformers.VertexSizeTransformer;
import invis.gl.networkapi.NetworkElementApi;
import java.awt.Shape;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Matt
 */
public class MixedVertexShaper<V> extends EllipseVertexShapeTransformer<V>
{

    private HashMap<String, NetworkClusterVertexApi> mNetworkClusterDataTable;
    private HashMap<String, NetworkElementApi> mNetworkElementDataTable;
    private HashMap<String, Double> mVertexFrequencyMap;
    private Double mMaxUniqueFrequency;
    private Double mSizeMultiplier;

    public MixedVertexShaper(HashMap<String, NetworkClusterVertexApi> NetworkClusterVertexTable, HashMap<String, NetworkElementApi> NetworkElementTable)
    {
        mNetworkClusterDataTable = NetworkClusterVertexTable;
        mNetworkElementDataTable = NetworkElementTable;
        mSizeMultiplier = 1.0;
    }

    public void UpdateSizeMultiplier(Double sizeMultiplier)
    {
        mSizeMultiplier = sizeMultiplier;
    }

    public void UpdateVertexFrequencyMap(HashMap<String, Double> uniqueFrequencyVertexWeightMap)
    {
        mVertexFrequencyMap = uniqueFrequencyVertexWeightMap;
        mMaxUniqueFrequency = this.findMaxFrequency();
    }

    private Double findMaxFrequency()
    {
        Double maxValue = Double.MIN_VALUE;
        for (Map.Entry<String, Double> entry : mVertexFrequencyMap.entrySet())
        {
            if (entry.getValue() > maxValue)
            {
                maxValue = entry.getValue();
            }
        }
        return (maxValue);
    }

    @Override
    public Shape transform(V v)
    {
        Double size = 40.0;
        //double ratio = (mVertexFrequencyMap.get(v) / mMaxUniqueFrequency);
        //size += ((ratio) * (240));

        if (v instanceof Graph)
        {
            return (factory.getRegularPolygon(v, 6));
            //return (factory.getEllipse(v));
        }
        if (mNetworkElementDataTable.containsKey(v)) //Means v is an instanceof NetworkElementApi
        {
            size = 50.0;
            if (mNetworkElementDataTable.get(v).getSelected())
            {
                size *= mSizeMultiplier;
            }
            this.setSizeTransformer(new VertexSizeTransformer<V>(size.intValue()));
            
            if (mNetworkElementDataTable.get(v).getErrorValue())
            {
                return (factory.getRegularPolygon(v, 8));
            }
            if (mNetworkElementDataTable.get(v).getGoalValue())
            {
                return (factory.getRegularPolygon(v, 4));
            }
            return (factory.getRegularPolygon(v, 50));

        }
        if (mNetworkClusterDataTable.containsKey(v)) // Means v instanceof NetworkClusterElementApi)
        {
            size = 80.0;
            if (mNetworkClusterDataTable.get(v).getSelected())
            {
                size *= mSizeMultiplier;
            }
            this.setSizeTransformer(new VertexSizeTransformer<V>(size.intValue()));
            return (factory.getRegularPolygon(v, 6));
        }

        //this.setSizeTransformer(new VertexSizeTransformer<String>(size.intValue()));

        /*if (mNetworkClusterDataTable.containsKey(v))
        {
            if (mNetworkClusterDataTable.get(v) instanceof NetworkClusterElementApi)
            {
                return (factory.getRegularPolygon(v, 6));
            }
            if (mNetworkClusterDataTable.get(v) instanceof NetworkElementApi)
            {
                return (factory.getRegularPolygon(v, 50));
            }
        }*/
        return (factory.getEllipse(v));
    }
}
