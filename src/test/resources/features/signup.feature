Feature: User Signup

Scenario Outline: Signup with existing user
  Given User launches browser
  When User opens URL
  And User clicks "Common-Login-Link"
  And User enters "<existingUser>" in "Signup-Name-Field" field
  And User enters "youremailtest@example.com" in "Signup-Email-Field" field
  And User clicks "Signup-Button"
  Then User verifies text "<validateErrMsg>" in "Signup-Email-Field-Helper-Text" field

  Examples:
  | existingUser | validateErrMsg |
  | tester | Email Address already exist! |