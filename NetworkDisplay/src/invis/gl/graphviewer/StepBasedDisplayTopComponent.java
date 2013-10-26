package invis.gl.graphviewer;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import invis.gl.NetworkViewer.NetworkViewerTopComponent;
import invis.gl.NetworkViewer.StepbasedViewerTopComponent;
import invis.gl.NetworkVisualizationViewer.NetworkVisualizationViewer;
import invis.gl.Transformers.NetworkElementGraphLabelFontTransformer;
import invis.gl.Transformers.StepBasedEdgeLabelTransformer;
import invis.gl.Transformers.StepbasedEdgePainter;
import invis.gl.Transformers.VertexStepbasedShaper;
import invis.gl.Transformers.edgeStrokePainter;
import invis.gl.Transformers.stepBasedVertexPainter;
import invis.gl.Transformers.vertexStrokePainter;
import invis.gl.dataprocessor.DataParser;
import invis.gl.graphvisualapi.NetworkDisplayApi.DisplayType;
import invis.gl.networkapi.NetworkVVDisplay;
import invis.gl.networkapi.NodeViewerTopComponentExtension;
import invis.gl.networkapi.VisualEditorTopComponentExtension;
import invis.gl.sequenceview.SequenceViewTopComponent;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.explorer.ExplorerManager;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
    dtd = "-//invis.gl.graphviewer//StepBasedDisplay//EN",
autostore = false)
@TopComponent.Description(
    preferredID = "StepBasedDisplayTopComponent",
//iconBase="SET/PATH/TO/ICON/HERE", 
persistenceType = TopComponent.PERSISTENCE_ONLY_OPENED)
@TopComponent.Registration(mode = "output", openAtStartup = false)
@ActionID(category = "Window", id = "invis.gl.graphviewer.StepBasedDisplayTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
    displayName = "#CTL_StepBasedDisplayAction",
preferredID = "StepBasedDisplayTopComponent")
@Messages(
{
    "CTL_StepBasedDisplayAction=Stepbased Display",
    "CTL_StepBasedDisplayTopComponent=Stepbased Display",
    "HINT_StepBasedDisplayTopComponent=This is a Stepbased Display window"
})
public final class StepBasedDisplayTopComponent extends NetworkVVDisplay
{

    public StepBasedDisplayTopComponent()
    {
        mDataParser = Lookup.getDefault().lookup(DataParser.class);

        if (mDataParser != null && !mDataParser.HasDerivedData())
        {
            mDataParser.BuildStepBasedData();
        }
        if (mDataParser != null && mDataParser.HasDerivedData())
        {
            mGraph = mDataParser.getDerivedData().getGraph();
            this.InitializeVisualizationViewer();
            initComponents();
            setName(NbBundle.getMessage(StepBasedDisplayTopComponent.class, "CTL_StepBasedDisplayTopComponent"));
            setToolTipText(NbBundle.getMessage(StepBasedDisplayTopComponent.class, "HINT_StepBasedDisplayTopComponent"));
        }
    }

    public StepBasedDisplayTopComponent(DirectedSparseMultigraph<String, String> dagGraph)
    {
        mGraph = dagGraph;
        this.InitializeVisualizationViewer();
        initComponents();
        setName(NbBundle.getMessage(StepBasedDisplayTopComponent.class, "CTL_StepBasedDisplayTopComponent"));
        setToolTipText(NbBundle.getMessage(StepBasedDisplayTopComponent.class, "HINT_StepBasedDisplayTopComponent"));
    }

