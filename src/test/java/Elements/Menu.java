/*
 * © Copyright 2002-2015 Hewlett Packard Enterprise Development LP
 */

package Elements;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static Elements.Globals.browser;
import static Helpers.Utility.log;
import static Helpers.Utility.sleep;
import static Helpers.Utility.warning;


// IMPLEMENTATION NOTES:
//
// A menu typically has an HTML structure like one of the two structures below:
//
// <div id="example-id" class="hp-drop-menu">
//   <label>...</label>
//   <ol class="hp-options">...</ol>
// </div>
//

/**
 * This class is used to interact with menu web elements listed on the web page.
 *
 * Your selenium locator should uniquely match the following element from the example in this class' code and is
 * typically used to represent the Actions menu.
 * Element: <div id="example-id" class="hp-drop-menu">
 *
 * Created by stauffel on 5/22/14.
 */
public class Menu extends BaseElement {
    private int MAXRETRIES = 5;

    // - - - - - Class attributes - - - - -


    // - - - - - Constructor - - - - -

    /**
     * Constructor used to reference this class.
     *
     * @param locator   the By object used to uniquely identify this web element
     */
    public Menu (By locator) {
        super(locator);

    }


    //  - - - - - Class Methods - - - - -


    /**
     * This method selects a menu item from the menu list.
     * <p>
     * <b><u>NOTE:</u></b>  The menu is first checked if it exists before interacting with it.  If the menu does not exist,
     * a NoSuchElementException is thrown.
     *
     * @param menuItem        [STRING] menu item to select
     */
    public void selectItem (String itemName) {
        // declaring local variables
        boolean found = false;
        boolean isOption = false;
        String temp = "";
        List<WebElement> listElements = null;


        if (isStubbed()) {
            log("=== This menu locator is currently stubbed out. ===");
        } else {
            // if the table refreshed while we are getting values, then a stale element is being thrown. To get around
            //     this will catch the stale element and try again
            boolean done = false;
            boolean clicked = false;
            int retries = 0;

            // getting the web element
            WebElement menuElement = null;
            //sleep(1,TimeUnit.SECONDS);
            int timeOutSeconds = 30; // TODO CRAIG

            String title;

            // declaring local variables
             long startTime;
            int elapsedTime;
            startTime = System.nanoTime();



            while (!done) {
                int i = 0;
                try {

                    // getting the web element
                    menuElement = getWebElement();

                    // finding out of the class name of this menu
                    String className = menuElement.getAttribute("className");
                    if (className.length() == 0) {
                        try {
                            // check to see if you can find a  class hp-select-form
                            WebElement clickableElement = menuElement.findElement(By.cssSelector(".hp-select-form > .hp-select"));
                            if (clickableElement != null) {
                                menuElement = clickableElement;
                            }
                        } catch (NoSuchElementException e) {
                            // leave the menu as is
                        }
                    }



                    // if the menu isn't in the Filter Pane or if the Filter Bar isn't active (or hasn't been clicked)
                    //and if the browser isn't IE.
                    if (!(className.contains("hp-filter") && className.contains("hp-pinned")) &&
                            !(className.contains("hp-filter") && className.contains("hp-active"))) {
                        this.clickWebElement(menuElement);

                        clicked = true;
                    }

                    // get list of menu item elements
                    listElements = menuElement.findElements(By.tagName("li"));

                    // for some reason, we need to pause here, otherwise Selenium doesn't see that the menu has been opened
                    // and menu items are displayed
                    sleep(500, TimeUnit.MILLISECONDS);//12-10-14  I'm changing this from 2 seconds to half a second to try to make this more efficient so we don't have
                                                      //          as many retries due to StaleElementExceptions.  This is a big problem with IE when accessing menus
                                                      //          with a bunch of menu items. Change it back if you start seeing problems.  --sylvia
                    //sleep(2, TimeUnit.SECONDS);

                    if (listElements.size() == 0) {
                        listElements = menuElement.findElements(By.tagName("option"));
                        isOption = true;
                        log ("  (debug temp, setting isOption to true)");
                    }
                    while (i<listElements.size()){
                        // getting the text of the menu item -- replace any '\n' and white space with empty string
                        temp = listElements.get(i).getAttribute("textContent");
                        temp = temp.replace("\n", "");
                        temp = temp.trim();

                        if (temp.equals(itemName)) {

                            if (isOption) {
                                listElements.get(i).click();
                                found = true;
                            }
                            else if (listElements.get(i).isDisplayed()) {
                                found = true;

                                if (listElements.get(i).findElements(By.tagName("a")).size() != 0) {
                                    //11-21-14
                                    //Note: I'm noticing that if you have more that one IE browser open when running a test
                                    //that when the following click method gets executed, it can cause the IE driver to die.
                                    //unfortunately, I can't code around this one, so we need to make sure no IE browsers are already open when we run tests on IE.
                                    //This behavior was seen with IEDriver 2.39.0 when the MATFileProvisioningGroupsScreenDefaultTest tries to
                                    //select one of the menu items from the Actions menu.  The problem doesn't happen consistently but intermittently.
                                    listElements.get(i).findElement(By.tagName("a")).click();
                                } else if (listElements.get(i).findElements(By.tagName("span")).size() != 0) {
                                     listElements.get(i).findElement(By.tagName("span")).click();
                                } else if (listElements.get(i).findElements(By.tagName("div")).size() != 0) {
                                     listElements.get(i).findElement(By.tagName("div")).click();
                                } else {
                                     listElements.get(i).click();
                                }
                                    break;

                            }
                            else {
                                log ("  (debug temp.  element not option and not displayed : " + listElements.get(i) + ")");
                                browser.takeScreenShot("Menu_selectItem_notoption_displayed" + RandomStringUtils.random(4, true, true));

                            }
                        }
                        ++i;
                    }

                    // If the specified menu item was found
                    if(found) {
                        done = true;
                    } else {
                        // get elapsed Time
                        elapsedTime = (int) TimeUnit.SECONDS.convert((System.nanoTime() - startTime), TimeUnit.NANOSECONDS);

                        // If it has reached the timeout, stop checking; otherwise sleep and try again
                        if (elapsedTime >= timeOutSeconds) {
                            done = true;

                        } else if(clicked) {
                            //need to close the menu in order to reset if this method has been retried MAXRETRIES times
                            menuElement.click();
                        }


                        log("Retry #" + retries);
                    }
                } catch(StaleElementReferenceException e){
                    elapsedTime = (int) TimeUnit.SECONDS.convert((System.nanoTime() - startTime), TimeUnit.NANOSECONDS);

                    // have we tried the maximum retries?
                    if (elapsedTime >= timeOutSeconds) {
                        done = true;

                        warning("ERROR!  StaleElementReference Exception - " + e.getMessage());
                        e.printStackTrace();
                        throw e;
                    } else {
                        if(clicked) {
                            //need to close the menu in order to reset if Stale Element Reference was thrown
                            menuElement.click();
                        }
                    }
                }
            }

            if (!found) {
                if (listElements != null)
                {
                    log ("*** listElement.size = " + listElements.size());
                    for (int x=0;  x < listElements.size(); x++)
                    {
                        temp = listElements.get(x).getAttribute("textContent");
                        temp = temp.replace("\n", "");
                        temp = temp.trim();
                        log ("*** listElement [" + x + "] = " + temp);
                    }
                }

                browser.takeScreenShot("Menu_selectItem_" + RandomStringUtils.random(4, true, true));

                throw new NoSuchElementException("The item '" + itemName + "' is not showing among the list of items in the menu.");
            }
        }
    }

