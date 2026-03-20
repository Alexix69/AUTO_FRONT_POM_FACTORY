package com.foodtech.automation.steps;

import com.foodtech.automation.pages.LoginPage;
import com.foodtech.automation.utils.EnvironmentChecker;
import com.foodtech.automation.utils.EvidenceManager;
import com.foodtech.automation.utils.TestConfig;
import net.serenitybdd.annotations.Step;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * Serenity Step Library for login actions.
 * <p>
 * Responsibilities (Constitution §5, §6):
 * - Orchestrates calls to LoginPage Page Object
 * - Owns all assertions related to the login page state
 * - Each method is annotated with @Step for Serenity evidence capture
 * - Contains NO Cucumber glue code
 */
public class LoginSteps {

    LoginPage loginPage;

    @Step("Validate environment availability")
    public void validateEnvironmentAvailability() {
        String baseUrl = TestConfig.getBaseUrl();
        if (!EnvironmentChecker.isFrontendAvailable(baseUrl)) {
            String loginUrl = EnvironmentChecker.buildLoginUrl(baseUrl);
            throw new RuntimeException(
                    "Precondition failed: frontend not available at " + loginUrl +
                            ". Verify the app is running and the base URL is correct.");
        }
    }

    @Step("Open the login page")
    public void openLoginPage() {
        validateEnvironmentAvailability();
        loginPage.open();
    }

    @Step("Enter credentials — email: '{0}'")
    public void enterCredentials(String email, String password) {
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.waitForEmailValueNotEmpty();
        loginPage.waitForPasswordValueNotEmpty();
        EvidenceManager.captureStep(loginPage.getDriver(), "before_submit_credentials");
    }

    @Step("Submit the login form")
    public void submitLoginForm() {
        loginPage.clickLogin();
    }

    @Step("Verify error message is displayed")
    public void shouldSeeErrorMessage() {
        loginPage.waitForErrorMessageVisible();
        EvidenceManager.captureStep(loginPage.getDriver(), "error_message_displayed");
        assertThat("Error message should be visible on the page",
                loginPage.isErrorMessageDisplayed(), is(true));
        assertThat("Error message text should match expected value",
                loginPage.getErrorMessage(), equalTo("Credenciales inválidas"));
    }

    @Step("Verify user remains on the login page")
    public void shouldBeOnLoginPage() {
        loginPage.waitForLoginUrl();
        String currentUrl = loginPage.getDriver().getCurrentUrl();
        assertThat("User should remain on the login page after failed authentication",
                currentUrl.contains("/login"), is(true));
        EvidenceManager.captureStep(loginPage.getDriver(), "final_error_state");
    }

}
