package com.mentalhealth.repository;

import com.mentalhealth.model.User;
import java.util.Optional;

/**
 * User Repository Interface
 * Person C: Implement this exactly
 * Person A: Call these methods from controllers
 */
public interface IUserRepository {
    
    // Create new user, returns user with generated ID
    User save(User user);
    
    // Find user by ID
    Optional<User> findById(int id);
    
    // Find user by username (for login)
    Optional<User> findByUsername(String username);
    
    // Update existing user
    boolean update(User user);
    
    // Check if username exists
    boolean existsByUsername(String username);
}