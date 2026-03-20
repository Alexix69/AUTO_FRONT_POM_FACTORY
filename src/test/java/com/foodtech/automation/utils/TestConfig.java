package com.foodtech.automation.utils;

/**
 * Centralized configuration access for test execution.
 */
public final class TestConfig {

    private static final String BASE_URL_PROPERTY = "webdriver.base.url";
    private static final String BASE_URL_ENV = "WEBDRIVER_BASE_URL";

    private static final String BACKEND_BASE_URL_PROPERTY = "foodtech.backend.base.url";
    private static final String BACKEND_BASE_URL_ENV = "FOODTECH_BACKEND_BASE_URL";

    private static final String DEFAULT_BASE_URL = "http://localhost:5173";
    private static final String DEFAULT_BACKEND_BASE_URL = "http://localhost:8080";

    private TestConfig() {
    }

    public static String getBaseUrl() {
        return resolveValue(BASE_URL_PROPERTY, BASE_URL_ENV, DEFAULT_BASE_URL);
    }

    public static String getBackendBaseUrl() {
        return resolveValue(BACKEND_BASE_URL_PROPERTY, BACKEND_BASE_URL_ENV, DEFAULT_BACKEND_BASE_URL);
    }

    private static String resolveValue(String propertyKey, String envKey, String defaultValue) {
        String value = System.getProperty(propertyKey);
        if (value == null || value.isBlank()) {
            value = System.getenv(envKey);
        }
        if (value == null || value.isBlank()) {
            value = defaultValue;
        }
        return value.trim();
    }
}
