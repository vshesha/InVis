package invis.gl.NetworkVisualizationViewer;

import edu.uci.ics.jung.algorithms.filters.KNeighborhoodFilter;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.PluggableGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingGraphMousePlugin;
import edu.uci.ics.jung.visualization.subLayout.GraphCollapser;
import invis.gl.NetworkClusterApi.NetworkClusterElementApi;
import invis.gl.NetworkViewerNodes.NetworkCaseNode;
import invis.gl.NetworkViewerNodes.NetworkEdgeNode;
import invis.gl.NetworkViewerNodes.NetworkInteractionsNode;
import invis.gl.NetworkViewerNodes.NetworkSequenceNode;
import invis.gl.NetworkViewerNodes.NetworkVertexNode;
import invis.gl.bubbleNodes.BubbleNode;
import invis.gl.bubbleNodes.PathNode;
import invis.gl.dataprocessor.DataParser;
import invis.gl.graphvisualapi.NetworkDisplayApi.DisplayType;
import invis.gl.networkapi.NetworkCaseApi;
import invis.gl.networkapi.NetworkElementApi;
import invis.gl.networkapi.NetworkElementApi.NetworkElementType;
import invis.gl.sequenceapi.NetworkInteractionSequenceApi;
import invis.gl.stepbasednodes.StepBasedEdgeNode;
import invis.gl.stepbasednodes.StepBasedVertexNode;
import invis.gl.stepbasednodes.StepbasedSequenceNode;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.openide.awt.StatusDisplayer;
import org.openide.explorer.ExplorerManager;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.windows.WindowManager;

/**
 *
 * @author Matt
 */
// In order to look instances of this object up, I believe I need to add that instance
// to an instanceContent.
public class NetworkVisualizationViewer extends VisualizationViewer<String, String> implements PropertyChangeListener, ItemListener
{

    private PluggableGraphMouse pgm;
    private DataParser mDataParser;
    private DisplayType mDisplayType;
    
    private GraphCollapser mCollapser;

    public NetworkVisualizationViewer()
    {
        this(null, null);
    }

    public NetworkVisualizationViewer(Layout layout, DisplayType displayType)
    {
        super(layout);
        mDataParser = Lookup.getDefault().lookup(DataParser.class);
        mDataParser.getNetworkVVLookup().AddNetworkVV(this);

        pgm = new PluggableGraphMouse();
        pgm.add(new TranslatingGraphMousePlugin_mwj(MouseEvent.BUTTON3_MASK));
        pgm.add(new ScalingGraphMousePlugin(new CrossoverScalingControl(), 0, 1 / 1.1f, 1.1f));
        pgm.add(new InVisGraphMousePlugin<String, String>(this));

        mDisplayType = displayType;
        mCollapser = new GraphCollapser(this.getGraphLayout().getGraph());

        this.setGraphMouse(pgm);
        this.getPickedVertexState().addItemListener(this);
        this.getPickedEdgeState().addItemListener(this);
    }
    
    public GraphCollapser getGraphCollapser()
    {
        return (mCollapser);
    }
    
    public DisplayType getDisplayType()
    {
        return (mDisplayType);
    }

    public DirectedSparseMultigraph<String, String> getDagGraph()
    {
        DirectedSparseMultigraph<String, String> g = new DirectedSparseMultigraph();
        g = (DirectedSparseMultigraph<String, String>) this.getGraphLayout().getGraph();
        return (g);
    }

    public void AddChangeListenerToExplorerManager(ExplorerManager em)
    {
        em.addPropertyChangeListener(this);
    }

    public void RemoveChangeListenerToExplorerManager(ExplorerManager em)
    {
        em.removePropertyChangeListener(this);
    }

    public void ClearSelected()
    {
        //Clear the current selection.
        for (int i = 0; i < this.getPickedEdgeState().getPicked().size(); i++)
        {
            String edge = this.getPickedEdgeState().getPicked().toArray()[i].toString();
            if (mDataParser.getEdgeTable().containsKey(edge))
            {
                mDataParser.getEdgeTable().get(edge).setSelected(false);
            }
            if (mDataParser.getDerivedData().getEdgeTable().containsKey(edge))
            {
                mDataParser.getDerivedData().getEdgeTable().get(edge).setSelected(false);
            }
        }
        this.getPickedEdgeState().clear();

        for (int i = 0; i < this.getPickedVertexState().getPicked().size(); i++)
        {
            String vertex = this.getPickedVertexState().getPicked().toArray()[i].toString();

            if (mDataParser.getNodeTable().containsKey(vertex))
            {
                mDataParser.getNodeTable().get(vertex).setSelected(false);
            }

            if (mDataParser.getDerivedData().getNodeTable().containsKey(vertex))
            {
                mDataParser.getDerivedData().getNodeTable().get(vertex).setSelected(false);
            }
        }
        this.getPickedVertexState().clear();
    }

