package org.example.stepdefinitions;

import io.cucumber.java.en.*;
import org.example.base.BaseTest;
import org.example.utils.CommonActions;
import org.example.utils.ConfigReader;
import org.junit.Assert;

import static org.junit.Assert.assertTrue;

/**
 * Step Definitions for Signup feature
 * This class initializes the browser, performs interactions defined in Signup.feature,
 * and uses CommonActions with locators loaded from JSON.
 *
 * Note: The browser is opened using BaseTest.initializeBrowser()
 * and closed automatically in Hooks.java after scenario execution.
 * So there's no need to close it here.
 */
public class SignupSteps extends BaseTest {

    private CommonActions actions;

    @Then("Signup should be successful with element {string} visible")
    public void signup_should_be_successful_with_element_visible(String elementKey) {
        boolean isVisible = actions.isDisplayed(elementKey);
        logger.info("Signup verification for element '" + elementKey + "': " + isVisible);
        assertTrue("Signup verification element not found!", isVisible);
    }
}
