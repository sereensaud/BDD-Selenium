package org.example.utils;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    private static final Logger logger = Logger.getLogger(ScreenshotUtil.class);

    public void takeScreenshot(WebDriver driver, String scenarioName) {
        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File destFile = new File("logs/screenshot/" + scenarioName + "_" + timestamp + ".png");
            FileUtils.copyFile(srcFile, destFile);
            logger.info("Screenshot taken: " + destFile.getAbsolutePath());
        } catch (IOException e) {
            logger.error("Failed to capture screenshot: " + e.getMessage());
        }
    }

}
