package com.smartwaste.controller;

import com.smartwaste.entity.User;
import com.smartwaste.repository.UserRepository;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CitizenProfileController {

    private final UserRepository userRepository;

    public CitizenProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/citizen/profile")
    public String showProfile(
            Authentication authentication,
            Model model) {

        User citizen = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new IllegalArgumentException("Citizen not found."));

        model.addAttribute("citizen", citizen);

        return "citizen/profile";
    }
}