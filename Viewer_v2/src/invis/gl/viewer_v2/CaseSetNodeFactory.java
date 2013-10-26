/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package invis.gl.viewer_v2;

import java.io.IOException;
import java.util.List;
import org.openide.loaders.DataObject;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

/**
 *
 * @author Matt
 */
class CaseSetNodeFactory extends ChildFactory<String>
{

    DataObject mDobj;

    public CaseSetNodeFactory(DataObject dObj)
    {
        mDobj = dObj;
    }

    @Override
    protected boolean createKeys(List<String> list)
    {
        try
        {
            //We skip the first line, ie. we don't start i at 0, because the
            //first line is the header line.
            for (int i = 1; i < mDobj.getPrimaryFile().asLines().size(); i++)
            {
                list.add(mDobj.getPrimaryFile().asLines().get(i).toString());
            }
        } catch (IOException ex)
        {
            Exceptions.printStackTrace(ex);
        }
        return (true);
    }


    @Override
    protected Node createNodeForKey(String key)
    {
        Node node = new CaseNode(Children.create(new InteractionsFactory(key), true), key);
        node.setDisplayName(key);
        return (Node)(node);
    }
}
