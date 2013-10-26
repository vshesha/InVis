package invis.gl.graphviewer;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.visualization.LayeredIcon;
import edu.uci.ics.jung.visualization.decorators.DefaultVertexIconTransformer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import invis.gl.NetworkViewer.NetworkViewerTopComponent;
import invis.gl.NetworkViewer.StepbasedViewerTopComponent;
import invis.gl.NetworkVisualizationViewer.NetworkVisualizationViewer;
import invis.gl.Transformers.EdgeLabelTransformer;
import invis.gl.Transformers.NetworkElementGraphLabelFontTransformer;
import invis.gl.Transformers.HSLColor;
import invis.gl.Transformers.MixedDisplay.MixedVertexPainter;
import invis.gl.Transformers.MixedDisplay.MixedVertexShaper;
import invis.gl.Transformers.MultiVertexRenderer;
import invis.gl.Transformers.NodeLabelTransformer;
import invis.gl.Transformers.StepBasedEdgeLabelTransformer;
import invis.gl.Transformers.StepbasedEdgePainter;
import invis.gl.Transformers.VertexStandardShaper;
import invis.gl.Transformers.VertexStepbasedShaper;
import invis.gl.Transformers.edgePainter;
import invis.gl.Transformers.edgeStrokePainter;
import invis.gl.Transformers.stepBasedVertexPainter;
import invis.gl.Transformers.vertexPainter;
import invis.gl.Transformers.vertexStrokePainter;
import invis.gl.dataprocessor.DataParser;
import invis.gl.graphvisualapi.NetworkDisplayApi;
import invis.gl.graphvisualapi.NetworkDisplayApi.DisplayType;
import invis.gl.networkapi.NetworkCaseApi;
import invis.gl.networkapi.NetworkVVDisplay;
import invis.gl.networkapi.NodeViewerTopComponentExtension;
import invis.gl.networkapi.VisualEditorTopComponentExtension;
import invis.gl.sequenceview.SequenceViewTopComponent;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
@ConvertAsProperties(dtd = "-//invis.gl.graphviewer//NetworkDisplay//EN",
autostore = false)
@TopComponent.Description(preferredID = "NetworkDisplayTopComponent",
//iconBase="SET/PATH/TO/ICON/HERE", 
persistenceType = TopComponent.PERSISTENCE_NEVER)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "invis.gl.graphviewer.NetworkDisplayTopComponent")
@ActionReference(path = "Menu/Window" /*
 * , position = 333
 */)
@TopComponent.OpenActionRegistration(displayName = "#CTL_NetworkDisplayAction",
preferredID = "NetworkDisplayTopComponent")
@Messages(
{
    "CTL_NetworkDisplayAction=Network Display",
    "CTL_NetworkDisplayTopComponent=NetworkDisplay Window"
})
public final class NetworkDisplayTopComponent extends NetworkVVDisplay
{

    DisplayType mDisplayType  = null;
    
    public NetworkDisplayTopComponent()
    {
        mDataParser = Lookup.getDefault().lookup(DataParser.class);
        if (mDataParser.hasData())
        {
            mGraph = mDataParser.getGraph();
            initComponents();
            mDisplayType = DisplayType.NETWORK;
            this.loadVisualizationViewer(DisplayType.NETWORK);

            setName(NbBundle.getMessage(NetworkDisplayTopComponent.class, "CTL_NetworkDisplayTopComponent"));
            setToolTipText(NbBundle.getMessage(NetworkDisplayTopComponent.class, "HINT_NetworkDisplayTopComponent"));

        } else
        {
            this.close();
        }
    }

    public NetworkDisplayTopComponent(DirectedSparseMultigraph<String, String> graph, DisplayType type)
    {
        mDataParser = Lookup.getDefault().lookup(DataParser.class);
        mDisplayType = type;
        if (mDataParser.hasData())
        {
            mGraph = graph;
            initComponents();

            this.loadVisualizationViewer(type);

            setName(NbBundle.getMessage(NetworkDisplayTopComponent.class, "CTL_NetworkDisplayTopComponent"));
            setToolTipText(NbBundle.getMessage(NetworkDisplayTopComponent.class, "HINT_NetworkDisplayTopComponent"));

        } else
        {
            this.close();
        }
    }

