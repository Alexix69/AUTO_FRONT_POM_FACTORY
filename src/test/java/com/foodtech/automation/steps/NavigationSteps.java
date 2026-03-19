package com.foodtech.automation.steps;

import com.foodtech.automation.pages.DashboardPage;
import com.foodtech.automation.utils.EvidenceManager;
import net.serenitybdd.annotations.Step;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Serenity Step Library for navigation verification.
 * <p>
 * Responsibilities (Constitution §5, §6):
 * - Orchestrates calls to DashboardPage Page Object
 * - Owns assertions related to post-login navigation
 * - Each method is annotated with @Step for Serenity evidence capture
 * - Contains NO Cucumber glue code
 */
public class NavigationSteps {

    DashboardPage dashboardPage;

    @Step("Verify user is redirected to the waiter dashboard")
    public void shouldBeOnDashboard() {
        dashboardPage.waitForDashboardVisible();
        EvidenceManager.captureStep(dashboardPage.getDriver(), "after_login_success");
        assertThat("User should be redirected to the waiter dashboard (/mesero) after successful login",
                dashboardPage.isDisplayed(), is(true));
        EvidenceManager.captureStep(dashboardPage.getDriver(), "final_success_state");
    }
}
