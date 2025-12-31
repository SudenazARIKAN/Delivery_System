package com.delivery.authservice.service;

import org.springframework.stereotype.Service;

import com.delivery.authservice.dto.AuthResponse;
import com.delivery.authservice.dto.LoginRequest;
import com.delivery.authservice.dto.RegisterRequest;
import com.delivery.authservice.model.Role;
import com.delivery.authservice.model.User;
import com.delivery.authservice.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Simple password check (plain text for now - no security)
        if (!request.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // Return success message instead of JWT token
        return new AuthResponse("Login successful for user: " + user.getUsername());
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword()); // Plain text password (no encoding)
        user.setEmail(request.getEmail());
        user.setRole(request.getRole() != null ? request.getRole() : Role.USER);

        userRepository.save(user);

        return new AuthResponse("User registered successfully: " + user.getUsername());
    }
}
