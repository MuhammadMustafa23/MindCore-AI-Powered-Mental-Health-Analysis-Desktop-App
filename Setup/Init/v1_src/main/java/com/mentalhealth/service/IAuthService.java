package com.mentalhealth.service;

import com.mentalhealth.model.User;
import java.util.Optional;

/**
 * Authentication Service Interface
 * Person C: Implement this
 * Person A: Call this for login/register
 */
public interface IAuthService {
    
    // Register new user, returns user if successful
    Optional<User> register(String username, String password, String name);
    
    // Login, returns user if credentials valid
    Optional<User> login(String username, String password);
    
    // Get currently logged in user
    Optional<User> getCurrentUser();
    
    // Logout current user
    void logout();
    
    // Check if user is logged in
    boolean isLoggedIn();
}