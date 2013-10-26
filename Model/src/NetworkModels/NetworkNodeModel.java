package NetworkModels;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Matt
 */
@ServiceProvider(service = NetworkNodeModel.class)
public class NetworkNodeModel extends NetworkElementModel
{

    private ArrayList<Integer> mInteractionList; //The indices of this list correlate to the indices of the CaseIdList.

    public NetworkNodeModel()
    {
        super(null,null,null,null);
    }

    public NetworkNodeModel(String stateDesc, Integer interactionIndex,
            NetworkElementType networkElementType, String caseName,
            int InteractionCount, ArrayList<String> optionalData)
    {
        super(interactionIndex, networkElementType, caseName, optionalData);

        mIdentifier = stateDesc;

        mInteractionList = new ArrayList<Integer>();
        mInteractionList.add(InteractionCount);
    }

    @Override
    public void setGoalPathValue(boolean value)
    {
        mGoalPathValue = (value);
    }

    @Override
    public void setGoalCaseCount(int goalCaseCount)
    {
        mGoalCaseCount = goalCaseCount;
    }

    public ArrayList<Integer> getInteractionList()
    {
        return (mInteractionList);
    }

    public Integer getInteractionListSize()
    {
        return (mInteractionList.size());
    }
}

class ValueComparator implements Comparator<String>
{

    Map<String, Integer> base;

    public ValueComparator(Map<String, Integer> base)
    {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with equals.    
    @Override
    public int compare(String a, String b)
    {
        if (base.get(a) < base.get(b))
        {
            return 1;
        } else if (base.get(a) == base.get(b))
        {
            return 1;
        } // returning 0 would merge keys
        else
        {
            return -1;
        }
    }
}
