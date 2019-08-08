/*
 * © Copyright 2002-2015 Hewlett Packard Enterprise Development LP
 */

package Elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static Helpers.Utility.log;


/**
 * This class is used to interact with an icon.
 *
 * Your selenium locator should select a div tag refers to an icon, such as (and most commonly) a status icon.
 *
 * Created by svultee on 5/21/14.
 */
public class Icon extends BaseElement{

    // - - - - - Constructor - - - - -

    /**
     * Constructor used to reference this class.
     *
     * @param locator      the Selenium {@link By locator} for locating this web element
     */
    public Icon (By locator) {
        // setting this classes locator value for this specific text
        super(locator);
    }

    /**
     * This method returns the string of the image value or file name.
     * <p>
     * <b><u>NOTE:</u></b>  The image is first checked if it exists before interacting with it.  If the image
     * does not exist, an error will be logged indicating the image did not exist, and the test will automatically
     * stop.
     *
     * @return      [STRING] the name of the image value or file name
     */
    public String getIconValue(){
        // declaring local variables
        String returnValue = "";
        String cls;
        String[] classes;
        WebElement webElement;
        boolean found = false;
        int i=0;

        if (this.isStubbed()) {
            log("=== This image's query is currently stubbed out. ===");
            returnValue = "VALUE";
        } else {

            webElement = getWebElement();
            //get the list of classes
            cls = webElement.getAttribute("class");
            classes = cls.split(" ");

            //look for the class that starts  with hp-status-. That seems to be the one we want.
            while((i<classes.length)&& !found){
                if (classes[i].startsWith("hp-status-"))
                    found = true;
                else
                    ++i;
            }
            //return the one we found
            if (found)
                returnValue = classes[i];

                //otherwise return the whole class string.
                //if it turns out there is some other way we can determine the right one to pick we can add the code here.
            else
                returnValue = cls;

        }

        return returnValue;
    }





}
