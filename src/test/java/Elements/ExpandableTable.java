/*
 * © Copyright 2002-2015 Hewlett Packard Enterprise Development LP
 */

package Elements;



import org.openqa.selenium.*;
import org.openqa.selenium.By;
import java.util.List;

import static Helpers.Utility.log;
import static Helpers.Utility.warning;

// IMPLEMENTATION NOTES:
//
// An expandable table typically has an HTML structure like one of the two structures below:
//
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
 * This class is used to interact with a table that contains expandable rows.
 * In particular, it knows how to handle tables rendered by jQuery and Piano.
 *
 * Your selenium locator should uniquely match the following element from the example in this class' code and contains
 * rows that are collapsible.
 * Element: <div id="example-table_wrapper" class="dataTables_wrapper">
 *
 * Created by CharlesL on 7/16/14.
 */
public class ExpandableTable extends Table {

    // - - - - - Class attributes - - - - -
    private final int MAXRETRIES = 0;


    // - - - - - Constructor - - - - -

    /**
     * Constructor used to reference this class.
     *
     * @param  locator     the Selenium {@link By locator} for locating this web element
     */
    public ExpandableTable (By locator) {
        super(locator);
    }

    /**
     * This method expands an expandable row in the table.  It first checks to see if the row is expandable.  If it is expandable,
     * the row is expanded.  If the row is not expandable, a NoSuchElementException is thrown.
     *
     * <p>
     * <b><u>NOTE:</u></b>  Currently, there isn't a way to determine if the row has already been expanded.
     * </p>
     * @param rowIndex   the row index to expand
     */
    public void expand (int rowIndex) {
        int retries = 0;
        boolean done = false;
        WebElement div = null;

        if (this.isStubbed()) {
            log("Table::expand === The table's table query is currently stubbed out. ===");
        }
        else {
            while (!done) {
                try {
                    // check to see if there is a DIV tag within this row with class name 'hp-collapser'
                    // -- this indicates this row can be expanded
                    div = this.findExpandableDiv(rowIndex);

                    if (div != null) {
                        div.click();
                    } else {
                        throw new NoSuchElementException("Table::expand -- The row is not expandable.");
                    }
                    done = true;

                } catch (StaleElementReferenceException e) {
                    retries++;

                    // have we tried the maximum retries?
                    if (retries == MAXRETRIES) {
                        done = true;

                        warning("ERROR!  StaleElementReference Exception when using Table.expand() - " + e.getMessage());
                        e.printStackTrace();
                        throw e;
                    }
                }
            }
        }

    }

    /**
     * This method collapses an expanded row in the table.  It first checks to see if the row is expanded. If it is
     * expanded, the row is collapsed.
     *
     * @param rowIndex   the row index to collapse
     */
    public void collapse (int rowIndex) {
        int retries = 0;
        boolean done = false;
        WebElement div = null;

        if (this.isStubbed()) {
            log("Table::expand === The table's table query is currently stubbed out. ===");
        }
        else {
            while (!done) {
                try {
                    if (isExpanded(rowIndex)) {
                        // check to see if there is a DIV tag within this row with class name 'hp-collapser'
                        div = this.findExpandableDiv(rowIndex);

                        if (div != null) {
                            div.click();
                        } else {
                            throw new NoSuchElementException("Table::collapse -- The row is not expandable.");
                        }
                    }
                    done = true;
                } catch (StaleElementReferenceException e) {
                    retries++;

                    // have we tried the maximum retries?
                    if (retries == MAXRETRIES) {
                        done = true;

                        warning("ERROR!  StaleElementReference Exception when using Table.isRowExpandable() - " + e.getMessage());
                        e.printStackTrace();
                        throw e;
                    }
                }
            }
        }
    }

