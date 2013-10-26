package invis.gl.dataexporter;

import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import invis.gl.NetworkClusterApi.NetworkClusterEdgeApi;
import invis.gl.NetworkClusterApi.NetworkClusterElementApi;
import invis.gl.NetworkClusterApi.NetworkClusterVertexApi;
import invis.gl.dataprocessor.DataParser;
import invis.gl.fileio.FileIO;
import invis.gl.networkapi.NetworkElementApi;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Boolean;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.openide.util.Lookup;

/**
 *
 * @author Matt
 */
public class InteractionNetworkExporter
{

    private enum ElementType
    {

        Node, Edge
    };

    public enum NetworkType
    {

        InteractionNetwork, StepbasedNetwork
    };
    DataParser mDataParser;
    StringBuilder mExportContents = new StringBuilder();
    File mFile = null;

    public InteractionNetworkExporter()
    {
        try
        {
            mFile = FileIO.SaveFile();
        } catch (Exception ex)
        {
            Logger.getLogger(InteractionNetworkExporter.class.getName()).log(Level.SEVERE, null, ex);
        }
        mDataParser = Lookup.getDefault().lookup(DataParser.class);
    }

    public void ExportSequenceData()
    {
        this.BuildSequenceOutput();
        this.WriteExportContents();
    }

    private void BuildSequenceOutput()
    {
        //We will build the sequences for all cases.
        for (int caseItr = 0; caseItr < mDataParser.getNetworkCaseSet().getNetworkCaseSetSize(); caseItr++)
        {
            StringBuilder sequence = new StringBuilder();
            //Build a sequence for a single case.
            for (int actionItr = 0; actionItr < mDataParser.getNetworkCaseSet().getNetworkCaseList().get(caseItr).getInteractionsList().size(); actionItr++)
            {

                if (mDataParser.getGraph().containsEdge(mDataParser.getNetworkCaseSet().getNetworkCaseList().get(caseItr).getInteractionsList().get(actionItr).getAction().toString()))
                {
                    //Append the action.
                    sequence.append(
                            mDataParser.getNetworkCaseSet().getNetworkCaseList().get(caseItr).getInteractionsList().get(actionItr).getAction().getValue());
                    //Open paren
                    sequence.append("(");
                    //the post condition.
                    sequence.append(
                            mDataParser.getNetworkCaseSet().getNetworkCaseList().get(caseItr).getInteractionsList().get(actionItr).getActionPostCondition());
                    //close paren, comma, space. This prepares us for the next action.
                    sequence.append("), -1 ");
                }
            }
            //Once we've compiled a single cases sequence, send it off to add to the output.
            sequence.append("-2");
            this.ExportSequence(sequence.toString());
        }
    }

    private void ExportSequence(String sequence)
    {
        mExportContents.append(sequence).append("\n");
    }

    public void ExportStepbasedNetwork()
    {
        this.BuildStepbasedHeader();
        this.BuildStepbasedNetwork();
        this.WriteExportContents();
    }

    private void BuildStepbasedHeader()
    {
        if (!mDataParser.DataContainsCaseGroupIds())
        {
            mExportContents.append("Source" + "\t" + "Action" + "\t" + "Target"
                    + "\t" + "Action-Label" + "\t" + "Post_Condition" + "\t" + "goal" + "\t"
                    + "Src-Frequency" + "\t" + "Action-Frequency" + "\t" + "Target-Frequency");
        }
        mExportContents.append("\n");
    }

