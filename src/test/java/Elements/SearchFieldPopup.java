/*
 * © Copyright 2002-2015 Hewlett Packard Enterprise Development LP
 */

package Elements;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static Elements.Globals.ELEMENTTIMEOUT;
import static Elements.Globals.browser;
import static Helpers.Utility.log;
import static Helpers.Utility.sleep;
import static Helpers.Utility.warning;


/**
 * This class is used to interact with the Search field popup web element at the top of each SSMC screen.
 *
 * Your selenium locator should select an input tag (<input>), which can be read-only.
 *
 * Created by millera on 11/25/2014.
 */
public class SearchFieldPopup extends BaseElement {

    // - - - - - Class Attributes - - - - -
    private By searchControlLocator;
    private By searchMenuLocator;


    // - - - - - Constructor - - - - -

    /**
     * Constructor used to reference this class.
     *
     * @param locator      the Selenium {@link org.openqa.selenium.By locator} for locating this web element
     */
    public SearchFieldPopup(By searchControlLocator, By searchMenuLocator) {
        // setting this classes locator value for this specific text
        super(searchControlLocator);

        this.searchControlLocator = searchControlLocator;
        this.searchMenuLocator = searchMenuLocator;
    }


    //  - - - - - Class Methods - - - - -

    /**
     * This method is to be used by a subclass to check whether the SearchFieldPopup's two locators are stubbed out or not.
     * A web element's locator is considered stubbed out if it contains the string "STUBBED".
     * @return  <code>true</code> if the web element's locator is stubbed out; <code>false</code> otherwise
     */
    @Override
    protected boolean isStubbed() {
        return this.searchControlLocator.toString().contains("STUBBED") || this.searchMenuLocator.toString().contains("STUBBED");
    }


    /**
     * This method is used to open the SearchFieldPopup by clicking on the search bar area.
     *
     * @return  'true' if the SearchFieldPopup was successfully opened or was already open; 'false' otherwise
     */
    public boolean openSearchFieldPopup() {
        // declaring local variables
        WebElement searchBar, searchMenu;
        boolean returnValue = false;

        searchMenu = browser.findElement(searchMenuLocator);

        // If the Search menu isn't open
        if(!searchMenu.getAttribute("class").contains("hp-active")) {
            // Opens the SearchFieldPopup by clicking on the search bar area
            searchBar = browser.findElement(searchControlLocator);
            searchBar.click();

            // Gives the browser two seconds to catch up with the web driver
            sleep(2, TimeUnit.SECONDS);

            // If the Search menu is open
            if(searchMenu.getAttribute("class").contains("hp-active")) {
                returnValue = true;
            }
        }
        // If the Search menu is already open
        else {
            returnValue = true;
        }

        return returnValue;
    }


    /**
     * This method is used to close the SearchFieldPopup by clicking on the search bar area.
     *
     * @return  'true' if the SearchFieldPopup was successfully closed or was already closed; 'false' otherwise
     */
    public boolean closeSearchFieldPopup() {
        // declaring local variables
        WebElement searchMenu, textField;
        boolean returnValue = false;

        // Obtains the SearchFieldPopu's search menu
        searchMenu = browser.findElement(searchMenuLocator);

        // If the search menu is open
        if(searchMenu.getAttribute("class").contains("hp-active")) {
            // Closes the SearchFieldPopup by pressing the Escape buttn
            textField = searchMenu.findElement(By.cssSelector("#hp-search-input input"));
            textField.sendKeys(Keys.ESCAPE);

            // Gives the browser two seconds to catch up with the web driver
            sleep(2, TimeUnit.SECONDS);

            // If the search menu is closed
            if(!searchMenu.getAttribute("class").contains("hp-active")) {
                returnValue = true;
            }
        }
        // If the search menu is already closed
        else {
            returnValue = true;
        }

        return returnValue;
    }


