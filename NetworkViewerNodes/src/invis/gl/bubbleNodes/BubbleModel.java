package invis.gl.bubbleNodes;

import invis.gl.networkapi.BubbleApi;
import invis.gl.networkapi.NetworkElementApi;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Matt
 */
public class BubbleModel implements BubbleApi
{

    private NetworkElementApi mSource;
    private NetworkElementApi mTarget;
    List<List<NetworkElementApi>> mPaths;

    public BubbleModel()
    {
        mSource = null;
        mTarget = null;
        mPaths = new ArrayList<List<NetworkElementApi>>();
    }

    public BubbleModel(NetworkElementApi source, NetworkElementApi target, List<List<NetworkElementApi>> paths)
    {
        mSource = source;
        mTarget = target;
        mPaths = paths;
    }

    @Override
    public boolean PathsEqualLength()
    {
        boolean EqualLength = true;
        int length = mPaths.get(0).size();
        for (int i = 0; i < mPaths.size(); i++)
        {
            if (mPaths.get(i).size() != length)
            {
                EqualLength = false;
            }
        }
        return (EqualLength);
    }

    public boolean EdgeContainedInPaths(String incEdge)
    {
        boolean contained = false;
        for (int i = 0; i < this.getPaths().size(); i++)
        {
            for (int j = 0; j < this.getPaths().get(i).size(); j++)
            {
                String Edge = this.getPaths().get(i).get(j).getValue();
                if (Edge.compareTo(incEdge) == 0)
                {
                    contained = true;
                }
            }
        }
        return (contained);
    }

    @Override
    public void setSource(NetworkElementApi source)
    {
        mSource = source;
    }

    @Override
    public void setTarget(NetworkElementApi target)
    {
        mTarget = target;
    }

    @Override
    public void setPaths(List<List<NetworkElementApi>> paths)
    {
        mPaths = paths;
    }

    @Override
    public NetworkElementApi getSource()
    {
        return (mSource);
    }

    @Override
    public NetworkElementApi getTarget()
    {
        return (mTarget);
    }

    @Override
    public List<List<NetworkElementApi>> getPaths()
    {
        return (mPaths);
    }

    @Override
    public void addPath(List<NetworkElementApi> path)
    {
        mPaths.add(path);
    }
}
