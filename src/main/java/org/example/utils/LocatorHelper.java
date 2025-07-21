package org.example.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;

import java.io.FileReader;

/**
 * LocatorHelper reads locators from a flat JSON file and returns
 * a Selenium By object based on the locator type and value.
 *
 * The JSON file contains key-value pairs where keys are unique logical names
 * and values include "type" and "value" for the locator.
 */
public class LocatorHelper {
    // Flat locator file path
    private static final String JSON_FILE = "src/test/resources/locators/DOMElements.json";

    /**
     * Returns a Selenium By locator for the given flat key (e.g. "Login-Email-Field").
     *
     * @param elementKey The flat key representing the element locator.
     * @return           A Selenium By object.
     */
    public static By getLocator(String elementKey) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject allLocators = (JSONObject) parser.parse(new FileReader(JSON_FILE));

            if (!allLocators.containsKey(elementKey)) {
                throw new RuntimeException("Locator not found for key: " + elementKey);
            }

            JSONObject locatorObj = (JSONObject) allLocators.get(elementKey);
            String type = (String) locatorObj.get("type");
            String value = (String) locatorObj.get("value");

            switch (type.toLowerCase()) {
                case "id": return By.id(value);
                case "name": return By.name(value);
                case "xpath": return By.xpath(value);
                case "css": return By.cssSelector(value);
                case "class": return By.className(value);
                case "linktext": return By.linkText(value);
                case "partiallinktext": return By.partialLinkText(value);
                case "tag": return By.tagName(value);
                default:
                    throw new RuntimeException("Unsupported locator type: " + type);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading locator for key: " + elementKey, e);
        }
    }
}
