package invis.gl.sequenceview;

import invis.gl.NBNodeFactories.NetworkFactories.NetworkSequenceNodeFactory;
import invis.gl.NBNodeFactories.StepBasedFactories.StepbasedSequenceSetNodeFactory;
import invis.gl.NetworkViewerNodes.NetworkSequenceSetNode;
import invis.gl.NetworkVisualizationViewer.NetworkVisualizationViewer;
import invis.gl.dataprocessor.DataParser;
import invis.gl.graphvisualapi.NetworkDisplayApi.DisplayType;
import invis.gl.networkapi.NodeViewerTopComponentExtension;
import invis.gl.sequenceapi.NetworkInteractionSequenceApi;
import invis.gl.sequenceapi.NetworkInteractionSequenceApi.SequenceType;
import invis.gl.sequencemodel.NetworkInteractionSequenceSetModel;
import invis.gl.stepbasednodes.StepbasedSequenceSetNode;
import java.io.IOException;
import java.util.ArrayList;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.explorer.view.BeanTreeView;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
    dtd = "-//invis.gl.sequenceview//SequenceView//EN",
autostore = false)
@TopComponent.Description(
    preferredID = "SequenceViewTopComponent",
//iconBase="SET/PATH/TO/ICON/HERE", 
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "leftSlidingSide", openAtStartup = false)
@ActionID(category = "Window", id = "invis.gl.sequenceview.SequenceViewTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
    displayName = "#CTL_SequenceViewAction",
preferredID = "SequenceViewTopComponent")
@Messages(
{
    "CTL_SequenceViewAction=SequenceView",
    "CTL_SequenceViewTopComponent=SequenceView Window",
    "HINT_SequenceViewTopComponent=This is a SequenceView window"
})
public final class SequenceViewTopComponent extends NodeViewerTopComponentExtension
{

    private DataParser mDataParser;
    //private NetworkInteractionSequenceSetApi setCases;
    private ExplorerManager mgr = new ExplorerManager();

