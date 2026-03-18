package com.foodtech.automation.pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object for the FoodTech Login page.
 * <p>
 * Responsibilities (Constitution §6):
 * - Declares element locators using data-testid attributes
 * - Exposes behaviour methods for interacting with the form
 * - Contains NO assertions and NO business logic
 */
public class LoginPage extends PageObject {

    @FindBy(css = "[data-testid='email-input']")
    private WebElementFacade emailInput;

    @FindBy(css = "[data-testid='password-input']")
    private WebElementFacade passwordInput;

    @FindBy(css = "[data-testid='submit-btn']")
    private WebElementFacade submitButton;

    @FindBy(css = "[data-testid='toggle-mode-btn']")
    private WebElementFacade toggleModeButton;

    @FindBy(css = "[data-testid='username-input']")
    private WebElementFacade usernameInput;

    @FindBy(css = "[data-testid='error-message']")
    private WebElementFacade errorMessage;

    /**
     * Types the given email/identifier into the email input field.
     */
    public void enterEmail(String email) {
        emailInput.waitUntilVisible().clear();
        emailInput.sendKeys(email);
    }

    /**
     * Types the given password into the password input field.
     */
    public void enterPassword(String password) {
        passwordInput.waitUntilVisible().clear();
        passwordInput.sendKeys(password);
    }

    /**
     * Types the given username into the username input field.
     */
    public void enterUsername(String username) {
        usernameInput.waitUntilVisible().clear();
        usernameInput.sendKeys(username);
    }

    /**
     * Switches the form to register mode if it is not already active.
     */
    public void openRegisterMode() {
        if (!usernameInput.isPresent()) {
            toggleModeButton.waitUntilClickable().click();
        }
    }

    /**
     * Clicks the login submit button.
     */
    public void clickLogin() {
        submitButton.waitUntilClickable().click();
    }

    /**
     * Returns whether the error message element is currently visible in the DOM.
     * The error element is conditionally rendered — only present on failed login.
     */
    public boolean isErrorMessageDisplayed() {
        return errorMessage.isPresent() && errorMessage.isVisible();
    }

    /**
     * Returns the visible text of the error message element.
     */
    public String getErrorMessage() {
        return errorMessage.waitUntilVisible().getText();
    }
}
