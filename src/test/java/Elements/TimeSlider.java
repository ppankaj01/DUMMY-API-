/*
 * © Copyright 2002-2015 Hewlett Packard Enterprise Development LP
 */

package Elements;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import static Helpers.Utility.log;


// IMPLEMENTATION NOTES:
//
// A time slider (either an Hour or Minute slider) typically has an HTML structure like below:
//
// <div class="ui-timepicker-div">
//   <div class="ui-widget-header ui-helper-clearfix ui-corner-all">...</div>
//   <dl>
//     <dt class="ui_tpicker_time_label">...</dt>
//     <dd class="ui_tpicker_time">...</dd>
//     <dt class="ui_tpicker_hour/minute_label">...</dt>
//     <dd class="ui_tpicker_hour/minute">
//       <div class="ui_tpicker_hour/minute_slider ui-slider ui-slider-horizontal ui-widget ui-widget-content ui-corner-all">
//         <a class="ui-slider-handle ui-state-default ui-corner-all" href="#"></a>
//       </div>
//     </dd>
//     ...
//   </dl>
//   </div>
// </div>
//

/**
 * This class is used to interact with slider web elements with the purpose of selecting a specific amount of time,
 * either in hours or minutes.
 *
 * Your selenium locator should uniquely match the following elements from the example in this class' code.
 * Element: <div class="ui_tpicker_hour/minute_slider ui-slider ui-slider-horizontal ui-widget ui-widget-content ui-corner-all">
 * Label element: <dd class="ui_tpicker_time">...</dd>
 *
 * Created by millera on 8/7/2014.
 */
public class TimeSlider extends BaseElement {

    // - - - - - attributes - - - - -
    private String sliderType;
    private By sliderLabelLocator;
    private int curMaxHourPosition = -1;
    private int curMaxMinutePosition = -1;

    // - - - - - constructor - - - - -

    /**
     * The constructor to a Slider, which requires a By locator argument for the div container that contains
     * the Slider <a> tag.
     *
     * @param locator    The Selenium {@link org.openqa.selenium.By locator} for locating this web element
     * @param sliderType A String indicating the type of the slider, either 'Hour', or 'Minute'
     */
    public TimeSlider(By locator, String sliderType) {
        super(locator);

        this.sliderType = sliderType;
        if (!sliderType.equals("Hour") && !sliderType.equals("Minute")) {
            log("An invalid slider type (" + sliderType + ") was given.  Note: Any methods called using this object " +
                    "will not cause any changes to the slider this object points to.");
        }
    }


    /**
     * The constructor to a Slider, which requires a By locator argument for the div container that contains
     * the Slider <a> tag.
     *
     * @param locator            The Selenium {@link org.openqa.selenium.By locator} for locating this web element
     * @param sliderType         A String indicating the type of the slider, either 'Hour', or 'Minute'
     * @param sliderLabelLocator The Selenium {@link org.openqa.selenium.By locator} for the time label associated with
     *                           this TimeSlider
     */
    public TimeSlider(By locator, String sliderType, By sliderLabelLocator) {
        super(locator);

        this.sliderLabelLocator = sliderLabelLocator;
        this.sliderType = sliderType;
        if (!sliderType.equals("Hour") && !sliderType.equals("Minute")) {
            log("An invalid slider type (" + sliderType + ") was given.  Note: Any methods called using this object " +
                    "will not cause any changes to the slider this object points to.");
        }
    }


