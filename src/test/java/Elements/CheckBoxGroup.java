/*
 * © Copyright 2002-2015 Hewlett Packard Enterprise Development LP
 */

package Elements;


import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;


import java.util.ArrayList;
import java.util.List;

import static Helpers.Utility.log;

// IMPLEMENTATION NOTES:
//
// A checkbox group typically has an HTML structure like below:
//
// <div id="example-id" class="hp-form-content">
//   <input id="checkbox-example-id" type="checkbox">
//   <label for="checkbox-example-id" data-localize="...">...</label>
//   ... (More input and label tags for checkboxes found here)
// </div>
//

/**
 * This class is used to interact with a group of checkbox web elements.
 *
 * Your selenium locator should uniquely match the following element from the example in this class' code.
 * Element: <div id="example-id" class="hp-form-content">
 *
 * Created by millera on 7/22/2014.
 */
public class CheckBoxGroup extends BaseElement {

    // - - - - - Class Members - - - - -
    private String query;
    private String fullQuery;

    /**
     * The constructor to a CheckBoxGroup, which requires a By locator query argument that retrives all grouped
     * checkboxes within a specific area/section.
     *
     * @param locator  The Selenium {@link org.openqa.selenium.By locator} for locating this web element
     */
    public CheckBoxGroup (By locator) {
        super(locator);

        // Obtains the String argument used for creating the By argument passed to this constructor
        fullQuery = locator.toString();
        String results[] = locator.toString().split(": ");
        this.query = results[1];

        return;
    }

    /**
     * This method returns a list checkboxes that are a part of this CheckBoxGroup.
     *
     * @return  A list of checkboxes in WebElement form
     */
    public List<WebElement> getCheckboxes() {
        List<WebElement> checkboxLabels;

        WebElement container = getWebElement();
        checkboxLabels = container.findElements(By.cssSelector("input"));


        // If the checkboxLabels list is populated and the last "label" is actually text from a tooltip, remove said label
        if(checkboxLabels.size() > 0 && checkboxLabels.get(checkboxLabels.size() - 1).getAttribute("class").contains("hp-help")) {
            checkboxLabels.remove(checkboxLabels.size() - 1);
        }

        return checkboxLabels;
    }


    /***
     * This method grabs the checkboxes' labels that are to be included in the group using the previously-set query value.
     *
     * @return  A list of checkbox labels in WebElement form
     */
    private List<WebElement> getLabels() {
        List<WebElement> checkboxLabels;

        WebElement container = getWebElement();
        checkboxLabels = container.findElements(By.cssSelector("span"));

        if(checkboxLabels.size() == 0) {
            checkboxLabels = container.findElements(By.cssSelector("label"));
        }

        // If the checkboxLabels list is populated and the last "label" is actually text from a tooltip, remove said label
        if(checkboxLabels.size() > 0 && checkboxLabels.get(checkboxLabels.size() - 1).getAttribute("class").contains("hp-help")) {
            checkboxLabels.remove(checkboxLabels.size() - 1);
        }

        // in case where a checkbox group has label has the very FIRST element then we should remove this label from the list of label
        // obtained above
        if(checkboxLabels.size() > 0 && container.findElement(By.cssSelector(":nth-child(1)")).getTagName().equals("label")) {
            checkboxLabels.remove(0);
        }

        // CRAIG: remove empty blank labels that can be seen by selenium that can be 'hidden' and not visible.
        //  example: Reports -> Create Report -> Chart Options -> ShowChart checkbox group
        for (int index = 0; index < checkboxLabels.size(); index++)
        {
            if (checkboxLabels.get (index).getText().equals("")) {
                checkboxLabels.remove(index);
                index--;  // we removed an item in the list, so decrement our index pointer
            }

        }

        return checkboxLabels;
    }

    /**
     * This method obtains the labels of all the checkboxes within the CheckBoxGroup.
     *
     * @return  An array of checkbox labels in String form
     */
    public String[] getLabelList() {
        String temp = "";
        String[] returnList = {};
        List<WebElement> checkboxLabels = getLabels();
        ArrayList<String> tempList = new ArrayList<String>();

        if (isStubbed()) {
            log("=== This checkbox group is currently stubbed out.  Returning an empty String array. ===");
        } else {
            // Converts the text within the checkbox labels into a list of Strings
            returnList = ElementUtil.getText(checkboxLabels, true);
        }

        return returnList;
    }

