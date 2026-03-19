package com.foodtech.automation.stepdefinitions;

import com.foodtech.automation.utils.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

/**
 * Hooks to provide scenario context for evidence naming.
 */
public class ScenarioHooks {

    @Before
    public void setScenarioName(Scenario scenario) {
        TestContext.setScenarioName(scenario.getName());
    }

    @After
    public void clearScenarioName() {
        TestContext.clear();
    }
}