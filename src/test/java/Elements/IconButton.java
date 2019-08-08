/*
 * © Copyright 2002-2015 Hewlett Packard Enterprise Development LP
 */

package Elements;

import static Helpers.Utility.log;
import static Helpers.Utility.sleep;


import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

public class IconButton extends BaseElement {


    // - - - - - Constructor - - - - -

    /**
     * Constructor used to reference this class.
     *
     * @param  locator     the Selenium {@link By locator} for locating this web element
     * @param  labelValue  [STRING] the expected label shown in the button
     */
    public IconButton (By locator) {
        super(locator);
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


}
