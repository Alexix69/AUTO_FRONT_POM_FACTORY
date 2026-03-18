package com.foodtech.automation.stepdefinitions;

import com.foodtech.automation.steps.LoginSteps;
import com.foodtech.automation.steps.NavigationSteps;
import com.foodtech.automation.utils.EvidenceManager;
import com.foodtech.automation.utils.TestDataFactory;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
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

    private String dynamicEmail;
    private String dynamicPassword;

    // Test credentials — invalid account (guaranteed not to exist)
    private static final String INVALID_EMAIL    = "wrong@email.com";
    private static final String INVALID_PASSWORD = "wrongpass";

    @Steps
    LoginSteps loginSteps;

    @Steps
    NavigationSteps navigationSteps;

    @Before
    public void setUpScenario() {
        dynamicEmail = TestDataFactory.generateEmail();
        dynamicPassword = TestDataFactory.getPassword();
    }

    // -------------------------------------------------------------------------
    // Given
    // -------------------------------------------------------------------------

    @Given("the user is on the login page")
    public void givenUserIsOnLoginPage() {
        loginSteps.openLoginPage();
    }

    // -------------------------------------------------------------------------
    // When — User Story 1: valid credentials
    // -------------------------------------------------------------------------

    @When("the user enters valid credentials")
    public void whenUserEntersValidCredentials() {
        loginSteps.register(dynamicEmail, dynamicPassword);
        loginSteps.enterCredentials(dynamicEmail, dynamicPassword);
    }

    // -------------------------------------------------------------------------
    // When — User Story 2: invalid credentials
    // -------------------------------------------------------------------------

    @When("the user enters invalid credentials")
    public void whenUserEntersInvalidCredentials() {
        loginSteps.enterCredentials(INVALID_EMAIL, INVALID_PASSWORD);
    }

    // -------------------------------------------------------------------------
    // When — shared
    // -------------------------------------------------------------------------

    @And("the user submits the login form")
    public void whenUserSubmitsLoginForm() {
        loginSteps.submitLoginForm();
    }

    // -------------------------------------------------------------------------
    // Then — User Story 1
    // -------------------------------------------------------------------------

    @Then("the user should be redirected to the main operational view")
    public void thenUserShouldBeRedirected() {
        navigationSteps.shouldBeOnDashboard();
    }

    // -------------------------------------------------------------------------
    // Then — User Story 2
    // -------------------------------------------------------------------------

    @Then("the user should see an error message on the page")
    public void thenUserShouldSeeErrorMessage() {
        loginSteps.shouldSeeErrorMessage();
    }

    @And("the user should remain on the login page")
    public void thenUserShouldRemainOnLoginPage() {
        loginSteps.shouldBeOnLoginPage();
    }

    @AfterStep
    public void captureEvidence(Scenario scenario) {
        String name = scenario.getName() + "_" + EvidenceManager.timestamp();
        EvidenceManager.saveScreenshot(Serenity.getDriver(), name);
    }
}
