package com.foodtech.automation.stepdefinitions;

import com.foodtech.automation.utils.RegisterApiClient;
import com.foodtech.automation.utils.TestContext;
import com.foodtech.automation.utils.TestDataFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;

/**
 * Scenario hooks for authentication setup.
 */
public class AuthHooks {

    @Before("@positiveLogin")
    public void registerUserForLogin() {
        TestDataFactory.RegistrationData user = TestDataFactory.createRegistrationData();
        System.out.println("[AuthHooks] registering user=" + user.email());
        RegisterApiClient.register(user);
        TestContext.setUser(user);
    }

    @After("@positiveLogin")
    public void clearUserContext() {
        TestContext.clear();
    }
}
