@API
Feature: Put User API, update user

  @Success
  Scenario: Verify Put User API
    Given User sends a valid Put User request
    When A success response is received
    Then Verify response time and size
    And Verify createUser response schema
    And Verify the putResult using get api