    public void PickGraphElement(NetworkElementApi element)
    {
        this.PickGraphElement(element.getValue(), element.getNetworkElementType());
    }

    public void PickGraphElement(Collection<NetworkElementApi> elements)
    {
        for (int i = 0; i < elements.size(); i++)
        {
            this.PickGraphElement((NetworkElementApi) elements.toArray()[i]);
        }
    }

    public void PickGraphElement(String element, NetworkElementType type)
    {
        if (type == NetworkElementType.EDGE)
        {
            //if (mDataParser.getGraph().containsEdge(element))
            if (this.getGraphLayout().getGraph().containsEdge(element))
            {

                this.getPickedEdgeState().pick(element, true);
                mDataParser.getEdgeTable().get(element).setSelected(true);
            }
        }
        if (type == NetworkElementType.NODE)
        {
            //if (mDataParser.getGraph().containsVertex(element))
            if (this.getGraphLayout().getGraph().containsVertex(element))
            {

                this.getPickedVertexState().pick(element, true);
                mDataParser.getNodeTable().get(element).setSelected(true);
            }
        }
    }

    public void PickGraphElement(Collection<String> elements, NetworkElementType type)
    {
        for (int i = 0; i < elements.size(); i++)
        {
            for (String element : elements)
            {
                this.PickGraphElement(element, type);
            }
        }
    }

    public void PickGraphClusterElement(NetworkClusterElementApi nce)
    {
        this.PickGraphClusterElement(nce.getValue());
    }

    public void PickGraphClusterElement(String ClusterElement)
    {
        //if (type == NetworkElementType.EDGE)
        //{
        //if (mDataParser.getGraph().containsEdge(element))
        if (this.getGraphLayout().getGraph().containsEdge(ClusterElement))
        {

            this.getPickedEdgeState().pick(ClusterElement, true);
            mDataParser.getDerivedData().getEdgeTable().get(ClusterElement).setSelected(true);
        }
        //}
        //if (type == NetworkElementType.NODE)
        //{
        //if (mDataParser.getGraph().containsVertex(element))
        if (this.getGraphLayout().getGraph().containsVertex(ClusterElement))
        {

            this.getPickedVertexState().pick(ClusterElement, true);
            mDataParser.getDerivedData().getNodeTable().get(ClusterElement).setSelected(true);
        }
        //}
    }

    /**
     *
     * @param state the value of the visibility, true for visible, false for
     * invisible
     * @param type the type of NetworkElement, which is either Edge or Node,
     * check the NetworkElementType Enum.
     */
    public void setHideNetworkLabels(boolean state, NetworkElementType type)
    {
        DataParser dataParser = Lookup.getDefault().lookup(DataParser.class);

        Collection<? extends NetworkElementApi> NetworkElements = (Collection<? extends NetworkElementApi>) dataParser.GetDataNetwork().getLookup().lookupAll(NetworkElementApi.class);

        for (NetworkElementApi ne : NetworkElements)
        {
            if (ne.getNetworkElementType() == type)
            {
                ne.setLabelVisibility(state);
            }
        }
        this.repaint();
    }

