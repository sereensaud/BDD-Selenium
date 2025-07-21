Feature: User Login

  Scenario: Login with valid credentials
    Given User launches browser
    When User opens URL
    And User clicks "Common-Login-Link"
    And User enters "youremailtest@example.com" in "Login-Email-Field" field
    And User enters "yourpassword" in "Login-Password-Field" field
    And User clicks "Login-Button"
    Then User should be logged in successfully as "Logged-In-Text"

  Scenario Outline: Login with invalid password credentials
    Given User launches browser
    When User opens URL
    And User clicks "Common-Login-Link"
    And User enters "youremailtest@example.com" in "Login-Email-Field" field
    And User enters "wrongpassword" in "Login-Password-Field" field
    And User clicks "Login-Button"
    Then User verifies text "<validateErrMsg>" in "Login-Password-Field-Helper-Text" field

    Examples:
      | validateErrMsg |
      | Your email or password is incorrect! |