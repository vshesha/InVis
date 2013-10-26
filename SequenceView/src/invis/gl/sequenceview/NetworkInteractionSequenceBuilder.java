package invis.gl.sequenceview;

import invis.gl.NetworkClusterApi.NetworkClusterVertexApi;
import invis.gl.dataprocessor.DataParser;
import invis.gl.networkapi.NetworkCaseApi;
import invis.gl.networkapi.NetworkInteractionApi;
import invis.gl.sequenceapi.NetworkInteractionSequenceApi;
import invis.gl.sequenceapi.NetworkInteractionSequenceApi.SequenceType;
import invis.gl.sequencemodel.NetworkInteractionSequenceModel;
import invis.gl.sequencemodel.NetworkInteractionSequenceSetModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import org.apache.commons.collections15.bag.HashBag;

/**
 *
 * @author Matt
 */
public class NetworkInteractionSequenceBuilder
{

    static private DataParser mDataParser;
    static private NetworkInteractionSequenceSetModel interactionSequence;
    static private NetworkInteractionSequenceSetModel stepbasedSequence;
    static private SequenceType mSequenceType;

    /*
     public NetworkInteractionSequenceBuilder(DataParser data)
     {
     mDataParser = data;
     }*/
    static public NetworkInteractionSequenceSetModel getNetworkInteractionSequenceSetModel(DataParser data, SequenceType seqType)
    {
        mDataParser = data;
        mSequenceType = seqType;
        interactionSequence = CalculateNodeSequenceSet();
        stepbasedSequence = CalculateStepbasedSequenceSet();
        if (seqType == SequenceType.STATE)
        {
            return (interactionSequence);
        } else
        {
            return (stepbasedSequence);
        }

    }

    /**
     * This method generates the NodeSequenceSet, to be displayed.
     *
     * @return the final NetworkSequenceSet
     */
    static private NetworkInteractionSequenceSetModel CalculateNodeSequenceSet()
    {
        //search through the data and pull all permutations of sequences and add it to a HashBag
        if (mDataParser.hasData())
        {
            HashBag<NetworkInteractionSequenceApi> interactionsBag = new HashBag<NetworkInteractionSequenceApi>();

            ArrayList<NetworkCaseApi> caseSet = mDataParser.getNetworkCaseSet().getNetworkCaseList();
            //First we iterate through each case.
            for (int caseIndex = 0; caseIndex < caseSet.size(); caseIndex++)
            {
                ArrayList<NetworkInteractionApi> interactionsList = caseSet.get(caseIndex).getInteractionsList();
                //We next iterate through that cases interactions.
                for (int interactionIndex = 0; interactionIndex < interactionsList.size(); interactionIndex++)
                {
                    boolean keepChain = true;
                    //Here we iterate through the cases interactions, using an offset, so we can identify sequences
                    //that start on the case's second, third or N-action.
                    for (int interactionOffset = 0; interactionIndex + interactionOffset < interactionsList.size(); interactionOffset++)
                    {
                        ArrayList<NetworkInteractionApi> curChain = new ArrayList<NetworkInteractionApi>();

                        //Lastly, we iterate through the various lengths of sequences that can be present.
                        for (int sequenceLength = 0; sequenceLength < interactionIndex + 1; sequenceLength++)
                        {
                            //We ignroe actions with frequency less of 1 (and below, obviously).
                            if (interactionsList.get(interactionOffset + sequenceLength).getAction().getUniqueFrequency() < 2)
                            {
                                keepChain = false;              //if any of the sequences in this chain contain a interaction
                                break;                          //with less than 2 users, discard the whole chain.
                            }
                            curChain.add(interactionsList.get(interactionOffset + sequenceLength));
                        }
                        //If the sequence has more than one interaction, and the frequency meets our requirements we keep the sequence.
                        if (curChain.size() > 1 && keepChain)
                        {
                            //The sequence is kept.
                            interactionsBag.add(new NetworkInteractionSequenceModel(curChain, mSequenceType));
                        }
                        //We reset our flag for requiring sequences to have greater than 1 frequency.
                        keepChain = true;
                    }
                }
            }
            //Set<NetworkSequenceApi> uniqueSet = this.CalculateAssignSequenceFrequencies(interactionsBag);
            return (NetworkInteractionSequenceBuilder.GenerateSortedNodeSequences(
                    NetworkInteractionSequenceBuilder.CalculateAssignSequenceFrequencies(
                    interactionsBag), false));
        }
        //If there is no data, we return null.
        return null;
    }

