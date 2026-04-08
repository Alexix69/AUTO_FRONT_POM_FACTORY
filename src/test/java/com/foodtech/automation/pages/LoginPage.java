package com.foodtech.automation.pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends PageObject {

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(5);

    @FindBy(css = "[data-testid='email-input']")
    private WebElementFacade emailInput;

    @FindBy(css = "[data-testid='password-input']")
    private WebElementFacade passwordInput;

    @FindBy(css = "[data-testid='submit-btn']")
    private WebElementFacade submitButton;

    @FindBy(css = "[data-testid='error-message']")
    private WebElementFacade errorMessage;

    public void enterEmail(String email) {
        emailInput.waitUntilVisible().clear();
        emailInput.sendKeys(email);
    }

    public void enterPassword(String password) {
        passwordInput.waitUntilVisible().clear();
        passwordInput.sendKeys(password);
    }

    public void clickLogin() {
        submitButton.waitUntilClickable().click();
    }

    public void waitForEmailValueNotEmpty() {
        WebDriverWait wait = new WebDriverWait(getDriver(), DEFAULT_TIMEOUT);
        wait.until(ExpectedConditions.attributeToBeNotEmpty(emailInput, "value"));
    }

    public void waitForPasswordValueNotEmpty() {
        WebDriverWait wait = new WebDriverWait(getDriver(), DEFAULT_TIMEOUT);
        wait.until(ExpectedConditions.attributeToBeNotEmpty(passwordInput, "value"));
    }

    public void waitForErrorMessageVisible() {
        errorMessage.waitUntilVisible();
    }

    public void waitForLoginUrl() {
        WebDriverWait wait = new WebDriverWait(getDriver(), DEFAULT_TIMEOUT);
        wait.until(ExpectedConditions.urlContains("/login"));
    }

    public boolean isErrorMessageDisplayed() {
        return errorMessage.isPresent() && errorMessage.isVisible();
    }

    public String getErrorMessage() {
        return errorMessage.waitUntilVisible().getText();
    }
}
