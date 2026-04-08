package com.foodtech.automation.pages;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DashboardPage extends PageObject {

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(5);

    public String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }

    public boolean isDisplayed() {
        return getCurrentUrl().contains("/mesero");
    }

    public void waitForDashboardVisible() {
        WebDriverWait wait = new WebDriverWait(getDriver(), DEFAULT_TIMEOUT);
        wait.until(ExpectedConditions.urlContains("/mesero"));
    }
}
