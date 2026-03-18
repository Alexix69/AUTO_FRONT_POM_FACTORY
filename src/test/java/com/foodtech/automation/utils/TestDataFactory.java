package com.foodtech.automation.utils;

import java.time.Instant;

/**
 * Factory for deterministic test data.
 */
public final class TestDataFactory {

    private static final String PASSWORD = "password123";

    private TestDataFactory() {
    }

    public static String generateEmail() {
        return "test+" + Instant.now().toEpochMilli() + "@restaurant.com";
    }

    public static String getPassword() {
        return PASSWORD;
    }
}
