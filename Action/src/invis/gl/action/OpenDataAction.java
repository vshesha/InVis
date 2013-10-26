package invis.gl.action;

import Transformers.MDPTopComponent;
import invis.gl.NetworkViewer.NetworkViewerTopComponent;
import invis.gl.dataprocessor.DataParser;
import invis.gl.graphviewer.NetworkDisplayTopComponent;
import invis.gl.networkapi.VisualEditorTopComponentExtension;
import invis.gl.viewer.RawDataviewerTopComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.WindowManager;

@ActionID(category = "Build",
id = "invis.gl.viewer.OpenDataAction")
@ActionRegistration(iconBase = "invis/gl/viewer/OpenFileIcon16.png",
displayName = "#CTL_OpenDataAction")
@ActionReferences(
{
    @ActionReference(path = "Menu/File", position = 950),
    @ActionReference(path = "Toolbars/File", position = 450)
})
@Messages("CTL_OpenDataAction=Open Data")
public final class OpenDataAction implements ActionListener
{

    private DataParser mDataParser;

    @Override
    public void actionPerformed(ActionEvent e)
    {
        mDataParser = Lookup.getDefault().lookup(DataParser.class);
        try
        {
            if (!mDataParser.hasData())
            {

                mDataParser.ParseData();

//                if (mDataParser.getFileName() != null)
//                {
                RawDataviewerTopComponent vtc = (RawDataviewerTopComponent) WindowManager.getDefault().findTopComponent("viewerTopComponent");
                if (vtc != null && vtc.isOpened())
                {
                    vtc.RefreshContents();
                }

                NetworkViewerTopComponent nvtc = (NetworkViewerTopComponent) WindowManager.getDefault().findTopComponent("NetworkViewerTopComponent");
                if (nvtc != null && nvtc.isOpened())
                {
                    nvtc.RefreshNetworkNodeContents();
                }

                NetworkDisplayTopComponent ndtc = (NetworkDisplayTopComponent) WindowManager.getDefault().findTopComponent("NetworkDisplayTopComponent");
                if (ndtc != null && ndtc.isOpened())
                {
                    //ndtc.loadVisualizationViewer();
                    ////ndtc.InitializeNodeFrequencySlider();
                    ////ndtc.InitializeEdgeFrequencySlider();
                    //ndtc.repaint();
                    //ndtc.RefreshVisualizationViewer();
                }


                MDPTopComponent mdptc = (MDPTopComponent) WindowManager.getDefault().findTopComponent("MDPTopComponenet");
                {
                    if (mdptc != null)
                    {
                        mdptc.InitializeMdpValueSlider();
                        mdptc.open();
                    }
                }



                //NetworkViewerTopComponent nvtc = (NetworkViewerTopComponent) WindowManager.getDefault().findTopComponent("NetworkViewerTopComponent");
                nvtc.open();
                //NetworkDisplayTopComponent ndtc = (NetworkDisplayTopComponent) WindowManager.getDefault().findTopComponent("NetworkDisplayTopComponent");
                ndtc.open();
                VisualEditorTopComponentExtension vetc = (VisualEditorTopComponentExtension) WindowManager.getDefault().findTopComponent("VisualEditorTopComponent");
                vetc.open();
                /*StepBasedDisplayTopComponent sbdtc = (StepBasedDisplayTopComponent) WindowManager.getDefault().findTopComponent("StepBasedDisplayTopComponent");
                 if (sbdtc != null)
                 {
                 sbdtc.open();
                 sbdtc.loadVisualizationViewer();
                 }*/
                //StepbasedViewerTopComponent sbvtc = (StepbasedViewerTopComponent) WindowManager.getDefault().findTopComponent("StepbasedViewerTopComponent");
                //sbvtc.open();
                //}
            }
        } catch (Exception ex)
        {
            Exceptions.printStackTrace(ex);
        }
    }
}