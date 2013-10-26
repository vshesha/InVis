
package invis.gl.action;

import invis.gl.dataprocessor.DataParser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;

@ActionID(
    category = "Build",
id = "invis.gl.action.GenerateStepbasedData")
@ActionRegistration(
    displayName = "#CTL_GenerateStepbasedData")
@ActionReference(path = "Menu/File", position = 1413, separatorAfter = 1419)
@Messages("CTL_GenerateStepbasedData=Generate Stepbased Data")
public final class GenerateStepbasedData implements ActionListener
{

    DataParser mDataParser;

    @Override
    public void actionPerformed(ActionEvent e)
    {
        mDataParser = Lookup.getDefault().lookup(DataParser.class);
        if (mDataParser.hasData())
        {
            mDataParser.BuildStepBasedData();
        }
    }
}
