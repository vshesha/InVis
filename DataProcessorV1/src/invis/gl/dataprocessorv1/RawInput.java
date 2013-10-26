package invis.gl.dataprocessorv1;

/**
 *
 * @author Matt
 */

import invis.gl.fileio.FileIO;
import invis.gl.rawinput.RawDataEntry;
import invis.gl.rawinput.RawInputData;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = RawInputInterface.class)

public class RawInput implements RawInputInterface
{
    private ArrayList<String> mMandatoryDataHeader;
    private ArrayList<String> mInputDataHeader;
    private String mDeliminator;
    private RawInputData mRawInputData;
    private String mFileName;

    public RawInput()
    {
        //Build and define our mandatory header data.
        mMandatoryDataHeader = new ArrayList<String>();
        mMandatoryDataHeader.add("STUDENT_ID");
        mMandatoryDataHeader.add("PRE_STATE");
        mMandatoryDataHeader.add("ACTION");
        mMandatoryDataHeader.add("POST_STATE");

        mInputDataHeader = new ArrayList<String>();
        mFileName = "";
    }
    
    public String getFileName()
    {
        return (mFileName);
    }
    
    @Override
    public RawInputData getRawInputData()
    {
        return (mRawInputData);
}
    
    public ArrayList<String> getInputDataHeader()
    {
        return (mInputDataHeader);
    }


    @Override
    public boolean ReadInFile(RawInputData rid) throws Exception
    {
        String defaultDirectory = System.getProperty("user.dir");
        final JFileChooser jfc = new JFileChooser(defaultDirectory);
        jfc.setMultiSelectionEnabled(false);
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc.setAcceptAllFileFilterUsed(false);

        if (jfc.showOpenDialog(jfc) != JFileChooser.APPROVE_OPTION)
        {
            return (false);
        }

        File file = jfc.getSelectedFile();

        mFileName = file.getName();
        //mRawInputData.setFileName(mFileName);
        rid.setFileName(mFileName);
        
        if (file == null)
        {
            return (false);
        }
        String inputData = FileIO.getContents(file);
        ProcessData(inputData, rid);
        mRawInputData = rid;
        return (true);
    }

    public boolean ProcessData(String inputData, RawInputData rde) throws Exception
    {
        Scanner scanner = new Scanner(inputData);
        boolean isFirstLine = true;
        while (scanner.hasNext())
        {
            String inputLine = scanner.nextLine();
            if (!inputLine.isEmpty())
            {
                if (isFirstLine)
                {
                    processHeader(inputLine);
                    isFirstLine = !isFirstLine;
                } 
                else
                {
                    RawDataEntry newRDE = new RawDataEntry(MapInputData(inputLine));
                    rde.add(newRDE);
                }
            }
        }
        return (true);
    }

    private void processHeader(String header) throws Exception
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

        for (int i = 0; i < mInputDataHeader.size(); i++)
        {
            if (!mMandatoryDataHeader.contains(mInputDataHeader.get(i)))
            {
                //Do we need to let the RawDataEntry be aware of this?
            }
        }
    }

    private HashMap<String, String> MapInputData(String inputLine)
    {
        HashMap<String, String> map = new HashMap<String, String>();
        StringTokenizer st = new StringTokenizer(inputLine, mDeliminator);
        int i = 0;
        while (st.hasMoreTokens())
        {
            String label = mInputDataHeader.get(i++);
            map.put(label, st.nextToken().trim());
        }
        return map;
    }
}
