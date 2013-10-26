package invis.gl.Transformers;

import edu.uci.ics.jung.visualization.picking.PickedState;
import java.awt.BasicStroke;
import java.awt.Stroke;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author Matt
 */
public class vertexStrokePainter<V> implements Transformer<V, Stroke>
{

    PickedState<V> mPickedVertex;

    public vertexStrokePainter(PickedState<V> pickedVertexState)
    {
        mPickedVertex = pickedVertexState;
    }

    @Override
    public Stroke transform(V vertex)
    {
        BasicStroke border;
        if (1 == 1)
        {
            if (mPickedVertex.isPicked(vertex))
            {
                border = new BasicStroke(5);
            } else
            {
                border = new BasicStroke(2);
            }
        } else
        {
            border = new BasicStroke(5);
        }
        return (border);
    }
}
