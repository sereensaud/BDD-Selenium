Feature: User Login

  Scenario: Login with valid credentials
    Given User launches browser
    When User opens URL
    And User clicks "DLP-Login-Link"
    And User enters "youremailtest@example.com" in "DLP-Email-Field" field
    And User enters "yourpassword" in "DLP-Password-Field" field
    And User clicks "DLP-Sign-In-Button"
    Then User should be logged in successfully as "DLP-Logged-In-Text"
