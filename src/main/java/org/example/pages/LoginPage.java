package org.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.apache.log4j.Logger;

public class LoginPage {

    WebDriver driver;
    static Logger logger = Logger.getLogger(LoginPage.class);

    // WebElements using @FindBy annotation
    @FindBy(partialLinkText = "Signup")
    WebElement loginLink;

    @FindBy(css = "input[data-qa='login-email']")
    WebElement emailField;

    @FindBy(css = "input[data-qa='login-password']")
    WebElement passwordField;

    @FindBy(css = "button[data-qa='login-button']")
    WebElement loginButton;

    @FindBy(xpath = "//a[contains(text(),'Logged in as')]")
    WebElement loggedInText;

    // Constructor with PageFactory
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Navigate and click login link
    public void openLoginPage() {
        logger.info("Navigating to homepage and clicking on Signup/Login link");
        loginLink.click();
    }

    // Fill in credentials
    public void enterCredentials(String email, String password) {
        logger.debug("Entering email: " + email);
        emailField.sendKeys(email);
        logger.debug("Entering password: password");
        passwordField.sendKeys(password);
    }

    // Click login button
    public void clickLogin() {
        logger.info("Clicking login button");
        loginButton.click();
    }

    // Check if login is successful
    public boolean isLoggedIn() {
        boolean status = loggedInText.isDisplayed();
        logger.info("Login status: " + status);
        return status;
    }
}
