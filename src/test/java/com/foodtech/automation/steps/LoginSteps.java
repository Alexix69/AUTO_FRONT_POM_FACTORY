package com.foodtech.automation.steps;

import com.foodtech.automation.pages.LoginPage;
import com.foodtech.automation.utils.EnvironmentChecker;
import net.serenitybdd.annotations.Step;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

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
        if (!EnvironmentChecker.isFrontendAvailable()) {
            throw new RuntimeException("Frontend not available");
        }
    }

    @Step("Open the login page")
    public void openLoginPage() {
        validateEnvironmentAvailability();
        loginPage.open();
    }

    @Step("Register user with email: '{0}'")
    public void register(String email, String password) {
        loginPage.openRegisterMode();

        String username = buildUsernameFromEmail(email);
        loginPage.enterEmail(email);
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        waitForDashboardRedirect();
        resetAuthState();
        loginPage.open();
    }

    @Step("Enter credentials — email: '{0}'")
    public void enterCredentials(String email, String password) {
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
    }

    @Step("Submit the login form")
    public void submitLoginForm() {
        loginPage.clickLogin();
    }

    @Step("Verify error message is displayed")
    public void shouldSeeErrorMessage() {
        assertThat("Error message should be visible on the page",
                loginPage.isErrorMessageDisplayed(), is(true));
        assertThat("Error message text should match expected value",
                loginPage.getErrorMessage(), equalTo("Credenciales inválidas"));
    }

    @Step("Verify user remains on the login page")
    public void shouldBeOnLoginPage() {
        String currentUrl = loginPage.getDriver().getCurrentUrl();
        assertThat("User should remain on the login page after failed authentication",
                currentUrl.contains("/login"), is(true));
    }

    private void waitForDashboardRedirect() {
        WebDriverWait wait = new WebDriverWait(loginPage.getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains("/mesero"));
    }

    private void resetAuthState() {
        loginPage.getDriver().manage().deleteAllCookies();
        if (loginPage.getDriver() instanceof JavascriptExecutor) {
            ((JavascriptExecutor) loginPage.getDriver())
                    .executeScript("window.localStorage.clear(); window.sessionStorage.clear();");
        }
    }

    private String buildUsernameFromEmail(String email) {
        String localPart = email.split("@", 2)[0];
        String sanitized = localPart.replaceAll("[^a-zA-Z0-9]", "_");
        return sanitized.isEmpty() ? "user" : sanitized;
    }
}
