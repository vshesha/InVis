package invis.gl.action;

import invis.gl.NetworkViewer.NetworkViewerTopComponent;
import invis.gl.NetworkViewer.StepbasedViewerTopComponent;
import invis.gl.dataprocessor.DataParser;
import invis.gl.graphviewer.NetworkDisplayTopComponent;
import invis.gl.graphviewer.StepBasedDisplayTopComponent;
import invis.gl.networkapi.NetworkElementApi;
import invis.gl.networkapi.NodeViewerTopComponentExtension;
import invis.gl.networkapi.VisualEditorTopComponentExtension;
import invis.gl.overview.OverviewTopComponent;
import invis.gl.viewer.RawDataviewerTopComponent;
import invis.gl.visualeditor.VisualEditorTopComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Collection;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.WindowManager;

@ActionID(category = "Build",
id = "invis.gl.viewer.CloseDataSetAction")
@ActionRegistration(iconBase = "invis/gl/viewer/CloseFileIcon16.png",
displayName = "#CTL_CloseDataSetAction")
@ActionReferences(
{
    @ActionReference(path = "Menu/File", position = 1950, separatorAfter = 2025),
    @ActionReference(path = "Toolbars/File", position = 500)
})
@Messages("CTL_CloseDataSetAction=Close Data Set")
public final class CloseDataSetAction implements ActionListener
{

    DataParser mDataParser;

    @Override
    public void actionPerformed(ActionEvent e)
    {
        mDataParser = Lookup.getDefault().lookup(DataParser.class);
        try
        {
            //We close the file.
            mDataParser.CloseFile();
            mDataParser = null;
            Collection<? extends NetworkElementApi> lookupAll = Lookup.getDefault().lookupAll(NetworkElementApi.class);
            for (int i = 0; i < lookupAll.size(); i++)
            {
                NetworkElementApi x = (NetworkElementApi) lookupAll.toArray()[i];

                //We make the assumption there is only one property change listener.
                x.removePropertyChangeListener(x.getPropertyChangeListener()[0]);
            }

            //We destroy the RootContext of the ExplorerManager.
            RawDataviewerTopComponent vtc = (RawDataviewerTopComponent) WindowManager.getDefault().findTopComponent("viewerTopComponent");
            if (vtc != null && vtc.isOpened())
            {
                vtc.ClearContents();
                vtc.RefreshContents();
            }

            NodeViewerTopComponentExtension nvtc = (NodeViewerTopComponentExtension) WindowManager.getDefault().findTopComponent("NetworkViewerTopComponent");
            if (nvtc != null && nvtc.isOpened())
            {
                nvtc.ClearContents();
                //nvtc.RefreshNetworkNodeContents();
                nvtc.close();
            }

            NetworkDisplayTopComponent ndtc = (NetworkDisplayTopComponent) WindowManager.getDefault().findTopComponent("NetworkDisplayTopComponent");
            if (ndtc != null && ndtc.isOpened())
            {
                ndtc.ClearContents();
                //ndtc.RefreshVisualizationViewer();
                ndtc.close();
            }

            OverviewTopComponent otc = (OverviewTopComponent) WindowManager.getDefault().findTopComponent("OverviewTopComponent");
            if (otc != null && otc.isOpened())
            {
                otc.ClearContents();
                //ndtc.RefreshVisualizationViewer();
                otc.close();
            }
            VisualEditorTopComponent vetc = (VisualEditorTopComponent) WindowManager.getDefault().findTopComponent("VisualEditorTopComponent");
            if (vetc != null && vetc.isOpened())
            {
                vetc.ClearContents();
                vetc.close();
            }

            StepBasedDisplayTopComponent sbdtc = (StepBasedDisplayTopComponent) WindowManager.getDefault().findTopComponent("StepBasedDisplayTopComponent");
            if (sbdtc != null && sbdtc.isOpened())
            {
                sbdtc.ClearContents();
                //ndtc.RefreshVisualizationViewer();
                sbdtc.close();
            }

            NodeViewerTopComponentExtension sbvtc = (NodeViewerTopComponentExtension) WindowManager.getDefault().findTopComponent("StepbasedViewerTopComponent");
            if (sbvtc != null && sbvtc.isOpened())
            {
                sbvtc.ClearContents();
                //sbvtc.RefreshStepBasedNodeContents();
                sbvtc.close();
            }

            NodeViewerTopComponentExtension evtc = (NodeViewerTopComponentExtension) WindowManager.getDefault().findTopComponent("EntropyViewerTopComponent");
            if (evtc != null && evtc.isOpened())
            {
                evtc.ClearContents();
                //evtc.RefreshContents();
                evtc.close();
            }


        } catch (IOException ex)
        {
            Exceptions.printStackTrace(ex);
        }
    }
}
