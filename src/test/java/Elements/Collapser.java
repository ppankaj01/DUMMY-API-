/*
 * © Copyright 2002-2017 Hewlett Packard Enterprise Development LP
 */

package Elements;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;

import static Elements.Globals.browser;
import static Helpers.Utility.log;
import static Helpers.Utility.sleep;


// IMPLEMENTATION NOTES:
//
// An expander button typically has an HTML structure like one of the two structures below:
//
// Structure #1:
// <div id="example-id" class="hp-collapsible">
//   ...
// </div>
//
// Structure #2:
// <a id="example-id" class="ssmc-super-expand-control">...</a>
//
// Structure #1:
// <div id="example-id" class="hp-collapser">
//   ...
// </div>
//

public class Collapser extends BaseElement {

    // - - - - - Class attributes - - - - -


    // - - - - - Constructor - - - - -

    /**
     * Constructor used to reference this class.
     *
     * @param locator the Selenium {@link org.openqa.selenium.By locator} for locating this web element
     */
    public Collapser(By locator) {
        super(locator);
        return;
    }


    // - - - - - Class Methods - - - - -

    /**
     * This method is used to expand the expander button.  First, the expander button is checked to make sure it
     * is collapsed.  If the expander button is in an expanded state, this method will do nothing.  Otherwise, it will
     * click on the button to expand.
     * <p>
     * <b><u>NOTE:</u></b>  The button is first checked if it exists before interacting with it.  If the button does not
     * exist, a {@link NoSuchElementException} exception will be thrown.
     */
    public void expand() {
        if (isStubbed()) {
            log("=== This expander button's locator is currently stubbed out. ===");
        } else {
            // check to see if the expander button is already expanded
            if (!this.isExpanded()) {
                // getting the web element with the default timeout
                WebElement webElement = getWebElement();

                // if we are using an IE browser, then we need to get the button in focus first before clicking on it
                //        if (browser.isIE()) {
                //            // Put the focus on the button
                //            moveCursorTo();
                //        }

                // wait 1 second for the UI to catch up with Selenium -- otherwise, Selenium tries to click on something
                // that Selenium knows is there, but it's not yet showing in the UI
                sleep(1, TimeUnit.SECONDS);

                    if (!this.isExpanded())
                        this.clickWebElement(webElement);
                    if (!this.isExpanded())//for some reason , you sometimes need to click twice.  Doesn't happen when you do this manually.
                        this.clickWebElement(webElement);


                sleep(1, TimeUnit.SECONDS);
            }
        }
    }


    /**
     * This method is used to collapse the expander button.  First, the expander button is checked to make sure it
     * is expanded.  If the expander button is in a collapsed state, this method will do nothing.  Otherwise, it will
     * click on the button to collapse.
     * <p>
     * <b><u>NOTE:</u></b>  The button is first checked if it exists before interacting with it.  If the button does not
     * exist, a {@link NoSuchElementException} exception will be thrown.
     */
    public void collapse() {
        WebElement clickableArea = null;
        if (isStubbed()) {
            log("=== This expander button's locator is currently stubbed out. ===");
        } else {
            // check to see if the expander button is already expanded
            if (this.isExpanded()) {
                // getting the web element with the default timeout
                WebElement webElement = getWebElement();

                //with with vertical expander buttons, you have to click on the inner label to
                //get it to collapse.  This doesn't apply to horizontal expander buttons.
                try {
                    clickableArea = webElement.findElement(By.tagName("label"));
                } catch (NoSuchElementException e) {
                    clickableArea = webElement;
                }


                // if we are using an IE browser, then we need to get the button in focus first before clicking on it
                //        if (browser.isIE()) {
                //            // Put the focus on the button
                //            moveCursorTo();
                //        }

                // wait 1 second for the UI to catch up with Selenium -- otherwise, Selenium tries to click on something
                // that Selenium knows is there, but it's not yet showing in the UI
                sleep(1, TimeUnit.SECONDS);

                clickableArea.click();


                sleep(1, TimeUnit.SECONDS);
            }
        }
    }

    /**
     * This method checks to see if the expander button is expanded on the screen.  If so, this method will return
     * a boolean value 'true'.  Otherwise, if the expander button is collapsed, a boolean value of 'false' will be
     * returned.
     * <p>
     * If the expander button does not exist, a {@link NoSuchElementException} exception will be thrown.
     *
     * @return a boolean value 'true' if the expander button is expanded, 'false' otherwise.
     */
    public boolean isExpanded() {
        // declaring local variables
        boolean returnValue = false;

        if (this.isStubbed()) {
            returnValue = true;
            log("=== This expander button's locator is currently stubbed out. Returning value '" + returnValue + "' ===");
        } else {
            // getting the web element with the default timeout
            WebElement webElement = getWebElement();

            // get the class name attribute, which will tell us if the checkobx is actually checked or not
            String className = webElement.getAttribute("className");

            //if this is a horizontal expander, then "expand-control" shows up if it is collapsed
            //if this is a vertical expander, then "hp-collapsed" is the class if it is collapsed
            if (!(className.contains("expand-control") || className.contains("hp-collapsed")))
                returnValue = true;

            if (className.contains("hp-collapser"))
                returnValue = className.contains("hp-active");
        }

        return returnValue;
    }

    /**
     * This method returns the label associated with the expander button.
     *
     * @return a String value containing the label of the expander button.  If there is no label asoociated with the
     * expander button, then an empty string is returned
     */
    public String getText() {
        String returnValue = "";
        WebElement labelArea = null;
        if (isStubbed()) {
            log("=== This expander button's locator is currently stubbed out. ===");
            returnValue = "Stubbed label";
        } else {
            // getting the web element with the default timeout
            WebElement webElement = getWebElement();

            //with with vertical expander buttons, you get the label from the inner label element.
            //  This doesn't apply to horizontal expander buttons which have no labels.
            try {
                labelArea = webElement.findElement(By.tagName("label"));
                returnValue = labelArea.getText();
            } catch (NoSuchElementException e) {
                //don't do anything.  the expander button has no label so we return empty string.
            }
        }

        return returnValue;
    }


}
