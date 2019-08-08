/*
 * © Copyright 2002-2015 Hewlett Packard Enterprise Development LP
 */

package Elements;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import static Helpers.Utility.log;

/**
 * This class is used to interact with text web elements listed on the web page.
 *
 * Your selenium locator should select a tag that contains text, such as <div>, <span>, or <label>, that isn't used as
 * a label for another web element, such as a radio button or checkbox.
 *
 * Created by stauffel on 12/10/13.
 */
public class Text extends BaseElement {

    // - - - - - Class attributes - - - - -



    // - - - - - Constructor - - - - -

    /**
     * Constructor used to reference this class.
     *
     * @param locator      the Selenium {@link By locator} for locating this web element
     */
    public Text (By locator) {
        // setting this classes locator value for this specific text
        super(locator);
    }


    // - - - - - Class Methods - - - - -


    /**
     * This method returns the actual string text of the text object.
     * <p>
     * <b><u>NOTE:</u></b>  The text element is first checked if it exists before interacting with it.
     * If the text element does not exist, a NoSuchElementException is thrown.
     *
     *  @return            a String value representing the actual text in this web element.
     */
    public String getText () {
        // declaring local variables
        WebElement webElement;
        String returnValue = "";

        if (this.isStubbed()) {
            returnValue = "VALUE";
            log("=== This text's query is currently stubbed out. Returning value '" + returnValue + "' ===");
        } else {

            webElement = getWebElement();
            returnValue = webElement.getText().trim();

            if (returnValue.equals("")) {
               // returnValue = webElement.getAttribute("value");  // CRAIG
                returnValue = this.waitWebElementValueNotNull (webElement);  // CRAIG

                if(returnValue == null) {
                    log("No text was found in this text web element as it lacks both text within the web element and a value attribute.");
                }
            }
        }

        return returnValue;
    }

    private String waitWebElementValueNotNull (WebElement webElement)
    {
        String returnValue = null;

        int timeOutSeconds = 120; //TODO

        String title;


        // declaring local variables
        boolean itemFound = false;
        boolean done = false;
        long startTime;
        int elapsedTime;
        startTime = System.nanoTime();

        // set start time
        while (done == false) {

            returnValue = webElement.getAttribute("value");

                if (returnValue != null) {
                    // good
                    itemFound = true;
                    done = true;
                }



            if (itemFound == false) {

                // get elapsed Time
                elapsedTime = (int) TimeUnit.SECONDS.convert((System.nanoTime() - startTime), TimeUnit.NANOSECONDS);

                // If it has reached the timeout, stop checking; otherwise sleep and try again
                if (elapsedTime >= timeOutSeconds) {
                    done = true;
                    break;
                } else {

                }
            }
        }


        return returnValue;

    }

    /**
     * Used to return multiple lines of text.
     * @return  String Array containing the multiple lines of text.
     */

    public String[] getMultiLineText(){
        ArrayList <String> returnList = new ArrayList();
        List<WebElement> listElements;
        String text;
        String[] returnValue;

        if (isStubbed()) {
            returnValue =new String[] {"One", "Two"};
            log("=== This text locator is currently stubbed out.  Returning a String array with values 'One', 'Two'. ===");
        } else {
            // getting the text web element
            WebElement element = getWebElement();
            text = element.getText();
            returnValue = text.split("\n");
        }
        return (returnValue);
    }


    /**
     * click on a text label.  Primarily used now to take focus away from another object in order
     * to close its tooltip text which can block other components
     * @author Craig Yara
     */
    public void click ()
    {
        WebElement webElement;
        webElement = getWebElement();
        webElement.click();
    }

    /**
     * This method is added to wait for element to appear on ui with expected Text
     * @param timeoutInSeconds
     * @param expectedText
     * @return
     * @author NileshG
     */
    public boolean verifyElememtText(int timeoutInSeconds, String expectedText) {
        // find this web element using the locator

            return this.waitForElementTextToBe(this.getLocator(), timeoutInSeconds, expectedText);

    }

}

