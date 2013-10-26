
package invis.gl.NetworkViewerNodes.CustomPropertyEditors;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.beans.PropertyChangeListener;
import java.beans.PropertyEditorSupport;
import org.apache.commons.collections.map.MultiValueMap;

/**
 *
 * @author Matt
 */
public class MultiValueMapPropertyEditor extends PropertyEditorSupport
{

    private MultiValueMap mMultiValueMap = new MultiValueMap();
    
    public MultiValueMapPropertyEditor()
    {
    }
    @Override
    public void setValue(Object value)
    {
        mMultiValueMap = (MultiValueMap) value;
    }

    @Override
    public Object getValue()
    {
        return (mMultiValueMap);
    }

    @Override
    public boolean isPaintable()
    {
        return (false);
    }

    @Override
    public void paintValue(Graphics gfx, Rectangle box)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getJavaInitializationString()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getAsText()
    {
        StringBuilder sb = new StringBuilder();
        
        for (int i =0;i<mMultiValueMap.size();i++)
        {
            sb.append(mMultiValueMap.keySet().toArray()[i].toString());
            sb.append(", ");
        }
        return (sb.toString());
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String[] getTags()
    {
        String[] x = new String[2];
        x[0] = "a";
        x[1] = "b";
        return(x);
    }

    @Override
    public Component getCustomEditor()
    {
        CustomEditor customEditor = new CustomEditor(mMultiValueMap);
        return customEditor;
    }

    @Override
    public boolean supportsCustomEditor()
    {
        return (true);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        
    }   
}
