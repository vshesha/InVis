package invis.gl.Transformers;

import invis.gl.networkapi.NetworkCaseApi;
import invis.gl.networkapi.NetworkCaseSetApi;
import invis.gl.networkapi.NetworkElementApi;
import java.awt.Color;
import java.awt.Paint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author Matt
 */
public class vertexPainter<V> implements Transformer<V, Paint>
{

    private HashMap<V, NetworkElementApi> mNodeDataTable;
    private int mClusterCount;
    private NetworkCaseSetApi mNCSA;
    private LinkedList<HSLColor> mColorList = new LinkedList<HSLColor>();
    private HashMap<String, Color> mClusterColorMap = new HashMap<String, Color>();
    private int lowerFrequencyFilterValue;
    private int upperFrequencyFilterValue;
    private int lowerMDPFilterValue;
    private int upperMDPFilterValue;
    private boolean mMultiGroupColorMode;
    private boolean mTwoGroupColorMode;
    private String mGroupOne;
    private String mGroupTwo;

    public vertexPainter(HashMap<V, NetworkElementApi> nodeDataTable, NetworkCaseSetApi NCSA)
    {
        mNodeDataTable = nodeDataTable;
        mNCSA = NCSA;
        lowerFrequencyFilterValue = 0;
        upperFrequencyFilterValue = Integer.MAX_VALUE;
        lowerMDPFilterValue = 0;
        upperMDPFilterValue = 0;
        mGroupOne = null;
        mGroupTwo = null;
    }

    public void setFrequencyThreshold(int lowerValue, int upperValue)
    {
        lowerFrequencyFilterValue = lowerValue;
        upperFrequencyFilterValue = upperValue;
    }

    public void setMDPThreshold(int lowerValue, int upperValue)
    {
        lowerMDPFilterValue = lowerValue;
        upperMDPFilterValue = upperValue;
    }

    public void setClusterColorMap(HashMap<String, Color> clusterColorMap)
    {
        mClusterColorMap = clusterColorMap;
    }

    public void setMultiGroupColorMode(boolean value)
    {
        mMultiGroupColorMode = value;
        if (mMultiGroupColorMode)
        {
            this.setTwoGroupColorMode(false);
        }
    }

    public void setTwoGroupColorMode(boolean value)
    {
        mTwoGroupColorMode = value;
        if (mTwoGroupColorMode)
        {
            this.setMultiGroupColorMode(false);
        }
    }

    public void setClusterCount(int cc)
    {
        mClusterCount = cc;
        if (mClusterCount > 0)
        {
            for (int i = 0; i < 360; i += 360 / mClusterCount)
            {

                float Saturation = (float) Math.random();

                if (Saturation < 0.25)
                {
                    Saturation += 0.25;
                }
                if (Saturation > 0.75)
                {
                    Saturation -= 0.25;
                }
                Saturation *= 100;

                float Luminance = (float) Math.random();

                if (Luminance < 0.25)
                {
                    Luminance += 0.25;
                }
                if (Luminance > 0.75)
                {
                    Luminance -= 0.25;
                }
                Luminance *= 100;
                mColorList.add(new HSLColor(i, Saturation, Luminance));
            }
        }
    }

