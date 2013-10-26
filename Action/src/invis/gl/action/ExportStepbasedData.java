package invis.gl.action;

import invis.gl.dataexporter.InteractionNetworkExporter;
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
id = "invis.gl.action.ExportStepbasedData")
@ActionRegistration(
    displayName = "#CTL_ExportStepbasedData")
@ActionReference(path = "Menu/File/Export", position = 300)
@Messages("CTL_ExportStepbasedData=Export Stepbased Network")
public final class ExportStepbasedData implements ActionListener
{

    private DataParser mDataParser;

    @Override
    public void actionPerformed(ActionEvent e)
    {
        mDataParser = Lookup.getDefault().lookup(DataParser.class);

        if (mDataParser.HasDerivedData())
        {
            InteractionNetworkExporter INE = new InteractionNetworkExporter();
            INE.ExportStepbasedNetwork();
        }
    }
}
