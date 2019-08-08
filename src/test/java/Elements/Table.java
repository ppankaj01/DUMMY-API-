/*
 * © Copyright 2002-2015 Hewlett Packard Enterprise Development LP
 */

package Elements;

import com.google.common.base.Stopwatch;

import org.openqa.selenium.By;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static Elements.Globals.browser;
import static Helpers.Utility.*;


// IMPLEMENTATION NOTES:
//
// A table typically has an HTML structure like one of the two structures below:
//
// Structure #1:
// <div id="example-table_wrapper" class="dataTables_wrapper">
//   <table id="example-table" class="dataTable">
//     <thead>
//       ...
//     </thead>
//     <tbody>
//       ...
//     </tbody>
//   </div>
// </div>
//
// Structure #2:
// <div id="example-table_wrapper" class="dataTables_wrapper">
//   <div class="dataTables_scroll">
//     <div class="dataTables_scrollHead">
//       <div class="dataTables_scrollHeadInner">
//         <table class="dataTable">
//           <thead>...</thead>
//         </table>
//       </div>
//     </div>
//     <div class="dataTables_scrollBody">
//       <table class="dataTable">
//         <thead>...</thead>
//         <tbody>...</tbody>
//       </table>
//     </div>
//   </div>
// </div>
//

/**
 * This class is used to interact with table web elements listed on the web page.
 * In particular, it knows how to handle tables rendered by jQuery and Piano.
 *
 * Your selenium locator should uniquely match one of the following elements from the examples in this class' code.
 * Structure #1 - Element: <table id="example-table" class="dataTable">
 * Structure #2 - Element: <div id="example-table_wrapper" class="dataTables_wrapper">
 *
 * Created by stauffel on 12/11/13.
 */
public class Table extends BaseElement {

    // - - - - - Class attributes - - - - -
    protected final int MAXRETRIES = 5;
    // Set below to true to log the method execution time for some Table methods.
    private final boolean LOG_PERFORMANCE = false;

    //Load More Link declaration
    private final Button loadMoreBelowLink = new Button(By.className("hp-master-load-more"),"Load more");

    private final Button loadMoreAboveLink = new Button(By.className("hp-master-load-more-above"),"Load more");

    private final Button topLink = new Button(By.className("hp-master-load-top"),"Top");


    // - - - - - Constructor - - - - -

    /**
     * Constructor used to reference this class.
     *
     * @param  locator     the Selenium {@link By locator} for locating this web element
     */
    public Table (By locator) {
        super(locator);
    }


    // - - - - - Class Methods - - - - -

    /**
     * This method returns the number of rows in the table.
     * <p>
     * <b><u>NOTE:</u></b>  The table is first checked if it exists before interacting with it.
     * If the table does not exist or is not displayed, a {@link NoSuchElementException} will br thrown.
     *
     * @return  an integer representing the number of rows within the table.
     */
    public int getRowCount() {
        // declaring local variables
        int returnValue = 0;

        if (this.isStubbed()) {
            log("=== The table's table query is currently stubbed out.  Returning value '" + returnValue + "' ===");
        } else {
            // if the table refreshed while we are getting values, then a stale element is being thrown. To get around
            //     this will catch the stale element and try again
            boolean done = false;
            int retries = 0;

            while(!done) {
                try {
                    // getting the table web element with the default timeout
                    // and then get the row elements and count the size
                    returnValue = getRowElements().size();

                    done = true;
                } catch(StaleElementReferenceException e){
                    retries++;

                    // have we tried the maximum retries?
                    if (retries == MAXRETRIES) {
                        done = true;

                        warning("ERROR!  StaleElementReference Exception - " + e.getMessage());
                        e.printStackTrace();
                        throw e;
                    }
                }
            }
        }

        return returnValue;
    }

    /***
     * This methods finds all of the web elements that represent the rows of the table.
     * If the table does not exist or is not displayed, it will keep trying until the default timeout has been reached.
     * <p>
     * <b>Note:</b> This method does not check whether the locator of this web element is stubbed out or not.
     * Thus, it is the responsibility of the caller NOT to call this method if the locator is stubbed out.
     *
     * @return  a list of {@link WebElement} instances representing the rows of the table
     * @throws NoSuchElementException if the table web element does not exist and/or is not displayed after timeout
     */
    protected List<WebElement> getRowElements() {
        WebElement tableElement = null;

        if (ElementUtil.hasCSSClass(getWebElement(),"dataTables_wrapper")){
            tableElement = getWebElement().findElement(By.cssSelector(".dataTables_scrollBody > table"));
        }
        else{
            // getting the table web element with the default timeout
            tableElement = getWebElement();
        }

        // get the row web elements of the table
        List<WebElement> rowElements = tableElement.findElements(By.xpath("tbody/tr"));

        // Check if the table has any rows
        if (rowElements.size() == 0) {
            log("No rowElements in table!");
            return rowElements;
        }

        // looking at the first element and the last element to make sure there is not a row that has
        // the "Load More" link . . as this is not part of the table.
        String className = rowElements.get(0).getAttribute("className");

        // does the first row element contain the Load More link?
        if ( (className.equals("hp-master-table-control")) || (className.equals("hp-master-load")) || (className.equals("hp-master-table-control hp-master-load-more-above")) ) {
            rowElements.remove(0);
        }

        // does the last row element contain the Load More link?
        className = rowElements.get(rowElements.size()-1).getAttribute("className");
        if ( (className.equals("hp-master-table-control")) || (className.equals("hp-master-load")) || (className.equals("hp-master-table-control hp-master-load-more")) ) {
            rowElements.remove(rowElements.size()-1);
        }


        return rowElements;
    }

    /**
     * This method finds the web element representing the row of the table identified by the specified index.
     * If the table does not exist or is not displayed, it will keep trying until the default timeout has been reached.
     * <p>
     * <b>Note:</b> This method does not check whether the locator of this web element is stubbed out or not.
     * Thus, it is the responsibility of the caller NOT to call this method if the locator is stubbed out.
     *
     * @param rowIndex     [INT] representing the row.  Rows start with index 1.
     * @return  a {@link WebElement} representing the row of the table identified by the specified index
     * @throws NoSuchElementException if the table web element does not exist and/or is not displayed after timeout
     */
    protected WebElement getRowElement(int rowIndex) {
        // getting the table web element with the default timeout
        List<WebElement> rowElements = getRowElements();

        //Verifying rowIndex is valid
        if((rowIndex < 1) || (rowIndex > rowElements.size())){
            throw new NoSuchElementException("No such row index, '" + rowIndex + "', within the row elements - '" + rowElements.size() + "'");
        }

        // get the web element of the row of the specified index in the table.
        //return tableElement.findElement(By.xpath("tbody/tr[" + rowIndex + "]"));
        return rowElements.get(rowIndex-1);
    }


    /**
     * This method determines whether or not a table has finished loading.
     *
     * @return  true, if the table has finished loading; false otherwise
     */
    public boolean isLoaded() {
        boolean returnValue = true;
        WebElement tableElement;

        // If the table is a "scroll table"
        if (ElementUtil.hasCSSClass(getWebElement(), "dataTables_wrapper")){
            // Obtains the table web element with the default timeout
            tableElement = getWebElement().findElement(By.cssSelector(".dataTables_scrollHeadInner > table"));
        }
        else{
            // Obtains the table web element with the default timeout
            tableElement = getWebElement();
        }

        // Check the CSS classes of this web element to determine if this table is still loading
        if (ElementUtil.hasCSSClass(tableElement, "hp-changing")) {
            returnValue = false;
        }

        return returnValue;
    }

    /**
     * waits until the table is loaded
     * @author Craig Yara
     * @return
     */
    public boolean waitForLoaded ()
    {
        // declaring local variables
        boolean itemFound = false;
        boolean done = false;
        long startTime;
        Stopwatch stopwatch = null;
        int timeOutSeconds = 120;

        // give the table a short moment to START to redraw in case it hasn't even started doing that yet.
        // this can potentially happen the moment an action is CLICKED that would invoke a table redarw
        sleep(2, TimeUnit.SECONDS);

        if (LOG_PERFORMANCE) {
            stopwatch = Stopwatch.createStarted();
        }

        if (this.isStubbed()) {
            log("Table::IsItemAppearinTable === The table's table query is currently stubbed out. ===");
            itemFound = true;
        }
        else {
            // set start time
            startTime = System.nanoTime();
            int rowIndex = 0;
            while (!done) {
                // Is the item in the table?
                if (this.isLoaded()) {
                    // We found it; we're done!
                    itemFound = true;
                    done = true;
                } else {
                    // We couldn't find it; keep checking until timeout.

                    // get elapsed Time
                    int elapsedTime = (int)TimeUnit.SECONDS.convert((System.nanoTime() - startTime), TimeUnit.NANOSECONDS);

                    // If it has reached the timeout, stop checking; otherwise sleep and try again
                    if (elapsedTime >= timeOutSeconds) {
                        done = true;
                    } else {
                        sleep(1, TimeUnit.SECONDS);
                    }
                }
            }
        }

        if (LOG_PERFORMANCE) {
            stopwatch.stop();
            //log(this.getName() + "::waitForLoaded('"  + timeOutSeconds + ", " + ") took " + stopwatch);
        }
        return itemFound;
    }


    /**
     * This method returns the number of columns in the table.
     * <p>
     * <b><u>NOTE:</u></b>  The table is first checked if it exists before interacting with it.
     * If the table does not exist or is not displayed, a {@link NoSuchElementException} will br thrown.
     *
     * @return  an integer representing the number of columns within the table.
     */
    public int getColumnCount() {
        // declaring local variables
        int returnValue;

        if (this.isStubbed()) {
            returnValue = 1;
            log("Table::getColumnCount === The table's table query is currently stubbed out.  Returning value '" + returnValue + "' ===");
        } else {
            // getting the table web element with the default timeout
            // and then get its headings and count the size
            returnValue = getColumnHeadingElements().size();
        }

        return returnValue;
    }

    /**
     * This methods finds all of the web elements that represent the column headings of the table.
     * If the table does not exist or is not displayed, it will keep trying until the default timeout has been reached.
     * <p>
     * <b>Note:</b> This method does not check whether the locator of this web element is stubbed out or not.
     * Thus, it is the responsibility of the caller NOT to call this method if the locator is stubbed out.
     *
     * @return  a list of {@link WebElement} instances representing the column headings of the table
     * @throws NoSuchElementException if the table web element does not exist and/or is not displayed after timeout
     */
    protected List<WebElement> getColumnHeadingElements() {
        List<WebElement> headingElements = null;
        WebElement tableElement = null;

        if (ElementUtil.hasCSSClass(getWebElement(),"dataTables_wrapper")){
            tableElement = getWebElement().findElement(By.cssSelector(".dataTables_scrollHeadInner > table"));
        }
        else{
            // getting the table web element with the default timeout
            tableElement = getWebElement();
        }
        // Check the CSS classes of this web element to determine
        // if this is a Piano hp-master-table
        if (ElementUtil.hasCSSClass(tableElement, "hp-master-table")) {
            // This is a Piano master table. It has a structure like
            // <div class="dataTables_wrapper">
            //   <div class="dataTables_scroll">
            //     <div class="dataTables_scrollHead">
            //       <div class="dataTables_scrollHeadInner">
            //         <table class="hp-master-table dataTable">
            //           <thead>...</thead>
            //     <div class="dataTables_scrollBody">
            //       <table class="hp-master-table dataTable">
            //         <thead>...</thead>
            //         <tbody>...</tbody>
            //
            // Get the headings <td> elements from the table above this one
            headingElements = tableElement.findElements(By.xpath("../../div/div/table/thead/tr/td"));

            // if the result is empty, we may have a different table structure that has this class
            if (headingElements.size() == 0) {
                // try to get the headings another way, because we may see the table structure as
                // <table class="hp-master-table dataTabe">
                //   <thead>
                //   <tbody>
                headingElements = tableElement.findElements(By.xpath("thead/tr/td"));

                if (headingElements.size() == 0) {

                    // check one more try to see if we can get the column headings within this structure
                    // <table class="hp-master-table dataTable">
                    //   <thead>
                    //     <tr>
                    //       <th>
                    //   <tbody>
                    headingElements = tableElement.findElements(By.xpath("thead/tr/th"));
                    if (headingElements.size() == 0) {
                        warning("Warning!  This hp-master-table may not have a known table structure.  Locator: " + this.getLocator());
                    }
                }
            }
        }
        else if (ElementUtil.hasCSSClass(tableElement, "dataTable")) {
            // This is a simple Piano data table. It has a structure like
            // <div class="dataTables_wrapper">
            //   <table class="dataTable">
            //     <thead>...</thead>
            //     <tbody>...</tbody>
            //
            // Get the headings <td> elements from this table
            headingElements = tableElement.findElements(By.xpath("thead/tr/td"));

            if (headingElements.size() == 0) {
                // check one more try to see if we can get the column headings within this structure
                // <table class="hp-master-table dataTable">
                //   <thead>
                //     <tr>
                //       <th>
                //   <tbody>
                headingElements = tableElement.findElements(By.xpath("thead/tr/th"));
                if (headingElements.size() == 0) {
                    warning("Warning!  This dataTable may not have a known table structure.  Locator: " + this.getLocator());
                }
            }
        }
        else {
            // This is not a known table type. Show a warning and
            // get the headings <td> elements from this table
            warning("Warning!  This table does not have a known table structure.  Locator: " + this.getLocator());
            headingElements = tableElement.findElements(By.xpath("thead/tr/td"));
        }

        return headingElements;
    }


    /**
     * This method returns the string value of the data within a specific table cell.
     * <p>
     *
     * @param rowIndex     [INT] representing the row.  Rows start with index 1.
     * @param columnIndex  [INT] representing the column.  Columns start with index 1.
     * @return             a String value
     */
    public String getCellData (int rowIndex, int columnIndex) {
        // declaring local variables
        String returnValue = "";
        // TODO: Instead of calling getRowText() to get the text of the entire row,
        //       this method should directly get the text of that particular cell.
        String [] rowContents = this.getRowText(rowIndex);

        if (rowContents.length > 0) {
            // make sure the column Index is NOT greater than the length
            if (rowContents.length < columnIndex) {
                throw new NoSuchElementException("Table::getCellData -- column index, '" + columnIndex + "' is greater than the number of columns, '" + rowContents.length + "'.");
            } else {
                returnValue = rowContents[columnIndex-1];
            }
        } else {
            throw new NoSuchElementException("Table::getCellData -- table is empty.");
        }

        return returnValue;

        // - - - - - - - - OLD CODE - - - - - - - - -
        // calls the getData method to return a string value
        //return (String)this.getData(rowIndex, columnIndex);
    }