    static private NetworkInteractionSequenceSetModel CalculateStepbasedSequenceSet()
    {
        //search through the data and pull all permutations of sequences and add it to a HashBag
        if (mDataParser.hasData())
        {
            HashBag<NetworkInteractionSequenceApi> stepbasedBag = new HashBag<NetworkInteractionSequenceApi>();

            ArrayList<NetworkCaseApi> caseSet = mDataParser.getNetworkCaseSet().getNetworkCaseList();
            //First we iterate through each case.
            for (int caseIndex = 0; caseIndex < caseSet.size(); caseIndex++)
            {
                ArrayList<NetworkInteractionApi> interactionsList = caseSet.get(caseIndex).getInteractionsList();
                //We next iterate through that cases interactions.
                for (int interactionIndex = 0; interactionIndex < interactionsList.size(); interactionIndex++)
                {
                    boolean keepChain = true;
                    //Here we iterate through the cases interactions, using an offset, so we can identify sequences
                    //that start on the case's second, third or N-action.
                    for (int interactionOffset = 0; interactionIndex + interactionOffset < interactionsList.size(); interactionOffset++)
                    {
                        ArrayList<NetworkInteractionApi> curChain = new ArrayList<NetworkInteractionApi>();

                        //Lastly, we iterate through the various lengths of sequences that can be present.
                        for (int sequenceLength = 0; sequenceLength < interactionIndex + 1; sequenceLength++)
                        {
                            //We ignroe actions with frequency less of 1 (and below, obviously).
                            if (interactionsList.get(interactionOffset + sequenceLength).getAction().getUniqueFrequency() < 2)
                            {
                                keepChain = false;              //if any of the sequences in this chain contain a interaction
                                break;                          //with less than 2 users, discard the whole chain.
                            }
                            curChain.add(interactionsList.get(interactionOffset + sequenceLength));
                        }
                        //If the sequence has more than one interaction, and the frequency meets our requirements we keep the sequence.
                        if (curChain.size() > 2 && keepChain)
                        {
                            ArrayList<NetworkClusterVertexApi> stepbasedList = NetworkInteractionSequenceBuilder.buildStepbasedList(curChain);
                            stepbasedBag.add(new NetworkInteractionSequenceModel(curChain, stepbasedList, mSequenceType));
                        }
                        //We reset our flag for requiring sequences to have greater than 1 frequency.
                        keepChain = true;
                    }
                }
            }
            //Set<NetworkSequenceApi> uniqueSet = this.CalculateAssignSequenceFrequencies(interactionsBag);
            return (NetworkInteractionSequenceBuilder.GenerateSortedNodeSequences(
                    NetworkInteractionSequenceBuilder.CalculateAssignSequenceFrequencies(
                    stepbasedBag),
                    true));
        }
        //If there is no data, we return null.
        return null;
    }

    static private ArrayList<NetworkClusterVertexApi> buildStepbasedList(ArrayList<NetworkInteractionApi> input)
    {
        ArrayList<NetworkClusterVertexApi> outputList = new ArrayList<NetworkClusterVertexApi>();

        for (int i = 0; i < input.size(); i++)
        {
            String key = ((NetworkInteractionApi) (input.toArray()[i])).getActionPostCondition();
            outputList.add(mDataParser.getDerivedData().getNodeTable().get(key));
        }

        return (outputList);
    }

