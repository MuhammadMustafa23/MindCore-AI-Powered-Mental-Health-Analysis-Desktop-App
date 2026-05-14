package com.mentalhealth.model;

import java.util.List;
import java.util.Map;

/**
 * API Response from Flask - EXACT format Person B will return
 * Person A: Parse responses into this format
 */
public class ApiResponse {
    private boolean success;
    private String prediction;
    private double confidence;
    private String severity;
    private String color;
    private List<Prediction.TopPrediction> top_predictions;
    private Map<String, Double> probabilities;
    private String timestamp;
    private String error;  // Only present if success=false
    
    // Getters
    public boolean isSuccess() { return success; }
    public String getPrediction() { return prediction; }
    public double getConfidence() { return confidence; }
    public String getSeverity() { return severity; }
    public String getColor() { return color; }
    public List<Prediction.TopPrediction> getTopPredictions() { return top_predictions; }
    public Map<String, Double> getProbabilities() { return probabilities; }
    public String getTimestamp() { return timestamp; }
    public String getError() { return error; }
}