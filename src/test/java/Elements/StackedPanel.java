/*
 * © Copyright 2002-2016 Hewlett Packard Enterprise Development LP
 */

package Elements;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.pagefactory.ByChained;

import static Elements.Globals.ELEMENTTIMEOUT;


/**
 * Created by pacet on 12/14/2015.
 */
public class StackedPanel extends BaseElement {

    private BaseElement additionalSettings;

    public StackedPanel(By locator) {
        super(locator);
        additionalSettings = new BaseElement(new ByChained(locator,
                By.xpath("//div[@id[contains(., 'toggle-content')]]")));
    }

    public String getTitle() {
        WebElement el = this.getWebElement();
        if (el != null) {
            WebElement title = el.findElement(By.cssSelector("> label:nth-child(1)"));
            return title.getText();
        }
        else {
            return null;
        }
    }

    public void setAdditionalSettingsElement(By locator) {
        additionalSettings = new BaseElement(locator);
    }

    @Override
    public boolean isDisplayed() {
        return isDisplayed(ELEMENTTIMEOUT);
    }

    public boolean isDisplayed(int timeout) {
        return isDisplayed(this, timeout);
    }

    public boolean isAdditionalSettingsDisplayed() {
        return isAdditionalSettingsDisplayed(ELEMENTTIMEOUT);
    }

    public boolean isAdditionalSettingsDisplayed(int timeout) {
        return isDisplayed(additionalSettings, timeout);
    }

    public static boolean isDisplayed(BaseElement element, int timeout) {
        try {
            WebElement webElement = element.getWebElement(timeout);
            if (webElement != null) {
                return webElement.isDisplayed();
            } else {
                return false;
            }
        } catch (NoSuchElementException ex) {
            // BaseElement throws NoSuchElementException if element is not visible when timeout
            return false;
        }
    }
}
