package com.mentalhealth.service;

import com.mentalhealth.model.ApiResponse;

/**
 * ML API Service Interface
 * Person B: Implement this
 * Person A/C: Call this for predictions
 */
public interface IMLService {
    
    // Check if API is available
    boolean isHealthy();
    
    // Get prediction for text
    ApiResponse predict(String text) throws Exception;
    
    // Get API base URL
    String getBaseUrl();
}