package invis.gl.sequenceapi;

import java.util.ArrayList;

/**
 *
 * @author GameLab
 */
public interface NetworkInteractionSequenceSetApi
{

    public int getSequenceSetSize();

    public ArrayList<NetworkInteractionSequenceApi> getNetworkSequenceSet();

    public NetworkInteractionSequenceSetApi trimTo(int max);

    public String getSequenceSetName();
}
