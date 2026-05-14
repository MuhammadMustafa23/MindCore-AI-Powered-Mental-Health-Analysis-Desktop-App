package com.mentalhealth.controller;

import com.mentalhealth.Main;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

/**
 * Controller for Login Screen
 */
public class LoginController {
    
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    @FXML private Button loginButton;
    @FXML private Hyperlink registerLink;
    @FXML private ProgressIndicator loadingIndicator;
    
    @FXML
    public void initialize() {
        // Hide error label initially
        if (errorLabel != null) {
            errorLabel.setVisible(false);
        }
        if (loadingIndicator != null) {
            loadingIndicator.setVisible(false);
        }
        
        System.out.println("✅ LoginController initialized");
    }
    
    @FXML
    public void handleLogin() {
        // Clear previous error
        hideError();
        
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        
        // Validation
        if (username.isEmpty()) {
            showError("Please enter your username");
            usernameField.requestFocus();
            return;
        }
        
        if (password.isEmpty()) {
            showError("Please enter your password");
            passwordField.requestFocus();
            return;
        }
        
        // TODO: Implement actual authentication with AuthService
        // For now, accept any non-empty credentials for testing
        System.out.println("🔐 Login attempt: " + username);
        
        try {
            // Navigate to main window
            Main.switchScene("MainWindow.fxml");
            System.out.println("✅ Login successful, navigating to main window");
            
        } catch (Exception e) {
            showError("Failed to load main window: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @FXML
    public void handleRegisterLink() {
        try {
            Main.switchScene("RegisterScreen.fxml");
        } catch (Exception e) {
            showError("Failed to load registration screen");
            e.printStackTrace();
        }
    }
    
    private void showError(String message) {
        if (errorLabel != null) {
            errorLabel.setText(message);
            errorLabel.setVisible(true);
        }
    }
    
    private void hideError() {
        if (errorLabel != null) {
            errorLabel.setVisible(false);
        }
    }
}