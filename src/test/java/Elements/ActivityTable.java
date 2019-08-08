/*
 * © Copyright 2002-2015 Hewlett Packard Enterprise Development LP
 */

package Elements;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static Helpers.Utility.log;
import static Helpers.Utility.sleep;
import static Helpers.Utility.warning;

// IMPLEMENTATION NOTES:
//
// An activity table typically has an HTML structure like below:
//
// <table id="example-id" class="hp-activities hp-selectable dataTable">
//   <thead>...</thead>
//   <tbody>...</tbody>
// </table>
//

/**
 * This class is an extension of the ExpandableTable class (and therefore, the Table class as well) and is used to
 * interact with the table found on the Activity screen.
 *
 * Your selenium locator should uniquely match the following element from the example found in this class' code.
 * Element: <table id="example-id" class="hp-activities hp-selectable dataTable">
 *
 *
 */
public class ActivityTable extends ExpandableTable {

    // - - - - - Class attributes - - - - -

    private final int MAXRETRIES = 0;


    // - - - - - Constructor - - - - -

    /**
     * Constructor used to reference this class.
     *
     * @param locator the Selenium {@link Elements.By locator} for locating this web element
     */
    public ActivityTable(org.openqa.selenium.By locator) {
        super(locator);
    }


    // - - - - - Class Methods - - - - -

    /**
     * This method obtains the details row of the specified row index by expanding the specified row
     *
     * @param rowIndex  The index of a Activity Table row
     * @return          A WebElement referring to the details row of the specified row
     */
    protected WebElement getDetailsRow(int rowIndex) {
        WebElement returnValue = null;

        // Selects the specified table row
        if(!isRowSelected(rowIndex)) {
            selectRow(rowIndex);
        }
        // If the specified Activity table row isn't expanded
        if(!isExpanded(rowIndex)) {
            // Expand the specified table row
            expand(rowIndex);

            // If the specified table row still isn't expanded
            if(!isExpanded(rowIndex)) {
                log("ActivityTable::getDetailsRow === The notification details of the specified row can't be obtained " +
                        "as the specified row isn't expanded.  Returning null WebElement. ===");
                return returnValue;
            }
        }
        sleep(5,TimeUnit.SECONDS);
        // Obtains the details row of the selected table row
        returnValue = getWebElement().findElement(By.cssSelector(".hp-row-details-row.hp-selected"));

        return returnValue;
    }


    /**
     * This method obtains the index of the currently selected row.  Note: Row indicies start at 1 instead of 0.
     *
     * @return  The integer index of the currently selected row
     */
    public int getSelectedRowIndex() {
        int returnValue = -1;
        boolean rowFound = false;

        // If the table is stubbed
        if (this.isStubbed()) {
            log("ActivityTable::getSelectedRowIndex === The table's table query is currently stubbed out.  Returning value '" + returnValue + "' ===");
        } else {
            // Obtains all row elements
            List<WebElement> rowElements = null;
            try {
                rowElements = getRowElements();
            }
            catch (org.openqa.selenium.StaleElementReferenceException e) {
                // CRAIG: added try/catch - redraw of activity at random times causing stale element exceptions
                sleep(4, TimeUnit.SECONDS);
                rowElements = getRowElements();
            }

            // For each obtained row element
            for(int i = 0; i < rowElements.size() && !rowFound; i++) {
                // Obtains the current row element
                WebElement rowElement = rowElements.get(i);

                // If the row is of the 'hp-selected' class
                if(rowElement.getAttribute("class").contains("hp-selected")) {
                    // Obtians the 1-based index of the selected row
                    returnValue = i + 1;
                    rowFound = true;
                }
            }
        }

        return returnValue;
    }


    /**
     * This method checks to see if the given Activity Table details row is expanded or not.
     *
     * @param rowIndex  The index of a Activity Table details row
     * @return          True if the Details section is expanded, false otherwise
     */
    private boolean isEventTaskDetailsSectionExpanded(int rowIndex) {
        boolean returnValue = false;
        WebElement detailsRow;

        // If the table is stubbed
        if (this.isStubbed()) {
            log("ActivityTable::isEventTaskDetailsSectionExpanded === The table's table query is currently stubbed out. ===");
        } else {
            // Obtains the details row of the selected table row
            detailsRow = getDetailsRow(rowIndex);

            // Obtains the Details section to be expanded
            List<WebElement> detailsSection = detailsRow.findElements(By.cssSelector(".hp-alert-events.hp-collapsible"));

            // If the Details section to be exapnded isn't an 'Event details' section
            if(detailsSection.size() == 0) {
                // Obtains the Details section to be expanded
                detailsSection = detailsRow.findElements(By.cssSelector(".hp-task-events.hp-collapsible"));
            }

            // Checks the CSS classes of the row element
            returnValue = !ElementUtil.hasCSSClass(detailsSection.get(0), "hp-collapsed");
        }

        return returnValue;
    }