    /**
     * This method returns the table's "no data" text, which can appear either within the table itself or in a <div>
     * following it, if it's present.
     *
     * @return      if the text is present, the "no data" text in the table or following the table; else, an empty string
     */
    public String getNoDataText() {
        String returnValue = "";
        By divSelector = By.xpath("../div[@class='hp-associate-no-data']");
        WebElement noDataText;
        WebElement table = this.getWebElement();

        // If the table's selector points to the dataTables_wrapper div
        if (table.getAttribute("class").contains("dataTables_wrapper")) {
            divSelector = By.cssSelector(".hp-association-no-data");
        }

        // If the table has only one row
        if (this.getRowCount() == 1) {
            String cellText = this.getCellData(1, 1);
            String cellSubstring = cellText.split(" ")[0].toLowerCase();

            // If the first word of the cell text is 'no'
            if (cellSubstring.equals("no")) {
                returnValue = cellText;
            }
        }
        // If the return value is still an empty string and the "no data" div is present
        if (returnValue.equals("") && getWebElement().findElements(divSelector).size() > 0) {
            noDataText = getWebElement().findElement(divSelector);
            returnValue = noDataText.getText();
        }

        return returnValue;
    }


    /**
     * This method returns the CSS class name of the icon in a table cell.
     * It returns an empty string if the table cell does not contain a recognized icon,
     * which is an element that has CSS class "hp-status" or "hp-icon".
     *
     * @param cellElement  WebElement of a table cell, i.e., a {@code <td>} element
     * @return the CSS class name of the icon in the table cell; empty if no icon is found
     */
    private String getIconValue(WebElement cellElement){
        String returnValue = "";
        String[] classes;
        WebElement iconElement = null;
        List<WebElement> tempList = null;

        // Firstly we look for elements in the cell that have the CSS class "hp-status"
        tempList = cellElement.findElements(By.className("hp-status"));

        // If an hp-status icon element was found
        if (tempList.size() > 0) {
            iconElement = tempList.get(0);

            // get the list of classes of the icon element
            classes = ElementUtil.getCSSClasses(iconElement);

            // look for the class that starts with 'hp-status-'
            for (String clas : classes) {
                if (clas.startsWith("hp-status-")){
                    returnValue = clas;
                    break;
                }
            }
        } else {
            // Otherwise we look for elements in the cell that have the CSS class "hp-icon"
            tempList = cellElement.findElements(By.className("hp-icon"));

            // If an hp-icon element was found
            if (tempList.size() > 0) {
                iconElement = tempList.get(0);

                // Get the parent element and determine whether this is a locate/stop-locate icon or not.
                //
                // Locate icon (off):
                //   <a class="ssmc-hp-locate-icon" href="#/hw-systems/locate">
                //     <div class="hp-icon hp-uid"></div>
                //   </a>
                //
                // Stop locate icon (on):
                //   <a class="ssmc-hp-stoplocate-icon" href="#/hw-systems/stoplocate">
                //     <div class="hp-icon hp-uid hp-on"></div>
                //   </a>
                //
                WebElement parentElement = iconElement.findElement(By.xpath(".."));
                if (ElementUtil.hasCSSClass(parentElement, "ssmc-hp-locate-icon")) {
                    returnValue = "ssmc-hp-locate-icon";
                } else if (ElementUtil.hasCSSClass(parentElement, "ssmc-hp-stoplocate-icon")) {
                    returnValue = "ssmc-hp-stoplocate-icon";
                } else {
                    // This is an hp-icon element that we do not recognize; return its class attribute for investigation
                    returnValue = "Unknown icon: class=\"" + iconElement.getAttribute("class") + "\"";
                }
            }
        }

        return returnValue;
    }

    /**
     * This method returns a String array of values in each column of a specific row in the table.
     * If the item in the column is a checkbox or an image, not a text, then the value will be an empty
     * string.
     * <p>
     * <b><u>NOTE:</u></b>  The table is first checked if it exists before interacting with it.
     * If the table does not exist, an error is logged and the test will stop.
     *
     * @param rowIndex      [INT] the row number.  Rows start with index 1.
     * @return              a String array with all the values in the row
     */
    public String[] getRowText (int rowIndex) {
        // declaring local variables
        WebElement row;
        List<WebElement> columnElements;
        int columnCount;
        String[] returnValue = {};
        String iconValue;
        Stopwatch stopwatch = null;
        if (LOG_PERFORMANCE) {
            stopwatch = Stopwatch.createStarted();
        }

        if (this.isStubbed()) {
            returnValue = new String[] {"One", "Two"};
            log("Table::getRowText === The table's table query is currently stubbed out.  Returning a String array with 2 items. ===");
        } else {
            // if the table refreshed while we are getting values, then a stale element is being thrown. To get around
            //     this will catch the stale element and try again
            boolean done = false;
            int retries = 0;

            while(!done) {
                try {
                    // adding a sleep here, because sometimes when the table is refreshing, some values within the row
                    // are '--'
                    // TODO: Remove this wait to avoid the performance penalty when this method is used multiple times.
                    //       Ideally, a test should handle the wait before calling this method. However, the reality is
                    //       that we may still have many tests that rely on this wait to work.
                    sleep(1, TimeUnit.SECONDS);

                    // getting the table web element with the default timeout
                    // and then get the specified row
                    row = getRowElement(rowIndex);

                    // find the column elements of this row
                    columnElements = row.findElements(By.xpath("td"));
                    columnCount = columnElements.size();

                    returnValue = new String[columnCount];
                    for (int j = 0; j < columnCount; j++) {
                        WebElement column = columnElements.get(j);
                        returnValue[j] = column.getText();

                        //if it is an empty string, it may contain an icon.
                        if (returnValue[j].equals("")) {
                            iconValue = getIconValue(column);
                            if (!iconValue.isEmpty())
                                returnValue[j] = iconValue;
                        }

                        //if it contains 'Running' in the string, it may contain a bar chart
                        if(returnValue[j].contains("Running")) {
                            List<WebElement> progressElement = column.findElements(By.cssSelector(".hp-progress > .hp-progress-done"));

                            if(progressElement.size() == 1) {
                                // get the progress percentage of the bar chart and add it to the current string
                                String temp = progressElement.get(0).getAttribute("style").split(": ")[1];
                                String percentage = temp.substring(0, temp.length() - 1);
                                returnValue[j] += " - " + percentage;
                            }
                        }
                    }

                    done = true;
                } catch(StaleElementReferenceException e){
                    retries++;
                    if (LOG_PERFORMANCE) {
                        log(this.getName() + "::getRowText(" + rowIndex + ") reties " + stopwatch);
                    }

                    // have we tried the maximum retries?
                    if (retries == MAXRETRIES) {
                        done = true;

                        warning("ERROR!  StaleElementReference Exception - " + e.getMessage());
                        e.printStackTrace();
                        throw e;
                    }
                }
            }

        }

        if (LOG_PERFORMANCE) {
            stopwatch.stop();
            log(this.getName() + "::getRowText(" + rowIndex + ") took " + stopwatch);
        }
        return returnValue;
    }



    /**
     * This method returns a String array of values in each row of a specific column in the table.
     * If the item in the column is a checkbox or an image, not a text, then the value will be an empty
     * string.
     * <p>
     * <b><u>NOTE:</u></b>  The table is first checked if it exists before interacting with it.
     * If the table does not exist, an error will be logged and the test will stop.
     *
     * @param columnIndex   [INT] the column number.  Columns start with index 1.
     * @return              a String array with all the values in the column
     */
    public String[] getColumnText (int columnIndex) {
        // declaring local variables
        int rowCount = 0;
        String[] returnValue = {};
        Stopwatch stopwatch = null;
        if (LOG_PERFORMANCE) {
            stopwatch = Stopwatch.createStarted();
        }

        if (this.isStubbed()) {
            returnValue = new String[] {"One", "Two"};
            log("Table::getColumnText === The table's table query is currently stubbed out.  Returning a String array with two items. ===");
        } else {
            // Make sure the column index is valid.
            if (columnIndex < 1) {
                throw new NoSuchElementException("Table::getColumnText -- Invalid column index, " + columnIndex);
            }

            // if the table refreshed while we are getting values, then a stale element is being thrown. To get around
            //     this will catch the stale element and try again
            boolean done = false;
            int retries = 0;

            while (!done) {
                try {
                    // getting the table web element with the default timeout
                    // and then get all of the row elements
                    List<WebElement> rowElements = getRowElements();

                    // get the row count and create the returnValue array
                    rowCount = rowElements.size();
                    returnValue = new String[rowCount];

                    // for each row, get the text of the specified column
                    for (int i = 0; i < rowCount; i++) {
                        WebElement row = rowElements.get(i);

                        // get the specified column element of this row
                        WebElement column = row.findElement(By.xpath("td[" + columnIndex + "]"));

                        returnValue[i] = column.getText();
                    }
                    done = true;

                } catch(StaleElementReferenceException e){
                    retries++;
                    if (LOG_PERFORMANCE) {
                        log(this.getName() + "::getColumnText(" + columnIndex + ") reties " + stopwatch);
                    }

                    // have we tried the maximum retries?
                    if (retries == MAXRETRIES) {
                        done = true;

                        warning("ERROR!  StaleElementReference Exception - " + e.getMessage());
                        e.printStackTrace();
                        throw e;
                    }
                }
            }
        }

        if (LOG_PERFORMANCE) {
            stopwatch.stop();
            log(this.getName() + "::getColumnText(" + columnIndex + ") took " + stopwatch);
        }
        return returnValue;
    }

    /**
     * This method determines if the table is displayed in the UI.  If the table is displayed, a boolean value of
     * 'true' will be returned.
     */
    public boolean isDisplayed () {
        // declaring local variables
        boolean returnValue = false;

        if (this.isStubbed()) {
            log("Table::selectRow === The table's table query is currently stubbed out. ===");
        } else {
            // getting the table web element with the default timeout
            WebElement tableElement = getWebElement();

            if (tableElement.isDisplayed()) {
                returnValue = true;
            }
        }

        return returnValue;
    }

    /**
     * This method selects a row in the table.
     * <p>
     * <b><u>NOTE:</u></b>  The table is first checked if it exists before interacting with it.
     * If the table does not exist, an error will be logged an the test will stop.
     *
     * @param rowIndex      [INT] the row to select.  Rows start with index 1.
     */
    public void selectRow (int rowIndex) {
        if (this.isStubbed()) {
            log("Table::selectRow === The table's table query is currently stubbed out. ===");
        } else {
            // if the table refreshed while we are getting values, then a stale element is being thrown. To get around
            //     this will catch the stale element and try again
            boolean done = false;
            int retries = 0;

            while(!done) {
                try {

                    // getting the table web element with the default timeout
                    // and then get the specified row
                    WebElement row = getRowElement(rowIndex);

                    // set the table element in focus

                    // click on this row element
                    row.click();

                    // giving the table a second to refresh
                    sleep(1, TimeUnit.SECONDS);

                    done = true;
                } catch(StaleElementReferenceException e){
                    retries++;

                    // have we tried the maximum retries?
                    if (retries == MAXRETRIES) {
                        done = true;

                        warning("ERROR!  StaleElementReference Exception - " + e.getMessage());
                        e.printStackTrace();
                        throw e;
                    }
                }
            }
        }
    }

    /**
     * this function will select a row, but it will click in the area of the given column index.
     * At the moment, this function was primarly created for the SSMC Activity table, where the
     * selectRow (int) function was sometimes randomly selecting a cell area that caused it to
     * link to another page. <br>
     * Do not attempt to use this function to click on a link or icon within the table cell area
     * since it doesn't seem to work, and wasn't meant for this purpose.
     * @author Craig Yara
     * @param rowIndex
     */
    public void selectCell (int rowIndex, int columnIndex) {
        if (this.isStubbed()) {
            log("Table::selectRow === The table's table query is currently stubbed out. ===");
        } else {
            // if the table refreshed while we are getting values, then a stale element is being thrown. To get around
            //     this will catch the stale element and try again
            boolean done = false;
            int retries = 0;
            columnIndex --;  // we will now be dealing with 0 based elements instead of framework's 1-based indexes

            while(!done) {
                try {

                    // getting the table web element with the default timeout
                    // and then get the specified row
                    WebElement row = getRowElement(rowIndex);


                    // find all table columns inside the row
                    List<WebElement> columnElements = row.findElements(By.xpath("td"));

                    // click on the Nth column.
                    columnElements.get (columnIndex).click ();

                    // giving the table a second to refresh
                    sleep(1, TimeUnit.SECONDS);

                    done = true;
                } catch(StaleElementReferenceException e){
                    retries++;

                    // have we tried the maximum retries?
                    if (retries == MAXRETRIES) {
                        done = true;

                        warning("ERROR!  StaleElementReference Exception - " + e.getMessage());
                        e.printStackTrace();
                        throw e;
                    }
                }
            }
        }
    }

    /**
     * This method returns the index of a Row based on the specified Item Name in a particular column of the table.
     * <p>
     * <b><u>NOTE:</u></b>  The table is first checked if it exists before interacting with it.
     * If the table does not exist, an error will be logged and the test will stop.
     *
     * @param itemName      [STRING] the item name to be selected in the row.
     * @param columnIndex   [INT] the column to search the item for. It starts with index 1.
     * @return              [INT] the first row index that matches the item name that was passed.  If no match is found, zero is returned.
     */

    public int findItemInColumn (String itemName, int columnIndex){
        int returnValue = 0;
        String temp, itemWithoutParenthese;

        if (this.isStubbed()) {
            log("Table::selectRow === The table's table query is currently stubbed out. ===");
            returnValue = 1;
        } else {
            // Make sure the column index is valid.
            if (columnIndex < 1) {
                throw new NoSuchElementException("Table::findItemInColumn -- Invalid column index, " + columnIndex);
            }

            // if the table refreshed while we are getting values, then a stale element is being thrown. To get around
            //     this will catch the stale element and try again
            boolean done = false;
            int retries = 0;

            while (!done) {
                try {
                    // getting the table web element with the default timeout
                    // and then get all of the row elements
                    List<WebElement> rowElements = getRowElements();

                    // get the row count
                    int rowCount = rowElements.size();

                    // for each row, check the text of the specified column
                    boolean found = false;
                    for (int i = 0; i < rowCount; i++) {
                        WebElement row = rowElements.get(i);

                        // get the specified column element of this row
                        WebElement column;
                        try {
                            column = row.findElement(By.xpath("td[" + columnIndex + "]"));
                        } catch (NoSuchElementException e) {
                            // This row does not have this column; skip it.
                            // It occurs when an empty table has a row showing "No data".
                            continue;
                        }

                        // check the column text with the itemName -- if the same, then this is the row
                        // the user wants
                        temp = column.getText();
                        itemWithoutParenthese = temp;
                        if (temp.contains("(") && (itemName.contains("(") == false)) {
                            itemWithoutParenthese = temp.substring(0, temp.indexOf("(")).trim();
                        }
                        if (itemWithoutParenthese.equals(itemName)) {

                            returnValue = i+1;

                            // get out of for loop
                            i = rowCount;

                            found = true;
                        }
                    }

                    if(!found){
                        returnValue = 0;
                    }
                    done = true;

                }  catch(StaleElementReferenceException e){
                    retries++;

                    // have we tried the maximum retries?
                    if (retries == MAXRETRIES) {
                        done = true;

                        warning("ERROR!  StaleElementReference Exception - " + e.getMessage());
                        e.printStackTrace();
                        throw e;
                    }
                }
            }
        }

        return returnValue;
    }

