package com.mentalhealth.service;

import com.mentalhealth.model.User;
import java.util.Optional;

/**
 * Mock Auth Service for UI testing
 * Person A uses this until Person C's real implementation is ready
 */
public class MockAuthService implements IAuthService {
    
    private User mockUser = null;
    
    @Override
    public Optional<User> register(String username, String password, String name) {
        // Simulate successful registration
        User user = new User(username, "hashed", name);
        user.setId(1);
        return Optional.of(user);
    }
    
    @Override
    public Optional<User> login(String username, String password) {
        // Accept any login for testing
        if (username != null && password != null) {
            mockUser = new User(username, "hashed", "Test User");
            mockUser.setId(1);
            return Optional.of(mockUser);
        }
        return Optional.empty();
    }
    
    @Override
    public Optional<User> getCurrentUser() {
        return Optional.ofNullable(mockUser);
    }
    
    @Override
    public void logout() {
        mockUser = null;
    }
    
    @Override
    public boolean isLoggedIn() {
        return mockUser != null;
    }
}