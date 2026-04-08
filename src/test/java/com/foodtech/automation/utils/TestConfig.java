package com.foodtech.automation.utils;

import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.thucydides.model.environment.SystemEnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;

public final class TestConfig {

    private static final String BASE_URL_PROPERTY = "webdriver.base.url";
    private static final String BASE_URL_ENV = "WEBDRIVER_BASE_URL";

    private static final String BACKEND_BASE_URL_PROPERTY = "foodtech.backend.base.url";
    private static final String BACKEND_BASE_URL_ENV = "FOODTECH_BACKEND_BASE_URL";

    public static final String REGISTER_PATH = "/api/auth/register";

    public static final String ROUTE_MESERO = "/mesero";
    public static final String ROUTE_COCINA = "/cocina";
    public static final String ROUTE_BARRA = "/barra";

    private TestConfig() {
    }

    public static String getBaseUrl() {
        return resolveValue(BASE_URL_PROPERTY, BASE_URL_ENV);
    }

    public static String getBackendBaseUrl() {
        return resolveValue(BACKEND_BASE_URL_PROPERTY, BACKEND_BASE_URL_ENV);
    }

    private static String resolveValue(String propertyKey, String envKey) {
        String value = System.getProperty(propertyKey);
        if (value == null || value.isBlank()) {
            value = System.getenv(envKey);
        }
        if (value == null || value.isBlank()) {
            EnvironmentVariables envVars = SystemEnvironmentVariables.createEnvironmentVariables();
            value = EnvironmentSpecificConfiguration.from(envVars)
                    .getOptionalProperty(propertyKey)
                    .orElse(null);
        }
        return value == null || value.isBlank() ? null : value.trim();
    }
}