    /**
     * This method expands the expandable Details section (either the Event Details or Task Detail sections) in the
     * given Activity Table details row.
     *
     * @param rowIndex  The index of a Activity Table details row
     * @throws          org.openqa.selenium.NoSuchElementException if the Details section of the given Activity Table
     *                  details row is not expandable
     */
    private void expandEventTaskDetailsSection(int rowIndex) {
        int retries = 0;
        boolean done = false;
        WebElement detailsRow, label = null;

        // If the table is stubbed
        if (this.isStubbed()) {
            log("ActivityTable::expandEventTaskDetailsSection === The table's table query is currently stubbed out. ===");
        }
        else {
            while (!done) {
                try {
                    // Obtains the details row of the selected table row
                    detailsRow = getDetailsRow(rowIndex);

                    // Sets the name of the Details section to be expanded
                    String sectionName = "Event details";
                    // Obtains the Details section to be expanded
                    List<WebElement> detailsSection = detailsRow.findElements(By.cssSelector(".hp-alert-events.hp-collapsible"));

                    // If the Details section to be exapnded isn't an 'Event details' section
                    if(detailsSection.size() == 0) {
                        // Sets the name of the Details section to be expanded
                        sectionName = "Task Detail";
                        // Obtains the Details section to be expanded
                        detailsSection = detailsRow.findElements(By.cssSelector(".hp-task-events.hp-collapsible"));
                    }

                    // Obtians the label that will be clicked on to expand the Details section
                    label = detailsSection.get(0).findElement(By.jQuery("label:contains(" + sectionName + ")"));

                    if (label != null) {
                        label.click();
                    } else {
                        throw new NoSuchElementException("ActivityTable::expandEventTaskDetailsSection -- The Details " +
                                "section within the given Activity Table details row is not expandable.");
                    }
                    done = true;

                } catch (StaleElementReferenceException e) {
                    retries++;

                    // have we tried the maximum retries?
                    if (retries == MAXRETRIES) {
                        done = true;

                        warning("ERROR!  StaleElementReference Exception when using ActivityTable.expandEventTaskDetailsSection() - " + e.getMessage());
                        e.printStackTrace();
                        throw e;
                    }
                }
            }
        }
    }


    /**
     * This method collapses the expandable Details section (either the Event Details or Task Detail sections) in the
     * given Activity Table details row.  The Details section is collapsed only if the section is expanded.
     *
     * @param rowIndex  The index of a Activity Table details row
     * @throws          org.openqa.selenium.NoSuchElementException if the Details section of the given Activity Table
     *                  details row is not expandable
     */
    public void collapseEventTaskDetailsSection(int rowIndex) {
        int retries = 0;
        boolean done = false;
        WebElement detailsRow, label = null;

        // If the table is stubbed
        if (this.isStubbed()) {
            log("ActivityTable::collapseEventTaskDetailsSection === The table's table query is currently stubbed out. ===");
        }
        else {
            while (!done) {
                try {
                    if (isEventTaskDetailsSectionExpanded(rowIndex)) {
                        // Obtains the details row of the selected table row
                        detailsRow = getDetailsRow(rowIndex);

                        // Sets the name of the Details section to be expanded
                        String sectionName = "Event details";
                        // Obtains the Details section to be expanded
                        List<WebElement> detailsSection = detailsRow.findElements(By.cssSelector(".hp-alert-events.hp-collapsible"));

                        // If the Details section to be exapnded isn't an 'Event details' section
                        if(detailsSection.size() == 0) {
                            // Sets the name of the Details section to be expanded
                            sectionName = "Task Detail";
                            // Obtains the Details section to be expanded
                            detailsSection = detailsRow.findElements(By.cssSelector(".hp-task-events.hp-collapsible"));
                        }

                        // Obtians the label that will be clicked on to expand the Details section
                        label = detailsSection.get(0).findElement(By.jQuery("label:contains(" + sectionName + ")"));

                        if (label != null) {
                            label.click();
                        } else {
                            throw new NoSuchElementException("ActivityTable::collapseEventTaskDetailsSection -- The " +
                                    "Details section within the given Activity Table details row is not expandable.");
                        }
                    }
                    done = true;
                } catch (StaleElementReferenceException e) {
                    retries++;

                    // have we tried the maximum retries?
                    if (retries == MAXRETRIES) {
                        done = true;

                        warning("ERROR!  StaleElementReference Exception when using ActivityTable.collapseEventTaskDetailsSection() - " + e.getMessage());
                        e.printStackTrace();
                        throw e;
                    }
                }
            }
        }
    }