    /**
     * This method returns the index of a Row based on the specified Item Name in a particular column of the table.
     * <p>
     * <b><u>NOTE:</u></b>  The table is first checked if it exists before interacting with it.
     * If the table does not exist, an error will be logged and the test will stop.
     *
     * Added by hemangiN
     * In case of AD user/group, while adding the quota, CLI adds the name in lower case.
     * This method is created to find out item in column ignoring the case.
     *
     * @param itemName      [STRING] the item name to be selected in the row.
     * @param columnIndex   [INT] the column to search the item for. It starts with index 1.
     * @return              [INT] the first row index that matches the item name that was passed.  If no match is found, zero is returned.
     */

    public int findItemInColumnEqualsIgnoreCase (String itemName, int columnIndex){
        int returnValue = 0;
        String temp, itemWithoutParenthese;

        if (this.isStubbed()) {
            log("Table::selectRow === The table's table query is currently stubbed out. ===");
            returnValue = 1;
        } else {
            // Make sure the column index is valid.
            if (columnIndex < 1) {
                throw new NoSuchElementException("Table::findItemInColumn -- Invalid column index, " + columnIndex);
            }

            // if the table refreshed while we are getting values, then a stale element is being thrown. To get around
            //     this will catch the stale element and try again
            boolean done = false;
            int retries = 0;

            while (!done) {
                try {
                    // getting the table web element with the default timeout
                    // and then get all of the row elements
                    List<WebElement> rowElements = getRowElements();

                    // get the row count
                    int rowCount = rowElements.size();

                    // for each row, check the text of the specified column
                    boolean found = false;
                    for (int i = 0; i < rowCount; i++) {
                        WebElement row = rowElements.get(i);

                        // get the specified column element of this row
                        WebElement column;
                        try {
                            column = row.findElement(By.xpath("td[" + columnIndex + "]"));
                        } catch (NoSuchElementException e) {
                            // This row does not have this column; skip it.
                            // It occurs when an empty table has a row showing "No data".
                            continue;
                        }

                        // check the column text with the itemName -- if the same, then this is the row
                        // the user wants
                        temp = column.getText();
                        itemWithoutParenthese = temp;
                        if (temp.contains("(") && (itemName.contains("(") == false)) {
                            itemWithoutParenthese = temp.substring(0, temp.indexOf("(")).trim();
                        }
                        if (itemWithoutParenthese.equalsIgnoreCase(itemName)) {

                            returnValue = i+1;

                            // get out of for loop
                            i = rowCount;

                            found = true;
                        }
                    }

                    if(!found){
                        returnValue = 0;
                    }
                    done = true;

                }  catch(StaleElementReferenceException e){
                    retries++;

                    // have we tried the maximum retries?
                    if (retries == MAXRETRIES) {
                        done = true;

                        warning("ERROR!  StaleElementReference Exception - " + e.getMessage());
                        e.printStackTrace();
                        throw e;
                    }
                }
            }
        }

        return returnValue;
    }


    /**
     * This method returns the index of a Row based on the specified Item Name in a particular column of the table.
     * <p>
     * <b><u>NOTE:</u></b>  The table is first checked if it exists before interacting with it.
     * If the table does not exist, an error will be logged and the test will stop.
     *
     * @param itemName      [STRING] the item name to be selected in the row.
     * @param columnHeaderName   [STRING] the name of the column to search the item for.
     * @return              [INT] the first row index that matches the item name that was passed.  If no match is found, zero is returned.
     */
    public int findItemInColumn (String itemName, String columnHeaderName)
    {
        int columnIndex = this.getColumnIndex(columnHeaderName);
        if (columnIndex > 0)
        {
            return this.findItemInColumn (itemName, columnIndex);
        }
        else
        {
            log ("WARNING: there is no table column name called: " + columnHeaderName);
            return -1;
        }
    }

    /**
     * This method returns the index of a Row based on the specified Item Name in a particular column of the table.
     * <p>
     * <b><u>NOTE:</u></b>  The table is first checked if it exists before interacting with it.
     * If the table does not exist, an error will be logged and the test will stop.
     *
     * @param itemName      [STRING] the item name to be selected in the row.
     * @param columnIndex   [INT] the column to search the item for. It starts with index 1.
     * @return              [INT] the first row index that matches the item name that was passed.  If no match is found, zero is returned.
     */

    public int containsItemInColumn (String itemName, int columnIndex){
        int returnValue = 0;

        if (this.isStubbed()) {
            log("Table::selectRow === The table's table query is currently stubbed out. ===");
            returnValue = 1;
        } else {
            // if the table refreshed while we are getting values, then a stale element is being thrown. To get around
            //     this will catch the stale element and try again
            boolean done = false;
            int retries = 0;

            while (!done) {
                try {
                    // getting the table web element with the default timeout
                    // and then get all of the row elements
                    List<WebElement> rowElements = getRowElements();

                    // get the row count
                    int rowCount = rowElements.size();

                    // for each row, check the text of the specified column
                    boolean found = false;
                    for (int i = 0; i < rowCount; i++) {
                        WebElement row = rowElements.get(i);

                        // get the specified column element of this row
                        WebElement column = row.findElement(By.xpath("td[" + columnIndex + "]"));

                        // check the column text with the itemName -- if the same, then this is the row
                        // the user wants
                        if (column.getText().contains(itemName)) {

                            returnValue = i+1;

                            // get out of for loop
                            i = rowCount;

                            found = true;
                        }
                    }

                    if(!found){
                        returnValue = 0;
                    }
                    done = true;

                }
                catch(StaleElementReferenceException e){
                    retries++;

                    // have we tried the maximum retries?
                    if (retries == MAXRETRIES) {
                        done = true;

                        warning("ERROR!  StaleElementReference Exception - " + e.getMessage());
                        e.printStackTrace();
                        throw e;
                    }
                }
                catch (org.openqa.selenium.NoSuchElementException e) {

                    retries++;

                    // have we tried the maximum retries?
                    if (retries == MAXRETRIES) {
                        done = true;
                        returnValue = -1;
                    }
                }
            }
        }

        return returnValue;
    }

    /**
     * This method selects a particular Row based on the Item Name in the table.
     * <p>
     * <b><u>NOTE:</u></b>  The table is first checked if it exists before interacting with it.
     * If the table does not exist, an error will be logged and the test will stop.
     *
     * @param itemName      [INT] the item name to be selected in the row.
     * @param rowIndex      [INT] the column to select.Column start with index 1.
     */
    public void selectItemInaRow (String itemName,int columnIndex) {
        if (this.isStubbed()) {
            log("Table::selectRow === The table's table query is currently stubbed out. ===");
        } else {
            // if the table refreshed while we are getting values, then a stale element is being thrown. To get around
            //     this will catch the stale element and try again
            boolean done = false;
            int retries = 0;
            String temp, itemWithoutParenthese;


            // CRAIG: added after removal of sleep revamps.
            this.isItemAppearinTable(itemName, 120, columnIndex); // TODO: remove harddcoded timeout

            while (!done) {
                try {
                    // getting the table web element with the default timeout
                    // and then get all of the row elements
                    List<WebElement> rowElements = getRowElements();

                    // get the row count
                    int rowCount = rowElements.size();

                    // for each row, check the text of the specified column
                    boolean found = false;
                    for (int i = 0; i < rowCount; i++) {
                        WebElement row = rowElements.get(i);

                        // get the specified column element of this row
                        WebElement column = row.findElement(By.xpath("td[" + columnIndex + "]"));

                        temp = column.getText();
                        itemWithoutParenthese = temp;

                        // we only want to get rid of Parenthesis strings  "myVV (1)"  if the item being searched for
                        // does NOT have a parenthesis while the table item we are looking at DOES.
                        if (temp.contains("(") && (itemName.contains("(") == false)) {
                            itemWithoutParenthese = temp.substring(0, temp.indexOf("(")).trim();
                        }

                        // check the column text with the itemName -- if the same, then this is the row
                        // the user wants
                        if (itemWithoutParenthese.equals(itemName)) {
                            // giving the browser a second to catch up with the web driver
                            sleep(1, TimeUnit.SECONDS);

                            // click on this row element
                            row.click();

                            // get out of for loop
                            i = rowCount;

                            found = true;
                        }
                    }

                    if(!found){
                        throw new NoSuchElementException("Unable to find row with the item '" + itemName + "'");
                    }
                    done = true;

                }  catch(StaleElementReferenceException e){
                    retries++;

                    // have we tried the maximum retries?
                    if (retries == MAXRETRIES) {
                        done = true;

                        warning("ERROR!  StaleElementReference Exception - " + e.getMessage());
                        e.printStackTrace();
                        throw e;
                    }
                }
            }
        }
    }

    public boolean selectItemInaRow (String itemName, String columnHeaderName) {

        boolean returnResult = true;

        if (this.isStubbed()) {
            log("Table::selectRow === The table's table query is currently stubbed out. ===");
        } else {

            int columnIndex = this.getColumnIndex(columnHeaderName);

            if (columnIndex != -1) {
                this.selectItemInaRow(itemName, columnIndex);
            }
            else
            {
                log ("WARNING: couldn't find a column called: " + columnHeaderName);
                returnResult = false;
            }
        }

        return returnResult;
    }



    /**
     * Selects the item in the table that belongs to a particular storeserv system.
     * @author Craig Yara
     * @param itemName
     * @param columnName
     * @param systemName
     * @param columnNameOfSystem
     */
    public void selectItemInaRow (String itemName, String columnName, String systemName, String columnNameOfSystem)
    {

        log ("Selecting item: " + itemName + "  for  " + columnNameOfSystem + ": " + systemName);

        if (this.isItemAppearinTable(itemName, columnName, systemName, columnNameOfSystem, 30)) {
            int row = this.getRowForItemNameAndSystem(itemName, columnName, systemName, columnNameOfSystem);

            if (row != 0) {
                this.selectRow(row);
            }
        }
        else
        {
            throw new NoSuchElementException("Unable to find row with the items:  '" + itemName + "'  '" + systemName + "'");
        }
    }

    /**
     * @author Craig Yara
     * @param itemName
     * @param systemName
     */
    public void selectItemInaRowWithSystem (String itemName, String systemName)
    {
        this.selectItemInaRow (itemName, "Name", systemName, "System");
    }


    /**
     * This method selects multiple rows in the table.
     * <p>
     * <b><u>NOTE:</u></b>  The table is first checked if it exists before interacting with it.
     * If the table does not exist, an error will be logged an the test will stop.
     *
     * @param rows      [ARRAY OF INT] the rows to select.  Rows start with index 1.
     */
    protected ArrayList<String> selectMultipleRows_original (int [] rows, int tdClickColumn, int dataColumn) {
        int rowCount = 0;
        int select = 0;

        Arrays.sort(rows);  // Sort the rows to work around a potential problem in selecting rows out of order
        ArrayList<String> actualItemlist = new ArrayList();
        ArrayList<String> theRealActualItemlist = new ArrayList();


        if (this.isStubbed()) {
            log("Table::selectRow === The table's table query is currently stubbed out. ===");
        } else {

            if (rows.length == 1) {
                this.selectRow (rows [0]);
                String selectedItem = this.getCellData(rows [0], dataColumn);
                theRealActualItemlist.add(selectedItem);  // TODO: CRAIG: hack.  when time, the later routines below should determine which items got selected, and not just assuming like this
            }

            else
            {   // getting the table web element with the default timeout
                // and then get all of the row elements
                List<WebElement> rowElements = getRowElements();

                // getting row count
                rowCount = rowElements.size();

                // clicking each desired row


                    sleep(5,TimeUnit.SECONDS);
                    // setting up builder
                    Actions builder = new Actions(browser.driver);
                    ArrayList<String> rowlist = new ArrayList();
                    sleep(1,TimeUnit.SECONDS);
                    for (int i = 0; i < rows.length; i++) {
                        // making sure the actual row to select is a valid row
                        rowlist.add(String.valueOf(rows[i]));
                        if ((rows[i] < 1) || (rows[i] > rowCount)) {
                            throw new NoSuchElementException("Table::selectMultipleRows -- row '" + rows[i] + "' is not a valid row to select.");
                        }

                        select++;
                        if (select == 1) {
                            builder.keyDown(rowElements.get(rows[i] - 1).findElements(By.tagName("td")).get(tdClickColumn), Keys.CONTROL);
                            sleep(1, TimeUnit.SECONDS);
                        } else if (i == rows.length - 1) {
                            builder.keyUp(rowElements.get(rows[i] - 1).findElements(By.tagName("td")).get(tdClickColumn), Keys.CONTROL);
                            sleep(1, TimeUnit.SECONDS);
                        } else {
                            builder.click(rowElements.get(rows[i] - 1).findElements(By.tagName("td")).get(tdClickColumn));
                            sleep(1, TimeUnit.SECONDS);
                        }
                        actualItemlist.add(this.getCellData(rows[i], dataColumn));
                    }//end for
                    sleep(1,TimeUnit.SECONDS);
                    // executing the key strokes
                    builder.build().perform();
                    //Below code is added to deselect unwanted rows that are getting selected bydefault.
                    Actions builderTodeSelectRow = new Actions(browser.driver);

                    log("List of rows given :" + rowlist);
                    //verify selected row is selcted or any other row is selected and deselect all rows which are not required.
                    //This is added as in some of the cases it is observed that bydefault 1st row is getting selected with expected row.
                    ArrayList<String> listOfselectedRows = this.getListOfselectedRow();
                    log("Actual List of rows selected :" + listOfselectedRows);


                    // CRAIG: there was still a bug with actualItemList.  It was being filled with the individual
                    //      row data during the selection process, but that does not necessarily reflect
                    //      the actual selected rows and something might have changed or happened.  The 'listOfSelectedRows'
                    //      contains the most recent real selected rows now.  So now we just get those rows text
                    int x;
                    for (String s:listOfselectedRows)
                    {
                        x = Integer.valueOf(s);
                        theRealActualItemlist.add (this.getCellData(x, dataColumn));
                    }



                    log("Actual List of item names that were selected :" + theRealActualItemlist);
                    listOfselectedRows.removeAll(rowlist);
                    log(" List of rows to be deselected :" + listOfselectedRows);
                    for (String rowNumber : listOfselectedRows) {
                        if (!actualItemlist.contains(rowElements.get(Integer.parseInt(rowNumber) - 1).findElements(By.tagName("td")).get(tdClickColumn).getText())) {
                            log("Deselcting row with name :" + rowElements.get(Integer.parseInt(rowNumber) - 1).findElements(By.tagName("td")).get(tdClickColumn).getText());
                            builderTodeSelectRow.keyDown(Keys.CONTROL);
                            builderTodeSelectRow.keyUp(rowElements.get(Integer.parseInt(rowNumber) - 1).findElements(By.tagName("td")).get(tdClickColumn), Keys.CONTROL);
                        }
                    }
                    builderTodeSelectRow.build().perform();
                }
            }




        return theRealActualItemlist;

    }

