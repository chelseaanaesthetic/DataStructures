/**
 * An Averager computes an average of a group of numbers.
 *
 * @author Michael Main (main@colorado.edu)
 * @version Feb 10, 2016
 * http://www.cs.colorado.edu/~main/edu/colorado/simulations/Averager.java
 *
 */
public class Averager
{
    private int count;  // How many numbers have been given to this averager
    private double sum; // Sum of all the numbers given to this averager

    /**
     * Initialize an Averager.
     * param - none
     *
     * Postcondition: This Averager has been initialized and is ready to
     *          accept numbers.
     **/
    public Averager( )
    {
        count = 0;
        sum = 0;
    }


    /**
     * Give another number to this Averager.
     * @param value - the next number to give to this Averager
     *
     * Precondition: howManyNumbers() < Integer.MAX_VALUE.
     * Postcondition: This Averager has accepted value as the next number.
     *
     * @exception IllegalStateException
     *   Indicates that howManyNumbers() is Integer.MAX_VALUE.
     **/
    public void addNumber(double value)
    {
        if (count == Integer.MAX_VALUE)
            throw new IllegalStateException("Too many numbers");
        count++;
        sum += value;
    }


    /**
     * Provide an average of all numbers given to this Averager.
     * @return the average of all the numbers that have been given to this Averager
     *   the next number to give to this Averager
     *
     * Postcondition:
     *   If howManyNumbers() is zero, then the answer is Double.NaN ("not a number").
     *   The answer may also be Double.POSITIVE_INFINITY or Double.NEGATIVE_INFINITY
     *   if there has been an arithmetic overflow during the summing of all the numbers.
     **/
    public double average( )
    {
        if (count == 0)
            return Double.NaN;
        else
            return sum/count;
    }


    /**
     * Provide a count of how many numbers have been given to this Averager.
     * @return the count of how many numbers have been given to this Averager
     **/
    public int howManyNumbers( )
    {
        return count;
    }
}