    private void BuildStepbasedNetwork()
    {
        for (int i = 0; i < mDataParser.getDerivedData().getGraph().getVertices().size(); i++)
        {
            String source = mDataParser.getDerivedData().getGraph().getVertices().toArray()[i].toString();

            for (int outEdgeIndex = 0; outEdgeIndex < mDataParser.getDerivedData().getGraph().getOutEdges(source).size(); outEdgeIndex++)
            {
                //mDataParser.getNetworkCaseSet().getNetworkCaseList().get(0).getCaseGroupID();
                String edge = mDataParser.getDerivedData().getGraph().getOutEdges(source).toArray()[outEdgeIndex].toString();
                String target = mDataParser.getDerivedData().getGraph().getDest(edge);

                if (!mDataParser.DataContainsCaseGroupIds())
                {
                    int SinglesrcFreq = mDataParser.getDerivedData().getNodeTable().get(source).getTotalUniqueFrequency();
                    int SingleactFreq = mDataParser.getDerivedData().getEdgeTable().get(edge).getTotalUniqueFrequency();
                    int SingletarFreq = mDataParser.getDerivedData().getNodeTable().get(target).getTotalUniqueFrequency();

                    //String xActionLabel = mDataParser.getDerivedData().getEdgeTable().get(edge).getElementLabel();
                    String ActionLabel = mDataParser.getDerivedData().getEdgeTable().get(edge).getValue();
                    String postCondition = mDataParser.getDerivedData().getNodeTable().get(target).getValue();

                    boolean goal = false;
                    if (mDataParser.DataContainsGoalStates())
                    {
                        goal = mDataParser.getDerivedData().getNodeTable().get(target).getContainsGoal();
                    }

                    this.WriteInteractionNetworkLine(source, edge, target, ActionLabel, postCondition, goal, SinglesrcFreq, SingleactFreq, SingletarFreq);
                }
            }
        }
    }