    /**
     * This method selects multiple rows in the table.
     * <p>
     * <b><u>NOTE:</u></b>  The table is first checked if it exists before interacting with it.
     * If the table does not exist, an error will be logged an the test will stop.
     *
     * @param rows      [ARRAY OF INT] the rows to select.  Rows start with index 1.
     */
    public  ArrayList<String> selectMultipleRows (int [] rows, int tdClickColumn, int dataColumn) {
        int rowCount = 0;
        int select = 0;
        int MAXTRIES = 4;
        ArrayList<String> returnActualList = null;

        if (this.isStubbed()) {
            log("Table::selectMultipleRows === The table's table query is currently stubbed out. ===");
        } else {
            // if the table refreshed while we are getting values, then a stale element is being thrown. To get around
            //     this will catch the stale element and try again
            boolean done = false;
            int retries = 0;

            while (!done) {
                try {
                    // getting the table web element with the default timeout
                    // and then get the specified row
                    returnActualList = selectMultipleRows_original(rows, tdClickColumn, dataColumn);

                    done = true;
                } catch (StaleElementReferenceException e) {
                    retries++;

                    // have we tried the maximum retries?
                    if (retries == MAXTRIES) {
                        done = true;

                        warning("ERROR!  StaleElementReference Exception - " + e.getMessage());
                        e.printStackTrace();
                        throw e;
                    }
                }
            }
        }

        return returnActualList;



    }

    public void selectMultipleRows (int [] rows) {
        selectMultipleRows(rows, 0, 2);
    }

    public ArrayList<String> selectMultipleRows (int [] indexNumbersToSelect, String itemListToSelect[],  int dataColumn) {

        ArrayList<String> actualListSelect = selectMultipleRows(indexNumbersToSelect, 0, dataColumn);

        return actualListSelect;
    }

    public void selectMultipleRows (int [] indexNumbersToSelect,  int dataColumn) {

        selectMultipleRows(indexNumbersToSelect, 0, dataColumn);
    }

    /**
     * This method selects multiple rows in the table based on the item names.
     * <p>
     * <b><u>NOTE:</u></b>  The table is first checked if it exists before interacting with it.
     * If the table does not exist, an error will be logged an the test will stop.
     * @param columnName     [STRING] the name of the table column where the itemsNames appear
     * @param itemNames      [ARRAY OF STRING] the item names in the rows to select.
     */
    public ArrayList<String> selectMultipleRows_original (String columnName, String [] itemNames) {
        String temp = "";
        int dataColumnIndex = this.getColumnIndex(columnName);
        ArrayList<String> notFoundList = new ArrayList<String>();
        ArrayList<String> actualSelectedList = new ArrayList<String> ();


        if (this.isStubbed()) {
            log("Table::selectRow === The table's table query is currently stubbed out. ===");
        } else {
            //get all the data from the indicated column
            List<String> cl = Arrays.asList(this.getColumnText(dataColumnIndex));
            ArrayList<String> columnNames = new ArrayList (cl);
            columnNames = this.removeParentheseFromNames (columnNames);

            int itemIndex;
            int[] selectRows = new int[itemNames.length];

            for(int i = 0; i<itemNames.length;++i){
                //find which row in the column this particular item is in
                itemIndex = columnNames.indexOf(itemNames[i]) + 1;

                //if the item is not found, then add it to the not found list
                if (itemIndex == 0){
                    notFoundList.add(itemNames[i]);
                    selectRows[i]= -1;//if it's not found then enter a -1 for its value.

                    //otherwise, add it to the list of rows to select.
                }else{
                    selectRows[i] = itemIndex;
                }
            }

            // if there are any items in the notFoundList, then throw an exception
            if (notFoundList.size() > 0) {
                temp = "";
                for (int i = 0; i < notFoundList.size(); i++) {
                    temp = temp + "'" + notFoundList.get(i) + "'; ";
                }

                // getting rid of the last '; ' in temp
                temp = temp.substring(0, temp.length()-2);
                throw new NoSuchElementException("Table::selectMultipleRows -- the following item names where not found in the table: " + temp);
            }

            //if everything was found, then go select them.
            else{
                try {
                    actualSelectedList = this.selectMultipleRows(selectRows, itemNames, dataColumnIndex);
                    actualSelectedList = this.removeParentheseFromNames(actualSelectedList);
                }
                catch (NoSuchElementException e)
                {
                    log ("** selectMultipleRows_original: NoSuchElement occurred. probably because the list of items changed in table. ");

                }

            }
        }

        return actualSelectedList;

    }

    public void selectMultipleRows (String columnName, String [] itemNames) {
        String temp = "";
        int dataColumnIndex = this.getColumnIndex(columnName);
        ArrayList<String> notFoundList = new ArrayList<String>();
        ArrayList<String> actualSelectedList = null;


        if (this.isStubbed()) {
            log("Table::selectRow === The table's table query is currently stubbed out. ===");
        } else {

            List<String> expectedItemList = Arrays.asList(itemNames);
            actualSelectedList  = this.selectMultipleRows_original (columnName,itemNames );

            Collections.sort(expectedItemList);
            Collections.sort(actualSelectedList);
            if (expectedItemList.equals(actualSelectedList) == false)
            {
                log ("** selectMultipleRows: multi select did not work properly the first time.  Trying again");
                log ("--- printing expected list ---");
                for (String s:expectedItemList) {
                    System.out.println (s);
                }
                log ("--- printing actual list ---");
                for (String s:actualSelectedList) {
                    System.out.println (s);
                }
                browser.takeScreenShot("selectMultipleRows_");
                sleep(3, TimeUnit.SECONDS);
                actualSelectedList = this.selectMultipleRows_original(columnName, itemNames);
                Collections.sort(actualSelectedList);
                if (expectedItemList.equals(actualSelectedList) == false)
                {
                    log ("*** FAILURE: could not properly select multiple items in list");
                    browser.takeScreenShot("selectMultipleRows_");
                    Assert.assertTrue(false,"** could not properly select multiple items in list");
                }
            }

        }



    }


    /**
     * This method returns the column heading for a specific column number.  If no column heading is found, an
     * empty string is returned.
     * <p>
     * Column indexes start with 1.
     *</p>
     * <p>
     * NOTE:  The table is first checked to see if it exists.  If the table does not exist, an error will be logged
     * and the test will stop.
     * </p>
     * @param columnIndex  [INT] the column index, starting with 1, for the heading desired
     * @return             the actual column heading
     */
    public String getColumnHeading (int columnIndex) {
        // declaring local variables
        String returnValue;

        if (this.isStubbed()) {
            returnValue = "VALUE";
            log("=== The table's headings query is currently stubbed out.  Returning a value '" + returnValue + "' ===");
        } else {
            // getting the table web element with the default timeout
            // and then get the specified column heading element
            WebElement headingElement = getColumnHeadingElements().get(columnIndex-1);

            // get the column heading value
            returnValue = headingElement.getText();
        }

        return returnValue;
    }

    /**
     * This method returns a String array of all the column headings for the table.  If there are no columns, this method
     * returns an empty array.
     *
     * @return       String array of column headings for the table
     */
    public String[] getColumnHeadingsList () {
        // declare local variables
        String[] returnValue = null;

        if (this.isStubbed()) {
            returnValue = new String[] { "COLUMN" };
            log("=== The table's headings query is currently stubbed out.  Returning a value '" + returnValue + "' ===");
        } else {
            // if the table refreshed while we are getting values, then a stale element is being thrown. To get around
            //     this will catch the stale element and try again
            boolean done = false;
            int retries = 0;

            while (!done) {
                try {

                    // getting the table web element with the default timeout
                    // and then get its headings elements
                    List<WebElement> headingElements = getColumnHeadingElements();

                    // get and trim the text of the heading elements
                    returnValue = ElementUtil.getText(headingElements, true);

                    done = true;
                } catch(StaleElementReferenceException e){
                    retries++;

                    // have we tried the maximum retries?
                    if (retries == MAXRETRIES) {
                        done = true;

                        warning("ERROR!  StaleElementReference Exception - " + e.getMessage());
                        e.printStackTrace();
                        throw e;
                    }
                }
            }
        }

        return returnValue;
    }

    /**
     * This class returns the index of the specific column heading.  If an index is not found, a value of
     * -1 will be returned.
     * <p>
     * Column indexes start with 1.
     * </p>
     * <p>
     * NOTE:  The table is first checked to see if it exists.  If the table does not exist, an error will be logged
     * and the test will stop.
     * </p>
     *
     * @param columnHeading    [STRING] the name of the column heading
     * @return                 the index of the specific column
     */
    protected int getColumnIndex_original (String columnHeading) {
        // declaring local variables
        int returnValue = -1;
        String headingValue;

        if (this.isStubbed()) {
            returnValue = 1;
            log("=== The table's table query is currently stubbed out.  Returning a value '" + returnValue + "' ===");
        } else {
            // if the table refreshed while we are getting values, then a stale element is being thrown. To get around
            //     this will catch the stale element and try again
            boolean done = false;
            int retries = 0;

            while (!done) {
                try {
                    // getting the table web element with the default timeout
                    // and then get its headings elements
                    List<WebElement> headingElements = getColumnHeadingElements();

                    // check the text of all heading elements to find the specified columnHeading
                    for (int i = 0; i < headingElements.size(); i++) {
                        // getting the heading of this column
                        headingValue = headingElements.get(i).getText();

                        if (headingValue.equalsIgnoreCase(columnHeading)) {
                            returnValue = i + 1;

                            // getting out of for loop
                            i = headingElements.size();
                        }
                    }
                    done = true;

                } catch(StaleElementReferenceException e){
                    retries++;

                    // have we tried the maximum retries?
                    if (retries == MAXRETRIES) {
                        done = true;

                        warning("ERROR!  StaleElementReference Exception - " + e.getMessage());
                        e.printStackTrace();
                        throw e;
                    }
                }
            }
        }

        return returnValue;
    }

    /**
     * This class returns the index of the specific column heading.  If an index is not found, a value of
     * -1 will be returned.
     * <p>
     * Column indexes start with 1.
     * </p>
     * <p>
     * NOTE:  The table is first checked to see if it exists.  If the table does not exist, an error will be logged
     * and the test will stop.
     * </p>
     *
     * @param columnHeading    [STRING] the name of the column heading
     * @return                 the index of the specific column
     */
    public int getColumnIndex (String columnHeading)
    {
        // declaring local variables
        boolean itemFound = false;
        boolean done = false;
        long startTime;
        int elapsedTime;
        startTime = System.nanoTime();
        int index = -1;
        int timeOutSeconds = 120;  //TODO: hardcoding

        // set start time
        while (done == false) {

            index = this.getColumnIndex_original (columnHeading);

            if (index != -1 ) {
                // good, value found
                itemFound = true;
                done = true;
            }

            if (itemFound == false) {
                // Object still there; keep waiting until timeout

                // get elapsed Time
                elapsedTime = (int) TimeUnit.SECONDS.convert((System.nanoTime() - startTime), TimeUnit.NANOSECONDS);

                // If it has reached the timeout, stop checking; otherwise sleep and try again
                if (elapsedTime >= timeOutSeconds) {
                    done = true;
                    break;
                } else {
                    sleep(1, TimeUnit.SECONDS);
                }
            }
        }
        return index;
    }


    /**
     * This method clicks on a specific column heading based on the parameter.
     *
     * @param columnHeading    a string representing the column heading
     */
    public void clickColumnHeading(String columnHeading) {
        // declaring local variables
        int index = this.getColumnIndex(columnHeading);

        this.clickColumnHeading(index);
    }

    /**
     * This method clicks on a specific column heading based on the index.
     *
     * @param columnIndex    an integer of the column heading to click.  Column headings start with 1.
     */
    public void clickColumnHeading(int columnIndex) {
        if (this.isStubbed()) {
            log("=== The table's table query is currently stubbed out.");
        } else {
            // getting the table web element with the default timeout
            // and then get the specified column heading element
            WebElement headingElement = getColumnHeadingElements().get(columnIndex-1);

            // clicking on the heading
            headingElement.click();
        }
    }

    /**
     * This method checks to see if the row in the table is selected.  If the row is selected, this method return
     * 'true'.  Otherwise, this method returns 'false'.
     *
     * @param rowIndex   [INT]  the row index being checked
     * @return           a boolean 'true' if the row is selected, 'false' otherwise.
     */
    public boolean isRowSelected (int rowIndex) {
        // declaring local variables
        boolean returnValue = false;

        if (this.isStubbed()) {
            log("Table::selectRow === The table's table query is currently stubbed out. ===");
        } else {
            // if the table refreshed while we are getting values, then a stale element is being thrown. To get around
            //     this will catch the stale element and try again
            boolean done = false;
            int retries = 0;

            while (!done) {
                try {
                    // getting the table web element with the default timeout
                    // and then get the specified row
                    WebElement row = getRowElement(rowIndex);

                    // check the CSS classes of the row element
                    returnValue = ElementUtil.hasCSSClass(row, "hp-selected");

                    done = true;
                } catch (StaleElementReferenceException e) {
                    retries++;

                    // have we tried the maximum retries?
                    if (retries == MAXRETRIES) {
                        done = true;

                        warning("ERROR!  StaleElementReference Exception - " + e.getMessage());
                        e.printStackTrace();
                        throw e;
                    }
                }
            }
        }

        return returnValue;
    }


