@Register @Web
Feature: Verify User Registration

  @Success
  Scenario: Verify Registration with all fields
    Given User navigates to the registration page
    When User enters data in all fields
    And Click on Register button
    Then Verify registration success message