    /**
     * This method determines the max position (or maximum hour or minute value) of this TimeSlider.
     */
    private void getMaxPosition() {
        int curMaxPos = 0;

        // If the TimeSlider is stubbed
        if (isStubbed()) {
            log("=== This TimeSlider is currently stubbed out.  The slider will not be adjusted. ===");
            return;
        }

        // If the TimeSlider's type is 'Hour', the current counter limit is set to 24 (for 24 hours)
        if (sliderType.equals("Hour")) {
            curMaxPos = 24;
        }
        // If the TimeSlider's type is 'Minute', the current counter limit is set to 60 (for 60 minutes)
        else if (sliderType.equals("Minute")) {
            curMaxPos = 60;
        }

        // Obtains the container containing the slider
        WebElement container = getWebElement();

        // Obtains the slider web element
        WebElement slider = container.findElement(By.tagName("a"));

        // Moves the slider to the max position by moving it incrementally to the right
        for (int i = 0; i < curMaxPos; i++) {
            slider.sendKeys(Keys.ARROW_RIGHT);
        }

        Text sliderLabel = new Text(sliderLabelLocator);
//        String curTime = sliderLabel.getText();
        String curTime = null;
        if (curTime == null) {
            String sliderLabelLocatorStr = sliderLabelLocator.toString();
            sliderLabelLocatorStr = sliderLabelLocatorStr.substring(12) + " > .ui_tpicker_time_input";
            sliderLabelLocator = By.cssSelector(sliderLabelLocatorStr);
            sliderLabel = new Text(sliderLabelLocator);
            curTime = sliderLabel.getText();
        }

        // If the TimeSlider's type is 'Hour', the current hour is obtained from the current time
        if (curTime != null) {
            if (sliderType.equals("Hour")) {
                String hour = curTime.split(":")[0];

                if (hour.charAt(0) == '0') {
                    hour = hour.substring(1);
                }

                curMaxHourPosition = Integer.parseInt(hour);

                // Resets the slider by moving it incrementally to the left
                for (int i = 0; i < curMaxHourPosition; i++) {
                    slider.sendKeys(Keys.ARROW_LEFT);
                }
            }
            // If the TimeSlider's type is 'Minute', the current minute is obtained from the current time
            else if (sliderType.equals("Minute")) {
                String minute = curTime.split(":")[1];

                if (minute.charAt(0) == '0') {
                    minute = minute.substring(1);
                }

                curMaxMinutePosition = Integer.parseInt(minute);

                // Resets the slider by moving it incrementally to the left
                for (int i = 0; i < curMaxMinutePosition; i++) {
                    slider.sendKeys(Keys.ARROW_LEFT);
                }
            }
        }
        else {
            log("WARNING :Current time in date picker is null");
        }

    }


    /**
     * This method returns the max hour position of the time slider.
     *
     * @return The max hour position of the slider as an integer
     */
    public int getCurMaxHourPosition() {
        // If the max hour position isn't set
        if (curMaxHourPosition == -1) {
            getMaxPosition();
        }

        return curMaxHourPosition;
    }


    /**
     * This method returns the max minute position of the time slider.
     *
     * @return The max minute position of the slider as an integer
     */
    public int getCurMaxMinutePosition() {
        // If the max minute position isn't set
        if (curMaxMinutePosition == -1) {
            getMaxPosition();
        }

        return curMaxMinutePosition;
    }


