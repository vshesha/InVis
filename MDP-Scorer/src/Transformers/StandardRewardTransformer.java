package Transformers;

import invis.gl.networkapi.NetworkElementApi;
import java.util.HashMap;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author Matt
 */
public class StandardRewardTransformer implements Transformer<String, Double>, EdgeRewardInterface
{

    HashMap<String, NetworkElementApi> mEdgeTable;
    
    private Double mMinScore;
    private Double mMaxScore;
    
    private Double mCost;
    
    int count = 0;

    public StandardRewardTransformer(HashMap<String, NetworkElementApi> edgeTable)
    {
        mEdgeTable = edgeTable;
        mMinScore = 0.0;
        mMaxScore = 100.0;
        mCost = -1.0;
    }

    @Override
    public Double getMaxScore()
    {
        return(mMaxScore);
    }

    @Override
    public Double getMinScore()
    {
        return(mMinScore);
    }

    @Override
    public Double transform(String i)
    {
        if (mEdgeTable.get(i).getErrorValue() == true)
        {
            count++;
            System.out.println(count);
            return (mMinScore);
        }
        if (mEdgeTable.get(i).getGoalValue() == true)
        {
            return (mMaxScore);
        }
        return (mCost);
    }
}
