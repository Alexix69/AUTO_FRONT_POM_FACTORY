package com.foodtech.automation.pages;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object for the FoodTech Waiter Dashboard page (/mesero).
 * <p>
 * Responsibilities (Constitution §6):
 * - Declares element locators for the dashboard view
 * - Exposes behaviour methods for querying dashboard state
 * - Contains NO assertions and NO business logic
 */
public class DashboardPage extends PageObject {

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(5);

    /**
     * Returns the current browser URL.
     * Used by NavigationSteps to assert the redirect happened.
     */
    public String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }

    /**
     * Returns true when the current URL contains "/mesero",
     * indicating the waiter dashboard has been loaded.
     */
    public boolean isDisplayed() {
        return getCurrentUrl().contains("/mesero");
    }

    public void waitForDashboardVisible() {
        WebDriverWait wait = new WebDriverWait(getDriver(), DEFAULT_TIMEOUT);
        wait.until(ExpectedConditions.urlContains("/mesero"));
    }
}
