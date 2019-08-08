package Helpers;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.concurrent.TimeUnit;

import static Elements.Globals.browser;

import static Helpers.Utility.log;
import static Helpers.Utility.warning;

/**
 * Created by demathur on 8/6/2019.
 */
public class Login {

    public static boolean login (String username, String password) {
        // declaring local variables
        boolean returnValue = true;

        log("");
        log("Log into SSMC management console.");

        returnValue = loginToSSMC(username, password, false);

        return returnValue;
    }

    private static boolean loginToSSMC (String username, String password, boolean addingSystems) {
        // declaring local variables
        boolean returnValue = true;
        long consoleLoadWaitTime = 10;
        final String ADMINTITLE = "Storage Systems - Administrator Console";

        // if log into Administrator Console
        if (addingSystems) {
            log("");
            log("Check if the Administrator Console CheckBox exists");
            if (ssmc.logInScreen.adminConsoleCheckBox.exists()) {
                log("");
                log("Check if the Administrator Console CheckBox is not checked");
                // if the Administrator Console CheckBox is not checked, check it
                if (!ssmc.logInScreen.adminConsoleCheckBox.isChecked()) {
                    log("");
                    log("Check the Administrator ConsoleBox");
                    ssmc.logInScreen.adminConsoleCheckBox.check();
                }
            } else {
                warning("The Administrator Console CheckBox on the Log In screen does not appear to exist.");
                browser.takeScreenShot("loginToSSMC_" + RandomStringUtils.random(4, true, true));
                returnValue = true; //when admin console opened by clicking on user icon from management console, checkbox doesn't exist, so making the return value true
            }

        }

        if (returnValue) {

            // CRAIG: Jun 21 2016 - As of the new login UI, with picture background, we are seeing some timing issues
            // where the User name field is not getting set properly.  I'm temporarily adding a short sleep
            // prio to setting the user name textfield to start debugging this problem
            Utility.sleep(4, TimeUnit.SECONDS);


            // setting textfields

            if (ssmc.logInScreen.usernameTextField.exists()) {
                // enter username
                log("");
                log("Entering the username '" + username + "' in the Log In screen.");
                ssmc.logInScreen.usernameTextField.clearText();
                ssmc.logInScreen.usernameTextField.setText(username);
            } else {
                browser.takeScreenShot("loginToSSMC_" + RandomStringUtils.random(4, true, true));
                warning("The username text field on the Log In screen does not appear to exist.");
                //browser.takeScreenShot("loginToSSMC_" + RandomStringUtils.random(4, true, true));
                returnValue = false;
            }
        }

        if (returnValue) {
            if (ssmc.logInScreen.passwordTextField.exists()) {
                // enter password
                log("Entering the password '" + password + "' in the Log In screen.");

                ssmc.logInScreen.passwordTextField.clearText();
                ssmc.logInScreen.passwordTextField.setText(password);

            } else {
                warning("The password text field on the Log In screen does not appear to exist.");
                browser.takeScreenShot("loginToSSMC_" + RandomStringUtils.random(4, true, true));
                returnValue = false;
            }
        }

        if (returnValue) {
            if (ssmc.logInScreen.logInButton.exists()) {
                if (ssmc.logInScreen.logInButton.isEnabled()) {
                    // clicking the log in button
                    log("Clicking the Log In button.");
                    ssmc.logInScreen.logInButton.click();
                } else {
                    warning("The Log In button on the Log In screen is not enabled.");
                    browser.takeScreenShot("loginToSSMC_" + RandomStringUtils.random(4, true, true));
                    returnValue = false;
                }
            } else {
                warning("The Log In button on the Log In screen does not appear to exist.");
                browser.takeScreenShot("loginToSSMC_" + RandomStringUtils.random(4, true, true));
                returnValue = false;
            }
        }

        if (returnValue) {
            // do we need to add systems?
            if (addingSystems) {
                // waiting to see if the Storage System Connections page is available
                //temporarily waiting for loading page to go away.
                log("");
                log("Waiting "+ Long.toString(consoleLoadWaitTime) +" sec for Administrator Console to load");
                Utility.sleep(consoleLoadWaitTime,TimeUnit.SECONDS);

                // the Administrator Console opens in a new window. Switch to that window.
                log("Switch to the new window");

                // making the new window active
                if (!browser.getWindow(ADMINTITLE)) {
                    warning("The Administrator Console page does not appear to be available. (getWindow)");
                    browser.takeScreenShot("loginToSSMC_" + RandomStringUtils.random(4, true, true));
                    returnValue = false;

                    if (!ssmc.adminConsoleScreen.isAvailable()) {
                        warning("The Administrator Console page does not appear to be available. (isAvailable)");
                        browser.takeScreenShot("loginToSSMC_" + RandomStringUtils.random(4, true, true));
                        returnValue = false;
                    }
                }

            } else {
                // checking for the title on the main console page
                if (!ssmc.isAvailable()) {
                    warning("The SSMC console web page does not appear to be available.");
                    browser.takeScreenShot("loginToSSMC_" + RandomStringUtils.random(4, true, true));
                    returnValue = false;
                }
            }
        }

        if(returnValue){
            //Popups do not have an exist() method, so checking for the popup title instead
            if(ssmc.tutorialPopup.dialogTitleText.exists(15)) {
                //Closing the Tutorial window
                if (ssmc.tutorialPopup.closeLink.exists(5)) {
                    if (ssmc.tutorialPopup.closeLink.isEnabled()) {
                        //clicking the close link
                        log("Clicking the Close link on the Tutorial dialog");
                        ssmc.tutorialPopup.closeLink.click();
                    } else {
                        warning("The Close link on the Tutorial dialog is not enabled.");
                        returnValue = false;
                    }
                } else {
                    warning("The Close link on the Tutorial dialog does not exist.");
                    returnValue = false;
                }
            }
        }

        return returnValue;
    }
}
