package com.foodtech.automation.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Stores structured evidence per execution.
 */
public final class EvidenceManager {

    private static final Path EXECUTION_DIR = initExecutionDir();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");

    private EvidenceManager() {
    }

    private static Path initExecutionDir() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Path dir = Paths.get("evidences", timestamp);
        try {
            Files.createDirectories(dir);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to create evidence directory: " + dir, e);
        }
        return dir;
    }

    public static void saveScreenshot(WebDriver driver, String name) {
        if (!(driver instanceof TakesScreenshot)) {
            return;
        }

        String sanitized = sanitize(name);
        String fileName = sanitized.endsWith(".png") ? sanitized : sanitized + ".png";
        Path target = EXECUTION_DIR.resolve(fileName);
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        try {
            Files.copy(screenshot.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("Unable to save screenshot to " + target + ": " + e.getMessage());
        }
    }

    public static String timestamp() {
        return LocalDateTime.now().format(FORMATTER);
    }

    private static String sanitize(String name) {
        return name.replaceAll("[^a-zA-Z0-9_-]", "_");
    }
}
