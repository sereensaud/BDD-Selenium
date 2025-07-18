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

public class Hooks extends BaseTest {

    private static final Logger logger = Logger.getLogger(Hooks.class);
    private final ScreenshotUtil screenshotUtil = new ScreenshotUtil();
    private static final String LOG_FILE_PATH = "logs/execution.log"; // change this if your log path differs

    @Before
    public void beforeScenario(Scenario scenario) {
        logger.info("=== Starting Scenario: " + scenario.getName() + " ===");
        scenario.log("Starting Scenario: " + scenario.getName());
    }

    @After
    public void afterScenario(Scenario scenario) {
        WebDriver driver = getDriver();

        if (scenario.isFailed()) {
            // 1. Take screenshot and attach to report
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Failure Screenshot");

            // 2. Save screenshot locally (optional)
            screenshotUtil.takeScreenshot(driver, scenario.getName().replaceAll(" ", "_"));

            // 3. Attach log file to report
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

        DriverManager.quitDriver();
        logger.info("=== Finished Scenario: " + scenario.getName() + " ===");
        scenario.log("Finished Scenario: " + scenario.getName());
    }
}