    /**
     * We generate the sorted set of sequences, in the form of of a
     * NetworkInteractionSequenceSetModel.
     *
     * @param uniqueSet This is the uniqueSet of NetworkInteractionSequenceApi
     * objects.
     * @return a sorted set of sequences in the form of a
     * NetworkInteractionSequenceSetModel.
     */
    static private NetworkInteractionSequenceSetModel GenerateSortedNodeSequences(Set<NetworkInteractionSequenceApi> uniqueSet, boolean stepbased)
    {
        NetworkInteractionSequenceSetModel SequenceSet = new NetworkInteractionSequenceSetModel("Unsorted Set", new ArrayList<NetworkInteractionSequenceApi>(uniqueSet));

        ArrayList<NetworkInteractionSequenceApi> sortedSet = new ArrayList<NetworkInteractionSequenceApi>(uniqueSet);

        /*
         * Here we calculate the ratings for each sequence based on it's
         * characteristics.
         */
        if (!stepbased)
        {
            NetworkInteractionSequenceBuilder.CalculateNodeSequenceRatings(SequenceSet);
        } else
        {
            NetworkInteractionSequenceBuilder.CalculateStepbasedSequenceRatings(SequenceSet);
        }

        /*
         * We create a comparator and sort based on the rating of the sequences
         */

        Collections.sort(sortedSet, new Comparator<NetworkInteractionSequenceApi>()
        {
            @Override
            public int compare(NetworkInteractionSequenceApi t, NetworkInteractionSequenceApi t1)
            {
                return t1.getRating() - t.getRating();

            }
        });

//<editor-fold defaultstate="collapsed" desc="comment">
        /*
         Here we limit the size of the sequence set to some pre-defined max.
             
         int max = 150;
         if (sortedSet.size() < max)
         {
         max = sortedSet.size();
         }
            
            
            
            
         // This is used to remove subsequences of higher rated larger
         // sequences.
             
         for (int caseIndex = 0; caseIndex < max; caseIndex++)
         {
         NetworkInteractionSequenceApi curSequence = sortedSet.get(caseIndex);
         for (NetworkInteractionApi nia : curSequence.getInteractionsList())
         {
         for (int interactionOffset = max - 1; interactionOffset > caseIndex; interactionOffset--)
         {
         if (sortedSet.get(interactionOffset).contains(nia))
         {
         sortedSet.remove(interactionOffset);
         max--;
         }
         }
         }
         }
         */
        //</editor-fold>
        /*
         * This loop removes all sequences of frequency 1. If the frequency
         * is one, we aren't so interested in the sequence.
         */
        for (int i = 0; i < sortedSet.size(); i++)
        {
            if (sortedSet.get(i).getFrequency() < 2)
            {
                sortedSet.remove(sortedSet.get(i));
            }
        }
        //for(NetworkInteractionSequenceApi nsa : sortedSet) {
        //  System.out.println(nsa + " Rating: " + nsa.getRating());
        //}
        //mSortedSequenceSet = new NetworkInteractionSequenceSetModel("SortedSet", sortedSet);
        return (new NetworkInteractionSequenceSetModel("SortedSet", sortedSet));

    }

    /**
     * This calculates the sequence ratings for the node-sequences. Which are
     * stored in the NetworkInteractionSequenceSetModel.
     *
     * @param sequenceSet this is an Unsorted set of
     * NetworkInteractionSequenceApi objects. That is all the sequences in which
     * you wish to sort.
     */
    static private void CalculateNodeSequenceRatings(NetworkInteractionSequenceSetModel sequenceSet)
    {
        for (NetworkInteractionSequenceApi nsa : sequenceSet.getNetworkSequenceSet())
        {
            int rating = 0;
            int totalSuccessors = 0;
            int successorNodes = 0;
            rating += nsa.getInteractionsList().get(0).getPreState().getUniqueCaseIdSet().size(); //get pre state of first
            ArrayList<String> successors = new ArrayList<String>(mDataParser.getGraph().getSuccessors(nsa.getInteractionsList().get(0).getPreState().getValue()));
            successorNodes += successors.size(); //do all successor calculations on first state as well
            if (successors.size() != 1)
            {
                for (String s : successors)
                {
                    totalSuccessors += mDataParser.getNodeTable().get(s).getUniqueCaseIdSet().size(); //count the successors                               
                }
            }
            for (NetworkInteractionApi interaction : nsa.getInteractionsList()) //then loop on the post state of everyone because of overlap
            {
                rating += interaction.getPostState().getUniqueCaseIdSet().size();
                successors = new ArrayList<String>(mDataParser.getGraph().getSuccessors(interaction.getPostState().getValue()));
                successorNodes += successors.size();
                if (successors.size() != 1)
                {
                    for (String s : successors)
                    {
                        totalSuccessors += mDataParser.getNodeTable().get(s).getUniqueCaseIdSet().size(); //count the successors                               
                    }
                }
                totalSuccessors -= interaction.getPostState().getUniqueFrequency();
            }
            rating = rating - (successorNodes * totalSuccessors);
            nsa.setRating(rating);
        }
    }