    private void InitializeVisualizationViewer()
    {

        mNVV = new NetworkVisualizationViewer(new CircleLayout<String, String>(mGraph), DisplayType.STEPBASED);

        /* These next three lines are really important for connecting the Jung graph to the Nodes-API */
        ExplorerManager em = ((NetworkViewerTopComponent) (WindowManager.getDefault().findTopComponent("NetworkViewerTopComponent"))).getExplorerManager();
        mNVV.AddChangeListenerToExplorerManager(em);
        em.addPropertyChangeListener(mNVV);

        em = ((StepbasedViewerTopComponent) (WindowManager.getDefault().findTopComponent("StepbasedViewerTopComponent"))).getExplorerManager();
        mNVV.AddChangeListenerToExplorerManager(em);
        em.addPropertyChangeListener(mNVV);

        //These let hook up the ExplorerManager to the VisualizationViewer, meaning clicks in the ExplorerManager will fire the property change
        //event in the VisualizationViewer.
        em = ((SequenceViewTopComponent) (WindowManager.getDefault().findTopComponent("SequenceViewTopComponent"))).getExplorerManager();
        mNVV.AddChangeListenerToExplorerManager(em);
        em.addPropertyChangeListener(mNVV);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        addComponentListener(new java.awt.event.ComponentAdapter()
        {
            public void componentResized(java.awt.event.ComponentEvent evt)
            {
                formComponentResized(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt)//GEN-FIRST:event_formComponentResized
    {//GEN-HEADEREND:event_formComponentResized
        this.sizeVisualizationViewer();

    }//GEN-LAST:event_formComponentResized

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened()
    {
        if (mDataParser != null && mDataParser.hasData())
        {
            this.setName("Stepbased Graph");
            this.initComponents();
            this.loadVisualizationViewer();
        }
        this.repaint();
    }

    @Override
    public void componentActivated()
    {
        mDataParser = Lookup.getDefault().lookup(DataParser.class);
        if (mDataParser != null && mDataParser.hasData())
        {
            NodeViewerTopComponentExtension svtc = (NodeViewerTopComponentExtension) WindowManager.getDefault().findTopComponent("StepbasedViewerTopComponent");
            if (svtc != null && svtc.isOpened())
            {
                ((VertexStepbasedShaper) (mNVV.getRenderContext().getVertexShapeTransformer())).UpdateVertexFrequencyMap(this.buildUniqueCaseFrequencyVertexWeightMap());
            }

            VisualEditorTopComponentExtension vetc = (VisualEditorTopComponentExtension) WindowManager.getDefault().findTopComponent("VisualEditorTopComponent");
            if (vetc != null)
            {
                vetc.UpdateCurrentVV(mNVV, DisplayType.STEPBASED);
            }

            VisualEditorTopComponentExtension netc = (VisualEditorTopComponentExtension) WindowManager.getDefault().findTopComponent("NetworkEditorTopComponent");
            if (netc != null && netc.isOpened())
            {
                netc.UpdateCurrentVV(mNVV, DisplayType.STEPBASED);
            }
        } else
        {
            this.close();
        }
        //Update the Minimap-Overview...
    }

    private HashMap<String, Double> buildUniqueCaseFrequencyVertexWeightMap()
    {
        HashMap<String, Double> vertexWeightMap = new HashMap<String, Double>();
        for (int i = 0; i < mGraph.getVertexCount(); i++)
        {
            String vertKey = mGraph.getVertices().toArray()[i].toString();
            Double vertexCaseFrequency = 0.0;
            for (int j = 0; j < mDataParser.getDerivedData().getNodeTable().get(vertKey).getNetworkElementSet().size(); j++)
            {
                vertexCaseFrequency += mDataParser.getDerivedData().getNodeTable().get(vertKey).getNetworkElementSet().get(j).getUniqueFrequency();
            }
            vertexWeightMap.put(vertKey, vertexCaseFrequency);
        }
        return vertexWeightMap;
    }

    private Map<String, Number> buildNetworkElementEdgeWeightMap()
    {
        Map<String, Number> edgeWeightMap = new HashMap<String, Number>();
        for (int i = 0; i < mGraph.getEdgeCount(); i++)
        {
            String edgeKey = mGraph.getEdges().toArray()[i].toString();
            Number edgeFrequency = mDataParser.getDerivedData().getEdgeTable().get(edgeKey).getNetworkElementSet().size();
            edgeWeightMap.put(edgeKey, edgeFrequency);
        }
        return edgeWeightMap;
    }

    private Map<String, Number> buildCaseFrequencyEdgeWeightMap()
    {
        Map<String, Number> edgeWeightMap = new HashMap<String, Number>();
        for (int i = 0; i < mGraph.getEdgeCount(); i++)
        {
            String edgeKey = mGraph.getEdges().toArray()[i].toString();
            Integer edgeCaseFrequency = 0;
            for (int j = 0; j < mDataParser.getDerivedData().getEdgeTable().get(edgeKey).getNetworkElementSet().size(); j++)
            {
                edgeCaseFrequency += mDataParser.getDerivedData().getEdgeTable().get(edgeKey).getNetworkElementSet().get(j).getCaseFrequency();
            }
            edgeWeightMap.put(edgeKey, edgeCaseFrequency);
        }
        return edgeWeightMap;
    }

    private Map<String, Number> buildUniqueCaseFrequencyEdgeWeightMap()
    {
        Map<String, Number> edgeWeightMap = new HashMap<String, Number>();
        for (int i = 0; i < mGraph.getEdgeCount(); i++)
        {
            String edgeKey = mGraph.getEdges().toArray()[i].toString();
            Integer edgeCaseFrequency = 0;
            for (int j = 0; j < mDataParser.getDerivedData().getEdgeTable().get(edgeKey).getNetworkElementSet().size(); j++)
            {
                edgeCaseFrequency += mDataParser.getDerivedData().getEdgeTable().get(edgeKey).getNetworkElementSet().get(j).getUniqueFrequency();
            }
            edgeWeightMap.put(edgeKey, edgeCaseFrequency);
        }
        return edgeWeightMap;
    }

    private void BuildVisualizationViewer()
    {
        mNVV.setBackground(Color.white);
        mNVV.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<String>());

        mNVV.getRenderContext().setEdgeLabelTransformer(new StepBasedEdgeLabelTransformer(mDataParser.getDerivedData().getEdgeTable()));


        mNVV.getRenderContext().setVertexFillPaintTransformer(new stepBasedVertexPainter<String>(mDataParser.getDerivedData().getNodeTable()));
        //mNVV.getRenderContext().setVertexLabelTransformer(new NodeLabelTransformer(mDataParser.getNodeTable(), mDataParser.DataContainsStateLabel()));
        mNVV.getRenderContext().setVertexShapeTransformer(new VertexStepbasedShaper(mDataParser.getDerivedData().getNodeTable()));
        ((VertexStepbasedShaper) (mNVV.getRenderContext().getVertexShapeTransformer())).UpdateVertexFrequencyMap(this.buildUniqueCaseFrequencyVertexWeightMap());
        //mNVV.getRenderContext().setVertexStrokeTransformer(new vertexStrokePainter(mDataParser.getNodeTable()));
        mNVV.getRenderContext().setVertexStrokeTransformer(new vertexStrokePainter<String>(mNVV.getPickedVertexState()));
        mNVV.getRenderContext().setVertexFontTransformer(new NetworkElementGraphLabelFontTransformer<String>());

        //mNVV.getRenderContext().setEdgeLabelTransformer(new EdgeLabelTransformer(mDataParser.getEdgeTable(), mDataParser.DataContainsActionLabel()));
        mNVV.getRenderContext().setEdgeStrokeTransformer(new edgeStrokePainter<String>(buildCaseFrequencyEdgeWeightMap(), buildUniqueCaseFrequencyEdgeWeightMap(), buildNetworkElementEdgeWeightMap(), true));
        mNVV.getRenderContext().setEdgeDrawPaintTransformer(new StepbasedEdgePainter<String>(mDataParser.getDerivedData().getEdgeTable()));
        mNVV.getRenderContext().setEdgeFontTransformer(new NetworkElementGraphLabelFontTransformer<String>());

    }

    public void loadVisualizationViewer()
    {
        this.RefreshGraph();
        if (mDataParser.HasDerivedData())
        {
            if (mNVV == null)
            {
                this.InitializeVisualizationViewer();
            }
            this.BuildVisualizationViewer();
            this.sizeVisualizationViewer();
            this.add(mNVV);
            this.RefreshVisualizationViewer();
        }
    }

    @Override
    protected void sizeVisualizationViewer()
    {
        int w = this.getWidth();
        int h = this.getHeight();
        mNVV.setSize(w, h);
    }

    @Override
    public void RefreshGraph()
    {
        mDataParser = Lookup.getDefault().lookup(DataParser.class);
        if (mDataParser.hasData())
        {
            mDataParser.BuildStepBasedData();
            mGraph = mDataParser.getDerivedData().getGraph();
        }
    }

    public void ClearContents()
    {
        if (mNVV != null)
        {
            this.remove(mNVV);

            ExplorerManager em = ((NetworkViewerTopComponent) (WindowManager.getDefault().findTopComponent("NetworkViewerTopComponent"))).getExplorerManager();
            mNVV.RemoveChangeListenerToExplorerManager(em);
            em.removePropertyChangeListener(mNVV);

            em = ((StepbasedViewerTopComponent) (WindowManager.getDefault().findTopComponent("StepbasedViewerTopComponent"))).getExplorerManager();
            mNVV.RemoveChangeListenerToExplorerManager(em);
            em.removePropertyChangeListener(mNVV);

            //These let hook up the ExplorerManager to the VisualizationViewer, meaning clicks in the ExplorerManager will fire the property change
            //event in the VisualizationViewer.
            em = ((SequenceViewTopComponent) (WindowManager.getDefault().findTopComponent("SequenceViewTopComponent"))).getExplorerManager();
            mNVV.RemoveChangeListenerToExplorerManager(em);
            em.removePropertyChangeListener(mNVV);

            mNVV = null;
        }
        this.repaint();
    }

    @Override
    public void RebroadcastNetworkVisualizationViewer()
    {
        throw new UnsupportedOperationException("Not supported yet.");
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
    }
}
