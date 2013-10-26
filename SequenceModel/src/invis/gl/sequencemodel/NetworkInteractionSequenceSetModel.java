package invis.gl.sequencemodel;

import invis.gl.sequenceapi.NetworkInteractionSequenceApi;
import invis.gl.sequenceapi.NetworkInteractionSequenceSetApi;
import java.util.ArrayList;

/**
 *
 * @author GameLab
 */
public class NetworkInteractionSequenceSetModel implements NetworkInteractionSequenceSetApi
{

    private ArrayList<NetworkInteractionSequenceApi> mSequenceSet;
    private String mName;

    public NetworkInteractionSequenceSetModel(ArrayList<NetworkInteractionSequenceApi> sequenceList)
    {
        this("Un-named", sequenceList);
    }

    public NetworkInteractionSequenceSetModel(String initName, ArrayList<NetworkInteractionSequenceApi> sequenceList)
    {
        mName = initName;
        mSequenceSet = sequenceList;
    }

    @Override
    public String getSequenceSetName()
    {
        return mName;
    }

    @Override
    public int getSequenceSetSize()
    {
        return mSequenceSet.size();
    }

    @Override
    public ArrayList<NetworkInteractionSequenceApi> getNetworkSequenceSet()
    {
        return mSequenceSet;
    }

    @Override
    public NetworkInteractionSequenceSetModel trimTo(int max)
    {
        if (max > mSequenceSet.size())
        {
            max = mSequenceSet.size();
        }
        return (new NetworkInteractionSequenceSetModel(
                new ArrayList<NetworkInteractionSequenceApi>(
                mSequenceSet.subList(0, max))));
    }
}
