package com.mentalhealth.service;

import com.google.gson.Gson;
import com.mentalhealth.model.ApiResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * Service to communicate with Python Flask API
 * This is the ONLY place where Java talks to Python
 */
public class MLAPIService implements IMLService {
    
    private static final String BASE_URL = "http://localhost:5000";
    private static final int TIMEOUT_SECONDS = 30;
    
    private final HttpClient httpClient;
    private final Gson gson;
    
    public MLAPIService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.gson = new Gson();
    }
    
    @Override
    public boolean isHealthy() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/health"))
                    .GET()
                    .timeout(Duration.ofSeconds(5))
                    .build();
            
            HttpResponse<String> response = httpClient.send(
                    request, 
                    HttpResponse.BodyHandlers.ofString()
            );
            
            return response.statusCode() == 200;
            
        } catch (Exception e) {
            System.err.println("API health check failed: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public ApiResponse predict(String text) throws Exception {
        // Validate input
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Text cannot be empty");
        }
        
        // Create JSON body
        String jsonBody = gson.toJson(new PredictRequest(text.trim()));
        
        // Build HTTP POST request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/predict"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .timeout(Duration.ofSeconds(TIMEOUT_SECONDS))
                .build();
        
        // Send request to Python API
        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );
        
        // Check status
        if (response.statusCode() != 200) {
            throw new Exception("API error: " + response.statusCode());
        }
        
        // Parse JSON response into Java object
        ApiResponse apiResponse = gson.fromJson(
                response.body(), 
                ApiResponse.class
        );
        
        if (!apiResponse.isSuccess()) {
            throw new Exception("Prediction failed: " + apiResponse.getError());
        }
        
        return apiResponse;
    }
    
    @Override
    public String getBaseUrl() {
        return BASE_URL;
    }
    
    // Inner class for request body
    private static class PredictRequest {
        private final String text;
        
        PredictRequest(String text) {
            this.text = text;
        }
    }
}