    /**
     * This method is used to wait for a particular item to appear in a particular column of the table.
     *
     * @param itemName          [STRING] the item Name to be searched in the table
     * @param timeOutSeconds    [INT] the maximum timeout to wait for the item to appear in the table
     * @param columnIndex       [INT] the column index where the item to be searched
     * @return                  {@code true} if this method successfully found the item in the specified table column; {@code false} otherwise.
     */
    public boolean isItemAppearinTable(String itemName,int timeOutSeconds,int columnIndex)
    {
        // declaring local variables
        boolean itemFound = false;
        boolean done = false;
        long startTime;
        Stopwatch stopwatch = null;
        if (LOG_PERFORMANCE) {
            stopwatch = Stopwatch.createStarted();
        }

        if (this.isStubbed()) {
            log("Table::IsItemAppearinTable === The table's table query is currently stubbed out. ===");
            itemFound = true;
        }
        else {

            log ("  Waiting for item to appear in table: " + itemName);
            // set start time
            startTime = System.nanoTime();
            int rowIndex = 0;
            while (!done) {
                // Is the item in the table?
                try {
                    rowIndex = findItemInColumn(itemName, columnIndex);
                } catch (Exception e) {}
                if (rowIndex > 0) {
                    // We found it; we're done!
                    itemFound = true;
                    done = true;
                } else {
                    // We couldn't find it; keep checking until timeout.

                    // get elapsed Time
                    int elapsedTime = (int)TimeUnit.SECONDS.convert((System.nanoTime() - startTime), TimeUnit.NANOSECONDS);

                    // If it has reached the timeout, stop checking; otherwise sleep and try again
                    if (elapsedTime >= timeOutSeconds) {
                        done = true;
                    } else {
                        sleep(1, TimeUnit.SECONDS);
                    }
                }
            }
        }

        if (LOG_PERFORMANCE) {
            stopwatch.stop();
            log(this.getName() + "::isItemAppearinTable('" + itemName + "', " + timeOutSeconds + ", " + columnIndex + ") took " + stopwatch);
        }

        if (itemFound)
        {
            log ("  item found: " + itemName);
        }
        else
        {
            log ("  item NOT found: " + itemName);
        }
        return itemFound;
    }

    public boolean isItemAppearinTable(String itemName, String columnHeaderForName, String systemName, String columnHeaderForSystem, int timeOutSeconds)
    {
        //deeclaring local variables
        boolean itemFound = false;
        boolean done = false;
        long startTime;
        Stopwatch stopwatch = null;
        if (LOG_PERFORMANCE) {
            stopwatch = Stopwatch.createStarted();
        }

        if (this.isStubbed()) {
            log("Table::IsItemAppearinTable === The table's table query is currently stubbed out. ===");
            itemFound = true;
        }
        else {
            // set start time
            startTime = System.nanoTime();
            while (!done) {
                // Is the item in the table?
                int rowIndex = this.getRowForItemNameAndSystem(itemName, columnHeaderForName, systemName, columnHeaderForSystem);
                if (rowIndex > 0) {
                    // We found it; we're done!
                    itemFound = true;
                    done = true;
                } else {
                    // We couldn't find it; keep checking until timeout.

                    // get elapsed Time
                    int elapsedTime = (int)TimeUnit.SECONDS.convert((System.nanoTime() - startTime), TimeUnit.NANOSECONDS);

                    // If it has reached the timeout, stop checking; otherwise sleep and try again
                    if (elapsedTime >= timeOutSeconds) {
                        done = true;
                    } else {
                        sleep(1, TimeUnit.SECONDS);
                    }
                }
            }
        }

        if (LOG_PERFORMANCE) {
            stopwatch.stop();
            log(this.getName() + "::isItemAppearinTable('" + itemName + "', " + timeOutSeconds + ", " + systemName + ") took " + stopwatch);
        }
        return itemFound;

    }
    public boolean isItemAppearinTable(String itemName, String systemName, int timeOutSeconds)
    {

        return this.isItemAppearinTable (itemName, "Name", systemName, "System", timeOutSeconds);
    }


    /**
     * returns TRUE if all the given array values are in a row in the table.
     * @author Craig Yara
     * @param columnValues
     * @param timeOutSeconds
     * @return
     */
    public boolean isItemAppearinTable(String columnValues [], int timeOutSeconds)
    {
        //deeclaring local variables
        boolean itemFound = false;
        boolean done = false;
        long startTime;
        Stopwatch stopwatch = null;
        if (LOG_PERFORMANCE) {
            stopwatch = Stopwatch.createStarted();
        }

        if (this.isStubbed()) {
            log("   Table::isItemAppearinTable === The table's table query is currently stubbed out. ===");
            itemFound = true;
        }
        else {
            // set start time
            startTime = System.nanoTime();
            while (!done) {
                try {
                    // Is the item in the table?
                    int rowIndex = this.getRowContainingAllColumnValues(columnValues);
                    if (rowIndex > 0) {
                        // We found it; we're done!
                        log ("  row was found!");
                        itemFound = true;
                        done = true;
                    } else {
                        // We couldn't find it; keep checking until timeout.

                        // get elapsed Time
                        int elapsedTime = (int) TimeUnit.SECONDS.convert((System.nanoTime() - startTime), TimeUnit.NANOSECONDS);

                        // If it has reached the timeout, stop checking; otherwise sleep and try again
                        if (elapsedTime >= timeOutSeconds) {
                            done = true;
                        } else {
                            sleep(1, TimeUnit.SECONDS);
                        }
                    }
                } catch (StaleElementReferenceException e) {
                    log ("  isItemAppearinTable:StaleElementReferenceException occurred in Table.  Waiting a few seconds and resuming");
                    // get elapsed Time
                    int elapsedTime = (int) TimeUnit.SECONDS.convert((System.nanoTime() - startTime), TimeUnit.NANOSECONDS);

                    // If it has reached the timeout, stop checking; otherwise sleep and try again
                    if (elapsedTime >= timeOutSeconds) {
                        done = true;
                    } else {
                        sleep(3, TimeUnit.SECONDS);
                    }
                }


            }
        }

        return itemFound;

    }


    /**
     * This method is used to wait for a particular item to disappear from a particular column of the table.
     *
     * @param itemName          [STRING] the item Name to be searched in the table
     * @param timeOutSeconds    [INT] the maximum timeout to wait for the item to disappear in the table
     * @param columnIndex       [INT] the column index where the item to be searched
     * @return                  {@code true} if this method could not found the item in the specified table column; {@code false} otherwise.
     */
    public boolean isItemDisappearinTable(String itemName,int timeOutSeconds,int columnIndex)
    {
        // declaring local variables
        boolean itemDisappeared = false;
        boolean done = false;
        long startTime;
        Stopwatch stopwatch = null;
        if (LOG_PERFORMANCE) {
            stopwatch = Stopwatch.createStarted();
        }

        if (this.isStubbed()) {
            log("Table::IsItemDisappearinTable === The table's table query is currently stubbed out. ===");
            itemDisappeared = true;
        }
        else {
            // set start time
            startTime = System.nanoTime();
            while (!done) {

                // Is the item in the table?
                int rowIndex = findItemInColumn(itemName, columnIndex);
                if (rowIndex == 0) {
                    // The item does not exist; we're done!
                    itemDisappeared = true;
                    done = true;
                } else {
                    // The item still exists; keep checking until timeout.

                    // get elapsed Time
                    int elapsedTime = (int)TimeUnit.SECONDS.convert((System.nanoTime() - startTime), TimeUnit.NANOSECONDS);

                    // If it has reached the timeout, stop checking; otherwise sleep and try again
                    if (elapsedTime >= timeOutSeconds) {
                        done = true;
                    } else {
                        sleep(1, TimeUnit.SECONDS);
                    }
                }
            }
        }

        if (LOG_PERFORMANCE) {
            stopwatch.stop();
            log(this.getName() + "::isItemDisappearinTable('" + itemName + "', " + timeOutSeconds + ", " + columnIndex + ") took " + stopwatch);
        }
        return itemDisappeared;
    }

    public boolean isItemDisappearinTable (String itemName, String columnHeaderForName, String systemName, String columnHeaderForSystem, int timeOutSeconds)
    {
        // declaring local variables
        boolean itemDisappeared = false;
        boolean done = false;
        long startTime;
        Stopwatch stopwatch = null;
        if (LOG_PERFORMANCE) {
            stopwatch = Stopwatch.createStarted();
        }

        if (this.isStubbed()) {
            log("Table::IsItemDisappearinTable === The table's table query is currently stubbed out. ===");
            itemDisappeared = true;
        }
        else {
            // set start time
            startTime = System.nanoTime();
            while (!done) {
                // Is the item in the table?
                int rowIndex = this.getRowForItemNameAndSystem(itemName, columnHeaderForName, systemName, columnHeaderForSystem);
                if (rowIndex == 0) {
                    // The item does not exist; we're done!
                    itemDisappeared = true;
                    done = true;
                } else {
                    // The item still exists; keep checking until timeout.

                    // get elapsed Time
                    int elapsedTime = (int)TimeUnit.SECONDS.convert((System.nanoTime() - startTime), TimeUnit.NANOSECONDS);

                    // If it has reached the timeout, stop checking; otherwise sleep and try again
                    if (elapsedTime >= timeOutSeconds) {
                        done = true;
                    } else {
                        sleep(1, TimeUnit.SECONDS);
                    }
                }
            }
        }

        if (LOG_PERFORMANCE) {
            stopwatch.stop();
            log(this.getName() + "::isItemDisappearinTable('" + itemName + "', " + timeOutSeconds + ", " + systemName + ") took " + stopwatch);
        }
        return itemDisappeared;
    }
    public boolean isItemDisappearinTable (String itemName, String systemName, int timeOutSeconds)
    {
        return this.isItemDisappearinTable (itemName, "Name", systemName, "System", timeOutSeconds);
    }

    /**
     * This method returns an list of String array of values in each column for all rows in the table.
     * If the item in the column is a checkbox or an image, not a text, then the value will be an empty
     * string.
     * <p>
     * <b><u>NOTE:</u></b>  The table is first checked if it exists before interacting with it.
     * If the table does not exist, an error is logged and the test will stop.     *

     * @return              an list of String array with all the values for all the rows
     * @author Craig Yara
     */

    public ArrayList<String[]> getAllRows () {
        // PROCEDURE:
        //      to maximize performance for tables with potentially thousands of items, this routine will
        // fetch ALL column <TD> table data in one single browser grab, and then place them into the returning array
        // in their proper row positions
        // Example:
        //      if number of columns = 3
        //      if number of rows in table = 4
        //      Get list of all <TD> objects in table..
        //          <TD1>
        //          <TD2>
        //          <TD3>
        //          <TD4>
        //          <TD5>
        //          <TD6>
        //          <TD7>
        //          <TD8>
        //          <TD9>
        //          <TD10>
        //          <TD11>
        //          <TD12>
        //
        //      since we know how many columns there are per row, we can then sort them into their proper rows
        //         row1 - <TD1>, <TD2>, <TD3>, <TD4>
        //         row2 - <TD5>, <TD6>, <TD7>, <TD8>
        //         row3 - <TD9>, <TD10>, <TD11>, <TD12>
        //

        // declaring local variables
        int totalRows;
        int columnCount;

        WebElement row;
        List<WebElement> columnElements;
        String[] returnValue = {};
        ArrayList<String[]> returnList = new ArrayList<String[]>();
        String iconValue;
        int MAXINDEXRETRIES=5;

        if (this.isStubbed()) {
            returnValue = new String[] {"One", "Two"};
            log("Table::getAllRows === The table's table query is currently stubbed out.  Returning a String array with 2 items. ===");
        } else {
            // if the table refreshed while we are getting values, then a stale element is being thrown. To get around
            //     this will catch the stale element and try again

            boolean done = false;
            int retries = 0;
            int indexretries=0;

            int indexColumn = 0;

            // adding a sleep here, because sometimes when the table is refreshing, some values within the row
            // are '--'
            sleep(1, TimeUnit.SECONDS);

            while(!done) {
                try {
                    totalRows = this.getRowCount();
                    columnCount = this.getColumnCount();
                    returnList = new ArrayList<String[]>();
                    indexColumn = 0;

                    // get all the table column <TD> elements in one huge grab
                    columnElements = getColumnElements();


                    // tables might be empty.  Check for this.
                    if (columnElements.size() != 0) {

                        // check if table is not empty, but has a "No data available in table" table row.
                        // If so, then we will treat this table as empty and return a list with no rows in it.
                        if (columnElements.size() >= columnCount) {

                            for (int i = 0; i < totalRows; i++) {

                                // find the column elements of this row

                                returnValue = new String[columnCount];

                                // since we know how many columns there are in the table (columnCount),
                                // we will simply take the next set of column values and add them to our
                                // row string array....
                                for (int j = 0; j < columnCount; j++) {

                                    returnValue[j] = columnElements.get(indexColumn).getText();


                                    //if it is an empty string, it may contain an icon.
                                    if (returnValue[j].equals("")) {
                                        iconValue = getIconValue(columnElements.get(indexColumn));
                                        if (!iconValue.isEmpty())
                                            returnValue[j] = iconValue;
                                    }

                                    indexColumn++;  // increase our index pointer of our huge <TD> list for the next column

                                }
                                returnList.add(i, returnValue);

                                // at this point the "indexColumn" pointer is pointing to the start of the next
                                // row of data (if any)
                            }
                        }

                    } // end if <TD> list is 0

                    done = true;
                } catch(StaleElementReferenceException e){
                    retries++;
                    sleep (3, TimeUnit.SECONDS);


                    // have we tried the maximum retries?
                    if (retries == MAXRETRIES) {
                        done = true;

                        warning("ERROR!  StaleElementReference Exception - " + e.getMessage());
                        e.printStackTrace();
                        throw e;
                    }
                } catch (IndexOutOfBoundsException e) {

                    // sometimes a right pane Details table object can end up getting redrawn because
                    // of timing delays.  This can result in unexpected index failures.  If this happens,
                    // restart.
                    indexretries++;
                    sleep (3, TimeUnit.SECONDS);
                    // have we tried the maximum retries?
                    if (indexretries == MAXINDEXRETRIES) {
                        done = true;

                        warning("ERROR!  IndexOutOfBoundsException Exception - " + e.getMessage());
                        e.printStackTrace();
                        throw e;
                    }
                }
            }
        }

        return returnList;
    }

    /**
     * This methods finds all of the web elements that represent the columns of the table.
     * If the table does not exist or is not displayed, it will keep trying until the default timeout has been reached.
     * <p>
     * <b>Note:</b> This method does not check whether the locator of this web element is stubbed out or not.
     * Thus, it is the responsibility of the caller NOT to call this method if the locator is stubbed out.
     *
     * @return  a list of {@link WebElement} instances representing the columns of the table
     */
    protected List<WebElement> getColumnElements() {
        WebElement tableElement = null;

        if (ElementUtil.hasCSSClass(getWebElement(),"dataTables_wrapper")){
            tableElement = getWebElement().findElement(By.cssSelector(".dataTables_scrollBody > table"));
        }
        else{
            // getting the table web element with the default timeout
            tableElement = getWebElement();
        }

        // get the row web elements of the table
        List<WebElement> rowElements = tableElement.findElements(By.xpath("tbody/tr/td"));

        return rowElements;
    }


    /**
     *<p>
     *  This method is used to check whether the Load More link exists in the table.
     * </p>
     * If any failure occurs in the steps, this method logs a warning, and returns
     * the value 'false'.
     * </p>
     *
     * @return                  a 'true' if this method successfully found the link and 'false' otherwise.
     */

