package org.example.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CommonActions {

    private WebDriver driver;

    public CommonActions(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getElement(String key) {
        By locator = LocatorHelper.getLocator(key);
        return driver.findElement(locator);
    }

    public void click(String key) {
        getElement(key).click();
    }

    public void sendKeys(String key, String value) {
        getElement(key).sendKeys(value);
    }

    public void clearAndSendKeys(String key, String value) {
        WebElement element = getElement(key);
        element.clear();
        element.sendKeys(value);
    }

    public boolean isDisplayed(String key) {
        try {
            return getElement(key).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
