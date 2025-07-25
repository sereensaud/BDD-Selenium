package org.example.stepdefinitions;

import io.cucumber.java.en.*;
import org.example.base.BaseTest;
import org.example.utils.CommonActions;
import org.example.utils.ConfigReader;
import org.junit.Assert;

import static org.junit.Assert.assertTrue;

public class CommonSteps extends BaseTest {

    private CommonActions actions;

    @Given("User launches browser")
    public void user_launches_browser() {
        initializeBrowser();
        actions = new CommonActions(getDriver());
        logger.info("Browser launched successfully.");
    }

    @When("User opens URL")
    public void user_opens_url() {
        String url = ConfigReader.getProperty("baseUrl");
        getDriver().get(url);
        logger.info("Navigated to URL: " + url);
    }

    @And("User clicks {string}")
    public void user_clicks(String elementKey) {
        actions.click(elementKey);
        logger.info("Clicked on element: " + elementKey);
    }

    @And("User enters {string} in {string} field")
    public void user_enters_in_field(String value, String elementKey) {
        actions.clearAndSendKeys(elementKey, value);
        logger.info("Entered value: '" + value + "' into field: " + elementKey);
    }

    @Then("User verifies text {string} in {string} field")
    public void user_verifies_text_in_field(String expectedText, String elementKey) {
        String actualText = actions.getTextOrValue(elementKey);
        Assert.assertEquals("Text mismatch in field: " + elementKey, expectedText.trim(), actualText.trim());
        logger.info("Verified text: '" + expectedText + "' in field: " + elementKey);
    }

    @And("User should be logged in successfully as {string}")
    public void user_should_be_logged_in_successfully(String elementKey) {
        boolean isVisible = actions.isDisplayed(elementKey);
        logger.info("Login success element visibility for key '" + elementKey + "': " + isVisible);
        assertTrue("Login verification element not found or not visible!", isVisible);
    }
}
