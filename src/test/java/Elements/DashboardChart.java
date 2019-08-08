/*
 * © Copyright 2002-2015 Hewlett Packard Enterprise Development LP
 */

package Elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static com.hp.piano.test.ui.elements.Globals.*;

import static com.hp.piano.test.ui.elements.Globals.browser;
import static com.hp.tpd.test.helpers.Utility.log;

// IMPLEMENTATION NOTES:
//
// A dashboard chart typically has an HTML structure like below:
//
// <div id="example-chart-id" class="hp-plot">
//   <canvas class="flot-base">
//   <div class="flot-text">
//     <div class="flot-x-axis">...</div>
//     <div class="flot-y-axis">...</div>
//   </div>
//   <canvas class="flot-overlay">
// </div>
// <ol id="example-legend-id" class="hp-plot-legend">
//   <li>...</li>
//   <li>...</li>
// </ol>
//

/**
 * This class is used to interact with charts on the Dashboard screen, specifically the Historical Capacity chart.
 *
 * Your selenium locator should uniquely match the following element from the example found in this class' code.
 * Element: <div id="example-utilization-allocation-legend-id" class="hp-plot">
 * Legend element: <ol id="example-legend-id" class="hp-plot-legend">
 *
 * Created by millera on 11/11/2014.
 */
public class DashboardChart extends BaseElement {

    // - - - - - attributes - - - - -
    By legendLocator;

    // - - - - - constructor - - - - -

    /**
     * The constructor to a DashboardChart, which requires a By locator argument for the container that contains
     * a series of charts.
     *
     * @param chartLocator  The Selenium {@link org.openqa.selenium.By locator} for locating this web element
     */
    public DashboardChart(By chartLocator, By legendLocator) {
        super(chartLocator);
        this.legendLocator = legendLocator;
    }


    /**
     * This method returns all of the labels of the specified axis (either "x" or "y") on this Dashboard chart.
     *
     * @param axis  A single-character string indicating which axis to obtian labels from, either "x" or "y"
     * @return      An array of axis labels, in String form
     */
    public String[] getAxisLabels(String axis) {
        String[] returnList = null;

        // If the x-axis was specified
        if(axis.equals("x")) {
            returnList = getXAxisLabels();
        }
        // If the y-axis was specified
        else if(axis.equals("y")) {
            returnList = getYAxisLabels();
        }
        // If neither the x-axis or y-axis was specified (an invalid axis was given)
        else {
            log("WARNING!  Invalid axis given, only \"x\" and \"y\" are valid axes.  Returning an empty String array.");
            returnList = new String[]{};
            return returnList;
        }

        return returnList;
    }


    /**
     * This method returns this Dashboard chart's x-axis labels as an array of Strings.
     *
     * @return  An array of x-axis labels, in String form
     */
    private String[] getXAxisLabels() {
        String[] returnList = null;
        ArrayList<String> tempList = new ArrayList<String>();

        // If this DashboardChart is stubbed
        if (isStubbed()) {
            log("=== This DashboardChart is currently stubbed out.  Returning an empty String array. ===");
            returnList = new String[]{};
        } else {
            // Obtains the chart element
            WebElement dashboardChart = getWebElement();

            // Obtains all of the x-axis label elements
            List<WebElement> xAxisLabelElements = dashboardChart.findElements(By.cssSelector(".flot-text .flot-x-axis .tickLabel"));

            // If one or more x-axis labels were obtained
            if(xAxisLabelElements.size() > 0) {
                // Adds each axis label to a temporary ArrayList
                for(WebElement xAxisLabel : xAxisLabelElements) {
                    tempList.add(xAxisLabel.getText());
                }

                // Converts the ArrayList of Strings to an Array of Strings
                returnList = new String[tempList.size()];
                for (int i = 0; i < tempList.size(); i++) {
                    returnList[i] = tempList.get(i);
                }
            }
        }

        return returnList;
    }

    /**
     * This method returns this Dashboard chart's y-axis labels as an array of Strings.
     *
     * @return  An array of y-axis labels, in String form
     */
    private String[] getYAxisLabels() {
        String[] returnList = null;
        ArrayList<String> tempList = new ArrayList<String>();

        // If this DashboardChart is stubbed
        if (isStubbed()) {
            log("=== This DashboardChart is currently stubbed out.  Returning an empty String array. ===");
            returnList = new String[]{};
        } else {
            // Obtains the chart element
            WebElement dashboardChart = getWebElement();

            // Obtains all of the y-axis label elements
            List<WebElement> yAxisLabelElements = dashboardChart.findElements(By.cssSelector(".flot-text .flot-y-axis .tickLabel"));

            // If one or more y-axis labels were obtained
            if(yAxisLabelElements.size() > 0) {
                // Adds each axis label to a temporary ArrayList
                for(WebElement xAxisLabel : yAxisLabelElements) {
                    tempList.add(xAxisLabel.getText());
                }

                // Converts the ArrayList of Strings to an Array of Strings
                returnList = new String[tempList.size()];
                for (int i = 0; i < tempList.size(); i++) {
                    returnList[i] = tempList.get(i);
                }
            }
        }

        return returnList;
    }


    /**
     * This method returns this Dashboard chart's legend labels as an array of Strings.
     *
     * @return  An array of legend labels, in String form
     */
    public String[] getLegendLabels() {
        String[] returnList = null;
        ArrayList<String> tempList = new ArrayList<String>();

        // If this DashboardChart is stubbed
        if (isStubbed()) {
            log("=== This DashboardChart is currently stubbed out.  Returning an empty String array. ===");
            returnList = new String[]{};
        } else {
            // Obtains the chart element
            WebElement chartLegend = browser.findElement(legendLocator);

            // Obtains all of the legend label elements
            List<WebElement> yAxisLabelElements = chartLegend.findElements(By.cssSelector("li .hp-key .hp-name"));

            // If one or more legend labels were obtained
            if(yAxisLabelElements.size() > 0) {
                // Adds each legend label to a temporary ArrayList
                for(WebElement xAxisLabel : yAxisLabelElements) {
                    tempList.add(xAxisLabel.getText());
                }

                // Converts the ArrayList of Strings to an Array of Strings
                returnList = new String[tempList.size()];
                for (int i = 0; i < tempList.size(); i++) {
                    returnList[i] = tempList.get(i);
                }
            }
        }

        return returnList;
    }
}