    /**
     * This method is used to select the scope of a search, either local or global.
     *
     * @param useLocalScope  [BOOLEAN] 'true' specifies a local scope, 'false' specifies a global scope
     */
    public void selectScope(boolean useLocalScope) {
        // declaring local variables
        WebElement searchBar, searchMenu, scopeButton;

        // is this object's query stubbed out?
        if (this.isStubbed()) {
            log("=== This SearchFieldPopup is currently stubbed out. ===");
        } else {
            // Opens the SearchFieldPopup
            if(!openSearchFieldPopup()) {
                warning("ERROR!  Could not open SearchFieldPopup after clicking it.");
                return;
            }

            searchMenu = browser.findElement(searchMenuLocator);

            // If a local scope is specified
            if(useLocalScope) {
                scopeButton = searchMenu.findElement(By.id("hp-search-scope-local"));
            }
            // If a global scope is specified
            else {
                scopeButton = searchMenu.findElement(By.id("hp-search-scope-global"));
            }

            // Selects the specified radio button
            scopeButton.click();

            // Closes the SearchFieldPopup
            if(!closeSearchFieldPopup()) {
                warning("ERROR!  Could not close SearchFieldPopup.");
            }
        }
    }


    /**
     * This method is used to obtain the scope of a search by checking the local and global scope radio buttons.
     *
     * @return  a String value representing the name of the selected scope
     */
    public String getScope() {
        // declaring local variables
        WebElement searchBar, searchMenu;
        RadioButton scopeButton;
        String returnValue = "";

        // is this object's query stubbed out?
        if (this.isStubbed()) {
            returnValue = "VALUE";
            log("=== This SearchFieldPopup is currently stubbed out. Returning value '" + returnValue + "' ===");
        } else {
            // Opens the SearchFieldPopup
            if(!openSearchFieldPopup()) {
                returnValue = "VALUE";
                warning("ERROR!  Could not open SearchFieldPopup after clicking it. Returning value '" + returnValue + "'");
                return returnValue;
            }

            // Obtains the local scope radio button
            searchMenu = browser.findElement(searchMenuLocator);
            scopeButton = new RadioButton(By.id("hp-search-scope-local"), By.cssSelector("label[for='hp-search-scope-local']"));

            // If a local scope is selected
            if(scopeButton.isSelected()) {
                returnValue = scopeButton.getText();
            } else {
                // Obtains the global scope radio button
                scopeButton = new RadioButton(By.id("hp-search-scope-global"), By.cssSelector("label[for='hp-search-scope-global']"));

                // If a global scope is selected
                if(scopeButton.isSelected()) {
                    returnValue = scopeButton.getText();
                }
                // If neither a local or global scope is selected
                else {
                    warning("No search scope has been selected.");
                }
            }

            // Closes the SearchFieldPopup
            if(!closeSearchFieldPopup()) {
                warning("ERROR!  Could not close SearchFieldPopup.");
            }
        }

        return returnValue;
    }


    /**
     * This method is used to conduct a search using the SearchFieldPopup.
     *
     * @param searchText     [STRING] search text to enter into the SearchFieldPopup's text field
     * @param useLocalScope  [[BOOLEAN] 'true' specifies a local scope, 'false' specifies a global scope
     */
    public void search(String searchText, boolean useLocalScope) {

        int MAX_ATTEMPTS = 5;
        int current_attempt = 1;
        String actualResult;

        boolean done = false;

        while ((done == false) && (current_attempt <= MAX_ATTEMPTS))
        {
            this.enter_search(searchText, useLocalScope);

            actualResult = getText();

            if (actualResult.equals(searchText)) {
                done = true;
            }
            else
                current_attempt ++;
        }

        if (done == false) {
            log ("** WARNING: the setting of the search filter may not have worked.  ");
        }

    }

    protected void enter_search(String searchText, boolean useLocalScope) {
        // declaring local variables
        WebElement searchMenu, searchTextField;

        // is this object's query stubbed out?
        if (this.isStubbed()) {
            log("=== This SearchFieldPopup is currently stubbed out. ===");
        } else {
            // Clears the text of the SearchFieldPopup's text field
            clearText();
            sleep(3,TimeUnit.SECONDS);

            // Sets the scope of the search
            selectScope(useLocalScope);

            // Sets the SearchFieldPopup's text field with the given text
            setText(searchText);
            log("Text set in searchpane is "+this.getText());

            // Opens the SearchFieldPopup
            if(!openSearchFieldPopup()) {
                warning("ERROR!  Could not open SearchFieldPopup after clicking it.");
                return;
            }

            // Starts the search by sending a Return key-press to the SearchFieldPopup's text field
            searchMenu = browser.findElement(searchMenuLocator);
            searchTextField = searchMenu.findElement(By.cssSelector("#hp-search-input input"));
            searchTextField.sendKeys(Keys.RETURN);

            // Waits for the screen's table to finish loading before continuing
            panelTableLoadedWait();

            // If the search has a local scope
            if(useLocalScope) {
                // Closes the SearchFieldPopup
                if (!closeSearchFieldPopup()) {
                    warning("ERROR!  Could not close SearchFieldPopup.");
                }
            }
        }
    }

