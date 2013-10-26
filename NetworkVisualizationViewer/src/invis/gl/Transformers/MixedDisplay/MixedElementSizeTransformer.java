
package invis.gl.Transformers.MixedDisplay;

import org.apache.commons.collections15.Transformer;

/**
 *
 * @author Matt
 */
public class MixedElementSizeTransformer<V> implements Transformer<V, Integer>
{

    Integer mSize = 80;

    public MixedElementSizeTransformer(Integer size)
    {
        mSize = size;
    }

    @Override
    public Integer transform(V v)
    {
        
    
        return (mSize);
    }
}