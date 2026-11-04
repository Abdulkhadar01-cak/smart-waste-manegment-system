package com.smartwaste.service;

import com.smartwaste.entity.User;
import com.smartwaste.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registerCitizen(
            String fullName,
            String email,
            String phoneNumber,
            String password) {

        // Clean and normalize input
        fullName = fullName.trim();
        email = email.trim().toLowerCase();
        phoneNumber = phoneNumber.trim();

        // Check duplicate email
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException(
                "This email is already registered."
            );
        }

        // Encrypt password
        String encryptedPassword =
                passwordEncoder.encode(password);

        // Create citizen account
        User user = new User(
                fullName,
                email,
                phoneNumber,
                encryptedPassword,
                "CITIZEN"
        );

        // Save citizen in database
        userRepository.save(user);
    }
    @Transactional
public void createWorker(
        String fullName,
        String email,
        String phoneNumber,
        String password) {

    // Clean input
    fullName = fullName.trim();
    email = email.trim().toLowerCase();
    phoneNumber = phoneNumber.trim();

    // Check duplicate email
    if (userRepository.existsByEmail(email)) {
        throw new IllegalArgumentException(
                "This email is already registered."
        );
    }

    // Validate password
    if (password == null || password.isBlank()) {
        throw new IllegalArgumentException(
                "Password cannot be empty."
        );
    }

    // Encrypt password
    String encryptedPassword =
            passwordEncoder.encode(password);

    // Create worker account
    User worker = new User(
            fullName,
            email,
            phoneNumber,
            encryptedPassword,
            "WORKER"
    );

    // Save worker in database
    userRepository.save(worker);
}
}