    /**
     * This method checks to see if the specific row is expandable.  Being expandable means the row has an icon to click
     * to expand that row, where other rows are hidden behind this row.  If the row is expandable, a value of 'true' will
     * be returned.
     * <p>
     * <b><u>NOTE:</u></b>  If the table does not exist, a NoSuchElementException is thrown.
     * </p>
     *
     * @param rowIndex    [INT] the row number (starting with index 1) of the row to check
     * @return            a boolean value, true if the row is expandable, false otherwise
     */
    public boolean isRowExpandable (int rowIndex) {
        int retries = 0;
        boolean done = false;
        boolean returnValue = false;
        WebElement div = null;

        if (this.isStubbed()) {
            log("Table::isRowExpandable === The table's table query is currently stubbed out. ===");
            returnValue = true;
        }
        else {
            while (!done) {
                try {
                    // check to see if there is a DIV tag within this row with class name 'hp-collapser'
                    // -- this indicates this row can be expanded
                    div = this.findExpandableDiv(rowIndex);

                    if (div != null)  {
                        returnValue = true;
                    }
                    done = true;
                } catch (StaleElementReferenceException e) {
                    retries++;

                    // have we tried the maximum retries?
                    if (retries == MAXRETRIES) {
                        done = true;

                        warning("ERROR!  StaleElementReference Exception when using Table.isRowExpandable() - " + e.getMessage());
                        e.printStackTrace();
                        throw e;
                    }
                }
            }
        }

        return returnValue;
    }

    /**
     * This method checks to see if the row in the table is expanded.  If the row is expanded, this method return
     * 'true'.  Otherwise, this method returns 'false'.
     *
     * @param rowIndex   [INT]  the row index being checked
     * @return           a boolean 'true' if the row is expanded, 'false' otherwise.
     */
    public boolean isExpanded (int rowIndex) {
        // declaring local variables
        boolean returnValue = false;

        if (this.isStubbed()) {
            log("Table::isExpanded === The table's table query is currently stubbed out. ===");
        } else {
            // getting the table web element with the default timeout
            // and then get the specified row
            WebElement row = getRowElement(rowIndex);

            // check the CSS classes of the row element
            returnValue = ElementUtil.hasCSSClass(row, "hp-expanded");
        }

        return returnValue;
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // Method:  findExpandableDiv
    //
    // Purpose:  to find the DIV tag within a row to determine if that row can be expanded or not.
    //
    // Parameters:
    // 1 - rowIndex -- [INT] the row index to examine
    //
    // Return:  a WebElement of the DIV object
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private WebElement findExpandableDiv (int rowIndex) {
        // declaring local variables
        WebElement returnValue = null;


        // getting the table web element with the default timeout
        // and then get all of the row elements
        List<WebElement> rowElements = getRowElements();

        // get the list of columns for this row
        // get a list of columns attached to this row
        // rowElements is based on index starting at 0 while rowIndex is based on index starting at 1
        List<WebElement> columnElements = rowElements.get(rowIndex-1).findElements(By.xpath("td"));

        try {
            // does the first column have a child with a div tag and the class name 'hp-collapser'
            WebElement div = columnElements.get(0).findElement(By.tagName("div"));

            if (div != null) {
                if (ElementUtil.hasCSSClass(div,"hp-collapser")) {
                    returnValue = div;
                } else {
                    returnValue = null;
                }
            }
        } catch (Exception e) {
            // make sure returnValue is set to null
            returnValue = null;
        }

        return returnValue;
    }


    /**
     * @author Craig Yara
     */
    public void expandSelectedRow () {
        String returnValue = "";

        log ("Expand the Selected Activity Row");

        // If the Activity Pane is stubbed
        if(isStubbed()) {
            returnValue = "VALUE";
            log("=== The locator for the Activity Pane is currently stubbed out.  Returning a value of '" + returnValue + "'. ===");
        } else {
            try {
                WebElement expander = getWebElement().findElement(By.cssSelector("#hp-activities > tbody > .hp-selected > .hp-icon > .hp-collapser"));

                log ("  clicking expander");
                expander.click();;


            } catch (NoSuchElementException e) {
                log ("expandSelectedRow - no activity selected");
            }
        }


    }



}