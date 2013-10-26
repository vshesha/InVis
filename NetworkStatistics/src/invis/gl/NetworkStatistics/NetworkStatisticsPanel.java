package invis.gl.NetworkStatistics;

import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import invis.gl.dataprocessor.DataParser;
import java.util.Set;

/**
 *
 * @author Matt
 */
public class NetworkStatisticsPanel extends javax.swing.JPanel
{
private DirectedSparseMultigraph<String, String> mGraph;
    /**
     * Creates new form NetworkStatisticsPanel
     */
    public NetworkStatisticsPanel(DataParser data, DirectedSparseMultigraph<String, String> graph)
    {
        initComponents();
        String FileName = data.getFileName();
        mFileNameLabel.setText(FileName);

        String CaseSetSize = data.getNetworkCaseSetSize().toString();
        mCaseCountLabel.setText(CaseSetSize);

        String StateCount = data.getNetworkStateCount().toString();
        mStateCountLabel.setText(StateCount);

        if (data.DataContainsErrorStates())
        {
            Integer errorCount = 0;
            Integer nonErrorCount = 0;
            for (int i = 0; i < graph.getVertices().size(); i++)
            {
                String vertKey = graph.getVertices().toArray()[i].toString();
                if (data.getNodeTable().get(vertKey).getErrorValue())
                {
                    errorCount++;
                }
                if (!data.getNodeTable().get(vertKey).getErrorValue())
                {
                    nonErrorCount++;
                }
                mErrorStateCountLabel.setText(errorCount.toString());
                mNonErrorStateCountLabel.setText(nonErrorCount.toString());
            }
        }

        String ActionCount = data.getNetworkActionCount().toString();
        mActionCountLabel.setText(ActionCount);

        if (data.DataContainsErrorStates())
        {
            Integer errorCount = 0;
            Integer nonErrorCount = 0;
            for (int i = 0; i < graph.getEdges().size(); i++)
            {
                String edgeKey = graph.getEdges().toArray()[i].toString();
                if (data.getEdgeTable().get(edgeKey).getErrorValue())
                {
                    errorCount++;
                }
                if (!data.getEdgeTable().get(edgeKey).getErrorValue())
                {
                    nonErrorCount++;
                }
                mErrorActionCountLabel.setText(errorCount.toString());
                mNonErrorActionCountLabel.setText(nonErrorCount.toString());
            }
        }
        Integer vertexCount = graph.getVertexCount();
        Integer edgeCount = graph.getEdgeCount();
        mNodeCountLabel.setText(vertexCount.toString());
        mEdgeCountLabel.setText(edgeCount.toString());

        Double FrequencyValue = 0.0;
        for (int i = 0; i < graph.getEdgeCount(); i++)
        {
            String edge = graph.getEdges().toArray()[i].toString();
            FrequencyValue += data.getEdgeTable().get(edge).getUniqueFrequency();
        }
        mSumEdgeFrequency.setText(FrequencyValue.toString());

        Integer goalCount = 0;
        for (int i = 0; i < graph.getVertexCount(); i++)
        {
            String vertex = graph.getVertices().toArray()[i].toString();
            if (data.getNodeTable().get(vertex).getGoalValue())
            {
                goalCount++;
            }
        }
        mGoalCountLabel.setText(goalCount.toString());

        Double FreqEdgeRatio = FrequencyValue / edgeCount;
        mFreqEdgeRatio.setText(FreqEdgeRatio.toString());

        this.BuildOutputData(FileName, StateCount, ActionCount, vertexCount, edgeCount, FrequencyValue, goalCount, FreqEdgeRatio, data);
    }

    private void BuildOutputData(String FileName, String StateCount, String ActionCount,
            Integer vertexCount, Integer edgeCount, Double FrequencyValue, Integer goalCount, Double FreqEdgeRatio, DataParser data)
    {
        StringBuilder sb = new StringBuilder();

        sb.append("File: " + FileName + "\n"
                + "State Count: " + StateCount + "\n"
                + "Action Count: " + ActionCount + "\n"
                + "\n"
                + "Graph: \n"
                + "Vertex Count: " + vertexCount + "\n"
                + "Edge Count: " + edgeCount + "\n"
                + "Goal Count: " + goalCount + "\n"
                + "Sum of Frequencies: " + FrequencyValue + "\n"
                + "Freq/Edge Ratio: " + FreqEdgeRatio);
        jTextArea1.setText(sb.toString());
        if (data.DataContainsCaseGroupIds())
        {
            sb.append("\n"
                    + "Group Data:\n"
                    + "Group Case Sizes:\n");
            Set<String> keySet = data.getNetworkCaseSet().getCaseGroupIdFrequencyMap().keySet();
            for (int i =0;i<keySet.size();i++)
            {
                sb.append("Group "+keySet.toArray()[i]+": "+data.getNetworkCaseSet().getCaseGroupIdFrequencyMap().get(keySet.toArray()[i]));
            }
            jTextArea1.setText(sb.toString());
        }
    }