    /**
     * This method obtains the notification details of the specififed Activity table row after selecting the specified
     * row and expanding it, if needed.  Note: Row indicies start at 1 instead of 0.
     *
     * @param rowIndex  The index of the Activity Table row to get notification details from
     * @return          A String containing the notification details of the specfied row
     */
    public String getNotificationDetails(int rowIndex) {
        String returnValue = "";
        WebElement detailsRow;

        // If the table is stubbed
        if (this.isStubbed()) {
            returnValue = "VALUE";
            log("ActivityTable::getNotificationDetails === The table's table query is currently stubbed out.  Returning value '" + returnValue + "' ===");
        } else {
            // Obtains the details row of the selected row
            detailsRow = getDetailsRow(rowIndex);

            // Obtains the notification details of the selected details row
            returnValue = detailsRow.findElement(By.className("hp-details")).getText();

            // Collapses the specified Activity Table row
            collapse(rowIndex);
        }

        return returnValue;
    }


    /**
     * This method obtains a list of section titles within the specified Activity Table row.  Note: Row indicies start
     * at 1 instead of 0.
     *
     * @param rowIndex  The index of the Activity Table row to get section titles from
     * @return          An array of Strings containing section titles from the specified row
     */
    public String[] getDetailsSectionTitles(int rowIndex) {
        String[] returnValue = {};
        boolean sectionFound = false;
        WebElement detailsRow, detailsSection;
        List<WebElement> detailsSectionTitles;

        // If the table is stubbed
        if (this.isStubbed()) {
            returnValue = new String[]{"One", "Two"};
            log("ActivityTable::getDetailsSectionTitles === The table's table query is currently stubbed out.  " +
                    "Returning a String array with two items. ===");
        } else {
            // Obtains the details row of the selected table row
            detailsRow = getDetailsRow(rowIndex);

            // If the Event Details or Task Detail section isn't expanded
            if(!isEventTaskDetailsSectionExpanded(rowIndex)) {
                // Expands the Event Details or Task Detail section
                expandEventTaskDetailsSection(rowIndex);
            }

            // Obtains all of the Details section titles
            detailsSectionTitles = detailsRow.findElements(By.cssSelector(".hp-show-form h2"));

            // Creates and populates a String array with the Details section titles
            returnValue = new String[detailsSectionTitles.size()];
            for(int i = 0; i < returnValue.length; i++) {
                returnValue[i] = detailsSectionTitles.get(i).getText();
            }

            // Collapses the Event Details or Task Detail section
            collapseEventTaskDetailsSection(rowIndex);
            // Collapses the specified Activity Table row
            collapse(rowIndex);
        }

        return returnValue;
    }


