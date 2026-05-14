package com.mentalhealth.controller;

import com.mentalhealth.Main;
import javafx.fxml.FXML;

/**
 * Controller for Register Screen
 */
public class RegisterController {
    
    @FXML
    public void initialize() {
        System.out.println("✅ RegisterController initialized");
    }
    
    @FXML
    public void handleBackToLogin() {
        try {
            Main.switchScene("LoginScreen.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}