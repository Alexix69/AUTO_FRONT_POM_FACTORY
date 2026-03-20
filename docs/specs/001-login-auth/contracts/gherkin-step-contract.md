# Gherkin Step Contract: User Authentication (Login)

**Feature**: 001-login-auth  
**Date**: 2026-03-17  
**Purpose**: Define the contract between Gherkin steps and their implementation binding

## Feature File Contract

**File**: `features/login/login.feature`

```gherkin
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
```

## Step Binding Contract

Each Gherkin step maps to exactly one Step Definition method, which delegates to exactly one Step Library method.

### Given Steps

| Step Text | Step Definition | Delegates To | Precondition | Postcondition |
|-----------|----------------|-------------|--------------|---------------|
| `the user is on the login page` | `givenUserIsOnLoginPage()` | `loginSteps.openLoginPage()` | Browser is open | Login page is loaded, form is visible |

### When Steps

| Step Text | Step Definition | Delegates To | Precondition | Postcondition |
|-----------|----------------|-------------|--------------|---------------|
| `the user enters valid credentials` | `whenUserEntersValidCredentials()` | `loginSteps.enterCredentials("test@restaurant.com", "password123")` | Login page is loaded | Email and password fields are filled |
| `the user enters invalid credentials` | `whenUserEntersInvalidCredentials()` | `loginSteps.enterCredentials("wrong@email.com", "wrongpass")` | Login page is loaded | Email and password fields are filled |
| `the user submits the login form` | `whenUserSubmitsLoginForm()` | `loginSteps.submitLoginForm()` | Credentials are entered | Form is submitted; system processes authentication |

### Then Steps

| Step Text | Step Definition | Delegates To | Precondition | Postcondition |
|-----------|----------------|-------------|--------------|---------------|
| `the user should be redirected to the main operational view` | `thenUserShouldBeRedirected()` | `navigationSteps.shouldBeOnDashboard()` | Form submitted with valid creds | URL contains `/mesero`; dashboard visible |
| `the user should see an error message on the page` | `thenUserShouldSeeErrorMessage()` | `loginSteps.shouldSeeErrorMessage()` | Form submitted with invalid creds | Error message element visible; text = "Credenciales inválidas" |
| `the user should remain on the login page` | `thenUserShouldRemainOnLoginPage()` | `loginSteps.shouldBeOnLoginPage()` | Form submitted with invalid creds | URL contains `/login` |

## Page Object Contract

### LoginPage

| Method | Input | Output | Side Effect |
|--------|-------|--------|-------------|
| `open()` | — | — | Navigates browser to `{base.url}/login` |
| `enterEmail(String email)` | email string | — | Types into email input field |
| `enterPassword(String password)` | password string | — | Types into password input field |
| `clickLogin()` | — | — | Clicks the submit button |
| `getErrorMessage()` | — | String (error text) | None — reads DOM |
| `isErrorMessageDisplayed()` | — | boolean | None — checks element visibility |

### DashboardPage

| Method | Input | Output | Side Effect |
|--------|-------|--------|-------------|
| `isDisplayed()` | — | boolean | None — checks URL or landmark element |
| `getPageTitle()` | — | String (page heading) | None — reads DOM |

## Invariants

1. **Step Definitions MUST NOT contain logic** — they are pure delegation to Step Libraries
2. **Page Objects MUST NOT contain assertions** — they return state for Step Libraries to verify
3. **Step Libraries MUST use `@Step` annotations** — for Serenity evidence capture
4. **Each Gherkin step binds to exactly one Step Definition method** — no overloading
5. **Test data is defined in Step Definitions** — credentials are constants, not injected from external sources
