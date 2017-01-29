/**
 * A Runway simulates the runway of a simple single-runway airport.
 *
 * @author Chelsea Hanson
 * ICS240 Assignment 3: due 5/5/16
 *
 * Extended from Washer.java by Michael Main:
 * http://www.cs.colorado.edu/~main/edu/colorado/simulations/Washer.java
 *
 */
public class Runway {
    private int secondsToLand; // seconds for a single plane to land
    private int secondsToTakeOff; // seconds for a single plane to take off
    private int planeTimeLeft; // seconds until the runway is no longer busy

    /**
     * Initialize a runway.
     * @param sLand - the number of seconds required for one plane to land
     * @param sTakeoff - the number of seconds required for one plane to take off
     *
     * Postcondition: This runway has been initialized so that it takes each plane
     *          sLand seconds to land, and sTakeOff seconds to take off.
     **/
    public Runway(int sLand, int sTakeoff) {
        secondsToLand = sLand;
        secondsToTakeOff = sTakeoff;
        planeTimeLeft = 0;
    }

    /**
     * Determine whether this runway is currently busy.
     * @return true if this runway is busy (land or takeoff); otherwise false
     **/
    public boolean isBusy() {
        return (planeTimeLeft > 0);
    }

    /**
     * Reduce the remaining time in the current plane time by one second.
     *
     * Postcondition: If a plane is using the runway, then the remaining time
     *          in the current plane time left has been reduced by one second.
     **/
    public void reduceRemainingTime() {
        if (planeTimeLeft > 0)
            planeTimeLeft--;
    }

    /**
     * Start a plane's decent to the runway.
     *
     * Precondition: isBusy() is false.
     * Postcondition: This runway has started simulating a land.
     *          Therefore, isBusy() will return true until the required number
     *          of simulated seconds have passed.
     *
     * @exception IllegalStateException
     *  Indicates that this runway is busy.
     */
    public void startLand() {
        if (planeTimeLeft > 0)
            throw new IllegalStateException("Runway is already busy.");
        planeTimeLeft = secondsToLand;
    }

    /**
     * Start a plane's ascend from the runway.
     *
     * Precondition: isBusy() is false.
     * Postcondition: This runway has started simulating a take off.
     *          Therefore, isBusy() will return true until the required number
     *          of simulated seconds have passed.
     *
     * @exception IllegalStateException
     *  Indicates that this runway is busy.
     */
    public void startTakeOff() {
        if (planeTimeLeft > 0)
            throw new IllegalStateException("Runway is already busy.");
        planeTimeLeft = secondsToTakeOff;
    }
}