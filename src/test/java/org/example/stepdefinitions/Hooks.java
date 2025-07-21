package org.example.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.log4j.Logger;
import org.example.base.BaseTest;
import org.example.driver.DriverManager;
import org.example.utils.ScreenshotUtil;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Hooks class is used to define actions that should run before and after each scenario.
 * This helps with setup and teardown tasks like logging, capturing screenshots, and closing the browser.
 */
public class Hooks extends BaseTest {

    private static final Logger logger = Logger.getLogger(Hooks.class);
    private final ScreenshotUtil screenshotUtil = new ScreenshotUtil();
    private static final String LOG_FILE_PATH = "logs/execution.log"; // path to the log file

    /**
     * This method runs before each scenario begins.
     * It logs the start of the scenario in both Log4j logs and Cucumber reports.
     */
    @Before
    public void beforeScenario(Scenario scenario) {
        logger.info("========== Starting Scenario: " + scenario.getName() + " ================");
        scenario.log("Starting Scenario: " + scenario.getName());

    }

    /**
     * This method runs after each scenario finishes.
     * It handles:
     *   - Taking a screenshot if the scenario fails
     *   - Attaching logs and screenshots to the report
     *   - Logging pass/fail status
     *   - Quitting the browser via DriverManager
     */
    @After
    public void afterScenario(Scenario scenario) {
        WebDriver driver = getDriver();

        if (scenario.isFailed()) {
            // Take screenshot for failed scenario and attach to the report
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Failure Screenshot");

            // Save screenshot locally in case you want to check manually
            screenshotUtil.takeScreenshot(driver, scenario.getName().replaceAll(" ", "_"));

            // Attach execution logs if log file is found
            File logFile = new File(LOG_FILE_PATH);
            if (logFile.exists()) {
                try {
                    byte[] logBytes = Files.readAllBytes(logFile.toPath());
                    scenario.attach(logBytes, "text/plain", "Execution Logs");
                    scenario.log("Execution log file attached.");
                } catch (IOException e) {
                    logger.error("Failed to attach log file", e);
                }
            } else {
                scenario.log("Log file not found at: " + LOG_FILE_PATH);
            }

            scenario.log("Scenario failed.");
        } else {
            scenario.log("Scenario passed.");
        }

        // Quit and clean up the browser
        DriverManager.quitDriver();
        logger.info("=== Finished Scenario: " + scenario.getName() + " ===");
        scenario.log("Finished Scenario: " + scenario.getName());
    }
}
