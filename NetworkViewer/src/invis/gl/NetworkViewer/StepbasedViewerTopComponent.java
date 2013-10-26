package invis.gl.NetworkViewer;

import invis.gl.NBNodeFactories.StepBasedFactories.StepBasedRootNodeFactory;
import invis.gl.NetworkVisualizationViewer.NetworkVisualizationViewer;
import invis.gl.dataprocessor.DataParser;
import invis.gl.graphvisualapi.NetworkDisplayApi.DisplayType;
import invis.gl.networkapi.NodeViewerTopComponentExtension;
import invis.gl.stepbasednodes.StepBasedRootNode;
import java.io.IOException;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.explorer.view.BeanTreeView;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.TopComponent;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
    dtd = "-//invis.gl.NetworkViewer//StepbasedViewer//EN",
autostore = false)
@TopComponent.Description(
    preferredID = "StepbasedViewerTopComponent",
//iconBase="SET/PATH/TO/ICON/HERE", 
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "explorer", openAtStartup = false)
@ActionID(category = "Window", id = "invis.gl.NetworkViewer.StepbasedViewerTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
    displayName = "#CTL_StepbasedViewerAction",
preferredID = "StepbasedViewerTopComponent")
@Messages(
{
    "CTL_StepbasedViewerAction=StepbasedViewer",
    "CTL_StepbasedViewerTopComponent=StepbasedViewer Window",
    "HINT_StepbasedViewerTopComponent=This is a StepbasedViewer window"
})
public final class StepbasedViewerTopComponent extends NodeViewerTopComponentExtension implements Lookup.Provider
{

    private ExplorerManager mgr = new ExplorerManager();
    private DataParser mDataParser;
    private Lookup mLookup;
    private InstanceContent mInstanceContent;

    public StepbasedViewerTopComponent()
    {
        initComponents();
        this.associateLookup(ExplorerUtils.createLookup(mgr, this.getActionMap()));

        mInstanceContent = new InstanceContent();
        mLookup = new AbstractLookup(mInstanceContent);

        setName(Bundle.CTL_StepbasedViewerTopComponent());
        setToolTipText(Bundle.HINT_StepbasedViewerTopComponent());

    }

    public void RefreshStepBasedNodeContents()
    {
        //new StepBasedRootNodeFactory(mDataParser.getDerivedData().getNodeTable())
        if (mDataParser.HasDerivedData())
        {
            //then append edge children. then send that in. ...
            Node trueRoot = new StepBasedRootNode(Children.create(
                    new StepBasedRootNodeFactory(
                    mDataParser.getDerivedData().getNodeTable(), mDataParser.getDerivedData().getEdgeTable()), true),
                    mDataParser.getNetworkCaseSet(),
                    mDataParser.getDerivedData().getNodeTable(),
                    mDataParser.getDerivedData().getEdgeTable());
            mgr.setRootContext(trueRoot);
            mgr.getRootContext().setDisplayName("File: " + mDataParser.getFileName() + " - Case Size: " + mDataParser.getCaseSet().getCaseSetSize());

        } else
        {
            AbstractNode EmptyNode = new AbstractNode(Children.LEAF);
            EmptyNode.setName("No Derived Data Loaded.");
            mgr.setRootContext(EmptyNode);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        beanTreeView = new javax.swing.JScrollPane();

        beanTreeView = new BeanTreeView();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(beanTreeView, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(beanTreeView, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane beanTreeView;
    // End of variables declaration//GEN-END:variables

    @Override
    public void componentOpened()
    {
        mDataParser = Lookup.getDefault().lookup(DataParser.class);
        if (mDataParser.HasDerivedData())
        {
            this.RefreshStepBasedNodeContents();
        }
    }

    @Override
    public void ClearContents()
    {
        mInstanceContent = new InstanceContent();
        mLookup = new AbstractLookup(mInstanceContent);
        try
        {
            this.getExplorerManager().getRootContext().destroy();
        } catch (IOException ex)
        {
            Exceptions.printStackTrace(ex);
        }

    }

    @Override
    public void componentClosed()
    {
        try
        {
            this.ClearContents();
            mgr.getRootContext().destroy();
        } catch (IOException ex)
        {
            Exceptions.printStackTrace(ex);
        }

    }

    void writeProperties(java.util.Properties p)
    {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");

    }

    /**
     * returns the lookup of the DataNetwork. Override because this implements
     * Lookup.Provider
     *
     * @return mLookup.
     */
    @Override
    public Lookup getLookup()
    {
        return (mLookup);
    }

    @Override
    public ExplorerManager getExplorerManager()
    {
        return (mgr);
    }

    void readProperties(java.util.Properties p)
    {
        String version = p.getProperty("version");

    }

    @Override
    public void UpdateCurrentVV(NetworkVisualizationViewer currentVV, DisplayType type)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void RefreshContents()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}