    /**
     * This method determines if the menu is Open or not.
     * <p>
     * <b><u>NOTE:</u></b>  The menu is first checked if it exists before interacting with it.  If the menu does not exist,
     * a NoSuchElementException is thrown.
     *
     * @return     A boolean value of true if returned if the menu is open.  A value of false is returned if it is closed.
     */
    protected boolean isOpen(){
        boolean returnValue = false;

        if (isStubbed()) {
            returnValue = false;
            log("=== This menu locator is currently stubbed out.  Returning the value '" + returnValue + "'. ===");
        } else {
            // getting the combo box web element
            WebElement menuElement = getWebElement();
            String classNames = menuElement.getAttribute("class");
            if (classNames.contains("hp-active"))
                returnValue = true;
            else {
                try{
                    WebElement menuStatusElement = menuElement.findElement(By.className("hp-select"));
                    classNames = menuStatusElement.getAttribute("class");
                    if (classNames.contains("hp-active"))
                        returnValue = true;
                    else
                        returnValue = false;
                }catch(Throwable e){
                    returnValue = false;
                }
            }
        }
        return returnValue;

    }

    /**
     * This method determines if there is a label within the menu items.  If there is a label, this method returns the value.
     * If there is no label, an empty String is returned.  If the web element is stubbed, this method returns the
     * String "VALUE".
     * <p>
     * <b>NOTE:</b>  This label will not be returned within the menu item list.
     *
     * @return     a String representing the label of the menu item list.
     */
    public String getLabel () {
        // declaring local variables
        String returnValue = "";

        if (isStubbed()) {
            returnValue = "VALUE";
            log("=== This menu locator is currently stubbed out.  Returning the value '" + returnValue + "'. ===");
        } else {
            // getting the combo box web element
            WebElement menuElement = getWebElement();

            // to get the list, we are expecting a label (the top of the menu) and a list of items
            try {
                WebElement labelElement = menuElement.findElement(By.tagName("label"));

                // add the label to the list
                returnValue = labelElement.getAttribute("textContent");


            } catch (Exception e) {
                // do nothing -- this means we have no label element
            }

        }

        return returnValue;
    }