    /**
     * This method is used to set the value of the text field in the SearchFieldPopup.
     *
     * @param value  [STRING] text to enter into the SearchFieldPopup's text field
     */
    public void setText(String value) {
        // declaring local variables
        WebElement searchBar, searchMenu;
        TextField searchTextField;

        // is this object's query stubbed out?
        if (this.isStubbed()) {
            log("=== This SearchFieldPopup is currently stubbed out. ===");
        } else {
            // Opens the SearchFieldPopup
            if(!openSearchFieldPopup()) {
                warning("ERROR!  Could not open SearchFieldPopup after clicking it.");
                return;
            }

            // Sets the text of the SearchFieldPopup's text field
            searchMenu = browser.findElement(searchMenuLocator);
            searchTextField = new TextField(By.cssSelector("#hp-search-input input"));
            searchTextField.setText(value);

            // Closes the SearchFieldPopup
            /*if(!closeSearchFieldPopup()) {
                warning("ERROR!  Could not close SearchFieldPopup.");
            }*/
        }
    }


    /**
     * This method is used to set the value in the text field in the SearchFieldPopup, one character at a time, waiting
     * seconds between entering each character.
     *
     * @param value    [STRING] value to enter into the the SearchFieldPopup's text field
     * @param seconds  [INT] number of seconds to pause between each character listed in value
     */
    public void setText(String value, int seconds) {
        // declaring local variables
        WebElement searchBar, searchMenu;
        TextField searchTextField;
        String temp = "";

        // is this object's query stubbed out?
        if (this.isStubbed()) {
            log("=== This SearchFieldPopup is currently stubbed out. ===");
        } else {
            // Opens the SearchFieldPopup
            if(!openSearchFieldPopup()) {
                warning("ERROR!  Could not open SearchFieldPopup after clicking it.");
                return;
            }

            // Sets the text of the SearchFieldPopup's text field, one character at a time over a specified period of time
            searchMenu = browser.findElement(searchMenuLocator);
            searchTextField = new TextField(By.cssSelector("#hp-search-input input"));
            searchTextField.setText(value, seconds);

            // Closes the SearchFieldPopup
            /*if(!closeSearchFieldPopup()) {
                warning("ERROR!  Could not close SearchFieldPopup.");
            }*/
        }
    }


    /**
     * This method is used to clear the existing value from the SearchFieldPopup's text field.
     */
    public void clearText() {
        // declaring local variables
        WebElement searchBar, searchMenu, searchTextField;

        // is this object's query stubbed out?
        if (this.isStubbed()) {
            log("=== This SearchFieldPopup is currently stubbed out. ===");
        } else {
            // Opens the SearchFieldPopup
            if(!openSearchFieldPopup()) {
                warning("ERROR!  Could not open SearchFieldPopup after clicking it.");
                return;
            }

            // Clears the text of the SearchFieldPopup's text field
            searchMenu = browser.findElement(searchMenuLocator);
            searchTextField = searchMenu.findElement(By.cssSelector("#hp-search-input input"));
            searchTextField.clear();
            searchTextField.sendKeys(Keys.RETURN);

            // Gives the browser two seconds to catch up with the web driver
            sleep(2, TimeUnit.SECONDS);


            if (searchTextField.getText().equals("") == false)
            {
                log ("hmmm, clear() of table filter did not work.  Trying again..");
                sleep(2, TimeUnit.SECONDS);
                searchTextField.clear();
                searchTextField.sendKeys(Keys.RETURN);
                sleep(2, TimeUnit.SECONDS);
            }

            // Waits for the screen's table to finish loading before continuing
            panelTableLoadedWait();

            // Closes the SearchFieldPopup
            if(!closeSearchFieldPopup()) {
                warning("ERROR!  Could not close SearchFieldPopup.");
            }
        }
    }

