Feature: User authentication
  As a restaurant staff member
  I want to authenticate in the FoodTech system
  So that I can access the restaurant operations view

  @positiveLogin
  Scenario: Successful access with valid credentials
    Given the user starts an authentication session
    When the user authenticates with valid credentials
    And the user submits the authentication request
    Then access to the operational view should be granted

  Scenario: Access denied with invalid credentials
    Given the user starts an authentication session
    When the user authenticates with invalid credentials
    And the user submits the authentication request
    Then an authentication error should be presented
    And access should remain on the authentication screen
