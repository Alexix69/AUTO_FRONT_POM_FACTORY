package com.foodtech.automation.pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationPage extends PageObject {

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(5);

    @FindBy(css = "[data-testid='toggle-mode-btn']")
    private WebElementFacade toggleModeButton;

    @FindBy(css = "[data-testid='email-input']")
    private WebElementFacade emailInput;

    @FindBy(css = "[data-testid='username-input']")
    private WebElementFacade usernameInput;

    @FindBy(css = "[data-testid='password-input']")
    private WebElementFacade passwordInput;

    @FindBy(css = "[data-testid='role-select-trigger']")
    private WebElementFacade roleSelectTrigger;

    @FindBy(css = "[data-testid='role-option-mesero']")
    private WebElementFacade roleOptionMesero;

    @FindBy(css = "[data-testid='role-option-cocinero']")
    private WebElementFacade roleOptionCocinero;

    @FindBy(css = "[data-testid='role-option-bartender']")
    private WebElementFacade roleOptionBartender;

    @FindBy(css = "[data-testid='submit-btn']")
    private WebElementFacade submitButton;

    @FindBy(css = "[data-testid='role-error']")
    private WebElementFacade roleError;

    public void activateRegistrationMode() {
        toggleModeButton.waitUntilClickable().click();
    }

    public void enterEmail(String email) {
        emailInput.waitUntilVisible().clear();
        emailInput.sendKeys(email);
    }

    public void enterUsername(String username) {
        usernameInput.waitUntilVisible().clear();
        usernameInput.sendKeys(username);
    }

    public void enterPassword(String password) {
        passwordInput.waitUntilVisible().clear();
        passwordInput.sendKeys(password);
    }

    public void openRoleDropdown() {
        roleSelectTrigger.waitUntilClickable().click();
    }

    public void selectRoleOption(String roleLower) {
        switch (roleLower) {
            case "mesero"    -> roleOptionMesero.waitUntilClickable().click();
            case "cocinero"  -> roleOptionCocinero.waitUntilClickable().click();
            case "bartender" -> roleOptionBartender.waitUntilClickable().click();
            default          -> throw new IllegalArgumentException("Unknown role: " + roleLower);
        }
    }

    public void clickSubmit() {
        submitButton.waitUntilClickable().click();
    }

    public boolean isRoleSelectTriggerVisible() {
        return roleSelectTrigger.isPresent() && roleSelectTrigger.isVisible();
    }

    public boolean isRoleOptionVisible(String roleLower) {
        return switch (roleLower) {
            case "mesero"    -> roleOptionMesero.isPresent() && roleOptionMesero.isVisible();
            case "cocinero"  -> roleOptionCocinero.isPresent() && roleOptionCocinero.isVisible();
            case "bartender" -> roleOptionBartender.isPresent() && roleOptionBartender.isVisible();
            default          -> throw new IllegalArgumentException("Unknown role: " + roleLower);
        };
    }

    public boolean isRoleErrorDisplayed() {
        return roleError.isPresent() && roleError.isVisible();
    }

    public String getRoleErrorMessage() {
        return roleError.waitUntilVisible().getText();
    }

    public void waitForRoleErrorVisible() {
        roleError.waitUntilVisible();
    }

    public void waitForRedirectAwayFromLogin() {
        WebDriverWait wait = new WebDriverWait(getDriver(), DEFAULT_TIMEOUT);
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/login")));
    }

    public void waitForUrlContaining(String path) {
        WebDriverWait wait = new WebDriverWait(getDriver(), DEFAULT_TIMEOUT);
        wait.until(ExpectedConditions.urlContains(path));
    }
}