    /**
     * This method returns the actual text value listed in the SearchFieldPopup's text field.  If the text field is
     * empty, then an empty string will be returned.
     *
     * @return  a String value representing the actual text displayed in the text field.
     */
    public String getText() {
        // declaring local variables
        WebElement searchBar, searchMenu;
        TextField searchTextField;
        String returnValue = "";

        // is this object's query stubbed out?
        if (this.isStubbed()) {
            returnValue = "VALUE";
            log("=== This SearchFieldPopup is currently stubbed out. Returning value '" + returnValue + "' ===");
        } else {
            // Opens the SearchFieldPopup
            if(!openSearchFieldPopup()) {
                returnValue = "VALUE";
                warning("ERROR!  Could not open SearchFieldPopup after clicking it. Returning value '" + returnValue + "'");
                return returnValue;
            }

            // Obtains the text of the SearchFieldPopup's text field
            searchMenu = browser.findElement(searchMenuLocator);
            searchTextField = new TextField(By.cssSelector("#hp-search-input input"));
            returnValue = searchTextField.getText();

            // Closes the SearchFieldPopup
            /*if(!closeSearchFieldPopup()) {
                warning("ERROR!  Could not close SearchFieldPopup.");
            }*/
        }

        return returnValue;
    }


    /**
     * This method checks to see if the SearchFieldPopup's text field is enabled, ready for the user to enter a value.
     * If the text field is enabled, this method will return 'true'.  Otherwise, this method will return 'false'.
     *
     * @return  a String value representing the actual text displayed in the text field.
     */
    public boolean isTextFieldEnabled() {
        // declaring local variables
        WebElement searchBar, searchMenu;
        TextField searchTextField;
        boolean returnValue = false;

        // is this object's query stubbed out?
        if (this.isStubbed()) {
            returnValue = true;
            log("=== This SearchFieldPopup is currently stubbed out. Returning value '" + returnValue + "' ===");
        } else {
            // Opens the SearchFieldPopup
            if(!openSearchFieldPopup()) {
                returnValue = true;
                warning("ERROR!  Could not open SearchFieldPopup after clicking it. Returning value '" + returnValue + "'");
                return returnValue;
            }

            // Checks if the SearchFieldPopup's text field is enabled
            searchMenu = browser.findElement(searchMenuLocator);
            searchTextField = new TextField(By.cssSelector("#hp-search-input input"));
            returnValue = searchTextField.isEnabled();

            // Closes the SearchFieldPopup
            if(!closeSearchFieldPopup()) {
                warning("ERROR!  Could not close SearchFieldPopup.");
            }
        }

        return returnValue;
    }

    /**
     * This method checks to see if the SearchFieldPopup's text field shows as invalid.  Some text fields have logic
     * behind them that check the value the user entered.  For example, password textfields may check to see if the
     * characters are a certain length.  If the logic returns that the value is invalid, the text field changes where a
     * color of red is shown and possisbly a message.  This method only checks to see if the text field shows as
     * "invlaid".  It doesn't show the color, nor the message, just if the text field is shown on the web page as being
     * invalid.
     * <p>
     * If the text field is showing as invalid, then this method returns a boolean value of 'true'.  Otherwise, if the
     * text field value is valid, this method returns a boolean value of 'false'.
     * <p>
     * In the case where a text field doesn't have logic to check the value, this method will return 'false'.
     *
     * @return  'true' if the text field is showing as invalid; otherwise (or if the text field lacks the logic to check
     * the value, 'false'
     */
    public boolean isSearchTextInvalid() {
        // declaring local variables
        String className = "";
        WebElement searchBar, searchMenu;
        TextField searchTextField;
        boolean returnValue = false;

        // is this object's query stubbed out?
        if (this.isStubbed()) {
            returnValue = true;
            log("=== This SearchFieldPopup is currently stubbed out. Returning value '" + returnValue + "' ===");
        } else {
            // Opens the SearchFieldPopup
            if(!openSearchFieldPopup()) {
                returnValue = true;
                warning("ERROR!  Could not open SearchFieldPopup after clicking it. Returning value '" + returnValue + "'");
            }

            // Checks if the text in the SearchFieldPopup's text field is invalid
            searchMenu = browser.findElement(searchMenuLocator);
            searchTextField = new TextField(By.cssSelector("#hp-search-input input"));
            returnValue = searchTextField.isEntryInvalid();

            // Closes the SearchFieldPopup
            /*if(!closeSearchFieldPopup()) {
                warning("ERROR!  Could not close SearchFieldPopup.");
            }*/
        }

        return returnValue;
    }


