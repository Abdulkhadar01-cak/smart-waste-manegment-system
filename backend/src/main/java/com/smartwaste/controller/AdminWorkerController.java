package com.smartwaste.controller;

import com.smartwaste.entity.User;
import com.smartwaste.repository.UserRepository;
import com.smartwaste.service.RegistrationService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/workers")
public class AdminWorkerController {

    private final RegistrationService registrationService;
    private final UserRepository userRepository;

    public AdminWorkerController(
            RegistrationService registrationService,
            UserRepository userRepository) {

        this.registrationService = registrationService;
        this.userRepository = userRepository;
    }

    // Display all workers
    @GetMapping
    public String showWorkers(Model model) {

        List<User> workers = userRepository.findByRole("WORKER");

        model.addAttribute("workers", workers);

        return "admin/workers";
    }

    // Open worker creation form
    @GetMapping("/new")
    public String showCreateWorkerForm() {
        return "admin/create-worker";
    }

    // Create worker account
    @PostMapping("/create")
    public String createWorker(
            @RequestParam String fullName,
            @RequestParam String email,
            @RequestParam String phoneNumber,
            @RequestParam String password,
            RedirectAttributes redirectAttributes) {

        try {
            registrationService.createWorker(
                    fullName,
                    email,
                    phoneNumber,
                    password
            );

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Worker account created successfully!"
            );

        } catch (IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    e.getMessage()
            );
        }

        return "redirect:/admin/workers/new";
    }
}