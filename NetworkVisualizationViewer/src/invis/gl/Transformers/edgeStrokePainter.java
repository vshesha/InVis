package invis.gl.Transformers;

import invis.gl.graphvisualapi.NetworkDisplayApi.EdgeWeightType;
import java.awt.BasicStroke;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author Matt
 */
public class edgeStrokePainter<E> implements Transformer<E, Stroke>
{

    /**
     * A hash Map for associating edges with a hash-number.
     */
    protected Map<E, Number> mCaseFrequencyEdgeWeight;
    protected Map<E, Number> mUniqueCaseFrequencyEdgeWeight;
    protected Map<E, Number> mNetworkElementsEdgeWeight;
    private Map<E, Number> mActiveEdgeWeightMap;
    private boolean mNormalized;
    private int mMaxValue = 0;

    /**
     * The constructor for the EdgeWeightStrokeFunction.
     *
     * @param edgeWeight a map between the Edges and a number for the
     * hash-table.
     */
    public edgeStrokePainter(Map<E, Number> caseFrequencyEdgeWeight, Map<E, Number> uniqueCaseFrequencyEdgeWeight)
    {
        this(caseFrequencyEdgeWeight, uniqueCaseFrequencyEdgeWeight, false);
    }

    public edgeStrokePainter(Map<E, Number> caseFrequencyEdgeWeight, Map<E, Number> uniqueCaseFrequencyEdgeWeight, boolean normalized)
    {
        this(caseFrequencyEdgeWeight, uniqueCaseFrequencyEdgeWeight, new HashMap<E, Number>(), false);
    }

    public edgeStrokePainter(Map<E, Number> caseFrequencyEdgeWeight, Map<E, Number> uniqueCaseFrequencyEdgeWeight, Map<E, Number> NetworkElementsEdgeWeight, boolean normalized)
    {

        this.mCaseFrequencyEdgeWeight = caseFrequencyEdgeWeight;
        mUniqueCaseFrequencyEdgeWeight = uniqueCaseFrequencyEdgeWeight;
        mNetworkElementsEdgeWeight = NetworkElementsEdgeWeight;
        mActiveEdgeWeightMap = this.mCaseFrequencyEdgeWeight;
        mNormalized = normalized;

    }

    public void setNormalized(boolean value)
    {
        mNormalized = value;

        Collection<Number> values = mActiveEdgeWeightMap.values();
        List<Integer> va = new ArrayList(values);
        Collections.sort(va);
        mMaxValue = va.get(va.size() - 1);


    }

    public void setActiveEdgeWeightMap(EdgeWeightType type)
    {
        if (EdgeWeightType.CASEFREQUENCY == type)
        {
            mActiveEdgeWeightMap = mCaseFrequencyEdgeWeight;
        }
        if (EdgeWeightType.UNIQUECASEFREQUENCY == type)
        {
            mActiveEdgeWeightMap = mUniqueCaseFrequencyEdgeWeight;
        }
        if (EdgeWeightType.NETWORKELEMENTS == type)
        {
            mActiveEdgeWeightMap = mNetworkElementsEdgeWeight;
        }
    }

    /**
     * Determine the look of the edge, pass the edge itself, and determine the
     * stroke of that edge.
     *
     * @param e the Edge to determine the stroke of.
     * @return Stroke - the appearance of the edge.
     */
    @Override
    public Stroke transform(E e)
    {
        int edgeWidth = 3;
        if (mActiveEdgeWeightMap.containsKey(e))
        {
            if (mNormalized)
            {

                edgeWidth = (int) mActiveEdgeWeightMap.get(e).doubleValue();
                edgeWidth = (int) (((float) edgeWidth / (float) mMaxValue) * 25.0f);

            }
            if (!mNormalized)
            {
                edgeWidth = (int) mActiveEdgeWeightMap.get(e).doubleValue();
                if (edgeWidth > 25)
                {
                    edgeWidth = 25;
                }
                //edgeWidth -= 2;
                if (edgeWidth < 1)
                {
                    edgeWidth = 1;
                }
            }
        }
        return (new BasicStroke(edgeWidth));
    }
}
