# Research: User Authentication (Login)

**Feature**: 001-login-auth  
**Date**: 2026-03-17  
**Purpose**: Resolve all technical unknowns and document design decisions before implementation

## Research Tasks

### RT-1: Serenity BDD + Cucumber + Gradle Integration (Java 21)

**Decision**: Use Serenity BDD 4.x with Cucumber 7.x on Gradle, targeting Java 21 LTS.

**Rationale**: Serenity BDD 4.x is the current stable version that supports Java 17+ including Java 21. Cucumber 7.x is the matching BDD framework version. Gradle is the prescribed build tool per ADR-006. Serenity manages the WebDriver lifecycle automatically, reducing boilerplate and ensuring screenshot capture per step.

**Alternatives considered**:
- Maven: Rejected — ADR-006 prescribes Gradle
- TestNG: Rejected — Cucumber + JUnit is the standard Serenity BDD integration
- Serenity 3.x: Rejected — lacks Java 21 support and newer Cucumber compatibility

### RT-2: Page Factory Pattern with Serenity PageObject

**Decision**: Use Serenity's built-in `PageObject` base class with `@FindBy` annotations (Selenium Page Factory). Elements are initialized automatically by Serenity when the page is accessed.

**Rationale**: Serenity's `PageObject` class extends Selenium's Page Factory support and adds built-in wait mechanisms, screenshot capture, and driver management. Using `@FindBy(css = "[data-testid='...']")` aligns with the project's locator strategy and avoids manual `PageFactory.initElements()` calls.

**Alternatives considered**:
- Raw Selenium PageFactory without Serenity: Rejected — loses Serenity's wait, reporting, and lifecycle management
- Serenity `$()` / `find()` dynamic locators: Viable alternative but `@FindBy` provides clearer declaration and aligns with "Page Factory" pattern name in the project title
- Custom element wrappers: Rejected — unnecessary complexity for this scope

### RT-3: Locator Strategy for FoodTech Login Page

**Decision**: Use `data-testid` CSS selectors as primary locators via `@FindBy(css = "[data-testid='...']")`.

**Rationale**: The FoodTech Front application has been stabilized with stable `data-testid` attributes on all login form elements (confirmed via testability analysis). CSS selectors with `data-testid` are:
- Resilient to visual changes (class name or styling changes don't break tests)
- Decoupled from application structure (unlike XPath)
- Explicitly designed for test automation
- Fast to evaluate (CSS selectors are faster than XPath in all browsers)

**Alternatives considered**:
- XPath: Rejected per locator strategy — fragile, slower, harder to maintain
- `id` attribute: Available as fallback (`id="email"`, `id="password"`) but `data-testid` is preferred since it's automation-specific
- `name` attribute: Not set on all elements; less reliable

### RT-4: Test Data Management

**Decision**: Define test credentials as constants within the Step Definitions class. No external data files or data providers for this scope.

**Rationale**: Only two data sets are needed (valid and invalid credentials). The simplicity of constants avoids the overhead of external data files, CSV readers, or property files. If future scenarios require parameterized data (e.g., Scenario Outline with multiple credential sets), this can be refactored to use Cucumber Examples tables.

**Alternatives considered**:
- External properties/YAML file: Rejected — overkill for 2 credential sets
- Cucumber Scenario Outline with Examples: Viable for future expansion but over-engineered for exactly 2 deterministic scenarios
- Environment variables: Could be used for base URL (via `serenity.conf`) but credentials are static test data, not environment config

### RT-5: Synchronization and Wait Strategy

**Decision**: Use Serenity's implicit waits (configured in `serenity.conf`) for general element readiness, and explicit waits (`waitForVisibilityOf`, `waitFor`) for specific conditions like post-login URL change and error message appearance.

**Rationale**: Serenity wraps Selenium's wait mechanisms and integrates them with its PageObject lifecycle. The login page is a React SPA that renders dynamically — elements may not be immediately available after navigation. Post-login redirect involves a React Router navigation that changes the URL asynchronously.

**Alternatives considered**:
- `Thread.sleep()`: Rejected — forbidden per synchronization strategy; non-deterministic and wasteful
- Only implicit waits: Insufficient — post-login redirect is an async operation that needs an explicit URL condition
- FluentWait: Viable but Serenity's `waitFor()` provides the same functionality with less boilerplate

### RT-6: Assertion Library Choice

**Decision**: Use Hamcrest matchers (bundled with Serenity) for assertions in Step Libraries.

**Rationale**: Serenity ships with Hamcrest and integrates it into its reporting. Hamcrest assertions produce readable failure messages that appear in Serenity reports. The `assertThat()` + matcher pattern aligns with the assertion strategy (behavioral assertions, not implementation checks).

**Alternatives considered**:
- AssertJ: Excellent library but would add a dependency not already bundled with Serenity
- JUnit 5 assertions: Less readable than Hamcrest matchers; no auto-integration with Serenity reports
- Serenity's `should()` DSL: Available for Screenplay but less common in POM-based Step Libraries

### RT-7: Browser Configuration

**Decision**: Configure Chrome (headless mode for CI, headed for local development) in `serenity.conf`. Use Serenity's automatic driver management.

**Rationale**: Chrome is the most widely supported browser for Selenium. Headless mode is needed for CI/CD pipelines. Serenity can manage ChromeDriver versions automatically, reducing setup friction.

**Alternatives considered**:
- Firefox: Viable but Chrome has broader CI support and faster headless execution
- WebDriverManager (Boni Garcia): Viable but Serenity 4.x handles driver management natively
- Dockerized browser (Selenium Grid): Out of scope for this slice

## Resolved Unknowns

| Unknown | Resolution |
|---------|-----------|
| Serenity + Java 21 compatibility | Serenity 4.x fully supports Java 21 |
| PageFactory vs dynamic locators | Use `@FindBy` (PageFactory) per project naming convention |
| How to locate elements | `data-testid` CSS selectors (confirmed available in FoodTech) |
| Where assertions live | Step Libraries only (not Page Objects per §6) |
| How to handle async navigation | Explicit wait for URL change to contain `/mesero` |
| Test data storage | Constants in Step Definitions; no external files |
| Browser driver management | Serenity automatic management |
| Report generation | `./gradlew clean test aggregate` produces HTML reports |

## Open Items

None — all unknowns resolved. Ready for Phase 1 design artifacts.
