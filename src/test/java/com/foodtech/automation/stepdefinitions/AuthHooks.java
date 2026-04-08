package com.foodtech.automation.stepdefinitions;

import com.foodtech.automation.utils.RegisterApiClient;
import com.foodtech.automation.utils.TestContext;
import com.foodtech.automation.utils.TestDataFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class AuthHooks {

    @Before("@positiveLogin")
    public void registerUserForLogin() {
        TestDataFactory.RegistrationData user = TestDataFactory.createRegistrationData();
        RegisterApiClient.register(user);
        TestContext.setUser(user);
    }

    @After("@positiveLogin")
    public void clearUserContext() {
        TestContext.clear();
    }

    @After("@positiveRegister")
    public void clearRegistrationContext() {
        TestContext.clear();
    }
}
