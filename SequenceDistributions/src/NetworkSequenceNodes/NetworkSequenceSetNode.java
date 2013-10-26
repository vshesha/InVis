package NetworkSequenceNodes;

import NetworkApi.NetworkSequenceSetApi;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author GameLab
 */
public class NetworkSequenceSetNode extends AbstractNode {
   private NetworkSequenceSetApi mNSSA;

    public NetworkSequenceSetNode(Children children, NetworkSequenceSetApi data)
    {
        //private ArrayList<CaseModelApi> mCaseList;
        //private String mCaseSetName;
        super(children, Lookups.singleton(data));
        this.setSequenceSetModelData(data);
        if(data.getName() != null) {
            this.setDisplayName(data.getName());
        }
        else {
            this.setDisplayName("Top Sequences");
        }
    }

    private void setSequenceSetModelData(NetworkSequenceSetApi data)
    {
        this.mNSSA = data;
    }

    //public NetworkCaseSetApi getData()
    //{
     //   mNCSA.getUniqueCaseGroupIdCount();
    //    return (mNCSA);
    //}
    
}
