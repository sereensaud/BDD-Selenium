package org.example.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.log4j.Logger;
import org.example.base.BaseTest;
import org.example.utils.ScreenshotUtil;

public class Hooks extends BaseTest {

    private static final Logger logger = Logger.getLogger(Hooks.class);
    private ScreenshotUtil screenshotUtil = new ScreenshotUtil();

    @Before
    public void beforeScenario(Scenario scenario) {
        logger.info("=== Starting Scenario: " + scenario.getName() + " ===");
    }

    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            screenshotUtil.takeScreenshot(getDriver(), scenario.getName().replaceAll(" ", "_"));
        }
        tearDown(); // Close driver
        logger.info("=== Finished Scenario: " + scenario.getName() + " ===");
    }
}
