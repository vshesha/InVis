package invis.gl.entropy.transformers;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.decorators.EllipseVertexShapeTransformer;
import invis.gl.Transformers.VertexSizeTransformer;
import invis.gl.networkapi.NetworkElementApi;
import java.awt.Shape;
import java.util.HashMap;

/**
 *
 * @author Matt
 */
public class EntropyShapeTransformer<V> extends EllipseVertexShapeTransformer<V>
{

    private HashMap<V, NetworkElementApi> mNEDataTable;
    private HashMap<V, Integer> mSizeDataTable;
    private Double mSizeMultiplier;
    private Integer mMinSize, mMaxSize, mMinDataValue, mMaxDataValue;

    public EntropyShapeTransformer(HashMap<V, NetworkElementApi> dataTable, HashMap<V, Integer> goalCountDataTable)
    {
        mNEDataTable = dataTable;
        mSizeMultiplier = 1.0;
        mSizeDataTable = goalCountDataTable;
        mMinSize = 30;
        mMaxSize = 300;
        mMinDataValue = null;
        mMaxDataValue = null;
    }

    public void UpdateMinMaxDataValues(Integer min, Integer max)
    {
        mMinDataValue = min;
        mMaxDataValue = max;
    }

    public void UpdateSizeMultiplier(Double sizeMultiplier)
    {
        mSizeMultiplier = sizeMultiplier;
    }

    @Override
    public Shape transform(V v)
    {
        Integer inputGoalCount = mSizeDataTable.get(v);

        Integer DimensionRange = mMaxSize - mMinSize;
        Integer DataRange = mMaxDataValue - mMinDataValue;
        
        Double ratio = inputGoalCount.doubleValue() / DataRange.doubleValue();
        ratio = mMinSize + (ratio * DimensionRange);

        if (mNEDataTable.get(v).getSelected())
        {
            Double size = (mSizeMultiplier * ratio);
            this.setSizeTransformer(new VertexSizeTransformer<V>(size.intValue()));
        } else
        {
            this.setSizeTransformer(new VertexSizeTransformer<V>(ratio.intValue()));
        }
        if (mNEDataTable.containsKey(v))
        {
            if (mNEDataTable.get(v).getGoalValue())
            {
                return (factory.getRegularPolygon(v, 4));
            }
            if (mNEDataTable.get(v).getErrorValue())
            {
                return (factory.getRegularPolygon(v, 8));
            }
        }
        return (factory.getEllipse(v));
    }
}
