package com.foodtech.automation.stepdefinitions;

import com.foodtech.automation.utils.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

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