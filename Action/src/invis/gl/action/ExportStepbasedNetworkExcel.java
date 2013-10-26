package invis.gl.action;

import invis.gl.dataexporter.InteractionNetworkExporter;
import invis.gl.dataexporter.InteractionNetworkExporter.NetworkType;
import invis.gl.dataprocessor.DataParser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;

@ActionID(
    category = "Build",
id = "invis.gl.action.ExportStepbasedNetworkExcel")
@ActionRegistration(
    displayName = "#CTL_ExportStepbasedNetworkExcel")
@ActionReference(path = "Menu/File/Export", position = 200)
@Messages("CTL_ExportStepbasedNetworkExcel=Export Stepbased Network (Excel)")
public final class ExportStepbasedNetworkExcel implements ActionListener
{

    private DataParser mDataParser;

    @Override
    public void actionPerformed(ActionEvent e)
    {
        mDataParser = Lookup.getDefault().lookup(DataParser.class);

        if (mDataParser.HasDerivedData())
        {
            try
            {
                InteractionNetworkExporter INE = new InteractionNetworkExporter();
                INE.ExportNetworkToExcel(NetworkType.StepbasedNetwork);
            } catch (IOException ex)
            {
                Exceptions.printStackTrace(ex);
            } 
        }
    }
}
