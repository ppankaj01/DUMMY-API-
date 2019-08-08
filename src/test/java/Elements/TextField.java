/*
 * © Copyright 2002-2016 Hewlett Packard Enterprise Development LP
 */

package Elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import java.util.concurrent.TimeUnit;

import static Helpers.Utility.log;
import static Helpers.Utility.sleep;


/**
 * This class is used to interact with textfield web elements listed on the web page.
 *
 * Your selenium locator should select an input tag (<input>), which can be read-only.
 *
 * Created by stauffel on 12/10/13.
 */
public class TextField extends BaseElement {


    // - - - - - Class Attributes - - - - -


    // - - - - - Constructor - - - - -

    /**
     * Constructor used to reference this class.
     *
     * @param locator      the Selenium {@link By locator} for locating this web element
     */
    public TextField (By locator) {
        // setting this classes locator value for this specific text
        super(locator);
    }


    //  - - - - - Class Methods - - - - -

    /**
     * This method is used to set the value in the text field on the screen.
     * <p>
     * <b><u>NOTE:</u></b>  The text field is first checked if it exists before interacting with it.
     * If the text field does not exist, a NoSuchElementException is thrown.
     *
     * @param value        [STRING] value to enter into the text field object
     */
    public void setText(String value) {
        // declaring local variables
        WebElement webElement;

        // is this object's query stubbed out?
        if (this.isStubbed()) {
            log("=== This text field's query is currently stubbed out. ===");
        } else {

            webElement = getWebElement();

            // giving the browser a second to catch up with the web driver
            //sleep(1, TimeUnit.SECONDS);
            webElement.click();
            // setting text
            webElement.sendKeys(value);


        }
    }

    /**
     * This method is used to set the value in the text field on the screen, one character at a time, waiting
     * seconds between entering each character.
     * <p>
     * <b><u>NOTE:</u></b>  The text field is first checked if it exists before interacting with it.
     * If the text field does not exist, a NoSuchElementException is thrown.
     *
     * @param value        [STRING] value to enter into the text field object
     * @param seconds      [INT] number of seconds to pause between each character listed in value
     */
    public void setText(String value, int seconds) {
        // declaring local variables
        WebElement webElement;
        String temp = "";

        // is this object's query stubbed out?
        if (this.isStubbed()) {
            log("=== This text field's query is currently stubbed out. ===");
        } else {

            webElement = this.getWebElement();

            // giving the browser a second to catch up with the web driver
            //sleep(1, TimeUnit.SECONDS);

            // setting text
            for (int i = 0; i < value.length(); i++) {
                temp = value.substring(i,  i+1);
                webElement.sendKeys(temp);
                sleep(seconds, TimeUnit.SECONDS);
            }
        }
    }

    /**
     * This method is used to clear the existing value from the text field.
     * <p>
     * <b><u>NOTE:</u></b>  The text field is first checked if it exists before interacting with it.
     * If the text field does not exist, a NoSuchElementException is thrown.
     *
     */
    public void clearText() {
        // declaring local variables
        WebElement webElement;

        // is this object's query stubbed out?
        if (this.isStubbed()) {
            log("=== This text field's query is currently stubbed out. ===");
        } else {

            webElement = this.getWebElement();

            // giving the browser a second to catch up with the web driver
            //sleep(1, TimeUnit.SECONDS);

            webElement.clear();
        }
    }

    /**
     * This method returns the actual text value listed in the text field.  If the text field is
     * empty, then an empty string will be returned.
     * <p>
     * <b><u>NOTE:</u></b>  The text field is first checked if it exists before interacting with it.
     * If the text field does not exist, a NoSuchElementException is thrown.
     *
     * @return            a String value representing the actual text displayed in the text field.
     */
    public String getText() {
        // declaring local variables
        WebElement webElement;
        String returnValue = "";

        // is this object's query stubbed out?
        if (this.isStubbed()) {
            returnValue = "VALUE";
            log("=== This text field's query is currently stubbed out. Returning value '" + returnValue + "' ===");
        } else {
            webElement = this.getWebElement();

            returnValue = webElement.getAttribute("value");

        }
        return returnValue;
    }


