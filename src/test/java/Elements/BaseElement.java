/*
 * © Copyright 2002-2016 Hewlett Packard Enterprise Development LP
 */

package Elements;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.pagefactory.ByAll;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

import java.util.concurrent.TimeUnit;

import static Elements.Globals.ELEMENTTIMEOUT;
import static Elements.Globals.browser;
import static Helpers.Utility.log;


/**
 * This class is the base class for most web element classes.  It includes methods that are common for web elements classes.
 * Created by stauffel on 12/10/13.
 */
public class BaseElement {

    // - - - - - Class Attributes - - - - -

    private By locator;

    protected static final String PRESENT = "present";
    protected static final String VISIBLE = "visible";
    protected static final String CLICKABLE = "clickable";

    // - - - - - Constructors - - - - -

    /**
     * Constructor to be used by a subclass.
     *
     * @param  locator  the Selenium {@link By locator} for locating this web element<br/>
     *                  To define a web element with an incomplete locator, use a locator that contains
     *                  the string "STUBBED", e.g., <code>By.id("STUBBED")</code>.<br/>
     * <b><u>NOTE:</u></b>  If a web element cannot be located by a simple locator,
     * you can chain multiple locators using the Selenium {@link ByAll} and/or {@link ByChained} classes.
     *
     * @throws NullPointerException if the specified <code>locator</code> is <code>null</code>
     */
    protected BaseElement (By locator) {
        if (locator == null) {
            throw new NullPointerException("The locator cannot be null.");
        }
        this.locator = locator;
    }

    // - - - - - Class Methods - - - - -

    /**
     * This method returns the name of the web element.
     * It is the simple class name of the most derived web element class.
     * @return the name of the web element
     */
    public String getName() {
        return getClass().getSimpleName();
    }

    /**
     * This method returns the locator for this web element.
     * @return  the locator for this web element
     */
    public By getLocator () {
        return this.locator;
    }

    /**
     * This method is to be used by a subclass to check whether the web element's locator is stubbed out or not.
     * A web element's locator is considered stubbed out if it contains the string "STUBBED".
     * @return  <code>true</code> if the web element's locator is stubbed out; <code>false</code> otherwise
     */
    protected boolean isStubbed() {
        return this.locator.toString().contains("STUBBED");
    }


    /**
     * This method determines if the web element is enabled or not.  If the web element is enabled, a value of 'true'
     * will be returned.  Otherwise, a value of 'false' will be returned.
     *
     * @return       a boolean 'true' if the web element is enabled, 'false' otherwise.
     */
    public boolean isEnabled () {
        // declaring local variables
        boolean isEnabled = false;

        // first, check to see if the web element's locator is stubbed -- if so, then print out a message and return true
        if (isStubbed()) {
            isEnabled = true;
            log("=== This web element's locator is currently stubbed out. Return value = '" + isEnabled + "' ===");
        } else {
            // getting the web element with the default timeout
            WebElement webElement = getWebElement(VISIBLE);
            isEnabled = isWebElementEnabled(webElement);
        }

        return isEnabled;
    }

    private <T extends WebElement> boolean isWebElementEnabled(T webElement) {
        boolean isEnabled = true;

        // get the string of class name list
        String className = webElement.getAttribute("className");
        String disabled = webElement.getAttribute("disabled");

        if (disabled == null) {
            disabled = "n/a";
        }

        // Need to implement this for Piano
        if (className.contains("x-item-disabled") || disabled.equals("true") || className.contains("hp-disabled")) {
            isEnabled = false;
        }
        return isEnabled;
    }

    public boolean isDisplayed() {
        WebElement el = this.getWebElement(this.VISIBLE);
        if (el != null) {
            return el.isDisplayed();
        }
        else {
            return false;
        }
    }

    public boolean isHidden(int timeout) {
        WebElement el = this.getWebElement(this.VISIBLE, timeout);
        if (el != null) {
            return !el.isDisplayed();
        } else {
            return true;
        }

    }


    /**
     * This method checks to see if the web element exists on the screen.  If the web element does not exist, this method
     * keeps checking for it's existence until:<br>
     * 1 - the web element exists and displayed<br>
     * 2 - the global ELEMENTTIMEOUT has been reached.
     * <p>
     * If the web element exists and is displayed before the global elementTimeOut has been reached, this method returns
     * a boolean value of 'true'.
     * </p>
     * <p>
     * If the web element does not exist and/or is not displayed, and the global ELEMENTTIMEOUT has been reached, this method
     * returns a boolean value of 'false'.
     * </p>
     * <p>
     * <b><u>NOTE:</u></b>  It's important to note that just because the web element exists, doesn't always mean it's enabled.
     * For example, if the web element is a button, the button may exist, but the button could be disabled.
     *
     * </p>
     * @return       a boolean value 'true' if the web element exists, 'false' otherwise.
     * */
    public boolean exists () {
        return exists(ELEMENTTIMEOUT);
    }

