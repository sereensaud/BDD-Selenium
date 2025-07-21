package org.example.utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CommonActions {

    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;
    private JavascriptExecutor js;

    private static final Logger logger = Logger.getLogger(CommonActions.class);
    private static final int MAX_RETRY = 3;
    private static final Duration TIMEOUT = Duration.ofSeconds(10);

    public CommonActions(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIMEOUT);
        this.actions = new Actions(driver);
        this.js = (JavascriptExecutor) driver;
    }

    private WebElement waitForElement(String key) {
        By locator = LocatorHelper.getLocator(key);
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    private WebElement waitForClickable(String key) {
        By locator = LocatorHelper.getLocator(key);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    private WebElement waitForVisible(String key) {
        By locator = LocatorHelper.getLocator(key);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement getElement(String key) {
        try {
            return waitForElement(key);
        } catch (NoSuchElementException e) {
            logger.error("Element not found: " + key, e);
            throw e;
        }
    }

    public void click(String key) {
        for (int attempt = 1; attempt <= MAX_RETRY; attempt++) {
            try {
                WebElement element = waitForClickable(key);
                element.click();
                logger.info("Clicked on element: " + key);
                return;
            } catch (StaleElementReferenceException | ElementClickInterceptedException e) {
                logger.warn("Retrying click on: " + key + " (attempt " + attempt + ")");
            } catch (Exception e) {
                logger.error("Click failed on: " + key, e);
                throw e;
            }
        }
        throw new RuntimeException("Failed to click element after retries: " + key);
    }

    public void sendKeys(String key, String value) {
        for (int attempt = 1; attempt <= MAX_RETRY; attempt++) {
            try {
                WebElement element = waitForVisible(key);
                element.sendKeys(value);
                logger.info("Sent keys to: " + key + " | Value: " + value);
                return;
            } catch (StaleElementReferenceException e) {
                logger.warn("Retrying sendKeys on: " + key + " (attempt " + attempt + ")");
            } catch (Exception e) {
                logger.error("sendKeys failed for: " + key, e);
                throw e;
            }
        }
        throw new RuntimeException("Failed to send keys after retries: " + key);
    }

    public void clearAndSendKeys(String key, String value) {
        try {
            WebElement element = waitForVisible(key);
            element.clear();
            element.sendKeys(value);
            logger.info("Cleared and sent keys to: " + key);
        } catch (Exception e) {
            logger.error("clearAndSendKeys failed for: " + key, e);
            throw e;
        }
    }

    public boolean isDisplayed(String key) {
        try {
            return waitForVisible(key).isDisplayed();
        } catch (Exception e) {
            logger.warn("Element not visible: " + key);
            return false;
        }
    }

    public void acceptAlert() {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();
            logger.info("Alert accepted.");
        } catch (Exception e) {
            logger.error("Failed to accept alert", e);
            throw e;
        }
    }

    public void dismissAlert() {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.dismiss();
            logger.info("Alert dismissed.");
        } catch (Exception e) {
            logger.error("Failed to dismiss alert", e);
            throw e;
        }
    }

    public String getAlertText() {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String text = alert.getText();
            logger.info("Alert text: " + text);
            return text;
        } catch (Exception e) {
            logger.error("Failed to get alert text", e);
            throw e;
        }
    }

    public void dragAndDrop(String sourceKey, String targetKey) {
        try {
            WebElement source = waitForVisible(sourceKey);
            WebElement target = waitForVisible(targetKey);
            actions.dragAndDrop(source, target).perform();
            logger.info("Dragged from " + sourceKey + " to " + targetKey);
        } catch (Exception e) {
            logger.error("Drag and drop failed", e);
            throw e;
        }
    }

    public void hoverOverElement(String key) {
        try {
            WebElement element = waitForVisible(key);
            actions.moveToElement(element).perform();
            logger.info("Hovered over: " + key);
        } catch (Exception e) {
            logger.error("Failed to hover: " + key, e);
            throw e;
        }
    }

    public void scrollToElement(String key) {
        try {
            WebElement element = waitForVisible(key);
            js.executeScript("arguments[0].scrollIntoView(true);", element);
            logger.info("Scrolled to: " + key);
        } catch (Exception e) {
            logger.error("Scroll failed: " + key, e);
            throw e;
        }
    }

    public void waitForVisibility(String key) {
        try {
            waitForVisible(key);
            logger.info("Waited for visibility: " + key);
        } catch (Exception e) {
            logger.error("Visibility wait failed for: " + key, e);
            throw e;
        }
    }

    public String getText(String key) {
        try {
            WebElement element = waitForVisible(key);
            String text = element.getText();
            logger.info("Got text from: " + key + " | Text: " + text);
            return text;
        } catch (Exception e) {
            logger.error("Failed to get text: " + key, e);
            throw e;
        }
    }

    // ========================== SHADOW DOM + IFRAME SUPPORT ==============================

    // Switch to iframe using locator
    public void switchToIFrame(By iframeLocator) {
        try {
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframeLocator));
            logger.info("Switched to iframe: " + iframeLocator);
        } catch (Exception e) {
            logger.error("Failed to switch to iframe: " + iframeLocator, e);
            throw e;
        }
    }

    // Switch back to default content
    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
        logger.info("Switched to default content");
    }

    // Get shadow root of a shadow host
    public SearchContext getShadowRoot(By shadowHostLocator) {
        try {
            WebElement shadowHost = wait.until(ExpectedConditions.visibilityOfElementLocated(shadowHostLocator));
            return (SearchContext) js.executeScript("return arguments[0].shadowRoot", shadowHost);
        } catch (Exception e) {
            logger.error("Failed to access shadow root: " + shadowHostLocator, e);
            throw e;
        }
    }

    // Get element inside shadow DOM
    public WebElement getShadowElement(By shadowHostLocator, By shadowElementLocator) {
        try {
            SearchContext shadowRoot = getShadowRoot(shadowHostLocator);
            return shadowRoot.findElement(shadowElementLocator);
        } catch (Exception e) {
            logger.error("Failed to find element inside shadow DOM: " + shadowElementLocator, e);
            throw e;
        }
    }

    // Click shadow DOM element
    public void clickShadowElement(By shadowHostLocator, By shadowElementLocator) {
        WebElement shadowElement = getShadowElement(shadowHostLocator, shadowElementLocator);
        shadowElement.click();
        logger.info("Clicked on shadow DOM element inside host: " + shadowHostLocator);
    }

    // Send keys to shadow DOM element
    public void sendKeysToShadowElement(By shadowHostLocator, By shadowElementLocator, String text) {
        WebElement shadowElement = getShadowElement(shadowHostLocator, shadowElementLocator);
        shadowElement.sendKeys(text);
        logger.info("Sent keys to shadow DOM element inside host: " + shadowHostLocator);
    }
}
