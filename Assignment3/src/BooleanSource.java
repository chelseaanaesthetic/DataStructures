/**
 * A BooleanSource provides a random sequence of boolean values.
 *
 * @author Michael Main (main@colorado.edu)
 * @version Feb 10, 2016
 * http://www.cs.colorado.edu/~main/edu/colorado/simulations/BooleanSource.java
 *
 */
public class BooleanSource {
    private double probability; // The approximate probability of query( ) returning true.

    /**
     * Initialize a BooleanSource.
     * @param p - a probability
     *
     * Precondition: 0 < p and p < 1
     * Postcondition: This BooleanSource has been initialized so that p is
     *          the approximate probability of returning true in any subsequent
     *          activation of the query method.
     *
     * @exception IllegalArgumentException
     *          Indicates that p is outside of its legal range.
     **/
    public BooleanSource(double p)
    {
        if ((p < 0) || (1 < p))
            throw new IllegalArgumentException("Illegal p: " + p);
        probability = p;
    }

    /**
     * Get the next value from this BooleanSource.
     * @return either true or false, with the probability of a true value
     * being determined by the argument that was given to the constructor.
     **/
    public boolean query( )
    {
        return (Math.random( ) < probability);
    }
}