    /**
     * This method obtains the label of the specfied details row, which is furthered specified by the name of the
     * Details section and Activity table row.  Note: The user will not be able to get a label from the Task Detail
     * expandable section as the section does not have label-value pairs, only values.  Note: Row indicies start at 1
     * instead of 0.
     *
     * @param tableRowIndex    The index of a row in the Activity table
     * @param sectionName      The name of a Activity table row Details section
     * @param detailsRowIndex  The index of a details section row
     * @throws NoSuchElementException if a Details section whose name matches sectionName isn't found
     * @return                 A String containing the label of the specified row
     */
    public String getLabel(int tableRowIndex, String sectionName, int detailsRowIndex) {
        String returnValue = "";
        boolean sectionFound = false;
        WebElement detailsRow, detailsSection;
        List<WebElement> detailsSectionTitles, tempList;

        // If the table is stubbed
        if (this.isStubbed()) {
            returnValue = "VALUE";
            log("ActivityTable::getLabel === The table's table query is currently stubbed out.  Returning value '" +
                    returnValue + "' ===");
        } else {
            // Obtains the details row of the selected table row
            detailsRow = getDetailsRow(tableRowIndex);

            // If the Event Details or Task Detail section isn't expanded
            if(!isEventTaskDetailsSectionExpanded(tableRowIndex)) {
                // Expands the Event Details or Task Detail section
                expandEventTaskDetailsSection(tableRowIndex);
            }

            // Obtains all of the Details section titles
            detailsSectionTitles = detailsRow.findElements(By.cssSelector(".hp-show-form h2"));

            // For each Details section title
            for(int i = 0; i < detailsSectionTitles.size(); i++) {
                // Obtains the current section title
                WebElement sectionTitle = detailsSectionTitles.get(i);

                // If the current section title matches that of the given section name
                if (sectionName.equals(sectionTitle.getText())) {
                    // Obtains the list of label-value pairs from a System Event details row
                    tempList = detailsRow.findElements(By.jQuery(".hp-show-form h2:contains(" + sectionName + ") ~ ol"));

                    // If a System Event list of label-value pairs wasn't found
                    if(tempList.size() == 0) {
                        // Obtains the list of label-value pairs from a System Task details row
                        tempList = detailsRow.findElements(By.jQuery(".hp-show-form header:contains(" + sectionName + ") ~ ol"));
                    }

                    // Obtains the text of the detailsRowIndex-th row in the current section
                    detailsSection = tempList.get(0);
                    returnValue = detailsSection.findElement(By.cssSelector("li:nth-child(" + detailsRowIndex + ") > label")).getText();

                    // Collapses the Event Details or Task Detail section
                    collapseEventTaskDetailsSection(tableRowIndex);
                    // Collapses the specified Activity Table row
                    collapse(tableRowIndex);

                    return returnValue;
                }
            }

            throw new NoSuchElementException("ERROR!  The " + tableRowIndex + "-th row of the Activity table does " +
                    "not have a Details section by the name of '" + sectionName + "'.");
        }

        return returnValue;
    }


    /**
     * This method obtains the labels of the specfied Details section of the specified Activity table row.  Note: The
     * user will not be able to get a label from the Task Detail expandable section as the section does not have
     * label-value pairs, only values.  Note: Row indicies start at 1 instead of 0.
     *
     * @param rowIndex     The index of a row in the Activity table
     * @param sectionName  The name of a Activity table row Details section
     * @throws NoSuchElementException if a Details section whose name matches sectionName isn't found
     * @return             A String array containing the labels of the specified Details section
     */
    public String[] getLabels(int rowIndex, String sectionName) {
        String[] returnValue = {};
        boolean sectionFound = false;
        WebElement detailsRow, detailsSection;
        List<WebElement> detailsSectionTitles, labels, tempList;

        // If the table is stubbed
        if (this.isStubbed()) {
            returnValue = new String[]{"One", "Two"};
            log("ActivityTable::getLabels === The table's table query is currently stubbed out.  Returning a String " +
                    "array with two items. ===");
        } else {
            // Obtains the details row of the selected table row
            detailsRow = getDetailsRow(rowIndex);

            // If the Event Details or Task Detail section isn't expanded
            if(!isEventTaskDetailsSectionExpanded(rowIndex)) {
                // Expands the Event Details or Task Detail section
                expandEventTaskDetailsSection(rowIndex);
            }

            // Obtains all of the Details section titles
            detailsSectionTitles = detailsRow.findElements(By.cssSelector(".hp-show-form h2"));

            // For each Details section title
            for(int i = 0; i < detailsSectionTitles.size(); i++) {
                // Obtains the current section title
                WebElement sectionTitle = detailsSectionTitles.get(i);

                // If the current section title matches that of the given section name
                if (sectionName.equals(sectionTitle.getText())) {
                    // Obtains the list of label-value pairs from a System Event details row
                    tempList = detailsRow.findElements(By.jQuery(".hp-show-form h2:contains(" + sectionName + ") ~ ol"));

                    // If a System Event list of label-value pairs wasn't found
                    if(tempList.size() == 0) {
                        // Obtains the list of label-value pairs from a System Task details row
                        tempList = detailsRow.findElements(By.jQuery(".hp-show-form header:contains(" + sectionName + ") ~ ol"));
                    }

                    // Obtains the text of the detailsRowIndex-th row in the current section
                    detailsSection = tempList.get(0);
                    labels = detailsSection.findElements(By.cssSelector("li > label"));

                    returnValue = new String[labels.size()];
                    for(int j = 0; j < returnValue.length; j++) {
                        returnValue[j] = labels.get(j).getText();
                    }

                    // Collapses the Event Details or Task Detail section
                    collapseEventTaskDetailsSection(rowIndex);
                    // Collapses the specified Activity Table row
                    collapse(rowIndex);

                    return returnValue;
                }
            }

            throw new NoSuchElementException("ERROR!  The " + rowIndex + "-th row of the Activity table does " +
                    "not have a Details section by the name of '" + sectionName + "'.");
        }

        return returnValue;
    }


