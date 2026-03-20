# Quickstart: User Authentication (Login)

**Feature**: 001-login-auth  
**Date**: 2026-03-17  
**Purpose**: Get the automation project running for this feature in minimal steps

## Prerequisites

| Requirement | Version | Check Command |
|-------------|---------|---------------|
| Java (JDK) | 21 (LTS) | `java -version` |
| Gradle | 8.x (via wrapper) | `./gradlew --version` |
| Chrome browser | Latest stable | `google-chrome --version` |
| FoodTech Front | Running instance | `curl http://localhost:5173/login` |
| FoodTech Backend | Running instance | `curl http://localhost:8080/api/auth/login` |

## Application Under Test Setup

The FoodTech Front application must be running before executing tests.

```bash
# Terminal 1: Start FoodTech Backend (required for real login)
cd /path/to/foodtech-backend
./gradlew bootRun  # or equivalent

# Terminal 2: Start FoodTech Frontend
cd /path/to/FoodTech-Front
npm install
npm run dev
# App available at http://localhost:5173
```

**Test Data**: Ensure the backend has the following test user provisioned:
- Email: `test@restaurant.com`
- Password: `password123`

## Run Tests

```bash
# Navigate to the automation project
cd AUTO_FRONT_POM_FACTORY

# Run all tests
./gradlew clean test

# Generate Serenity reports
./gradlew aggregate

# Run tests + reports in one command
./gradlew clean test aggregate
```

## View Reports

After test execution, Serenity reports are available at:

```
AUTO_FRONT_POM_FACTORY/target/site/serenity/index.html
```

Open in a browser to see:
- Scenario results (pass/fail)
- Step-by-step execution with screenshots
- Test evidence per @Step method

## Configuration

### Base URL

The application URL is configured in `serenity.conf`:

```
webdriver.base.url = http://localhost:5173
```

To override at runtime:

```bash
./gradlew clean test -Dwebdriver.base.url=http://your-host:5173
```

### Browser

Default: Chrome (headless in CI, headed locally).

To run headed locally:

```bash
./gradlew clean test -Dheadless.mode=false
```

## Project Structure (Key Files)

```
AUTO_FRONT_POM_FACTORY/
├── build.gradle                                    # Dependencies + plugins
├── serenity.conf                                   # Base URL, browser, timeouts
├── features/login/login.feature                    # Gherkin scenarios
└── src/test/java/com/foodtech/automation/
    ├── pages/LoginPage.java                        # Page Object (locators + behavior)
    ├── pages/DashboardPage.java                    # Page Object (dashboard verification)
    ├── steps/LoginSteps.java                       # Step Library (orchestration + assertions)
    ├── steps/NavigationSteps.java                  # Step Library (navigation verification)
    ├── stepdefinitions/LoginStepDefinitions.java   # Cucumber glue
    └── runners/LoginTestRunner.java                # Test runner
```

## Troubleshooting

| Problem | Cause | Solution |
|---------|-------|---------|
| `Connection refused` on test run | FoodTech app not running | Start both frontend and backend |
| `NoSuchElementException` | Element not found / timing | Check `serenity.conf` timeout settings |
| `WebDriverException` | Chrome driver mismatch | Let Serenity manage drivers (default) |
| Scenario 1 fails with 401 | Test user not provisioned | Create user `test@restaurant.com` in backend |
| Reports not generated | Missing `aggregate` task | Run `./gradlew aggregate` after `test` |
