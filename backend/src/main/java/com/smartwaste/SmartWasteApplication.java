package com.smartwaste;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

/**
 * Smart Waste Management System - Main Application Entry Point
 * 
 * Boots up the Spring Boot application and configures:
 * - Automated loading of environment variables from the root '.env' file
 * - Embedded Web Server (Tomcat)
 * - Spring MVC DispatcherServlet
 * - Thymeleaf View Resolver
 * - Static resource handler
 * - Spring Data JPA repositories & Spring Security
 */
@SpringBootApplication
public class SmartWasteApplication {

    public static void main(String[] args) {
        // Automatically load environment variables from root or local .env file
        loadDotenv();
        SpringApplication.run(SmartWasteApplication.class, args);
    }

    /**
     * Loads environment variables from .env into JVM System properties before Spring starts.
     * Checks both the current working directory and the parent directory (project root).
     * Zero external dependencies required.
     * Does not log or expose secret values.
     */
    public static void loadDotenv() {
        try {
            File envFile = new File(".env");
            if (!envFile.exists()) {
                envFile = new File("../.env");
            }

            if (envFile.exists() && envFile.isFile()) {
                List<String> lines = Files.readAllLines(envFile.toPath());
                for (String line : lines) {
                    line = line.trim();
                    // Ignore comments and empty lines
                    if (line.isEmpty() || line.startsWith("#") || !line.contains("=")) {
                        continue;
                    }

                    int equalsIndex = line.indexOf('=');
                    String key = line.substring(0, equalsIndex).trim();
                    String value = line.substring(equalsIndex + 1).trim();

                    // Strip optional quotes
                    if ((value.startsWith("\"") && value.endsWith("\"")) || 
                        (value.startsWith("'") && value.endsWith("'"))) {
                        value = value.substring(1, value.length() - 1);
                    }

                    // Only set property if not already configured in host OS environment or System properties
                    if (System.getProperty(key) == null && System.getenv(key) == null) {
                        System.setProperty(key, value);
                    }
                }
            }
        } catch (Exception ignored) {
            // Gracefully fall back to standard OS environment variables
        }
    }
}
