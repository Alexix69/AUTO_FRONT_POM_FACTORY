# AUTO_FRONT_POM_FACTORY

Frontend UI automation for FoodTech using Page Object Model + Page Factory with Serenity BDD.

## Purpose

Validate the authentication flow (successful and failed login) with clean POM architecture and Serenity reporting, ready for academic/professional evaluation.

## Stack

- Serenity BDD
- Cucumber (BDD)
- Selenium WebDriver
- Gradle
- Java 21

## Project Structure

```
src/
  test/
    java/       - steps, stepdefinitions, pages, utils, runners
    resources/  - serenity.conf, logback-test.xml, features
```

## Scenarios Covered

- Successful access with valid credentials
- Access denied with invalid credentials

## Prerequisites

- Java 21
- Chrome installed
- FoodTech frontend running at the configured base URL

## Configuration

Base URL is resolved in this order:

1) webdriver.base.url (system property)
2) WEBDRIVER_BASE_URL (env)
3) default http://localhost:5173

Backend registration URL is resolved in this order:

1) foodtech.backend.base.url (system property)
2) FOODTECH_BACKEND_BASE_URL (env)
3) default http://localhost:8080

## How to Execute

```bash
./gradlew clean test
```

Reports:

- Serenity report: target/site/serenity/index.html
- Evidence folder: evidences/<timestamp>/

## Workflow (SpecKit / SDD)

This repository follows Spec-Driven Development using SpecKit in the workspace root:

constitution -> specify -> plan -> tasks -> implement

## Notes for Evaluators

- Scenarios are independent and register a fresh user via backend before the positive login.
- Evidence is captured per scenario (final) and on failure to avoid noise.
- POM responsibilities are enforced: Page Objects are interaction-only, Steps perform validation.
