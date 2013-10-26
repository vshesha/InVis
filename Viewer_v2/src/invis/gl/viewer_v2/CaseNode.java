package invis.gl.viewer_v2;

import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 *
 * @author Matt
 */
public class CaseNode extends AbstractNode
{
    public CaseNode(Children children, String data)
    {
        super(children);
        this.setDisplayName(data);
    }
}
