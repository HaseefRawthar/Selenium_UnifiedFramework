@API
Feature: Get users API, get all user list

  @Success
  Scenario: Verify Get Users API
    Given User sends a valid Get Users request
    When A success response is received
    Then Verify response time and size
    And Verify getUsers response schema
