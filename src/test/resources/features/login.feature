Feature: User Login

  Scenario: Login with valid credentials
    Given I launch the browser and open the login page
    When I enter email "youremailtest@example.com" and password "yourpassword"
    And I click on login button
    Then I should be logged in successfully
