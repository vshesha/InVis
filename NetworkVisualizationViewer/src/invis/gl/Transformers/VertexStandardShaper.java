package invis.gl.Transformers;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.decorators.EllipseVertexShapeTransformer;
import invis.gl.networkapi.NetworkElementApi;
import java.awt.Shape;
import java.util.HashMap;

/**
 *
 * @author Matt
 */
public class VertexStandardShaper<V> extends EllipseVertexShapeTransformer<V>
{
    private HashMap<V, NetworkElementApi> mNEDataTable;
    private Double mSizeMultiplier;

    public VertexStandardShaper(HashMap<V, NetworkElementApi> dataTable)
    {
        mNEDataTable = dataTable;
        mSizeMultiplier = 1.0;
    }

    public void UpdateSizeMultiplier(Double sizeMultiplier)
    {
        mSizeMultiplier = sizeMultiplier;
    }

    @Override
    public Shape transform(V v)
    {
        if (v instanceof Graph)
        {
            int size = ((Graph) v).getVertexCount();
            if (size < 8)
            {
                int sides = Math.max(size, 3);
                return factory.getRegularPolygon(v, sides);
            }
        } else
        {
            if (mNEDataTable.get(v).getSelected())
            {
                Double size = (mSizeMultiplier * 80);
                this.setSizeTransformer(new VertexSizeTransformer<V>(size.intValue()));
            } else
            {
                this.setSizeTransformer(new VertexSizeTransformer<V>(80));
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

        return super.transform(v);
    }
}