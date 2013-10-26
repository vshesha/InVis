package invis.gl.viewer_v2;

import invis.gl.fileio.FileIO;

import java.io.File;

import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.view.BeanTreeView;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataNode;
import org.openide.loaders.DataObject;
import org.openide.nodes.Children;
import org.openide.util.Exceptions;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//invis.gl.viewer_v2//viewer_v2//EN",
autostore = false)
@TopComponent.Description(preferredID = "doViewerTopComponent",
//iconBase="SET/PATH/TO/ICON/HERE", 
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "explorer", openAtStartup = false)
@ActionID(category = "Window", id = "invis.gl.viewer_v2.doViewerTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(displayName = "#CTL_doViewerAction",
preferredID = "doViewerTopComponent")
public final class doViewerTopComponent extends TopComponent implements ExplorerManager.Provider
{

    private ExplorerManager mgr = new ExplorerManager();
    private File mFile = null;

    public doViewerTopComponent()
    {
        initComponents();
        setName(NbBundle.getMessage(doViewerTopComponent.class, "CTL_doViewerTopComponent"));
        setToolTipText(NbBundle.getMessage(doViewerTopComponent.class, "HINT_doViewerTopComponent"));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();

        jScrollPane1 = new BeanTreeView();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void componentOpened()
    {
        if (mFile == null)
        {
            try
            {
                mFile = FileIO.getFile();
                FileObject fo = FileUtil.toFileObject(mFile);
                DataObject dObj = DataObject.find(fo);
                
                

                FileToObjectParser x = new FileToObjectParser(dObj);

                if (dObj != null)
                {
                    mgr.setRootContext(new DataNode(dObj, Children.create(new CaseSetNodeFactory(dObj), true)));
                }
            } catch (Exception ex)
            {
                Exceptions.printStackTrace(ex);
            }
        }
    }

    @Override
    public void componentClosed()
    {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p)
    {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p)
    {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    @Override
    public ExplorerManager getExplorerManager()
    {
        return (mgr);
    }
}
