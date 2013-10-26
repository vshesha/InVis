package invis.gl.Transformers;

import invis.gl.NetworkClusterApi.NetworkClusterEdgeApi;
import invis.gl.NetworkClusterApi.NetworkClusterElementApi;
import java.awt.Color;
import java.awt.Paint;
import java.util.HashMap;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author Matt
 */
public class StepbasedEdgePainter<E> implements Transformer<E, Paint>
{

    /**
     * This parameter is used to determine what visual effects to apply to the
     * edge.
     */
    private HashMap<E, NetworkClusterElementApi> mEdgeDataTable;
    private int lowerFrequencyFilterValue;
    private int upperFrequencyFilterValue;

    public StepbasedEdgePainter(HashMap<E, NetworkClusterElementApi> edgeDataTable)
    {
        mEdgeDataTable = edgeDataTable;
        lowerFrequencyFilterValue = 0;
        upperFrequencyFilterValue = Integer.MAX_VALUE;
    }

    public void setFrequencyThreshold(int lowerValue, int upperValue)
    {
        lowerFrequencyFilterValue = lowerValue;
        upperFrequencyFilterValue = upperValue;
    }

    /**
     * Determine the Paint of the edge.
     *
     * @param edge pass in edge to determine what color to make it.
     * @return the Paint of the edge, which defines the color to draw the edge.
     */
    @Override
    public Paint transform(E edge)
    {
        float alpha;
        if (mEdgeDataTable.containsKey(edge))
        {
            /*
            if (mEdgeDataTable.get(edge).getTotalUniqueFrequency() < this.lowerFrequencyFilterValue
                    || mEdgeDataTable.get(edge).getTotalUniqueFrequency() > this.upperFrequencyFilterValue)
            {
                alpha = 0.1f;
                return (new Color(0.0f, 0.0f, 0.0f, alpha));
            }*/
            if (mEdgeDataTable.get(edge).getSelected())
            {
                return (Color.CYAN);
            } else
            {
                return new Color(0.0f, 0.0f, 0.0f, 1.0f);
            }
        }
        return (Color.BLUE); //This is the hint edge, by default.
    }
}