    /**
     * This method obtains the specified label's index within the specified Details section of the specified Activity
     * Table details row.  Note: Row indicies start at 1 instead of 0.
     *
     * @param rowIndex     The index of a row in the Activity table
     * @param sectionName  The name of a Activity table row Details section
     * @param label        The name of the label to look for in the specified Details section
     * @throws NoSuchElementException if a Details section whose name matches sectionName isn't found or
     *                     if a label (in the specified Details section) whose name matches label isn't found
     * @return             The integer index of the specified label
     */
    public int getIndexOfLabel(int rowIndex, String sectionName, String label) {
        int returnValue = -1;
        boolean sectionFound = false, labelFound = false;
        WebElement detailsRow, detailsSection;
        List<WebElement> detailsSectionTitles, labels, tempList;

        // If the table is stubbed
        if (this.isStubbed()) {
            log("ActivityTable::getIndexOfLabel === The table's table query is currently stubbed out.  Returning " +
                    "value '" + returnValue + "'. ===");
        } else {
            // Obtains the details row of the selected table row
            detailsRow = getDetailsRow(rowIndex);

            // If the Event Details or Task Detail section isn't expanded
            if(!isEventTaskDetailsSectionExpanded(rowIndex)) {
                // Expands the Event Details or Task Detail section
                expandEventTaskDetailsSection(rowIndex);
            }

            // Obtains all of the Details section titles
            detailsSectionTitles = detailsRow.findElements(By.cssSelector(".hp-show-form h2"));

            // For each Details section title
            for(int i = 0; i < detailsSectionTitles.size(); i++) {
                // Obtains the current section title
                WebElement sectionTitle = detailsSectionTitles.get(i);

                // If the current section title matches that of the given section name
                if (sectionName.equals(sectionTitle.getText())) {
                    // Obtains the list of label-value pairs from a System Event details row
                    tempList = detailsRow.findElements(By.jQuery(".hp-show-form h2:contains(" + sectionName + ") ~ ol"));

                    // If a System Event list of label-value pairs wasn't found
                    if(tempList.size() == 0) {
                        // Obtains the list of label-value pairs from a System Task details row
                        tempList = detailsRow.findElements(By.jQuery(".hp-show-form header:contains(" + sectionName + ") ~ ol"));
                    }

                    // Obtains all of the labels of the current section
                    detailsSection = tempList.get(0);
                    labels = detailsSection.findElements(By.cssSelector("li > label"));

                    // For each label
                    for(int j = 0; j < labels.size() && !labelFound; j++) {
                        // Obtains the current label
                        WebElement curLabel = labels.get(j);

                        // If the current label matches that of the given label
                        if(label.equals(curLabel.getText())) {
                            returnValue = j + 1;
                            labelFound = true;
                        }
                    }

                    // If a label wans't found that matches the given label
                    if(!labelFound) {
                        throw new NoSuchElementException("ERROR!  The '" + sectionName + "' Details section of the " +
                                "specified Activity table details row does not have a label by the name of '" + label + "'.");
                    }

                    // Collapses the Event Details or Task Detail section
                    collapseEventTaskDetailsSection(rowIndex);
                    // Collapses the specified Activity Table row
                    collapse(rowIndex);

                    return returnValue;
                }
            }

            throw new NoSuchElementException("ERROR!  The " + rowIndex + "-th row of the Activity table does " +
                    "not have a Details section by the name of '" + sectionName + "'.");
        }

        return returnValue;
    }


