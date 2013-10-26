package invis.gl.graphvisualapi;

/**
 *
 * @author Matt
 */
public interface NetworkDisplayApi
{
    public enum DisplayType
    {
        NETWORK, STEPBASED, MIXED;
    }

    public enum EdgeWeightType
    {
        CASEFREQUENCY, UNIQUECASEFREQUENCY, NETWORKELEMENTS;
    }
}
