/*
 * © Copyright 2002-2015 Hewlett Packard Enterprise Development LP
 */

package Elements;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

import static Helpers.Utility.log;
import static Helpers.Utility.sleep;
import static Helpers.Utility.warning;


// IMPLEMENTATION NOTES:
//
// A button typically has an HTML structure like below:
//
// <input id="example-id" class="hp-button" type="button" data-localize="..." value="...">
//

/**
 * This class is used to interact with button web elements shown on a web page.
 *
 * Your selenium locator should uniquely match the following element from the example in this class' code.
 * Element: <input id="example-id" class="hp-button" type="button" data-localize="..." value="...">
 *
 * Created by stauffel on 12/10/13.
 */
public class Button extends BaseElement {

    // - - - - - Class attributes - - - - -

    private String label;

    // - - - - - Constructor - - - - -

    /**
     * Constructor used to reference this class.
     *
     * @param  locator     the Selenium {@link By locator} for locating this web element
     * @param  labelValue  [STRING] the expected label shown in the button
     */
    public Button (By locator, String labelValue) {
        super(locator);
        this.label = labelValue;
        return;
    }


    // - - - - - Class Methods - - - - -

    /**
     * This method is used to click on a button object.
     * <p>
     * <b><u>NOTE:</u></b>  The button is first checked if it exists before interacting with it.  If the button does not
     * exist, a {@link NoSuchElementException} exception will be thrown.
     */
    public void click () {
        // declaring local variables
        WebElement webElement;

        if (isStubbed()) {
            log("=== This button's locator is currently stubbed out. ===");
        } else {

            //check for existence only if the label string is not empty.
            //If it is empty then we only need to check the locator which will be done by getWebelement.
            if (!this.exists())
                throw new NoSuchElementException("ERROR!  This " + getName() + " does not exist.  Locator: " + this.getLocator () + "  Label: " + this.label);

            // getting the web element with the default timeout
            webElement = getWebElement();



            // wait 1 second for the UI to catch up with Selenium -- otherwise, Selenium tries to click on something
            // that Selenium knows is there, but it's not yet showing in the UI
            sleep(1, TimeUnit.SECONDS);

            webElement.click();

            sleep(1, TimeUnit.SECONDS);
        }
    }


    /**
     * This method returns the actual text shown on the button.
     * <p>
     * <b><u>NOTE:</u></b>  The button is first checked if it exists before interacting with it.  If the button does not
     * exist, a {@link NoSuchElementException} exception will be thrown.
     *
     *  @return  a String value representing the text shown on the button.
     */
    public String getText() {
        // declaring local variables
        String returnValue;

        if (isStubbed()) {
            returnValue = "VALUE";
            log("=== This button's locator is currently stubbed out. Returning value '" + returnValue + "' ===");
        } else {
            // getting the web element with the default timeout and then get its text
            returnValue = getWebElement().getText();
            // If an empty string was returned when obtaining the text of a button, text will be grabbed from the value
            // of the button
            if(returnValue.equals("")) {
                returnValue = getWebElement().getAttribute("value");
            }

        }

        return returnValue;
    }

    /**
     * This method checks to see if the button is enabled on the screen.  If so, this method will return
     * a boolean value 'true'.  If it does not exist, a {@link NoSuchElementException} exception will be thrown.
     *
     *  @return  a boolean value 'true' if the button is enabled, 'false' otherwise.
     */
    @Override
    public boolean isEnabled () {
        // declaring local variables
        WebElement webElement;
        String className;
        boolean returnValue = true;

        if (this.isStubbed()) {
            log("=== This button's locator is currently stubbed out. Returning value '" + returnValue + "' ===");
        } else {
            // getting the web element with the default timeout
            webElement = getWebElement();

            // get the class name attribute, which will tell us if the checkobx is actually checked or not
            className = webElement.getAttribute("className");

            // TODO: What do we need for Piano?
            returnValue = !className.contains("disabled");
        }

        return returnValue;
    }

    /**
     * This method returns the expected label of what the Button should be.
     *
     * @return   [STRING] value of what the button label should be
     */
    public String getExpectedLabel () {
        return this.label;
    }

    /**
     * This method checks to see if the web element exists and is displayed on the screen.  If the web element does not exist,
     * and is not displayed, this method keeps checking until:<br>
     * 1 - the web element exists and is displayed or<br>
     * 2 - the timeOutSeconds has been reached.
     * <p>
     * If the web element exists and is displayed before the timeOutSeconds has been reached, this method returns a boolean
     * value of 'true'.
     * </p>
     * <p>
     * If the web element does not exist and is not displayed, and the timeOutSeconds has been reached, this method returns
     * a boolean value of 'false'.
     * </p>
     * <p>
     * <b><u>NOTE:</u></b>  It's important to note that just because the web element exists and is displayed, doesn't always mean
     * it's enabled.
     * For example, if the web element is a button, the button may exist, but the button could be disabled.
     * </p>
     *
     * @param timeOutSeconds     [INT] the maximum number of seconds to wait before timing out
     * @return                   a boolean value 'true' if the web element exists, 'false' otherwise.
     */
    @Override
    public boolean exists (int timeOutSeconds) {
        // declaring local variables
        String textValue = "";
        boolean returnValue = true;

        // first, check to see if the web element's locator is stubbed -- if so, then print out a message and return true
        if (isStubbed()) {
            returnValue = true;
            log("=== This web element's locator is currently stubbed out. ===");
        } else {
            // call the parent's exist method
            //------------------
            if (super.exists(timeOutSeconds)) {
                WebElement buttonElement = getWebElement();

                // make sure the actual text for this tab is the one we expect
                textValue = buttonElement.getText();
                textValue = textValue.trim();

                if (textValue.equals(this.label)) {
                    returnValue = true;
                } else {
                    // try to get the text another way
                    textValue = buttonElement.getAttribute("value");
                    if (textValue == null)
                        returnValue = false;
                    else{
                        textValue = textValue.trim();

                        if (textValue.equals(this.label)) {
                            returnValue = true;
                        } else {
                            warning("WARNING:  Expected the button label to be '" + this.label + "', but the actual button label is '" + textValue + "'.");
                            returnValue = false;
                        }
                    }
                }
            } else {
                returnValue = false;
            }
        }
        return returnValue;
    }

    /**
     * This method returns the tooltip text shown on the button.
     * <p>
     * <b><u>NOTE:</u></b>  The button is first checked if it exists before interacting with it.  If the button does not
     * exist, a {@link NoSuchElementException} exception will be thrown.
     *
     *  @return  a String value representing the tooltip text shown on the button.
     */
    public String getToolTipText () {
        // declaring local variables
        String returnValue;

        if (isStubbed()) {
            returnValue = "VALUE";
            log("=== This button's locator is currently stubbed out. Returning value '" + returnValue + "' ===");
        } else {
            // getting the web element with the default timeout and then get its tooltip
            returnValue = getWebElement().getAttribute("data-tooltip");
        }
        return returnValue;
    }
}
