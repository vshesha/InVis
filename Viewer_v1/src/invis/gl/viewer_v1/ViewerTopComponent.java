package invis.gl.viewer_v1;

import invis.gl.dataprocessorv1.RawInput;
import invis.gl.dataprocessorv1.RawInputInterface;
import invis.gl.rawinput.RawInputData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.explorer.view.BeanTreeView;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//invis.gl.viewer_v1//viewer_v1//EN",
autostore = false)
@TopComponent.Description(preferredID = "ViewerTopComponent",
//iconBase="SET/PATH/TO/ICON/HERE", 
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "invis.gl.viewer_v1.ViewerTopComponent")
@ActionReferences(
{
    @ActionReference(path = "Menu/Window" /*, position = 333 */)
})
@TopComponent.OpenActionRegistration(displayName = "#CTL_ViewerAction")//,
//preferredID = "ViewerTopComponent")
public final class ViewerTopComponent extends TopComponent implements ExplorerManager.Provider, LookupListener
{
    private ExplorerManager mgr = new ExplorerManager();
    private List<RawInputData> mRawInputList = new ArrayList<RawInputData>();
//    private RawInputInterface mRI;
    private Lookup.Result result = null;

    public ViewerTopComponent()
    {
        initComponents();

        //This lookup is what sets up nodes to work with the properties window(s).
        associateLookup(ExplorerUtils.createLookup(mgr, this.getActionMap()));
//        associateLookup (new AbstractLookup (content));

        setName(NbBundle.getMessage(ViewerTopComponent.class, "CTL_ViewerTopComponent"));
        setToolTipText(NbBundle.getMessage(ViewerTopComponent.class, "HINT_ViewerTopComponent"));
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                .addGap(40, 40, 40))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void componentOpened()
    {
        Lookup.Template t = new Lookup.Template(RawInputInterface.class);
        Lookup.Result res = Lookup.getDefault().lookup(t);
        res.addLookupListener(this);
        RawInputInterface RII = null;
        try
        {
            RII = Lookup.getDefault().lookup(RawInputInterface.class);
            if (RII.getRawInputData() == null)
            {
                (Lookup.getDefault().lookup(RawInputInterface.class)).ReadInFile(new RawInputData());
            }

        } catch (Exception ex)
        {
            Exceptions.printStackTrace(ex);
        }
        Collection all = res.allInstances();
        List l = new ArrayList(all);
        if (((RawInput) l.get(0)).getRawInputData() != null)
        {
            RawInput RI = (RawInput) l.get(0);
            mRawInputList.add(RI.getRawInputData());
            mgr.setRootContext(new AbstractNode(Children.create(new FileDataNodeFactory(mRawInputList), true)));
            mgr.getRootContext().setDisplayName("Data");
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

    @Override
    public void resultChanged(LookupEvent le)
    {
        Lookup.Template t = new Lookup.Template(RawInputInterface.class);
        Lookup.Result res = Lookup.getDefault().lookup(t);
        Collection all = res.allInstances();

        List l = new ArrayList(all);
        if (((RawInput) l.get(0)).getRawInputData() != null)
        {
            RawInput RI = (RawInput) l.get(0);
            mRawInputList.add(RI.getRawInputData());
            mgr.setRootContext(new AbstractNode(Children.create(new FileDataNodeFactory(mRawInputList), true)));
        }
    }
}