
package invis.gl.sequencedistributions;

/**
 *
 * @author Matt
 */
public class ObjectCounter {

    private String mValue;
    private int mFrequency;
    
    public void IncreaseFrequency()
    {
        this.mFrequency++;
    }

    public int getFrequency() {
        return mFrequency;
    }

    public void setFrequency(int frequency) {
        this.mFrequency = frequency;
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        this.mValue = value;
    }
}
