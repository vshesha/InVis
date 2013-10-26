package invis.gl.Transformers;

import org.apache.commons.collections15.Transformer;

/**
 *
 * @author Matt
 */
public class VertexSizeTransformer<V> implements Transformer<V, Integer>
{

    Integer mSize = 80;

    public VertexSizeTransformer(Integer size)
    {
        mSize = size;
    }

    @Override
    public Integer transform(V v)
    {
        return (mSize);
    }
}
