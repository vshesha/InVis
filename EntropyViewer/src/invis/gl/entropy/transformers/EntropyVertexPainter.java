package invis.gl.entropy.transformers;

import invis.gl.Transformers.HSLColor;
import invis.gl.networkapi.NetworkElementApi;
import java.awt.Color;
import java.awt.Paint;
import java.util.HashMap;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author Matt
 */
public class EntropyVertexPainter<V> implements Transformer<V, Paint>
{

    private HashMap<V, NetworkElementApi> mNodeDataTable;
    private HashMap<V, Integer> mFailCountDataTable;
    private int mLowerFrequencyFilterValue;
    private int mUpperFrequencyFilterValue;
    private int mLowerMDPFilterValue;
    private int mUpperMDPFilterValue;
    private Integer mMinSize, mMaxSize, mMinDataValue, mMaxDataValue;

    public EntropyVertexPainter(HashMap<V, NetworkElementApi> nodeDataTable, HashMap<V, Integer> failCountDataTable)
    {
        mNodeDataTable = nodeDataTable;
        mFailCountDataTable = failCountDataTable;
        mMinSize = 25;
        mMaxSize = 100;
    }

    public void setFrequencyThreshold(int lowerValue, int upperValue)
    {
        mLowerFrequencyFilterValue = lowerValue;
        mUpperFrequencyFilterValue = upperValue;
    }

    public void setMDPThreshold(int lowerValue, int upperValue)
    {
        mLowerMDPFilterValue = lowerValue;
        mUpperMDPFilterValue = upperValue;
    }

    public void UpdateMinMaxDataValues(Integer min, Integer max)
    {
        mMinDataValue = min;
        mMaxDataValue = max;
    }

    @Override
    public Paint transform(V vertex) //So for each node that we draw...
    {

        HSLColor color;
        int Saturation = 70;
        float Value;

        Integer inputFailCount = mFailCountDataTable.get(vertex);
        Integer DimensionRange = mMaxSize - mMinSize;
        Integer DataRange = mMaxDataValue - mMinDataValue;
        
        //I want to reverse this, so white is good, dark is high fail.
        float ratio = (DataRange.floatValue() - inputFailCount.floatValue()) / DataRange.floatValue();
        Value = mMinSize + (ratio * DimensionRange);
        
        color = new HSLColor(350, Saturation, Value);
        //Color tempColor = (CheckSelected(vertex, color));
        Color tempColor = color.getRGB();

        float alpha = 1.0f;
        /*if (mNodeDataTable.containsKey(vertex))
        {
            if (mNodeDataTable.get(vertex).getUniqueFrequency() < this.mLowerFrequencyFilterValue
                    || mNodeDataTable.get(vertex).getUniqueFrequency() > this.mUpperFrequencyFilterValue
                    || mNodeDataTable.get(vertex).getMDPValue() < this.mLowerMDPFilterValue
                    || mNodeDataTable.get(vertex).getMDPValue() > this.mUpperMDPFilterValue)
            {
                alpha = 0.1f;
            }
        }*/

        float red = (float) (tempColor.getRed()) / 255;
        float green = (float) (tempColor.getGreen()) / 255;
        float blue = (float) (tempColor.getBlue()) / 255;

        Color vertexColor = new Color(red, green, blue, alpha);

        return (vertexColor);
    }

    /**
     * Adjust the color for selected vertices.
     *
     * @param vertex the vertex to check for selection.
     * @param color the current color of the vertex.
     * @return the adjusted vertex color, based on Tone.
     */
    private Color CheckSelected(V vertex, HSLColor color)
    {
        Color newColor;
        if (mNodeDataTable.containsKey(vertex))
        {
            if (mNodeDataTable.get(vertex).getSelected())
            {
                //newColor = color.adjustTone(70.0f);
                newColor = color.getRGB();
            } else
            {
                newColor = color.getRGB();
            }
        } else
        {
            newColor = color.getRGB();
        }
        return (newColor);
    }
}
