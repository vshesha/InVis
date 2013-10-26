
package invis.gl.sequenceapi;

import java.util.ArrayList;

/**
 *
 * @author Matt
 */
public interface StepbasedSequenceSetApi
{
    public int getSequenceSetSize();
    
    public ArrayList<StepbasedSequenceApi> getStepbasedSequenceSet();
    
    public String getName();
}