    private void InitializeVisualizationViewer(DisplayType type)
    {
        mNVV = new NetworkVisualizationViewer(new CircleLayout<String, String>(mGraph), type);

        ExplorerManager em = ((NodeViewerTopComponentExtension) (WindowManager.getDefault().findTopComponent("NetworkViewerTopComponent"))).getExplorerManager();
        mNVV.AddChangeListenerToExplorerManager(em);
        em.addPropertyChangeListener(mNVV);

        em = ((NodeViewerTopComponentExtension) (WindowManager.getDefault().findTopComponent("StepbasedViewerTopComponent"))).getExplorerManager();
        mNVV.AddChangeListenerToExplorerManager(em);
        em.addPropertyChangeListener(mNVV);

        em = ((NodeViewerTopComponentExtension) (WindowManager.getDefault().findTopComponent("SequenceViewTopComponent"))).getExplorerManager();
        mNVV.AddChangeListenerToExplorerManager(em);
        em.addPropertyChangeListener(mNVV);

        em = ((NodeViewerTopComponentExtension) (WindowManager.getDefault().findTopComponent("EntropyViewerTopComponent"))).getExplorerManager();
        mNVV.AddChangeListenerToExplorerManager(em);
        em.addPropertyChangeListener(mNVV);

        em = ((NodeViewerTopComponentExtension) (WindowManager.getDefault().findTopComponent("BubbleViewerTopComponent"))).getExplorerManager();
        mNVV.AddChangeListenerToExplorerManager(em);
        em.addPropertyChangeListener(mNVV);
    }

    /**
     * Iterates through all the edges in mGraph and finds the edge with the
     * highest frequency.
     *
     * @return the frequency of that edge.
     *
     * private int findMaxFrequency(NetworkElementType type) { int maxFrequency
     * = 0;
     *
     * if (type == NetworkElementType.EDGE) { for (int i = 0; i <
     * mGraph.getEdgeCount(); i++) { String edgeKey =
     * mGraph.getEdges().toArray()[i].toString(); if
     * (mDataParser.getEdgeTable().get(edgeKey).getUniqueFrequency() >
     * maxFrequency) { maxFrequency =
     * mDataParser.getEdgeTable().get(edgeKey).getUniqueFrequency(); } } }
     *
     * if (type == NetworkElementType.NODE) { for (int i = 0; i <
     * mGraph.getVertexCount(); i++) { String vertexKey =
     * mGraph.getVertices().toArray()[i].toString(); if
     * (mDataParser.getNodeTable().get(vertexKey).getUniqueFrequency() >
     * maxFrequency) { maxFrequency =
     * mDataParser.getNodeTable().get(vertexKey).getUniqueFrequency(); } } }
     *
     * return (maxFrequency); }
     */
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        ColorCaseGroupIdsjButton = new javax.swing.JButton();
        LoadImageIconButton = new javax.swing.JButton();

        setAutoscrolls(true);
        addComponentListener(new java.awt.event.ComponentAdapter()
        {
            public void componentResized(java.awt.event.ComponentEvent evt)
            {
                formComponentResized(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(ColorCaseGroupIdsjButton, org.openide.util.NbBundle.getMessage(NetworkDisplayTopComponent.class, "NetworkDisplayTopComponent.ColorCaseGroupIdsjButton.text")); // NOI18N
        ColorCaseGroupIdsjButton.setToolTipText(org.openide.util.NbBundle.getMessage(NetworkDisplayTopComponent.class, "NetworkDisplayTopComponent.ColorCaseGroupIdsjButton.toolTipText")); // NOI18N
        ColorCaseGroupIdsjButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                ColorCaseGroupIdsjButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(LoadImageIconButton, org.openide.util.NbBundle.getMessage(NetworkDisplayTopComponent.class, "NetworkDisplayTopComponent.LoadImageIconButton.text")); // NOI18N
        LoadImageIconButton.setEnabled(false);
        LoadImageIconButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                LoadImageIconButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ColorCaseGroupIdsjButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LoadImageIconButton)
                .addContainerGap(784, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(323, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ColorCaseGroupIdsjButton)
                    .addComponent(LoadImageIconButton))
                .addGap(12, 12, 12))
        );
    }// </editor-fold>//GEN-END:initComponents