    /**
     * This method obtains the value associted with the specified label within the specified Details section of the
     * specified Activity Table details row.  Note: Row indicies start at 1 instead of 0.
     *
     * @param rowIndex     The index of a row in the Activity table
     * @param sectionName  The name of a Activity table row Details section
     * @param label        The name of the label to look for in the specified Details section
     * @throws NoSuchElementException if a Details section whose name matches sectionName isn't found or
     *                     if a label (in the specified Details section) whose name matches label isn't found
     * @return             The value associated with the specified label in String form
     */
    public String getValue(int rowIndex, String sectionName, String label) {
        String returnValue = "";
        boolean sectionFound = false, labelFound = false;
        WebElement detailsRow, detailsSection;
        List<WebElement> detailsSectionTitles, labels, tempList;

        // If the table is stubbed
        if (this.isStubbed()) {
            log("ActivityTable::getValue === The table's table query is currently stubbed out.  Returning value '" +
                    returnValue + "'. ===");
        } else {
            // Obtains the details row of the selected table row
            detailsRow = getDetailsRow(rowIndex);

            // If the Event Details or Task Detail section isn't expanded
            if(!isEventTaskDetailsSectionExpanded(rowIndex)) {
                // Expands the Event Details or Task Detail section
                expandEventTaskDetailsSection(rowIndex);
            }

            // Obtains all of the Details section titles
            detailsSectionTitles = detailsRow.findElements(By.cssSelector(".hp-show-form h2"));

            // For each Details section title
            for(int i = 0; i < detailsSectionTitles.size(); i++) {
                // Obtains the current section title
                WebElement sectionTitle = detailsSectionTitles.get(i);

                // If the current section title matches that of the given section name
                if (sectionName.equals(sectionTitle.getText())) {
                    // Obtains the list of label-value pairs from a System Event details row
                    tempList = detailsRow.findElements(By.jQuery(".hp-show-form h2:contains(" + sectionName + ") ~ ol"));

                    // If a System Event list of label-value pairs wasn't found
                    if(tempList.size() == 0) {
                        // Obtains the list of label-value pairs from a System Task details row
                        tempList = detailsRow.findElements(By.jQuery(".hp-show-form header:contains(" + sectionName + ") ~ ol"));
                    }

                    // Obtains all of the labels of the current section
                    detailsSection = tempList.get(0);
                    labels = detailsSection.findElements(By.cssSelector("li > label"));

                    // For each label
                    for(int j = 0; j < labels.size() && !labelFound; j++) {
                        // Obtains the current label
                        WebElement curLabel = labels.get(j);

                        // If the current label matches that of the given label
                        if(label.equals(curLabel.getText())) {
                            int index = j + 1;
                            // Obtains the value of the current label
                            returnValue = detailsSection.findElement(By.cssSelector("li:nth-child(" + index + ") > div")).getText();
                            labelFound = true;
                        }
                    }

                    // If a label wans't found that matches the given label
                    if(!labelFound) {
                        throw new NoSuchElementException("ERROR!  The '" + sectionName + "' Details section of the " +
                                "specified Activity table details row does not have a label by the name of '" + label + "'.");
                    }

                    // Collapses the Event Details or Task Detail section
                    collapseEventTaskDetailsSection(rowIndex);
                    // Collapses the specified Activity Table row
                    collapse(rowIndex);

                    return returnValue;
                }
            }

            throw new NoSuchElementException("ERROR!  The " + rowIndex + "-th row of the Activity table does " +
                    "not have a Details section by the name of '" + sectionName + "'.");
        }

        return returnValue;
    }


    /**
     * This method obtains the Task Details of the specified Activity Table details row.
     *
     * @param rowIndex  The index of a row in the Activity table from which to get Task Details from
     * @throws NoSuchElementException if the specified row doesn't have a Task Detail section
     * @return          A String array containing the specified details row task details
     */
    public String[] getTaskDetails(int rowIndex) {
        String[] returnValue = {};
        String text = "";
        boolean sectionFound = false, labelFound = false;
        WebElement detailsRow, taskDetailSection;
        List<WebElement> detailsSectionTitles, tempList;

        log ("Getting the details text from the DETAILS section of this activity row: " + rowIndex);

        // If the table is stubbed
        if (this.isStubbed()) {
            returnValue = new String[]{"One", "Two"};
            log("ActivityTable::getTaskDetails === The table's table query is currently stubbed out.  Returning a " +
                    "String array with two items. ===");
        } else {
            // Obtains the details row of the selected table row
            detailsRow = getDetailsRow(rowIndex);

            // If the Event Details or Task Detail section isn't expanded
            if(!isEventTaskDetailsSectionExpanded(rowIndex)) {
                // Expands the Event Details or Task Detail section
                expandEventTaskDetailsSection(rowIndex);
            }
            sleep(5,TimeUnit.SECONDS);
            // Obtains the Details section to be expanded
            taskDetailSection = detailsRow.findElement(By.jQuery(".hp-task-events.hp-collapsible"));

            // Obtians the label that will be clicked on to expand the Details section
            tempList = taskDetailSection.findElements(By.jQuery("label:contains(Task Detail)"));

            // If a Task Detail section wasn't found
            if(tempList.size() == 0) {
                throw new NoSuchElementException("ERROR!  The " + rowIndex + "-th row of the Activity table does " +
                        "not have a Task Detail section.");
            }
            sleep(5, TimeUnit.SECONDS);         //Added as browser take time to display contain of activity---Nileshg 11/19/2014
            // Obtains all of the text within the Task Detail section
            // Taking reference of taskDetailSection again to handle stale element reference exception
            taskDetailSection = detailsRow.findElement(By.jQuery(".hp-task-events.hp-collapsible"));
            returnValue = taskDetailSection.findElement(By.tagName("div")).getText().split("\n");
        }

        return returnValue;
    }


