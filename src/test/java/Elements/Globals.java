/*
 * © Copyright 2002-2015 Hewlett Packard Enterprise Development LP
 */

package Elements;

/**
 * This class defines the global objects and constants used by the web element classes.
 * Created by stauffel on 1/15/14.
 */
public class Globals {
    /**
     * This global Browser object is used throughout the screen and web element classes.  Once the browser is set (in the
     * test itself, all screen and web elements classes use it.
     */
    public static Browser browser = new Browser();

    /**
     * This constant is used for to wait for a maximum amount of time to see if a web element exists.  This constant
     * is also used as a maximum for waiting to see if a UI screen or panel is available.
     */
    public static final int ELEMENTTIMEOUT = 120;  // seconds
}
