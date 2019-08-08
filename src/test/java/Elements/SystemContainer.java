/*
 * © Copyright 2002-2015 Hewlett Packard Enterprise Development LP
 */

package Elements;

import org.openqa.selenium.By;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static Helpers.Utility.log;


// IMPLEMENTATION NOTES:
//
// A system container typically has an HTML structure like below:
//  Note: '#' in the following HTML stands for an integer
//
// <ol class="hp-physical-switch-rows">
//   <li class="hp-physical-switch-row" id="1">
//   ... (More "chartDivId#" div tags may follow)
//   </li>
// </ol>
//

/**
 * This class is used to interact with all of the Systems within the Remote Copy Group configuration container.
 *
 * <p><b>
 * NOTE: Typically, the System container contains rows of systems. Each row contains 2 system Names and port check boxes
 * if applicable *
 * </b></p>
 *

 *
 * Created by Naga on 7/7/15.
 */
public class SystemContainer extends BaseElement {


    // - - - - - attributes - - - - -


    // - - - - - constructor - - - - -

    /**
     * The constructor to a SyncChartContainer, which requires a By locator argument for the container that contains
     * a series of charts.
     *
     * @param chartLocator  The Selenium {@link org.openqa.selenium.By locator} for locating this web element
     */
    public SystemContainer(By chartLocator) {
        super(chartLocator);
    }



    /**
     * This method selects the system contained within the system container.
     *
     * @return  None
     */
    public void selectSystem(int rowIndex,int colIndex,String systemName) {
        List<WebElement> returnValue = null;

        if (isStubbed()) {
            log("=== This SyncChartContainer is currently stubbed out.  Returning an null List. ===");
        } else {
            // Obtains the container holding the collection of rows
            WebElement container = getWebElement();
            //Returns the number of rows that contains the system
            returnValue = container.findElements(By.cssSelector(".hp-physical-switch-row"));
            if(returnValue.size() == 0){
                throw new NoSuchElementException("The item '" + systemName + "' is not showing among the list of items in the menu.");
            }
        }


    }



}