    public void ColorCaseGroupIdsRandomColors()
    {
        vertexPainter<String> vertexPainter = (vertexPainter) mNVV.getRenderContext().getVertexFillPaintTransformer();
        HashMap<String, Color> ClusterColorMap = new HashMap<String, Color>();

        vertexPainter.setMultiGroupColorMode(true);
        int clusterCount = mDataParser.getNetworkCaseSet().getUniqueCaseGroupIdCount();
        float Hue = 360.0f / clusterCount;
        if (clusterCount > 0)
        {
            //for (int i = 0; i < 360; i += 360 / clusterCount)
            for (int i = 0; i < clusterCount; i++)
            {

                float Saturation = (float) Math.random();

                if (Saturation < 0.25)
                {
                    Saturation += 0.25;
                }
                if (Saturation > 0.75)
                {
                    Saturation -= 0.25;
                }
                Saturation *= 100;

                float Luminance = (float) Math.random();

                if (Luminance < 0.25)
                {
                    Luminance += 0.25;
                }
                if (Luminance > 0.75)
                {
                    Luminance -= 0.25;
                }
                Luminance *= 100;
                String clusterId = mDataParser.getNetworkCaseSet().getUniqueCaseGroupIdSet().toArray()[i].toString();
                HSLColor hslColor = new HSLColor(i * Hue, Saturation, Luminance);
                ClusterColorMap.put(clusterId, hslColor.getRGB());
            }
        }
        vertexPainter.setClusterColorMap(ClusterColorMap);
        mNVV.getRenderContext().setVertexFillPaintTransformer(vertexPainter);
    }

    public void ColorCaseGroupIdsTwoGroupColorMap()
    {
        vertexPainter<String> vertexPainter = (vertexPainter) mNVV.getRenderContext().getVertexFillPaintTransformer();
        HashMap<String, Color> ClusterColorMap = new HashMap<String, Color>();
        vertexPainter.setTwoGroupColorMode(true);


        //TODO: need to write a check to confirm that only two groups are being loaded.
//        int clusterCount = mDataParser.getNetworkCaseSet().getUniqueCaseGroupIdCount();

        String firstGroup = mDataParser.getNetworkCaseSet().getUniqueCaseGroupIdSet().toArray()[0].toString();
        HSLColor hslColor = new HSLColor(77, 50, 100);
        ClusterColorMap.put(firstGroup, hslColor.getRGB());

        String secondGroup = mDataParser.getNetworkCaseSet().getUniqueCaseGroupIdSet().toArray()[1].toString();
        hslColor = new HSLColor(77, 50, 0);
        ClusterColorMap.put(secondGroup, hslColor.getRGB());

        vertexPainter.setCaseGroupIDs(firstGroup, secondGroup);

        vertexPainter.setClusterColorMap(ClusterColorMap);


        mNVV.getRenderContext().setVertexFillPaintTransformer(vertexPainter);

        HashMap<String, Double> ColorDataPerVertex = new HashMap<String, Double>();

        for (int i = 0; i < mGraph.getVertexCount(); i++)
        {
            Double ratio;
            String vertex = mGraph.getVertices().toArray()[i].toString();
            ArrayList<String> currentVertexCaseIdList = mDataParser.getNodeTable().get(vertex).getCaseIdList();
            HashMap<String, Integer> currentVertexCaseGroupIdFrequencyMap = new HashMap<String, Integer>();

            //Let's find the Group with the highest frequency on the current vertex.
            for (int j = 0; j < currentVertexCaseIdList.size(); j++)
            {
                NetworkCaseApi CaseI = mDataParser.getNetworkCaseSet().findCaseByCaseId(currentVertexCaseIdList.get(j));
                if (CaseI != null)
                {
                    String key = CaseI.getCaseGroupID();
                    if (currentVertexCaseGroupIdFrequencyMap.containsKey(key))
                    {
                        int elementCount = Integer.parseInt(currentVertexCaseGroupIdFrequencyMap.get(key).toString());
                        elementCount++;
                        currentVertexCaseGroupIdFrequencyMap.put(key, elementCount);

                    } else
                    {
                        currentVertexCaseGroupIdFrequencyMap.put(key, 1);
                    }
                }
            }

            Set<String> KeySet = currentVertexCaseGroupIdFrequencyMap.keySet();



            if (KeySet.size() == 1)
            {
                if (KeySet.toArray()[0].toString().equals(firstGroup))
                {
                    ratio = 1.0;
                } else//(grp2)
                {
                    ratio = 0.0;
                }
            } else
            {
                //TODO: If there is only 1 GroupID on the vertex then the array Set is of size 1.
                String FirstGroupKey = KeySet.toArray()[0].toString();
                Integer GroupOneFrequency = currentVertexCaseGroupIdFrequencyMap.get(FirstGroupKey);
                String SecondGroupKey = KeySet.toArray()[1].toString();
                Integer GroupTwoFrequency = currentVertexCaseGroupIdFrequencyMap.get(SecondGroupKey);

                ratio = (GroupOneFrequency.doubleValue() / (GroupOneFrequency.doubleValue() + GroupTwoFrequency.doubleValue()));

            }
            ColorDataPerVertex.put(vertex, ratio);
        }
        mDataParser.SetExtraDataTable(ColorDataPerVertex);
    }

    private void ColorCaseGroupIdsjButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_ColorCaseGroupIdsjButtonActionPerformed
    {//GEN-HEADEREND:event_ColorCaseGroupIdsjButtonActionPerformed
        //Set enabled based on...
        //mDataParser.DataContainsCaseGroupIds();
        if (mDataParser.DataContainsCaseGroupIds())
        {
            //Load a Jpanel, ask Random or ColorMap
            //DialogDescriptor dg = new DialogDescriptor(new CaseGroupIdColorizerTypeChooser(), "Case GroupIds Color Mode Chooser Window");
            //Dialog dd = DialogDisplayer.getDefault().createDialog(dg);
            //dd.setVisible(true);
            //dd.dispose();
            String[] buttons =
            {
                "Multi Group Random", "Two Group Gradient", "Cancel"
            };
            int rc = JOptionPane.showOptionDialog(null,
                    "Choose Desired Case-Group Color Mode",
                    "Color Mode Chooser",
                    JOptionPane.CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, buttons, buttons[2]);

            if (rc == 0)
            {
                this.ColorCaseGroupIdsRandomColors();
            } else if (rc == 1)
            {

                this.ColorCaseGroupIdsTwoGroupColorMap();
            } else
            {
                //user selected cancel.
            }
            mNVV.repaint();

        } else
        {
            JOptionPane.showMessageDialog(new JFrame(), "Warning: Cases do not have Goup-Ids which are necessary for coloring based on groups.\n"
                    + "Load data that has optional data fields, GROUP_ID.");
        }
    }//GEN-LAST:event_ColorCaseGroupIdsjButtonActionPerformed
    //Helper Function for loading Virtual Bead Loom Data

    private <V> void loadImage(Collection<V> vertices, Map<V, Icon> imageMap)
    {
        for (V v : vertices)
        {
            Icon icon =
                    new LayeredIcon(
                    new ImageIcon("BeadloomData\\iconOutput\\"
                    + v.toString()).getImage());
            imageMap.put(v, icon);
        }
    }
    private void LoadImageIconButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_LoadImageIconButtonActionPerformed
    {//GEN-HEADEREND:event_LoadImageIconButtonActionPerformed
        DefaultVertexIconTransformer<String> vertexIconFunction = new DefaultVertexIconTransformer<String>();
        mNVV.getRenderer().setVertexRenderer(new MultiVertexRenderer<String, String>());

        //Load Images, this is specific to VBL data
        loadImage(mGraph.getVertices(), vertexIconFunction.getIconMap());
        vertexIconFunction.setIconMap(vertexIconFunction.getIconMap());


        //Needed to connect Image Map to the Icon and Shape Transformers
        mNVV.getRenderContext().setVertexIconTransformer(vertexIconFunction);
        mNVV.getRenderer().setVertexRenderer(new MultiVertexRenderer<String, String>());
    }//GEN-LAST:event_LoadImageIconButtonActionPerformed

