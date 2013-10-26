package invis.gl.action;

import invis.gl.dataexporter.InteractionNetworkExporter;
import invis.gl.dataprocessor.DataParser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;

@ActionID(
    category = "Build",
id = "invis.gl.action.ExportDataAction")
@ActionRegistration(
    iconBase = "invis/gl/action/ExportIcon16.PNG",
displayName = "#CTL_ExportDataAction")
@ActionReferences(
{
    @ActionReference(path = "Menu/File/Export", position = 975),
    @ActionReference(path = "Toolbars/File", position = 600)
})
@Messages("CTL_ExportDataAction=Export Interaction Network")
public final class ExportDataAction implements ActionListener
{
    private DataParser mDataParser;

    @Override
    public void actionPerformed(ActionEvent e)
    {
        mDataParser = Lookup.getDefault().lookup(DataParser.class);

        if (mDataParser.hasData())
        {
            InteractionNetworkExporter INE = new InteractionNetworkExporter();
            INE.ExportInteractionNetwork();
        }
    }
}
