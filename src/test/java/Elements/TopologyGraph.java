/*
 * © Copyright 2002-2015 Hewlett Packard Enterprise Development LP
 */

package Elements;



import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import static Helpers.Utility.warning;

/**
 * This class is used to interact with the TopologyGraphs that appear in remote copy config panels
 *
 * Created by James Li on 9/4/2015.
 */
public class TopologyGraph extends BaseElement {

    // - - - - - Class Attributes - - - - -
    protected final int MAXRETRIES = 5;

    // - - - - - Constructor - - - - -

    /**
     * Constructor used to reference this class.
     *
     * @param locator the By object used to uniquely identify this web element
     */
    public TopologyGraph(By locator) {
        super(locator);
    }

    //  - - - - - Class Methods - - - - -
    /**
     * This method returns a list graph boxes that are a part of this topology graph.
     *
     * @return  A list of graph nodes in WebElement form
     */
    protected List<WebElement> getGraphNodes() {
        List<WebElement> nodes = getWebElement().findElements(By.className("hp-graph-system"));
        // Removing first node because it's an extra "unknown" node that doesn't actually exist in the graph
        nodes.remove(0);
        return nodes;
    }

    /**
     * This method returns the number of nodes in the topology graph.
     *
     * @return  An integer representing the number of nodes in the topology graph
     */
    public int getNodeCount() {
        return getGraphNodes().size();
    }

    /**
     * This method returns the number of nodes that have system names in the topology graph.
     *
     * @return  An integer representing the number of system names in the topology graph
     */
    public ArrayList<String> getNodeSystemNames() {
        ArrayList<String> returnValue = new ArrayList<String>();

        boolean done = false;
        int retries = 0;

        while(!done) {
            try {
                List<WebElement> nodes = getGraphNodes();

                for(WebElement n : nodes) {
                    // Check that a link exists for the name
                    if(n.findElement(By.className("hp-contents")).findElement(By.className("hp-summary")).findElement(By.id("topology-graph-system-name")).findElement(By.className("hp-name")).findElement(By.tagName("a")) != null) {
                        returnValue.add(n.findElement(By.className("hp-contents")).findElement(By.className("hp-summary")).findElement(By.id("topology-graph-system-name")).findElement(By.className("hp-name")).findElement(By.tagName("a")).getText());
                    }
                }

                done = true;
            }
            catch(StaleElementReferenceException e) {
                retries ++;
                if(retries == MAXRETRIES) {
                    done = true;

                    warning("Error! StaleElementReference Exception - " + e.getMessage());
                    e.printStackTrace();
                    throw e;
                }
            }
        }

        return returnValue;
    }
    /**
     * This method returns the number of nodes that have system ports in the topology graph.
     *
     * @return  An integer representing the number of system ports in the topology graph
     */
    public ArrayList<String> getNodeSystemPorts() {
        ArrayList<String> returnValue = new ArrayList<String>();

        boolean done = false;
        int retries = 0;

        while(!done) {
            try {
                List<WebElement> nodes = getGraphNodes();

                for(WebElement n : nodes) {
                    // Check that a link exists for the port
                    if(n.findElement(By.className("hp-contents")).findElement(By.className("hp-summary")).findElement(By.id("topology-graph-system-conn")).findElement(By.className("hp-name")).findElement(By.tagName("a")) != null) {
                        returnValue.add(n.findElement(By.className("hp-contents")).findElement(By.className("hp-summary")).findElement(By.id("topology-graph-system-conn")).findElement(By.className("hp-name")).findElement(By.tagName("a")).getText());
                    }
                }

                done = true;
            }
            catch(StaleElementReferenceException e) {
                retries ++;
                if(retries == MAXRETRIES) {
                    done = true;

                    warning("Error! StaleElementReference Exception - " + e.getMessage());
                    e.printStackTrace();
                    throw e;
                }
            }
        }

        return returnValue;
    }

    /**
     * This method returns the number of nodes that have system port statuses in the topology graph.
     *
     * @return  An integer representing the number of system port statuses in the topology graph
     */
    public ArrayList<String> getNodeSystemPortStatuses() {
        ArrayList<String> returnValue = new ArrayList<String>();

        boolean done = false;
        int retries = 0;

        while(!done) {
            try {
                List<WebElement> nodes = getGraphNodes();

                for(WebElement n : nodes) {
                    returnValue.add(n.findElement(By.className("hp-contents")).findElement(By.className("hp-summary")).findElement(By.id("topology-graph-system-conn")).findElement(By.className("hp-status")).getAttribute("class"));
                }

                done = true;
            }
            catch(StaleElementReferenceException e) {
                retries ++;
                if(retries == MAXRETRIES) {
                    done = true;

                    warning("Error! StaleElementReference Exception - " + e.getMessage());
                    e.printStackTrace();
                    throw e;
                }
            }
        }

        return returnValue;
    }

}
