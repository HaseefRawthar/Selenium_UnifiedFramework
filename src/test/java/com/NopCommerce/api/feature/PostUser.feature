@API
Feature: Post User API, create user

  @Success
  Scenario: Verify Post User API
    Given User sends a valid Post Users request
    When A success response is received
    Then Verify response time and size
    And Verify createUser response schema
