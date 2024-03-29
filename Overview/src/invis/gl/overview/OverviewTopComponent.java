package invis.gl.overview;

import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.SatelliteVisualizationViewer;
import invis.gl.dataprocessor.DataParser;
import invis.gl.graphviewer.NetworkDisplayTopComponent;
import invis.gl.networkapi.NetworkVVDisplay;
import java.awt.Color;
import java.awt.Dimension;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//invis.gl.overview//Overview//EN",
autostore = false)
@TopComponent.Description(preferredID = "OverviewTopComponent",
//iconBase="SET/PATH/TO/ICON/HERE", 
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "explorer", openAtStartup = false)
@ActionID(category = "Window", id = "invis.gl.overview.OverviewTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(displayName = "#CTL_OverviewAction",
preferredID = "OverviewTopComponent")
public final class OverviewTopComponent extends TopComponent
{

    private SatelliteVisualizationViewer<String, String> satVV;
    private DataParser mDataParser;

    public OverviewTopComponent()
    {
        initComponents();
        setName(NbBundle.getMessage(OverviewTopComponent.class, "CTL_OverviewTopComponent"));
        setToolTipText(NbBundle.getMessage(OverviewTopComponent.class, "HINT_OverviewTopComponent"));
        putClientProperty(TopComponent.PROP_MAXIMIZATION_DISABLED, Boolean.TRUE);
        putClientProperty(TopComponent.PROP_KEEP_PREFERRED_SIZE_WHEN_SLIDED_IN, Boolean.TRUE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        FitButton = new javax.swing.JButton();

        addComponentListener(new java.awt.event.ComponentAdapter()
        {
            public void componentResized(java.awt.event.ComponentEvent evt)
            {
                formComponentResized(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(FitButton, org.openide.util.NbBundle.getMessage(OverviewTopComponent.class, "OverviewTopComponent.FitButton.text")); // NOI18N
        FitButton.setLabel(org.openide.util.NbBundle.getMessage(OverviewTopComponent.class, "OverviewTopComponent.FitButton.label")); // NOI18N
        FitButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                FitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 355, Short.MAX_VALUE)
                .addComponent(FitButton))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 277, Short.MAX_VALUE)
                .addComponent(FitButton))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt)//GEN-FIRST:event_formComponentResized
    {//GEN-HEADEREND:event_formComponentResized
        int w = this.getParent().getWidth();
        int h = this.getParent().getHeight();
        if (satVV != null)
        {
            satVV.setSize(w, h);
        }
    }//GEN-LAST:event_formComponentResized

    private void FitButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_FitButtonActionPerformed
    {//GEN-HEADEREND:event_FitButtonActionPerformed
        this.ReCreateSatelliteViewer();
    }//GEN-LAST:event_FitButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton FitButton;
    // End of variables declaration//GEN-END:variables

    @Override
    public void componentOpened()
    {
        mDataParser = Lookup.getDefault().lookup(DataParser.class);

        if (mDataParser.hasData())
        {
            this.ReCreateSatelliteViewer();
//            int w = this.getParent().getWidth();
//            int h = this.getParent().getHeight();
//            NetworkDisplayTopComponent test = (NetworkDisplayTopComponent) WindowManager.getDefault().findTopComponent("NetworkDisplayTopComponent");
//            satVV = new SatelliteVisualizationViewer<String, String>(test.getVV(), new Dimension(w, h));
//            satVV.setBackground(Color.white);
//            satVV.setSize(w, h);
//            satVV.setVisible(true);
//            CrossoverScalingControl x = new CrossoverScalingControl();
//            x.setCrossover(0.1);
//            satVV.scaleToLayout(x);
//            Rectangle2D dim = satVV.getMaster().getBounds().getBounds2D();
//            satVV.setSize((int) dim.getMaxX(), (int) dim.getMaxY());
//            satVV.getRenderContext().setVertexFillPaintTransformer(new vertexPainter(mDataParser.getNodeTable()));
//            satVV.getRenderContext().setVertexLabelTransformer(new nodeLabeller(mDataParser.getNodeTable()));
//            this.add(satVV);
//            this.repaint();
//            satVV.repaint();

            //satVV.getGraphLayout().initialize();

        }
    }

    @Override
    public void componentClosed()
    {
        //this.removeAll();
        this.close();
    }

    public void ReCreateSatelliteViewer()
    {
        this.ClearContents();
        int w = this.getParent().getWidth();
        int h = this.getParent().getHeight();
        NetworkVVDisplay ndtc = (NetworkVVDisplay) WindowManager.getDefault().findTopComponent("NetworkDisplayTopComponent");
        
        satVV = new SatelliteVisualizationViewer<String, String>(ndtc.getVV(), new Dimension(w, h));
        satVV.setBackground(Color.white);
        satVV.setSize(w, h);
        satVV.setVisible(true);
        CrossoverScalingControl x = new CrossoverScalingControl();
        x.setCrossover(0.1);
        x.scale(satVV, 0.1f, satVV.getMaster().getCenter());
        satVV.scaleToLayout(x);
        satVV.scaleToLayout(new CrossoverScalingControl());
        //satVV.getRenderContext().setVertexFillPaintTransformer(new vertexPainter(mDataParser.getNodeTable(), mDataParser.getNetworkCaseSet()));
        //satVV.getRenderContext().setVertexLabelTransformer(new nodeLabeller(mDataParser.getNodeTable()));
        this.add(satVV);
        this.repaint();
        satVV.repaint();
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

    public void ClearContents()
    {
        if (satVV != null)
            this.remove(satVV);
        satVV = null;
        this.repaint();
    }
}
