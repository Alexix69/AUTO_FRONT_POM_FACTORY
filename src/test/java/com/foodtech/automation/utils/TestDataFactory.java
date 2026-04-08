package com.foodtech.automation.utils;

import java.time.Instant;

public final class TestDataFactory {

    private static final String PASSWORD_PREFIX = "Pass";
    private static final String USERNAME_PREFIX = "user";

    public static final String INVALID_EMAIL = "wrong@email.com";
    public static final String INVALID_PASSWORD = "wrongpass";

    private TestDataFactory() {
    }

    public static String generateEmail() {
        return "test+" + Instant.now().toEpochMilli() + "@restaurant.com";
    }

    public static String generatePassword() {
        return PASSWORD_PREFIX + Instant.now().toEpochMilli();
    }

    public static String generateUsername() {
        return USERNAME_PREFIX + Instant.now().toEpochMilli();
    }

    public static RegistrationData createRegistrationData() {
        String email = generateEmail();
        String password = generatePassword();
        String username = generateUsername();
        return new RegistrationData(username, email, password);
    }

    public record RegistrationData(String username, String email, String password) {
    }
}
