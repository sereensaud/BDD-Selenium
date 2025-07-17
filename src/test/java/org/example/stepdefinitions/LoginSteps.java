package org.example.stepdefinitions;

import io.cucumber.java.en.*;
import org.example.pages.LoginPage;
import org.example.base.BaseTest;

import static org.junit.Assert.assertTrue;

public class LoginSteps extends BaseTest {

    LoginPage loginPage;

    @Given("I launch the browser and open the login page")
    public void i_launch_browser_and_open_login_page() {
        logger.info("========================================= New Test Run Started ====================================");
        initializeBrowser(); // from BaseTest
        loginPage = new LoginPage(getDriver());
        loginPage.openLoginPage();
    }

    @When("I enter email {string} and password {string}")
    public void enter_email_and_password(String email, String password) {
        loginPage.enterCredentials(email, password);
    }

    @When("I click on login button")
    public void i_click_login() {
        loginPage.clickLogin();
    }

    @Then("I should be logged in successfully")
    public void i_should_be_logged_in() {
        assertTrue("Login failed!", loginPage.isLoggedIn());
//        tearDown(); // from BaseTest // no need to call now bcz now you have hooks for this for SS
    }
}
