package invis.gl.rawinput;

import java.util.ArrayList;

/**
 *
 * @author Matt
 */
public class RawInputData
{
    private ArrayList<RawDataEntry> mRawDataEntryList;
    private String mFileName;

    public RawInputData()
    {
        mRawDataEntryList = new ArrayList<RawDataEntry>();
        mFileName = "";
    }
    
    public void add (RawDataEntry rde)
    {
        mRawDataEntryList.add(rde);
    }
    public String getFileName()
    {
        return (mFileName);
    }
    
    public ArrayList<RawDataEntry> getRawDataEntryList()
    {
        return (mRawDataEntryList);
    }

    public void setFileName(String fileName)
    {
        mFileName = fileName;
    }
}