    public boolean isLoadMoreLinkExists () {
        // declaring local variables
        boolean returnValue = true;

        if (this.isStubbed()) {
            log("=== This table's locator is currently stubbed out. Returning value '" + returnValue + "' ===");
        } else {

            if (this.loadMoreBelowLink.exists()) {
                returnValue = true;
            }
            else if (this.loadMoreAboveLink.exists()) {
                returnValue = true;
            }
            else {
                returnValue = false;
            }

        }

        return returnValue;
    }

    /**
     * This method clicks on the load more button in the table
     *
     * @param None
     */
    public void loadMoreItems(){

        if (this.loadMoreBelowLink.exists()) {
            this.loadMoreBelowLink.click();
        }
        else if (this.loadMoreAboveLink.exists()) {
            this.loadMoreAboveLink.click();
        }

    }

    /**
     * This method checks to see if the table cell at the specified row and column contains an Edit button.
     *
     * @param rowIndex  The row index of the specified cell. Rows start with index 1.
     * @param colIndex  The column index of the specified cell. Columns start with index 1.
     * @return          'true' if the specified cell contains an Edit button, 'false' otherwise
     */
    public boolean containsEditButton(int rowIndex, int colIndex) {
        boolean returnValue = false;
        WebElement rowElement = getRowElement(rowIndex);
        List<WebElement> columnElements;

        // Obtains the column elements of this row
        columnElements = rowElement.findElements(By.xpath("td"));

        // Obtains the specified column in the specified row
        WebElement columnElement = columnElements.get(colIndex-1);

        // If the cell is not of the 'hp-icon' class
        if(!columnElement.getAttribute("class").equals("hp-icon")) {
            return returnValue;
        }

        // Obtains all of the Edit buttons within the specified cell
        List<WebElement> editButtonElements = columnElement.findElements(By.className("hp-edit"));

        // If there's 1 or more Edit buttons within the specified cell
        if(editButtonElements.size() > 0) {
            returnValue = true;
        }

        return returnValue;
    }

    /**
     * This method clicks on the Edit button within the table cell at the specified row and column if an Edit button exists.
     *
     * @param rowIndex  The row index of the specified cell. Rows start with index 1.
     * @param colIndex  The column index of the specified cell. Columns start with index 1.
     * @throws NoSuchElementException if an Edit button isn't found within the specified table cell
     */
    public void clickEditButton(int rowIndex, int colIndex) {
        WebElement rowElement = getRowElement(rowIndex);
        List<WebElement> columnElements;

        // Obtains the column elements of this row
        columnElements = rowElement.findElements(By.xpath("td"));

        // Obtains the specified column in the specified row
        WebElement columnElement = columnElements.get(colIndex-1);

        // If the cell is not of the 'hp-icon' class
        if(!columnElement.getAttribute("class").equals("hp-icon")) {
            throw new NoSuchElementException("ERROR!  Specified table cell does not contain an Edit button.");
        }

        // Obtains all of the Edit buttons within the specified cell
        List<WebElement> editButtonElements = columnElement.findElements(By.className("hp-edit"));

        // If there isn't an Edit button within the specified cell
        if(editButtonElements.size() == 0) {
            throw new NoSuchElementException("ERROR!  Specified table cell does not contain an Edit button.");
        }

        // Clicks on the Edit button within the specified cell
        editButtonElements.get(0).click();
    }

    /**
     * This method clicks on the Edit button specified at given index within the table cell
     *  at the specified row and column if multiple Edit button exists.
     *
     * @param rowIndex  The row index of the specified cell. Rows start with index 1.
     * @param colIndex  The column index of the specified cell. Columns start with index 1.
     * @param buttonIndex  The button index in the specified cell. Start with index 0.
     * @throws NoSuchElementException if an Edit button isn't found within the specified table cell at specified index
     */
    public void clickEditButton(int rowIndex, int colIndex, int buttonIndex) {
        WebElement rowElement = getRowElement(rowIndex);
        List<WebElement> columnElements;

        // Obtains the column elements of this row
        columnElements = rowElement.findElements(By.xpath("td"));

        // Obtains the specified column in the specified row
        WebElement columnElement = columnElements.get(colIndex-1);

        // If the cell is not of the 'hp-icon' class
        if(!columnElement.getAttribute("class").equals("hp-icon")) {
            throw new NoSuchElementException("ERROR!  Specified table cell does not contain an Edit button.");
        }

        // Obtains all of the Edit buttons within the specified cell
        List<WebElement> editButtonElements = columnElement.findElements(By.className("hp-edit"));

        // If there isn't an Edit button within the specified cell
        if(editButtonElements.size() < buttonIndex) {
            throw new NoSuchElementException("ERROR!  Specified table cell does not contain an Edit button at specified index.");
        }

        // Clicks on the Edit button within the specified cell
        editButtonElements.get(buttonIndex).click();
    }

    /**
     * This method clicks on the CLOSE (X) button within the table cell at the specified row and column if an Edit button exists.
     *
     * @param rowIndex  The row index of the specified cell. Rows start with index 1.
     * @param colIndex  The column index of the specified cell. Columns start with index 1.
     * @throws NoSuchElementException if an Edit button isn't found within the specified table cell
     */
    public void clickCloseButton(int rowIndex, int colIndex) {
        WebElement rowElement = getRowElement(rowIndex);
        List<WebElement> columnElements;

        // Obtains the column elements of this row
        columnElements = rowElement.findElements(By.xpath("td"));

        // Obtains the specified column in the specified row
        WebElement columnElement = columnElements.get(colIndex-1);

        // If the cell is not of the 'hp-icon' class
        if(!columnElement.getAttribute("class").equals("hp-icon")) {
            throw new NoSuchElementException("ERROR!  Specified table cell does not contain an Edit button.");
        }

        // Obtains all of the Edit buttons within the specified cell
        List<WebElement> editButtonElements = columnElement.findElements(By.cssSelector("div[class='hp-close']"));

        // If there isn't an Edit button within the specified cell
        if(editButtonElements.size() == 0) {
            throw new NoSuchElementException("ERROR!  Specified table cell does not contain a Close button.");
        }

        // Clicks on the Edit button within the specified cell
        editButtonElements.get(0).click();
    }
    /**
     * This method checks to see if the table cell at the specified row and column contains a link.
     *
     * @param rowIndex  The row index of the specified cell. Rows start with index 1.
     * @param colIndex  The column index of the specified cell. Columns start with index 1.
     * @return          'true' if the specified cell contains a link, 'false' otherwise
     */
    public boolean containsLink(int rowIndex, int colIndex) {
        boolean returnValue = false;
        WebElement rowElement = getRowElement(rowIndex);
        List<WebElement> columnElements;

        // Obtains the column elements of this row
        columnElements = rowElement.findElements(By.xpath("td"));

        // Obtains the specified column in the specified row
        WebElement columnElement = columnElements.get(colIndex-1);

        // Obtains all of the links within the specified cell
        List<WebElement> linkElements = columnElement.findElements(By.tagName("a"));

        // If there's 1 or more links within the specified cell
        if(linkElements.size() > 0) {
            returnValue = true;
        }

        return returnValue;
    }

    /**
     * This method clicks on the link within the table cell at the specified row and column if the link exists.
     *
     * @param rowIndex  The row index of the specified cell. Rows start with index 1.
     * @param colIndex  The column index of the specified cell. Columns start with index 1.
     * @throws NoSuchElementException if a link isn't found within the specified table cell
     */
    public void clickLink(int rowIndex, int colIndex) {
        WebElement rowElement = getRowElement(rowIndex);
        List<WebElement> columnElements;

        // Obtains the column elements of this row
        columnElements = rowElement.findElements(By.xpath("td"));

        // Obtains the specified column in the specified row
        WebElement columnElement = columnElements.get(colIndex-1);

        // Obtains all of the links within the specified cell
        List<WebElement> linkElements = columnElement.findElements(By.tagName("a"));

        // If there isn't a link within the specified cell
        if(linkElements.size() == 0) {
            throw new NoSuchElementException("ERROR!  Specified table cell does not contain a link.");
        }

        // Clicks on the link within the specified cell
        linkElements.get(0).click();
    }

    /**
     * This method clicks on the link within the table cell at the specified row and column if the link exists.
     *
     * @param rowIndex  The row index of the specified cell. Rows start with index 1.
     * @param colIndex  The column index of the specified cell. Columns start with index 1.
     * @throws NoSuchElementException if a link isn't found within the specified table cell
     */
    public void clickCellLink(int rowIndex, int colIndex, String linkText) {
        WebElement rowElement = getRowElement(rowIndex);
        List<WebElement> columnElements;

        // Obtains the column elements of this row
        columnElements = rowElement.findElements(By.xpath("td"));

        // Obtains the specified column in the specified row
        WebElement columnElement = columnElements.get(colIndex-1);

        // Obtains link with text Delete
        WebElement linkElement = columnElement.findElement(By.linkText(linkText));

        // If there isn't a link within the specified cell
        /*if(linkElement.size() == 0) {
            throw new NoSuchElementException("ERROR!  Specified table cell does not contain a link.");
        }*/

        // Clicks on the link within the specified cell
        linkElement.click();
    }

    /**
     * This method checks to see if the table cell at the specifid row and column contains a SelectorMenu.
     *
     * @param rowIndex  The row index of the specified cell. Rows start with index 1.
     * @param colIndex  The column index of the specified cell. Columns start with index 1.
     * @return          'true' if the specified cell contains a SelectorMenu, 'false' otherwise
     */
    public boolean containsMenu(int rowIndex, int colIndex) {
        boolean returnValue = false;
        WebElement rowElement = getRowElement(rowIndex);
        List<WebElement> columnElements;

        // Obtains the column elements of this row
        columnElements = rowElement.findElements(By.xpath("td"));

        // Obtains the specified column of the specified row
        WebElement columnElement = columnElements.get(colIndex-1);

        // Obtains all of the menus within the specified cell
        List<WebElement> menuElements = columnElement.findElements(By.className("hp-select"));

        // if there's 1 or more menus within the specified cell
        if(menuElements.size() > 0) {
            returnValue = true;
        }

        return returnValue;
    }






    /**
     * For the given table item object, this function determines if a column data for this item matches
     * the expected value
     * @author Craig Yara
     * @param columnNameForItem - the column header name that represents the name of the main item.  Usually its "Name"
     * @param itemNameInTable - the item name , e.g. myCPG1
     * @param columnNameForData - the column header name of the column you wish to verify.
     * @param expectedColumnDataInTable - the expected data value that the column should be for this item
     * @param timeOutSeconds - the timeout in seconds
     * @return
     */
    public boolean isColumnDataAppearForItemInTable (String columnNameForItem, String itemNameInTable,
                                                     String columnNameForData, String expectedColumnDataInTable, int timeOutSeconds)
    {
        // in the example below, this function can verify that the value of RAID for volume VV1 is "Raid 0"
        //
        //      NAME      CPG       RAID       SIZE
        //      ----      ----      ----       ----
        //      myVV      cpg1      Raid 5     3 gig
        //      vv1       cpg2      Raid 0     1 gig
        //      vv2       cpg3      Raid 1     2 gig

        List headerlist = Arrays.asList(this.getColumnHeadingsList());


        // get the column index values for the item name and its data column
        int columnIndexForNameColumn = headerlist.indexOf(columnNameForItem)+1;
        int columnIndexForData = headerlist.indexOf(columnNameForData)+1;

        if (columnIndexForNameColumn == -1) {
            log ("*** FAILURE: there is no column name in the table: " + columnNameForItem);
        }
        if (columnIndexForData == -1) {
            log ("*** FAILURE: there is no column name in the table: " + columnNameForData);
        }

        // declaring local variables
        String cellData;
        int elapsedTime;
        boolean itemFound = false;
        boolean done = false;
        long startTime;
        Stopwatch stopwatch = null;
        if (LOG_PERFORMANCE) {
            stopwatch = Stopwatch.createStarted();
        }

        if (this.isStubbed()) {
            log("Table::isColumnDataAppearForItemInTable === The table's table query is currently stubbed out. ===");
            itemFound = true;
        }
        else {
            // set start time
            startTime = System.nanoTime();
            while (!done) {
                // Is the item in the table?
                int rowIndex = findItemInColumn(itemNameInTable, columnIndexForNameColumn);
                if (rowIndex > 0) {
                    // We found the item in the table.   Now check the column of interest...
                    cellData = this.getCellData(rowIndex, columnIndexForData);

                    // does that data match what we expect?
                    if (cellData.equals(expectedColumnDataInTable))
                    {
                        // yes, data matches
                        itemFound = true;
                        done = true;
                    }


                }

                if ((done == false) && (itemFound == false))
                {
                    // We couldn't find the item   OR  the item's column value is not yet as expected.  time to wait a bit

                    // get elapsed Time
                    elapsedTime = (int)TimeUnit.SECONDS.convert((System.nanoTime() - startTime), TimeUnit.NANOSECONDS);

                    // If it has reached the timeout, stop checking; otherwise sleep and try again
                    if (elapsedTime >= timeOutSeconds) {
                        done = true;
                    } else {
                        sleep(1, TimeUnit.SECONDS);
                    }
                }
            }
        }


        return itemFound;
    }

    /**
     *
     * @author Craig Yara
     * @param columnNameForItem
     * @param itemNameInTable
     * @param columnIndexForData
     * @param expectedColumnDataInTable
     * @param timeOutSeconds
     * @return
     */
    public boolean isColumnDataAppearForItemInTable (String columnNameForItem, String itemNameInTable,
                                                     int columnIndexForData, String expectedColumnDataInTable, int timeOutSeconds)
    {
        // TODO: function above should now call this one so that we don't have duplicate coce


        // in the example below, this function can verify that the value of RAID for volume VV1 is "Raid 0"
        //
        //      NAME      CPG       RAID       SIZE
        //      ----      ----      ----       ----
        //      myVV      cpg1      Raid 5     3 gig
        //      vv1       cpg2      Raid 0     1 gig
        //      vv2       cpg3      Raid 1     2 gig

        log ("Waiting for colunn " + columnIndexForData + " for item " + itemNameInTable + " to become: "  + expectedColumnDataInTable);

        List headerlist = Arrays.asList(this.getColumnHeadingsList());


        // get the column index values for the item name and its data column
        int columnIndexForNameColumn = headerlist.indexOf(columnNameForItem)+1;

        if (columnIndexForNameColumn == -1) {
            log ("*** FAILURE: there is no column name in the table: " + columnNameForItem);
        }
        if (columnIndexForData == -1) {
            log ("*** FAILURE: isColumnDataAppearForItemInTable::columnIndexForData is -1");
        }

        // declaring local variables
        String cellData;
        int elapsedTime;
        boolean itemFound = false;
        boolean done = false;
        long startTime;
        Stopwatch stopwatch = null;
        if (LOG_PERFORMANCE) {
            stopwatch = Stopwatch.createStarted();
        }

        if (this.isStubbed()) {
            log("Table::isColumnDataAppearForItemInTable === The table's table query is currently stubbed out. ===");
            itemFound = true;
        }
        else {
            // set start time
            startTime = System.nanoTime();
            while (!done) {
                // Is the item in the table?
                int rowIndex = findItemInColumn(itemNameInTable, columnIndexForNameColumn);
                if (rowIndex > 0) {
                    // We found the item in the table.   Now check the column of interest...
                    cellData = this.getCellData(rowIndex, columnIndexForData);

                    // does that data match what we expect?
                    if (cellData.equals(expectedColumnDataInTable))
                    {
                        // yes, data matches
                        itemFound = true;
                        done = true;
                    }


                }

                if ((done == false) && (itemFound == false))
                {
                    // We couldn't find the item   OR  the item's column value is not yet as expected.  time to wait a bit

                    // get elapsed Time
                    elapsedTime = (int)TimeUnit.SECONDS.convert((System.nanoTime() - startTime), TimeUnit.NANOSECONDS);

                    // If it has reached the timeout, stop checking; otherwise sleep and try again
                    if (elapsedTime >= timeOutSeconds) {
                        done = true;
                    } else {
                        sleep(1, TimeUnit.SECONDS);
                    }
                }
            }
        }


        return itemFound;
    }

