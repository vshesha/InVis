
package invis.gl.networkapi;

import java.util.List;

/**
 *
 * @author Matt
 */
public interface BubbleApi
{
    public void setSource(NetworkElementApi source);
    
    public void setTarget(NetworkElementApi target);
    
    public void setPaths(List<List<NetworkElementApi>> paths);
    
    public boolean PathsEqualLength();
    
    public boolean EdgeContainedInPaths(String incEdge);
    
    public NetworkElementApi getSource();
    
    public NetworkElementApi getTarget();
    
    public List<List<NetworkElementApi>> getPaths();
    
    public void addPath(List<NetworkElementApi> path);
}
