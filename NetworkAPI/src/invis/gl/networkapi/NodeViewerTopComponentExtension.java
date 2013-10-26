
package invis.gl.networkapi;

import invis.gl.NetworkVisualizationViewer.NetworkVisualizationViewer;
import invis.gl.dataprocessor.DataParser;
import invis.gl.graphvisualapi.NetworkDisplayApi;
import org.openide.explorer.ExplorerManager;
import org.openide.windows.TopComponent;

/**
 *
 * @author Matt
 */

public abstract class NodeViewerTopComponentExtension extends TopComponent implements ExplorerManager.Provider
{
        protected NetworkVisualizationViewer mNVV;
    protected DataParser mDataParser;

    public abstract void UpdateCurrentVV(NetworkVisualizationViewer currentVV, NetworkDisplayApi.DisplayType type);
    public abstract void RefreshContents();
    public abstract void ClearContents();
    @Override
    public abstract ExplorerManager getExplorerManager();
}