    private void BuildOutputData(String FileName, String StateCount, String ActionCount,
            Integer vertexCount, Integer edgeCount, Double FrequencyValue, Integer goalCount, Double FreqEdgeRatio)
    {
        StringBuilder sb = new StringBuilder();

        sb.append("File: " + FileName + "\n"
                + "State Count: " + StateCount + "\n"
                + "Action Count: " + ActionCount + "\n"
                + "\n"
                + "Graph: \n"
                + "Vertex Count: " + vertexCount + "\n"
                + "Edge Count: " + edgeCount + "\n"
                + "Goal Count: " + goalCount + "\n"
                + "Sum of Frequencies: " + FrequencyValue + "\n"
                + "Freq/Edge Ratio: " + FreqEdgeRatio);
        jTextArea1.setText(sb.toString());
    }

    public NetworkStatisticsPanel(DataParser data, int NodeSize, int EdgeSize)
    {
        String nodeSize = Integer.toString(NodeSize);
        String edgeSize = Integer.toString(EdgeSize);

        initComponents();
        mFileNameLabel.setText(data.getFileName());
        mCaseCountLabel.setText(data.getNetworkCaseSetSize().toString());
        mStateCountLabel.setText(nodeSize);
        mActionCountLabel.setText(edgeSize);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jLabel11 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        mFileNameLabel = new javax.swing.JLabel();
        mCaseCountLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        mStateCountLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        mActionCountLabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        mErrorStateCountLabel = new javax.swing.JLabel();
        mNonErrorStateCountLabel = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        mErrorActionCountLabel = new javax.swing.JLabel();
        mNonErrorActionCountLabel = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        mEdgeCountLabel = new javax.swing.JLabel();
        mNodeCountLabel = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        mGoalCountLabel = new javax.swing.JLabel();
        mSumEdgeFrequency = new javax.swing.JLabel();
        mFreqEdgeRatio = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel11.setText(org.openide.util.NbBundle.getMessage(NetworkStatisticsPanel.class, "NetworkStatisticsPanel.jLabel11.text")); // NOI18N

        jLabel1.setText(org.openide.util.NbBundle.getMessage(NetworkStatisticsPanel.class, "NetworkStatisticsPanel.jLabel1.text")); // NOI18N

        mFileNameLabel.setText(org.openide.util.NbBundle.getMessage(NetworkStatisticsPanel.class, "NetworkStatisticsPanel.mFileNameLabel.text")); // NOI18N

        mCaseCountLabel.setText(org.openide.util.NbBundle.getMessage(NetworkStatisticsPanel.class, "NetworkStatisticsPanel.mCaseCountLabel.text")); // NOI18N

        jLabel2.setText(org.openide.util.NbBundle.getMessage(NetworkStatisticsPanel.class, "NetworkStatisticsPanel.jLabel2.text")); // NOI18N

        mStateCountLabel.setText(org.openide.util.NbBundle.getMessage(NetworkStatisticsPanel.class, "NetworkStatisticsPanel.mStateCountLabel.text")); // NOI18N

        jLabel3.setText(org.openide.util.NbBundle.getMessage(NetworkStatisticsPanel.class, "NetworkStatisticsPanel.jLabel3.text")); // NOI18N

        mActionCountLabel.setText(org.openide.util.NbBundle.getMessage(NetworkStatisticsPanel.class, "NetworkStatisticsPanel.mActionCountLabel.text")); // NOI18N

        jLabel4.setText(org.openide.util.NbBundle.getMessage(NetworkStatisticsPanel.class, "NetworkStatisticsPanel.jLabel4.text")); // NOI18N

        jLabel5.setText(org.openide.util.NbBundle.getMessage(NetworkStatisticsPanel.class, "NetworkStatisticsPanel.jLabel5.text")); // NOI18N
        jLabel5.setToolTipText(org.openide.util.NbBundle.getMessage(NetworkStatisticsPanel.class, "NetworkStatisticsPanel.jLabel5.toolTipText")); // NOI18N

        jLabel6.setText(org.openide.util.NbBundle.getMessage(NetworkStatisticsPanel.class, "NetworkStatisticsPanel.jLabel6.text")); // NOI18N

        mErrorStateCountLabel.setText(org.openide.util.NbBundle.getMessage(NetworkStatisticsPanel.class, "NetworkStatisticsPanel.mErrorStateCountLabel.text")); // NOI18N

        mNonErrorStateCountLabel.setText(org.openide.util.NbBundle.getMessage(NetworkStatisticsPanel.class, "NetworkStatisticsPanel.mNonErrorStateCountLabel.text")); // NOI18N

        jLabel7.setText(org.openide.util.NbBundle.getMessage(NetworkStatisticsPanel.class, "NetworkStatisticsPanel.jLabel7.text")); // NOI18N

        jLabel8.setText(org.openide.util.NbBundle.getMessage(NetworkStatisticsPanel.class, "NetworkStatisticsPanel.jLabel8.text")); // NOI18N

        mErrorActionCountLabel.setText(org.openide.util.NbBundle.getMessage(NetworkStatisticsPanel.class, "NetworkStatisticsPanel.mErrorActionCountLabel.text")); // NOI18N

        mNonErrorActionCountLabel.setText(org.openide.util.NbBundle.getMessage(NetworkStatisticsPanel.class, "NetworkStatisticsPanel.mNonErrorActionCountLabel.text")); // NOI18N

        jLabel9.setText(org.openide.util.NbBundle.getMessage(NetworkStatisticsPanel.class, "NetworkStatisticsPanel.jLabel9.text")); // NOI18N

        jLabel10.setText(org.openide.util.NbBundle.getMessage(NetworkStatisticsPanel.class, "NetworkStatisticsPanel.jLabel10.text")); // NOI18N

        jLabel12.setText(org.openide.util.NbBundle.getMessage(NetworkStatisticsPanel.class, "NetworkStatisticsPanel.jLabel12.text")); // NOI18N

        mEdgeCountLabel.setText(org.openide.util.NbBundle.getMessage(NetworkStatisticsPanel.class, "NetworkStatisticsPanel.mEdgeCountLabel.text")); // NOI18N

        mNodeCountLabel.setText(org.openide.util.NbBundle.getMessage(NetworkStatisticsPanel.class, "NetworkStatisticsPanel.mNodeCountLabel.text")); // NOI18N

        jLabel13.setText(org.openide.util.NbBundle.getMessage(NetworkStatisticsPanel.class, "NetworkStatisticsPanel.jLabel13.text")); // NOI18N

        jLabel14.setText(org.openide.util.NbBundle.getMessage(NetworkStatisticsPanel.class, "NetworkStatisticsPanel.jLabel14.text")); // NOI18N

        jLabel15.setText(org.openide.util.NbBundle.getMessage(NetworkStatisticsPanel.class, "NetworkStatisticsPanel.jLabel15.text")); // NOI18N

        mGoalCountLabel.setText(org.openide.util.NbBundle.getMessage(NetworkStatisticsPanel.class, "NetworkStatisticsPanel.mGoalCountLabel.text")); // NOI18N

        mSumEdgeFrequency.setText(org.openide.util.NbBundle.getMessage(NetworkStatisticsPanel.class, "NetworkStatisticsPanel.mSumEdgeFrequency.text")); // NOI18N

        mFreqEdgeRatio.setText(org.openide.util.NbBundle.getMessage(NetworkStatisticsPanel.class, "NetworkStatisticsPanel.mFreqEdgeRatio.text")); // NOI18N

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel6)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel3)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(6, 6, 6)
                                    .addComponent(jLabel8))
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(mNonErrorStateCountLabel)
                                            .addComponent(mErrorStateCountLabel, javax.swing.GroupLayout.Alignment.TRAILING))
                                        .addComponent(mStateCountLabel))
                                    .addComponent(mActionCountLabel)
                                    .addComponent(mErrorActionCountLabel))
                                .addGap(70, 70, 70)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel12)
                                            .addComponent(jLabel13)
                                            .addComponent(jLabel11)
                                            .addComponent(jLabel10)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel15)
                                            .addComponent(jLabel14))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(mFreqEdgeRatio, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(mSumEdgeFrequency, javax.swing.GroupLayout.Alignment.TRAILING))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(mNodeCountLabel, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(mEdgeCountLabel, javax.swing.GroupLayout.Alignment.LEADING)))
                                    .addComponent(mGoalCountLabel))
                                .addGap(168, 168, 168))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(mNonErrorActionCountLabel)
                                .addGap(186, 186, 186))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(mFileNameLabel))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(mCaseCountLabel)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(mFileNameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(mCaseCountLabel))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(mStateCountLabel)
                    .addComponent(jLabel11)
                    .addComponent(mNodeCountLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(mErrorStateCountLabel)
                    .addComponent(jLabel12)
                    .addComponent(mEdgeCountLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(mNonErrorStateCountLabel)
                    .addComponent(jLabel13)
                    .addComponent(mGoalCountLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(mActionCountLabel)
                    .addComponent(jLabel14)
                    .addComponent(mSumEdgeFrequency))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mErrorActionCountLabel)
                    .addComponent(jLabel7)
                    .addComponent(jLabel15)
                    .addComponent(mFreqEdgeRatio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(mNonErrorActionCountLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel mActionCountLabel;
    private javax.swing.JLabel mCaseCountLabel;
    private javax.swing.JLabel mEdgeCountLabel;
    private javax.swing.JLabel mErrorActionCountLabel;
    private javax.swing.JLabel mErrorStateCountLabel;
    private javax.swing.JLabel mFileNameLabel;
    private javax.swing.JLabel mFreqEdgeRatio;
    private javax.swing.JLabel mGoalCountLabel;
    private javax.swing.JLabel mNodeCountLabel;
    private javax.swing.JLabel mNonErrorActionCountLabel;
    private javax.swing.JLabel mNonErrorStateCountLabel;
    private javax.swing.JLabel mStateCountLabel;
    private javax.swing.JLabel mSumEdgeFrequency;
    // End of variables declaration//GEN-END:variables
}
