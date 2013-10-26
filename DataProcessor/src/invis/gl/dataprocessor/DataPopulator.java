package invis.gl.dataprocessor;

import invis.gl.api.CaseSetModelApi;
import invis.gl.api.RawCaseModelApi;
import invis.gl.api.RawInteractionApi;
import invis.gl.model.CaseModel;
import invis.gl.model.CaseSetModel;
import invis.gl.model.RawInteractionModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;
import org.openide.filesystems.FileObject;

/**
 *
 * @author Matt
 */
public class DataPopulator
{

    private ArrayList<String> mMandatoryDataHeader;
    private ArrayList<OptionalDataItem> mOptionalDataHeader;
    private ArrayList<String> mInputDataHeader;
    private String mDeliminator;
    private ArrayList<RawCaseModelApi> mCaseList;
    
    FileObject mFileObject;

    //private boolean mLoadImages;
    /**
     * This is the constructor for the DataPopulator. The constructor builds the
     * list of mandatory items in the header. It also initializes the data
     * structures that will hold all of the data for when it is being read in.
     *
     * @param fileObject - This is the input file.
     * @throws Exception
     */
    DataPopulator() throws Exception
    {
        //Build and define our mandatory header data.
        mMandatoryDataHeader = new ArrayList<String>();
        mMandatoryDataHeader.add("STUDENT_ID");
        mMandatoryDataHeader.add("PRE_STATE");
        mMandatoryDataHeader.add("ACTION");
        mMandatoryDataHeader.add("POST_STATE");


        mOptionalDataHeader = new ArrayList<OptionalDataItem>();
        mOptionalDataHeader.add(new OptionalDataItem("ERROR", false));
        mOptionalDataHeader.add(new OptionalDataItem("GOAL", false));
        mOptionalDataHeader.add(new OptionalDataItem("CASE_GROUP_ID", false));
        mOptionalDataHeader.add(new OptionalDataItem("STATE_LABEL", false));
        mOptionalDataHeader.add(new OptionalDataItem("ACTION_LABEL", false));
        mOptionalDataHeader.add(new OptionalDataItem("PRE_CONDITION", false));
        mOptionalDataHeader.add(new OptionalDataItem("POST_CONDITION", false));
        //mOptionalDataHeader.add(new OptionalDataItem("LOAD_IMAGES", false));

        //mLoadImages = false;

        //Initialize data-structures.
        //mDataMap = new HashMap<String, String>();
        mInputDataHeader = new ArrayList<String>();
        mCaseList = new ArrayList<RawCaseModelApi>();
        
    }

    /**
     *
     * @return a list of headers (Strings) that are recognized optional data.
     */
    public ArrayList<String> getValidOptionalHeaderItems()
    {
        ArrayList<String> ValidItemsList = new ArrayList<String>();

        for (int index = 0; index < mOptionalDataHeader.size(); index++)
        {
            if (mOptionalDataHeader.get(index).getHeaderExists())
            {
                ValidItemsList.add(mOptionalDataHeader.get(index).getHeaderTitle());
            }
        }
        return (ValidItemsList);
    }

    /**
     *
     * @return CaseSetModel object - which is built from the mCaseList.
     */
    public CaseSetModelApi getCaseSetModel()
    {
        CaseSetModel csma = new CaseSetModel(mCaseList);
        return (csma);
    }

    /**
     * This method processes the data from an input string. The input file is
     * passed to hear as a String. This method reads each line of the input. If
     * it is the first line, it is recognized as the header line and is passed
     * to process the header. If it is not the first line it is sent to a
     * separate location to be processed.
     *
     * @param inputData - the input from the file as a String.
     * @throws Exception
     */
    public void ProcessData(String inputData) throws Exception
    {
        Scanner scanner = new Scanner(inputData);
        int LineCount = 0;
        while (scanner.hasNext())
        {
            String inputLine = scanner.nextLine();

            //Make sure their is data to read.
            if (!inputLine.isEmpty())
            {
                //If it is the first line, then it is the header line. i.e. different
                //processing to be done.
                if (LineCount == 0)
                {
                    //Process the header line.
                    processHeader(inputLine);
                } else //When it is not the header line, we process the data according
                //to the HashMap we created with the header line. This allows us
                //to have columns in any order in the input file.
                {
                    HashMap<String, String> TheLineMapped = this.ProcessDataMap(inputLine);
                    this.BuildCaseSet(TheLineMapped, LineCount);
                }
            }
            else
            {
                throw new Exception("No data on first line, likely missing header.");
            }
            LineCount++;
        }
    }