    /**
     * gets the array of strings that are under the TASK DETAILS section of an expanded activity row
     * @author Craig Yara
     * @return
     */
    public String[] getTaskDetailsOfSelectedRow() {
        String[] returnValue = {};
        String text = "";
        boolean sectionFound = false, labelFound = false;
        WebElement detailsRow, taskDetailSection;
        List<WebElement> detailsSectionTitles, tempList;

        log ("Getting the details text from the DETAILS section of this activity row: ");

        // If the table is stubbed
        if (this.isStubbed()) {
            returnValue = new String[]{"One", "Two"};
            log("ActivityTable::getTaskDetails === The table's table query is currently stubbed out.  Returning a " +
                    "String array with two items. ===");
        } else {
            // Obtains the details row of the selected table row
            detailsRow =  getWebElement().findElement(org.openqa.selenium.By.cssSelector("#hp-activities > tbody > .hp-selected"));

            // If the Event Details or Task Detail section isn't expanded
            expandEventTaskDetailsSectionForSelectedRow ();

            sleep(5, TimeUnit.SECONDS);
            // Obtains the Details section to be expanded
            taskDetailSection = detailsRow.findElement(By.jQuery(".hp-task-events.hp-collapsible"));

            // Obtians the label that will be clicked on to expand the Details section
            tempList = taskDetailSection.findElements(By.jQuery("label:contains(Task Detail)"));

            // If a Task Detail section wasn't found
            if(tempList.size() == 0) {
                throw new NoSuchElementException("ERROR!  The xxxx -th row of the Activity table does " +
                        "not have a Task Detail section.");
            }
            sleep(5, TimeUnit.SECONDS);         //Added as browser take time to display contain of activity---Nileshg 11/19/2014
            // Obtains all of the text within the Task Detail section
            returnValue = taskDetailSection.findElement(By.tagName("div")).getText().split("\n");
        }

        return returnValue;
    }


    /**
     * expands the TASK DETAILS section of the currently selected (and expanded) activity row
     * @author Craig Yara
     */
    public void expandEventTaskDetailsSectionForSelectedRow() {
        int retries = 0;
        boolean done = false;
        WebElement detailsRow, label = null;

        // If the table is stubbed
        if (this.isStubbed()) {
            log("ActivityTable::expandEventTaskDetailsSection === The table's table query is currently stubbed out. ===");
        }
        else {
            while (!done) {
                try {
                    // Obtains the details row of the selected table row

                    detailsRow = getWebElement().findElement(org.openqa.selenium.By.cssSelector("#hp-activities > tbody > .hp-selected"));

                    // Sets the name of the Details section to be expanded
                    String sectionName = "Event details";
                    // Obtains the Details section to be expanded
                    List<WebElement> detailsSection = detailsRow.findElements(By.cssSelector(".hp-alert-events.hp-collapsible"));

                    // If the Details section to be exapnded isn't an 'Event details' section
                    WebElement aaa;
                    if(detailsSection.size() == 0) {
                        // Sets the name of the Details section to be expanded
                        sectionName = "Task Detail";
                        // Obtains the Details section to be expanded

//                        try {
//                             aaa = getWebElement().findElement(org.openqa.selenium.By.cssSelector("#hp-activities > tbody > .hp-selected > .hp-row-details-cell >  .hp-task-details > .hp-task-notification-container > .hp-show-form > .hp-task-events.hp-collapsible"));
//                        } catch (Exception e) {}
//                        detailsSection = detailsRow.findElements(org.openqa.selenium.By.cssSelector(".hp-row-details-cell >  .hp-task-details > .hp-task-notification-container > .hp-show-form > .hp-task-events.hp-collapsible"));

                        this.waitForElement(org.openqa.selenium.By.cssSelector("#hp-activities > tbody > .hp-selected > .hp-row-details-cell >  .hp-task-details > .hp-task-notification-container > .hp-show-form > .hp-task-events.hp-collapsible"),  30);

                        detailsSection = getWebElement().findElements(org.openqa.selenium.By.cssSelector("#hp-activities > tbody > .hp-selected > .hp-row-details-cell >  .hp-task-details > .hp-task-notification-container > .hp-show-form > .hp-task-events.hp-collapsible"));
                    }


                    // Obtians the label that will be clicked on to expand the Details section
                    label = detailsSection.get(0).findElement(By.jQuery("label:contains(" + sectionName + ")"));

                    if (label != null) {
                        label.click();
                    } else {
                        throw new NoSuchElementException("ActivityTable::expandEventTaskDetailsSection -- The Details " +
                                "section within the given Activity Table details row is not expandable.");
                    }
                    done = true;

                } catch (StaleElementReferenceException e) {
                    retries++;

                    // have we tried the maximum retries?
                    if (retries == MAXRETRIES) {
                        done = true;

                        warning("ERROR!  StaleElementReference Exception when using ActivityTable.expandEventTaskDetailsSection() - " + e.getMessage());
                        e.printStackTrace();
                        throw e;
                    }
                }
            }
        }
    }

