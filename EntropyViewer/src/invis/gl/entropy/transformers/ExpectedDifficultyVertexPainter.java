
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
public class ExpectedDifficultyVertexPainter<V> implements Transformer<V, Paint>
{

    private HashMap<V, NetworkElementApi> mNodeDataTable;
    private HashMap<V, Double> mExpectedDifficultyDataTable;
    /*private int mLowerFrequencyFilterValue;
    private int mUpperFrequencyFilterValue;
    private int mLowerMDPFilterValue;
    private int mUpperMDPFilterValue;*/
    private Double mMinSize, mMaxSize, mMinDataValue, mMaxDataValue;

    public ExpectedDifficultyVertexPainter(HashMap<V, NetworkElementApi> nodeDataTable, HashMap<V, Double> expectedDifficultyDataTable)
    {
        mNodeDataTable = nodeDataTable;
        mExpectedDifficultyDataTable = expectedDifficultyDataTable;
        mMinSize = 25.0;
        mMaxSize = 100.0;
    }

/*    public void setFrequencyThreshold(int lowerValue, int upperValue)
    {
        mLowerFrequencyFilterValue = lowerValue;
        mUpperFrequencyFilterValue = upperValue;
    }

    public void setMDPThreshold(int lowerValue, int upperValue)
    {
        mLowerMDPFilterValue = lowerValue;
        mUpperMDPFilterValue = upperValue;
    }*/

    public void UpdateMinMaxDataValues(Double min, Double max)
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

        Double inputDifficulty = mExpectedDifficultyDataTable.get(vertex);
        Double DimensionRange = mMaxSize - mMinSize;
        Double DataRange = mMaxDataValue - mMinDataValue;
        
        //I want to reverse this, so white is good, dark is high fail.
        //Double ratio = (DataRange - inputDifficulty) / DataRange;
        //Value = mMinSize.floatValue() + (ratio.floatValue() * DimensionRange.floatValue());
        
        Value = (float) (inputDifficulty * 100);
        
        color = new HSLColor(350, Saturation, Value);
        //Color tempColor = (CheckSelected(vertex, color));
        Color tempColor = CheckSelected(vertex, color);

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
                newColor = color.adjustTone(70.0f);
                //newColor = color.getRGB();
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