package invis.gl.dataprocessor;

/**
 *
 * @author Matt
 */
public class OptionalDataItem
{
    private String mOptionalHeaderTitle;
    private boolean mOptionalHeaderExistsValue;

    public OptionalDataItem(String headerTitle, boolean existsValue)
    {
        mOptionalHeaderTitle = headerTitle;
        mOptionalHeaderExistsValue = existsValue;
    }
    /**
     * Call this to signify that this OptionalHeader exists.
     */
    public void SetHeaderExists()
    {
        mOptionalHeaderExistsValue = true;
    }

    /**
     * This is used by the DataPopulator. If the header item exists in the data
     * then different behavior is executed, then compared to when the data does
     * not contain the data-item.
     * @return true if the OptionalDimension exists in the data.
     */
    public boolean getHeaderExists()
    {
        return mOptionalHeaderExistsValue;
    }

    /**
     * Returns the optional Header Title, which is the name used for the optional
     * data item.
     * @return String, the name of the optional dimensions.
     */
    public String getHeaderTitle()
    {
        return mOptionalHeaderTitle;
    }
    
    @Override
    public String toString()
    {
        return (mOptionalHeaderTitle);
    }
}