    /**
     * This method selects the specified menu item from the SearchFieldPopup's search menu list.
     *
     * @param menuItem  [STRING] menu item to select
     */
    public void selectItem (String menuItemName) {
        String temp = "";
        List<WebElement> listElements;
        WebElement searchElement;

        // is this object's query stubbed out?
        if (this.isStubbed()) {
            log("=== This SearchFieldPopup is currently stubbed out. ===");
        } else {
            // Opens the SearchFieldPopup
            if(!openSearchFieldPopup()) {
                warning("ERROR!  Could not open SearchFieldPopup after clicking it.");
                return;
            }

            // Gives the browser two seconds to catch up with the web driver
            sleep(2, TimeUnit.SECONDS);

            // Obtains the SearchFieldPopup's search menu
            WebElement searchMenu = browser.findElement(this.searchMenuLocator);

            // Obtains all of the items in the menu list
            List<WebElement> menuItemsList = searchMenu.findElements(By.cssSelector("#hp-search-suggestions > li,#hp-search-recent > li"));

            // Searches for the menu item we are looking for and clicks on it
            boolean found = false;
            int i = 0;
            while((!found) && (i < menuItemsList.size())) {
                if(menuItemsList.get(i).isDisplayed()) {
                    //Note: some of these menu items are long and wrap for multiple lines.
                    //      So for this reason, I just check if the item name matches the first part of the menu item text.
                    String menuItemText = menuItemsList.get(i).getText().replace("\n", " ");

                    if( menuItemText.startsWith(menuItemName)) {
                        found = true;
                        menuItemsList.get(i).click();
                    } else {
                        ++i;
                    }
                } else {
                    ++i;
                }
            }

            // Gives the browser two seconds to catch up with the web driver
            sleep(2, TimeUnit.SECONDS);

            // Waits for the screen's table to finish loading before continuing
            panelTableLoadedWait();

            // Closes the SearchFieldPopup
            if(!closeSearchFieldPopup()) {
                warning("ERROR!  Could not close SearchFieldPopup.");
            }
        }
    }


    /**
     * This method enters the search text in the SearchFieldPopup's text field and then selects the menu item from the
     * SearchFieldPopup's search menu list.
     *
     * @param searchText  [STRING] text to enter and search
     * @param menuItem    [STRING] menu item to select
     */
    public void selectItem (String searchText, String menuItemName) {
        String temp = "";
        List<WebElement> listElements;
        WebElement textFieldElement;

        // is this object's query stubbed out?
        if (this.isStubbed()) {
            log("=== This SearchFieldPopup is currently stubbed out. ===");
        } else {
            // Opens the SearchFieldPopup
            if(!openSearchFieldPopup()) {
                warning("ERROR!  Could not open SearchFieldPopup after clicking it.");
                return;
            }

            // Clears and sets the text of the SearchFieldPopup's text field
            clearText();
            setText(searchText);

            // Gives the browser two seconds to catch up with the web driver
            sleep(2, TimeUnit.SECONDS);

            // Obtains the SearchFieldPopup's search menu
            WebElement searchMenu = browser.findElement(this.searchMenuLocator);

            // Obtains all of the items in the menu list
            List<WebElement> menuItemsList = searchMenu.findElements(By.cssSelector("#hp-search-suggestions > li,#hp-search-recent > li"));

            // Searches for the menu item we are looking for and clicks on it
            boolean found = false;
            int i = 0;
            while((!found) && (i < menuItemsList.size())) {
                if(menuItemsList.get(i).isDisplayed()) {
                    //Note: some of these menu items are long and wrap for multiple lines.
                    //      So for this reason, I just check if the item name matches the first part of the menu item text.
                    String menuItemText = menuItemsList.get(i).getText().replace("\n", " ");

                    if(menuItemText.startsWith(menuItemName)) {
                        found = true;
                        menuItemsList.get(i).click();
                    } else {
                        ++i;
                    }
                } else {
                    ++i;
                }
            }

            // Gives the browser two seconds to catch up with the web driver
            sleep(2, TimeUnit.SECONDS);

            // Waits for the screen's table to finish loading before continuing
            panelTableLoadedWait();

            // Closes the SearchFieldPopup
            if(!closeSearchFieldPopup()) {
                warning("ERROR!  Could not close SearchFieldPopup.");
            }
        }
    }


