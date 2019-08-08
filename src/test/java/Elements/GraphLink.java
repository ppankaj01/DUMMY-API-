/*
 * © Copyright 2002-2015 Hewlett Packard Enterprise Development LP
 */

package Elements;

import static Elements.Globals.browser;
import static Helpers.Utility.log;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * This class is used to interact with the TopologyGraphs that appear in remote copy config panels
 *
 * Created by James Li on 9/23/2015.
 */
public class GraphLink extends BaseElement {

	/**
	 * Constructor used to reference this class.
	 *
	 * @param locator the By object used to uniquely identify this web element.
	 */
	public GraphLink(By locator) {
		super(locator);
	}

	//  - - - - - Class Methods - - - - -
	public WebElement getDummyElement(String id) {
		return browser.findElement(By.id(id));
	}
	/**
	 * This method returns a list switch boxes that are a part of this graph link.
	 *
	 * @return  A list of graph nodes in WebElement form.
	 */
	public List<WebElement> getSwitches() {
		List<WebElement> switches = getWebElement().findElements(By.className("hp-switch"));
		return switches;
	}

	/**
	 * This method returns the switch of the specified system
	 * @param String of the system
	 *
	 * @return Switch of the system
	 */
	public WebElement getSwitch(String system) {
		List<WebElement> switches = getSwitches();
		for(WebElement s : switches) {
			if(s.findElement(By.className("hp-contents")).findElement(By.className("hp-summary")).findElement(By.className("hp-name")).getAttribute("innerText").equals(system)) {
				return s;
			}
		}
		return null;
	}

	/**
	 * This method returns all the ports of the specific switch
	 * @param String representing the system name of the switch
	 * @return All the ports of the switch
	 */
	public List<WebElement> getSwitchPorts(String system) {
		WebElement systemSwitch = getSwitch(system);
		List<WebElement> ports = systemSwitch.findElements(By.className("hp-up-port"));
		return ports;
	}

	/**
	 * This method returns the port protocol of the port
	 * @param WebElement representing the port
	 * @return String of the protocol of the port
	 */
	public String getPortProtocol(WebElement port) {
		return port.findElement(By.id("rc-port-type")).getAttribute("innerText");
	}

