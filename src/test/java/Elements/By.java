/*
 * © Copyright 2002-2015 Hewlett Packard Enterprise Development LP
 */

package Elements;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.remote.RemoteWebElement;

/**
 * This class extends the Selenium {@link org.openqa.selenium.By} class to provide
 * finding web elements by using a jQuery selector or JavaScript code.
 *
 * <P>Below are examples for finding web elements using a jQuery selector.</P>
 *
 * <PRE>
 *   // Replace import org.openqa.selenium.By;
 *   import com.hp.piano.test.ui.elements.By;
 *
 *   // Find the first &lt;table&gt; element.
 *   WebElement table1 = webDriver.findElement(By.jQuery("table"));
 *
 *   // Find the last &lt;table&gt; element.
 *   WebElement table2 = webDriver.findElement(By.jQuery("table:last"));
 *
 *   // Find all &lt;table&gt; elements.
 *   List<WebElement> tables = webDriver.findElements(By.jQuery("table"));
 *
 *   // Find the selected row in the last &lt;table&gt;.
 *   // Note: This row has the CSS class "selected".
 *   WebElement selected = webDriver.findElement(By.jQuery("table:last .selected"));
 * </PRE>
 */
public abstract class By extends org.openqa.selenium.By {

    /**
     * Creates a Selenium {@link By locator} that can be used to locate web elements
     * using a jQuery selector.
     * @param  selector  a jQuery selector string (without <code>$()</code>)
     * @return a <code>By</code> object that locates web elements by using the specified jQuery selector string
     */
    public static By jQuery(final String selector) {
        return By.javascript("return $(\"" + selector + "\").get()");
    }

    /**
     * Creates a web element locator that can be used to locate web elements using JavaScript code.
     * @param  jsCode  JavaScript code that can be evaluated into <code>HTMLElement</code> objects
     * @return a <code>By</code> object that locates web elements by executing the specified JavaScript code
     */
    public static By javascript(final String jsCode) {
        return new ByJavascript(jsCode);
    }


    /**
     * This class implements the mechanism that locates HTML elements using JavaScript code.
     * Depending on the return value of executing the specified JavaScript code,
     * it may do one of the following:
     * <UL>
     *   <LI>If the code returns <code>undefined</code>, <code>null</code>, or an empty list,
     *       it will be treated as no elements found.</LI>
     *   <LI>If the code returns a single HTML element,
     *       it will be treated as one element found.</LI>
     *   <LI>If the code returns a non-empty array and every object in the array is an HTML element,
     *       it will be treated as one or more HTML elements found.</LI>
     *   <LI>If the code returns an object that is not an HTML element
     *       (e.g., a non-HTML DOM element, a text string, a number, etc.),
     *       or if an object in the returned array is not an HTML element,
     *       it will throw a {@link ClassCastException}.</LI>
     *   <LI>If the specified JavaScript code causes an execution error,
     *       the {@link #findElements findElements} method will forward the {@link WebDriverException}
     *       while the {@link #findElement findElement} method will convert it into a
     *       {@link NoSuchElementException} (see the note below).</LI>
     * </UL>
     *
     * <P><B>Note:</B>
     *     The {@link #findElement findElement} method of this class converts a {@link WebDriverException}
     *     into a {@link NoSuchElementException} so that a user can write simpler JavaScript code,
     *     without needing to deal with exceptions that are thrown when no HTML element is found.
     *     The down side of doing such conversion is that any JavaScript code that causes
     *     an error during execution will be treated as element-not-found by the
     *     <code>findElement</code> method. (Note that the <code>findElements</code> method
     *     does not do such conversion and does not have this issue.)
     * </P>
     */
    public static class ByJavascript extends By {

        private static final List<WebElement> EMPTY_LIST = new ArrayList<WebElement>(0);
        private final String jsCode;

        public ByJavascript(final String jsCode) {
            if (jsCode == null) {
                throw new NullPointerException("The JavaScript code cannot be null.");
            }
            this.jsCode = jsCode;
        }

