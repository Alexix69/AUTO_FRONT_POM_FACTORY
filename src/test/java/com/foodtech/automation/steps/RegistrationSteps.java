package com.foodtech.automation.steps;

import com.foodtech.automation.pages.RegistrationPage;
import com.foodtech.automation.utils.EvidenceManager;
import com.foodtech.automation.utils.TestContext;
import com.foodtech.automation.utils.TestDataFactory;
import net.serenitybdd.annotations.Step;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class RegistrationSteps {

    private static final String EXPECTED_ROLE_ERROR = "Debe seleccionar un rol para continuar";

    private static final Map<String, String> ROLE_REDIRECT_PATHS = Map.of(
            "MESERO",    "/mesero",
            "COCINERO",  "/cocina",
            "BARTENDER", "/barra"
    );

    RegistrationPage registrationPage;

    @Step("Open the registration form")
    public void openRegistrationForm() {
        registrationPage.open();
        registrationPage.activateRegistrationMode();
    }

    @Step("Open registration form and fill all required fields with dynamic test data")
    public void openFormAndFillAllRequiredFields() {
        openRegistrationForm();
        fillAllRequiredFields();
    }

    @Step("Open registration form and fill all required fields except role")
    public void openFormAndFillFieldsExceptRole() {
        openRegistrationForm();
        fillAllRequiredFields();
    }

    @Step("Observe the available registration form fields")
    public void captureFormState() {
        EvidenceManager.captureStep(registrationPage.getDriver(), "registration_form_fields");
    }

    @Step("Fill all required registration fields with dynamic test data")
    public void fillAllRequiredFields() {
        TestDataFactory.RegistrationData user = TestDataFactory.createRegistrationData();
        TestContext.setUser(user);
        registrationPage.enterEmail(user.email());
        registrationPage.enterUsername(user.username());
        registrationPage.enterPassword(user.password());
        EvidenceManager.captureStep(registrationPage.getDriver(), "fields_filled_before_role");
    }

    @Step("Open the role selection dropdown")
    public void openRoleDropdown() {
        registrationPage.openRoleDropdown();
        EvidenceManager.captureStep(registrationPage.getDriver(), "role_dropdown_open");
    }

    @Step("Select role option '{0}'")
    public void selectRole(String role) {
        registrationPage.selectRoleOption(role.toLowerCase());
        EvidenceManager.captureStep(registrationPage.getDriver(), "role_selected_" + role.toLowerCase());
    }

    @Step("Select role '{0}' and submit the registration form")
    public void selectRoleAndSubmit(String role) {
        registrationPage.openRoleDropdown();
        registrationPage.selectRoleOption(role.toLowerCase());
        registrationPage.clickSubmit();
    }

    @Step("Submit the registration form without role selection")
    public void submitWithoutRole() {
        registrationPage.clickSubmit();
    }

    @Step("Verify the role selection dropdown is visible on the form")
    public void shouldSeeRoleDropdown() {
        assertThat("Role dropdown trigger should be visible on the registration form",
                registrationPage.isRoleSelectTriggerVisible(), is(true));
        EvidenceManager.captureStep(registrationPage.getDriver(), "role_dropdown_visible");
    }

    @Step("Verify the dropdown shows exactly MESERO, COCINERO and BARTENDER")
    public void shouldSeeAllRoleOptions() {
        assertThat("MESERO option should be visible in the dropdown",
                registrationPage.isRoleOptionVisible("mesero"), is(true));
        assertThat("COCINERO option should be visible in the dropdown",
                registrationPage.isRoleOptionVisible("cocinero"), is(true));
        assertThat("BARTENDER option should be visible in the dropdown",
                registrationPage.isRoleOptionVisible("bartender"), is(true));
        EvidenceManager.captureStep(registrationPage.getDriver(), "all_role_options_visible");
    }

    @Step("Verify the user was successfully registered with role '{0}'")
    public void shouldBeRegisteredWithRole(String role) {
        String expectedPath = ROLE_REDIRECT_PATHS.get(role.toUpperCase());
        if (expectedPath == null) {
            throw new IllegalArgumentException("No redirect path configured for role: " + role);
        }
        registrationPage.waitForRedirectAwayFromLogin();
        registrationPage.waitForUrlContaining(expectedPath);
        String currentUrl = registrationPage.getDriver().getCurrentUrl();
        assertThat("After successful registration with role " + role + " the user should be redirected to " + expectedPath,
                currentUrl, containsString(expectedPath));
        EvidenceManager.captureStep(registrationPage.getDriver(), "registration_success_" + role.toLowerCase());
    }

    @Step("Verify role validation error is displayed")
    public void shouldSeeRoleValidationError() {
        registrationPage.waitForRoleErrorVisible();
        EvidenceManager.captureStep(registrationPage.getDriver(), "role_error_displayed");
        assertThat("Role validation error should be visible on the page",
                registrationPage.isRoleErrorDisplayed(), is(true));
        assertThat("Role validation error text should match expected message",
                registrationPage.getRoleErrorMessage(), equalTo(EXPECTED_ROLE_ERROR));
    }
}
