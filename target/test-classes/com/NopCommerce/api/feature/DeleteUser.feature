@API
Feature: Delete User API, delete a user

  @Success
  Scenario: Verify Delete User API
    Given User sends a valid Delete User request
    When A success response is received
    Then Verify response time and size
    And Verify createUser response schema
    And Verify the deleteResult using get api