    /**
     * This method checks to see if the web element exists on the screen.  If the web element does not exist, this method
     * keeps checking for it's existence until:<br>
     * 1 - the web element exists and displayed<br>
     * 2 - the timeOutSeconds has been reached.
     * <p>
     * If the web element exists and is displayed before the timeOutSeconds has been reached, this method returns
     * a boolean value of 'true'.
     * </p>
     * <p>
     * If the web element does not exist and/or is not displayed, and the timeOutSeconds has been reached, this method
     * returns a boolean value of 'false'.
     * </p>
     * <p>
     * <b><u>NOTE:</u></b>  It's important to note that just because the web element exists, doesn't always mean it's enabled.
     * For example, if the web element is a button, the button may exist, but the button could be disabled.
     * </p>
     *
     * @param timeOutSeconds     [INT] the maximum number of seconds to wait before timing out
     * @return                   a boolean value 'true' if the web element exists, 'false' otherwise.
     */
    public boolean exists (int timeOutSeconds) {
        // declaring local variables
        boolean returnValue = false;

        // first, check to see if the web element's locator is stubbed -- if so, then print out a message and return true
        if (isStubbed()) {
            returnValue = true;
            log("=== This web element's locator is currently stubbed out. ===");
        } else {
            try{
                // getting the web element with the specified timeout
                getWebElement(timeOutSeconds);
                // no exception; the web element was found
                returnValue = true;
            } catch (NoSuchElementException e) {
                // cannot find the web element
                returnValue = false;
            }
        }

        return returnValue;
    }


    /**
     * This method is to be used by a subclass to get the {@link WebElement} of this web element
     * using the default timeout.  It is the same as {@link #getWebElement(int)} except that it
     * uses the default timeout value defined by {@link Globals.ELEMENTTIMEOUT}.
     * @see #getWebElement(int)
     */
    protected WebElement getWebElement () {
        return getWebElement(ELEMENTTIMEOUT);
    }

    protected WebElement getWebElement (String expectedElementState) {
        return getWebElement(expectedElementState, ELEMENTTIMEOUT);
    }

    /**
     * CRAIG UPDATE:  This method will wait until the element is clickable.
     *
     * This method is to be used by a subclass to get the {@link WebElement} of this web element
     * using the specified timeout.
     * It firstly checks to see if the web element exists and is displayed on the screen.
     * If it does not exist or is not displayed, it keeps checking until:<br>
     * 1 - the web element exists and is displayed or<br>
     * 2 - the timeOutSeconds has been reached.
     * <p>
     * If the web element exists and is displayed before the timeOutSeconds, this method returns the
     * {@link WebElement} of this web element.
     * <p>
     * If the web element does not exist and/or is not displayed, and the timeOutSeconds has been reached,
     * this method throws a {@link NoSuchElementException}.
     * <p>
     * <b>Note:</b> This method does not check whether the locator of this web element is stubbed out or not.
     * Thus, it is the responsibility of the caller NOT to call this method if the locator is stubbed out.
     *
     * @param   timeOutSeconds     [INT] the maximum number of seconds to wait before timing out
     * @return  a {@link WebElement} representing the located web element
     * @throws NoSuchElementException if the web element does not exist and/or is not displayed after timeout
     */
    protected WebElement getWebElement (int timeOutSeconds) {
       return findElement(timeOutSeconds);
    }

    protected WebElement getWebElement (String expectedElementState, int timeOutSeconds) {
        return findElement(expectedElementState, timeOutSeconds);
    }