    /**
     * This method builds the set of cases, which in turn builds all the data
     * for the DataPopulator. The method gets a line of data from the
     * inputLineMap. First it checks to see if the case for the line exists. If
     * it does exist, it adds the new interaction to the corresponding case. If
     * the case does not already exist, it creates a new CaseModel object and
     * adds the interaction to that CaseModel.
     *
     * @param inputLineMap this HashMap contains the Header as the key, and the
     * object is the actual data. This is used to get the actual data from the
     * input line.
     * @param LineCount this Integer says what line of the inputFile the current
     * line is.
     */
    private void BuildCaseSet(HashMap<String, String> inputLineMap, Integer LineCount)
    {
        boolean exists = false;
        int existsIndex = -1;
        for (int i = 0; i < mCaseList.size(); i++)
        {
            //Get a case-name from the STUDENT_ID column of the input file.
            if (mCaseList.get(i).getCaseName().compareTo(inputLineMap.get("STUDENT_ID")) == 0)
            {
                exists = true;
                existsIndex = i; //This variable is the index of the desired Case.
            }
        }
        if (exists)
        {
            //The current Case already exists, so we add the interaction to the existing case.
            mCaseList.get(existsIndex).addInteraction(
                    new RawInteractionModel(
                    inputLineMap.get("PRE_STATE"),
                    inputLineMap.get("ACTION"),
                    inputLineMap.get("POST_STATE"),
                    LineCount,
                    mCaseList.get(existsIndex).getInteractionsList().size()));

            //Here we process some data for optional header items.
            ProcessOptionalheader(existsIndex, inputLineMap);

        } else
        {
            // Add a new case to the CaseList.
            mCaseList.add(new CaseModel(inputLineMap.get("STUDENT_ID")));
            mCaseList.get(mCaseList.size() - 1).addInteraction(new RawInteractionModel(
                    inputLineMap.get("PRE_STATE"),
                    inputLineMap.get("ACTION"),
                    inputLineMap.get("POST_STATE"),
                    LineCount,
                    mCaseList.get(mCaseList.size() - 1).getInteractionsList().size()));

            //Since we added a new Case to our list, we send the size of the list -1.
            ProcessOptionalheader(mCaseList.size() - 1, inputLineMap);
        }
    }

