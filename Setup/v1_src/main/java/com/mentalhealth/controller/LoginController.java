package com.mentalhealth.controller;

import com.mentalhealth.model.User;
import com.mentalhealth.service.IAuthService;
import com.mentalhealth.service.MockAuthService;
import com.mentalhealth.Main;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

public class LoginController {
    
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    @FXML private Button loginButton;
    
    // Use interface - can be Mock or Real implementation
    private IAuthService authService;
    
    @FXML
    public void initialize() {
        // Week 1: Use mock
        // authService = new MockAuthService();
        
        // Week 2+: Use real (Person C's implementation)
        authService = ServiceFactory.getAuthService();
    }
    
    @FXML
    public void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        
        // Validation
        if (username.isEmpty() || password.isEmpty()) {
            showError("Please enter username and password");
            return;
        }
        
        // Attempt login
        Optional<User> user = authService.login(username, password);
        
        if (user.isPresent()) {
            // Login successful - navigate to main window
            try {
                Main.switchScene("MainWindow.fxml");
            } catch (Exception e) {
                showError("Failed to load main window");
            }
        } else {
            showError("Invalid username or password");
        }
    }
    
    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
}