# Data Model: User Authentication (Login)

**Feature**: 001-login-auth  
**Date**: 2026-03-17  
**Purpose**: Define entities, fields, relationships, and state transitions for the login automation feature

## Entities

### 1. UserCredentials

Represents the authentication input provided by a user.

| Field | Type | Description | Constraints |
|-------|------|-------------|-------------|
| identifier | String | Email address or username | Non-empty; used as the login key |
| password | String | User password | Non-empty; masked in UI |

**Variants**:
- **ValidCredentials**: Known to the system → authentication succeeds
- **InvalidCredentials**: Not recognized by the system → authentication fails

**Test Data Instances**:

| Variant | identifier | password |
|---------|-----------|----------|
| Valid | `test@restaurant.com` | `password123` |
| Invalid | `wrong@email.com` | `wrongpass` |

### 2. LoginPage

Represents the login form in the FoodTech application.

| Element | Locator | Interaction |
|---------|---------|-------------|
| Email input | `[data-testid='email-input']` | Type identifier |
| Password input | `[data-testid='password-input']` | Type password |
| Submit button | `[data-testid='submit-btn']` | Click to submit |
| Error message | `[data-testid='error-message']` | Read text (conditionally rendered) |
| Login form | `[data-testid='login-form']` | Container element |

**URL**: `{base.url}/login`

### 3. DashboardPage

Represents the main operational view (waiter dashboard) — the target after successful login.

| Aspect | Detail |
|--------|--------|
| URL | `{base.url}/mesero` |
| Purpose | Landing page for authenticated users |
| Verification | URL contains `/mesero` and/or landmark element is visible |

### 4. ErrorFeedback

Represents the error state displayed on login failure.

| Aspect | Detail |
|--------|--------|
| Element | `[data-testid='error-message']` |
| Text | "Credenciales inválidas" |
| Visibility | Only rendered when authentication fails |
| Type | DOM-based (not a browser alert) |

## Relationships

```
UserCredentials ──enters──► LoginPage ──submits──► Authentication
                                                        │
                                   ┌────────────────────┤
                                   │                    │
                              (success)            (failure)
                                   │                    │
                                   ▼                    ▼
                            DashboardPage        ErrorFeedback
                            (redirect to          (displayed on
                             /mesero)              LoginPage)
```

## State Transitions

### Authentication State Machine

```
┌──────────┐    navigate to /login    ┌───────────────┐
│  Initial  │ ───────────────────────► │  Login Page   │
│  (no auth)│                          │  (form empty) │
└──────────┘                          └───────┬───────┘
                                              │
                                    enter credentials
                                    + submit form
                                              │
                                    ┌─────────▼─────────┐
                                    │   Authenticating   │
                                    │   (loading state)  │
                                    └─────────┬─────────┘
                                              │
                              ┌───────────────┼───────────────┐
                              │                               │
                         valid creds                    invalid creds
                              │                               │
                              ▼                               ▼
                    ┌─────────────────┐            ┌──────────────────┐
                    │   Authenticated  │            │   Login Failed   │
                    │  (redirect to    │            │  (error message  │
                    │   /mesero)       │            │   displayed)     │
                    └─────────────────┘            └──────────────────┘
```

### Scenario 1: Valid Credentials → Success Path

1. **State: Login Page** → User sees empty login form
2. **Transition: Enter credentials** → Email and password filled
3. **Transition: Submit** → Form submitted, loading state
4. **State: Authenticated** → URL changes to `/mesero`, dashboard visible

### Scenario 2: Invalid Credentials → Failure Path

1. **State: Login Page** → User sees empty login form
2. **Transition: Enter credentials** → Email and password filled
3. **Transition: Submit** → Form submitted, loading state
4. **State: Login Failed** → URL remains `/login`, error message "Credenciales inválidas" visible

## Validation Rules

| Rule | Source | Applies To |
|------|--------|-----------|
| Email/username must not be empty | FoodTech form validation | Out of scope (edge case) |
| Password must not be empty | FoodTech form validation | Out of scope (edge case) |
| Error message is DOM-based | FR-004 in spec | Scenario 2 assertion |
| Navigation target is `/mesero` | FR-003 in spec | Scenario 1 assertion |
| Scenarios are independent | FR-007 in spec | Both scenarios |