    /**
     * This method is called by the {@link #getWebElement(int)} method to find the {@link WebElement} of this web element.
     * <p>
     * If the web element exists, this method returns the {@link WebElement} of this web element.
     * If the web element does not exist, this method throws a {@link NoSuchElementException}.
     * <p>
     * A subclass may override this method to provide its own implementation of finding the {@link WebElement}.
     * <p>
     * Note that this method does not need to check whether the found {@link WebElement} is displayed or not;
     * the {@link #getWebElement(int)} method will do that. In addition, because the {@link #exists(int)} method
     * is implemented using the  {@link #getWebElement(int)} method, it will indirectly call this method to find
     * the {@link WebElement}. Thus, a subclass may not need to override the {@link #exists(int)} method to provide
     * its own way of checking whether the web element exists or not.
     * <p>
     * <b>Note:</b> This method does not check whether the locator of this web element is stubbed out or not.
     * Thus, it is the responsibility of the caller NOT to call this method if the locator is stubbed out.
     *
     * @return  a {@link WebElement} representing the located web element
     * @throws NoSuchElementException if the web element does not exist
     */
    protected WebElement findElement () {
        // find this web element using the locator
        return browser.findElement(this.locator);
    }

    /**
     * This is a modified version of findElement which redirects the call to our new "waitForElement" function, which
     * waits for the element to be "clickable". This *should* guarentee that the object is not ONLY in the
     * DOM tree, but that it is visibible and ready.
     *
     * This method is called by the {@link #getWebElement(int)} method to find the {@link WebElement} of this web element.
     * <p>
     * If the web element exists, this method returns the {@link WebElement} of this web element.
     * If the web element does not exist, this method throws a {@link NoSuchElementException}.
     * <p>
     * A subclass may override this method to provide its own implementation of finding the {@link WebElement}.
     * <p>
     * Note that this method does not need to check whether the found {@link WebElement} is displayed or not;
     * the {@link #getWebElement(int)} method will do that. In addition, because the {@link #exists(int)} method
     * is implemented using the  {@link #getWebElement(int)} method, it will indirectly call this method to find
     * the {@link WebElement}. Thus, a subclass may not need to override the {@link #exists(int)} method to provide
     * its own way of checking whether the web element exists or not.
     * <p>
     * <b>Note:</b> This method does not check whether the locator of this web element is stubbed out or not.
     * Thus, it is the responsibility of the caller NOT to call this method if the locator is stubbed out.
     *
     * @author Craig Yara
     * @return  a {@link WebElement} representing the located web element
     * @throws NoSuchElementException if the web element does not exist
     */
    protected WebElement findElement (int timeoutInSeconds) {
        // find this web element using the locator
        return waitForElement(this.getLocator(), timeoutInSeconds);
    }

    protected WebElement findElement (String expectedElementState, int timeoutInSeconds) {
        // find this web element using the locator
        return this.waitForElement(this.getLocator(), timeoutInSeconds, expectedElementState);
    }

    /**
     * this function waits for the given element until it is present in the UI.
     * @author Craig Yara
     * @param by
     * @param timeoutInSeconds
     * @return
     */
    protected WebElement waitForElement(By by, int timeoutInSeconds) {
        return waitForElement(by, timeoutInSeconds, null);
    }


    protected WebElement waitForElement(By by, int timeoutInSeconds, String expectedElementState) {
        try {
            WebDriverWait wait_givenTimeout = new WebDriverWait(browser.driver, timeoutInSeconds);

            // wait if any persence of the element is found in the DOM
            WebElement element = wait_givenTimeout.until(ExpectedConditions.presenceOfElementLocated(by));

            if (!PRESENT.equals(expectedElementState)) {
                if (isWebElementEnabled(element)) {
                    // wait and verify that the item is visible and clickable in the UI
                    element = wait_givenTimeout.until(ExpectedConditions.elementToBeClickable(by));
                } else {
                    // if disabled, wait and verify that the element is visable in the DOM
                    element = wait_givenTimeout.until(ExpectedConditions.visibilityOfElementLocated(by));
                }
            }
            // if expected state is PRESENT return presence of element (may be disabled)
            return element;

        } catch (TimeoutException e) {
            String elemClassName = this.getClass().getSimpleName();
            String errMsg = "BaseElement:waitForElement() - timed out (" + timeoutInSeconds + " seconds) in waiting for "
                    + elemClassName + "(" + by.toString() + ") ";
            if (expectedElementState == CLICKABLE) {
                errMsg += "to be present and clickable.";
            } else {
                errMsg += "to be present.";
            }
            throw new NoSuchElementException(errMsg);
        } catch (Exception e) {
            throw new NoSuchElementException("BaseElement:waitForElement() - exception occurred:", e);
        }
    }

