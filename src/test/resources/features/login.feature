Feature: User Login

  Scenario: Login with valid credentials
    Given User launches browser
    When User opens URL
    And User clicks "DLP-Login-Link"
    And User enters "youremailtest@example.com" in "Email-Field" field
    And User enters "yourpassword" in "Password-Field" field
    And User clicks "Log-In-Button"
    Then User should be logged in successfully as "Logged-In-Text"

  Scenario Outline: Login with invalid password credentials
    Given User launches browser
    When User opens URL
    And User clicks "DLP-Login-Link"
    And User enters "youremailtest@example.com" in "Email-Field" field
    And User enters "wrongpassword" in "Password-Field" field
    And User clicks "Log-In-Button"
    Then User verifies text "<validateErrMsg>" in "Password-Field-Helper-Text" field

    Examples:
      | validateErrMsg |
      | Your email or password is incorrect! |