    /**
     * This handles the selection of Nodes in the NB-Platform Nodes-API. The
     * resulting vertices in the Jung Graphs will become selected.
     *
     * @param evt
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getPropertyName().equals("selectedNodes"))
        {
            ExplorerManager EM = (ExplorerManager) evt.getSource();
            Node[] SelectedNodes = EM.getSelectedNodes();
            this.ClearSelected();

            //Select the selected nodes.
            for (int i = 0; i < SelectedNodes.length; i++)
            {
                //Get the class of nodes we want.
                //if (SelectedNodes[i].getClass().getName().compareTo("invis.gl.NetworkViewerNodes.NetworkVertexNode") == 0)
                if (SelectedNodes[i] instanceof NetworkVertexNode)
                {

                    if (mDataParser.getNodeTable().containsKey(((NetworkVertexNode) (SelectedNodes[i])).getData().getValue()))
                    {
                        //this.getPickedVertexState().pick(((NetworkVertexNode) (SelectedNodes[i])).getData().getValue(), true);
                        this.PickGraphElement(((NetworkVertexNode) (SelectedNodes[i])).getData());
                    }
                }

                //Handle the clicking of Edge-Nodes in the Viewer (BeanTree)
                //if (SelectedNodes[i].getClass().getName().compareTo("invis.gl.NetworkViewerNodes.NetworkEdgeNode") == 0)
                if (SelectedNodes[i] instanceof NetworkEdgeNode)
                {
                    //Do the above for EDGES.
                    //Find the vertex in the graph that has the same data as the selected node from the explorerManager.
                    if (mDataParser.getEdgeTable().containsKey(((NetworkEdgeNode) (SelectedNodes[i])).getData().getValue()))
                    {
                        //Set the graph-node to be picked.
                        //this.getPickedEdgeState().pick(((NetworkEdgeNode) (SelectedNodes[i])).getData().getValue(), true);
                        this.PickGraphElement(((NetworkEdgeNode) (SelectedNodes[i])).getData());
                    }
                }

                //Handle the selection of Interactions in the NetworkViewer Module.
                //if (SelectedNodes[i].getClass().getName().compareTo("invis.gl.NetworkViewerNodes.NetworkInteractionsNode") == 0)
                if (SelectedNodes[i] instanceof NetworkInteractionsNode)
                {
                    //NetworkElementApi preState = ((NetworkInteractionsNode) (SelectedNodes[i])).getData().getPreState();
                    //NetworkElementApi postState = ((NetworkInteractionsNode) (SelectedNodes[i])).getData().getPostState();
                    //NetworkElementApi action = ((NetworkInteractionsNode) (SelectedNodes[i])).getData().getAction();

                    this.PickGraphElement(((NetworkInteractionsNode) (SelectedNodes[i])).getData().getPreState());
                    this.PickGraphElement(((NetworkInteractionsNode) (SelectedNodes[i])).getData().getPostState());
                    this.PickGraphElement(((NetworkInteractionsNode) (SelectedNodes[i])).getData().getAction());


                    /*this.getPickedVertexState().pick(preState.getValue(), true);
                     this.getPickedVertexState().pick(postState.getValue(), true);
                     this.getPickedEdgeState().pick(action.getValue(), true);*/
                }

                //Handle the selection of a student.
                //if (SelectedNodes[i].getClass().getName().compareTo("invis.gl.NetworkViewerNodes.NetworkCaseNode") == 0)
                if (SelectedNodes[i] instanceof NetworkCaseNode)
                {
                    NetworkCaseApi NCA = ((NetworkCaseNode) (SelectedNodes[i])).getData();
                    //For all the interactions, select both the states and the actions.
                    for (int InteractionIndex = 0; InteractionIndex < NCA.getInteractionsList().size(); InteractionIndex++)
                    {
                        /*this.getPickedVertexState().pick(NCA.getInteractionsList().get(InteractionIndex).getPreState().getValue(), true);
                         this.getPickedVertexState().pick(NCA.getInteractionsList().get(InteractionIndex).getPostState().getValue(), true);
                         this.getPickedEdgeState().pick(NCA.getInteractionsList().get(InteractionIndex).getAction().getValue(), true);*/

                        this.PickGraphElement(NCA.getInteractionsList().get(InteractionIndex).getPreState());
                        this.PickGraphElement(NCA.getInteractionsList().get(InteractionIndex).getAction());
                        this.PickGraphElement(NCA.getInteractionsList().get(InteractionIndex).getPostState());

                    }
                }

                //if (SelectedNodes[i].getClass().getName().compareTo("invis.gl.NetworkViewerNodes.NetworkSequenceNode") == 0)
                if (SelectedNodes[i] instanceof StepbasedSequenceNode)
                {
                    NetworkInteractionSequenceApi NSA = ((NetworkSequenceNode) (SelectedNodes[i])).getData();
                    //For all the interactions, select both the states and the actions.
                    for (int InteractionIndex = 0; InteractionIndex < NSA.getInteractionsList().size(); InteractionIndex++)
                    {
                        /*this.getPickedVertexState().pick(NSA.getInteractionsList().get(InteractionIndex).getPreState().getValue(), true);
                         this.getPickedVertexState().pick(NSA.getInteractionsList().get(InteractionIndex).getPostState().getValue(), true);
                         this.getPickedEdgeState().pick(NSA.getInteractionsList().get(InteractionIndex).getAction().getValue(), true);*/

                        this.PickGraphElement(NSA.getInteractionsList().get(InteractionIndex).getPreState());
                        this.PickGraphElement(NSA.getInteractionsList().get(InteractionIndex).getPostState());
                        this.PickGraphElement(NSA.getInteractionsList().get(InteractionIndex).getAction());
                    }

                    if (mDataParser.HasDerivedData() && NSA.getStepbasedSequenceSet() != null)
                    {
                        for (int SequenceIndex = 0; SequenceIndex < NSA.getStepbasedSequenceSet().size(); SequenceIndex++)
                        {
                            //this.getPickedVertexState().pick(NSA.getStepbasedSequenceSet().get(SequenceIndex).getValue(), true);
                            this.PickGraphElement(NSA.getStepbasedSequenceSet().get(SequenceIndex).getNetworkElementSet());
                        }
                    }
                }

                //if (SelectedNodes[i].getClass().getName().compareTo("invis.gl.stepbasednodes.StepbasedSequenceNode") == 0)
                if (SelectedNodes[i] instanceof StepbasedSequenceNode)
                {
                    NetworkInteractionSequenceApi NSA = ((StepbasedSequenceNode) (SelectedNodes[i])).getData();

                    for (int InteractionIndex = 0; InteractionIndex < NSA.getInteractionsList().size(); InteractionIndex++)
                    {
                        /*this.getPickedVertexState().pick(NSA.getInteractionsList().get(InteractionIndex).getPreState().getValue(), true);
                         this.getPickedVertexState().pick(NSA.getInteractionsList().get(InteractionIndex).getPostState().getValue(), true);
                         this.getPickedEdgeState().pick(NSA.getInteractionsList().get(InteractionIndex).getAction().getValue(), true);*/

                        this.PickGraphElement(NSA.getInteractionsList().get(InteractionIndex).getPreState());
                        this.PickGraphElement(NSA.getInteractionsList().get(InteractionIndex).getPostState());
                        this.PickGraphElement(NSA.getInteractionsList().get(InteractionIndex).getAction());
                    }

                    if (mDataParser.HasDerivedData())
                    {
                        for (int SequenceIndex = 0; SequenceIndex < NSA.getStepbasedSequenceSet().size(); SequenceIndex++)
                        {
                            //this.getPickedVertexState().pick(NSA.getStepbasedSequenceSet().get(SequenceIndex).getValue(), true);
                            this.PickGraphElement(NSA.getStepbasedSequenceSet().get(SequenceIndex).getNetworkElementSet());
                        }
                    }
                }


                //if (SelectedNodes[i].getClass().getName().compareTo("invis.gl.stepbasednodes.StepBasedVertexNode") == 0)
                if (SelectedNodes[i] instanceof StepBasedVertexNode)
                {
                    //NetworkClusterVertexApi mNCVA = ((StepBasedVertexNode) (SelectedNodes[i])).getData();
                    //For all the interactions, select both the states and the actions.
                    //String key = mNCVA.getValue();
                    //this.getPickedVertexState().pick(key, true);
                    this.PickGraphElement(((StepBasedVertexNode) (SelectedNodes[i])).getData().getNetworkElementSet());

                    this.PickGraphClusterElement(((StepBasedVertexNode) (SelectedNodes[i])).getData());
                    //((StepBasedVertexNode) (SelectedNodes[i])).getData().setSelected(true);
                    //for (int NetworkElementIndex = 0; NetworkElementIndex < mNCVA.getNetworkElementSet().size(); NetworkElementIndex++)
                    //{
                    //    this.getPickedVertexState().pick(mNCVA.getNetworkElementSet().get(NetworkElementIndex).getValue(), true);
                    //}
                }

                //if (SelectedNodes[i].getClass().getName().compareTo("invis.gl.stepbasednodes.StepBasedEdgeNode") == 0)
                if (SelectedNodes[i] instanceof StepBasedEdgeNode)
                {
                    //NetworkClusterElementApi mNCVA = ((StepBasedEdgeNode) (SelectedNodes[i])).getData();
                    //For all the interactions, select both the states and the actions.

                    //String key = mNCVA.getValue();
                    //this.getPickedEdgeState().pick(key, true);

                    this.PickGraphElement(((StepBasedEdgeNode) (SelectedNodes[i])).getData().getNetworkElementSet());

                    this.PickGraphClusterElement(((StepBasedEdgeNode) (SelectedNodes[i])).getData());

                    //((StepBasedEdgeNode) (SelectedNodes[i])).getData().setSelected(true);
                    //for (int NetworkElementIndex = 0; NetworkElementIndex < mNCVA.getNetworkElementSet().size(); NetworkElementIndex++)
                    //{
                    //    this.getPickedEdgeState().pick(mNCVA.getNetworkElementSet().get(NetworkElementIndex).getValue(), true);
                    //}
                }

                //Handle the selection of a student.
                //if (SelectedNodes[i].getClass().getName().compareTo("invis.gl.bubbleNodes.BubbleNode") == 0)
                if (SelectedNodes[i] instanceof BubbleNode)
                {
                    BubbleNode bubble = ((BubbleNode) (SelectedNodes[i]));

                    this.PickGraphElement(bubble.getSource().getValue(), NetworkElementType.NODE);
                    this.PickGraphElement(bubble.getTarget().getValue(), NetworkElementType.NODE);

                    for (int j = 0; j < bubble.getPaths().size(); j++)
                    {
                        this.PickGraphElement(bubble.getPaths().get(j));
                    }
                }
                //if (SelectedNodes[i].getClass().getName().compareTo("invis.gl.bubbleNodes.PathNode") == 0)
                if (SelectedNodes[i] instanceof PathNode)
                {
                    List<NetworkElementApi> edges = ((PathNode) (SelectedNodes[i])).getPathModel();


                    for (int edgeItr = 0; edgeItr < edges.size(); edgeItr++)
                    {
                        this.PickGraphElement(edges.get(edgeItr));
                    }
                }

            }
            //This links the selection of Nodes in the NodesAPI for the following "windows" to the NetworkVisualizationViewer.
            WindowManager.getDefault().findTopComponent("NetworkViewerTopComponent").setActivatedNodes(SelectedNodes);
            //WindowManager.getDefault().findTopComponent("SequenceViewTopComponent").setActivatedNodes(SelectedNodes);
            WindowManager.getDefault().findTopComponent("EntropyViewerTopComponent").setActivatedNodes(SelectedNodes);
            WindowManager.getDefault().findTopComponent("BubbleViewerTopComponent").setActivatedNodes(SelectedNodes);
            //SelectNodes();
            this.repaint();
        }
    }

    private void SelectNodes()
    {
        String key;
        for (int i = 0; i < this.getPickedVertexState().getPicked().size(); i++)
        {
            key = (String) this.getPickedVertexState().getPicked().toArray()[i];
            if (mDataParser.getNodeTable().containsKey(key))
            {
                mDataParser.getNodeTable().get(key).setSelected(true);
            }
            if (mDataParser.getDerivedData().getNodeTable().containsKey(key))
            {
                mDataParser.getDerivedData().getNodeTable().get(key).setSelected(true);
            }
        }

        for (int i = 0; i < this.getPickedEdgeState().getPicked().size(); i++)
        {
            key = this.getPickedEdgeState().getPicked().toArray()[i].toString();
            if (mDataParser.getEdgeTable().containsKey(key))
            {
                mDataParser.getEdgeTable().get(key).setSelected(true);
            }
            if (mDataParser.getDerivedData().getEdgeTable().containsKey(key))
            {
                mDataParser.getDerivedData().getEdgeTable().get(key).setSelected(true);
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e)
    {
        if (e.getSource() != null)
        {
            StatusDisplayer.getDefault().setStatusText(
                    this.getPickedVertexState().getPicked().size() + " Vertices selected and "
                    + this.getPickedEdgeState().getPicked().size() + " Edges selected.");
        }
    }

    public Graph GenerateSubgraph()
    {
        this.getGraphLayout().getGraph();
        KNeighborhoodFilter x;
        x = new KNeighborhoodFilter(this.getPickedVertices(), 0, KNeighborhoodFilter.EdgeType.IN_OUT);
        Graph transform = x.transform(this.getGraphLayout().getGraph());

        //this.getGraphLayout().setGraph(transform);
        return (transform);

    }

    public ArrayList<String> getPickedElements()
    {
        Collection<String> pickedVertices = this.getPickedVertexState().getPicked();
        Collection<String> pickedEdges = this.getPickedEdgeState().getPicked();
        ArrayList<String> elements = new ArrayList<String>();
        elements.addAll(pickedVertices);
        elements.addAll(pickedEdges);
        return (elements);
    }

    public Set<String> getPickedVertices()
    {
        return (this.getPickedVertexState().getPicked());
    }

    public Set<String> getPickedEdges()
    {
        return (this.getPickedEdgeState().getPicked());
    }
}