    /**
     * returns the row number for a table item that belongs to a particular storeserv system.
     * Although this function is primarily used to find a table item and storeserv under NAME and
     * SYSTEM respectively, this function can be passed in any column headers of interest
     * @author Craig Yara
     * @param itemName
     * @param columnNameForItem
     * @param systemName
     * @param systemColumnHeaderName
     * @return
     */
    public int getRowForItemNameAndSystem (String itemName, String columnNameForItem,
                                           String systemName, String systemColumnHeaderName)
    {
        // TODO: adjust so that it is timer based

        int returnValue = 0;

        List headerlist = Arrays.asList(this.getColumnHeadingsList());
        // declaring local variables
        String [] ssmcUIRow;
        List headerList;
        String currentItemName, currentSystemName;


        headerList = Arrays.asList(this.getColumnHeadingsList());

        if (this.isStubbed()) {
            log("Table::isColumnDataAppearForItemInTable === The table's table query is currently stubbed out. ===");
            returnValue = 0;
        }
        else {
            ArrayList <String[]> entireSSMCTable = this.getAllRows();
            int ssmcRowCount = entireSSMCTable.size();

            for(int i=0;i<ssmcRowCount;i++) {


                //Get the SSMC UI values
                ssmcUIRow = entireSSMCTable.get(i);  // GET THE ENTIRE ROW , ARRAY OF STRINGS
                currentItemName = ssmcUIRow[headerList.indexOf(columnNameForItem)];
                currentSystemName = ssmcUIRow[headerList.indexOf(systemColumnHeaderName)];

                // strip off the "(xx)" string if any.  e.g.  "MyVolume (1)"
                if (currentItemName.contains("("))
                {
                    int pos = currentItemName.indexOf("(");
                    currentItemName = currentItemName.substring(0, pos).trim();
                }

                if (currentItemName.equals(itemName) && (currentSystemName.equals(systemName)))
                {
                    returnValue = i+1;
                    break;

                }

            }

        }


        return returnValue;
    }

    /**
     * returns the row number for a particular storeserv system.
     * Although this function is primarily used to find a table item and storeserv under NAME and
     * SYSTEM respectively, this function can be passed in any column headers of interest
     * @param itemName
     * @param columnNameForItem
     * @param systemName
     * @param systemColumnHeaderName
     * @return
     */
    public int getRowForSystem (String systemName, String systemColumnHeaderName)
    {
        // TODO: adjust so that it is timer based

        int returnValue = 0;

        List headerlist = Arrays.asList(this.getColumnHeadingsList());
        // declaring local variables
        String [] ssmcUIRow;
        List headerList;
        String currentItemName, currentSystemName;


        headerList = Arrays.asList(this.getColumnHeadingsList());

        if (this.isStubbed()) {
            log("Table::isColumnDataAppearForItemInTable === The table's table query is currently stubbed out. ===");
            returnValue = 0;
        }
        else {
            ArrayList <String[]> entireSSMCTable = this.getAllRows();
            int ssmcRowCount = entireSSMCTable.size();

            for(int i=0;i<ssmcRowCount;i++) {

                //Get the SSMC UI values
                ssmcUIRow = entireSSMCTable.get(i);  // GET THE ENTIRE ROW , ARRAY OF STRINGS
                currentSystemName = ssmcUIRow[headerList.indexOf(systemColumnHeaderName)];
                if (currentSystemName.equals(systemName))
                {
                    returnValue = i+1;
                    break;

                }

            }

        }


        return returnValue;
    }


    /**
     * @author Craig Yara
     * @param columnValues
     * @return
     */
    public int getRowContainingAllColumnValues (String columnValues [])
    {
        // TODO: adjust so that it is timer based

        int returnValue = 0;

        //List headerlist = Arrays.asList(this.getColumnHeadingsList());
        // declaring local variables
        String [] ssmcUIRow;
        List headerList;
        String currentItemName, currentSystemName;
        List<String> myList = new ArrayList<String>();
        int x;

        boolean allValuesGood;


        if (this.isStubbed()) {
            log("Table::isColumnDataAppearForItemInTable === The table's table query is currently stubbed out. ===");
            returnValue = 0;
        }
        else {
            ArrayList <String[]> entireSSMCTable = this.getAllRows();
            int ssmcRowCount = entireSSMCTable.size();

            for(int i=0;i<ssmcRowCount;i++) {


                //Get the SSMC UI values
                ssmcUIRow = entireSSMCTable.get(i);  // GET THE ENTIRE ROW , ARRAY OF STRINGS

                myList = Arrays.asList(ssmcUIRow);

                allValuesGood = true;

                for (x=0; x<columnValues.length; x++)
                {
                    if ((myList.indexOf(columnValues[x]) == -1))
                    {
                        allValuesGood= false;
                    }
                }

                if (allValuesGood)

                {
                    returnValue = i+1;
                    break;

                }

            }

        }


        return returnValue;
    }



    /**
     * returns the row number for a table item that belongs to a particular storeserv system.
     * In this function, it is assumed that the column header for the item name is "Name" and
     * that the column header for the system is "System"
     * @param itemName
     * @param systemName
     * @author Craig Yara
     * @return
     */
    public int getRowForItemNameAndSystem (String itemName,  String systemName)
    {
        return this.getRowForItemNameAndSystem (itemName, "Name", systemName, "System");
    }


    /**
     * This method checks to see if the table cell at the specified row and column contains a deletebutton.
     *
     * @param rowIndex  The row index of the specified cell. Rows start with index 1.
     * @param colIndex  The column index of the specified cell. Columns start with index 1.
     * @return          'true' if the specified cell contains a link, 'false' otherwise
     */
    public boolean containsDeleteButton(int rowIndex, int colIndex) {
        boolean returnValue = false;
        WebElement rowElement = getRowElement(rowIndex);
        List<WebElement> columnElements;

        // Obtains the column elements of this row
        columnElements = rowElement.findElements(By.xpath("td"));

        // Obtains the specified column in the specified row
        WebElement columnElement = columnElements.get(colIndex-1);

        // If the cell is not of the 'hp-icon' class
        if(!columnElement.getAttribute("class").equals("hp-icon")) {
            return returnValue;
        }

        // Obtains all of the Delete buttons within the specified cell
        List<WebElement> deleteButtonElements = columnElement.findElements(By.cssSelector("div[class='hp-delete']"));

        // If there's 1 or more Delete buttons within the specified cell
        if(deleteButtonElements.size() > 0) {
            returnValue = true;
        }

        // If there's 1 or more button within the specified cell
        if(deleteButtonElements.size() > 0) {
            returnValue = true;
        }

        return returnValue;
    }

    /**
     * This method clicks on the delete button within the table cell at the specified row and column if an delete button exists.
     *
     * @param rowIndex  The row index of the specified cell. Rows start with index 1.
     * @param colIndex  The column index of the specified cell. Columns start with index 1.
     * @throws NoSuchElementException if an delete button isn't found within the specified table cell
     * Added by NileshG on 03/25/2015
     */
    public void clickDeleteButton(int rowIndex, int colIndex) {
        WebElement rowElement = getRowElement(rowIndex);
        List<WebElement> columnElements;

        // Obtains the column elements of this row
        columnElements = rowElement.findElements(By.xpath("td"));

        // Obtains the specified column in the specified row
        WebElement columnElement = columnElements.get(colIndex-1);

        // If the cell is not of the 'hp-icon' class
        if(!columnElement.getAttribute("class").equals("hp-icon")) {
            throw new NoSuchElementException("ERROR!  Specified table cell does not contain an delete button.");
        }

        // Obtains all of the delete buttons within the specified cell
        List<WebElement> deleteButtonElements = columnElement.findElements(By.cssSelector("div[class='hp-delete']"));

        // If there isn't an delete button within the specified cell
        if(deleteButtonElements.size() == 0) {
            throw new NoSuchElementException("ERROR!  Specified table cell does not contain an delete button.");
        }

        // Clicks on the delete button within the specified cell
        deleteButtonElements.get(0).click();
    }

    /**
     * returns boolean value after verifying expected ColumnItemNames from mutliple columns
     * @author NileshG
     * @param columnNameForItem1
     * @param expectedItemName1
     * @param columnNameForItem2
     * @param expectedItemName2
     * @return
     */
    public boolean verifyMultipleColumnData(String columnNameForItem1, String expectedItemName1, String columnNameForItem2,String expectedItemName2)
    {
        boolean returnValue = false;

        List headerlist = Arrays.asList(this.getColumnHeadingsList());
        // declaring local variables
        String [] ssmcUIRow;
        List headerList;
        String currentItemName1, currentItemName2;


        headerList = Arrays.asList(this.getColumnHeadingsList());

        if (this.isStubbed()) {
            log("Table::isColumnDataAppearForItemInTable === The table's table query is currently stubbed out. ===");
            returnValue = false;
        }
        else {
            ArrayList <String[]> entireSSMCTable = this.getAllRows();
            int ssmcRowCount = entireSSMCTable.size();

            for(int i=0;i<ssmcRowCount;i++) {
                //Get the SSMC UI values
                ssmcUIRow = entireSSMCTable.get(i);  // GET THE ENTIRE ROW , ARRAY OF STRINGS
                currentItemName1 = ssmcUIRow[headerList.indexOf(columnNameForItem1)];
                currentItemName2 = ssmcUIRow[headerList.indexOf(columnNameForItem2)];

                // strip off the "(xx)" string if any.  e.g.  "MyVolume (1)"
                if (currentItemName1.contains("("))
                {
                    int pos = currentItemName1.indexOf("(");
                    currentItemName1 = currentItemName1.substring(0, pos).trim();
                }
                // strip off the "(xx)" string if any.  e.g.  "MyVolume (1)"
                if (currentItemName2.contains("("))
                {
                    int pos = currentItemName2.indexOf("(");
                    currentItemName2 = currentItemName2.substring(0, pos).trim();
                }
                if (currentItemName1.equals(expectedItemName1) && (currentItemName2.equals(expectedItemName2)))
                {
                    returnValue = true;
                    break;

                }
            }
        }
        return returnValue;
    }

    /**
     * This method will provide arraylist of rows that are selected
     * @return listOfSelectedRow
     * Created By NileshG on 06/01/2015
     */
    public ArrayList<String> getListOfselectedRow(){

        ArrayList<String> listOfSelectedRow = new ArrayList();
        for(int i=1;i<=getRowCount();i++){
            if(isRowSelected(i)){
                log("Row with index "+i+" is selected");
                listOfSelectedRow.add(String.valueOf(i));
            }
        }
        return listOfSelectedRow;
    }
    /**
     * This method clicks on the load more button in the table
     *
     * @param None
     */
    public void loadMoreItems(int timeOutSeconds){

        if (this.loadMoreBelowLink.exists(timeOutSeconds)) {
            this.loadMoreBelowLink.click();
        }
        else if (this.loadMoreAboveLink.exists(timeOutSeconds)) {
            this.loadMoreAboveLink.click();
        }

    }

    /**
     * This method checks if the column is selected in descending or ascending order
     *
     * @param columnIndex The selected column index
     * @param isAscending True if checking for ascending, false if checking for descending
     * return Returns true if the column is selected in descending or ascending order
     */
    public boolean isColumnAscOrDescSelected(int columnIndex, boolean isAscending) {
        boolean selected = false;
        List<WebElement> columnElements = getColumnHeadingElements();
        if(isAscending) {
            if(columnElements.get(columnIndex).getAttribute("class").contains("sorting_asc")) {
                selected = true;
            }
        } else {
            if(columnElements.get(columnIndex).getAttribute("class").contains("sorting_desc")) {
                selected = true;
            }
        }
        return selected;
    }

    /**
     * This method checks to see if the table cell at the specified row and column contains a close button.
     *
     * @param rowIndex  The row index of the specified cell. Rows start with index 1.
     * @param colIndex  The column index of the specified cell. Columns start with index 1.
     * @return          'true' if the specified cell contains a link, 'false' otherwise
     */
    public boolean containsCloseButton(int rowIndex, int colIndex) {
        boolean returnValue = false;
        WebElement rowElement = getRowElement(rowIndex);
        List<WebElement> columnElements;

        // Obtains the column elements of this row
        columnElements = rowElement.findElements(By.xpath("td"));

        // Obtains the specified column in the specified row
        WebElement columnElement = columnElements.get(colIndex-1);

        // If the cell is not of the 'hp-icon' class
        if(!columnElement.getAttribute("class").equals("hp-icon")) {
            return returnValue;
        }

        // Obtains all of the Close buttons within the specified cell
        List<WebElement> closeButtonElements = columnElement.findElements(By.cssSelector("div[class='hp-close']"));

        // If there's 1 or more Close buttons within the specified cell
        if(closeButtonElements.size() > 0) {
            returnValue = true;
        }

        // If there's 1 or more Close button within the specified cell
        if(closeButtonElements.size() > 0) {
            returnValue = true;
        }
        return returnValue;
    }
    /**
     * This method checks to see if the table cell at the specified row and column contains a deletebutton.
     * This method is used only for maintenance mode.
     *
     * @param rowIndex  The row index of the specified cell. Rows start with index 1.
     * @param colIndex  The column index of the specified cell. Columns start with index 1.
     * @return          'true' if the specified cell contains a link, 'false' otherwise
     */
    public boolean containsDeleteButtonForMaintenance(int rowIndex, int colIndex) {
        boolean returnValue = false;
        WebElement rowElement = getRowElement(rowIndex);
        List<WebElement> columnElements;

        // Obtains the column elements of this row
        columnElements = rowElement.findElements(By.xpath("td"));

        // Obtains the specified column in the specified row
        WebElement columnElement = columnElements.get(colIndex-1);
        // If the cell is not of the 'hp-icon' class
        if(!columnElement.getAttribute("class").equals("hp-icon")) {
            return returnValue;
        }

        // Obtains all of the Delete buttons within the specified cell
        List<WebElement> deleteButtonElements = columnElement.findElements(By.cssSelector("p[class='hp-close']"));

        // If there's 1 or more Delete buttons within the specified cell
        if(deleteButtonElements.size() > 0) {
            returnValue = true;
        }

        // If there's 1 or more button within the specified cell
        if(deleteButtonElements.size() > 0) {
            returnValue = true;
        }

        return returnValue;
    }

