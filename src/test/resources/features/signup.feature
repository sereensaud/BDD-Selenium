Feature: User Signup

Scenario Outline: Signup with existing user
  Given User launches browser
  When User opens URL
  And User clicks "DLP-Login-Link"
  And User is on the "Signup" page
  And User enters "<existingUser>" in "Name-Field" field
  And User enters "youremailtest@example.com" in "Email-Field" field
  And User clicks "Sign-In-Button"
  Then User verifies text "<validateErrMsg>" in "Email-Field-Helper-Text" field

  Examples:
  | existingUser | validateErrMsg |
  | tester | Email Address already exist! |