package com.mentalhealth.model;

import java.util.List;
import java.util.Map;

/**
 * Prediction model - AGREED BY ALL TEAM MEMBERS
 */
public class Prediction {
    private int id;
    private int userId;
    private String inputText;
    private String prediction;
    private double confidence;
    private String severity;
    private String color;
    private List<TopPrediction> topPredictions;
    private String createdAt;
    
    // Default constructor
    public Prediction() {}
    
    // Constructor for saving to database
    public Prediction(int userId, String inputText, String prediction, 
                      double confidence, String severity) {
        this.userId = userId;
        this.inputText = inputText;
        this.prediction = prediction;
        this.confidence = confidence;
        this.severity = severity;
    }
    
    // Inner class for top predictions
    public static class TopPrediction {
        private String className;
        private double probability;
        private String severity;
        private String color;
        
        public String getClassName() { return className; }
        public void setClassName(String className) { this.className = className; }
        
        public double getProbability() { return probability; }
        public void setProbability(double probability) { this.probability = probability; }
        
        public String getSeverity() { return severity; }
        public void setSeverity(String severity) { this.severity = severity; }
        
        public String getColor() { return color; }
        public void setColor(String color) { this.color = color; }
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public String getInputText() { return inputText; }
    public void setInputText(String inputText) { this.inputText = inputText; }
    
    public String getPrediction() { return prediction; }
    public void setPrediction(String prediction) { this.prediction = prediction; }
    
    public double getConfidence() { return confidence; }
    public void setConfidence(double confidence) { this.confidence = confidence; }
    
    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }
    
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    
    public List<TopPrediction> getTopPredictions() { return topPredictions; }
    public void setTopPredictions(List<TopPrediction> topPredictions) { 
        this.topPredictions = topPredictions; 
    }
    
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}