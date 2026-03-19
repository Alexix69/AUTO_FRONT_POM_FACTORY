package com.foodtech.automation.utils;

/**
 * Thread-local storage for per-scenario test data.
 */
public final class TestContext {

    private static final ThreadLocal<TestDataFactory.RegistrationData> USER = new ThreadLocal<>();
    private static final ThreadLocal<String> SCENARIO_NAME = new ThreadLocal<>();

    private TestContext() {
    }

    public static void setUser(TestDataFactory.RegistrationData user) {
        USER.set(user);
    }

    public static TestDataFactory.RegistrationData getUser() {
        return USER.get();
    }

    public static void clear() {
        USER.remove();
        SCENARIO_NAME.remove();
    }

    public static void setScenarioName(String scenarioName) {
        SCENARIO_NAME.set(scenarioName);
    }

    public static String getScenarioName() {
        return SCENARIO_NAME.get();
    }
}