    /**
     * This method checks to see if a specific menu item is enabled.  If it is enabled (meaning the user can select that
     * menu item), this method returns a boolean value of 'true'.  Otherwise a boolean value of 'false' is returned.
     * <p>
     * <b><u>NOTE:</u></b>  The menu is first checked if it exists before interacting with it.  If the menu does not exist,
     * a NoSuchElementException is thrown.
     *
     * @param menuItem        [STRING] the menu item to check
     * @return                a boolean value 'true' if the menu item is enabled, 'false' otherwise
     */
    public boolean isMenuItemEnabled (String menuItem) {
        // declaring local variables
        boolean returnValue = false;

        if (isStubbed()) {
            returnValue = true;
            log("=== This menu locator is currently stubbed out.  Returning value '" + returnValue + "'. ===");
        } else {
            // if the table refreshed while we are getting values, then a stale element is being thrown. To get around
            //     this will catch the stale element and try again
            List<WebElement> listElements;
            WebElement menuItemElement;
            boolean found = false;
            boolean clicked = false;
            int retries = 0;

            WebElement menuElement = null;

            while (!found) {
                try {
                    // getting the menu web element
                    menuElement = getWebElement();

                    // finding out of the class name of this menu
                    String className = menuElement.getAttribute("className");
                    if (className.length() == 0) {
                        try{
                            // check to see if you can find a  class hp-select-form
                            WebElement clickableElement = menuElement.findElement(By.cssSelector(".hp-select-form > .hp-select"));
                            if (clickableElement != null) {
                                menuElement = clickableElement;
                            }
                        } catch (NoSuchElementException e) {
                            // leave the menu as is
                        }
                    }

                    // clicking on the menu element to open the menu
                    menuElement.click();
                    clicked = true;


                    // sleeping 1 second to give time for the UI to catch up with Selenium.  If we don't sleep 1 second here,
                    // then sometimes the menu doesn't work right
                    sleep(1, TimeUnit.SECONDS);

                    // get a list of menu items
                    listElements = menuElement.findElements(By.tagName("li"));

                    // finding the menu item we want to check
                    for (int i = 0; i < listElements.size(); i++) {
                        if (listElements.get(i).getText().equals(menuItem)) {
                            found = true;

                            if (listElements.get(i).findElements(By.tagName("a")).size() != 0)
                                menuItemElement = listElements.get(i).findElement(By.tagName("a"));
                            else if (listElements.get(i).findElements(By.tagName("span")).size() != 0)
                                menuItemElement = listElements.get(i).findElement(By.tagName("span"));
                            else if (listElements.get(i).findElements(By.tagName("div")).size() != 0)
                                menuItemElement = listElements.get(i).findElement(By.tagName("div"));
                            else
                                menuItemElement = listElements.get(i);


                            returnValue = menuItemElement.isEnabled();


                            i = listElements.size();
                        }
                    }

                    // closing the menu window
                    menuElement.click();


                    sleep(1, TimeUnit.SECONDS);

                    // If the specified menu item wasn't found
                    if(!found) {
                        retries++;
                        // have we tried the maximum retries?
                        if (retries == MAXRETRIES) {
                            found = true;
                        }

                        log("Retry #" + retries);
                    }
                } catch (StaleElementReferenceException e) {
                    retries++;

                    // have we tried the maximum retries?
                    if (retries == MAXRETRIES) {

                        warning("ERROR!  StaleElementReference Exception - " + e.getMessage());
                        e.printStackTrace();
                        throw e;
                    } else if (clicked){
                        //need to close the menu in order to reset if Stale Element Reference was thrown
                        menuElement.click();
                    }
                }
            }

            if (!found) {
                throw new NoSuchElementException("Menu::isMenuItemEnabled -- menu item '" + menuItem + "' not found in the menu.");
            }
        }

        return returnValue;
    }


