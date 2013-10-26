package invis.gl.networkapi;

import invis.gl.NetworkVisualizationViewer.NetworkVisualizationViewer;
import invis.gl.dataprocessor.DataParser;
import invis.gl.graphvisualapi.NetworkDisplayApi.DisplayType;
import org.openide.explorer.ExplorerManager;
import org.openide.windows.TopComponent;

/**
 *
 * @author Matt
 */
public abstract class VisualEditorTopComponentExtension extends TopComponent
{

    protected NetworkVisualizationViewer mNVV;
    protected DataParser mDataParser;

    public abstract void UpdateCurrentVV(NetworkVisualizationViewer currentVV, DisplayType type);
    //public abstract void RefreshContents();
    public abstract void ClearContents();
    
}
