# AUTO_FRONT_POM_FACTORY

Frontend UI automation for the **FoodTech** restaurant management application using the **Page Object Model + Page Factory** pattern with **Serenity BDD**.

---

## Workshop Context

This project is the first of three automation deliverables for **Semana 5 — Maestría en automatización: del objeto al actor**. It covers the POM + Page Factory requirement, validating the FoodTech authentication flow with two independent scenarios (one positive, one negative).

---

## Application Under Test

**FoodTech Front** — React 19 SPA running at `http://localhost:5173` (configurable).

| Attribute | Value |
|---|---|
| Login URL | `{base.url}/login` |
| Post-login redirect | `/mesero` (waiter dashboard) |
| Email field locator | `data-testid="email-input"` |
| Password field locator | `data-testid="password-input"` |
| Submit button locator | `data-testid="submit-btn"` |
| Error message locator | `data-testid="error-message"` |
| Valid credentials | `test@restaurant.com` / `password123` |
| Error text on invalid login | `Credenciales inválidas` |

---

## Scenarios Covered

| # | Tag | Type | Description |
|---|---|---|---|
| 1 | `@positiveLogin` | Positive | Successful access with valid credentials → redirected to `/mesero` |
| 2 | — | Negative | Access denied with invalid credentials → error message visible, remains on login page |

Both scenarios are **fully independent**. The positive scenario provisions a fresh user via the backend API before authenticating, ensuring no dependency on pre-seeded data.

---

## Tech Stack

| Tool | Version |
|---|---|
| Java | 21 |
| Serenity BDD | 5.3.2 |
| Cucumber | 7.34.2 (JUnit Platform Engine) |
| Selenium WebDriver | managed by Serenity |
| Gradle | Wrapper (included) |
| JUnit Platform | 1.13.0 |
| Browser | Google Chrome (headless-capable) |

---

## Architecture

```
src/test/java/com/foodtech/automation/
├── pages/
│   ├── LoginPage.java          ← Page Object: @FindBy locators + interaction methods (no assertions)
│   └── DashboardPage.java      ← Page Object: URL-based presence check (no assertions)
├── steps/
│   ├── LoginSteps.java         ← Step Library: @Step-annotated orchestration methods
│   └── NavigationSteps.java    ← Step Library: URL and redirect assertions
├── stepdefinitions/
│   ├── LoginStepDefinitions.java  ← Glue: Gherkin → Step Library delegation (no logic)
│   ├── AuthHooks.java             ← @Before: provisions valid user via API for positive scenario
│   └── ScenarioHooks.java         ← @After: captures evidence on failure, cleans state
├── runners/
│   └── LoginTestRunner.java    ← @Suite (JUnit Platform) runner
└── utils/
    ├── TestConfig.java         ← Base URL and backend URL resolution
    ├── TestContext.java        ← ThreadLocal test state holder
    ├── TestDataFactory.java    ← Unique credential generation
    ├── RegisterApiClient.java  ← Backend user provisioning via REST
    ├── EnvironmentChecker.java ← Startup validation guard
    └── EvidenceManager.java    ← Screenshot capture to evidences/<timestamp>/

src/test/resources/
├── features/login/login.feature  ← Gherkin: 2 scenarios
└── serenity.conf                 ← WebDriver + screenshot configuration
```

**Separation of concerns**:
- `Page Objects` own locators (`@FindBy`) and raw browser interactions — no assertions, no business logic.
- `Step Libraries` (`@Steps`) orchestrate actions and contain assertions — never touch the DOM directly.
- `Step Definitions` are pure glue — one delegation per step, no logic.

---

## Prerequisites

- Java 21 (`java -version`)
- Google Chrome installed
- FoodTech Front running at `http://localhost:5173` (or configured URL)
- FoodTech Kitchen Services API running at `http://localhost:8080` (required for positive scenario user provisioning)

---

## Configuration

Base URL resolution order:

1. System property: `-Dwebdriver.base.url=<url>`
2. Environment variable: `WEBDRIVER_BASE_URL`
3. Default: `http://localhost:5173`

Backend URL resolution order:

1. System property: `-Dfoodtech.backend.base.url=<url>`
2. Environment variable: `FOODTECH_BACKEND_BASE_URL`
3. Default: `http://localhost:8080`

---

## How to Execute

**Run all scenarios (default):**
```bash
./gradlew clean test aggregate
```

**Run only the positive scenario:**
```bash
./gradlew clean test aggregate -Dcucumber.filter.tags="@positiveLogin"
```

**Override the application URL:**
```bash
./gradlew clean test aggregate -Dwebdriver.base.url=http://my-host:5173
```

**Chrome headless mode** is controlled in `src/test/resources/serenity.conf` — set `headless.mode = true` for CI environments.

---

## Reports

After execution:

| Report | Location |
|---|---|
| Serenity HTML report | `target/site/serenity/index.html` |
| Single-page report | `target/site/serenity/serenity-summary.html` |
| Screenshots (failure + final) | `evidences/<timestamp>/` |

Open `target/site/serenity/index.html` in any browser. No server required.

---

## Spec-Driven Development with SpecKit

This project was built using **Spec-Driven Development (SDD)** with the SpecKit workflow:

```
constitution → specify → plan → tasks → implement
```

All specification artifacts produced during development are preserved in [`docs/specs/001-login-auth/`](docs/specs/001-login-auth/):

| Artifact | Purpose |
|---|---|
| [`spec.md`](docs/specs/001-login-auth/spec.md) | Feature specification: user stories, acceptance criteria, Gherkin |
| [`plan.md`](docs/specs/001-login-auth/plan.md) | Implementation plan: architecture decisions, constitution compliance gate |
| [`tasks.md`](docs/specs/001-login-auth/tasks.md) | Atomic task breakdown with completion tracking |
| [`research.md`](docs/specs/001-login-auth/research.md) | Technical investigation: AUT locators, DOM inspection results |
| [`data-model.md`](docs/specs/001-login-auth/data-model.md) | Test data contracts |
| [`contracts/`](docs/specs/001-login-auth/contracts/) | Step-to-component mapping contracts |
| [`checklists/`](docs/specs/001-login-auth/checklists/) | Pre-implementation readiness gates |

The spec was produced by AI-assisted analysis of the FoodTech user stories (spec first, code second).

---

## Notes for Evaluators

- The positive login scenario uses `AuthHooks` to register a fresh user via the backend API before each run — no pre-seeded data dependency.
- Screenshots are captured at the end of each scenario and on failure only (`AFTER_EACH_STEP` for detailed runs, configurable).
- `data-testid` is the exclusive locator strategy, matching the FoodTech front-end contract.
- The browser is restarted for each scenario (`restart.browser.for.each = scenario`) ensuring isolation.