    /**
     * This method checks the checkbox specified by the given checkbox label.
     *
     * @param checkboxLabel  The label of the checkbox to be checked
     * @throws               org.openqa.selenium.NoSuchElementException if a checkbox isn't found with a label that
     *                       matches the given checkbox label
     */
    public void check(String checkboxLabel) {

        CheckBox checkbox = getCheckboxForLabel (checkboxLabel);
        checkbox.check();


    }

    /**
     * This method checks the checkboxes specified by the given checkbox labels.
     *
     * @param checkboxLabels  The labels of the checkboxes to be checked
     */
    public void checkMultiple(String[] checkboxLabels) {
        // For each checkbox label in checkboxLabels
        for(int i = 0; i < checkboxLabels.length; i++) {
            check(checkboxLabels[i]);
        }
    }

    private CheckBox getCheckboxForLabel (Object label)
    {

        CheckBox returnCheckBox = null;

        String newQuery = "";
        boolean noCheckboxFound = true;
        List<WebElement> checkboxLabels = getLabels();
        List <WebElement> checkBoxList = getCheckboxes();
        Object[] labelList = getLabelList();

        // For each checkbox label in labelList
        for (int i = 0; i < checkBoxList.size(); i++) {
            // If the current checkbox label matches the given checkbox label
            if (labelList[i].equals(label)) {
                // Obtains a reference to the found checkbox
                WebElement wElement = checkBoxList.get(i);
                String ID  = wElement.getAttribute("id");

                returnCheckBox = new CheckBox(By.id(ID));

                noCheckboxFound = false;

                break;
            }
        }

        // If a checkbox with the given label isn't found
        if(noCheckboxFound) {
            throw new NoSuchElementException("ERROR!  No checkbox found with the label '" + label + "'.");
        }

        return returnCheckBox;
    }

    /**
     * This method unchecks the checkbox specified by the given checkbox label.
     *
     * @param checkboxLabel  The label of the checkbox to be unchecked
     * @throws               org.openqa.selenium.NoSuchElementException if a checkbox isn't found with a label that
     *                       matches the given checkbox label
     */
    public void uncheck(Object checkboxLabel) {

        CheckBox checkbox = getCheckboxForLabel(checkboxLabel);
        checkbox.uncheck();

    }

    /**
     * This method unchecks the checkboxes speicified by the given checkbox labels.
     *
     * @param checkboxLabels  The labels of the checkboxes to be unchecked
     */
    public void uncheckMultiple(Object[] checkboxLabels) {
        // For each checkbox label in checkboxLabels
        for(int i = 0; i < checkboxLabels.length; i++) {
            uncheck(checkboxLabels[i]);
        }
    }

    /**
     * The method checks to see if the the checkbox, specified by the given checkbox label, is checked or not.
     *
     * @param checkboxLabel  The label of the checkbox whose status is to be checked
     * @return               A boolean value 'true' if the checkbox is checked or 'false' if it isn't
     * @throws               org.openqa.selenium.NoSuchElementException if a checkbox isn't found with a label that
     *                       matches the given checkbox label
     */
    public boolean isChecked(String checkboxLabel) {
        boolean returnValue = false;
        boolean noCheckboxFound = true;
        String[] labelList = getLabelList();

        if (isStubbed()) {
            log("=== This checkbox group is currently stubbed out.  Returning false. ===");
            return false;
        } else {
            // For each checkbox label in labelList
            for (int i = 0; i < labelList.length; i++) {
                // If the current checkbox label matches the given checkbox label
                if (labelList[i].equals(checkboxLabel)) {
                    CheckBox checkbox = new CheckBox(By.cssSelector("#" + this.query + " > input:nth-of-type(" + (i+1) + ")"));

                    // Checks if the found checkbox is checked
                    returnValue = checkbox.isChecked();
                    noCheckboxFound = false;

                    break;
                }
            }

            // If a checkbox with the given label isn't found
            if(noCheckboxFound) {
                throw new NoSuchElementException("ERROR!  No checkbox found with the label '" + checkboxLabel + "'.");
            }
        }

        return returnValue;
    }
}
