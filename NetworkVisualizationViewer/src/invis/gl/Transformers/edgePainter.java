package invis.gl.Transformers;

import invis.gl.networkapi.NetworkElementApi;
import java.awt.*;
import java.util.HashMap;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author Matt
 */
public class edgePainter<E> implements Transformer<E, Paint>
{

    /**
     * This parameter is used to determine what visual effects to apply to the
     * edge.
     */
    private HashMap<String, NetworkElementApi> mEdgeDataTable;
    private int lowerFrequencyFilterValue;
    private int upperFrequencyFilterValue;
    private int lowerMDPFilterValue;
    private int upperMDPFilterValue;

    public edgePainter(HashMap<String, NetworkElementApi> edgeDataTable)
    {
        mEdgeDataTable = edgeDataTable;
        lowerFrequencyFilterValue = 0;
        upperFrequencyFilterValue = Integer.MAX_VALUE;
        lowerMDPFilterValue = 0;
        upperMDPFilterValue = 0;
    }

    public void setFrequencyThreshold(int lowerValue, int upperValue)
    {
        lowerFrequencyFilterValue = lowerValue;
        upperFrequencyFilterValue = upperValue;
    }

    public void setMDPThreshold(int lowerValue, int upperValue)
    {
        lowerMDPFilterValue = lowerValue;
        upperMDPFilterValue = upperValue;
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
        //MWJ: Check selected nodes.
        //if (((MDP_Edge) edge).isSelected())
        //{
        //    return (new Color(0.0f, 0.0f, 1.0f, 1.0f));
        //}


        if (mEdgeDataTable.containsKey(edge))
        {
            if (mEdgeDataTable.get(edge).getUniqueFrequency() < this.lowerFrequencyFilterValue
                    || mEdgeDataTable.get(edge).getUniqueFrequency() > this.upperFrequencyFilterValue
                    || mEdgeDataTable.get(edge).getMDPValue() < this.lowerMDPFilterValue
                    || mEdgeDataTable.get(edge).getMDPValue() > this.upperMDPFilterValue)
            {
                alpha = 0.1f;
                return (new Color(0.0f, 0.0f, 0.0f, alpha));
            } else if (mEdgeDataTable.get(edge).getSelected())
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
