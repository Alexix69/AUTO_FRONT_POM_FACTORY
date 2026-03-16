# AUTO_FRONT_POM_FACTORY

Frontend UI automation project using the **Page Object Model + Factory** pattern.

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
    java/       ← Step definitions, page objects, factories
    resources/  ← serenity.conf, cucumber options
features/       ← Gherkin feature files
```

## Workflow

This project follows **Spec-Driven Development** using [Spec-Kit](https://github.com/github/spec-kit).

Branch strategy: `main` → `develop` → `feature/*`
