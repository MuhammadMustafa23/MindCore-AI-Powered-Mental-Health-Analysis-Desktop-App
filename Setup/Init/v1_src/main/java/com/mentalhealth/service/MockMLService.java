package com.mentalhealth.service;

import com.mentalhealth.model.ApiResponse;

/**
 * Mock ML Service for UI testing
 * Person A uses this until Person B's real implementation is ready
 */
public class MockMLService implements IMLService {
    
    @Override
    public boolean isHealthy() {
        return true;
    }
    
    @Override
    public ApiResponse predict(String text) {
        // Return fake prediction for UI testing
        ApiResponse response = new ApiResponse();
        // Use reflection or create setters for testing
        // This just demonstrates the concept
        return createMockResponse(text);
    }
    
    private ApiResponse createMockResponse(String text) {
        // Create a mock response based on keywords in text
        // This lets Person A test UI without real API
        String prediction = "Stress";
        double confidence = 0.75;
        
        if (text.toLowerCase().contains("happy")) {
            prediction = "Normal";
            confidence = 0.85;
        } else if (text.toLowerCase().contains("anxious")) {
            prediction = "Anxiety";
            confidence = 0.80;
        }
        
        // Return mock response (implement properly with setters)
        return new ApiResponse(); // Simplified
    }
    
    @Override
    public String getBaseUrl() {
        return "http://localhost:5000";
    }
}