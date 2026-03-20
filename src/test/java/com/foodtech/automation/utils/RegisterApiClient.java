package com.foodtech.automation.utils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * Simple client to register users via the real backend endpoint.
 */
public final class RegisterApiClient {

    private static final Duration TIMEOUT = Duration.ofSeconds(5);

    private RegisterApiClient() {
    }

    public static void register(TestDataFactory.RegistrationData user) {
        String baseUrl = TestConfig.getBackendBaseUrl();
        String url = buildRegisterUrl(baseUrl);
        String payload = buildPayload(user);

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(TIMEOUT)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(TIMEOUT)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int status = response.statusCode();
            System.out.println("[RegisterApiClient] user=" + user.email() + " status=" + status);
            if (status != 200 && status != 201) {
                throw new IllegalStateException("User setup failed: backend registration unavailable (status " + status + ")");
            }
        } catch (IOException | InterruptedException e) {
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            throw new IllegalStateException("User setup failed: backend registration unavailable", e);
        }
    }

    private static String buildRegisterUrl(String baseUrl) {
        String trimmed = baseUrl == null ? "" : baseUrl.trim();
        if (trimmed.endsWith("/")) {
            return trimmed + "api/auth/register";
        }
        return trimmed + "/api/auth/register";
    }

    private static String buildPayload(TestDataFactory.RegistrationData user) {
        return "{\"username\":\"" + user.username() + "\"," +
                "\"email\":\"" + user.email() + "\"," +
                "\"password\":\"" + user.password() + "\"}";
    }
}