    /**
     * We process the optional header items separately from the normal
     * processing of the file to keep the code clear.
     *
     * @param existsIndex the index of the current case.
     */
    private void ProcessOptionalheader(int existsIndex, HashMap<String, String> inputLineMap)
    {
        for (int index = 0; index < mOptionalDataHeader.size(); index++)
        {
            if (mOptionalDataHeader.get(index).getHeaderExists())
            {
                if ("ERROR".equals(mOptionalDataHeader.get(index).getHeaderTitle()))
                {
                    //We convert the number error to a boolean.
                    String error = inputLineMap.get("ERROR");

                    int indexLastInteraction = mCaseList.get(existsIndex).getInteractionsList().size() - 1;
                    if (error.equals("0") || error.equals("FALSE") || error.equals("false"))
                    {
                        mCaseList.get(existsIndex).getInteractionsList().get(indexLastInteraction).setErrorValue(false);
                    } else
                    {
                        mCaseList.get(existsIndex).getInteractionsList().get(indexLastInteraction).setErrorValue(true);
                    }
                }

                if ("GOAL".equals(mOptionalDataHeader.get(index).getHeaderTitle()))
                {
                    //We convert the String goal to a boolean.
                    String goal = inputLineMap.get("GOAL");

                    if (goal.equals("true") || goal.equals("TRUE") || goal.equals("1"))
                    {
                        int InteractionIndex = mCaseList.get(existsIndex).getInteractionsList().size() - 1;
                        mCaseList.get(existsIndex).getInteractionsList().get(InteractionIndex).setGoalValue(true);
                        //Add a value to the state.
                        mCaseList.get(existsIndex).getInteractionsList().get(InteractionIndex).updatePostStateGoalValue(true);
                    } else
                    {
                        mCaseList.get(existsIndex).getInteractionsList().get(
                                mCaseList.get(existsIndex).getInteractionsList().size() - 1).setGoalValue(false);
                    }
                }

                if ("CASE_GROUP_ID".equals(mOptionalDataHeader.get(index).getHeaderTitle()))
                {
                    String CaseGroupID = inputLineMap.get("CASE_GROUP_ID");
                    mCaseList.get(existsIndex).setCaseGroupID(CaseGroupID);
                }

                if ("STATE_LABEL".equals(mOptionalDataHeader.get(index).getHeaderTitle()))
                {
                    int indexLastInteraction = mCaseList.get(existsIndex).getInteractionsList().size() - 1;
                    String stateLabel = inputLineMap.get("STATE_LABEL");
                    mCaseList.get(existsIndex).getInteractionsList().get(indexLastInteraction).setPostStateLabel(stateLabel);
                }
                if ("ACTION_LABEL".equals(mOptionalDataHeader.get(index).getHeaderTitle()))
                {
                    int indexLastInteraction = mCaseList.get(existsIndex).getInteractionsList().size() - 1;
                    String actionLabel = inputLineMap.get("ACTION_LABEL");
                    mCaseList.get(existsIndex).getInteractionsList().get(indexLastInteraction).setActionLabel(actionLabel);
                }

                /*if ("LOAD_IMAGES".equals(mOptionalDataHeader.get(index).getHeaderTitle()))
                 {
                 //if the header_item "LOAD_IMAGES" is found in the header then we will load the Beadloom data.
                 //This header_item acts as a flag.
                 for (int i = 0; i < mOptionalDataHeader.size(); i++)
                 {
                 if (mOptionalDataHeader.get(i).getHeaderTitle().equals("LOAD_IMAGES"))
                 {
                 mOptionalDataHeader.get(i).SetHeaderExists();
                 mLoadImages = true;
                 }
                 }
                 }*/

                if ("PRE_CONDITION".equals(mOptionalDataHeader.get(index).getHeaderTitle()))
                {
                    int indexLastInteraction = mCaseList.get(existsIndex).getInteractionsList().size() - 1;
                    String actionPreCondition = inputLineMap.get("PRE_CONDITION");
                    mCaseList.get(existsIndex).getInteractionsList().get(indexLastInteraction).setActionPreCondition(actionPreCondition);
                }
                if ("POST_CONDITION".equals(mOptionalDataHeader.get(index).getHeaderTitle()))
                {
                    int indexLastInteraction = mCaseList.get(existsIndex).getInteractionsList().size() - 1;
                    String actionPostCondition = inputLineMap.get("POST_CONDITION");
                    mCaseList.get(existsIndex).getInteractionsList().get(indexLastInteraction).setActionPostCondition(actionPostCondition);
                }
            }
        }
    }

    /*public boolean getLoadImagesValue()
     {
     return (mLoadImages);
     }*/
    /**
     * This implementation uses String.Split() instead of a StringTokenizer so
     * that it can support "columns" with empty content.
     *
     * This method processes the header of the input file. It determines if the
     * header exists. Next it checks whether the file is tab-deliminated, or
     * comma deliminated. After that it checks to make sure the header contains
     * all of the elements of the Mandatory Data for the header.
     *
     * If no errors are detected in the header the method exits and the header
     * should be successfully read in.
     *
     * @param header the first line of the file, which is the header line.
     * @throws Exception
     */
    private void processHeader(String header) throws Exception
    {
        mDeliminator = "\t";
        String[] HeaderSplit = header.split("\t");

        if (HeaderSplit.length < 1)
        {
            throw new Exception("Missing Header");
        }

        //We check, if there is only 1 token, then perhaps they did not  use a 
        //tab deliminator, so we test a comma delimiter.
        if (HeaderSplit.length == 1)
        {
            HeaderSplit = header.split(",");
            mDeliminator = ",";
        }

        if (HeaderSplit.length == 1)
        {
            throw new Exception("The contents of the file header are not "
                    + "separated by either tabs or commas, the file cannot "
                    + "be opened.\n" + "A proper format is: STUDENT_ID,PRE_STATE"
                    + ",ACTION,POST_STATE");
        }


        for (int i = 0; i < HeaderSplit.length; i++)
        {
            mInputDataHeader.add(HeaderSplit[i].toUpperCase());
        }

        for (int i = 0; i < mMandatoryDataHeader.size(); i++)
        {
            //If the input-fileHeader does not contain mandatory-headerData,
            //there is a problem.
            if (!mInputDataHeader.contains(mMandatoryDataHeader.get(i)))
            {
                
                throw new Exception("Mandatory Header, "+mMandatoryDataHeader.get(i)+" Values Missing");
            }
        }
        this.ProcessOptionalHeaderData();
    }