    /**
     * This method returns a list of all menu items, including the label if there is one, displayed within this menu.  If there
     * is a label, it is not a selectable item, but will be added to the item list for verification purposes.
     * <p>
     * <b><u>NOTE:</u></b> If the menu list doesn't exist, a NoSuchElementException is thrown.
     * </p>
     *
     * @return   a String array with a list of menu items for this menu
     */
    public String[] getItemList () {
        // declaring local variables
        String temp = "";
        String className = "";
        String[] returnList = {};
        ArrayList<String> tempList = new ArrayList<String>();
        WebElement labelElement;
        List<WebElement> listElements;


        if (isStubbed()) {
            returnList = new String[] {"One", "Two"};
            log("=== This menu locator is currently stubbed out.  Returning a String array with values 'One', 'Two'. ===");
        } else {
            // if the table refreshed while we are getting values, then a stale element is being thrown. To get around
            //     this will catch the stale element and try again
            boolean done = false;
            boolean clicked = false;
            int retries = 0;

            // getting the web element
            WebElement menuElement = null;
            int i = 0;
            while (!done) {
                sleep(1, TimeUnit.SECONDS);
                try {
                    // getting the combo box web element
                    menuElement = getWebElement();

                    // get a list of the menu items
                    listElements = menuElement.findElements(By.tagName("li"));

                    if (listElements.size() > 0) {
                        // adding menu items to tempList
                        while(i<listElements.size()){

                            // is this menu item displayed?
                            String style = listElements.get(i).getAttribute("style");
                            //check the elements parent style attribute also.
                           // style = listElements.get(i).findElement(By.xpath("..")).getAttribute("style");

                            if (!style.equals("display: none;")) {
                                // get the menu item name
                                temp = listElements.get(i).getAttribute("textContent");

                                // making sure no \n characters are in temp
                                temp = temp.replace("\n", "");
                                temp = temp.trim();

                                // add this item, only if there is text for it
                                if (temp.length() > 0) {
                                    tempList.add(temp);
                                }
                            }
                            ++i;
                        }
                    } else {
                        // since we don't have any list elements, we may have option tags
                        listElements = menuElement.findElements(By.tagName("option"));

                        for (int j = 0; j < listElements.size(); j++) {
                            // first, check if this list item is displayed
                            if (listElements.get(j).isDisplayed()) {
                                // get the menu item name
                                temp = listElements.get(j).getAttribute("textContent");

                                // add this item, only if there is text for it
                                if (temp.length() > 0) {
                                    tempList.add(temp);
                                }
                            }
                        }
                    }

                    // A list of items was obtained from the menu
                    if(tempList.size() != 0) {
                        done = true;
                    } else {
                        retries++;
                        // have we tried the maximum retries?
                        if (retries == MAXRETRIES) {
                            done = true;
                        }

                        log("Retry #" + retries);
                    }
                } catch(StaleElementReferenceException e){
                    retries++;
                    // have we tried the maximum retries?
                    if (retries == MAXRETRIES) {
                        done = true;

                        warning("ERROR!  StaleElementReference Exception - " + e.getMessage());
                        e.printStackTrace();
                        throw e;
                    } else {
                        //tempList = new ArrayList<String>();
                        if(clicked) {
                            //need to close the menu in order to reset if Stale Element Reference was thrown
                            menuElement.click();
                        }
                    }
                }
            }
            // now, transferring over tempList to returnList
            returnList = new String[tempList.size()];
            for (int k = 0; k < tempList.size(); k++) {
                returnList[k] = tempList.get(k);
            }


            // checking to see if there is only 1 item in the list, and that item is null -- then we want to return an empty list
            if (returnList.length == 1) {
                if (returnList[0] == null) {
                    returnList = new String[]{};
                }
            }
        }

        return returnList;
    }

