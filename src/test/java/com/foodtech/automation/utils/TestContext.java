package com.foodtech.automation.utils;

/**
 * Thread-local storage for per-scenario test data.
 */
public final class TestContext {

    private static final ThreadLocal<TestDataFactory.RegistrationData> USER = new ThreadLocal<>();

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
    }
}
