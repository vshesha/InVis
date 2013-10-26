package invis.gl.Transformers;

import invis.gl.NetworkClusterApi.NetworkClusterVertexApi;
import java.awt.Color;
import java.awt.Paint;
import java.util.HashMap;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author Matt
 */
public class stepBasedVertexPainter<V> implements Transformer<V, Paint>
{

    private HashMap<V, NetworkClusterVertexApi> mNodeDataTable;
    private int lowerFrequencyFilterValue;
    private int upperFrequencyFilterValue;

    public stepBasedVertexPainter(HashMap<V, NetworkClusterVertexApi> nodeDataTable)
    {
        mNodeDataTable = nodeDataTable;
    }
    
        public void setFrequencyThreshold(int lowerValue, int upperValue)
    {
        lowerFrequencyFilterValue = lowerValue;
        upperFrequencyFilterValue = upperValue;
    }

    @Override
    public Paint transform(V vertex) //So for each node that we draw...
    {

        HSLColor color;
        int Saturation = 70;
        int Value = 50;

        //Default vertex Color:
        color = new HSLColor(240, Saturation, Value);
        Color tempColor = (CheckSelected(vertex, color));

        //Vertex Color for Errors.
        if (mNodeDataTable.containsKey(vertex))
        {
            if (mNodeDataTable.get(vertex).getContainsError())
            {
                color = new HSLColor(0, Saturation, Value);
                tempColor = (CheckSelected(vertex, color));
            }
        }

        //Vertex Color for Goals.
        if (mNodeDataTable.containsKey(vertex))
        {
            if (mNodeDataTable.get(vertex).getContainsGoal())
            {
                color = new HSLColor(120, Saturation, Value);
                tempColor = (CheckSelected(vertex, color));
            }
        }


        float alpha = 1.0f;
        if (mNodeDataTable.containsKey(vertex))
        {
            if (mNodeDataTable.get(vertex).getTotalUniqueFrequency() < this.lowerFrequencyFilterValue
                    || mNodeDataTable.get(vertex).getTotalUniqueFrequency() > this.upperFrequencyFilterValue)
            /*
             || mNodeDataTable.get(vertex).getMDPValue() < this.lowerMDPFilterValue
             || mNodeDataTable.get(vertex).getMDPValue() > this.upperMDPFilterValue)*/
            {
                alpha = 0.1f;
            }
        }
          
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
