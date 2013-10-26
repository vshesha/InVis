
package invis.gl.sequenceapi;

import invis.gl.NetworkClusterApi.NetworkClusterVertexApi;
import java.util.ArrayList;

/**
 *
 * @author Matt
 */
public interface StepbasedSequenceApi
{

    public int getSequenceSetSize();

    public ArrayList<NetworkClusterVertexApi> getStepbasedSequenceSet();

    public String getName();
    
}
