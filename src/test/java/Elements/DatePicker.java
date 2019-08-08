/*
 * © Copyright 2002-2015 Hewlett Packard Enterprise Development LP
 */

package Elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static Helpers.Utility.log;


// IMPLEMENTATION NOTES:
//
// A date picker typically has an HTML structure like below:
//
// <div id="example-datepicker-id" class="ui-datepicker">
//   <div class="ui-datepicker-header">...</div
//   <table class="ui-dateipcker-calendar">
//     ...
//   </table>
// </div>

/**
 * This class is used to interact with calendar on the web page that allow the users to choose a specific date.
 *
 * Your selenium locator should uniquely match the following element from the example in this class' code.
 * Element: <div id="example-datepicker-id" class="ui-datepicker">
 *
 * Created by millera on 8/7/2014.
 */
public class DatePicker extends BaseElement {

    // - - - - - attributes - - - - -

    // - - - - - constructor - - - - -

    /**
     * The constructor to a DatePicker, which requires a By locator argument for the div container that contains
     * the DatePicker header and calendar.
     *
     * @param locator  The Selenium {@link org.openqa.selenium.By locator} for locating this web element
     */
    public DatePicker(By locator) { super(locator); }


    /**
     * This method obtains the selected dat in String form.  If a day is selected, the day, month and year are returned
     * in the following format: 'Month DD, YYYY'.  If a day is not selected, the month and year are returned in the following
     * format: 'Month YYYY'.
     *
     * @return  A String representation of the selected date
     */
    public String getSelectedDate() {
        String returnValue = "VALUE";
        int selectedDayNumber = -1;
        int flag=0;

        if(isStubbed()) {
            log("=== This DatePicker is currently stubbed out.  Returning a String whose value is 'VALUE'. ===");
            return returnValue;
        }

        // Obtains the container holding the DatePicker
        WebElement container = getWebElement();

        // Obtains all of the weeks within the calendar for the current month
        List<WebElement> calendarWeeks = container.findElements(By.cssSelector("table > tbody > tr"));

        // For each week in calendarWeeks
        for(WebElement week : calendarWeeks) {
            // Obtains the days of the current week
            List<WebElement> daysOfWeek = week.findElements(By.cssSelector("td"));

            // For each day in daysOfWeek
            for(WebElement day : daysOfWeek) {
                // If the current day is part of the current month and is selected
                if(!day.getAttribute("class").contains("ui-datepicker-unselectable") &&
                        day.getAttribute("class").contains("ui-datepicker-current-day")) {
                    // Obtains the numerical value of the selected day
                    selectedDayNumber = Integer.parseInt(day.findElement(By.className("ui-state-default")).getAttribute("textContent"));
                    flag=1;
                    break;
                }
                if(flag==1)
                    break;
            }

            // If the numerical value of the selected day has been found
            if(selectedDayNumber != -1) {
                break;
            }
        }

        // Obtains the title of the DatePicker
        WebElement title = container.findElement(By.cssSelector(".ui-datepicker-header > .ui-datepicker-title"));

        // Obtains the name of the current month
        String month = title.findElement(By.cssSelector(".ui-datepicker-month")).getAttribute("textContent");
        // Obtains the current year
        String year = title.findElement(By.cssSelector(".ui-datepicker-year")).getAttribute("textContent");

        // If the numerical value of the selected day has been found
        if(selectedDayNumber != -1) {
            returnValue = month + " " + selectedDayNumber + ", " + year;
        } else {
            returnValue = month + " " + year;
        }

        return returnValue;
    }

    /**
     * This method obtains the integer value of the year shown in the calender
     *
     * @return An integer representing the year shown in the calender
     */
    public int getYear(){
        // Obtains the container holding the DatePicker
        WebElement container = getWebElement();

        // Obtains the current year
        String year = container.findElement(By.cssSelector(".ui-datepicker-header > .ui-datepicker-title > .ui-datepicker-year"))
                .getAttribute("textContent");
        return Integer.parseInt(year);
    }

    /**
     * This method obtains the String value of the month shown in the calender
     *
     * @return  A string representing the month shown in the calender
     */
    public String getMonth(){
        // Obtains the container holding the DatePicker
        WebElement container = getWebElement();

        //  Obtains the month of the DatePicker
        String month = container.findElement(By.cssSelector(".ui-datepicker-header > .ui-datepicker-title > .ui-datepicker-month"))
                .getAttribute("textContent");
        return month;
    }


    /**
     * This method obtains the numerical value of the last day of the current month.
     *
     * @return  An integer whose value is that of the numerical value of the last day of the current month
     */
    public int getLastDay() {
        int returnValue = 0;
        int flag=0;
        // If the DatePicker is stubbed
        if(isStubbed()) {
            log("=== This DatePicker is currently stubbed out.  Returning an integer value of 0. ===");
        } else {
            // Obtains the container holding the DatePicker
            WebElement container = getWebElement();

            // Obtains all of the weeks within the calendar for the current month
            List<WebElement> calendarWeeks = container.findElements(By.cssSelector("table > tbody > tr"));

            // Obtains the last week of the current month
            WebElement lastWeek = calendarWeeks.get(calendarWeeks.size() - 1);

            // Obtains the days of the last week
            List<WebElement> lastWeekDays = lastWeek.findElements(By.cssSelector("td"));

            // Obtains the last day of the last week
            WebElement lastDay = lastWeekDays.get(lastWeekDays.size() - 1);

            // If the last day of the last week is a part of the current month
            if (!lastDay.getAttribute("class").contains("ui-datepicker-other-month")) {
                // Obtains the numerical value of the last day of the last week
                returnValue = Integer.parseInt(lastDay.findElement(By.className("ui-state-default")).getAttribute("textContent"));
            } else {
                // For each day in the last week of the current month
                for(int i = lastWeekDays.size() - 1; i >= 0; i--) {
                //for (int i = 1; i < lastWeekDays.size(); i++) {
                    WebElement day = lastWeekDays.get(i);

                    // If the current day is not selectable, then the previous day is the last day of the month
                    if (!day.getAttribute("class").contains("ui-datepicker-other-month")) {
                        returnValue = Integer.parseInt(lastWeekDays.get(i).findElement(By.className("ui-state-default")).getAttribute("textContent"));
                        flag=1;
                        break;
                    }
                    if(flag==1)
                        break;
                }
            }
        }

        return returnValue;
    }