    /**
     * This method moves the position of the slider to the given offset, moving the slider from left to right.  The
     * specified position must be a positive number that is less than either 24 or 60, depending
     * on if an Hour or Minute TimeSlider, respectively, is calling this method.
     *
     * @param position The position that the slider will move to, an offset from the slider's left-most position
     *
     */
    public void slideTo(int position) {
        // If the TimeSlider is stubbed
        if (isStubbed()) {
            log("=== This TimeSlider is currently stubbed out.  The slider will not be adjusted. ===");
            return;
        }

        // If the TimeSlider's type is 'Hour'
        if (sliderType.equals("Hour")) {
            // If the max hour position isn't set
            if (curMaxHourPosition == -1) {
                getMaxPosition();
            }

            // If the specified position is greater than max hour poisition
            if (position > curMaxHourPosition) {
                log("A position greater than or equal to the max hour position (" + curMaxHourPosition + ") was given " +
                        "for the Hour TimeSlider.  The slider will not be adjusted.");
                return;
            }
            // If the specified position is less than 0
            if (position < 0) {
                log("A negative position was given for the Hour TimeSlider.  The slider will not be adjusted.");
                return;
            }
        }
        // If the TimeSlider's type is 'Minute'
        else if (sliderType.equals("Minute")) {
            // If the max minute position isn't set
            if (curMaxMinutePosition == -1) {
                getMaxPosition();
            }

            // If the specified position is greater than max minute position
            if (position > curMaxMinutePosition) {
                log("A position greater than or equal to the max minute position (" + curMaxMinutePosition + ") was " +
                        "given for the Minute TimeSlider. The slider will not be adjusted.");
                return;
            }
            // If the specified position is less than 0
            if (position < 0) {
                log("A negative position was given for the Minute TimeSlider. The slider will not be adjusted.");
                return;
            }
        }
        // If the TimerSlider's type is neither 'Hour' nor 'Minute'
        else if (!sliderType.equals("Hour") && !sliderType.equals("Minute")) {
            log("An invalid slider type (" + sliderType + ") was previously given.  The slider will not be adjusted.");
            return;
        }

        // Obtains the container containing the slider
        WebElement container = getWebElement();

        // Obtains the slider web element
        WebElement slider = container.findElement(By.tagName("a"));

        int curPos = getPosition();
        int diff = position - curPos;

        if (diff < 0) {
            // Moves the slider to the specified offset by moving it incrementally to the left
            for (int i = diff; i < 0; i++) {
                slider.sendKeys(Keys.ARROW_LEFT);
            }
        } else if (diff > 0) {
            // Moves the slider to the specified offset by moving it incrementally to the right
            for (int i = 0; i < diff; i++) {
                slider.sendKeys(Keys.ARROW_RIGHT);
            }
        }
    }


    /**
     * This method returns the current position of the slider.
     *
     * @return An integer indicating the slider's offset from its left-most position
     */
    public int getPosition() {
        int returnValue = -1;
        int counter = 0, maxPos = 0;
        String[] substrs = {};
        String sliderPos = "";
        int curPosition = 0;
        // If the TimeSlider is stubbed
        if (isStubbed()) {
            log("=== This TimeSlider is currently stubbed out.  Returning an integer offset of '-1'. ===");
            return returnValue;
        }

        // If the TimerSlider's type is neither 'Hour' nor 'Minute'
        if (!sliderType.equals("Hour") && !sliderType.equals("Minute")) {
            log("An invalid slider type (" + sliderType + ") was previously given.  The slider will not be adjusted " +
                    "and '-1' will be returned.");
            return returnValue;
        }

        // Obtains the container containing the slider
        WebElement container = getWebElement();

        // Obtains the slider web element
        WebElement slider = container.findElement(By.tagName("a"));

        // If the TimeSlider's type is 'Hour', the counter limit is set to the max hour position
        if (sliderType.equals("Hour")) {
            // If the max hour position isn't set
            if (curMaxHourPosition == -1) {
                getMaxPosition();
            }

            maxPos = curMaxHourPosition;
        }
        // If the TimeSlider's type is 'Minute', the counter limit is set to the max minute position
        else if (sliderType.equals("Minute")) {
            // If the max minute position isn't set
            if (curMaxMinutePosition == -1) {
                getMaxPosition();
            }

            maxPos = curMaxMinutePosition;
        }

        // Obtains the percentage of how far the along the slider the slider icon has moved (from left to right)
        substrs = slider.getAttribute("style").split("[ ;]");
        sliderPos = substrs[1].substring(0, substrs[1].length() - 1);
        if (0.0 == Double.parseDouble(sliderPos)) {
            Text sliderLabel = new Text(sliderLabelLocator);
            String curTime = null;
            String sliderLabelLocatorStr = new String(sliderLabelLocator.toString());
            sliderLabel = new Text(sliderLabelLocator);
            curTime = sliderLabel.getText();
            if (curTime != null) {
                double convertSlider = 0;
                if (sliderType.equals("Hour")) {
                    String hour = curTime.split(":")[0];

                    if (hour.charAt(0) == '0') {
                        hour = hour.substring(1);
                    }
                    curPosition = Integer.parseInt(hour);
                    convertSlider = (curPosition * 100) / maxPos;
                }
                // If the TimeSlider's type is 'Minute', the current minute is obtained from the current time
                else if (sliderType.equals("Minute")) {
                    String minute = curTime.split(":")[1];

                    if (minute.charAt(0) == '0') {
                        minute = minute.substring(1);
                    }
                    curPosition = Integer.parseInt(minute);
                    convertSlider = (curPosition * 100) / maxPos;
                }
                sliderPos = "" + convertSlider;
            } else {
                log("WARNING :Current time in date picker is null");
            }
        }
        // Obtains the double value of the slider position/percentage
        double percentage = Double.parseDouble(sliderPos) / 100;

        // Determines the position by multiplying the percentage by the maximum possible position
        returnValue = (int) Math.round(percentage * maxPos);

        // If the position is equal to the max value of the slider, decrement the position
        /*if(returnValue == maxPos) {
            returnValue--;
        }*/

        return returnValue;
    }


