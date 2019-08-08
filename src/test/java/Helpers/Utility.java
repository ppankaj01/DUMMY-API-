package Helpers;

import org.testng.Assert;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Created by demathur on 8/6/2019.
 */
public class Utility {

    private static final PrintStream stdout = System.out;
    /**
     * This method logs a message to standard out.  It formats the date and time, and prints this time stamp along
     * with the message.
     *
     * @param msg        [STRING] the message to log
     */
    public static void log (String msg) {
        DateFormat format = new SimpleDateFormat("yyy/MM/dd HH:mm:ss");
        Date date = new Date();
        stdout.println("[" + format.format(date) + "]:   " + msg);
    }


    /**
     * This method logs an error to standard out.  It formats the date and time, and prints this time stamp along with
     * "ERROR", and then the message.
     *
     * @param msg        [STRING] the message to log
     */
    public static void fail (String msg) {
        msg = "ERROR:  " + msg;
        log(msg);
    }
    /**
     * This method logs a warning to standard out.  It formats the date and time, and prints this time stamp along with
     * "WARNING", and then the message.
     *
     * @param msg        [STRING] the message to log
     */
    public static void warning (String msg) {
        msg = "WARNING:  " + msg;
        log(msg);
    }

    /**
     * This method sleeps a number of time units.
     *
     * @param number     [LONG] the amount to wait
     * @param units      [TIMEUNIT] the time units
     */
    public static void sleep(long number, TimeUnit units) {
        try {
            units.sleep(number);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method logs a 'FAIL' at the end of the test, with beginning and following lines.
     * It also uses 'Assert.fail'.
     * @param msg        [STRING] the message to log
     */
    public static void testFail(String msg) {

        logTestFail(msg);

        Assert.fail(msg);
    }

    public static void logTestFail (String msg) {
        log("===========================================================================");
        msg = "FAIL!!  " + msg;
        log(msg);
        log("===========================================================================");
    }


    /**
     * This method retrieves a property value from a property file.
     * <p>
     * The file name is first checked within all the tests properties directories to see if it exists.  If the file
     * cannot be found, an IOException is thrown.
     * </p>
     *
     * @param fileName      [STRING] the name of the property file, not including the path
     * @param propertyName  [STRING] the name of the property where the value desired lives
     */
    public static String getPropertyValue (String fileName, String propertyName) throws IOException {
        // declaring local variables
        Properties prop = new Properties();
        String propertyDir = getPropertyPath(fileName);
        String returnValue = "";

        // open file
        InputStream input = new FileInputStream(propertyDir + File.separator + fileName);

        // load the file
        prop.load(input);

        // find the value with the listName
        returnValue = prop.getProperty(propertyName);

        return returnValue;
    }
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // Private Method:  getPropertyPath
    //
    // Purpose:  get the absolute path of the given property file, checking in each test directory for the properties
    //           directory.
    //
    // Parameters:
    // 1 - propertyFileName  [STRING] the name of the property file
    //
    // Returns:              [STRING] the absolute path where the property file resides
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static String getPropertyPath (String propertyFileName) throws FileNotFoundException {
        // declaring local variables
        String returnValue;
        String basePath = "D:/workspace/devanshSelenium/src/test/java";

        // declaring possible testing direcoties where property files can reside
        File manual = new File(basePath + File.separator + "properties" + File.separator + propertyFileName);

        // find the valid directory path for this property file
      if (manual.exists()) {
            returnValue = manual.getAbsolutePath();
        }else {
            throw new FileNotFoundException("File not found:  " + propertyFileName);
        }

        // now, take out the property file name out of returnValue -- we just want to return the path
        returnValue = returnValue.replace(File.separator + propertyFileName, "");

        return returnValue;
    }

}