	/**
	 * This method returns true if the port is disabled
	 * @param WebElement representing the port
	 * @return boolean true if the port is disabled
	 */
	public boolean isPortDisabled(WebElement port) {
		if(port.findElement(By.className("hp-condensed")).findElement(By.tagName("div")).findElement(By.tagName("input")).getAttribute("disabled") == null) {
			return false;
		} else if(port.findElement(By.className("hp-condensed")).findElement(By.tagName("div")).findElement(By.tagName("input")).getAttribute("disabled").equals("disabled")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method returns true if the port is linked
	 * @param WebElement representing the port
	 * @return boolean true if the port is linked
	 */
	public boolean isPortLinked(WebElement port) {
		return port.findElement(By.id("rc-port-linked")).getAttribute("innerText").equals("Linked");
	}

	/**
	 * This method returns the port node N:S:P
	 * @param WebElement representing the port
	 * @return String of the port node N:S:P
	 */
	public String getPortNode(WebElement port) {
		return port.findElement(By.className("hp-condensed")).findElement(By.className("hp-name")).getAttribute("innerText");
	}

	/**
	 * This method returns the port address
	 * @param WebElement representing the port
	 * @return String of the port address
	 */
	public String getPortAddress(WebElement port) {
		return port.findElement(By.id("rc-port-addr")).getAttribute("innerText");
	}

	/**
	 * This method return the port speed
	 * @param WebElement representing the port
	 * @return String of the port speed
	 */
	public String getPortSpeed(WebElement port) {
		return port.findElement(By.id("rc-port-speed")).getAttribute("innerText");
	}

	/**
	 * This method returns the number of switches in the graph link.
	 *
	 * @return  An integer representing the number of switches.
	 */
	public int getSwitchCount() {
		return getSwitches().size();
	}

	/**
	 * This method returns a list of the names of the switches in the graph link.
	 *
	 * @return  A list of the switch names in Strings.
	 */
	public List<String> getSwitchNames() {
		List<String> names = new ArrayList<String>();
		int attempts = 0;
		while(attempts < 2) {
			try {
				List<WebElement> switches = getSwitches();
				for(WebElement s : switches) {
					if(attempts == 0 && s.findElement(By.className("hp-contents")).findElement(By.className("hp-summary")).findElement(By.className("hp-name")).findElement(By.tagName("a")) != null) {
						names.add(s.findElement(By.className("hp-contents")).findElement(By.className("hp-summary")).findElement(By.className("hp-name")).findElement(By.tagName("a")).getText());
					}
					if (attempts == 1 && s.findElement(By.xpath(".//div[contains(@class, 'hp-edit-contents')]/fieldset/ol/li/table/tbody/tr/td/a/span")) != null) {
						names.add(s.findElement(By.xpath(".//div[contains(@class, 'hp-edit-contents')]/fieldset/ol/li/table/tbody/tr/td/a/span")).getText());
					}

				}
				break;
			} catch(Exception e) {
			}
			attempts++;
		}
		return names;
	}

	/**
	 * This method hovers over the element that is passed to it.
	 *
	 * @param ele WebElement to hover over
	 */
	public void hoverElement(WebElement ele) {
		int attempts = 0;
		while(attempts < 2) {
			try {
				Actions action = new Actions(browser.driver);
				action.moveToElement(ele).build().perform();
				break;
			} catch(Exception e) {
			}
			attempts++;
		}
	}

	/**
	 * This method returns a list graph ports that are a part of this graph link.
	 *
	 * @return  A list of graph ports in WebElement form.
	 */
	public List<List<WebElement>> getPorts() {
		List<List<WebElement>> ports = new ArrayList<List<WebElement>>();
		int attempts = 0;
		while(attempts < 2) {
			try {
				for(WebElement s : getSwitches()) {
					ports.add(s.findElements(By.className("hp-up-port")));
				}
				break;
			} catch(Exception e) {
			}
			attempts++;
		}
		return ports;
	}

	/**
	 * This method returns true if the hp-flyout exists and is displayed.
	 *
	 * @return  A boolean value true if the flyout exists and is displayed.
	 */
	public boolean flyoutExists() {
		if(browser.findElement(By.className("hp-flyout")) != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method returns true if the port details contains all the correct details.
	 *
	 * @return  A boolean value true if all the port details are correctly displayed.
	 */
	public boolean validatePortDetails() {
		boolean result = false;
		if(flyoutExists()) {
			if(browser.findElement(By.className("hp-flyout")).findElement(By.className("hp-name")).getText() != null) {
				result = true;
			} else {
				result = false;
				log("Port ID not displayed");
			}
			if(browser.findElement(By.className("hp-flyout")).findElement(By.cssSelector("label[for=rc-port-type]")).getText().equals("Protocol")) {
				result = true && result;
			} else {
				result = false;
				log("Port type label not displayed");
			}
			if(browser.findElement(By.className("hp-flyout")).findElement(By.id("rc-port-type")).getText() != null) {
				result = true && result;
			} else {
				result = false;
				log("Port type not displayed");
			}
			if(browser.findElement(By.className("hp-flyout")).findElement(By.cssSelector("label[for=rc-port-addr]")).getText().equals("Address")) {
				result = true && result;
			} else {
				result = false;
				log("Port address label not displayed");
			}
			if(browser.findElement(By.className("hp-flyout")).findElement(By.id("rc-port-addr")).getText() != null) {
				result = true && result;
			} else {
				result = false;
				log("Port address not displayed");
			}
			if(browser.findElement(By.className("hp-flyout")).findElement(By.cssSelector("label[for=rc-port-speed]")).getText().equals("Speed")) {
				result = true && result;
			} else {
				result = false;
				log("Port speed label not displayed");
			}
			if(browser.findElement(By.className("hp-flyout")).findElement(By.id("rc-port-speed")).getText() != null) {
				result = true && result;
			} else {
				result = false;
				log("Port speed not displayed");
			}
			WebElement e = browser.findElement(By.id("ssmc-rc-ports-table"));
			if(e != null) {
				result = true && result;
			} else {
				result = false;
				log("Target table not displayed");
			}
			List<WebElement> headers = e.findElement(By.tagName("thead")).findElement(By.tagName("tr")).findElements(By.tagName("td"));
			String[] headerStrings = new String[3];
			int j = 0;
			for(WebElement h : headers) {
				headerStrings[j++] = h.getAttribute("innerText");
			}
			List<WebElement> row = e.findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElements(By.tagName("td"));
			String[] expectedHeaders = {"Remote Port (N:S:P)", "Remote Address"};
			for(int i = 0; i < expectedHeaders.length; i++) {
				if(Arrays.asList(headerStrings).contains(expectedHeaders[i])) {
					result = true && result;
				} else {
					result = false;
					log("Header '" + expectedHeaders[i] + "' doesn't exist in details table");
				}
			}
			for(WebElement r : row) {
				if(r.getText() != null) {
					result = true && result;
				} else {
					result = false;
					log("The data cell in details table doesn't have a value");
				}
			}
		}
		return result;
	}

	/**
	 * This method returns true if the port is highlighted.
	 *
	 * @return  A boolean value true if the port is highlighted.
	 */
	public boolean isPortHighlighted(WebElement e) {
		if(e.getAttribute("class").contains("hp-highlight")) {
			return true;
		} else {
			return false;
		}
	}
}