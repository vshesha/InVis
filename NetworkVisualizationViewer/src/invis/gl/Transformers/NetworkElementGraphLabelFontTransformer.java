package invis.gl.Transformers;

import invis.gl.networkapi.NetworkElementApi;
import java.awt.Font;
import java.util.HashMap;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author Matt
 */
public class NetworkElementGraphLabelFontTransformer<Element> implements Transformer<Element, Font>
{

    private HashMap<Element, NetworkElementApi> mNetworkElementDataTable;
    private Font LabelFont;
    private int mFontSize;
    private int lowerFrequencyFilterValue;
    private int upperFrequencyFilterValue;

    public NetworkElementGraphLabelFontTransformer()
    {
        mFontSize = 20;
        LabelFont = new Font("Arial", Font.PLAIN, mFontSize);
    }

    public NetworkElementGraphLabelFontTransformer(HashMap<Element, NetworkElementApi> dataTable)
    {
        mFontSize = 20;
        LabelFont = new Font("Arial", Font.PLAIN, mFontSize);
        mNetworkElementDataTable = dataTable;
    }

    public void SetFontSize(int value)
    {
        mFontSize = value;
        LabelFont = new Font("Arial", Font.PLAIN, mFontSize);
    }

    public void setFrequencyThreshold(int lowerValue, int upperValue)
    {
        lowerFrequencyFilterValue = lowerValue;
        upperFrequencyFilterValue = upperValue;
    }

    @Override
    public Font transform(Element VE)
    {
        if (mNetworkElementDataTable != null)
        {
            if (mNetworkElementDataTable.containsKey(VE))
            {
                //If we are in range, print the label.
                if (mNetworkElementDataTable.get(VE).getUniqueFrequency() > this.lowerFrequencyFilterValue
                        || mNetworkElementDataTable.get(VE).getUniqueFrequency() < this.upperFrequencyFilterValue)
                {
                    return (LabelFont);
                }
                //If we're out of range print null.
            }
        }
        else
        {
            //If the NetworkElementDataTable is null, it means the 
            //clusterTable for the stepbased graph is being used.
            //currently we don't do anything special for that graph.
            //TODO: Do something special for that graph, like write a GraphLabel Transformer
            //specifically for it.
            return (LabelFont);
        }
        //If it doesn't exist print null.
        return null;
    }
}
