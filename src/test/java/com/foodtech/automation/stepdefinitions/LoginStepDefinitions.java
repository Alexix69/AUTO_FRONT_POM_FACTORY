package com.foodtech.automation.stepdefinitions;

import com.foodtech.automation.steps.LoginSteps;
import com.foodtech.automation.steps.NavigationSteps;
import com.foodtech.automation.utils.EvidenceManager;
import com.foodtech.automation.utils.TestContext;
import com.foodtech.automation.utils.TestDataFactory;
import io.cucumber.java.AfterStep;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.annotations.Steps;

/**
 * Cucumber Step Definitions for User Authentication scenarios.
 * <p>
 * Responsibilities (Constitution §4, §6):
 * - Binds Gherkin steps to Step Library methods (thin glue layer)
 * - Contains NO logic, NO assertions, NO direct WebDriver access
 * - Pure delegation: each method body is a single Step Library call
 */
public class LoginStepDefinitions {


    // Test credentials — invalid account (guaranteed not to exist)
    private static final String INVALID_EMAIL    = "wrong@email.com";
    private static final String INVALID_PASSWORD = "wrongpass";

    @Steps
    LoginSteps loginSteps;

    @Steps
    NavigationSteps navigationSteps;

    // -------------------------------------------------------------------------
    // Given
    // -------------------------------------------------------------------------

    @Given("the user starts an authentication session")
    public void givenUserStartsAuthenticationSession() {
        loginSteps.openLoginPage();
    }

    // -------------------------------------------------------------------------
    // When — User Story 1: valid credentials
    // -------------------------------------------------------------------------

    @When("the user authenticates with valid credentials")
    public void whenUserAuthenticatesWithValidCredentials() {
        TestDataFactory.RegistrationData user = TestContext.getUser();
        if (user == null) {
            throw new IllegalStateException("User setup failed: backend registration unavailable");
        }
        loginSteps.enterCredentials(user.email(), user.password());
    }

    // -------------------------------------------------------------------------
    // When — User Story 2: invalid credentials
    // -------------------------------------------------------------------------

    @When("the user authenticates with invalid credentials")
    public void whenUserAuthenticatesWithInvalidCredentials() {
        loginSteps.enterCredentials(INVALID_EMAIL, INVALID_PASSWORD);
    }

    // -------------------------------------------------------------------------
    // When — shared
    // -------------------------------------------------------------------------

    @And("the user submits the authentication request")
    public void whenUserSubmitsAuthenticationRequest() {
        loginSteps.submitLoginForm();
    }

    // -------------------------------------------------------------------------
    // Then — User Story 1
    // -------------------------------------------------------------------------

    @Then("access to the operational view should be granted")
    public void thenAccessToOperationalViewShouldBeGranted() {
        navigationSteps.shouldBeOnDashboard();
    }

    // -------------------------------------------------------------------------
    // Then — User Story 2
    // -------------------------------------------------------------------------

    @Then("an authentication error should be presented")
    public void thenAuthenticationErrorShouldBePresented() {
        loginSteps.shouldSeeErrorMessage();
    }

    @And("access should remain on the authentication screen")
    public void thenAccessShouldRemainOnAuthenticationScreen() {
        loginSteps.shouldBeOnLoginPage();
    }

    @AfterStep
    public void captureEvidence(Scenario scenario) {
        if (scenario.isFailed()) {
            String name = EvidenceManager.buildScenarioFileName(scenario.getName(), "failed_step");
            EvidenceManager.saveScreenshot(Serenity.getDriver(), name);
        }
    }

    @After
    public void captureFinalEvidence(Scenario scenario) {
        String suffix = scenario.isFailed() ? "failed_final" : "final";
        String name = EvidenceManager.buildScenarioFileName(scenario.getName(), suffix);
        EvidenceManager.saveScreenshot(Serenity.getDriver(), name);
    }
}
