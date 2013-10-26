package invis.gl.Transformers.MixedDisplay;

import invis.gl.NetworkClusterApi.NetworkClusterElementApi;
import invis.gl.NetworkClusterApi.NetworkClusterVertexApi;
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
public class MixedVertexPainter<V> implements Transformer<V, Paint>
{

    private HashMap<V, NetworkElementApi> mNetworkElementDataTable;
    private HashMap<V, NetworkClusterVertexApi> mNetworkClusterElementDataTable;

    public MixedVertexPainter(HashMap<V, NetworkElementApi> NETable, HashMap<V, NetworkClusterVertexApi> NCETable)
    {
        mNetworkElementDataTable = NETable;
        mNetworkClusterElementDataTable = NCETable;
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

        if (mNetworkElementDataTable.containsKey(vertex))
        {
            //Vertex Color for Errors.
            if (mNetworkElementDataTable.containsKey(vertex))
            {
                if (mNetworkElementDataTable.get(vertex).getErrorValue())
                {
                    color = new HSLColor(0, Saturation, Value);
                    tempColor = (CheckSelected(vertex, color));
                }
            }

            //Vertex Color for Goals.
            if (mNetworkElementDataTable.containsKey(vertex))
            {
                if (mNetworkElementDataTable.get(vertex).getGoalValue())
                {
                    color = new HSLColor(120, Saturation, Value);
                    tempColor = (CheckSelected(vertex, color));
                }
            }
        }

        if (mNetworkClusterElementDataTable.containsKey(vertex))
        {
            tempColor = (CheckSelected(vertex, color));
        }

        return tempColor;
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
        if (mNetworkElementDataTable.containsKey(vertex))
        {
            if (mNetworkElementDataTable.get(vertex).getSelected())
            {
                newColor = color.adjustTone(70.0f);
            } else
            {
                newColor = color.getRGB();
            }
        } else if (mNetworkClusterElementDataTable.containsKey(vertex))
        {
            if (mNetworkClusterElementDataTable.get(vertex).getSelected())
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