    /**
     * This method processes the header of the input file. It determines if the
     * header exists. Next it checks whether the file is tab-deliminated, or
     * comma deliminated. After that it checks to make sure the header contains
     * all of the elements of the Mandatory Data for the header.
     *
     * If no errors are detected in the header the method exits and the header
     * should be successfully read in.
     *
     * @param header the first line of the file, which is the header line.
     * @throws Exception
     */
    private void processHeader_StringTokenizer(String header) throws Exception
    {
        StringTokenizer st = new StringTokenizer(header, "\t");
        mDeliminator = "\t";

        if (st.countTokens() < 1)
        {
            throw new Exception("Missing Header");
        }

        //We check, if there is only 1 token, then perhaps they did not  use a 
        //tab deliminator, so we test a comma delimiter.
        if (st.countTokens() == 1)
        {
            st = new StringTokenizer(header, ",");
            mDeliminator = ",";
        }

        if (st.countTokens() == 1)
        {
            throw new Exception("The contents of the file header are not "
                    + "separated by either tabs or commas, the file cannot "
                    + "be opened.\n" + "A proper format is: STUDENT_ID,PRE_STATE"
                    + ",ACTION,POST_STATE");
        }

        while (st.hasMoreTokens())
        {
            //Add all the header's from the InputFile.
            mInputDataHeader.add(st.nextToken().toUpperCase());
        }

        for (int i = 0; i < mMandatoryDataHeader.size(); i++)
        {
            //If the input-fileHeader does not contain mandatory-headerData,
            //there is a problem.
            if (!mInputDataHeader.contains(mMandatoryDataHeader.get(i)))
            {
                throw new Exception("Mandatory Header Values Missing");
            }
        }
        this.ProcessOptionalHeaderData();
    }

    private HashMap<String, String> ProcessDataMap(String inputLine)
    {
        HashMap<String, String> DataMap = new HashMap<String, String>();
        String[] Data = inputLine.split(mDeliminator);

        for (int i = 0; i < mInputDataHeader.size(); i++)
        {
            String label = mInputDataHeader.get(i);
            DataMap.put(label, Data[i].trim());
        }
        return (DataMap);
    }

    /**
     * This method constructs the DataMap, which maps the header label, to the
     * actual content. The header-label becomes the key in the HashMap and the
     * Object is the contents of the file.
     *
     * @param inputLine this is the string of text from input file.
     * @return DataMap - a HashMap that associates the header-label with the
     * contents of the input file.
     */
    private HashMap<String, String> ProcessDataMap_StringTokenizer(String inputLine)
    {
        HashMap<String, String> DataMap = new HashMap<String, String>();
        StringTokenizer st = new StringTokenizer(inputLine, mDeliminator);
        int i = 0;
        while (st.hasMoreTokens())
        {
            String label = mInputDataHeader.get(i++);
            DataMap.put(label, st.nextToken().trim());
        }
        return (DataMap);
    }

    /**
     * Based on what was read-in, in the InputFile-header, determine which
     * OptionalHeaderItems exist.
     */
    private void ProcessOptionalHeaderData()
    {
        for (int index = 0; index < mOptionalDataHeader.size(); index++)
        {
            if (mInputDataHeader.contains(mOptionalDataHeader.get(index).getHeaderTitle()))
            {
                mOptionalDataHeader.get(index).SetHeaderExists();
            }
        }
    }
}
