package com.smartwaste.controller;

import com.smartwaste.service.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final RegistrationService registrationService;

    public AuthController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/citizen/login")
    public String citizenLogin() {
        return "citizen/login";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "citizen/login";
    }

    @GetMapping("/citizen/register")
    public String citizenRegister() {
        return "citizen/register";
    }

    @PostMapping("/citizen/register")
    public String registerCitizen(
            @RequestParam String fullName,
            @RequestParam String email,
            @RequestParam String phoneNumber,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            Model model) {

        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            return "citizen/register";
        }

        try {
            registrationService.registerCitizen(
                    fullName, email, phoneNumber, password);
            return "redirect:/citizen/login?registered";

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "citizen/register";
        }
    }

    @GetMapping("/worker/login")
    public String workerLogin() {
        return "worker/login";
    }

    @GetMapping("/admin/login")
    public String adminLogin() {
        return "admin/login";
    }
}