
package invis.gl.sequenceview;

import invis.gl.dataprocessor.DataParser;
import invis.gl.networkapi.NetworkCaseApi;
import invis.gl.networkapi.NetworkInteractionApi;
import invis.gl.sequenceapi.StepbasedSequenceApi;
import invis.gl.sequencemodel.StepbasedSequenceSetModel;
import java.util.ArrayList;
import org.apache.commons.collections15.bag.HashBag;

/**
 *
 * @author Matt
 */
@Deprecated
public class StepbasedSequenceBuilder
{
    static private DataParser mDataParser;
    
    static private StepbasedSequenceSetModel stepbasedSequence;
    
    static public StepbasedSequenceSetModel getStepbasedSequenceModel(DataParser data)
    {
        mDataParser = data;
        stepbasedSequence = CalculateStepbasedSequenceSet();
        return (stepbasedSequence);
    }
    
    static private StepbasedSequenceSetModel CalculateStepbasedSequenceSet()
    {
        StepbasedSequenceSetModel SSSModel = new StepbasedSequenceSetModel();
        
        if (mDataParser.HasDerivedData())
        {
            HashBag<StepbasedSequenceApi> interactionsBag = new HashBag<StepbasedSequenceApi>();
            ArrayList<NetworkCaseApi> caseSet = mDataParser.getNetworkCaseSet().getNetworkCaseList();
            
            for (int caseIndex = 0; caseIndex < caseSet.size(); caseIndex++)
            {
                ArrayList<NetworkInteractionApi> interactionsList = caseSet.get(caseIndex).getInteractionsList();
                
                for (int interactionIndex = 0; interactionIndex < interactionsList.size(); interactionIndex++)
                {
                    boolean keepChain = true;
                    for (int interactionOffset = 0; interactionIndex + interactionOffset < interactionsList.size(); interactionOffset++)
                    {
                        ArrayList<NetworkInteractionApi> curChain = new ArrayList<NetworkInteractionApi>();
                        for (int sequenceLength = 0; sequenceLength < interactionIndex+1; sequenceLength++)
                        {
                            
                        }
                    }
                }
            }
        }
        
        return (SSSModel);
    }
}
