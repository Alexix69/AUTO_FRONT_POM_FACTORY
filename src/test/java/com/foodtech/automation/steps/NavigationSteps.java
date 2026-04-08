package com.foodtech.automation.steps;

import com.foodtech.automation.pages.DashboardPage;
import com.foodtech.automation.utils.EvidenceManager;
import net.serenitybdd.annotations.Step;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

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
