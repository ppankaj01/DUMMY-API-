

package Helpers;



import Elements.Browser;
import org.testng.annotations.*;
import sun.security.krb5.Credentials;

import java.io.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static Elements.Globals.browser;
import static Helpers.Login.login;
import static Helpers.Utility.getPropertyValue;
import static Helpers.Utility.log;
import static Helpers.Utility.testFail;

/**
 * This class is a base class for use with the new SSMC UI test template Template_UserStoryNumber.java to create automated tests.
 * See sample test SampleTest_US8330 for a sample test using this template.
 * <p>
 * Note:  This base class and its corresponding template were not created to encourage people to create more test base classes. <p>
 *        If you have a need for further functionality in the base class, then contact the test automation team.
 *
 *
 *
 */


public abstract class BaseTest {

    private String testUserName="";
    private String testPassword="";
    private static String testUrl = "";
    protected static String testClassName = "" ;
    private String propertyFile ="automation_plugin.properties";
    private boolean loginSuccess = false;

    @BeforeTest
    public void beforeTest() {
            String username = testUserName;
            String password = testPassword;

            log("");
           // log(testClassName + "." + getTestName() + ": @Before - beforeTest() - open browser and login");
            log(": @Before - beforeTest() - open browser and login");
        // browserType is provided by the getTestParameters method
        try {
            browser = browser.chrome();

            // navigate to the SSMC login window
            browser.navigate(testUrl);

//            if (ssmc.logInScreen.adminConsoleCheckBox.exists()) // wait for indication taht the page is finished rendering
//                browser.disableTutorialPopup();

            // login

            username = getPropertyValue(propertyFile, "array1.username");
            password = getPropertyValue(propertyFile, "array1.password");

            loginSuccess = login(username, password);
            // log an issue if logging in wasn't successful
            if (loginSuccess) {
                log("SUCCESS:  Able to log into SSMC");
            } else {
                testFail("UNSUCCESSFUL LOG IN TO SSMC");
            }
        } catch (Exception e) {
            e.printStackTrace();
            browser.takeScreenShot("beforeTest_");
            testFail(testClassName + " - may be issue with browser driver.");
        }
    }




}
