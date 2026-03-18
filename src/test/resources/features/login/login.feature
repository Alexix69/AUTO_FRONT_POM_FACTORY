Feature: User Authentication
  As a restaurant staff member
  I want to authenticate into the FoodTech system
  So that I can access the restaurant management features

  Scenario: Successful login with valid credentials
    Given the user is on the login page
    When the user enters valid credentials
    And the user submits the login form
    Then the user should be redirected to the main operational view

  Scenario: Failed login with invalid credentials
    Given the user is on the login page
    When the user enters invalid credentials
    And the user submits the login form
    Then the user should see an error message on the page
    And the user should remain on the login page