    /**
     * This method moves the cursor to the middle of the web element and scrolling it into view, if need be.
     */
    public void moveCursorTo() {
        // declaring local variables
        WebElement webElement;
        Actions builder = null;

        // first, check to see if the web element's locator is stubbed -- if so, then print out a message
        if (isStubbed()) {
            log("=== This web element's locator is currently stubbed out. The cursor will not be moved. ===");
        } else {
            // getting the web element with the default timeout
            webElement = getWebElement();

            // setting up the cursor movement action
            builder = new Actions(browser.driver);
            builder.moveToElement(webElement);

            // building and executing the cursor movement action
            Action action = builder.build();
            action.perform();
        }
    }

    /**
     * This method moves the cursor to the middle of the web element and scrolling it into view, if need be.
     */
    public void moveCursorTo_withOffset(int xOffset,int yOffset) {
        // declaring local variables
        WebElement webElement;
        Actions builder = null;

        // first, check to see if the web element's locator is stubbed -- if so, then print out a message
        if (isStubbed()) {
            log("=== This web element's locator is currently stubbed out. The cursor will not be moved. ===");
        } else {
            // getting the web element with the default timeout
            webElement = getWebElement();

            // setting up the cursor movement action
            builder = new Actions(browser.driver);
            builder.moveToElement(webElement,xOffset,yOffset);

            // building and executing the cursor movement action
            Action action = builder.build();
            action.perform();
        }
    }

    /**
     * This method checks if this web element contains a particular CSS class.
     * It gets the CSS classes from the <code>class</code> attribute of the web element.
     *
     * @param  cssClassName  name of the CSS class to check
     * @return <code>true</code> if the specified web element has the CSS class specified by <code>cssClassName</code>;
     *         <code>false</code> otherwise
     */
    public boolean hasCSSClass(String cssClassName) {
        // first, check to see if the web element's locator is stubbed -- if so, then print out a message and return true
        if (isStubbed()) {
            log("=== This web element's locator is currently stubbed out. ===");
            return true;
        } else {
            return ElementUtil.hasCSSClass(getWebElement(), cssClassName);
        }
    }


    protected void clickWebElement (WebElement webElement) {
        // declaring local variables
        boolean itemFound = false;
        boolean done = false;
        long startTime;
        int elapsedTime = 0;
        startTime = System.nanoTime();

        int timeOutSeconds = 30; // TODO CRAIG


        // set start time
        while (done == false) {

            try {
                webElement.click();
                done = true;
                itemFound = true;
            } catch (Exception e) {

            }


            if (itemFound == false) {

                // get elapsed Time
                elapsedTime = (int) TimeUnit.SECONDS.convert((System.nanoTime() - startTime), TimeUnit.NANOSECONDS);

                // If it has reached the timeout, stop checking; otherwise sleep and try again
                if (elapsedTime >= timeOutSeconds) {
                    done = true;
                    break;
                } else {
                    // sleep(2, TimeUnit.SECONDS);
                }
            }
        }

        if (itemFound == false) {

            throw new NoSuchElementException("*** FAILURE:  clickWebElement():  clicking on element failed");

        }
    }

    /**	444
     * This method is added to wait for element to appear on ui with expected Text	445
     * @param i	446
     * @param timeoutInSeconds	447
     * @param expectedElementState	448
     * @return	449
     * Created bbuy NileshG on 08/25/2015	450
     */
    public Boolean waitForElementTextToBe(By i, int timeoutInSeconds, String expectedElementText) {
        boolean gotElementWithText =false;
        try {
            WebDriverWait wait_givenTimeout = new WebDriverWait(browser.driver, timeoutInSeconds);

            // now wait and verify that the item is visible and clickable in the UI
            return wait_givenTimeout.until(ExpectedConditions.textToBePresentInElement(i, expectedElementText));
        }catch (org.openqa.selenium.TimeoutException e){
            return false;                // returned false , so as to handle this boolean value in helper methods for the case where expected text not got after timeout.
        }
    }


    /**
     * This method moves the cursor to web element with CSS using jQuery
     * Created buy AravindV on 01/30/2018
     */
    public void moveCursorUsingjQuery() {

        String cssLocator = locator.toString().split(":")[1].trim().replaceAll("'", "\\\\\"");
        // first, check to see if the web element's locator is stubbed -- if so, then print out a message
        if (isStubbed()) {
            log("=== This web element's locator is currently stubbed out. The cursor will not be moved. ===");
        } else {
            // getting the web element with the default timeout
            ((JavascriptExecutor) browser.driver).executeScript("$('" + cssLocator + "')[0].scrollIntoView(true);$('" + cssLocator + "').mouseenter();");
        }
    }

}
