package com.foodtech.automation.stepdefinitions;

import com.foodtech.automation.steps.LoginSteps;
import com.foodtech.automation.steps.NavigationSteps;
import com.foodtech.automation.utils.EvidenceManager;
import com.foodtech.automation.utils.TestContext;
import com.foodtech.automation.utils.TestDataFactory;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;

public class LoginStepDefinitions {


    @Steps
    LoginSteps loginSteps;

    @Steps
    NavigationSteps navigationSteps;

    @Given("the user starts an authentication session")
    public void givenUserStartsAuthenticationSession() {
        loginSteps.openLoginPage();
    }

    @When("the user authenticates with valid credentials")
    public void whenUserAuthenticatesWithValidCredentials() {
        loginSteps.enterCredentials(TestContext.getUser());
    }

    @When("the user authenticates with invalid credentials")
    public void whenUserAuthenticatesWithInvalidCredentials() {
        loginSteps.enterCredentials(TestDataFactory.INVALID_EMAIL, TestDataFactory.INVALID_PASSWORD);
    }

    @And("the user submits the authentication request")
    public void whenUserSubmitsAuthenticationRequest() {
        loginSteps.submitLoginForm();
    }

    @Then("access to the operational view should be granted")
    public void thenAccessToOperationalViewShouldBeGranted() {
        navigationSteps.shouldBeOnDashboard();
    }

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
        EvidenceManager.captureIfFailed(scenario);
    }

    @After
    public void captureFinalEvidence(Scenario scenario) {
        EvidenceManager.captureFinal(scenario);
    }
}
