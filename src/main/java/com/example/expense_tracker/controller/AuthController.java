package com.example.expense_tracker.controller;

import com.example.expense_tracker.model.User;
import com.example.expense_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // Register
    @PostMapping("/register")
    public Map<String, String> register(@RequestBody User user) {
        Map<String, String> response = new HashMap<>();
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            response.put("message", "Email already exists!");
            return response;
        }
        userRepository.save(user);
        response.put("message", "Registration successful!");
        return response;
    }

    // Login
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {
        Map<String, String> response = new HashMap<>();
        Optional<User> found = userRepository.findByEmail(user.getEmail());
        if (found.isPresent() && found.get().getPassword().equals(user.getPassword())) {
            response.put("message", "Login successful!");
            response.put("userId", found.get().getId().toString());
            response.put("name", found.get().getName());
        } else {
            response.put("message", "Invalid email or password!");
        }
        return response;
    }

    // Reset Password
    @PostMapping("/reset-password")
    public Map<String, String> resetPassword(@RequestBody User user) {
        Map<String, String> response = new HashMap<>();
        Optional<User> found = userRepository.findByEmail(user.getEmail());
        if (found.isPresent()) {
            User existingUser = found.get();
            existingUser.setPassword(user.getPassword());
            userRepository.save(existingUser);
            response.put("message", "Password reset successful!");
        } else {
            response.put("message", "Email not found!");
        }
        return response;
    }
}