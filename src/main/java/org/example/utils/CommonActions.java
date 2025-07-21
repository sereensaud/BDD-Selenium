package org.example.utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CommonActions {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;
    private final JavascriptExecutor js;

    private static final Logger logger = Logger.getLogger(CommonActions.class);
    private static final int MAX_RETRY = 3;
    private static final Duration TIMEOUT = Duration.ofSeconds(10);

    public CommonActions(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIMEOUT);
        this.actions = new Actions(driver);
        this.js = (JavascriptExecutor) driver;
    }

    private By getBy(String key) {
        return LocatorHelper.getLocator(key);
    }

    private WebElement waitForElement(String key) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(getBy(key)));
    }

    private WebElement waitForClickable(String key) {
        return wait.until(ExpectedConditions.elementToBeClickable(getBy(key)));
    }

    private WebElement waitForVisible(String key) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(getBy(key)));
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

    public String getTextOrValue(String key) {
        try {
            WebElement element = waitForVisible(key);
            String text = element.getText().trim();
            if (!text.isEmpty()) {
                logger.info("Got visible text from: " + key + " | Text: " + text);
                return text;
            }
            String value = element.getAttribute("value");
            logger.info("Got value attribute from: " + key + " | Value: " + value);
            return value != null ? value.trim() : "";
        } catch (Exception e) {
            logger.error("Failed to get text or value from: " + key, e);
            throw e;
        }
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

    public void switchToIFrame(By iframeLocator) {
        try {
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframeLocator));
            logger.info("Switched to iframe: " + iframeLocator);
        } catch (Exception e) {
            logger.error("Failed to switch to iframe: " + iframeLocator, e);
            throw e;
        }
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
        logger.info("Switched to default content");
    }

    public SearchContext getShadowRoot(By shadowHostLocator) {
        try {
            WebElement shadowHost = wait.until(ExpectedConditions.visibilityOfElementLocated(shadowHostLocator));
            return (SearchContext) js.executeScript("return arguments[0].shadowRoot", shadowHost);
        } catch (Exception e) {
            logger.error("Failed to access shadow root: " + shadowHostLocator, e);
            throw e;
        }
    }

    public WebElement getShadowElement(By shadowHostLocator, By shadowElementLocator) {
        try {
            SearchContext shadowRoot = getShadowRoot(shadowHostLocator);
            return shadowRoot.findElement(shadowElementLocator);
        } catch (Exception e) {
            logger.error("Failed to find element inside shadow DOM: " + shadowElementLocator, e);
            throw e;
        }
    }

    public void clickShadowElement(By shadowHostLocator, By shadowElementLocator) {
        WebElement shadowElement = getShadowElement(shadowHostLocator, shadowElementLocator);
        shadowElement.click();
        logger.info("Clicked on shadow DOM element inside host: " + shadowHostLocator);
    }

    public void sendKeysToShadowElement(By shadowHostLocator, By shadowElementLocator, String text) {
        WebElement shadowElement = getShadowElement(shadowHostLocator, shadowElementLocator);
        shadowElement.sendKeys(text);
        logger.info("Sent keys to shadow DOM element inside host: " + shadowHostLocator);
    }
}
