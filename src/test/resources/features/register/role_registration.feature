Feature: User Registration with Role Selection
  As an unregistered user
  I want to select a role during registration
  So that my profile is associated with a specific role within the system

  @positiveRegister
  Scenario: TC-HU1-1.1 Role dropdown is present in the registration form
    Given the user is on the registration form
    When the user views the available fields
    Then the role selection dropdown should be visible

  @positiveRegister
  Scenario: TC-HU1-1.2 Role dropdown displays all valid role options
    Given the user is on the registration form
    When the user opens the role dropdown
    Then the dropdown should show exactly MESERO, COCINERO and BARTENDER

  @positiveRegister
  Scenario: TC-HU1-2.1 Successful registration with role MESERO
    Given a new user has completed all required registration fields
    When the user selects MESERO and submits the registration form
    Then the system creates the user associated to the role MESERO

  @positiveRegister
  Scenario: TC-HU1-2.2 Successful registration with role COCINERO
    Given a new user has completed all required registration fields
    When the user selects COCINERO and submits the registration form
    Then the system creates the user associated to the role COCINERO

  @positiveRegister
  Scenario: TC-HU1-2.3 Successful registration with role BARTENDER
    Given a new user has completed all required registration fields
    When the user selects BARTENDER and submits the registration form
    Then the system creates the user associated to the role BARTENDER

  @negativeRegister
  Scenario: TC-HU1-3.1 Registration without role is rejected with a validation message
    Given a new user has completed all required fields except role
    When the user attempts to submit the registration form
    Then the system prevents submission and displays the role validation error
