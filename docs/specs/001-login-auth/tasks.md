# Tasks: User Authentication (Login)

**Input**: Design documents from `/specs/001-login-auth/`
**Prerequisites**: [plan.md](plan.md) ✅ | [spec.md](spec.md) ✅ | [research.md](research.md) ✅ | [data-model.md](data-model.md) ✅ | [contracts/gherkin-step-contract.md](contracts/gherkin-step-contract.md) ✅
**Target Repo**: `AUTO_FRONT_POM_FACTORY`
**Base**: Serenity Cucumber Starter (https://github.com/serenity-bdd/serenity-cucumber-starter) — adapted, not copied blindly
**Framework**: Serenity BDD 5.x + Cucumber 7.x + Gradle + Java 21

## Format: `[ID] [P?] [Story?] Description`

- **[P]**: Can run in parallel with other [P] tasks in the same phase (different files, no dependency)
- **[Story]**: User story this task belongs to — [US1] = Successful Login | [US2] = Failed Login
- Exact file paths included in all implementation tasks

---

## Phase 1: Project Initialization from Serenity Starter

**Purpose**: Bootstrap the AUTO_FRONT_POM_FACTORY project using the official Serenity Cucumber Starter as the base, adapt it for FoodTech POM architecture, and remove all demo/example content.

*(Constitution §5 Serenity components | §12 Spec-Driven workflow)*

- [X] T001 Initialize project by downloading Serenity Cucumber Starter content (build.gradle, gradlew, gradlew.bat, gradle/wrapper/, .gitignore, serenity.properties) into `AUTO_FRONT_POM_FACTORY/`
- [X] T002 [P] Set Java 21 source and target compatibility in `AUTO_FRONT_POM_FACTORY/build.gradle` (`sourceCompatibility = JavaVersion.VERSION_21`, `targetCompatibility = JavaVersion.VERSION_21`)
- [X] T003 [P] Remove all demo/example source files shipped with the starter (DuckDuckGo or Wikipedia test classes and page objects) from `AUTO_FRONT_POM_FACTORY/src/test/java/`
- [X] T004 [P] Remove all demo/example feature files shipped with the starter from `AUTO_FRONT_POM_FACTORY/src/test/resources/features/`
- [X] T005 Update `AUTO_FRONT_POM_FACTORY/serenity.properties` — set `serenity.project.name=FoodTech Login Automation`

**Checkpoint**: Project can compile (`./gradlew compileTestJava`) with no demo code and no errors.

---

## Phase 2: Foundational Setup (Blocking Prerequisites)

**Purpose**: Create the core project skeleton — directory structure, Serenity + Cucumber configuration, base URL configuration, and the test runner — before any user story work begins.

*(Constitution §5 required components | §6 POM rules | §11 reporting)*

**⚠️ CRITICAL**: No user story implementation can begin until this phase is complete.

- [X] T006 Create Java package directory structure under `AUTO_FRONT_POM_FACTORY/src/test/java/com/foodtech/automation/`: `pages/`, `steps/`, `stepdefinitions/`, `runners/`
- [X] T007 [P] Create `AUTO_FRONT_POM_FACTORY/src/test/resources/features/login/` directory (Gherkin feature files location — on the test classpath so Serenity resolves it via `@SelectClasspathResource("features/login")`)
- [X] T008 [P] Create `AUTO_FRONT_POM_FACTORY/src/test/resources/serenity.conf` with: `webdriver.driver = chrome`, `headless.mode = true`, default environment base URL (`webdriver.base.url = "http://localhost:5173"`), and `serenity.take.screenshots = AFTER_EACH_STEP`
- [X] T009 Create `AUTO_FRONT_POM_FACTORY/src/test/java/com/foodtech/automation/runners/LoginTestRunner.java` — `@Suite`, `@IncludeEngines("cucumber")`, `@SelectClasspathResource("features/login")`, `@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.foodtech.automation.stepdefinitions")`

**Checkpoint**: `./gradlew compileTestJava` succeeds. Runner class exists and compiles. `serenity.conf` is present.

---

## Phase 3: User Story 1 — Successful Login with Valid Credentials (Priority: P1) 🎯 MVP

**Goal**: A user with valid credentials can log in and be redirected to the main operational view (`/mesero`). The automation must confirm the redirect happens.

**Independent Test**: Run `./gradlew clean test -Dcucumber.filter.tags="@us1"` (or run the single scenario by name) — passes when the FoodTech app is running with valid test credentials provisioned.

### Implementation for User Story 1

> **Note**: For MVP purposes, test credentials are hardcoded. In a production-ready implementation, credentials should be externalized via environment variables or a test data provider.

- [X] T010 [US1] Create `AUTO_FRONT_POM_FACTORY/src/test/resources/features/login/login.feature` — add Feature header (`Feature: User Authentication`) with narrative and Scenario 1 (`Scenario: Successful login with valid credentials`) with all Given/When/Then/And steps from spec.md
- [X] T011 [P] [US1] Create `AUTO_FRONT_POM_FACTORY/src/test/java/com/foodtech/automation/pages/LoginPage.java` — extend `net.serenitybdd.core.pages.PageObject`; declare `@FindBy(css = "[data-testid='email-input']")`, `@FindBy(css = "[data-testid='password-input']")`, `@FindBy(css = "[data-testid='submit-btn']")`, `@FindBy(css = "[data-testid='error-message']")`; implement `enterEmail(String)`, `enterPassword(String)`, `clickLogin()`, `isErrorMessageDisplayed()`, `getErrorMessage()` — NO assertions
- [X] T012 [P] [US1] Create `AUTO_FRONT_POM_FACTORY/src/test/java/com/foodtech/automation/pages/DashboardPage.java` — extend `net.serenitybdd.core.pages.PageObject`; implement `isDisplayed()` (checks current URL contains `/mesero`) — NO assertions
- [X] T013 [US1] Create `AUTO_FRONT_POM_FACTORY/src/test/java/com/foodtech/automation/steps/LoginSteps.java` — annotate with `@Steps` for Serenity injection; implement `@Step("Open the login page") openLoginPage()`, `@Step("Enter credentials for {0}") enterCredentials(String email, String password)`, `@Step("Submit the login form") submitLoginForm()`; inject `LoginPage` page object
- [X] T014 [US1] Create `AUTO_FRONT_POM_FACTORY/src/test/java/com/foodtech/automation/steps/NavigationSteps.java` — implement `@Step("Verify user is on the dashboard") shouldBeOnDashboard()` using Hamcrest `assertThat` to verify current URL contains `/mesero`; inject `DashboardPage` page object
- [X] T015 [US1] Create `AUTO_FRONT_POM_FACTORY/src/test/java/com/foodtech/automation/stepdefinitions/LoginStepDefinitions.java` — define constants `VALID_EMAIL = "test@restaurant.com"`, `VALID_PASSWORD = "password123"`; inject `loginSteps` and `navigationSteps` via `@Steps`; bind Scenario 1 steps: `@Given("the user is on the login page")`, `@When("the user enters valid credentials")`, `@When("the user submits the login form")`, `@Then("the user should be redirected to the main operational view")` — pure delegation, no logic
- [ ] T016 [US1] Run Scenario 1 only against the running FoodTech app and confirm it passes: `./gradlew clean test`

**Checkpoint**: Scenario 1 (`Successful login with valid credentials`) passes end-to-end. URL changes to `/mesero` after form submission.

---

## Phase 4: User Story 2 — Failed Login with Invalid Credentials (Priority: P1)

**Goal**: A user with invalid credentials sees a visible DOM error message ("Credenciales inválidas") and remains on the login page. The automation must confirm the error message is displayed and no navigation occurs.

**Independent Test**: Run Scenario 2 by name — passes without any dependency on Scenario 1 having run first.

### Implementation for User Story 2

- [X] T017 [US2] Add Scenario 2 (`Scenario: Failed login with invalid credentials`) to `AUTO_FRONT_POM_FACTORY/src/test/resources/features/login/login.feature` with all Given/When/Then/And steps from spec.md
- [X] T018 [US2] Extend `AUTO_FRONT_POM_FACTORY/src/test/java/com/foodtech/automation/steps/LoginSteps.java` — add `@Step("Verify error message is displayed") shouldSeeErrorMessage()` using Hamcrest `assertThat` to verify `loginPage.isErrorMessageDisplayed()` is `true` and `loginPage.getErrorMessage()` equals `"Credenciales inválidas"`; add `@Step("Verify user remains on the login page") shouldBeOnLoginPage()` asserting current URL contains `/login`
- [X] T019 [US2] Add to `AUTO_FRONT_POM_FACTORY/src/test/java/com/foodtech/automation/stepdefinitions/LoginStepDefinitions.java` — define constants `INVALID_EMAIL = "wrong@email.com"`, `INVALID_PASSWORD = "wrongpass"`; bind Scenario 2 steps: `@When("the user enters invalid credentials")`, `@Then("the user should see an error message on the page")`, `@Then("the user should remain on the login page")` — pure delegation, no logic
- [ ] T020 [US2] Run Scenario 2 only against the running FoodTech app and confirm it passes: `./gradlew clean test`

**Checkpoint**: Scenario 2 (`Failed login with invalid credentials`) passes end-to-end. Error element is visible on page. URL remains `/login`.

---

## Phase 5: Polish & Cross-Cutting Concerns

**Purpose**: Validate both scenarios together, confirm Serenity reporting, apply clean code checks, and confirm full Constitution compliance.

- [ ] T021 Run both scenarios together and verify all pass: `./gradlew clean test`
- [ ] T022 [P] Generate Serenity aggregate report and verify both scenario names appear: `./gradlew aggregate` → open `AUTO_FRONT_POM_FACTORY/target/site/serenity/index.html`
- [ ] T023 [P] Confirm report shows step-level evidence (screenshots per `@Step`) for both scenarios with readable step names matching `@Step` annotation text
- [X] T024 [P] Review `LoginPage.java` and `DashboardPage.java` — confirm zero assertions, zero business logic (§6 compliance)
- [X] T025 [P] Review `LoginSteps.java` and `NavigationSteps.java` — confirm all assertions use Hamcrest `assertThat`, all methods carry `@Step` annotations with descriptive text (§10 compliance)
- [X] T026 Review `LoginStepDefinitions.java` — confirm every method body is a single delegation call, no logic, no assertions, no direct WebDriver access (§6 + §10 compliance)

**Checkpoint**: `./gradlew clean test aggregate` succeeds. Both scenarios pass. Serenity HTML report includes both scenarios with step evidence. All Constitution gates confirmed.

---

## Dependencies & Execution Order

### Phase Dependencies

```
Phase 1 (Init)
    └── Phase 2 (Foundation)  ← BLOCKS all user stories
            ├── Phase 3 (US1)  ← independently testable when done
            │       └── Phase 4 (US2)  ← builds on shared infrastructure from US1
            │               └── Phase 5 (Polish)
```

### User Story Dependencies

| Story | Depends On | Can Be Tested Independently After |
|-------|-----------|----------------------------------|
| US1 — Successful Login | Phase 2 complete | Phase 3 complete |
| US2 — Failed Login | Phase 2 complete + LoginPage + LoginSteps from Phase 3 | Phase 4 complete |
| Polish | Both US1 and US2 complete | — |

### Within Each Phase

- Tasks marked `[P]` within the same phase can be executed simultaneously (they target different files)
- T011/T012 (LoginPage, DashboardPage) can be created in parallel — independent files
- T013/T014 (LoginSteps, NavigationSteps) can be created in parallel — independent files
- T018 (extend LoginSteps) must complete before T019 (bind new steps in StepDefinitions)

### Critical Sequential Dependencies

```
T001 → T002, T003, T004, T005 (star fan-out, all safe in parallel)
T005 → T006 → T007, T008, T009 (T009 needs structure from T006)
T009 → T010 (runner must exist before feature file matters)
T010 → T011, T012 (feature file defines what pages are needed)
T011, T012 → T013, T014 (pages must exist before steps reference them)
T013, T014 → T015 (step libraries must exist before step defs reference them)
T015 → T016 (all US1 glue must exist before running the scenario)
T016 → T017 (US1 confirmed before adding US2)
T017 → T018 → T019 → T020 (US2 sequential within phase)
T020 → T021 → T022, T023, T024, T025, T026 (polish after both stories)
```

---

## Parallel Execution Examples

### Example: Phase 1 after T001

```
T001 complete
├── T002  (build.gradle Java 21 compatibility)
├── T003  (remove demo Java sources)          } all in parallel
├── T004  (remove demo feature files)
└── T005  (update serenity.properties)
```

### Example: Phase 3 core parallel window

```
T010 complete (feature file written)
├── T011  (LoginPage.java)
└── T012  (DashboardPage.java)              } in parallel
     ↓ both complete
├── T013  (LoginSteps.java)
└── T014  (NavigationSteps.java)            } in parallel
     ↓ both complete
└── T015  (LoginStepDefinitions.java — US1 bindings)
```

---

## Implementation Strategy

### MVP Scope

**Just User Story 1 (Phase 1 + Phase 2 + Phase 3)** is the MVP.
It delivers: a working authenticated login test that navigates to the dashboard and captures Serenity evidence.

### Incremental Delivery

| Increment | Phases | Deliverable |
|-----------|--------|------------|
| MVP | 1 + 2 + 3 | 1 passing scenario: Successful login → redirect to `/mesero` |
| Full slice | 1 + 2 + 3 + 4 | 2 passing scenarios: Successful + Failed login coverage |
| Production-ready | All 5 | Full Serenity report + clean code + Constitution verified |

### Serenity Starter Adaptation Notes

| Starter Component | Action |
|-------------------|--------|
| `build.gradle` | Keep structure; add Java 21 compatibility; retain `serenity-gradle-plugin`; remove unused `serenity-screenplay` dependencies |
| `gradlew` / `gradlew.bat` | Keep as-is (Gradle wrapper) |
| `gradle/wrapper/` | Keep as-is |
| `serenity.properties` | Keep; update `serenity.project.name` |
| `src/test/resources/serenity.conf` | Add fresh (starter may not have this); configure base URL and browser |
| Demo test classes | **Delete** |
| Demo feature files | **Delete** |
| `pom.xml` | **Delete** (Gradle-only project) |

---

## Task Summary

| Phase | Tasks | Parallelizable Tasks | Constitution Gates |
|-------|-------|---------------------|-------------------|
| Phase 1: Init | T001–T005 | T002, T003, T004, T005 | §12 |
| Phase 2: Foundation | T006–T009 | T007, T008 | §5, §6, §11 |
| Phase 3: US1 | T010–T016 | T011, T012, T013\*, T014\* | §2, §3, §4, §6 |
| Phase 4: US2 | T017–T020 | — | §2, §3, §4, §6 |
| Phase 5: Polish | T021–T026 | T022, T023, T024, T025, T026 | §3, §6, §10, §11 |
| **Total** | **26 tasks** | **14 parallelizable** | — |

*\*T013/T014 parallelizable relative to each other, sequential relative to T011/T012*

| User Story | Task Count | Independent Test Criteria |
|-----------|------------|--------------------------|
| US1 — Successful Login | 7 (T010–T016) | T016: Scenario 1 passes in isolation |
| US2 — Failed Login | 4 (T017–T020) | T020: Scenario 2 passes in isolation |
| Setup / Shared | 15 (T001–T009, T021–T026) | — |
