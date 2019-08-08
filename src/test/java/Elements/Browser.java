package Elements;

import org.openqa.selenium.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import javax.imageio.ImageIO;
import java.awt.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Timestamp;
import java.util.*;


import static Helpers.Utility.fail;
import static Helpers.Utility.log;


/**
 * Created by demathur on 7/26/2019.
 */
public class Browser {

    private final int defaultWidth = 1366;     // 1550;
    private final int defaultHeight = 768;     // 1015;

    /**
     * This constant is used for Internet Explorer string.
     */
    public final static String IE = "INTERNET EXPLORER";

    /**
     * This constant is used for Firefox string.
     */
    public final static String FIREFOX = "FIREFOX";

    /**
     * This constant is used for Chrome string.
     */
    public final static String CHROME = "CHROME";

    WebDriver driver;
    private String browserType;
    private int browserWidth;
    private int browserHeight;
    private int originalWidth;
    private int originalHeight;
    private String locale;
    private String parentWindowHandle;

    /**
     * Constructor for this class.  This only returns a reference to the browser class, but does not create a browser.
     * <p>
     * To create a specific browser, call the firefox(), ie(), or chrome() methods.
     * </p>
     */
    public Browser () {
        return;
    }


    public void setDriver(WebDriver driver){
        this.driver = driver;
    }

    public WebDriver getDriver() {
        return this.driver;
    }

    /**
     * This method opens a browser based on the specified browser type, using the default width and height.
     *
     * @param browserType     [STRING] use the constants defined in this class for the browser type
     *
     * @throws RuntimeException if it fails to open the specified browser
     */
        public void open (String browserType) throws InterruptedException {
            this.setBrowser(browserType, this.defaultWidth, this.defaultHeight);
            if (this.driver == null) {
                throw new RuntimeException("");
            }
        }

        public Browser setBrowser (String browserType, int width, int height) throws InterruptedException {
            // setting browser type
            this.browserType = browserType;

            // setting width and height to default values
            this.browserWidth = width;
            this.browserHeight = height;
            this.originalWidth = this.browserWidth;
            this.originalHeight = this.browserHeight;

            // setting web driver for this browser based on the browser type
            this.setDriver();

            return this;
        }


    /**
     * This method opens a Chrome browser, and returns a reference to this specific browser.
     *
     * @return      a reference to a Chrome browser.
     * @throws RuntimeException if it fails to open Chrome
     */
    public Browser chrome () throws InterruptedException {
        return this.setBrowser(CHROME, this.defaultWidth, this.defaultHeight);
    }
    public java.util.List<WebElement> findElements(final By by) {
        return this.driver.findElements(by);
    }


    //------------------------------------------------------------------------------------------
    /*
     * This method sets the web driver based on the browser type.  It also opens a browser, however it does not navigate
     * to a URL.  Use the navigate method to navigate to a specific URL.
     * It throws a RuntimeException if it fails to open the browser.
     */
    //------------------------------------------------------------------------------------------
    private void setDriver () throws InterruptedException {
        // declaring local variables
        File driverDir = null;
        File chromePath = null;
        File iePath = null;
        File firefoxPath = null;

        File f = new File(".");

        // getting the absolute path where "devanshSelenium" is located
        f.getAbsolutePath().toLowerCase().contains("devanshSelenium") ;


        // check, first, to make sure that the driver directory has a value, and exists
        if (!driverDir.exists()) {
            // let the user know the driver path is not set
            throw new RuntimeException("Unable to find the driver directory (" + driverDir.getAbsolutePath() + ") where the chromedriver.exe or IEDriverServer.exe resides.");
        }

                // determining which OS these tests are running on
                chromePath = new File(driverDir.getAbsolutePath() + File.separator + "../src/test/java/drivers/chromedriver.exe");


//        // if rowser type is FireFox, then set the web driver to FireFox
//        if (this.browserType.equals(this.FIREFOX)) {
//            if (OSUtil.getOS() == OS.LINUX) {
//                //System.setProperty("webdriver.firefox.driver","/var/lib/firefox/firefox");
//                System.setProperty("webdriver.firefox.driver", firefoxPath.getAbsolutePath());
//                //File pathToBinary = new File("/var/lib/firefox/firefox");
//                //FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
//                FirefoxBinary ffBinary = new FirefoxBinary(firefoxPath);
//                FirefoxProfile firefoxProfile = new FirefoxProfile();
//                firefoxProfile.setPreference( "intl.accept_languages", "ja,en-US" );
//                this.driver = new FirefoxDriver(ffBinary,firefoxProfile);
//            } else {
//                FirefoxProfile firefoxProfile = new FirefoxProfile();
//                if ( locale != null )
//                {
//                    firefoxProfile.setPreference("intl.accept_languages", locale);
//                }
//                firefoxProfile.setPreference("browser.helperApps.alwaysAsk.force",false);
//                firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk","application/zip,text/csv");
//                this.driver = new FirefoxDriver(firefoxProfile);
//            }
//        }
        // if the browser type is Chrome, then set the web driver to Chrome

            // checking to see if chromePath exists
            if (chromePath.exists()) {
                System.setProperty("webdriver.chrome.driver", chromePath.getAbsolutePath());
                try {
                    ChromeOptions options = new ChromeOptions();
                    if ( locale != null )
                    {
                        options.addArguments("–lang="+locale);
                    }
                    this.driver = new ChromeDriver(options);
                } catch (WebDriverException e) {
                    // If opening the Chrome driver fails due to time out in communicating with the driver process,
                    // kill all of the existing driver processes and try again.
                    this.killDriver();
                    Thread.sleep(5000);
                    this.driver = new ChromeDriver();
                }
            } else {
                throw new RuntimeException(("Failed to find Chrome Selenium driver at " + chromePath.getAbsolutePath()));
            }

        // set size
        this.setSize(this.browserWidth,  this.browserHeight);
        //this.maximize();

        // set this window handle
        this.parentWindowHandle = this.driver.getWindowHandle();
    }

