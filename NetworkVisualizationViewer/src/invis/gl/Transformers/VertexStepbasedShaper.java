package invis.gl.Transformers;

import edu.uci.ics.jung.visualization.decorators.EllipseVertexShapeTransformer;
import invis.gl.NetworkClusterApi.NetworkClusterVertexApi;
import java.awt.Shape;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Matt
 */
public class VertexStepbasedShaper extends EllipseVertexShapeTransformer<String>
{

    private HashMap<String, NetworkClusterVertexApi> mNEDataTable;
    private HashMap<String, Double> mVertexFrequencyMap;
    private Double mMaxUniqueFrequency;
    private Double mSizeMultiplier;

    public VertexStepbasedShaper(HashMap<String, NetworkClusterVertexApi> dataTable)
    {
        mNEDataTable = dataTable;
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
    public Shape transform(String v)
    {
        /*
         SelectedTable = new HashMap<String, Boolean>();
         Set<String> keySet = mNEDataTable.keySet();
         for (int i = 0; i < keySet.size(); i++)
         {
         SelectedTable.put(keySet.toArray()[i].toString(), mNEDataTable.get(keySet.toArray()[i]).getSelected());
         }*/

        Double size = 40.0;
        double ratio = (mVertexFrequencyMap.get(v) / mMaxUniqueFrequency);
        size += ((ratio) * (240));

        if (mNEDataTable.get(v).getSelected())
        {
            size *= mSizeMultiplier;
            this.setSizeTransformer(new VertexSizeTransformer<String>(size.intValue()));
        } else
        {
            this.setSizeTransformer(new VertexSizeTransformer<String>(size.intValue()));
        }

        if (mNEDataTable.containsKey(v))
        {
            if (mNEDataTable.get(v).getContainsGoal())
            {
                return (factory.getRegularPolygon(v, 4));
            }
            if (mNEDataTable.get(v).getContainsError())
            {
                return (factory.getRegularPolygon(v, 8));
            }
        }
        return (factory.getEllipse(v));
    }
}