        /**
         * Returns the JavaScript code that defines this locator.
         * @return the JavaScript code that defines this locator
         */
        public String getCode() {
            return this.jsCode;
        }

        @Override
        public String toString() {
            return "By.javascript: " + this.jsCode;
        }

        @Override
        public WebElement findElement(final SearchContext context) {
            Object result;
            try{
                result = getJavascriptExecutor(context).executeScript(this.jsCode);
            } catch (final WebDriverException e) {
                // Technically we should let this WebDriverException bubble up
                // because it is caused by a JavaScript execution error.
                // However, for convenience, we convert it into a NoSuchElementException.
                // See the note in the ByJavascript class API doc for details.
                throw new NoSuchElementException("Unable to locate element: {\"method\":\"javascript\", selector:\"" + this.jsCode + "\", \"error\":\"" + e.getMessage() + "\"}", e);
            }

            if (result == null) {
                throw new NoSuchElementException("Unable to locate element because the JavaScript code returned null: {\"method\":\"javascript\", \"selector\":\"" + this.jsCode + "\"}");
            }

            if (result instanceof List<?>) {
                final List<Object> resultList = (List<Object>) result;
                if (resultList.size() > 0) {
                    result = resultList.get(0);
                } else {
                    throw new NoSuchElementException("Unable to locate element because the JavaScript code returned an empty list: {\"method\":\"javascript\", \"selector\":\"" + this.jsCode + "\"}");
                }
            }

            if (result instanceof WebElement) {
                return (WebElement) result;
            } else {
                throw new ClassCastException("The JavaScript code returned a result (" + result + ") that cannot be converted to a WebElement.");
            }
        }

        @Override
        public List<WebElement> findElements(final SearchContext context) {
            Object result;
            try{
                result = getJavascriptExecutor(context).executeScript(this.jsCode);
            } catch (final WebDriverException e) {
                // Add more info to the JavaScript execution error.
                throw new WebDriverException("Unable to locate elements: {\"method\":\"javascript\", selector:\"" + this.jsCode + "\", \"error\":\"" + e.getMessage() + "\"}", e);
            }

            if (result == null) {
                return EMPTY_LIST;
            } else if (result instanceof WebElement) {
                final List<WebElement> list = new ArrayList<WebElement>(1);
                list.add((WebElement)result);
                return list;
            } else if (result instanceof List<?>) {
                // The simple type-casting (List<WebElement>) does not do type-checking
                // on each object!! Thus, let's do it ourselves instead of using
                // return (List<WebElement>) result;
                final List<Object> fromList = (List<Object>) result;
                final List<WebElement> toList = new ArrayList<WebElement>(fromList.size());
                for(final Object obj : fromList) {
                    if (obj instanceof WebElement) {
                        toList.add((WebElement)obj);
                    } else {
                        throw new ClassCastException("One of the JavaScript returned objects (" + obj + ") cannot be converted to a WebElement.");
                    }
                }
                return toList;
            } else {
                throw new ClassCastException("The JavaScript code returned a result (" + result + ") that cannot be converted to a List<WebElement>.");
            }
        }

        /**
         * Returns the {@link JavascriptExecutor} associated with the specified {@link SearchContext}.
         * A  {@link SearchContext} is typically either a {@link WebDriver} or a {@link RemoteWebElement}.
         * @throws IllegalArgumentException thrown if the specified <code>SearchContext</code>
         *     is not associated with a <code>JavascriptExecutor</code>.
         */
        private JavascriptExecutor getJavascriptExecutor(SearchContext context) {
            for(;;) {
                if (context instanceof JavascriptExecutor) {
                    return (JavascriptExecutor) context;
                }
                if (context instanceof WrapsDriver) {
                    // This context is probably a RemoteWebElement;
                    // thus, find its WebDriver and try again.
                    context = ((WrapsDriver)context).getWrappedDriver();
                } else {
                    throw new IllegalArgumentException(
                        "This SearchContext does not support running JavaScript code.");
                }
            }
        }

    }

}