    /**
     * This method sets the browser to a specific size.
     *
     * @param width      [INT] an int representing the width of the browser window
     * @param height     [INT] an int representing the height of the browser window
     */
    public void setSize (int width, int height) {
        // declaring local variables
        org.openqa.selenium.Dimension size = new org.openqa.selenium.Dimension(width, height);

        this.driver.manage().window().setSize(size);

    }


    /**
     * This method opens a Chrome browser, and returns a reference to this specific browser.
     *
     * @param width     the desired width of the browser
     * @param height    the desired height of the browser
     * @return          a reference to a Firefox browser.
     * @throws RuntimeException if it fails to open Chrome
     */
    public Browser chrome (int width, int height) throws InterruptedException {
        return this.setBrowser(CHROME, width, height);
    }

    public void navigate (String URL) {
        // in case the user is using an invalid URL, we need to give a nice message when this fails
        try {
            // navigating to the URL
            this.driver.get(URL);

        } catch (Exception e) {
            log("============ DEBUG STATEMENTS ============");
            log("Browser::navigate() -- exception caught when calling driver.get(" + URL + ") with webdriver '" + this.browserType + "':");
            e.printStackTrace();
//            fail("May be an invalid URL '" + URL + "' -- manually check the URL to make sure it works.");
            //browser.takeScreenShot();
            log("==========================================");
        }
    }
    /**
     * This method kills the driver process.  For Chrome and IE, this process seems to hang around after the test finishes,
     * and the browser is closed.  It's a good idea to use this method after you've closed the browser.
     */
    private void killDriver () {
        // declaring local variables
        String driverStr="";
        //String os = String.valueOf(OSUtil.getOS());


            if (this.browserType.equals(Browser.CHROME)) {
                driverStr = "chromedriver.exe";
            } else if (this.browserType.equals(Browser.IE)){
                driverStr = "IEDriverService.exe";
            }

            try {
                Runtime rt = Runtime.getRuntime();
                rt.exec("taskkill /f /im " + driverStr);

            } catch (Exception e) {
              //("ERROR:  Got an exception when trying to kill the " + driverStr + " process:");
                e.printStackTrace();
            }
        }


    private static String getCurrentTimestamp(){
        //long time = Calendar.getInstance().getTimeInMillis();
        //Timestamp ts = new Timestamp(time);
        Timestamp ts = new Timestamp(Calendar.getInstance().getTimeInMillis());
        String tsString = ts.toString();
        int msSeparatorPos = tsString.indexOf('.', tsString.length()-4);
        String msString = tsString.substring(msSeparatorPos +1);

        if (msString.length() == 1){
            msString =  msString + "00";
        }else if (msString.length() == 2){
            msString = msString + "0" ;
        }

        tsString = tsString.substring(0, msSeparatorPos + 1);
        tsString = tsString + msString;


        return tsString;
    }

    /**
     * Takes a snapshot of the of the screen and saves it in a file in the <code>ServerTests/build/reports/screenshots</code> directory.
     * If there are two connected screens, the screenshot will be of the primary screen.
     * @param filename name of the snapshot file.  If a directory path is included as part of the filename, the directory path will be ignored.
     * @return path name of the snapshot file
     */
    public String takeScreenShot(String filename) {
        String ts ;//timestamp string
        String screenshotFileName; //snapshot file name
        File screenShotDirectory = new File(System.getProperty("user.dir") + File.separator + "build" +
                File.separator + "reports" + File.separator + "screenshots");
        int start;

        //Variables needed to capture screenshot in IE
        Toolkit tk;
        java.awt.Dimension screenSize;
        BufferedImage screenImage;

        if (!screenShotDirectory.exists()) {
            screenShotDirectory.mkdirs();
        }

        //remove any directory path that might be included in the string.
        if (filename.contains(File.separator)){
            start = filename.lastIndexOf(File.separator) + + File.separator.length() ;
            filename = filename.substring(start,filename.length());
        }

        //figure out what to name the screenshot file.
        ts = getCurrentTimestamp();
        ts = ts.replace(' ', '_');
        ts = ts.replace(':', '.');

        // setting the file name for this screen shot
        screenshotFileName = screenShotDirectory + File.separator + filename + "_" + ts + ".png";

        // take the screenshot and save it to a file
        try{

                // getting the screen shot
                File screenshot = ((TakesScreenshot) this.driver).getScreenshotAs(OutputType.FILE);

                // changing the name of the screen shot to the screenshotFileName
                screenshot.renameTo(new File(screenshotFileName));
                log("Took screenshot and placed it in file " + screenshotFileName);

        }catch (Exception e){
            String err = "INTERNAL LOGGING ERROR: Unable to capture screenshot.";
            fail(err);
            e.printStackTrace();
        }
        return screenshotFileName;
    }
    /**
     * Finds the first <code>WebElement</code> using the given mechanism.
     * It simply redirects the call to the {@link WebDriver#findElement findElement}
     * method of the underlying {@link WebDriver} instance.
     * @param by the locating mechanism
     * @return the first {@link WebElement} located by the given mechanism
     * @throws NoSuchElementException thrown if it finds no element using the given mechanism
     */
    public WebElement findElement(final By by) {
        return this.driver.findElement(by);
    }
}