    /**
     * This method changes the current month to the previous month by clicking on the previous month link.
     */
    public void prevMonth() {
        // If the DatePicker is stubbed
        if(isStubbed()) {
            log("=== This DatePicker is currently stubbed out.  The month will not change. ===");
        } else {
            // Obtains the container holding the DatePicker
            WebElement container = getWebElement();

            // Obtains the previous month link in the DatePicker header
            WebElement prevMonthLink = container.findElement(By.cssSelector(".ui-datepicker-header > .ui-datepicker-prev"));

            // Clicks on the previous month link
            prevMonthLink.click();
        }
    }


    /**
     * This method changes the current month to the next month by clicking on the next month link.
     */
    public void nextMonth() {
        // If the DatePicker is stubbed
        if(isStubbed()) {
            log("=== This DatePicker is currently stubbed out.  The month will not change. ===");
        } else {
            // Obtains the container holding the DatePicker
            WebElement container = getWebElement();

            // Obtains the next month link in the DatePicker header
            WebElement nextMonthLink = container.findElement(By.cssSelector(".ui-datepicker-header > .ui-datepicker-next"));

            // Clicks on the next month link
            nextMonthLink.click();
        }
    }


    /**
     * This method selects the specified date on the DatePicker calendar.
     *
     * @param dayNumber  The numberical value of the day to be selected
     */
    public void pickDay(int dayNumber) {
        boolean lastSelectableWeek = false;
        int lastDayOfMonth = getLastDay();
        int curDayIndex = -1, lastDayIndex = -1, curDayNumber = -1;
        WebElement lastDayOfWeek = null, selectedDay = null;
        DateFormat dateFormat = new SimpleDateFormat("dd");
        Calendar calendar = Calendar.getInstance();
        int curDay = Integer.parseInt(dateFormat.format(calendar.getTime()));

        // If the DatePicker is stubbed
        if(isStubbed()) {
            log("=== This DatePicker is currently stubbed out.  The specified day will not be selected. ===");
            return;
        }

        // If the day number is a negative integer or zero
        else if(dayNumber <= 0) {
            log("ERROR!  An invalid day number was given: " + dayNumber + ".  The specified day can't be selected.");
        }

        // if the day number is greater than the last day of the month
        else if(dayNumber > lastDayOfMonth) {
            log("ERROR!  The given day number (" + dayNumber + ") is too large as there are only " + lastDayOfMonth +
                    " days in the current month.  The specified day can't be selected.");
        }

        else {
            // Obtains the container holding the DatePicker
            WebElement container = getWebElement();

            // Obtains all of the weeks within the calendar for the current month
            List<WebElement> calendarWeeks = container.findElements(By.cssSelector("table > tbody > tr"));

            // For each week in calendarWeeks
            for (WebElement week : calendarWeeks) {
                // Obtains the days of the current week
                List<WebElement> days = week.findElements(By.cssSelector("td"));

                // For each of the current week, moving from the last day to the first
                for(lastDayIndex = days.size() - 1; lastDayIndex >= 0; lastDayIndex--) {
                    // Obtains the current last day of the week
                    lastDayOfWeek = days.get(lastDayIndex);

                    // If the current last day of the week is selectable
                    if (!lastDayOfWeek.getAttribute("class").contains("ui-datepicker-unselectable")) {
                        break;
                    } else {
                        lastSelectableWeek = true;
                    }
                }

                // Gets the numerical value of the last day of the week
                curDayNumber = Integer.parseInt(lastDayOfWeek.findElement(By.className("ui-state-default")).getAttribute("textContent"));

                // For every day before the selectable last day of the week
                for(curDayIndex = lastDayIndex - 1; curDayIndex >= 0 && dayNumber < curDayNumber; curDayIndex--) {
                    // Obtains the numerical value for the current day
                    curDayNumber = Integer.parseInt(days.get(curDayIndex).findElement(By.className("ui-state-default")).getAttribute("textContent"));
                }

                // If the current day number matches that of the specified day number, a reference to current day
                if(dayNumber == curDayNumber) {
                    curDayIndex++;
                    selectedDay = days.get(curDayIndex);
                    break;
                }
                // If the current week is the last selectable week and the current day number didn't match that of
                    // the specified day number
                else if(lastSelectableWeek) {
                    break;
                }
            }

            // If the day number is greater than the current date
            if(lastSelectableWeek && dayNumber > curDay) {
                log("ERROR!  The given day number (" + dayNumber + ") is too large as only dates up to and " +
                        "including the current date (" + curDay + ") can be selected.  The specified day can't be " +
                        "selected.");
                return;
            }

            // Clicks on the specified date to select it
            selectedDay.findElement(By.className("ui-state-default")).click();
        }
    }
}
