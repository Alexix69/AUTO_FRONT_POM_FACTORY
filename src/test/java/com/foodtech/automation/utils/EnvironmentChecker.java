package com.foodtech.automation.utils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * Lightweight environment availability checker.
 */
public final class EnvironmentChecker {

    private static final Duration TIMEOUT = Duration.ofSeconds(2);

    private EnvironmentChecker() {
    }

    public static boolean isFrontendAvailable(String baseUrl) {
        String loginUrl = buildLoginUrl(baseUrl).replace("localhost", "127.0.0.1");
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(TIMEOUT)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(loginUrl))
                .timeout(TIMEOUT)
                .GET()
                .build();

        try {
            HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
            int status = response.statusCode();
            return status >= 200 && status < 400;
        } catch (IOException | InterruptedException e) {
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            return false;
        }
    }

    public static String buildLoginUrl(String baseUrl) {
        String trimmed = baseUrl == null ? "" : baseUrl.trim();
        if (trimmed.endsWith("/")) {
            return trimmed + "login";
        }
        return trimmed + "/login";
    }
}
