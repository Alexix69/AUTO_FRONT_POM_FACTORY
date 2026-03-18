package com.foodtech.automation.steps;

import com.foodtech.automation.pages.DashboardPage;
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
        assertThat("User should be redirected to the waiter dashboard (/mesero) after successful login",
                dashboardPage.isDisplayed(), is(true));
    }
}