    /**
     * This method checks to see if the text field is enabled, ready for the user to enter a value.  If the text
     * field is enabled, this method will return 'true'.  Otherwise, this method will return 'false'.
     * <p>
     * <b><u>NOTE:</u></b>  The text field is first checked if it exists before interacting with it.
     * If the text field does not exist, a NoSuchElementException is thrown.
     *
     * @return            a boolean if enabled or not
     */
    public boolean isEnabled() {
        // declaring local variables
        WebElement webElement;
        boolean returnValue = false;

        // is this object's query stubbed out?
        if (this.isStubbed()) {
            returnValue = true;
            log("=== This text field's query is currently stubbed out. Returning value '" + returnValue + "' ===");
        } else {
            webElement = this.getWebElement();

            returnValue = webElement.isEnabled();

        }
        return returnValue;
    }

    /**
     * This method checks to see if the text field is disabled. It determines this by checking if the element contains a {@code disabled} element. <br>
     * For example, the element {@code <input id="ssmc-hw-systems-edit-fcrawspacealert-value" name="fcRawSpaceAlert" style="display: none;" disabled>}
     * is disabled and will return true.
     * <p> <b>NOTE:</b> this method differs from the {@link #isEnabled()} method. The {@link #isEnabled()} calls the default  {@link #getWebElement()} method which
     * has an underlying assumption that the element is clickable or visible. Not all elements are clickable or visible. In the previous example, the element is not
     * clickable because it's disabled and it's not visible because it has the {@code style="display: none;"} attribute. This method waits on the presence of the element
     * (simple query by ID) and asserts that it's disabled.
     *
     * @return a boolean value if the element has the disabled attribute or not
     *
     * @throws NoSuchElementException if a timeout occurs when waiting on the presence of the element.
     */
    public boolean isDisabled() {
        boolean isDisabled = false;
        if (isStubbed()) {
            log("=== This text field's query is currently stubbed out. Returning value '" + isDisabled + "' ===");
        } else {
            WebElement webElement = getWebElement(PRESENT);
            isDisabled = !webElement.isEnabled();
        }
        return isDisabled;
    }

    /**
     * This method checks to see if the textfield shows as invalid.  Some textfields have logic behind them that check
     * the value the user entered.  For example, password textfields may check to see if the characters are a certain
     * length.  If the logic returns that the value is invalid, the textfield changes where a color of red is shown and
     * possisbly a message.  This method only checks to see if the textfield shows as "invlaid".  It doesn't show the
     * color, nor the message, just if the textfield is shown on the web page as being invalid.
     * <p>
     * If the textfield is showing as invalid, then this method returns a boolean value of 'true'.  Otherwise, if the
     * textfield value is valid, this method returns a boolean value of 'false'.
     * <p>
     * In the case where a textfield doesn't have logic to check the value, this method will return 'false'.
     *  <p>
     * <b><u>NOTE:</u></b>  The text field is first checked if it exists before interacting with it.
     * If the text field does not exist, a NoSuchElementException is thrown.
     * @return
     */
    public boolean isEntryInvalid () {
        // declaring local variables
        String className = "";
        WebElement webElement;
        boolean returnValue = false;

        // is this object's query stubbed out?
        if (this.isStubbed()) {
            returnValue = true;
            log("=== This text field's query is currently stubbed out. Returning value '" + returnValue + "' ===");
        } else {

            webElement = this.getWebElement();

            className = webElement.getAttribute("className");

            if (className.contains("hp-error")) {
                returnValue = true;
            }

        }
        return returnValue;
    }

    /**
     * This method is used to click on TextField and to do Key Board Enter
     * <p>
     * <b><u>NOTE:</u></b>  The text field is first checked if it exists before interacting with it.
     * If the text field does not exist, a NoSuchElementException is thrown.
     *
     * @param value        [STRING] value to enter into the text field object
     */
    public void clickAndEnter() {
        // declaring local variables
        WebElement webElement;

        // is this object's query stubbed out?
        if (this.isStubbed()) {
            log("=== This text field's query is currently stubbed out. ===");
        } else {

            webElement = getWebElement();

            // giving the browser a second to catch up with the web driver
            //sleep(1, TimeUnit.SECONDS);
            webElement.click();
            sleep(5, TimeUnit.SECONDS);
            webElement.sendKeys(Keys.ENTER);


        }
    }
}