    private void formComponentResized(java.awt.event.ComponentEvent evt)//GEN-FIRST:event_formComponentResized
    {//GEN-HEADEREND:event_formComponentResized
        sizeVisualizationViewer();
    }//GEN-LAST:event_formComponentResized
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ColorCaseGroupIdsjButton;
    private javax.swing.JButton LoadImageIconButton;
    // End of variables declaration//GEN-END:variables

    @Override
    public void componentOpened()
    {
        if (mDataParser.hasData())
        {
            this.initComponents();
            //The next line is important, beceuase it is what allows new windows to, particularly node-viewers, to link to the mNVV.
            //This facilitates clicking Netbean Nodes, and having the respective nodes get highlighted in the Visualization Viewer.
            this.loadVisualizationViewer(mDisplayType);
        }
        this.repaint();
    }

    @Override
    public void componentActivated()
    {
        if (mDataParser != null && mDataParser.hasData())
        {
            VisualEditorTopComponentExtension vetc = (VisualEditorTopComponentExtension) WindowManager.getDefault().findTopComponent("VisualEditorTopComponent");

            if (vetc != null && vetc.isOpened())
            {
                vetc.UpdateCurrentVV(mNVV, mNVV.getDisplayType());
            }
            NodeViewerTopComponentExtension evtc = (NodeViewerTopComponentExtension) WindowManager.getDefault().findTopComponent("EntropyViewerTopComponent");
            if (evtc != null && evtc.isOpened())
            {
                evtc.UpdateCurrentVV(mNVV, mNVV.getDisplayType());
            }

            VisualEditorTopComponentExtension netc = (VisualEditorTopComponentExtension) WindowManager.getDefault().findTopComponent("NetworkEditorTopComponent");
            if (netc != null && netc.isOpened())
            {
                netc.UpdateCurrentVV(mNVV, mNVV.getDisplayType());
            }
            NodeViewerTopComponentExtension bvtc = (NodeViewerTopComponentExtension) WindowManager.getDefault().findTopComponent("BubbleViewerTopComponent");
            if (bvtc != null && bvtc.isOpened())
            {
                bvtc.UpdateCurrentVV(mNVV, mNVV.getDisplayType());
            }
        } else
        {
            this.close();
        }
    }

    @Override
    public void RebroadcastNetworkVisualizationViewer()
    {
        this.componentActivated();
    }

    public void loadVisualizationViewer(DisplayType type)
    {
        //this.RefreshGraph();
        if (mDataParser.hasData())
        {
            InitializeVisualizationViewer(type);
            if (type == DisplayType.NETWORK)
            {
                mGraph = mNVV.getDagGraph();
                this.InitializeNetworkVisuals();
            }
            if (type == DisplayType.STEPBASED)
            {
                mGraph = mNVV.getDagGraph();
                this.InitializeStepbasedVisuals();
            }
            if (type == DisplayType.MIXED)
            {
                mGraph = mNVV.getDagGraph();
                this.InitializeMixedVisuals();
            }
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
        mNVV.setSize(w, h - 42);
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

            em = ((SequenceViewTopComponent) (WindowManager.getDefault().findTopComponent("SequenceViewTopComponent"))).getExplorerManager();
            mNVV.RemoveChangeListenerToExplorerManager(em);
            em.removePropertyChangeListener(mNVV);

            //this.RefreshGraph();

            mDataParser.getNetworkVVLookup().RemoveNetworkVV(mNVV);

            mNVV = null;
        }
        this.repaint();
    }