    /**
     * This method resets the position of the slider to zero.
     */
    public void reset() {
        int curPos = 0;
        int counter = 0, maxPos = 0;
        String[] substrs = {};
        String sliderPos = "";

        // If the TimeSlider is stubbed
        if (isStubbed()) {
            log("=== This TimeSlider is currently stubbed out.  The slider will not be adjusted. ===");
            return;
        }

        // If the TimerSlider's type is neither 'Hour' nor 'Minute'
        if (!sliderType.equals("Hour") && !sliderType.equals("Minute")) {
            log("An invalid slider type (" + sliderType + ") was previously given.  The slider will not be adjusted.");
            return;
        }

        // Obtains the container containing the slider
        WebElement container = getWebElement();

        // Obtains the slider web element
        WebElement slider = container.findElement(By.tagName("a"));

        // If the TimeSlider's type is 'Hour', the counter limit is set to the max hour position
        if (sliderType.equals("Hour")) {
            // If the max hour position isn't set
            if (curMaxHourPosition == -1) {
                getMaxPosition();
            }

            maxPos = curMaxHourPosition;
        }
        // If the TimeSlider's type is 'Minute', the counter limit is set to the max minute position
        else if (sliderType.equals("Minute")) {
            // If the max minute position isn't set
            if (curMaxMinutePosition == -1) {
                getMaxPosition();
            }

            maxPos = curMaxMinutePosition;
        }

        // Obtains the percentage of how far the along the slider the slider icon has moved (from left to right)
        substrs = slider.getAttribute("style").split("[ ;]");
        sliderPos = substrs[1].substring(0, substrs[1].length() - 1);

        // Obtains the double value of the slider position/percentage
        double percentage = Double.parseDouble(sliderPos) / 100;

        // Determines the position by multiplying the percentage by the maximum possible position
        curPos = (int) Math.round(percentage * maxPos);

        // Resets the slider to zero by moving it incrementally to the left
        for (int i = 0; i < curPos; i++) {
            slider.sendKeys(Keys.ARROW_LEFT);
        }
    }


    /**
     * This method checks to see if the slider has been reset.
     *
     * @return A boolean value, 'true' is the slider has been reset, 'false' otherwise
     */
    public boolean isReset() {
        boolean returnValue = false;

        // If the TimeSlider is stubbed
        if (isStubbed()) {
            log("=== This TimeSlider is currently stubbed out.  Returning false. ===");
            return returnValue;
        }

        // If the TimerSlider's type is neither 'Hour' nor 'Minute'
        if (!sliderType.equals("Hour") && !sliderType.equals("Minute")) {
            log("An invalid slider type (" + sliderType + ") was previously given.  The slider will not be adjusted " +
                    "and 'false' will be returned.");
            return returnValue;
        }

        // Obtains the container containing the slider
        WebElement container = getWebElement();

        // Obtains the slider web element
        WebElement slider = container.findElement(By.tagName("a"));

        // Obtains the percentage of how far the along the slider the slider icon has moved (from left to right)
        String[] substrs = slider.getAttribute("style").split("[ ;]");
        String sliderPos = substrs[1].substring(0, substrs[1].length() - 1);

        // If the slider's position is zero
        if (sliderPos.equals("0")) {
            returnValue = true;
        }

        return returnValue;
    }
}