    /**
     * This method returns a list of all SearchFieldPopup's search menu items, including the label if there is one,
     * displayed within this menu.  If there is a label, it is not a selectable item, but will be added to the item
     * list for verification purposes.
     *
     * @return  a String array with a list of menu items for this menu
     */
    public String[] getItemList () {
        // declaring local variables
        ArrayList<String> tempList = new ArrayList<String>();
        WebElement labelElement;
        List<WebElement> listElements;
        WebElement searchElement;

        if (this.isStubbed()) {
            tempList.add("One");
            tempList.add("Two");
            log("=== This SearchFieldPopup is currently stubbed out. Returning a String array with values 'One', 'Two'. ===");
        } else {
            // Opens the SearchFieldPopup
            if(!openSearchFieldPopup()) {
                warning("ERROR!  Could not open SearchFieldPopup after clicking it.  Returning an empty String array with values 'One', 'Two'.");
                return new String[]{"One", "Two"};
            }

            // Gives the browser two seconds to catch up with the web driver
            sleep(2, TimeUnit.SECONDS);

            // Obtains the SearchFieldPopup's search menu
            WebElement searchMenu = browser.findElement(this.searchMenuLocator);

            // Obtains all of the items in the menu list
            List<WebElement> menuItemsList = searchMenu.findElements(By.cssSelector("#hp-search-suggestions > li,#hp-search-recent > li"));

            for (WebElement menuItem:menuItemsList){
                if(menuItem.isDisplayed())
                    tempList.add(menuItem.getText().replace("\n", " "));
            }

            // Closes the SearchFieldPopup
            if(!closeSearchFieldPopup()) {
                warning("ERROR!  Could not close SearchFieldPopup.");
            }
        }

        // Transfers over tempList to returnList
        String[] returnList = new String[tempList.size()];
        for (int i = 0; i < tempList.size(); i++) {
            returnList[i] = tempList.get(i);
        }

        return returnList;
    }


    /**
     * This method first enters the search text in the SearchFieldPopup's text field and then returns the resulting
     * list of all SearchFieldPopup's search menu items, including the label if there is one, displayed within this
     * menu.  If there is a label, it is not a selectable item, but will be added to the item list for verification purposes.
     *
     * @param searchText  [STRING] text to enter and search
     * @return            a String array with a list of menu items for this menu
     */
    public String[] getItemList (String searchText) {
        // declaring local variables
        ArrayList<String> tempList = new ArrayList<String>();
        WebElement labelElement;
        List<WebElement> listElements;
        WebElement textFieldElement;
        WebElement searchElement;

        if (this.isStubbed()) {
            tempList.add("One");
            tempList.add("Two");
            log("=== This SearchFieldPopup is currently stubbed out. Returning a String array with values 'One', 'Two'. ===");
        } else {
            // Opens the SearchFieldPopup
            if(!openSearchFieldPopup()) {
                warning("ERROR!  Could not open SearchFieldPopup after clicking it.  Returning an empty String array with values 'One', 'Two'.");
                return new String[]{"One", "Two"};
            }

            // Clears and sets the text of the SearchFieldPopup's text field
            clearText();
            setText(searchText);

            // Gives the browser 15 seconds to catch up with the web driver
            sleep(15, TimeUnit.SECONDS);

            // Obtains the SearchFieldPopup's search menu
            WebElement searchMenu = browser.findElement(this.searchMenuLocator);

            // Obtains all of the items in the menu list
            List<WebElement> menuItemsList = searchMenu.findElements(By.cssSelector("#hp-search-suggestions > li,#hp-search-recent > li"));

            for (WebElement menuItem:menuItemsList){
                if(menuItem.isDisplayed())
                    tempList.add(menuItem.getText().replace("\n", " "));
            }

            /*
            // Clears the text of the SearchFieldPopup's text field
            clearText();

            // Closes the SearchFieldPopup
            if(!closeSearchFieldPopup()) {
                warning("ERROR!  Could not close SearchFieldPopup.");
            }
            */
        }

        // Transfers over tempList to returnList
        String [] returnList = new String[tempList.size()];
        for (int i = 0; i < tempList.size(); i++) {
            returnList[i] = tempList.get(i);
        }

        return returnList;
    }