    private void InitializeNetworkVisuals()
    {

        buildCaseFrequencyEdgeWeightMap();

        mNVV.setBackground(Color.white);

        //mNVV.getRenderContext().setVertexStrokeTransformer(new vertexStrokePainter(mDataParser.getNodeTable()));
        mNVV.getRenderContext().setVertexFillPaintTransformer(new vertexPainter<String>(mDataParser.getNodeTable(), mDataParser.getNetworkCaseSet()));
        mNVV.getRenderContext().setVertexLabelTransformer(new NodeLabelTransformer<String>(mDataParser.getNodeTable(), mDataParser.DataContainsStateLabel()));
        mNVV.getRenderContext().setVertexShapeTransformer(new VertexStandardShaper<String>(mDataParser.getNodeTable()));
        mNVV.getRenderContext().setVertexStrokeTransformer(new vertexStrokePainter(mNVV.getPickedVertexState()));
        mNVV.getRenderContext().setVertexFontTransformer(new NetworkElementGraphLabelFontTransformer<String>(mDataParser.getNodeTable()));

        ((NodeLabelTransformer) mNVV.getRenderContext().getVertexLabelTransformer()).setStateDescriptionFlag(false);
        ((NodeLabelTransformer) mNVV.getRenderContext().getVertexLabelTransformer()).setClusterIDFlag(false);
        ((NodeLabelTransformer) mNVV.getRenderContext().getVertexLabelTransformer()).setMDPValueFlag(false);
        ((NodeLabelTransformer) mNVV.getRenderContext().getVertexLabelTransformer()).setParametersFlag(false);
        ((NodeLabelTransformer) mNVV.getRenderContext().getVertexLabelTransformer()).setLabelTitleFlag(false);
        ((NodeLabelTransformer) mNVV.getRenderContext().getVertexLabelTransformer()).setInteractionIndexFlag(false);


        mNVV.getRenderContext().setEdgeLabelTransformer(new EdgeLabelTransformer(mDataParser.getEdgeTable(), mDataParser.DataContainsActionLabel()));
        mNVV.getRenderContext().setEdgeStrokeTransformer(new edgeStrokePainter(buildCaseFrequencyEdgeWeightMap(), buildUniqueCaseFrequencyEdgeWeightMap(), true));
        mNVV.getRenderContext().setEdgeDrawPaintTransformer(new edgePainter(mDataParser.getEdgeTable()));
        mNVV.getRenderContext().setEdgeFontTransformer(new NetworkElementGraphLabelFontTransformer<String>(mDataParser.getEdgeTable()));

        ((EdgeLabelTransformer) mNVV.getRenderContext().getEdgeLabelTransformer()).setLabelTitle(false);
        ((EdgeLabelTransformer) mNVV.getRenderContext().getEdgeLabelTransformer()).setActionDescriptionFlag(false);
        ((EdgeLabelTransformer) mNVV.getRenderContext().getEdgeLabelTransformer()).setFrequencyFlag(false);
        ((EdgeLabelTransformer) mNVV.getRenderContext().getEdgeLabelTransformer()).setUniqueFrequencyFlag(false);
        ((EdgeLabelTransformer) mNVV.getRenderContext().getEdgeLabelTransformer()).setBetweennessFlag(false);
        ((EdgeLabelTransformer) mNVV.getRenderContext().getEdgeLabelTransformer()).setParametersFlag(false);
    }

    private void InitializeStepbasedVisuals()
    {
        mNVV.setBackground(Color.white);


        //mNVV.getRenderContext().setVertexLabelTransformer(new NodeLabelTransformer(mDataParser.getNodeTable(), mDataParser.DataContainsStateLabel()));
        //mNVV.getRenderContext().setVertexStrokeTransformer(new vertexStrokePainter(mDataParser.getNodeTable()));

        mNVV.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<String>());

        mNVV.getRenderContext().setVertexFillPaintTransformer(new stepBasedVertexPainter<String>(mDataParser.getDerivedData().getNodeTable()));
        mNVV.getRenderContext().setVertexShapeTransformer(new VertexStepbasedShaper(mDataParser.getDerivedData().getNodeTable()));
        ((VertexStepbasedShaper) (mNVV.getRenderContext().getVertexShapeTransformer())).UpdateVertexFrequencyMap(this.buildUniqueCaseFrequencyVertexWeightMap());
        mNVV.getRenderContext().setVertexStrokeTransformer(new vertexStrokePainter<String>(mNVV.getPickedVertexState()));
        mNVV.getRenderContext().setVertexFontTransformer(new NetworkElementGraphLabelFontTransformer<String>());

