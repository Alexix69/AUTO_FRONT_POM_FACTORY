package com.foodtech.automation.stepdefinitions;

import com.foodtech.automation.steps.RegistrationSteps;
import com.foodtech.automation.utils.EvidenceManager;
import io.cucumber.java.AfterStep;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;

public class RegistrationStepDefinitions {

    @Steps
    RegistrationSteps registrationSteps;

    @Given("the user is on the registration form")
    public void givenUserIsOnRegistrationForm() {
        registrationSteps.openRegistrationForm();
    }

    @Given("a new user has completed all required registration fields")
    public void givenNewUserHasCompletedAllRequiredFields() {
        registrationSteps.openFormAndFillAllRequiredFields();
    }

    @Given("a new user has completed all required fields except role")
    public void givenNewUserHasCompletedAllFieldsExceptRole() {
        registrationSteps.openFormAndFillFieldsExceptRole();
    }

    @When("the user views the available fields")
    public void whenUserViewsAvailableFields() {
        registrationSteps.captureFormState();
    }

    @When("the user opens the role dropdown")
    public void whenUserOpensRoleDropdown() {
        registrationSteps.openRoleDropdown();
    }

    @When("the user selects MESERO and submits the registration form")
    public void whenUserSelectsMeseroAndSubmits() {
        registrationSteps.selectRoleAndSubmit("MESERO");
    }

    @When("the user selects COCINERO and submits the registration form")
    public void whenUserSelectsCocineroAndSubmits() {
        registrationSteps.selectRoleAndSubmit("COCINERO");
    }

    @When("the user selects BARTENDER and submits the registration form")
    public void whenUserSelectsBartenderAndSubmits() {
        registrationSteps.selectRoleAndSubmit("BARTENDER");
    }

    @When("the user attempts to submit the registration form")
    public void whenUserAttemptsToSubmit() {
        registrationSteps.submitWithoutRole();
    }

    @Then("the role selection dropdown should be visible")
    public void thenRoleDropdownShouldBeVisible() {
        registrationSteps.shouldSeeRoleDropdown();
    }

    @Then("the dropdown should show exactly MESERO, COCINERO and BARTENDER")
    public void thenDropdownShouldShowAllRoleOptions() {
        registrationSteps.shouldSeeAllRoleOptions();
    }

    @Then("the system creates the user associated to the role MESERO")
    public void thenSystemCreatesUserWithRoleMesero() {
        registrationSteps.shouldBeRegisteredWithRole("MESERO");
    }

    @Then("the system creates the user associated to the role COCINERO")
    public void thenSystemCreatesUserWithRoleCocinero() {
        registrationSteps.shouldBeRegisteredWithRole("COCINERO");
    }

    @Then("the system creates the user associated to the role BARTENDER")
    public void thenSystemCreatesUserWithRoleBartender() {
        registrationSteps.shouldBeRegisteredWithRole("BARTENDER");
    }

    @Then("the system prevents submission and displays the role validation error")
    public void thenSystemPreventsSubmissionAndDisplaysError() {
        registrationSteps.shouldSeeRoleValidationError();
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
