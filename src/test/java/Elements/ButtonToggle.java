/*
 * © Copyright 2002-2015 Hewlett Packard Enterprise Development LP
 */

package Elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static Elements.Globals.browser;
import static Helpers.Utility.log;


// IMPLEMENTATION NOTES:
//
// A button toggle typically has an HTML structure like below:
//
// <a id="toggle-hpToggle-example-id" class="hp-toggle">
//   <ol>
//     <li class="hp-on">...</li>
//     <li class="hp-off">...</li>
//   </ol>
//   <div class="hp-toggle-thumb hp-checked"></div>
// </div>
//

/**
 * This class is used to interact with a button that can be toggled on and off, or between two options.
 *
 * Your selenium locator should uniquely match the following element from the example in this class' code.
 * Element: <a id="toggle-hpToggle-example-id" class="hp-toggle">
 *
 * Created by stauffel on 6/6/14.
 */
public class ButtonToggle extends Button {

    // - - - - - class members - - - - -


    // - - - - - constructor - - - - -

    public ButtonToggle (By locatorValue) {
        super(locatorValue, "");
    }

    /**
     *
     * @param locatorValue
     * @param labelValue the should the concatenated text of the text displayed when toggle is on and off.
     */
    public ButtonToggle(By locatorValue, String labelValue) {
        super(locatorValue, labelValue);
    }


    // - - - - - class methods - - - - -

    /**
     * This method checks to see if the button toggle is considered 'on' or 'off'.  If the button toggle is
     * considered 'on', this method will return a boolean value of 'true'.  If the button toggle is considered
     * 'off', this method will reutrn a boolean vlaue of 'false'.
     *
     * @return        a boolean value 'true' if the toggle is shown as 'on', 'false' otherwise
     */
    public boolean isToggleOn () {
        // declaring local variables
        WebElement webElement;
        String className;
        boolean returnValue = false;

        if (this.isStubbed()) {
            log("=== This button's locator is currently stubbed out. Returning value '" + returnValue + "' ===");
        } else {
            // getting the web element with the default timeout
            webElement = getWebElement();

            // get the class name attribute, which will tell us if the checkobx is actually checked or not
            className = webElement.getAttribute("className");

            // if we see the class name 'hp-checked', then we know the toggle is considered 'on'
            if (className.contains("hp-checked")) {
                returnValue = true;
            }
        }

        return returnValue;
    }

    /**
     * This method returns the current text of the button being displayed.  This text changes depending on when
     * the button toggle is turned 'on' or 'off'.
     *
     * @return      a string representing the current text displayed in the button
     */
    public String getText () {
        // declaring local variables
        WebElement webElement;
        boolean toggleStatus = this.isToggleOn();
        String className = "";
        String returnValue = "";

        if (this.isStubbed()) {
            log("=== This button's locator is currently stubbed out. Returning value '" + returnValue + "' ===");
        } else {
            // getting the web element with the default timeout
            webElement = getWebElement();

            // get the list of possible text showing in the button
            List<WebElement> listElements = webElement.findElements(By.tagName("li"));

            for (int i = 0; i < listElements.size(); i++) {
                // getting the class name of this list element
                className = listElements.get(i).getAttribute("className");

                // get the text depending on the status of the toggle
                if (toggleStatus) {
                    if (className.contains("hp-on")) {
                        returnValue = listElements.get(i).getAttribute("textContent");
                        i = listElements.size();
                    }
                } else {
                    if (className.contains("hp-off")) {
                        returnValue = listElements.get(i).getAttribute("textContent");
                        i = listElements.size();
                    }
                }
            }
        }

        return returnValue;
    }

    /**
     * This method toggles the toggle button.
     */
    public void toggle () {
        // declaring local variables
        WebElement webElement;

        if (isStubbed()) {
            log("=== This toggle button's locator is currently stubbed out. ===");
        } else {

            webElement = browser.findElement(getLocator());
            webElement.click();
        }
    }



}
