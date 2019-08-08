/*
 * © Copyright 2002-2015 Hewlett Packard Enterprise Development LP
 */

package Elements;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static Elements.Globals.browser;
import static Helpers.Utility.log;
import static Helpers.Utility.sleep;
import static Helpers.Utility.warning;


/**
 * Created by pacet on 9/21/2015.
 */
public class MasterGrid extends BaseElement {

    // - - - - - Class attributes - - - - -
    protected final int MAXRETRIES = 5;
    // Set below to true to log the method execution time for some Table methods.
    private final boolean LOG_PERFORMANCE = false;

    //Load More Link declaration
    private final Button loadMoreBelowLink = new Button(By.className("hp-master-load-more"),"Load more");

    private final Button loadMoreAboveLink = new Button(By.className("hp-master-load-more-above"),"Load more");

    // - - - - - Constructor - - - - -

    /**
     * Constructor used to reference this class.
     *
     * @param  locator     the Selenium {@link By locator} for locating this web element
     */
    public MasterGrid (By locator) {
        super(locator);
    }


    /**
     * This method returns the number of rows in the table.
     * <p>
     * <b><u>NOTE:</u></b>  The table is first checked if it exists before interacting with it.
     * If the table does not exist or is not displayed, a {@link org.openqa.selenium.NoSuchElementException} will br thrown.
     *
     * @return  an integer representing the number of rows within the table.
     */
    public int getNumberOfResources() {
        if (this.isStubbed()) {
            log("=== The table's table query is currently stubbed out.  Returning value '0' ===");
            return 0;
        }
        else {
            // If the table refreshed while we are getting values, then a stale element is being thrown. To get around
            // this, catch the stale element and try again.
            int retries = MAXRETRIES;

            while (true) {
                try {
                    return getResources().size();
                }
                catch(StaleElementReferenceException e){
                    retries--;

                    // have we tried the maximum retries?
                    if (retries == 0) {
                        warning("ERROR!  StaleElementReference Exception - " + e.getMessage());
                        e.printStackTrace();
                        throw e;
                    }
                }
            }
        }
    }

    public String getResourceName(int index) {
        WebElement resource = getResource(index);
        return resource.findElement(By.className("hp-master-item-name")).getText();
    }

    public String getResourceStatus(int index) {
        WebElement resource = getResource(index);
        WebElement status = resource.findElement(By.cssSelector("header .hp-status"));
        String[] classes = ElementUtil.getCSSClasses(status);
        for (int i = 0; i < classes.length; ++i) {
            if (classes[i].startsWith("hp-status-")) {
                return classes[i];
            }
        }
        return null;
    }

    public void selectResource(int index) {
        int retries = MAXRETRIES;
        while (true) {
            try {
                WebElement resource = getResource(index);
                Actions builder = new Actions(browser.driver);
                builder.moveToElement(resource, 0, 0);
                builder.click();
                Action action = builder.build();
                action.perform();

                // Give the UI 1 second to process this action.
                sleep(1, TimeUnit.SECONDS);

                break;
            }
            catch(StaleElementReferenceException e){
                --retries;
                if (retries == 0) {
                    warning("ERROR!  StaleElementReference Exception - " + e.getMessage());
                    e.printStackTrace();
                    throw e;
                }
            }
        }
    }

    public void selectResource(String name) {
        int retries = MAXRETRIES;
        while (true) {
            try {
                WebElement resource = getResource(name);
                Actions builder = new Actions(browser.driver);
                builder.moveToElement(resource, 0, 0);
                builder.click();
                Action action = builder.build();
                action.perform();

                // Give the UI 1 second to process this action.
                sleep(1, TimeUnit.SECONDS);

                break;
            }
            catch(StaleElementReferenceException e){
                --retries;
                if (retries == 0) {
                    warning("ERROR!  StaleElementReference Exception - " + e.getMessage());
                    e.printStackTrace();
                    throw e;
                }
            }
        }
    }

    /**
     * This method checks to see if the resource at the specified index in the master grid is selected.
     *
     * @param rowIndex int  The resource index being checked
     * @return boolean      Indicates whether the resource is selected.
     */
    public boolean isResourceSelected (int index) {
        if (this.isStubbed()) {
            log("MasterGrid::isResourceSelected === The master grid's query is currently stubbed out. ===");
            return false;
        }
        else {
            int retries = MAXRETRIES;
            while (true) {
                try {
                    WebElement row = getResource(index);
                    return ElementUtil.hasCSSClass(row, "hp-selected");
                }
                catch (StaleElementReferenceException e) {
                    --retries;
                    if (retries == 0) {
                        warning("ERROR!  StaleElementReference Exception - " + e.getMessage());
                        e.printStackTrace();
                        throw e;
                    }
                }
            }
        }
    }