    static private void CalculateStepbasedSequenceRatings(NetworkInteractionSequenceSetModel sequenceSet)
    {
        for (NetworkInteractionSequenceApi nsa : sequenceSet.getNetworkSequenceSet())
        {
            int rating = 0;
            int totalSuccessors = 0;
            int successorNodes = 0;
            rating += nsa.getInteractionsList().get(0).getPreState().getUniqueCaseIdSet().size(); //get pre state of first
            ArrayList<String> successors = new ArrayList<String>(mDataParser.getDerivedData().getGraph().getSuccessors(nsa.getInteractionsList().get(0).getActionPostCondition()));
            successorNodes += successors.size(); //do all successor calculations on first state as well
            if (successors.size() != 1)
            {
                for (String s : successors)
                {
                    totalSuccessors += mDataParser.getDerivedData().getNodeTable().get(s).getTotalUniqueFrequency(); //count the successors                               
                }
            }
            for (NetworkInteractionApi interaction : nsa.getInteractionsList()) //then loop on the post state of everyone because of overlap
            {
                rating += mDataParser.getDerivedData().getNodeTable().get(interaction.getActionPostCondition()).getTotalUniqueFrequency();
                successors = new ArrayList<String>(mDataParser.getDerivedData().getGraph().getSuccessors(interaction.getActionPostCondition()));
                successorNodes += successors.size();
                if (successors.size() != 1)
                {
                    for (String s : successors)
                    {
                        totalSuccessors += mDataParser.getDerivedData().getNodeTable().get(s).getTotalUniqueFrequency(); //count the successors                               
                    }
                }
                //totalSuccessors -= interaction.getPostState().getUniqueFrequency();
            }
            rating = rating - (successorNodes * totalSuccessors);
            nsa.setRating(rating);
        }
    }

    /**
     * We generate the set of unique sequences. Then we calculate the frequency
     * of the unique sequences.
     *
     * @param interactionsBag the interactionsBag is used to determine the
     * frequencies of the sequence by looking at all interactions performed
     * across the set of cases.
     */
    static private Set<NetworkInteractionSequenceApi> CalculateAssignSequenceFrequencies(HashBag<NetworkInteractionSequenceApi> interactionsBag)
    {
        Set<NetworkInteractionSequenceApi> uniqueSet = interactionsBag.uniqueSet();
        for (NetworkInteractionSequenceApi nsa : uniqueSet)
        {
            nsa.setFrequency(interactionsBag.getCount(nsa));
        }
        return (uniqueSet);
    }

    /*
     public static void DoAllTheSortWork()
     {
     NetworkSequenceSetNode x = (NetworkSequenceSetNode) mgr.getRootContext();
     ArrayList<NetworkInteractionSequenceApi> networkSequenceSet = x.getData().getNetworkSequenceSet();
     }*/
    public static NetworkInteractionSequenceSetModel SortModelFrequency(ArrayList<NetworkInteractionSequenceApi> uniqueSet, String style)
    {
        ArrayList<NetworkInteractionSequenceApi> sortedSet = new ArrayList<NetworkInteractionSequenceApi>(uniqueSet);
        /*
         * We create a comparator and sort based on the rating of the sequences
         */

        if (style.compareTo("Frequency") == 0)
        {
            Collections.sort(sortedSet, new Comparator<NetworkInteractionSequenceApi>()
            {
                @Override
                public int compare(NetworkInteractionSequenceApi t, NetworkInteractionSequenceApi t1)
                {
                    return (t1.getFrequency() - t.getFrequency());

                }
            });
        }
        if (style.compareTo("Interaction") == 0)
        {
            Collections.sort(sortedSet, new Comparator<NetworkInteractionSequenceApi>()
            {
                @Override
                public int compare(NetworkInteractionSequenceApi t, NetworkInteractionSequenceApi t1)
                {
                    return (t1.getInteractionCount() - t.getInteractionCount());

                }
            });
        }
        if (style.compareTo("Rating") == 0)
        {
            Collections.sort(sortedSet, new Comparator<NetworkInteractionSequenceApi>()
            {
                @Override
                public int compare(NetworkInteractionSequenceApi t, NetworkInteractionSequenceApi t1)
                {
                    return (t1.getRating() - t.getRating());

                }
            });
        }
        return (new NetworkInteractionSequenceSetModel("SortedSet", sortedSet));
    }
}