    private void WriteExportContents()
    {
        try
        {
            FileIO.setContents(mFile, mExportContents.toString());
        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(InteractionNetworkExporter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(InteractionNetworkExporter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ExportInteractionNetwork()
    {
        this.BuildInteractionNetworkHeader();
        this.BuildInteractionNetworkData();
        this.WriteExportContents();
    }

    private void BuildInteractionNetworkHeader()
    {
        if (mDataParser.DataContainsCaseGroupIds())
        {
            Set<String> caseGroupIDSet = mDataParser.getNetworkCaseSet().getUniqueCaseGroupIdSet();
            //mExportContents.append("Source" + "\t" + "Action" + "\t" + "Target"); //+ "Src-Frequency" + "\t" + "Action-Frequency" + "\t" + "Target-Frequency");

            mExportContents.append("Source" + "\t" + "Action" + "\t" + "Target" + "\t");
            //Print the source frequencies for each group.
            for (int i = 0; i < caseGroupIDSet.size(); i++)
            {
                mExportContents.append("SF-grp: " + caseGroupIDSet.toArray()[i].toString() + "\t");
            }
            for (int i = 0; i < caseGroupIDSet.size(); i++)
            {
                mExportContents.append("AF-grp: " + caseGroupIDSet.toArray()[i].toString() + "\t");
            }
            for (int i = 0; i < caseGroupIDSet.size(); i++)
            {
                mExportContents.append("TF-grp: " + caseGroupIDSet.toArray()[i].toString() + "\t");
            }
        }


        if (!mDataParser.DataContainsCaseGroupIds())
        {
            mExportContents.append("Source" + "\t" + "Action" + "\t" + "Target"
                    + "\t" + "Action-Label" + "\t" + "Post_Condition" + "\t" + "goal" + "\t"
                    + "Src-Frequency" + "\t" + "Action-Frequency" + "\t" + "Target-Frequency");
        }
        mExportContents.append("\n");
    }

    private void BuildInteractionNetworkData()
    {
        Set<String> caseGroupIDSet = mDataParser.getNetworkCaseSet().getUniqueCaseGroupIdSet();

        for (int i = 0; i < mDataParser.getGraph().getVertices().size(); i++)
        {
            String source = mDataParser.GetDataNetwork().getGraph().getVertices().toArray()[i].toString();

            for (int outEdgeIndex = 0; outEdgeIndex < mDataParser.getGraph().getOutEdges(source).size(); outEdgeIndex++)
            {
                //mDataParser.getNetworkCaseSet().getNetworkCaseList().get(0).getCaseGroupID();
                String edge = mDataParser.getGraph().getOutEdges(source).toArray()[outEdgeIndex].toString();
                String target = mDataParser.getGraph().getDest(edge);

                if (mDataParser.DataContainsCaseGroupIds())
                {
                    LinkedList<Integer> srcFreq = new LinkedList<Integer>();
                    LinkedList<Integer> actFreq = new LinkedList<Integer>();
                    LinkedList<Integer> tarFreq = new LinkedList<Integer>();

                    for (int CaseGroupIdIndex = 0; CaseGroupIdIndex < caseGroupIDSet.size(); CaseGroupIdIndex++)
                    {
                        srcFreq.add(getCaseGroupIdFrequencyMapBasedOnNetworkElement(source, ElementType.Node).get(caseGroupIDSet.toArray()[CaseGroupIdIndex].toString()));
                        actFreq.add(getCaseGroupIdFrequencyMapBasedOnNetworkElement(edge, ElementType.Edge).get(caseGroupIDSet.toArray()[CaseGroupIdIndex].toString()));
                        Integer x = getCaseGroupIdFrequencyMapBasedOnNetworkElement(target, ElementType.Node).get(caseGroupIDSet.toArray()[CaseGroupIdIndex].toString());
                        tarFreq.add(x);

                    }
                    this.WriteInteractionNetworkLine(source, edge, target, srcFreq, actFreq, tarFreq);
                }

                if (!mDataParser.DataContainsCaseGroupIds())
                {
                    int SinglesrcFreq = mDataParser.getNodeTable().get(source).getCaseFrequency();
                    int SingleactFreq = mDataParser.getEdgeTable().get(edge).getCaseFrequency();
                    int SingletarFreq = mDataParser.getNodeTable().get(target).getCaseFrequency();

                    String xActionLabel = mDataParser.getEdgeTable().get(edge).getElementLabel();
                    String ActionLabel = mDataParser.getEdgeTable().get(edge).getValue();
                    String postCondition = mDataParser.getNodeTable().get(target).getPostConditionStringList();

                    boolean goal = false;
                    if (mDataParser.DataContainsGoalStates())
                    {
                        goal = mDataParser.getNodeTable().get(target).getGoalValue();
                    }

                    this.WriteInteractionNetworkLine(source, edge, target, ActionLabel, postCondition, goal, SinglesrcFreq, SingleactFreq, SingletarFreq);
                }
            }
        }
    }

    public HashMap<String, Integer> getCaseGroupIdFrequencyMapBasedOnNetworkElement(String Element, ElementType type)
    {
        HashMap<String, Integer> CaseGroupIdFrequencyMap = new HashMap<String, Integer>();

        Set<String> caseGroupIDSet = mDataParser.getNetworkCaseSet().getUniqueCaseGroupIdSet();
        int caseGroupIDSetSize = caseGroupIDSet.size();
        //This is only useful if cases in the data have group-Ids so we check that first.
        if (mDataParser.DataContainsCaseGroupIds())
        {
            //if they do have Group-Ids, then we iterate through the mCaseGroupIdList.
            //The mCaseGroupIdList is populated from the private function GenerateGroupIdContainers().
            //Which is called by setDataContainsCaseGroupIds(). setDataContainsCaseGroupIds also sets mCasesHaveGroupIds to true.
            if (type == ElementType.Node)
            {
                //Set all the frequencies for the current node to zero (0).
                for (int caseIdIndex = 0; caseIdIndex < caseGroupIDSetSize; caseIdIndex++)
                {
                    CaseGroupIdFrequencyMap.put(caseGroupIDSet.toArray()[caseIdIndex].toString(), 0);
                }

                //Iterate through the people on the current node.
                for (int caseIndex = 0; caseIndex < mDataParser.getNodeTable().get(Element).getCaseIdList().size(); caseIndex++)
                {
                    String currentCase = mDataParser.getNodeTable().get(Element).getCaseIdList().get(caseIndex);
                    String CurrentCasesGroupID = mDataParser.getNetworkCaseSet().findCaseByCaseId(currentCase).getCaseGroupID();

                    if (CaseGroupIdFrequencyMap.containsKey(CurrentCasesGroupID))
                    {
                        //Increase an already existing item.
                        int elementCount = CaseGroupIdFrequencyMap.get(CurrentCasesGroupID);
                        elementCount++;
                        CaseGroupIdFrequencyMap.put(CurrentCasesGroupID, elementCount);
                    }
                    //There should not be an ELSE because, we already instantiated all values to zero.
                    //else
                    //{
                    //else add a new item, with frequency 1.

                    //uniqueList.add(mCaseGroupIdList.get(networkCaseIndex); //This will make the list of uniqueCaseGroupIds....
                    //Which could be a more efficient method than making mUniqueCaseGroupIdSet (HashSet) in GenerateGroupIdContainers()
                    // However I think calling the HashSet constructor is pretty fast.
                    //CaseGroupIdFrequencyMap.put(caseGroupIDSet.toArray()[i].toString(), 1);
                    //}

                }
            }

            if (type == ElementType.Edge)
            {
                //Set all the frequencies for the current node to zero (0).
                for (int caseIdIndex = 0; caseIdIndex < caseGroupIDSetSize; caseIdIndex++)
                {
                    CaseGroupIdFrequencyMap.put(caseGroupIDSet.toArray()[caseIdIndex].toString(), 0);
                }

                //Iterate through the people on the current node.
                for (int caseIndex = 0; caseIndex < mDataParser.getEdgeTable().get(Element).getCaseIdList().size(); caseIndex++)
                {

                    //for (int i = 0; i < mDataParser.getNetworkCaseSet().getUniqueCaseGroupIdSet().size(); i++)
                    String currentCase = mDataParser.getEdgeTable().get(Element).getCaseIdList().get(caseIndex);
                    String CurrentCasesGroupID = mDataParser.getNetworkCaseSet().findCaseByCaseId(currentCase).getCaseGroupID();

                    if (CaseGroupIdFrequencyMap.containsKey(CurrentCasesGroupID))
                    {
                        //Increase an already existing item.
                        int elementCount = CaseGroupIdFrequencyMap.get(CurrentCasesGroupID);
                        elementCount++;
                        CaseGroupIdFrequencyMap.put(CurrentCasesGroupID, elementCount);
                    }
                }

            }
        }
        return (CaseGroupIdFrequencyMap);
    }

    private void WriteInteractionNetworkLine(String source, String edge, String target, LinkedList<Integer> srcFreq, LinkedList<Integer> actFreq, LinkedList<Integer> tarFreq)
    {
        mExportContents.append(source).append("\t");
        mExportContents.append(edge).append("\t");
        mExportContents.append(target).append("\t");
        for (int i = 0; i < srcFreq.size(); i++)
        {
            mExportContents.append(srcFreq.get(i)).append("\t");
        }
        for (int i = 0; i < actFreq.size(); i++)
        {
            mExportContents.append(actFreq.get(i)).append("\t");
        }
        for (int i = 0; i < tarFreq.size(); i++)
        {
            mExportContents.append(tarFreq.get(i)).append("\t");
        }

        mExportContents.append("\n");
    }

    private void WriteInteractionNetworkLine(String source, String edge, String target, String actionLabel, String postCondition, boolean goal, int srcFreq, int actFreq, int tarFreq)
    {
        mExportContents.append(source).append("\t");
        mExportContents.append(edge).append("\t");
        mExportContents.append(target).append("\t");

        mExportContents.append(actionLabel).append("\t");
        mExportContents.append(postCondition).append("\t");
        mExportContents.append(goal).append("\t");

        mExportContents.append(srcFreq).append("\t");
        mExportContents.append(actFreq).append("\t");
        mExportContents.append(tarFreq);
        mExportContents.append("\n");
    }

    private WritableWorkbook WriteStepbasedNetwork(WritableWorkbook wb)
    {
        this.writeEdgeList(wb.getSheet(0),
                mDataParser.getDerivedData().getGraph().getVertices(),
                mDataParser.getDerivedData().getGraph(),
                mDataParser.getDerivedData().getNodeTable(),
                mDataParser.getDerivedData().getEdgeTable());
        this.writeNodeList(wb.getSheet(1),
                mDataParser.getDerivedData().getGraph().getVertices(),
                mDataParser.getDerivedData().getNodeTable());
        return (wb);
    }

    private WritableWorkbook WriteInteractionNetwork(WritableWorkbook wb)
    {
        this.writeInteractionNetworkEdgeList(wb.getSheet(0),
                mDataParser.getGraph().getVertices(),
                mDataParser.getGraph(),
                mDataParser.getNodeTable(),
                mDataParser.getEdgeTable());
        this.writeInteractionNetworkNodeList(wb.getSheet(1),
                mDataParser.getGraph().getVertices(),
                mDataParser.getNodeTable());
        return (wb);
    }

    public void ExportNetworkToExcel(NetworkType type) throws IOException
    {

        WorkbookSettings ws = new WorkbookSettings();
        ws.setLocale(new Locale("en", "EN"));

        WritableWorkbook wb = Workbook.createWorkbook(mFile, ws);
        try
        {
            this.writeEdgeHeader(wb.createSheet("EdgeList", 0));
            this.writeNodeHeader(wb.createSheet("NodeList", 1));


            if (type == NetworkType.StepbasedNetwork)
            {
                WriteStepbasedNetwork(wb);
            }
            if (type == NetworkType.InteractionNetwork)
            {
                WriteInteractionNetwork(wb);
            }

        } catch (WriteException ex)
        {
            Logger.getLogger(InteractionNetworkExporter.class.getName()).log(Level.SEVERE, null, ex);
        }

        wb.write();
        try
        {
            wb.close();
        } catch (WriteException ex)
        {
            Logger.getLogger(InteractionNetworkExporter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void writeInteractionNetworkEdgeList(WritableSheet sheet, Collection<String> vertices, DirectedSparseMultigraph<String, String> graph, HashMap<String, NetworkElementApi> nodeTable, HashMap<String, NetworkElementApi> edgeTable)
    {
        for (int i = 0; i < vertices.size(); i++)
        {
            String source = vertices.toArray()[i].toString();

            for (int outEdgeIndex = 0; outEdgeIndex < graph.getOutEdges(source).size(); outEdgeIndex++)
            {
                //mDataParser.getNetworkCaseSet().getNetworkCaseList().get(0).getCaseGroupID();
                String edge = graph.getOutEdges(source).toArray()[outEdgeIndex].toString();
                String target = graph.getDest(edge);

                int SinglesrcFreq = nodeTable.get(source).getUniqueFrequency();
                int SingleactFreq = edgeTable.get(edge).getUniqueFrequency();
                int SingletarFreq = nodeTable.get(target).getUniqueFrequency();

                //String xActionLabel = mDataParser.getDerivedData().getEdgeTable().get(edge).getElementLabel();
                String ActionLabel = edgeTable.get(edge).getValue();
                String postCondition = nodeTable.get(target).getValue();

                boolean goal = false;
                if (mDataParser.DataContainsGoalStates())
                {
                    goal = nodeTable.get(target).getGoalValue();
                }
                try
                {
                    //this.WriteInteractionNetworkLine(source, edge, target, ActionLabel, postCondition, goal, SinglesrcFreq, SingleactFreq, SingletarFreq);
                    this.writeEdgeRow(sheet, source, edge, target, ActionLabel, postCondition, goal, SinglesrcFreq, SingleactFreq, SingletarFreq);
                } catch (WriteException ex)
                {
                    Logger.getLogger(InteractionNetworkExporter.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    private void writeInteractionNetworkNodeList(WritableSheet sheet, Collection<String> vertices, HashMap<String, NetworkElementApi> nodeTable)
    {
        for (int i = 0; i < vertices.size(); i++)
        {
            String source = vertices.toArray()[i].toString();

            boolean goal = nodeTable.get(source).getGoalValue();

            int caseFrequency = nodeTable.get(source).getCaseFrequency();

            String label = nodeTable.get(source).getMostFrequentUniquePostCondition();

            int uniqueFreq = nodeTable.get(source).getUniqueFrequency();

            Double ExtraData = mDataParser.getExtraDatatable().get(source);

            try
            {
                this.writeNodeRow(sheet, source, goal, label, caseFrequency, uniqueFreq, ExtraData);
            } catch (WriteException ex)
            {
                Logger.getLogger(InteractionNetworkExporter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //Writes for Stepbased Network.
    private void writeNodeList(WritableSheet sheet, Collection<String> vertices, HashMap<String, NetworkClusterVertexApi> nodeTable)
    {
        for (int i = 0; i < vertices.size(); i++)
        {
            String source = vertices.toArray()[i].toString();

            boolean goal = nodeTable.get(source).getContainsGoal();

            int ElementCount = nodeTable.get(source).getElementCount();

            int uniqueFreq = nodeTable.get(source).getTotalUniqueFrequency();

            Double ExtraData = mDataParser.getExtraDatatable().get(source);

            try
            {
                this.writeNodeRow(sheet, source, goal, "label", ElementCount, uniqueFreq, ExtraData);
            } catch (WriteException ex)
            {
                Logger.getLogger(InteractionNetworkExporter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //Writes for Stepbased Network.
    private void writeEdgeList(WritableSheet sheet, Collection<String> vertices,
            DirectedSparseMultigraph<String, String> graph,
            HashMap<String, NetworkClusterVertexApi> nodeTable,
            HashMap<String, NetworkClusterElementApi> edgeTable)
    {
        for (int i = 0; i < vertices.size(); i++)
        {
            String source = vertices.toArray()[i].toString();

            for (int outEdgeIndex = 0; outEdgeIndex < graph.getOutEdges(source).size(); outEdgeIndex++)
            {
                //mDataParser.getNetworkCaseSet().getNetworkCaseList().get(0).getCaseGroupID();
                String edge = graph.getOutEdges(source).toArray()[outEdgeIndex].toString();
                String target = graph.getDest(edge);
                if (!mDataParser.DataContainsCaseGroupIds())
                {
                    int SinglesrcFreq = nodeTable.get(source).getTotalUniqueFrequency();
                    int SingleactFreq = edgeTable.get(edge).getTotalUniqueFrequency();
                    int SingletarFreq = nodeTable.get(target).getTotalUniqueFrequency();

                    //String xActionLabel = mDataParser.getDerivedData().getEdgeTable().get(edge).getElementLabel();
                    String ActionLabel = edgeTable.get(edge).getValue();
                    String postCondition = nodeTable.get(target).getValue();

                    boolean goal = false;
                    if (mDataParser.DataContainsGoalStates())
                    {
                        goal = nodeTable.get(target).getContainsGoal();
                    }
                    try
                    {
                        //this.WriteInteractionNetworkLine(source, edge, target, ActionLabel, postCondition, goal, SinglesrcFreq, SingleactFreq, SingletarFreq);
                        this.writeEdgeRow(sheet, source, edge, target, ActionLabel, postCondition, goal, SinglesrcFreq, SingleactFreq, SingletarFreq);
                    } catch (WriteException ex)
                    {
                        Logger.getLogger(InteractionNetworkExporter.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    /*
     private void writeNodeList(WritableSheet nodeSheet) throws WriteException
     {
     for (int i = 0; i < mDataParser.getDerivedData().getGraph().getVertices().size(); i++)
     {
     String source = mDataParser.getDerivedData().getGraph().getVertices().toArray()[i].toString();

     boolean goal = mDataParser.getDerivedData().getNodeTable().get(source).getContainsGoal();

     int ElementCount = mDataParser.getDerivedData().getNodeTable().get(source).getStateCount();

     int uniqueFreq = mDataParser.getDerivedData().getNodeTable().get(source).getTotalUniqueFrequency();

     this.writeNodeRow(nodeSheet, source, goal, "label", ElementCount, uniqueFreq);
     }
     }*/
    /*
     private void writeEdgeList(WritableSheet edgeSheet) throws WriteException
     {
     for (int i = 0; i < mDataParser.getDerivedData().getGraph().getVertices().size(); i++)
     {
     String source = mDataParser.getDerivedData().getGraph().getVertices().toArray()[i].toString();

     for (int outEdgeIndex = 0; outEdgeIndex < mDataParser.getDerivedData().getGraph().getOutEdges(source).size(); outEdgeIndex++)
     {
     //mDataParser.getNetworkCaseSet().getNetworkCaseList().get(0).getCaseGroupID();
     String edge = mDataParser.getDerivedData().getGraph().getOutEdges(source).toArray()[outEdgeIndex].toString();
     String target = mDataParser.getDerivedData().getGraph().getDest(edge);
     if (!mDataParser.DataContainsCaseGroupIds())
     {
     int SinglesrcFreq = mDataParser.getDerivedData().getNodeTable().get(source).getTotalUniqueFrequency();
     int SingleactFreq = mDataParser.getDerivedData().getEdgeTable().get(edge).getTotalUniqueFrequency();
     int SingletarFreq = mDataParser.getDerivedData().getNodeTable().get(target).getTotalUniqueFrequency();

     //String xActionLabel = mDataParser.getDerivedData().getEdgeTable().get(edge).getElementLabel();
     String ActionLabel = mDataParser.getDerivedData().getEdgeTable().get(edge).getValue();
     String postCondition = mDataParser.getDerivedData().getNodeTable().get(target).getValue();

     boolean goal = false;
     if (mDataParser.DataContainsGoalStates())
     {
     goal = mDataParser.getDerivedData().getNodeTable().get(target).getContainsGoal();
     }

     //this.WriteInteractionNetworkLine(source, edge, target, ActionLabel, postCondition, goal, SinglesrcFreq, SingleactFreq, SingletarFreq);
     this.writeEdgeRow(edgeSheet, source, edge, target, ActionLabel, postCondition, goal, SinglesrcFreq, SingleactFreq, SingletarFreq);
     }
     }
     }
     }*/
    private void writeNodeHeader(WritableSheet sheet) throws WriteException
    {
        String[] header = "State\tGoal\tLabel\tfreq\tuniqueFreq\tExtraData".split("\t");

        for (int i = 0; i < header.length; i++)
        {
            sheet.addCell(new Label(i, 0, header[i]));
        }
    }

    private void writeEdgeHeader(WritableSheet sheet) throws WriteException
    {
        //String[] header = "Action\tPreCondition\tPostCondition\tStartState\tEndState\tGoal\tError\tFreq".split("\t");
        String[] header = "Source\tEdge\tTarget\tactionLabel\tpostCondition\tGoal\tSrcFrequency\tActFrequency\ttarFrequency".split("\t");

        for (int i = 0; i < header.length; i++)
        {
            sheet.addCell(new Label(i, 0, header[i]));
        }
    }

    private void writeEdgeRow(WritableSheet edgeSheet, String source, String edge, String target, String actionLabel, String postCondition, boolean goal, int srcFreq, int actFreq, int tarFreq) throws WriteException
    {
        int rows = edgeSheet.getRows();
        edgeSheet.addCell(new Label(0, rows, source));
        edgeSheet.addCell(new Label(1, rows, edge));
        edgeSheet.addCell(new Label(2, rows, target));
        edgeSheet.addCell(new Label(3, rows, actionLabel));
        edgeSheet.addCell(new Label(4, rows, postCondition));
        edgeSheet.addCell(new Boolean(5, rows, goal));
        edgeSheet.addCell(new Number(6, rows, srcFreq));
        edgeSheet.addCell(new Number(7, rows, actFreq));
        edgeSheet.addCell(new Number(8, rows, tarFreq));
    }

    private void writeNodeRow(WritableSheet nodeSheet, String state, boolean goal, String label, int caseFrequency, int UniqueFrequency, Double ExtraData) throws WriteException
    {
        int rows = nodeSheet.getRows();
        nodeSheet.addCell(new Label(0, rows, state));
        nodeSheet.addCell(new Boolean(1, rows, goal));
        nodeSheet.addCell(new Label(2, rows, label));
        nodeSheet.addCell(new Number(3, rows, caseFrequency));
        nodeSheet.addCell(new Number(4, rows, UniqueFrequency));
        if (ExtraData != null)
        {
            nodeSheet.addCell(new Number(5, rows, ExtraData));
        } else
        {
            nodeSheet.addCell(new Number(5, rows, 0.0));
        }
    }
}
