package org.example.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;

import java.io.FileReader;

public class LocatorHelper {
    private static final String JSON_FILE = "src/test/resources/locators/DOMElements.json";

    public static By getLocator(String logicalName) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject allLocators = (JSONObject) parser.parse(new FileReader(JSON_FILE));

            JSONObject locatorObj = (JSONObject) allLocators.get(logicalName);
            if (locatorObj == null) {
                throw new RuntimeException("Locator not found for: " + logicalName);
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
            throw new RuntimeException("Error reading locator for: " + logicalName, e);
        }
    }
}
