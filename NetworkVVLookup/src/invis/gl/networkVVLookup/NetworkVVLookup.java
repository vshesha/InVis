package invis.gl.networkVVLookup;

import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 *
 * @author Matt
 */
public class NetworkVVLookup
{

    private InstanceContent mIC;
    private Lookup mLookup;

    public NetworkVVLookup()
    {
        mIC = new InstanceContent();
        mLookup = new AbstractLookup(mIC);

    }

    public Lookup getNVVLookup()
    {
        return (mLookup);
    }

    public void AddExplorerManager(Object em)
    {
        mIC.add(em);
    }
    public void RemoveExplorerManager(Object em)
    {
        mIC.remove(em);
    }
    public void AddNetworkVV(Object nvv)
    {
        mIC.add(nvv);
    }

    public void RemoveNetworkVV(Object nvv)
    {
        mIC.remove(nvv);
    }
}