    public boolean isResourceSelected (String name) {
        if (this.isStubbed()) {
            log("MasterGrid::isResourceSelected === The master grid's query is currently stubbed out. ===");
            return false;
        }
        else {
            int retries = MAXRETRIES;
            while (true) {
                try {
                    WebElement row = getResource(name);
                    return ElementUtil.hasCSSClass(row, "hp-selected");
                }
                catch (StaleElementReferenceException e) {
                    --retries;
                    if (retries == 0) {
                        warning("ERROR!  StaleElementReference Exception - " + e.getMessage());
                        e.printStackTrace();
                        throw e;
                    }
                }
            }
        }
    }

    public int indexOfSummaryItem(int resourceIndex, String text) {
        WebElement resource = getResource(resourceIndex);
        List<WebElement> summaryItems = resource.findElements(
                By.cssSelector(".hp-master-item-summary-items li"));
        for (int i = 0; i < summaryItems.size(); ++i) {
            if (summaryItems.get(i).getText().contains(text)) {
                return i;
            }
        }
        return -1;
    }

    public boolean summaryItemIsEmpty(int resourceIndex, String className) {
        WebElement resource = getResource(resourceIndex);
        String selector = String.format(".hp-master-item-summary-items li.%s", className);
        WebElement summaryItem = resource.findElement(By.cssSelector(selector));
        return summaryItem.getText() == null ;
    }

    public boolean summaryItemIsLink(int resourceIndex, int summaryItemIndex) {
        WebElement resource = getResource(resourceIndex);
        WebElement linkElement = resource.findElement(By.tagName("a"));
        return linkElement != null;
    }

    public boolean isItemAppearinGrid(String resourceName, int timeOutSeconds) {
        if (this.isStubbed()) {
            log("MasterGrid::isItemAppearinGrid === The master grid's query is currently stubbed out. ===");
            return true;
        }
        else {
            // set start time
            long startTime = System.nanoTime();
            WebElement resource = null;
            while (true) {
                // Is the item in the table?
                try {
                    resource = getResource(resourceName);
                }
                catch (Exception e) {

                }
                if (resource != null) {
                    return true;
                }
                else {
                    // We couldn't find it; keep checking until timeout.
                    // get elapsed Time
                    int elapsedTime = (int)TimeUnit.SECONDS.convert((System.nanoTime() - startTime), TimeUnit.NANOSECONDS);

                    // If it has reached the timeout, stop checking; otherwise sleep and try again
                    if (elapsedTime >= timeOutSeconds) {
                        return false;
                    }
                    else {
                        sleep(1, TimeUnit.SECONDS);
                    }
                }
            }
        }
    }

    /**
     * This methods finds all of the web elements that represent the resources in the master grid.
     * If the master grid does not exist or is not displayed, it will keep trying until the default
     * timeout has been reached.
     * <p>
     * <b>Note:</b> This method does not check whether the locator of this web element is stubbed out or not.
     * Thus, it is the responsibility of the caller NOT to call this method if the locator is stubbed out.
     *
     * @return  a list of {@link org.openqa.selenium.WebElement} instances representing the resources in the master grid
     * @throws org.openqa.selenium.NoSuchElementException if the table web element does not exist and/or is not
     * displayed after timeout
     */
    protected List<WebElement> getResources() {
        // Retrieve the web element for the master grid.
        WebElement tableElement = getWebElement();
        // Get the individual li elements that make up the list of resources.
        return tableElement.findElements(By.className("hp-master-grid-item"));
    }

    /**
     * This methods finds web element that represents the resource at the specified index in the master grid.
     * If the master grid does not exist or is not displayed, it will keep trying until the default
     * timeout has been reached.
     * <p>
     * <b>Note:</b> This method does not check whether the locator of this web element is stubbed out or not.
     * Thus, it is the responsibility of the caller NOT to call this method if the locator is stubbed out.
     *
     * @return  a {@link org.openqa.selenium.WebElement} instance representing the resource in the master grid
     * @throws org.openqa.selenium.NoSuchElementException if the master grid web element does not exist and/or is not
     * displayed after timeout
     */
    protected WebElement getResource(int index) {
        return getResources().get(index);
    }

    protected WebElement getResource(String name) {
        for (WebElement resource : getResources()) {
            String resourceName = resource.findElement(By.className("hp-master-item-name")).getText();
            if (resourceName.equals(name)) {
                return resource;
            }
        }
        return null;
    }
}