    /**
     * The purporse of this method is to wait for the panel's table to load (if a table is available) or until the
     * timout time is reached.
     */
    private void panelTableLoadedWait() {
        boolean done = false;
        int elapsedTime = 0;
        //Set start time
        long startTime = System.nanoTime();
        Table panelTable = null;
        String tableSelector = ".hp-page .hp-master-details .hp-master-pane .dataTables_wrapper";
        String altTableSelector = ".hp-page .hp-master-full .dataTables_wrapper";
        String searchTableSelector = "#hp-search-page .hp-page-contents #hp-search-results";

        if(browser.findElements(By.cssSelector(tableSelector)).size() > 0) {
            panelTable = new Table(By.cssSelector(tableSelector));
        } else if(browser.findElements(By.cssSelector(altTableSelector)).size() > 0) {
            panelTable = new Table(By.cssSelector(altTableSelector));
        } else if(browser.findElements(By.cssSelector(searchTableSelector)).size() > 0) {
            panelTable = new Table(By.cssSelector(searchTableSelector));
        }else {
            log("        No table available to check if loaded.");
            return;
        }

        //Sometimes there is an issue of the table taking a long time to load, waiting till the table is loaded before grabbing information
        while(!done) {
            if(panelTable.isLoaded()) {
                log("        It took the table '" + elapsedTime + "' seconds to load");
                done = true;
            } else if(elapsedTime >= ELEMENTTIMEOUT) {
                //Making sure that the MAT doesn't get stuck in an infinite loop, giving it a timeout of ELEMENTTIMEOUT
                warning("        The table took to over '" + ELEMENTTIMEOUT + "' to load, bailing out");
                done = true;
            } else {
                //Waiting a second before looking again to see if the table is loaded
                sleep(1, TimeUnit.SECONDS);
            }

            //Get elapsed time
            elapsedTime = (int)TimeUnit.SECONDS.convert((System.nanoTime() - startTime), TimeUnit.NANOSECONDS);
        }
    }

    /**
     * This method is used to press Enter key in search textfield
     */
    public void pressEnter() {
        // declaring local variables
        WebElement searchMenu, searchTextField;
        // is this object's query stubbed out?
        if (this.isStubbed()) {
            log("=== This SearchFieldPopup is currently stubbed out. ===");
        } else {
            // Sets the text of the SearchFieldPopup's text field
            searchMenu = browser.findElement(searchMenuLocator);
            searchTextField = searchMenu.findElement(By.cssSelector("#hp-search-input input"));
            searchTextField.sendKeys(Keys.ENTER);

            // Waits for the screen's table to finish loading before continuing
            panelTableLoadedWait();
        }
    }
    /**
     * This method is used to set the value of the text field in the SearchFieldPopup.
     *
     * @param value  [STRING] text to enter into the SearchFieldPopup's text field
     */
    public void setTextAndEnter(String value) {
        // declaring local variables

        TextField searchTextField;

        // is this object's query stubbed out?
        if (this.isStubbed()) {
            log("=== This SearchFieldPopup is currently stubbed out. ===");
        } else {
            // Opens the SearchFieldPopup
            if(!openSearchFieldPopup()) {
                warning("ERROR!  Could not open SearchFieldPopup after clicking it.");
                return;
            }

            // Sets the text of the SearchFieldPopup's text field

            searchTextField = new TextField(By.cssSelector("#hp-search-input input"));
            searchTextField.setText(value);
            searchTextField.clickAndEnter();


        }
    }


}
