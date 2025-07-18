package org.example.base;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.example.driver.DriverManager;
import org.example.utils.ConfigReader;

import java.time.Duration;

public class BaseTest {
    protected static final Logger logger = Logger.getLogger(BaseTest.class);

    public void initializeBrowser() {
        logger.info("Loading configuration...");
        ConfigReader.loadProperties();

        String browser = ConfigReader.getProperty("browser");
//        String url = ConfigReader.getProperty("baseUrl");
        int waitTime = Integer.parseInt(ConfigReader.getProperty("implicitWait"));

        logger.info("Initializing browser: " + browser);
        WebDriver driver;

        switch (browser.toLowerCase()) {
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            default:
                throw new RuntimeException("Unsupported browser: " + browser);
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(waitTime));
//        driver.get(url);

        DriverManager.setDriver(driver);
    }

    public WebDriver getDriver() {
        return DriverManager.getDriver();
    }
}