    /**
     * Check each vertex if it is an error, goal, selected, or filtered. We then
     * color the vertex accordingly.
     *
     * @param vertex the vertex to be colored.
     * @return the Paint for the vertex.
     */
    @Override
    public Paint transform(V vertex) //So for each node that we draw...
    {

        HSLColor color;
        int Saturation = 70;
        int Value = 50;

        //Default vertex Color:
        color = new HSLColor(240, Saturation, Value);
        Color tempColor = (CheckSelected(vertex, color));

        //Vertex Color for Errors.
        if (mNodeDataTable.containsKey(vertex))
        {
            if (mNodeDataTable.get(vertex).getErrorValue())
            {
                color = new HSLColor(0, Saturation, Value);
                tempColor = (CheckSelected(vertex, color));
            }
        }

        //Vertex Color for Goals.
        if (mNodeDataTable.containsKey(vertex))
        {
            if (mNodeDataTable.get(vertex).getGoalValue())
            {
                color = new HSLColor(120, Saturation, Value);
                tempColor = (CheckSelected(vertex, color));
            }
        }
        //Vertex colors when in clusters.
        for (int i = 0; i < mClusterCount; i++)
        {
            if (mNodeDataTable.containsKey(vertex))
            {
                if (mNodeDataTable.get(vertex).getClusterID() == i)
                {
                    return (mColorList.get(i).getRGB());
                }
            }
        }

        if (mTwoGroupColorMode)
        {
            if (!mClusterColorMap.isEmpty())
            {
                ArrayList<String> currentVertexCaseIdList = mNodeDataTable.get(vertex).getCaseIdList();
                HashMap<String, Integer> currentVertexCaseGroupIdFrequencyMap = new HashMap<String, Integer>();

                //Let's find the Group with the highest frequency on the current vertex.
                for (int i = 0; i < currentVertexCaseIdList.size(); i++)
                {
                    NetworkCaseApi x = mNCSA.findCaseByCaseId(currentVertexCaseIdList.get(i));
                    if (x != null)
                    {
                        String key = x.getCaseGroupID();
                        if (currentVertexCaseGroupIdFrequencyMap.containsKey(key))
                        {
                            int elementCount = Integer.parseInt(currentVertexCaseGroupIdFrequencyMap.get(key).toString());
                            elementCount++;
                            currentVertexCaseGroupIdFrequencyMap.put(key, elementCount);

                        } else
                        {
                            currentVertexCaseGroupIdFrequencyMap.put(key, 1);
                        }
                    }
                }

                Set<String> KeySet = currentVertexCaseGroupIdFrequencyMap.keySet();

                float ratio;

                if (KeySet.size() == 1)
                {
                    if (KeySet.toArray()[0].toString().equals(mGroupOne))
                    {
                        ratio = 1.0f;
                    } else//(grp2)
                    {
                        ratio = 0.0f;
                    }
                    //x should always be the same in this mode.
                    HSLColor x = new HSLColor(mClusterColorMap.get(mGroupOne));
                    HSLColor y = new HSLColor(180, ratio * 100, 50);
                    tempColor = (CheckSelected(vertex, y));
                } else
                {
                    //TODO: If there is only 1 GroupID on the vertex then the array Set is of size 1.
                    String FirstGroupKey = KeySet.toArray()[0].toString();
                    Integer GroupOneFrequency = currentVertexCaseGroupIdFrequencyMap.get(FirstGroupKey);
                    String SecondGroupKey = KeySet.toArray()[1].toString();
                    Integer GroupTwoFrequency = currentVertexCaseGroupIdFrequencyMap.get(SecondGroupKey);

                    ratio = (GroupOneFrequency.floatValue() / (GroupOneFrequency.floatValue() + GroupTwoFrequency.floatValue()));
                    HSLColor x = new HSLColor(mClusterColorMap.get(FirstGroupKey));
                    HSLColor y = new HSLColor(180, ratio * 100, 50);
                    tempColor = (CheckSelected(vertex, y));
                }
            }
        }

        //Used for color based on clusters.
        if (mMultiGroupColorMode)
        {
            if (!mClusterColorMap.isEmpty())
            {
                ArrayList<String> currentVertexCaseIdList = mNodeDataTable.get(vertex).getCaseIdList();

                HashMap<String, Integer> currentVertexCaseGroupIdFrequencyMap = new HashMap<String, Integer>();

                //Let's find the Group with the highest frequency on the current vertex.
                String mostFrequentCaseGroupId = "";
                for (int i = 0; i < currentVertexCaseIdList.size(); i++)
                {
                    int maxScore = 0;
                    NetworkCaseApi x = mNCSA.findCaseByCaseId(currentVertexCaseIdList.get(i));
                    if (x != null)
                    {
                        String key = x.getCaseGroupID();
                        if (currentVertexCaseGroupIdFrequencyMap.containsKey(key))
                        {
                            int elementCount = Integer.parseInt(currentVertexCaseGroupIdFrequencyMap.get(key).toString());
                            elementCount++;
                            currentVertexCaseGroupIdFrequencyMap.put(key, elementCount);

                            //If the current element is the most frequent, we update the most frequent CaseGroupId and maxScore.
                            if (elementCount > maxScore)
                            {
                                maxScore = elementCount;
                                mostFrequentCaseGroupId = key;
                            }
                        } else
                        {
                            currentVertexCaseGroupIdFrequencyMap.put(key, 1);
                            //For the first entry, we update the maxScore and mostFrequentCaseGroupId
                            if (1 > maxScore)
                            {
                                maxScore = 1;
                                mostFrequentCaseGroupId = key;
                            }
                        }
                    }
                }
                //the ClusterColorMap takes a CaseGroupId as the key and the value the color associated with that CaseGroupId.
                HSLColor x = new HSLColor(mClusterColorMap.get(mostFrequentCaseGroupId));
                tempColor = (CheckSelected(vertex, x));

                //TODO: This variable: currentVertexCaseGroupIdFrequencyMap
                // is a really good one, it has the frequency of all the CaseGroupIds for each vertex.
                // ideally, this should be a separate object and placed on the NetworkNodeModel.

                //TODO: In addition, doing all this calculation on the vertexPainter, is probably a HORRIBLE idea.
            }
        }

        float alpha = 1.0f;
        if (mNodeDataTable.containsKey(vertex))
        {
            if (mNodeDataTable.get(vertex).getUniqueFrequency() < this.lowerFrequencyFilterValue
                    || mNodeDataTable.get(vertex).getUniqueFrequency() > this.upperFrequencyFilterValue
                    || mNodeDataTable.get(vertex).getMDPValue() < this.lowerMDPFilterValue
                    || mNodeDataTable.get(vertex).getMDPValue() > this.upperMDPFilterValue)
            {
                alpha = 0.1f;
            }
        }


        float red = (float) (tempColor.getRed()) / 255;
        float green = (float) (tempColor.getGreen()) / 255;
        float blue = (float) (tempColor.getBlue()) / 255;

        Color vertexColor = new Color(red, green, blue, alpha);

        return (vertexColor);

    }
//TODO: Temporary, Remove in future.
    public void setCaseGroupIDs(String GroupOne, String GroupTwo)
    {
        mGroupOne = GroupOne;
        mGroupTwo = GroupTwo;
    }

    /**
     * Adjust the color for selected vertices.
     *
     * @param vertex the vertex to check for selection.
     * @param color the current color of the vertex.
     * @return the adjusted vertex color, based on Tone.
     */
    private Color CheckSelected(V vertex, HSLColor color)
    {
        Color newColor;
        if (mNodeDataTable.containsKey(vertex))
        {
            if (mNodeDataTable.get(vertex).getSelected())
            {
                newColor = color.adjustTone(70.0f);
            } else
            {
                newColor = color.getRGB();
            }
        } else
        {
            newColor = color.getRGB();
        }
        return (newColor);
    }
}