    /**
     * This method checks if this menu contains the specified item.
     *
     * @param menuItem        [STRING] menu item to be searched for
     * @return                a boolean value 'true' if the specified menu item is in this menu, 'false' otherwise
     */
    public boolean containsMenuItem(String itemName) {
        boolean returnValue = false;
        String[] itemList = getItemList();

        // For each item in this Menu
        for(String curItemName : itemList) {
            // If the current item's name matches the given item name, then the specified item is in this Menu
            if(itemName.equals(curItemName)) {
                returnValue = true;
                break;
            }
        }

        return returnValue;
    }

    /**
     * This method waits for the specified menu item to appear in the menu.
     *
     * @param menuItem        [STRING] menu item to be searched for
     * @param timeOutSeconds  [INT] maximum time in seconds to wait for the menu item to appear in the menu.
     * @return                a boolean value 'true' if the specified menu item appears in this menu, 'false' otherwise
     */
    public boolean waitForMenuItem(String itemName, int timeOutSeconds){

        //sanity checking.
        if (timeOutSeconds < 0)
            throw new IllegalArgumentException("Parameter timeOutSeconds must be equal to or greater than zero.");

        // set start time
        long startTime = System.nanoTime();
        //initialize elapsed time
        int elapsedTime = 0;

        try {
            while ((elapsedTime <= timeOutSeconds) && (!containsMenuItem(itemName))) {
                sleep(1, TimeUnit.SECONDS);
                // get elapsed time
                elapsedTime = (int) TimeUnit.SECONDS.convert((System.nanoTime() - startTime), TimeUnit.NANOSECONDS);
            }
        }
        catch (StaleElementReferenceException e)
        {
            log ("waitForMenuItem:StaleElementReferenceException occurred");
            elapsedTime = (int) TimeUnit.SECONDS.convert((System.nanoTime() - startTime), TimeUnit.NANOSECONDS);
            sleep(1, TimeUnit.SECONDS);
        }


        if (elapsedTime > timeOutSeconds)
            return false;
        else
            return true;
    }

    /**
     * @uahtor Craig yYara
     * @param itemName
     * @return
     */
    public boolean waitForMenuItem (String itemName)
    {
        return this.waitForMenuItem(itemName, 120); // TODO: unhardcode
    }

    /**
     * @author Craig Yara
     * @param itemName
     * @param timeOutSeconds
     * @return
     */
    public boolean waitForMenuItemsList (String [] expectedMenuItems, int timeOutSeconds){

        //sanity checking.
        if (timeOutSeconds < 0)
            throw new IllegalArgumentException("Parameter timeOutSeconds must be equal to or greater than zero.");

        // set start time
        long startTime = System.nanoTime();
        //initialize elapsed time
        int elapsedTime = 0;
        String[] actualmenuItems;
        boolean done = false;
        boolean found = false;

        while(done == false) {

            actualmenuItems = this.getItemList();

            if (Arrays.equals(actualmenuItems, expectedMenuItems))
            {
                done = true;
                found = true;
            }

            if (done == false) {
                sleep(1, TimeUnit.SECONDS);
                // get elapsed time
                elapsedTime = (int) TimeUnit.SECONDS.convert((System.nanoTime() - startTime), TimeUnit.NANOSECONDS);
                if (elapsedTime > timeOutSeconds)
                {
                    done = true;
                }
            }
        }

        return found;

    }

    /**
     * @author Craig Yara
     * @param itemName
     * @return
     */
    public boolean waitForMenuItemsList (String [] expectedMenuItems)
    {
        return this.waitForMenuItemsList (expectedMenuItems, 30);
    }


