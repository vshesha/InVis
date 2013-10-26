package invis.gl.MDP;

import edu.uci.ics.jung.algorithms.scoring.AbstractIterativeScorer;
import edu.uci.ics.jung.algorithms.scoring.util.VEPair;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Hypergraph;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author Maiku
 * @param <V>
 * @param <E>
 */
public class MDPScorer<V, E> extends AbstractIterativeScorer<V, E, Double>
{

    Transformer<E, String> edgeToAction;
    Transformer<E, Double> edgeReward;
    Double discount = 1.0;
    HashMap<V, Double> vertexErrors;
    Double mTolerance = .9;

    /**
     *
     * @param graph - The graph in which to calculate the MDP values.
     * @param edge_weights - The weights on the edges for how the MDP-process
     * decides where to move to next at each iteration.
     * @param edgeToAction - An identifying value for the edges, edges with the
     * same identifier are averaged in the MDP process.
     * @param edgeReward - The reward function, these are the edges and their
     * associated rewards.
     */
    public MDPScorer(Hypergraph<V, E> graph, Transformer<E, ? extends Number> edge_weights,
            Transformer<E, String> edgeToAction, Transformer<E, Double> edgeReward)
    //Transformer<V, Double> vertexValueFunction)
    {
        super((Graph<V,E>) graph, edge_weights);
        super.initialize();
        
        this.setTolerance(mTolerance);
        this.edgeToAction = edgeToAction;
        this.edgeReward = edgeReward;



        for (V v : graph.getVertices())
        {
            //setOutputValue(v, vertexValueFunction.transform(v));
            setOutputValue(v, 0.0);
        }

    }

    @Override
    public Double getOutputValue(V v)
    {
        return super.getOutputValue(v);
    }

    @Override
    protected double update(V v)
    {
        /*
         * if(getCurrentValue(v) == 100) { setOutputValue(v,100.0); return 0;
         }
         */
        Double maxValue = Double.MIN_VALUE;


        for (String action : getActionList(graph.getOutEdges(v)))
        {
            Double actionFreq = 0.0;
            for (E edge : graph.getOutEdges(v))
            {
                if (edgeToAction.transform(edge).equals(action))
                {
                    actionFreq += (edge_weights.transform(new VEPair<V, E>(v, edge))).intValue();
                }
            }

            Double actionValue = 0.0;
            for (V sPrime : graph.getSuccessors(v))
            {
                Double subActionFreq = 0.0;
                for (E edge : graph.findEdgeSet(v, sPrime))
                {
                    if (edgeToAction.transform(edge).equals(action))
                    {
                        subActionFreq += (Double) (edge_weights.transform(new VEPair<V, E>(v, edge)));
                    }
                }

                Double actionProb;
                if (actionFreq == 0)
                {
                    actionProb = 0.0;
                } else
                {
                    actionProb = subActionFreq / actionFreq;
                }

                if (graph.findEdge(v, sPrime) != null)
                {
                    actionValue += actionProb * (edgeReward.transform(graph.findEdge(v, sPrime)) + (discount * this.getCurrentValue(sPrime)));
                }
            }


            if (actionValue > maxValue)
            {
                maxValue = actionValue;
            }
        }
        setOutputValue(v, maxValue);
        //System.out.println(this.max_delta);
        return Math.abs(getCurrentValue(v) - maxValue);
    }

    ArrayList<String> getActionList(Collection<E> edges)
    {
        ArrayList<String> actionList = new ArrayList<String>();
        for (E edge : edges)
        {
            if (!actionList.contains(edgeToAction.transform(edge)))
            {
                actionList.add(edgeToAction.transform(edge));
            }
        }
        return actionList;
    }

    @Override
    public void step()
    {
        this.max_delta = Double.MIN_VALUE;
        super.step();
    }
}