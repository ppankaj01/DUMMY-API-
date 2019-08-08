/*
 * © Copyright 2002-2015 Hewlett Packard Enterprise Development LP
 */

package Elements;


import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static Helpers.Utility.log;
import static Helpers.Utility.sleep;

// IMPLEMENTATION NOTES:
//
// A checkbox typically has an HTML structure like below:
//
// <input id="checkbox-example-id" type="checkbox">
// <label for="checkbox-example-id" data-localize="...">...</label>
//

/**
 * This class is used to interact with checkbox web elements listed on the web page.
 *
 * Your selenium locator should uniquely match the following element from the example in this class' code.
 * Element: <input id="checkbox-example-id" type="checkbox">
 * Label element: <label for="checkbox-example-id" data-localize="...">...</label>
 *
 * Created by stauffel on 12/10/13.
 */
public class CheckBox extends BaseElement {


    // - - - - - Class attributes - - - - -
    By labelLocator = null;

    // - - - - - Constructor - - - - -

    /**
     * Constructor used to reference this class.
     *
     * @param  locator  the Selenium {@link By locator} for locating this web element
     */
    public CheckBox (By locator) {
        super(locator);
    }


    /**
     * Constructor used to reference this class.
     *
     * @param  locator       the Selenium {@link By locator} for locating this web element
     * @param  labelLocator  the Selenium {@link By locator} for locating the label associated with this web element
     */
    public CheckBox (By locator, By labelLocator) {
        super(locator);
        this.labelLocator = labelLocator;
    }

    //  - - - - - Class Methods - - - - -

    /**
     * This method checks the check box.
     * <p>
     * <b><u>NOTE:</u></b>  The check box is first checked if it exists before interacting with it.  If the check box does not
     * exist, a NoSuchElementException will be thrown.
     */
    public void check () {
        // declaring local variables
        WebElement webElement;

        if (isStubbed()) {
            log("=== This checkbox's locator is currently stubbed out. ===");
        } else {
            // getting the web element of the check box with the default timeout
            // and then check its status

            int MAXRETRIES = 3;
            int tries = 1;
            while (!this.isChecked() && tries <= MAXRETRIES) {
                // need to give a second for the browser to catch up with the web driver
                //sleep(1, TimeUnit.SECONDS);

                // Directly get the web element since we know that it exists and is displayed
                webElement = getWebElement ();
                this.clickWebElement (webElement);




                tries++;
                if (this.isChecked() == false)
                {
                    sleep(1, TimeUnit.SECONDS);
                }
            }
        }
    }

    /**
     * This method checks the check box.
     * @param doNotVerify if true, do not verify that the checkbox was checked. This is useful for tests that want to
     *                    click the checkbox but will prevent the checkbox from being selected for certain conditions.
     * <p>
     * <b><u>NOTE:</u></b>  The check box is first checked if it exists before interacting with it.  If the check box does not
     * exist, a NoSuchElementException will be thrown.
     */
    public void check (boolean doNotVerify) {
        // declaring local variables
        WebElement webElement;

        if (isStubbed()) {
            log("=== This checkbox's locator is currently stubbed out. ===");
        } else if (doNotVerify) {
            // Only click the element and immediately return.
            // Directly get the web element since we know that it exists and is displayed
            webElement = getWebElement ();
            this.clickWebElement (webElement);
        } else {
            // getting the web element of the check box with the default timeout
            // and then check its status

            int MAXRETRIES = 3;
            int tries = 1;
            while (!this.isChecked() && tries <= MAXRETRIES) {
                // need to give a second for the browser to catch up with the web driver
                //sleep(1, TimeUnit.SECONDS);

                // Directly get the web element since we know that it exists and is displayed
                webElement = getWebElement ();
                this.clickWebElement (webElement);


                tries++;
                if (this.isChecked() == false)
                {
                    sleep(1, TimeUnit.SECONDS);
                }
            }
        }
    }

    /**
     * This method unchecks the check box.
     * <p>
     * <b><u>NOTE:</u></b>  The check box is first checked if it exists before interacting with it.  If the check box does not
     * exist, a NoSuchElementException will be thrown.
     */
    public void uncheck () {
        // declaring local variables
        WebElement webElement;

        if (isStubbed()) {
            log("=== This checkbox's locator is currently stubbed out. ===");
        } else {
            // getting the web element of the check box with the default timeout
            // and then check its status
            if (this.isChecked()) {
                // need to give a second for the browser to catch up with the web driver
                //sleep(1, TimeUnit.SECONDS);

                // Directly get the web element since we know that it exists and is displayed
                webElement = getWebElement ();
                webElement.click();



            }
        }
    }


    /**
     * This method checks to see if the check box is checked.  If it is, a boolean value 'true' is returned.
     * If the check box is not checked, a boolean value 'false' is returned.
     * <p>
     * <b><u>NOTE:</u></b>  The check box is first checked if it exists before interacting with it.  If the check box does not
     * exist, a NoSuchElementException will be thrown.
     *
     *  @return        a boolean value 'true' if the check box is checked, 'false' otherwise
     */
    public boolean isChecked () {
        // declaring local variables
        WebElement webElement;
        String className;
        boolean returnValue = true;

        if (isStubbed()) {
            log("=== This checkbox's locator is currently stubbed out. Returning value '" + returnValue + "' ===");
        } else {
            // getting the web element with the default timeout
            webElement = getWebElement();

            returnValue = webElement.isSelected();
        }

        return returnValue;
    }

    /**
     * This method returns the actual text associated with check box.  If there is no text associated with the
     * check box, a NoSuchElementException will be thrown.
     * <p>
     * <b><u>NOTE:</u></b>  The check box is first checked if it exists before interacting with it.  If the check box does not
     * exist, an error will be logged indicating the check box did not exist, and the test will automatically stop.
     *
     *  @return        a String value representing the text listed with check box.
     */
    public String getText() {
        // declaring local variables
        String returnValue = "VALUE";

        if (isStubbed()) {
            log("=== This checkbox's locator is currently stubbed out. Returning value '" + returnValue + "' ===");
        } else {
            // If a locator for this checkbox was given
            if(labelLocator != null) {
                returnValue = new Text(labelLocator).getText();
            } else {
                returnValue = getWebElement().getText();

                if (returnValue.isEmpty()) {
                    // getting the web element with the default timeout and then get its parent which has the label associated
                    // with the check box
                    returnValue = getWebElement().findElement(By.xpath("../label")).getText();
                }
            }
        }

        return returnValue;
    }

    /**
     * Added to get data as per attribute of checkbox
     * @param attribute
     * @return
     * Created By NileshG on 06/22/2015
     */
    public String getAttribute(String attribute){
        return getWebElement().getAttribute(attribute);
    }
}