    /**
     * waits for the drop down list to become non-empty.  Needed in slow server environment situations
     * @author Craig Yara
     * @return
     */
    public boolean waitForNonEmptyList ()
    {
       boolean returnResult = true;

       int timeOutSeconds = 120; //TODO unhardcocde timeout

       String title;

       // declaring local variables
       boolean itemFound = false;
       boolean done = false;
       long startTime;
       int elapsedTime;
       startTime = System.nanoTime();
       String[] itemList;

       // set start time
       while (done == false) {

           try {
               itemList = getItemList();

               if (itemList.length != 0) {
                   // good, we have values in the drop down list
                   itemFound = true;
                   done = true;
               }
           } catch (org.openqa.selenium.NoSuchElementException e) {

           } catch (StaleElementReferenceException e) {

           }


           if (itemFound == false) {
               // expected dialog title not there yet

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

       if(itemFound == false){
           returnResult = false;
       }
       return returnResult;
   }

    /**
     * This method returns a list of all SELECTED menu items, including the label if there is one, displayed within this menu.  If there
     * is a label, it is not a selectable item, but will be added to the item list for verification purposes.
     * <p>
     * <b><u>NOTE:</u></b> If the menu list doesn't exist, a NoSuchElementException is thrown.
     * </p>
     *
     * @return   a String array with a list of menu items for this menu
     */
    public String[] getSelectedItemList () {
        // declaring local variables
        String temp = "";
        String className = "";
        String[] returnList = {};
        ArrayList<String> tempList = new ArrayList<String>();
        WebElement labelElement;
        List<WebElement> listElements;


        if (isStubbed()) {
            returnList = new String[] {"One", "Two"};
            log("=== This menu locator is currently stubbed out.  Returning a String array with values 'One', 'Two'. ===");
        } else {
            // if the table refreshed while we are getting values, then a stale element is being thrown. To get around
            //     this will catch the stale element and try again
            boolean done = false;
            boolean clicked = false;
            int retries = 0;

            // getting the web element
            WebElement menuElement = null;
            int i = 0;
            while (!done) {
                sleep(1, TimeUnit.SECONDS);
                try {
                    // getting the combo box web element
                    menuElement = getWebElement();

                    // get a list of the menu items
                    listElements = menuElement.findElements(By.tagName("li"));

                    if (listElements.size() > 0) {
                        // adding menu items to tempList
                        while(i<listElements.size()){

                            // is this menu item displayed?
                            String style = listElements.get(i).getAttribute("style");
                            //check the elements parent style attribute also.
//                            if (style.isEmpty())
//                                style = listElements.get(i).findElement(By.xpath("..")).getAttribute("style");

                            if (!style.equals("display: none;") && listElements.get(i).getAttribute("class").contains("hp-selected")) {
                                // get the menu item name
                                temp = listElements.get(i).getAttribute("textContent");

                                // making sure no \n characters are in temp
                                temp = temp.replace("\n", "");
                                temp = temp.trim();

                                // add this item, only if there is text for it
                                if (temp.length() > 0) {
                                    tempList.add(temp);
                                }
                            }
                            ++i;
                        }
                    } else {
                        // since we don't have any list elements, we may have option tags
                        listElements = menuElement.findElements(By.tagName("option"));

                        for (int j = 0; j < listElements.size(); j++) {
                            // first, check if this list item is displayed
                            if (listElements.get(j).isDisplayed()) {
                                // get the menu item name
                                temp = listElements.get(j).getAttribute("textContent");

                                // add this item, only if there is text for it
                                if (temp.length() > 0) {
                                    tempList.add(temp);
                                }
                            }
                        }
                    }

                    // A list of items was obtained from the menu
                    if(tempList.size() != 0) {
                        done = true;
                    } else {
                        retries++;
                        // have we tried the maximum retries?
                        if (retries == MAXRETRIES) {
                            done = true;
                        }

                        log("Retry #" + retries);
                    }
                } catch(StaleElementReferenceException e){
                    retries++;
                    // have we tried the maximum retries?
                    if (retries == MAXRETRIES) {
                        done = true;

                        warning("ERROR!  StaleElementReference Exception - " + e.getMessage());
                        e.printStackTrace();
                        throw e;
                    } else {
                        //tempList = new ArrayList<String>();
                        if(clicked) {
                            //need to close the menu in order to reset if Stale Element Reference was thrown
                            menuElement.click();
                        }
                    }
                }
            }
            // now, transferring over tempList to returnList
            returnList = new String[tempList.size()];
            for (int k = 0; k < tempList.size(); k++) {
                returnList[k] = tempList.get(k);
            }


            // checking to see if there is only 1 item in the list, and that item is null -- then we want to return an empty list
            if (returnList.length == 1) {
                if (returnList[0] == null) {
                    returnList = new String[]{};
                }
            }
        }

        return returnList;
    }

}
