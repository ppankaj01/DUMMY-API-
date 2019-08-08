/*
 * © Copyright 2002-2015 Hewlett Packard Enterprise Development LP
 */

package Elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static Elements.Globals.browser;
import static Helpers.Utility.log;


// IMPLEMENTATION NOTES:
//
// A radio button typically has an HTML structure like below:
//
// <input type="radio" id="example-id" name="..." value="...">
// <label for="example-id" data-localize="...">...</label>
//

/**
 * This class is used to interact with radio button web elements listed on the web page.
 *
 * Your selenium locator should uniquely match the following elements from the example in this class' code.
 * Element: <input type="radio" id="example-id" name="..." value="...">
 * Label element: <label for="example-id" data-localize="...">...</label>
 *
 * Created by stauffel on 12/10/13.
 */
public class RadioButton extends BaseElement {

    // - - - - - Class Attributes - - - - -
    By labelLocater;


    // - - - - - Constructor - - - - -

    /**
     * Constructor used to reference this class.
     *
     * @param  locator  the Selenium {@link By locator} for locating this web element<br/>
     */
    public RadioButton (By locator, By label) {
        // setting this classes query value for this specific radio button
        super(locator);
        labelLocater = label;
        return;
    }


    // - - - - - Class Methods - - - - -

    /**
     * This method selects on the radio button.
     * <p>
     * <b><u>NOTE:</u></b>  The radio button is first checked if it exists before interacting with it.
     * If the radio button does not exist, a NoSuchElementException is thrown.
     *
     */
    public void select () {
        // declaring local variables
        WebElement webElement;

        if (isStubbed()) {
            log("=== This radio button's locator is currently stubbed out. ===");
        } else {
            if (!isSelected()) {
                // getting the web element with the default timeout
                webElement = getWebElement(this.VISIBLE);

                webElement.click();

            }
        }
    }

    /**
     * This method returns the actual text associated with the radio button object.  If there is no text
     * associated with this radio button, an empty string will be returned.
     * <p>
     * <b><u>NOTE:</u></b>  The radio button is first checked if it exists before interacting with it.
     * If the radio button does not exist, a NoSuchElementException is thrown.
     *
     *  @return            a String value representing the text associated with the radio button.
     */
    public String getText() {
        // declaring local variables
        String returnValue = "";

        if (isStubbed()) {
            returnValue = "VALUE";
            log("=== This radio button's locator is currently stubbed out. Returning value '" + returnValue + "' ===");
        } else {
            returnValue = browser.findElement(labelLocater).getText(); // CRAIG: reverted to original code since getWebElement() broke this
        }
        return returnValue;
    }

    /**
     * This method checks to see if the radio button is selected.  If it is, a boolean value 'true' is returned.
     * If the radio button is not checked, a boolean value 'false' is returned.
     *
     * <b><u>NOTE:</u></b>  The radio button is first checked if it exists before interacting with it.
     * If the radio button does not exist, a NoSuchElementException is thrown.
     *
     *  @return        a boolean value 'true' if the radio button is selected, 'false' otherwise
     */
    public boolean isSelected () {
        // declaring local variables
        WebElement webElement;
        boolean returnValue = false;

        if (isStubbed()) {
            returnValue = true;
            log("=== This radio button's locator is currently stubbed out. Returning value '" + returnValue + "' ===");
        } else {
            webElement = getWebElement(this.VISIBLE);

            returnValue = Boolean.parseBoolean(webElement.getAttribute("checked"));
        }

        return returnValue;
    }

    @Override
    public boolean isStubbed() {
        // Override the isStubbed() method to take the labelQuery into account
        return super.isStubbed() || this.labelLocater.toString().contains("STUBBED");
    }

    /**
     * This will return the webelement when the radion button is in clickable state.
     * @return
     */
    public WebElement getRadioButtonElement() {
        return getWebElement(this.CLICKABLE);
    }
}