        //mNVV.getRenderContext().setEdgeLabelTransformer(new EdgeLabelTransformer(mDataParser.getEdgeTable(), mDataParser.DataContainsActionLabel()));
        //mNVV.getRenderContext().setEdgeStrokeTransformer(new edgeStrokePainter<String>(buildCaseFrequencyEdgeWeightMap(), buildUniqueCaseFrequencyEdgeWeightMap(), buildNetworkElementEdgeWeightMap(), true));
        mNVV.getRenderContext().setEdgeLabelTransformer(new StepBasedEdgeLabelTransformer(mDataParser.getDerivedData().getEdgeTable()));
        mNVV.getRenderContext().setEdgeDrawPaintTransformer(new StepbasedEdgePainter<String>(mDataParser.getDerivedData().getEdgeTable()));
        mNVV.getRenderContext().setEdgeFontTransformer(new NetworkElementGraphLabelFontTransformer<String>());
    }

    private void InitializeMixedVisuals()
    {
        mNVV.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<String>());
        mNVV.getRenderContext().setVertexFillPaintTransformer(new MixedVertexPainter<String>(mDataParser.getNodeTable(),mDataParser.getDerivedData().getNodeTable()));
        mNVV.getRenderContext().setVertexShapeTransformer(new MixedVertexShaper(mDataParser.getDerivedData().getNodeTable(), mDataParser.getNodeTable()));
//        ((VertexStepbasedShaper) (mNVV.getRenderContext().getVertexShapeTransformer())).UpdateVertexFrequencyMap(this.buildUniqueCaseFrequencyVertexWeightMap());
        mNVV.getRenderContext().setVertexStrokeTransformer(new vertexStrokePainter<String>(mNVV.getPickedVertexState()));
        mNVV.getRenderContext().setVertexFontTransformer(new NetworkElementGraphLabelFontTransformer<String>());

        mNVV.getRenderContext().setEdgeLabelTransformer(new StepBasedEdgeLabelTransformer(mDataParser.getDerivedData().getEdgeTable()));
        mNVV.getRenderContext().setEdgeDrawPaintTransformer(new StepbasedEdgePainter<String>(mDataParser.getDerivedData().getEdgeTable()));
        mNVV.getRenderContext().setEdgeFontTransformer(new NetworkElementGraphLabelFontTransformer<String>());
    }

    private Map<String, Number> buildCaseFrequencyEdgeWeightMap()
    {
        Map<String, Number> edgeWeightMap = new HashMap<String, Number>();
        for (int i = 0; i < mGraph.getEdgeCount(); i++)
        {
            String edgeKey = mGraph.getEdges().toArray()[i].toString();
            Number edgeCaseFrequency = mDataParser.getEdgeTable().get(edgeKey).getCaseFrequency();
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
            Number edgeCaseFrequency = mDataParser.getEdgeTable().get(edgeKey).getUniqueFrequency();
            edgeWeightMap.put(edgeKey, edgeCaseFrequency);
        }
        return edgeWeightMap;
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

    private HashMap<String, Double> buildUniqueCaseFrequencyVertexWeightMap()
    {
        HashMap<String, Double> vertexWeightMap = new HashMap<String, Double>();
        for (int i = 0; i < mGraph.getVertexCount(); i++)
        {
            String vertKey = mGraph.getVertices().toArray()[i].toString();
            Double vertexCaseFrequency = 0.0;
            if (mDataParser.getDerivedData().getNodeTable().containsKey(vertKey))
            {
                for (int j = 0; j < mDataParser.getDerivedData().getNodeTable().get(vertKey).getNetworkElementSet().size(); j++)
                {
                    vertexCaseFrequency += mDataParser.getDerivedData().getNodeTable().get(vertKey).getNetworkElementSet().get(j).getUniqueFrequency();
                }
                vertexWeightMap.put(vertKey, vertexCaseFrequency);
            }
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
}