    public SequenceViewTopComponent()
    {
        initComponents();
        this.associateLookup(ExplorerUtils.createLookup(mgr, this.getActionMap()));
        setName(Bundle.CTL_SequenceViewTopComponent());
        setToolTipText(Bundle.HINT_SequenceViewTopComponent());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        mSequenceButtonGroup = new javax.swing.ButtonGroup();
        beanTreeView = new javax.swing.JScrollPane();
        mNodeButton = new javax.swing.JRadioButton();
        mEdgeButton = new javax.swing.JRadioButton();
        mStepbasedButton = new javax.swing.JRadioButton();
        mSortComboBox = new javax.swing.JComboBox();

        mSequenceButtonGroup.add(mNodeButton);
        mSequenceButtonGroup.add(mEdgeButton);
        mSequenceButtonGroup.add(mStepbasedButton);

        beanTreeView = new BeanTreeView();

        mNodeButton.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(mNodeButton, org.openide.util.NbBundle.getMessage(SequenceViewTopComponent.class, "SequenceViewTopComponent.mNodeButton.text")); // NOI18N
        mNodeButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                mNodeButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(mEdgeButton, org.openide.util.NbBundle.getMessage(SequenceViewTopComponent.class, "SequenceViewTopComponent.mEdgeButton.text")); // NOI18N
        mEdgeButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                mEdgeButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(mStepbasedButton, org.openide.util.NbBundle.getMessage(SequenceViewTopComponent.class, "SequenceViewTopComponent.mStepbasedButton.text")); // NOI18N
        mStepbasedButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                mStepbasedButtonActionPerformed(evt);
            }
        });

        mSortComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Frequency", "Interaction", "Rating" }));
        mSortComboBox.setSelectedIndex(2);
        mSortComboBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                mSortComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(beanTreeView)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(mStepbasedButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mSortComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(mNodeButton)
                                .addGap(4, 4, 4)
                                .addComponent(mEdgeButton)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(beanTreeView, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mEdgeButton)
                    .addComponent(mNodeButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mStepbasedButton)
                    .addComponent(mSortComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void mEdgeButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mEdgeButtonActionPerformed
    {//GEN-HEADEREND:event_mEdgeButtonActionPerformed
        mgr.setRootContext(new AbstractNode(Children.LEAF));
        mgr.getRootContext().setName("Coming soon.");
        //this.RefreshInteractionSequenceContents(this.CalculateActionSequenceSet());
    }//GEN-LAST:event_mEdgeButtonActionPerformed

    private void mNodeButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mNodeButtonActionPerformed
    {//GEN-HEADEREND:event_mNodeButtonActionPerformed
        //this.RefreshInteractionSequenceContents(this.CalculateNodeSequenceSet());
        this.RefreshInteractionSequenceContents(NetworkInteractionSequenceBuilder.getNetworkInteractionSequenceSetModel(mDataParser, SequenceType.STATE));
    }//GEN-LAST:event_mNodeButtonActionPerformed

    private void mStepbasedButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mStepbasedButtonActionPerformed
    {//GEN-HEADEREND:event_mStepbasedButtonActionPerformed
        //this.RefreshStepbasedSequenceContents(StepbasedSequenceBuilder.getStepbasedSequenceModel(mDataParser));
        this.RefreshStepbasedSequenceContents(NetworkInteractionSequenceBuilder.getNetworkInteractionSequenceSetModel(mDataParser, SequenceType.STEPBASED));
    }//GEN-LAST:event_mStepbasedButtonActionPerformed

    private void mSortComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mSortComboBoxActionPerformed
    {//GEN-HEADEREND:event_mSortComboBoxActionPerformed
        int val = mSortComboBox.getSelectedIndex();

        ArrayList<NetworkInteractionSequenceApi> networkSequenceSet = null;
        if (mStepbasedButton.isSelected())
        {
            StepbasedSequenceSetNode rootNode = (StepbasedSequenceSetNode) mgr.getRootContext();
            networkSequenceSet = rootNode.getData().getNetworkSequenceSet();
        }
        if (mNodeButton.isSelected())
        {
            NetworkSequenceSetNode rootNode = (NetworkSequenceSetNode) mgr.getRootContext();
            networkSequenceSet = rootNode.getData().getNetworkSequenceSet();
        }
        
        switch (val)
        {
            case 0: //Frequency

                if (mNodeButton.isSelected())
                {
                    this.RefreshInteractionSequenceContents(
                            NetworkInteractionSequenceBuilder.SortModelFrequency(networkSequenceSet, "Frequency"));
                }
                if (mStepbasedButton.isSelected())
                {
                    this.RefreshStepbasedSequenceContents(
                            NetworkInteractionSequenceBuilder.SortModelFrequency(networkSequenceSet, "Frequency"));
                }
                if (mEdgeButton.isSelected())
                {
                    try
                    {
                        mNodeButton.setSelected(true);
                        mEdgeButton.setSelected(false);
                        throw new Exception("Edge Sequences are not yet implemented.");
                    } catch (Exception ex)
                    {
                        Exceptions.printStackTrace(ex);
                    }
                }
                break;
            case 1:  //Interaction Count
                if (mNodeButton.isSelected())
                {
                    this.RefreshInteractionSequenceContents(
                            NetworkInteractionSequenceBuilder.SortModelFrequency(networkSequenceSet, "Interaction"));
                }
                if (mStepbasedButton.isSelected())
                {
                    this.RefreshStepbasedSequenceContents(
                            NetworkInteractionSequenceBuilder.SortModelFrequency(networkSequenceSet, "Interaction"));
                }
                if (mEdgeButton.isSelected())
                {
                    try
                    {
                        mNodeButton.setSelected(true);
                        mEdgeButton.setSelected(false);
                        throw new Exception("Edge Sequences are not yet implemented.");
                    } catch (Exception ex)
                    {
                        Exceptions.printStackTrace(ex);
                    }
                }
                break;


            case 2: //Rating
                if (mNodeButton.isSelected())
                {
                    this.RefreshInteractionSequenceContents(
                            NetworkInteractionSequenceBuilder.SortModelFrequency(networkSequenceSet, "Rating"));
                }
                if (mStepbasedButton.isSelected())
                {
                    this.RefreshStepbasedSequenceContents(
                            NetworkInteractionSequenceBuilder.SortModelFrequency(networkSequenceSet, "Rating"));
                }
                if (mEdgeButton.isSelected())
                {
                    try
                    {
                        mNodeButton.setSelected(true);
                        mEdgeButton.setSelected(false);
                        throw new Exception("Edge Sequences are not yet implemented.");
                    } catch (Exception ex)
                    {
                        Exceptions.printStackTrace(ex);
                    }
                }
                break;
            default:
        }

    }//GEN-LAST:event_mSortComboBoxActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane beanTreeView;
    private javax.swing.JRadioButton mEdgeButton;
    private javax.swing.JRadioButton mNodeButton;
    private javax.swing.ButtonGroup mSequenceButtonGroup;
    private javax.swing.JComboBox mSortComboBox;
    private javax.swing.JRadioButton mStepbasedButton;
    // End of variables declaration//GEN-END:variables

    @Override
    public void componentOpened()
    {
        mDataParser = Lookup.getDefault().lookup(DataParser.class);
        if (mDataParser.hasData())
        {
            mDataParser.getNetworkVVLookup().AddExplorerManager(mgr);
            //this.RefreshInteractionSequenceContents(this.CalculateNodeSequenceSet());
            this.RefreshInteractionSequenceContents(
                    NetworkInteractionSequenceBuilder.getNetworkInteractionSequenceSetModel(mDataParser, SequenceType.STATE));
        }
    }

    private void RefreshInteractionSequenceContents(NetworkInteractionSequenceSetModel SequenceSetModel)
    {
        if (SequenceSetModel != null)
        {
            NetworkSequenceSetNode trueRoot = new NetworkSequenceSetNode(Children.create(
                    new NetworkSequenceNodeFactory(SequenceSetModel), true),
                    SequenceSetModel);
            mgr.setRootContext(trueRoot);
            mgr.getRootContext().setDisplayName("State Sequences - File: " + mDataParser.getFileName() + " SeqSize: " + trueRoot.getData().getSequenceSetSize());

        } else
        {
            AbstractNode EmptyNode = new AbstractNode(Children.LEAF);
            EmptyNode.setName("File not loaded.");
            mgr.setRootContext(EmptyNode);
        }
    }

    public void RefreshStepbasedSequenceContents(NetworkInteractionSequenceSetModel SequenceSetModel)
    {
        if (SequenceSetModel != null)
        {
            StepbasedSequenceSetNode trueRoot = new StepbasedSequenceSetNode(Children.create(
                    new StepbasedSequenceSetNodeFactory(SequenceSetModel),
                    true),
                    SequenceSetModel);
            mgr.setRootContext(trueRoot);
            mgr.getRootContext().setDisplayName("Stepbased - File: " + mDataParser.getFileName() + " SeqSize: " + trueRoot.getData().getSequenceSetSize());


        } else
        {
            AbstractNode EmptyNode = new AbstractNode(Children.LEAF);
            EmptyNode.setName("File not loaded.");
            mgr.setRootContext(EmptyNode);
        }
    }

    @Override
    public void componentClosed()
    {
        try
        {
            mDataParser.getNetworkVVLookup().RemoveExplorerManager(mgr);
            mgr.getRootContext().destroy();
        } catch (IOException ex)
        {
            Exceptions.printStackTrace(ex);
        }
    }

    /*
     private NetworkInteractionSequenceSetModel CalculateActionSequenceSet()
     {
     //search through the data and pull all permutations of sequences and add it to a HashBag
     if (mDataParser.hasData())
     {
     HashBag<NetworkInteractionSequenceApi> interactionsBag = new HashBag<NetworkInteractionSequenceApi>();
     ArrayList<NetworkCaseApi> caseSet = mDataParser.getNetworkCaseSet().getNetworkCaseList();
     //First we iterate through each case.
     for (int caseIndex = 0; caseIndex < caseSet.size(); caseIndex++)
     {
     ArrayList<NetworkInteractionApi> interactionsList = caseSet.get(caseIndex).getInteractionsList();
     //We next iterate through that cases interactions.
     for (int interactionIndex = 0; interactionIndex < interactionsList.size(); interactionIndex++)
     {
     boolean keepChain = true;
     //Here we iterate through the cases interactions, using an offset, so we can identify sequences
     //that start on the case's second, third or N-action.
     for (int interactionOffset = 0; interactionIndex + interactionOffset < interactionsList.size(); interactionOffset++)
     {
     ArrayList<NetworkElementApi> curChain = new ArrayList<NetworkElementApi>();

     //Lastly, we iterate through the various lengths of sequences that can be present.
     for (int sequenceLength = 0; sequenceLength < interactionIndex + 1; sequenceLength++)
     {
     //We ignroe actions with frequency less of 1 (and below, obviously).
     if (interactionsList.get(interactionOffset + sequenceLength).getAction().getUniqueFrequency() < 2)
     {
     keepChain = false;              //if any of the sequences in this chain contain a interaction
     break;                          //with less than 2 users, discard the whole chain.
     }
     curChain.add(interactionsList.get(interactionOffset + sequenceLength).getAction());
     }
     //If the sequence has more than one interaction, and the frequency meets our requirements we keep the sequence.
     if (curChain.size() > 1 && keepChain)
     {
     //The sequence is kept.
     //interactionsBag.add(new NetworkActionSequenceModel(curChain));
     }
     //We reset our flag for requiring sequences to have greater than 1 frequency.
     keepChain = true;
     }
     }
     }
     //Set<NetworkSequenceApi> uniqueSet = this.CalculateAssignSequenceFrequencies(interactionsBag);
     return (this.GenerateSortedNodeSequences(this.CalculateAssignSequenceFrequencies(interactionsBag)));
     }
     //If there is no data, we return null.
     return null;
     }*/
    /**
     * This method generates the NodeSequenceSet, to be displayed.
     *
     * @return the final NetworkSequenceSet
     */
    /*
     private NetworkInteractionSequenceSetModel CalculateNodeSequenceSet()
     {
     //search through the data and pull all permutations of sequences and add it to a HashBag
     if (mDataParser.hasData())
     {
     HashBag<NetworkInteractionSequenceApi> interactionsBag = new HashBag<NetworkInteractionSequenceApi>();
     ArrayList<NetworkCaseApi> caseSet = mDataParser.getNetworkCaseSet().getNetworkCaseList();
     //First we iterate through each case.
     for (int caseIndex = 0; caseIndex < caseSet.size(); caseIndex++)
     {
     ArrayList<NetworkInteractionApi> interactionsList = caseSet.get(caseIndex).getInteractionsList();
     //We next iterate through that cases interactions.
     for (int interactionIndex = 0; interactionIndex < interactionsList.size(); interactionIndex++)
     {
     boolean keepChain = true;
     //Here we iterate through the cases interactions, using an offset, so we can identify sequences
     //that start on the case's second, third or N-action.
     for (int interactionOffset = 0; interactionIndex + interactionOffset < interactionsList.size(); interactionOffset++)
     {
     ArrayList<NetworkInteractionApi> curChain = new ArrayList<NetworkInteractionApi>();

     //Lastly, we iterate through the various lengths of sequences that can be present.
     for (int sequenceLength = 0; sequenceLength < interactionIndex + 1; sequenceLength++)
     {
     //We ignroe actions with frequency less of 1 (and below, obviously).
     if (interactionsList.get(interactionOffset + sequenceLength).getAction().getUniqueFrequency() < 2)
     {
     keepChain = false;              //if any of the sequences in this chain contain a interaction
     break;                          //with less than 2 users, discard the whole chain.
     }
     curChain.add(interactionsList.get(interactionOffset + sequenceLength));
     }
     //If the sequence has more than one interaction, and the frequency meets our requirements we keep the sequence.
     if (curChain.size() > 1 && keepChain)
     {
     //The sequence is kept.
     interactionsBag.add(new NetworkInteractionSequenceModel(curChain));
     }
     //We reset our flag for requiring sequences to have greater than 1 frequency.
     keepChain = true;
     }
     }
     }
     //Set<NetworkSequenceApi> uniqueSet = this.CalculateAssignSequenceFrequencies(interactionsBag);
     return (this.GenerateSortedNodeSequences(this.CalculateAssignSequenceFrequencies(interactionsBag)));
     }
     //If there is no data, we return null.
     return null;
     }*/
    /**
     * We generate the set of unique sequences. Then we calculate the frequency
     * of the unique sequences.
     *
     * @param interactionsBag the interactionsBag is used to determine the
     * frequencies of the sequence by looking at all interactions performed
     * across the set of cases.
     */
    /*
     private Set<NetworkInteractionSequenceApi> CalculateAssignSequenceFrequencies(HashBag<NetworkInteractionSequenceApi> interactionsBag)
     {
     Set<NetworkInteractionSequenceApi> uniqueSet = interactionsBag.uniqueSet();
     for (NetworkInteractionSequenceApi nsa : uniqueSet)
     {
     nsa.setFrequency(interactionsBag.getCount(nsa));
     }
     return (uniqueSet);
     }*/
    /**
     * We generate the sorted set of sequences, in the form of of a
     * NetworkInteractionSequenceSetModel.
     *
     * @param uniqueSet This is the uniqueSet of NetworkInteractionSequenceApi
     * objects.
     * @return a sorted set of sequences in the form of a
     * NetworkInteractionSequenceSetModel.
     */
    /*
     private NetworkInteractionSequenceSetModel GenerateSortedNodeSequences(Set<NetworkInteractionSequenceApi> uniqueSet)
     {
     NetworkInteractionSequenceSetModel SequenceSet = new NetworkInteractionSequenceSetModel("Unsorted Set", new ArrayList<NetworkInteractionSequenceApi>(uniqueSet));

     ArrayList<NetworkInteractionSequenceApi> sortedSet = new ArrayList<NetworkInteractionSequenceApi>(uniqueSet);

        
     // Here we calculate the ratings for each sequence based on it's
     // characteristics.
         
     this.CalculateNodeSequenceRatings(SequenceSet);

        
     //  We create a comparator and sort based on the rating of the sequences
         
     Collections.sort(sortedSet, new Comparator<NetworkInteractionSequenceApi>()
     {
     @Override
     public int compare(NetworkInteractionSequenceApi t, NetworkInteractionSequenceApi t1)
     {
     return t1.getRating() - t.getRating();
     }
     });


        
     // Here we limit the size of the sequence set to some pre-defined max.
             
     int max = 150;
     if (sortedSet.size() < max)
     {
     max = sortedSet.size();
     }
            
            
            
            
     // This is used to remove subsequences of higher rated larger
     // sequences.
             
     for (int caseIndex = 0; caseIndex < max; caseIndex++)
     {
     NetworkInteractionSequenceApi curSequence = sortedSet.get(caseIndex);
     for (NetworkInteractionApi nia : curSequence.getInteractionsList())
     {
     for (int interactionOffset = max - 1; interactionOffset > caseIndex; interactionOffset--)
     {
     if (sortedSet.get(interactionOffset).contains(nia))
     {
     sortedSet.remove(interactionOffset);
     max--;
     }
     }
     }
     }
         

        
     // This loop removes all sequences of frequency 1. If the frequency
     // is one, we aren't so interested in the sequence.
         
     for (int i = 0; i < sortedSet.size(); i++)
     {
     if (sortedSet.get(i).getFrequency() < 2)
     {
     sortedSet.remove(sortedSet.get(i));
     }
     }
     //for(NetworkInteractionSequenceApi nsa : sortedSet) {
     //  System.out.println(nsa + " Rating: " + nsa.getRating());
     //}
     //mSortedSequenceSet = new NetworkInteractionSequenceSetModel("SortedSet", sortedSet);
     return (new NetworkInteractionSequenceSetModel("SortedSet", sortedSet));

     }*/
    /**
     * This calculates the sequence ratings for the node-sequences. Which are
     * stored in the NetworkInteractionSequenceSetModel.
     *
     * @param sequenceSet this is an Unsorted set of
     * NetworkInteractionSequenceApi objects. That is all the sequences in which
     * you wish to sort.
     */
    /*
     public void CalculateNodeSequenceRatings(NetworkInteractionSequenceSetModel sequenceSet)
     {
     for (NetworkInteractionSequenceApi nsa : sequenceSet.getNetworkSequenceSet())
     {
     int rating = 0;
     int totalSuccessors = 0;
     int successorNodes = 0;
     rating += nsa.getInteractionsList().get(0).getPreState().getUniqueCaseIdSet().size(); //get pre state of first
     ArrayList<String> successors = new ArrayList<String>(mDataParser.getGraph().getSuccessors(nsa.getInteractionsList().get(0).getPreState().getValue()));
     successorNodes += successors.size(); //do all successor calculations on first state as well
     if (successors.size() != 1)
     {
     for (String s : successors)
     {
     totalSuccessors += mDataParser.getNodeTable().get(s).getUniqueCaseIdSet().size(); //count the successors                               
     }
     }
     for (NetworkInteractionApi interaction : nsa.getInteractionsList()) //then loop on the post state of everyone because of overlap
     {
     rating += interaction.getPostState().getUniqueCaseIdSet().size();
     successors = new ArrayList<String>(mDataParser.getGraph().getSuccessors(interaction.getPostState().getValue()));
     successorNodes += successors.size();
     if (successors.size() != 1)
     {
     for (String s : successors)
     {
     totalSuccessors += mDataParser.getNodeTable().get(s).getUniqueCaseIdSet().size(); //count the successors                               
     }
     }
     totalSuccessors -= interaction.getPostState().getUniqueFrequency();
     }
     rating = rating - (successorNodes * totalSuccessors);
     nsa.setRating(rating);
     }
     }*/
    //specialized sort function to sort on the number of the sequences in the bag
    //only used for CalculateNodeSequenceSet
    /*
     @Deprecated
     private static void SortOnCount(ArrayList<NetworkInteractionSequenceApi> list, Bag<NetworkInteractionSequenceApi> bag)
     {
     for (int i = 0; i < list.size(); i++)
     {
     int min = i;
     for (int y = i; y < list.size(); y++)
     {
     if (bag.getCount(list.get(min)) < bag.getCount(list.get(y)))
     {
     min = y;
     }
     }
     NetworkInteractionSequenceApi temp = list.get(i);
     list.set(i, list.get(min));
     list.set(min, temp);
     }
     }
     */
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
    public void UpdateCurrentVV(NetworkVisualizationViewer currentVV, DisplayType type)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void RefreshContents()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void ClearContents()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
