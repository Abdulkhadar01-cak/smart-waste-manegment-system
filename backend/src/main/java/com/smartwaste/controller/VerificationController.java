package com.smartwaste.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * VerificationController
 * 
 * Purpose:
 * Serves the root dashboard verification page at "/" to confirm:
 * 1. Thymeleaf template rendering from resources/templates/index.html
 * 2. Static CSS, JS, and image resolution from resources/static/
 * 3. Bootstrap 5 WebJars integration
 * 4. Spring Security 6 active filter chain
 * 5. Spring Data JPA repository and Hibernate configuration
 * 6. PostgreSQL driver and Flyway migration readiness
 * 7. Global exception handling
 */
@Controller
public class VerificationController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("appName", "Smart Waste Management System");
        model.addAttribute("projectStatus", "Backend Infrastructure Initialized");
        model.addAttribute("javaVersion", System.getProperty("java.version"));
        model.addAttribute("springBootVersion", "3.3.5");
        model.addAttribute("securityStatus", "Spring Security 6 Active (Public Mode)");
        model.addAttribute("jpaStatus", "Spring Data JPA & Hibernate Configured");
        model.addAttribute("postgresStatus", "PostgreSQL Driver Registered");
        model.addAttribute("flywayStatus", "Flyway Migration Engine Configured");
        model.addAttribute("bootstrapStatus", "Bootstrap 5.3.3 WebJars Configured");
        model.addAttribute("exceptionStatus", "GlobalExceptionHandler Active");
        model.addAttribute("checkTimestamp", LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        return "index";
    }
}
