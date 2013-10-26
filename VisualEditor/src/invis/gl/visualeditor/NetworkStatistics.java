package invis.gl.visualeditor;

import invis.gl.NetworkStatistics.NetworkStatisticsPanel;
import invis.gl.dataprocessor.DataParser;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(category = "Build",
id = "NetworkVisualizationViewer.NetworkNodeDisplay")
@ActionRegistration(displayName = "#CTL_NetworkNodeDisplay")
@ActionReferences(
{
})
@Messages("CTL_NetworkNodeDisplay=Node Display")
@Deprecated
public final class NetworkStatistics implements ActionListener
{
    DataParser mDataParser;

    public void NetworkStatistics(DataParser dataParser)
    {
        mDataParser = dataParser;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        //DialogDescriptor dg = new DialogDescriptor(new NetworkStatisticsPanel(mDataParser), "Statistics Window");
        //Dialog dd = DialogDisplayer.getDefault().createDialog(dg);
        //dd.setVisible(true);
    }
}