    /**
     * This method clicks on the delete button within the table cell at the specified row and column if an delete button exists.
     * This method is used only for maintenance mode.
     * @param rowIndex  The row index of the specified cell. Rows start with index 1.
     * @param colIndex  The column index of the specified cell. Columns start with index 1.
     * @throws NoSuchElementException if an delete button isn't found within the specified table cell
     * Added by NileshG on 03/25/2015
     */
    public void clickDeleteButtonForMaintenance(int rowIndex, int colIndex) {
        WebElement rowElement = getRowElement(rowIndex);
        List<WebElement> columnElements;
        // Obtains the column elements of this row
        columnElements = rowElement.findElements(By.xpath("td"));

        // Obtains the specified column in the specified row
        WebElement columnElement = columnElements.get(colIndex-1);

        // If the cell is not of the 'hp-icon' class
        if(!columnElement.getAttribute("class").equals("hp-icon")) {
            throw new NoSuchElementException("ERROR!  Specified table cell does not contain an delete button.");
        }

        // Obtains all of the delete buttons within the specified cell
        List<WebElement> deleteButtonElements = columnElement.findElements(By.cssSelector("p[class='hp-close']"));

        // If there isn't an delete button within the specified cell
        if(deleteButtonElements.size() == 0) {
            throw new NoSuchElementException("ERROR!  Specified table cell does not contain an delete button.");
        }

        // Clicks on the delete button within the specified cell
        deleteButtonElements.get(0).click();
    }

    public void selectTopLink ()   {
        if (this.topLink.exists (1)) {
            log ("Clicking the TOP link in table");


                this.topLink.click();

            waitForNoSelectedItems();
            this.waitForSelectedItem();
        }
    }

    /**
     * @author Craig Yara
     * @return
     */
    public boolean waitForSelectedItem () {
        log ("  waiting for table selection..");
        int timeOutSeconds = 60;
        WebElement webElement = null;

        // declaring local variables
        boolean itemFound = false;
        boolean done = false;
        long startTime;
        int elapsedTime;
        startTime = System.nanoTime();

        // set start time
        while (done == false) {
            try {
                webElement = browser.getDriver().findElement(By.cssSelector(".dataTables_scrollBody > table > tbody > .hp-selected"));
                // good, object no longer is there
                itemFound = true;
                done = true;

            } catch (Exception e) {
            }

            if (itemFound == false) {

                // get elapsed Time
                elapsedTime = (int) TimeUnit.SECONDS.convert((System.nanoTime() - startTime), TimeUnit.NANOSECONDS);

                // If it has reached the timeout, stop checking; otherwise sleep and try again
                if (elapsedTime >= timeOutSeconds) {
                    done = true;
                }
            }
        }
        return itemFound;
    }


    /**
     * @author Craig Yara
     * @return
     */
    public boolean waitForNoSelectedItems () {
        log ("  waiting for table selection..");
        int timeOutSeconds = 10;
        WebElement webElement = null;

        // declaring local variables
        boolean itemFound = false;
        boolean done = false;
        long startTime;
        int elapsedTime;
        startTime = System.nanoTime();

        // set start time
        while (done == false) {
            try {
                webElement = browser.getDriver().findElement(By.cssSelector(".dataTables_scrollBody > table > tbody > .hp-selected"));
                // good, object no longer is there


            } catch (Exception e) {
                itemFound = true;
                done = true;
            }

            if (itemFound == false) {

                // get elapsed Time
                elapsedTime = (int) TimeUnit.SECONDS.convert((System.nanoTime() - startTime), TimeUnit.NANOSECONDS);

                // If it has reached the timeout, stop checking; otherwise sleep and try again
                if (elapsedTime >= timeOutSeconds) {
                    done = true;
                }
            }
        }
        return itemFound;
    }


    /**
     * @author Craig Yara
     * @param itemNames
     * @return
     */
    private ArrayList<String> removeParentheseFromNames (ArrayList<String> itemNames)
    {
        ArrayList<String> returnList = itemNames;
        int index;
        String temp;
        int position;

        for (index=0; index<returnList.size(); index++)
        {
            if (returnList.get (index).contains("("))
            {
                temp = returnList.get (index).replaceAll("\\(\\d+\\)", "").trim();
                returnList.set(index, temp);
            }
        }


        return returnList;
    }


    public void dragAndDropColumnHeading(String columnHeadingToDrag, String columnDestination) {
        int indexColumnFrom = this.getColumnIndex(columnHeadingToDrag);
        int indexColumnTo = this.getColumnIndex(columnDestination);
        WebElement columnHeadingFromElement = getColumnHeadingElements().get(indexColumnFrom-1);
        WebElement columnHeadingToElement = getColumnHeadingElements().get(indexColumnTo-1);
        (new Actions(browser.getDriver()))
                .dragAndDrop(columnHeadingFromElement, columnHeadingToElement).perform();
    }



    /**
     * This methods finds all of the web elements that represent the column headings of the footer table.
     * If the footer table does not exist or is not displayed, it will keep trying until the default timeout has been reached.
     * <p>
     * <b>Note:</b> This method does not check whether the locator of this web element is stubbed out or not.
     * Thus, it is the responsibility of the caller NOT to call this method if the locator is stubbed out.
     *
     * @return  a list of {@link WebElement} instances representing the column headings of the footer table
     * @throws NoSuchElementException if the table web element does not exist and/or is not displayed after timeout
     */
    private List<WebElement> getAllElementsFromFooterTable() {
        List<WebElement> headingElements = null;
        WebElement tableElement = null;

        if (ElementUtil.hasCSSClass(getWebElement(),"dataTables_wrapper")){
            tableElement = getWebElement().findElement(By.cssSelector(".dataTables_scrollFootInner > table"));
        }
        else{
            // getting the table web element with the default timeout
            tableElement = getWebElement();
        }
        // Check the CSS classes of this web element to determine
        // if this is a Piano hp-master-table
        if (ElementUtil.hasCSSClass(tableElement, "ssmc-has-footer")) {

            // Get the headings <td> elements from the table above this one
            headingElements = tableElement.findElements(By.xpath("../../div/div/table/tfoot/tr/th"));

            // if the result is empty, we may have a different table structure that has this class
            if (headingElements.size() == 0) {
                // try to get the headings another way, because we may see the table structure as
                headingElements = tableElement.findElements(By.xpath("tfoot/tr/th"));

                if (headingElements.size() == 0) {

                    // check one more try to see if we can get the column headings within this structure
                    // <table class="hp-master-table dataTable">
                    //   <thead>
                    //     <tr>
                    //       <th>
                    //   <tbody>
                    headingElements = tableElement.findElements(By.xpath("tfoot/tr/th"));
                    if (headingElements.size() == 0) {
                        warning("Warning!  This hp-footer-table may not have a known table structure.  Locator: " + this.getLocator());
                    }
                }
            }
        }
        else if (ElementUtil.hasCSSClass(tableElement, "dataTable")) {
            // This is a simple Piano data table. It has a structure like
            // <div class="dataTables_wrapper">
            //   <table class="dataTable">
            //     <thead>...</thead>
            //     <tbody>...</tbody>
            //
            // Get the headings <td> elements from this table
            headingElements = tableElement.findElements(By.xpath("tfoot/tr/th"));

            if (headingElements.size() == 0) {
                // check one more try to see if we can get the column headings within this structure
                // <table class="hp-master-table dataTable">
                //   <thead>
                //     <tr>
                //       <th>
                //   <tbody>
                headingElements = tableElement.findElements(By.xpath("tfoot/tr/th"));
                if (headingElements.size() == 0) {
                    warning("Warning!  This dataTable may not have a known table structure.  Locator: " + this.getLocator());
                }
            }
        }
        else {
            // This is not a known table type. Show a warning and
            // get the headings <td> elements from this table
            warning("Warning!  This table does not have a known table structure.  Locator: " + this.getLocator());
            headingElements = tableElement.findElements(By.xpath("thead/tr/th"));
        }

        return headingElements;
    }

    /**
     * This method returns a String array of all the column headings for the footer table.  If there are no columns, this method
     * returns an empty array.
     *
     * @return       String array of column headings for the footer table
     */
    private String[] getAllValuesFromFooterTable () {
        // declare local variables
        String[] returnValue = null;

        if (this.isStubbed()) {
            returnValue = new String[] { "COLUMN" };
            log("=== The table's headings query is currently stubbed out.  Returning a value '" + returnValue + "' ===");
        } else {
            // if the table refreshed while we are getting values, then a stale element is being thrown. To get around
            //     this will catch the stale element and try again
            boolean done = false;
            int retries = 0;

            while (!done) {
                try {

                    // getting the table web element with the default timeout
                    // and then get its headings elements
                    List<WebElement> headingElements = getAllElementsFromFooterTable();

                    // get and trim the text of the heading elements
                    returnValue = ElementUtil.getText(headingElements, true);

                    done = true;
                } catch(StaleElementReferenceException e){
                    retries++;

                    // have we tried the maximum retries?
                    if (retries == MAXRETRIES) {
                        done = true;

                        warning("ERROR!  StaleElementReference Exception - " + e.getMessage());
                        e.printStackTrace();
                        throw e;
                    }
                }
            }
        }

        return returnValue;
    }
    /**
     * This method returns the total value for a specific column number from footer table.  If no column heading is found, an
     * empty string is returned.
     * <p>
     * Column indexes start with 1.
     *</p>
     * <p>
     * NOTE:  The table is first checked to see if it exists.  If the table does not exist, an error will be logged
     * and the test will stop.
     * </p>
     * @param columnIndex  [INT] the column index, starting with 1, for the heading desired
     * @return             the total column value from footer table
     */
    public int getTotalValueOfColumnFromFooterTable (String columnName) {
        // declaring local variables
        String returnValue;
        int columnIndex=getColumnIndex(columnName);
        int val=0;
        if((columnIndex < 1) || (columnIndex > getAllElementsFromFooterTable().size())){
            throw new NoSuchElementException("No such column index, '" + columnIndex + "', within the column elements - '" + getAllElementsFromFooterTable().size() + "'");
        }

        if (this.isStubbed()) {
            returnValue = "VALUE";
            log("=== The table's headings query is currently stubbed out.  Returning a value '" + returnValue + "' ===");
        } else {
            // getting the table web element with the default timeout
            // and then get the specified column heading element

            String stringVal=getAllValuesFromFooterTable()[columnIndex-1];
            log("StringValue: "+stringVal);

            if(stringVal.equals("")){
                return 0;
            }
            // get the column heading value
             val=Integer.parseInt(stringVal.replaceAll(",",""));
        }

        return val;
    }

    /**
     * This function lists the activities which contains the itemName(Activity Name) in the given column Index
     * @param itemName
     * @param columnIndex
     * @return
     */
    public ArrayList<String> findItemsInColumn (String itemName, int columnIndex){
        ArrayList<String> returnValue ;
        String temp, itemWithoutParenthese;

        if (this.isStubbed()) {
            log("Table::selectRow === The table's table query is currently stubbed out. ===");
            returnValue = null;
        } else {
            // Make sure the column index is valid.
            if (columnIndex < 1) {
                throw new NoSuchElementException("Table::findItemInColumn -- Invalid column index, " + columnIndex);
            }

            // if the table refreshed while we are getting values, then a stale element is being thrown. To get around
            //     this will catch the stale element and try again
            boolean done = false;
            int retries = 0;
            returnValue = new ArrayList<String>();
            int index =0;
//            while (!done) {
            try {
                // getting the table web element with the default timeout
                // and then get all of the row elements
                List<WebElement> rowElements = getRowElements();

                // get the row count
                int rowCount = rowElements.size();

                // for each row, check the text of the specified column

                boolean found = false;
                for (int i = 0; i < rowCount; i++) {
                    WebElement row = rowElements.get(i);

                    // get the specified column element of this row
                    WebElement column;
                    try {
                        column = row.findElement(By.xpath("td[" + columnIndex + "]"));
                    } catch (NoSuchElementException e) {
                        // This row does not have this column; skip it.
                        // It occurs when an empty table has a row showing "No data".
                        continue;
                    }

                    // check the column text with the itemName -- if the same, then this is the row
                    // the user wants
                    temp = column.getText();
                    itemWithoutParenthese = temp;
                    if (temp.contains("(") && (itemName.contains("(") == false)) {
                        itemWithoutParenthese = temp.substring(0, temp.indexOf("(")).trim();
                    }
                    if (itemWithoutParenthese.contains(itemName)) {

                        returnValue.add(index++, itemWithoutParenthese);

                        // get out of for loop
//                            i = rowCount;

//                            found = true;
                    }
                }
                if( returnValue.size() ==0)
                    found =true;
                if(found){
                    returnValue = null;
                }
                done = true;

            }  catch(StaleElementReferenceException e){
                retries++;

                // have we tried the maximum retries?
                if (retries == MAXRETRIES) {
                    done = true;

                    warning("ERROR!  StaleElementReference Exception - " + e.getMessage());
                    e.printStackTrace();
                    throw e;
                }
            }
//            }
        }

        return returnValue;
    }

    /**
     * The table matches for the particular string other than no matches when there is no data in the table
     * @return
     */
    public String getNoMatchesText() {
        String returnValue = "";
        By divSelector = By.cssSelector(".dataTables_empty");
        WebElement noDataText;
        WebElement table = this.getWebElement();

        // If the table has only one row
        if (this.getRowCount() == 1) {
            String cellText = this.getCellData(1, 1);
            String cellSubstring = cellText.split(" ")[0].toLowerCase();

            // If the first word of the cell text is 'no'
            if (cellSubstring.equals("no")) {
                returnValue = cellText;
            }
        }
        // If the return value is still an empty string and the "No matches" div is present
        if (returnValue.equals("") && getWebElement().findElements(divSelector).size() > 0) {
            noDataText = getWebElement().findElement(divSelector);
            returnValue = noDataText.getText();
        }

        return returnValue;
    }


}
