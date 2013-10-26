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
id = "invis.gl.action.ExportInteractionNetworkExcel")
@ActionRegistration(
    displayName = "#CTL_ExportInteractionNetworkExcel")
@ActionReference(path = "Menu/File/Export", position = 100)
@Messages("CTL_ExportInteractionNetworkExcel=Export Interaction Network (Excel)")
public final class ExportInteractionNetworkExcel implements ActionListener
{

    private DataParser mDataParser;

    @Override
    public void actionPerformed(ActionEvent e)
    {
        mDataParser = Lookup.getDefault().lookup(DataParser.class);

        if (mDataParser.hasData())
        {
            try
            {
                InteractionNetworkExporter INE = new InteractionNetworkExporter();
                INE.ExportNetworkToExcel(NetworkType.InteractionNetwork);
            } catch (IOException ex)
            {
                Exceptions.printStackTrace(ex);
            }
        }
    }
}
