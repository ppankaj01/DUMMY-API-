/*
 * © Copyright 2002-2015 Hewlett Packard Enterprise Development LP
 */

package Elements;

import java.util.List;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;



/**
 * This class provides utility methods for the Selenium {@link WebElement}.
 */
public class ElementUtil
{
    /**
     * This method gets the CSS classes of the specified web element and returns them as an array of strings.
     * It gets the CSS classes from the <code>class</code> attribute of the web element.
     *
     * @param  webElement  a {@link WebElement}
     * @return an array of CSS class names of the specified web element
     */
    public static String[] getCSSClasses(WebElement webElement) {
        // get the "class" attribute of the web element;
        // this attribute should contain a list of CSS classes separated by spaces
        String classesStr = webElement.getAttribute("class");
        if (classesStr == null) {
            // this web element does not have the "class" attribute
            return new String[0];
        } else {
            // found the "class" attribute; split it by spaces
            return classesStr.split(" ");
        }
    }

    /**
     * This method checks if the specified web element contains a particular CSS class name.
     * It gets the CSS classes from the <code>class</code> attribute of the web element.
     *
     * @param  webElement    a {@link WebElement}
     * @param  cssClassName  name of the CSS class to check
     * @return <code>true</code> if the specified web element has the CSS class specified by <code>cssClassName</code>;
     *         <code>false</code> otherwise
     */
    public static boolean hasCSSClass(WebElement webElement, String cssClassName) {
        boolean returnValue = false;

        // get CSS class names of the web element and look for a match
        String[] cssClasses = getCSSClasses(webElement);
        for (String cssClass : cssClasses) {
            if (cssClass.equals(cssClassName)) {
                returnValue = true;
            }
        }

        return returnValue;
    }


    /**
     * This method gets the text of the specified web elements and returns them as an array.
     *
     * @param  webElements  a list of {@link WebElement} instances
     */
    public static String[] getText(List<WebElement> webElements)
    {
        return getText(webElements, false);
    }

    /**
     * This method gets the text of the specified web elements and returns them as an array.
     * If <code>toTrim</code> is <code>true</code>, it trims the leading and trailing white spaces
     * of the text.
     * @param  webElements  a list of {@link WebElement} instances
     * @param  toTrim       a boolean value determining whether the leading and trailing white spaces
     *                      of text will be trimmed or not
     */
    public static String[] getText(List<WebElement> webElements, boolean toTrim)
    {
        int count = webElements.size();
        String[] textArray = new String[count];
        for( int i = 0; i < count; i++ ) {
            String text = webElements.get(i).getText();
            text = text.replace("\n"," ");
            if (toTrim) {
                textArray[i] = text.trim();
            } else {
                textArray[i] = text;
            }
        }
        return textArray;
    }

    /**
     * This method trims the leading and trailing white spaces of all text strings in an array.
     *
     * @param  strArray  an array of text strings
     * @return the array of strings that have been trimmed 
     */
    public static String[] trimStrings(String[] strArray)
    {
        final int count = strArray.length;
        String[] newArray = new String[count];
        for( int i = 0; i < count; i++ ) {
            newArray[i] = strArray[i].trim();
        }
        return newArray;
    }

    /**
     * Returns a text string describing the HTML element referenced by the specified
     * {@link WebElement}.
     */
    public static String describeElement(WebElement el) {
        String tagName = el.getTagName();
        String id = el.getAttribute("id");
        String text = el.getText();
        if (id != null && id.length() > 0) {
            return "<" + tagName + " id='" + id + "'>" + text + "</" + tagName + ">";
        } else {
            return "<" + tagName + ">" + text + "</" + tagName + ">";
        }
    }


    /**
     * This method gets the spcific attribute value of the id and returns them as an array.
     * If <code id="abc">toTrim</code> is <code>true</code>, it trims the leading and trailing white spaces
     * of the text.
     * @param  webElements  a list of {@link WebElement} instances
     * @param  toTrim       a boolean value determining whether the leading and trailing white spaces
     *                      of text will be trimmed or not
     * @param  id a attribut id
     */
    public static String[] getAttributeText(List<WebElement> webElements, boolean toTrim, String attributeId)
    {
        int count = webElements.size();
        String[] textArray = new String[count];
        for( int i = 0; i < count; i++ ) {
            String text = webElements.get(i).getAttribute(attributeId);
            text = text.replace("\n"," ");
            if (toTrim) {
                textArray[i] = text.trim();
            } else {
                textArray[i] = text;
            }
        }
        return textArray;
    }
}
