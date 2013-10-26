/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package invis.gl.viewer_v2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;
import org.openide.loaders.DataObject;

/**
 *
 * @author Matt
 */
public class FileToObjectParser
{

    private DataObject mDobj;
    private ArrayList<String> mMandatoryDataHeader;
    private ArrayList<String> mInputDataHeader;
    private String mDeliminator;
    
    

    public FileToObjectParser(DataObject dObj)
    {
        mDobj = dObj;
    }

    public boolean ProcessDataObject(DataObject dObj) throws Exception
    {
        Scanner scanner = new Scanner(mDobj.getPrimaryFile().asText());
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
                } else
                {
                    //RawDataEntry newRDE = new RawDataEntry(MapInputData(inputLine));
                    //rde.add(newRDE);
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