    /**
     * This method obtains the value associted with the specified label within the specified Details section of the
     * specified Activity Table details row.  Note: Row indicies start at 1 instead of 0.
     *
     * @param rowIndex     The index of a row in the Activity table
     * @param sectionName  The name of a Activity table row Details section
     * @param label        The name of the label to look for in the specified Details section
     * @throws NoSuchElementException if a Details section whose name matches sectionName isn't found or
     *                     if a label (in the specified Details section) whose name matches label isn't found
     * @return             The value associated with the specified label in String form
     */
    public String getLabeValueofRow(int rowIndex, String sectionName, String label) {
        String returnValue = "";
        boolean sectionFound = false, labelFound = false;
        WebElement detailsRow, detailsSection;
        List<WebElement> detailsSectionTitles, labels, tempList;

        // If the table is stubbed
        if (this.isStubbed()) {
            log("ActivityTable::getValue === The table's table query is currently stubbed out.  Returning value '" +
                    returnValue + "'. ===");
        } else {
            // Obtains the details row of the selected table row
            detailsRow = getDetailsRow(rowIndex);

            // If the Event Details or Task Detail section isn't expanded
          /*  if(!isEventTaskDetailsSectionExpanded(rowIndex)) {
                // Expands the Event Details or Task Detail section
                expandEventTaskDetailsSection(rowIndex);
            }*/

            // Obtains all of the Details section titles
            detailsSectionTitles = detailsRow.findElements(By.cssSelector(".hp-show-form h2"));

            // For each Details section title
            for(int i = 0; i < detailsSectionTitles.size(); i++) {
                // Obtains the current section title
                WebElement sectionTitle = detailsSectionTitles.get(i);

                // If the current section title matches that of the given section name
                if (sectionName.equals(sectionTitle.getText())) {
                    // Obtains the list of label-value pairs from a System Event details row
                    tempList = detailsRow.findElements(By.jQuery(".hp-show-form h2:contains(" + sectionName + ") ~ ol"));

                    // If a System Event list of label-value pairs wasn't found
                    if(tempList.size() == 0) {
                        // Obtains the list of label-value pairs from a System Task details row
                        tempList = detailsRow.findElements(By.jQuery(".hp-show-form header:contains(" + sectionName + ") ~ ol"));
                    }

                    // Obtains all of the labels of the current section
                    detailsSection = tempList.get(0);
                    labels = detailsSection.findElements(By.cssSelector("li > label"));

                    // For each label
                    for(int j = 0; j < labels.size() && !labelFound; j++) {
                        // Obtains the current label
                        WebElement curLabel = labels.get(j);

                        // If the current label matches that of the given label
                        if(label.equals(curLabel.getText())) {
                            int index = j + 1;
                            // Obtains the value of the current label
                            returnValue = detailsSection.findElement(By.cssSelector("li:nth-child(" + index + ") > div")).getText();
                            labelFound = true;
                        }
                    }

                    // If a label wans't found that matches the given label
                    if(!labelFound) {
                        throw new NoSuchElementException("ERROR!  The '" + sectionName + "' Details section of the " +
                                "specified Activity table details row does not have a label by the name of '" + label + "'.");
                    }

                    // Collapses the Event Details or Task Detail section
                    // collapseEventTaskDetailsSection(rowIndex);
                    // Collapses the specified Activity Table row
                    collapse(rowIndex);

                    return returnValue;
                }
            }

            throw new NoSuchElementException("ERROR!  The " + rowIndex + "-th row of the Activity table does " +
                    "not have a Details section by the name of '" + sectionName + "'.");
        }

        return returnValue;
    }


}


