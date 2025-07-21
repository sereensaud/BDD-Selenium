package org.example.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;

import java.io.FileReader;

/**
 * LocatorHelper is a utility class that reads locators from a JSON file
 * and returns a Selenium By object based on the locator type and value.
 *
 * Locators are stored in a structured JSON file, organized by page names.
 * It supports locator strategies like id, name, xpath, css, etc.
 *
 * If a locator is not found on the requested page, it falls back to look under the "Common" page section.
 * This helps avoid duplication of shared elements across multiple pages.
 */
public class LocatorHelper {
    // Path to the JSON file that contains all element locators organized by page
    private static final String JSON_FILE = "src/test/resources/locators/DOMElements.json";

    /**
     * Returns a Selenium By locator for the given logical name from the specified page.
     * Falls back to the "Common" section if the locator is not found on the specific page.
     *
     * @param pageName    The name of the page in the JSON locator file.
     * @param logicalName The logical name of the web element.
     * @return            A Selenium By object for interacting with the element.
     */
    public static By getLocator(String pageName, String logicalName) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject allLocators = (JSONObject) parser.parse(new FileReader(JSON_FILE));

            // Try to get locator from the given page
            JSONObject locatorObj = getLocatorObject(allLocators, pageName, logicalName);

            // If not found, fall back to "Common"
            if (locatorObj == null && allLocators.containsKey("Common")) {
                locatorObj = getLocatorObject(allLocators, "Common", logicalName);
            }

            if (locatorObj == null) {
                throw new RuntimeException("Locator not found for: " + logicalName + " on page: " + pageName + " or in Common locators.");
            }

            String type = (String) locatorObj.get("type");
            String value = (String) locatorObj.get("value");

            switch (type.toLowerCase()) {
                case "id":
                    return By.id(value);
                case "name":
                    return By.name(value);
                case "xpath":
                    return By.xpath(value);
                case "css":
                    return By.cssSelector(value);
                case "class":
                    return By.className(value);
                case "linktext":
                    return By.linkText(value);
                case "partiallinktext":
                    return By.partialLinkText(value);
                case "tag":
                    return By.tagName(value);
                default:
                    throw new RuntimeException("Invalid locator type: " + type);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading locator for: " + logicalName + " on page: " + pageName, e);
        }
    }

    /**
     * Helper method to fetch a locator JSON object for a given page and logical name.
     *
     * @param allLocators The full JSON containing all pages and their locators.
     * @param pageName    The page from which to fetch the locator.
     * @param logicalName The logical name of the locator.
     * @return            The JSON object of the locator or null if not found.
     */
    private static JSONObject getLocatorObject(JSONObject allLocators, String pageName, String logicalName) {
        JSONObject pageLocators = (JSONObject) allLocators.get(pageName);
        if (pageLocators != null) {
            return (JSONObject) pageLocators.get(logicalName);
        }
        return null;
    }
}
