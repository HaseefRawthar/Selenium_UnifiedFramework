@Web @Login
Feature: Verify Login Feature

  @Success
  Scenario: Verify Login with invalid credentials
    Given User navigates to login screen
    When User tries to login with invalid credentials
    Then Verify the error message

  @Success
  Scenario: Verify Login with valid credentials
    Given User navigates to login screen
    When User tries to login with valid credentials
    Then Verify the text from dashboard
