# AI-Based Mental Health Analysis Project
## Complete Analysis & Implementation Guide for 3-Person Team (1 Month)

**Analysis Date:** January 29, 2026  
**Model Status:** ✅ Trained & Ready (85% Accuracy)  
**Team Size:** 3 Members  
**Timeline:** 4 Weeks

---

## 📊 Executive Summary

### Overall Verdict: **9/10 Feasibility - Highly Achievable** ✅

With your ML model already trained at 85% accuracy, you've eliminated the biggest risk and time constraint. This project is now **highly achievable** with proper planning.

| Metric | Value | Status |
|--------|-------|--------|
| **Feasibility Rating** | 9/10 | ✅ Excellent |
| **Complexity** | Moderate-High | ⚠️ Manageable |
| **Time Pressure** | Low | ✅ Comfortable |
| **Success Probability** | 85-90% | ✅ Very High |
| **Risk Level** | Low | ✅ Controlled |

---

## 🎯 Your Model Analysis

### Model Specifications

**Algorithm:** Logistic Regression (Multi-class Classification)

**Performance:**
- Accuracy: **85%**
- Classes: **7 mental health conditions**
- Vocabulary: **5,000 TF-IDF features**
- N-gram Range: **(1, 2)** - Unigrams and Bigrams
- File Sizes: Model (275KB), Vectorizer (184KB)

### Mental Health Classes Detected

1. **Anxiety** - Moderate Severity
2. **Bipolar** - High Severity  
3. **Depression** - High Severity
4. **Normal** - Low Severity
5. **Personality Disorder** - High Severity
6. **Stress** - Moderate Severity
7. **Suicidal** - Critical Severity

### Top Predictive Keywords by Class

**Anxiety:**
- anxiety, restless, anxious, worried, nervous, worry
- Strong indicators: "health anxiety", "symptoms"

**Stress:**
- stress, stressed, ptsd, abuse, nightmares, stressful
- Context: Work/life pressure, chronic stress

**Depression:**
- depression, depressed, "do not", "cannot", negative contractions
- Indicators: Hopelessness, inability, negativity

**Suicidal (Critical):**
- suicide, suicidal, die, kill, myself, "do not", "cannot"
- **Requires immediate attention in app**

**Bipolar:**
- bipolar, manic, mania, episode, meds, lithium
- Medical terminology strong predictor

**Normal:**
- yes, twitter, morning, busy, routine daily activities
- Neutral/positive content

**Personality Disorder:**
- avpd, social, interaction, avoid, shame, people
- Social avoidance patterns

---

## ⏱️ Revised Timeline Analysis

### Time Already Saved ✅

| Component | Original Estimate | Status | Time Saved |
|-----------|------------------|--------|------------|
| Dataset Collection | 3-4 days | ✅ Done | 3-4 days |
| Data Preprocessing | 2-3 days | ✅ Done | 2-3 days |
| Model Training | 3-5 days | ✅ Done | 3-5 days |
| Model Validation | 2 days | ✅ Done | 2 days |
| Hyperparameter Tuning | 1-2 days | ✅ Done | 1-2 days |
| **TOTAL SAVED** | **11-16 days** | ✅ | **~2 weeks** |

### Remaining Work Breakdown

| Feature | Complexity | Est. Days | Priority |
|---------|-----------|-----------|----------|
| Flask API Service | Low | 1-2 days | **CRITICAL** |
| User Profile System | Low | 2-3 days | **MUST HAVE** |
| JavaFX UI Foundation | Medium | 4-5 days | **MUST HAVE** |
| REST Integration | Low-Medium | 2-3 days | **MUST HAVE** |
| Mood Tracking | Medium | 3-4 days | **SHOULD HAVE** |
| Analytics Dashboard | Medium | 3-4 days | **SHOULD HAVE** |
| Recommendations | Low | 2-3 days | **SHOULD HAVE** |
| OCR Integration | Medium-High | 4-5 days | **NICE TO HAVE** |
| Reminder System | Low | 2 days | **NICE TO HAVE** |
| Data Encryption | Medium | 2-3 days | **SHOULD HAVE** |
| **TOTAL** | | **25-36 days** | |

**Available Resources:** 60 person-days (3 people × 20 working days)  
**Buffer Time:** ~25-35 days (40-58% buffer) ✅

---

## 👥 Optimized Team Structure & Roles

### Person A: Frontend/UI Developer
**Primary Responsibilities:**
- JavaFX application architecture
- User interface design & implementation
- User profile management screens
- Mood tracking dashboard with charts
- Visual design and UX

**Skills Required:**
- JavaFX (Scene Builder optional)
- FXML layouts
- CSS styling
- Event handling
- Charts (LineChart, BarChart)

**Week-by-Week Tasks:**
- Week 1: Project setup, login/profile UI
- Week 2: Prediction display UI, charts setup
- Week 3: Mood tracking dashboard, analytics views
- Week 4: Polish, animations, final UX

---

### Person B: Backend/API Developer
**Primary Responsibilities:**
- Flask API development
- Model integration (loading PKL files)
- API endpoint design
- Rule-based recommendation engine
- API documentation

**Skills Required:**
- Python (Flask/FastAPI)
- REST API design
- joblib for model loading
- Error handling
- Logging

**Week-by-Week Tasks:**
- Week 1: Flask API with prediction endpoint
- Week 2: Batch predictions, recommendations API
- Week 3: OCR integration (Tesseract), optimization
- Week 4: API testing, documentation, deployment

---

### Person C: Integration/Database Specialist
**Primary Responsibilities:**
- SQLite database design
- Java HTTP client for API calls
- Data persistence layer
- Repository pattern implementation
- Integration testing

**Skills Required:**
- Java (Core + JDBC)
- SQLite database
- HTTP clients (java.net.http)
- Design patterns (Repository, Singleton)
- Testing (JUnit)

**Week-by-Week Tasks:**
- Week 1: Database schema, HTTP client setup
- Week 2: End-to-end integration, data persistence
- Week 3: Encryption, analytics data layer
- Week 4: Testing, bug fixes, documentation

---

## 📅 Detailed 4-Week Development Plan

### Week 1: Foundation & Core Integration (Days 1-5)

**Monday - Setup & Architecture**
- All: Project setup, Git repository, development environment
- Person A: JavaFX project structure, main window
- Person B: Flask API skeleton, model loading
- Person C: Database schema design

**Tuesday - Database & API**
- Person A: User profile UI design
- Person B: `/predict` endpoint implementation
- Person C: SQLite database creation, User table

**Wednesday - API Integration**
- Person A: Profile form implementation
- Person B: API testing with Postman, error handling
- Person C: HTTP client service in Java

**Thursday - First Integration**
- Person A: Connect profile UI to database
- Person B: Logging, health check endpoint
- Person C: Complete User Repository pattern

**Friday - Integration Testing**
- All: First end-to-end test (UI → DB → API → Model → Response)
- Code review and Git merge
- **Deliverable:** Working API, basic UI, database ready

---

### Week 2: Core Features (Days 6-10)

**Monday - Prediction UI**
- Person A: Text input screen, loading indicators
- Person B: Batch prediction endpoint
- Person C: Prediction persistence in database

**Tuesday - Results Display**
- Person A: Prediction result display with colors
- Person B: Add confidence scores, top 3 predictions
- Person C: Prediction history retrieval

**Wednesday - Recommendations**
- Person A: Recommendation display UI
- Person B: Rule-based recommendation engine
- Person C: Integration layer for recommendations

**Thursday - Mood Tracking Setup**
- Person A: Mood entry form
- Person B: Analytics helper functions
- Person C: MoodEntry table and repository

**Friday - Weekly Review**
- All: Integration testing, bug fixes
- Demo of working prediction flow
- **Deliverable:** Complete prediction + results + recommendations

---

### Week 3: Advanced Features (Days 11-15)

**Monday - Dashboard Charts**
- Person A: LineChart for mood trends over time
- Person B: Begin OCR integration (Tesseract setup)
- Person C: Analytics calculation functions

**Tuesday - Analytics**
- Person A: Additional charts (weekly patterns, stress frequency)
- Person B: OCR text extraction from images
- Person C: Query optimizations for analytics

**Wednesday - OCR Integration**
- Person A: Image upload UI, file picker
- Person B: Connect OCR to prediction pipeline
- Person C: File storage management

**Thursday - Additional Features**
- Person A: Reminder system UI
- Person B: Scheduled task implementation
- Person C: AES encryption for sensitive data

**Friday - Feature Freeze**
- All: Complete all planned features
- Integration testing
- **Deliverable:** All 8 features working

---

### Week 4: Polish, Testing & Documentation (Days 16-20)

**Monday - Testing**
- Person A: UI/UX testing, edge cases
- Person B: API stress testing, error scenarios
- Person C: Unit tests for critical components

**Tuesday - Bug Fixing**
- All: Address bugs from testing
- Performance optimization
- Code cleanup

**Wednesday - Documentation**
- Person A: User manual, screenshots
- Person B: API documentation, setup guide
- Person C: Technical documentation, architecture diagrams

**Thursday - Presentation Prep**
- All: Demo video recording
- Presentation slides
- Practice demo

**Friday - Final Submission**
- All: Final testing, submission package
- **Deliverable:** Complete, tested, documented project

---

## 🏗️ Technical Architecture

### System Architecture Diagram

```
┌─────────────────────────────────────────────────────┐
│                  JavaFX Desktop UI                  │
│  ┌──────────┐  ┌──────────┐  ┌─────────────────┐  │
│  │ Profile  │  │Prediction│  │  Mood Tracking  │  │
│  │  Screen  │  │  Screen  │  │   Dashboard     │  │
│  └────┬─────┘  └────┬─────┘  └────────┬────────┘  │
└───────┼─────────────┼─────────────────┼────────────┘
        │             │                 │
        ▼             ▼                 ▼
┌─────────────────────────────────────────────────────┐
│              Java Business Layer                    │
│  ┌──────────────┐  ┌────────────┐  ┌──────────┐   │
│  │  Repository  │  │ API Client │  │ Analytics│   │
│  │   Pattern    │  │  Service   │  │  Engine  │   │
│  └──────┬───────┘  └─────┬──────┘  └────┬─────┘   │
└─────────┼─────────────────┼──────────────┼─────────┘
          │                 │              │
          ▼                 ▼              │
    ┌──────────┐      ┌──────────────┐    │
    │  SQLite  │      │  HTTP/REST   │    │
    │ Database │      │     API      │    │
    └──────────┘      └──────┬───────┘    │
                             │            │
                             ▼            │
                 ┌─────────────────────┐  │
                 │  Flask API Service  │  │
                 │  ┌───────────────┐  │  │
                 │  │ Load PKL files│  │  │
                 │  ├───────────────┤  │  │
                 │  │ ML Vectorizer │  │  │
                 │  ├───────────────┤  │  │
                 │  │  ML Model     │  │  │
                 │  │  (Logistic    │  │  │
                 │  │  Regression)  │  │  │
                 │  └───────────────┘  │  │
                 └─────────────────────┘  │
                             │            │
                             ▼            ▼
                    JSON Response    Analytics Data
                    (Prediction +    (Aggregations)
                     Confidence)
```

### Database Schema

```sql
-- Users table
CREATE TABLE users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    age INTEGER,
    goal TEXT,
    baseline_stress INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Predictions table
CREATE TABLE predictions (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL,
    input_text TEXT NOT NULL,
    prediction TEXT NOT NULL,
    confidence REAL NOT NULL,
    severity TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Mood entries table
CREATE TABLE mood_entries (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL,
    mood_score INTEGER NOT NULL,
    notes TEXT,
    entry_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Reminders table
CREATE TABLE reminders (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL,
    title TEXT NOT NULL,
    description TEXT,
    reminder_time TIME NOT NULL,
    is_active BOOLEAN DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

---

## 💻 Implementation Details

### 1. Flask API (Already Created ✅)

I've created a production-ready Flask API (`flask_api_service.py`) with:

**Features:**
- ✅ Model loading with joblib
- ✅ `/predict` endpoint with full response
- ✅ `/batch_predict` for multiple texts
- ✅ `/health` and `/model_info` endpoints
- ✅ Error handling and logging
- ✅ CORS enabled for JavaFX
- ✅ Color codes and severity levels
- ✅ Confidence scores and probabilities

**Key Response Format:**
```json
{
  "success": true,
  "prediction": "Stress",
  "confidence": 0.7834,
  "severity": "Moderate",
  "color": "#FF9800",
  "top_predictions": [
    {"class": "Stress", "probability": 0.7834, "severity": "Moderate"},
    {"class": "Anxiety", "probability": 0.1312, "severity": "Moderate"},
    {"class": "Normal", "probability": 0.0521, "severity": "Low"}
  ],
  "probabilities": {
    "Anxiety": 0.1312,
    "Bipolar": 0.0012,
    ...
  },
  "timestamp": "2026-01-29T17:00:00"
}
```

---

### 2. Java API Client Service

```java
package com.mentalhealth.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mentalhealth.model.PredictionRequest;
import com.mentalhealth.model.PredictionResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Singleton service for communicating with the Flask ML API
 */
public class MLAPIService {
    
    private static final Logger LOGGER = Logger.getLogger(MLAPIService.class.getName());
    private static MLAPIService instance;
    
    private final HttpClient httpClient;
    private final Gson gson;
    private final String baseUrl;
    
    // Private constructor for singleton
    private MLAPIService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        this.baseUrl = "http://localhost:5000";
    }
    
    /**
     * Get singleton instance
     */
    public static synchronized MLAPIService getInstance() {
        if (instance == null) {
            instance = new MLAPIService();
        }
        return instance;
    }
    
    /**
     * Check if API is healthy
     */
    public boolean isHealthy() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/health"))
                    .GET()
                    .timeout(Duration.ofSeconds(5))
                    .build();
            
            HttpResponse<String> response = httpClient.send(
                    request, 
                    HttpResponse.BodyHandlers.ofString()
            );
            
            return response.statusCode() == 200;
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Health check failed", e);
            return false;
        }
    }
    
    /**
     * Get mental health prediction for text
     * 
     * @param text The text to analyze
     * @return PredictionResponse with prediction details
     * @throws Exception if API call fails
     */
    public PredictionResponse predict(String text) throws Exception {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Text cannot be null or empty");
        }
        
        // Create request body
        PredictionRequest requestBody = new PredictionRequest(text, true, 3);
        String jsonBody = gson.toJson(requestBody);
        
        LOGGER.info("Sending prediction request for text: " + text.substring(0, Math.min(50, text.length())));
        
        // Build HTTP POST request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/predict"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .timeout(Duration.ofSeconds(30))
                .build();
        
        // Send request
        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );
        
        // Parse response
        if (response.statusCode() == 200) {
            PredictionResponse predictionResponse = gson.fromJson(
                    response.body(), 
                    PredictionResponse.class
            );
            
            LOGGER.info(String.format("Prediction successful: %s (%.2f%% confidence)",
                    predictionResponse.getPrediction(),
                    predictionResponse.getConfidence() * 100));
            
            return predictionResponse;
            
        } else {
            String errorMsg = String.format("API returned status %d: %s",
                    response.statusCode(), response.body());
            LOGGER.severe(errorMsg);
            throw new Exception(errorMsg);
        }
    }
}
```

---

### 3. Model Classes

```java
package com.mentalhealth.model;

import java.util.List;
import java.util.Map;

/**
 * Request model for prediction API
 */
public class PredictionRequest {
    private String text;
    private boolean include_probabilities;
    private int top_n;
    
    public PredictionRequest(String text, boolean includeProbabilities, int topN) {
        this.text = text;
        this.include_probabilities = includeProbabilities;
        this.top_n = topN;
    }
    
    // Getters and setters
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public boolean isIncludeProbabilities() { return include_probabilities; }
    public void setIncludeProbabilities(boolean include_probabilities) { 
        this.include_probabilities = include_probabilities; 
    }
    public int getTopN() { return top_n; }
    public void setTopN(int top_n) { this.top_n = top_n; }
}

/**
 * Response model from prediction API
 */
public class PredictionResponse {
    private boolean success;
    private String prediction;
    private double confidence;
    private String severity;
    private String color;
    private List<TopPrediction> top_predictions;
    private Map<String, Double> probabilities;
    private String timestamp;
    
    // Getters and setters
    public boolean isSuccess() { return success; }
    public String getPrediction() { return prediction; }
    public double getConfidence() { return confidence; }
    public String getSeverity() { return severity; }
    public String getColor() { return color; }
    public List<TopPrediction> getTopPredictions() { return top_predictions; }
    public Map<String, Double> getProbabilities() { return probabilities; }
    public String getTimestamp() { return timestamp; }
    
    /**
     * Inner class for top predictions
     */
    public static class TopPrediction {
        private String className;  // maps to "class" in JSON
        private double probability;
        private String severity;
        private String color;
        
        // Getters
        public String getClassName() { return className; }
        public double getProbability() { return probability; }
        public String getSeverity() { return severity; }
        public String getColor() { return color; }
    }
}
```

---

### 4. JavaFX Controller Example

```java
package com.mentalhealth.controller;

import com.mentalhealth.model.PredictionResponse;
import com.mentalhealth.service.MLAPIService;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.time.format.DateTimeFormatter;

public class PredictionController {
    
    @FXML private TextArea inputTextArea;
    @FXML private Button analyzeButton;
    @FXML private Label resultLabel;
    @FXML private Label confidenceLabel;
    @FXML private ProgressBar confidenceBar;
    @FXML private VBox topPredictionsBox;
    @FXML private VBox recommendationsBox;
    @FXML private ProgressIndicator loadingIndicator;
    
    private MLAPIService apiService;
    private RecommendationEngine recommendationEngine;
    
    @FXML
    public void initialize() {
        apiService = MLAPIService.getInstance();
        recommendationEngine = new RecommendationEngine();
        
        // Check API health on startup
        checkAPIHealth();
    }
    
    private void checkAPIHealth() {
        Task<Boolean> healthTask = new Task<>() {
            @Override
            protected Boolean call() {
                return apiService.isHealthy();
            }
        };
        
        healthTask.setOnSucceeded(e -> {
            if (!healthTask.getValue()) {
                showError("API Service Unavailable",
                        "Cannot connect to ML API. Please ensure Flask server is running.");
            }
        });
        
        new Thread(healthTask).start();
    }
    
    @FXML
    public void handleAnalyzeButton() {
        String text = inputTextArea.getText().trim();
        
        // Validation
        if (text.isEmpty()) {
            showWarning("No Text Entered", "Please enter some text to analyze.");
            return;
        }
        
        if (text.length() < 10) {
            showWarning("Text Too Short", 
                    "Please enter at least 10 characters for accurate analysis.");
            return;
        }
        
        // Show loading
        setLoading(true);
        clearResults();
        
        // Call API in background
        Task<PredictionResponse> predictionTask = new Task<>() {
            @Override
            protected PredictionResponse call() throws Exception {
                return apiService.predict(text);
            }
        };
        
        predictionTask.setOnSucceeded(e -> {
            setLoading(false);
            PredictionResponse response = predictionTask.getValue();
            displayResults(response);
            savePrediction(text, response);
        });
        
        predictionTask.setOnFailed(e -> {
            setLoading(false);
            Throwable exception = predictionTask.getException();
            showError("Prediction Failed", 
                    "An error occurred: " + exception.getMessage());
        });
        
        new Thread(predictionTask).start();
    }
    
    private void displayResults(PredictionResponse response) {
        // Main prediction
        resultLabel.setText(response.getPrediction());
        resultLabel.setStyle("-fx-text-fill: " + response.getColor() + ";");
        resultLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        
        // Confidence
        double confidence = response.getConfidence();
        confidenceLabel.setText(String.format("%.1f%% Confident", confidence * 100));
        confidenceBar.setProgress(confidence);
        
        // Set progress bar color based on confidence
        if (confidence > 0.7) {
            confidenceBar.setStyle("-fx-accent: green;");
        } else if (confidence > 0.5) {
            confidenceBar.setStyle("-fx-accent: orange;");
        } else {
            confidenceBar.setStyle("-fx-accent: red;");
        }
        
        // Display top 3 predictions
        topPredictionsBox.getChildren().clear();
        for (PredictionResponse.TopPrediction topPred : response.getTopPredictions()) {
            Label predLabel = new Label(String.format("%s - %.1f%% (%s severity)",
                    topPred.getClassName(),
                    topPred.getProbability() * 100,
                    topPred.getSeverity()));
            predLabel.setStyle("-fx-text-fill: " + topPred.getColor() + ";");
            topPredictionsBox.getChildren().add(predLabel);
        }
        
        // Get and display recommendations
        displayRecommendations(response.getPrediction(), response.getSeverity());
    }
    
    private void displayRecommendations(String prediction, String severity) {
        recommendationsBox.getChildren().clear();
        
        var recommendations = recommendationEngine.getRecommendations(
                prediction, severity);
        
        for (String rec : recommendations) {
            Label recLabel = new Label("• " + rec);
            recLabel.setWrapText(true);
            recLabel.setMaxWidth(400);
            recommendationsBox.getChildren().add(recLabel);
        }
    }
    
    private void savePrediction(String text, PredictionResponse response) {
        // Save to database via repository
        // Implementation depends on your repository pattern
    }
    
    private void setLoading(boolean loading) {
        Platform.runLater(() -> {
            loadingIndicator.setVisible(loading);
            analyzeButton.setDisable(loading);
            inputTextArea.setDisable(loading);
        });
    }
    
    private void clearResults() {
        resultLabel.setText("");
        confidenceLabel.setText("");
        confidenceBar.setProgress(0);
        topPredictionsBox.getChildren().clear();
        recommendationsBox.getChildren().clear();
    }
    
    private void showError(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
    
    private void showWarning(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
}
```

---

### 5. Recommendation Engine

```java
package com.mentalhealth.engine;

import java.util.ArrayList;
import java.util.List;

public class RecommendationEngine {
    
    public List<String> getRecommendations(String prediction, String severity) {
        List<String> recommendations = new ArrayList<>();
        
        switch (prediction) {
            case "Anxiety":
                recommendations.add("Practice deep breathing: Inhale for 4 counts, hold for 4, exhale for 4");
                recommendations.add("Try progressive muscle relaxation exercises");
                recommendations.add("Limit caffeine intake, especially in the afternoon");
                recommendations.add("Consider talking to a mental health professional");
                break;
                
            case "Stress":
                recommendations.add("Take regular breaks during work or study (Pomodoro technique)");
                recommendations.add("Engage in physical exercise for at least 30 minutes");
                recommendations.add("Practice time management and prioritize tasks");
                recommendations.add("Get 7-9 hours of quality sleep");
                break;
                
            case "Depression":
                recommendations.add("⚠️ Please consider reaching out to a mental health professional");
                recommendations.add("Maintain social connections with friends and family");
                recommendations.add("Establish a daily routine with small, achievable goals");
                recommendations.add("Engage in activities you used to enjoy, even if briefly");
                recommendations.add("Crisis Hotline: 988 (US) or local mental health services");
                break;
                
            case "Suicidal":
                recommendations.add("🆘 IMMEDIATE ACTION REQUIRED");
                recommendations.add("National Suicide Prevention Lifeline: 988 (US)");
                recommendations.add("Crisis Text Line: Text HOME to 741741");
                recommendations.add("Please reach out to emergency services or a trusted person NOW");
                recommendations.add("You are not alone. Professional help is available 24/7");
                break;
                
            case "Bipolar":
                recommendations.add("⚠️ Consult with a psychiatrist for proper medication management");
                recommendations.add("Track your mood daily to identify patterns");
                recommendations.add("Maintain a regular sleep schedule");
                recommendations.add("Avoid alcohol and recreational drugs");
                break;
                
            case "Personality disorder":
                recommendations.add("⚠️ Professional therapy (DBT/CBT) is highly recommended");
                recommendations.add("Practice self-compassion and avoid self-judgment");
                recommendations.add("Build a support network");
                recommendations.add("Learn and practice emotional regulation techniques");
                break;
                
            case "Normal":
                recommendations.add("Continue maintaining your positive mental health!");
                recommendations.add("Regular exercise and good sleep are important");
                recommendations.add("Stay connected with loved ones");
                recommendations.add("Practice gratitude journaling");
                break;
        }
        
        // Add severity-based recommendations
        if ("Critical".equals(severity) || "High".equals(severity)) {
            recommendations.add(0, "⚠️ HIGH PRIORITY: Consider professional mental health support");
        }
        
        return recommendations;
    }
}
```

---

## 🎨 UI/UX Design Guidelines

### Color Scheme Based on Severity

```java
public enum SeverityColors {
    NORMAL("#4CAF50"),        // Green - Safe
    MODERATE("#FF9800"),      // Orange - Caution
    HIGH("#F44336"),          // Red - Warning
    CRITICAL("#9C27B0");      // Purple - Emergency
    
    private final String hexColor;
    
    SeverityColors(String hexColor) {
        this.hexColor = hexColor;
    }
    
    public String getHex() {
        return hexColor;
    }
}
```

### Dashboard Layout Recommendations

1. **Main Prediction Screen:**
   - Large, centered result label
   - Visual confidence indicator (progress bar + percentage)
   - Top 3 predictions in smaller cards
   - Recommendations section below

2. **Mood Tracking Dashboard:**
   - Line chart showing 7-day/30-day trends
   - Quick mood entry button (1-10 scale)
   - Weekly average display
   - Pattern insights ("Most stressed on Mondays")

3. **Analytics View:**
   - Bar chart: Frequency of each condition
   - Pie chart: Distribution of mood scores
   - Calendar heatmap: Daily mood visualization
   - Export button for PDF reports

---

## ⚠️ Critical Implementation Notes

### 1. Important: Use `joblib`, not `pickle`

Your model files **must be loaded with joblib**, not standard pickle:

```python
# ✅ CORRECT
import joblib
model = joblib.load('mental_health_model.pkl')

# ❌ WRONG
import pickle
with open('mental_health_model.pkl', 'rb') as f:
    model = pickle.load(f)  # Will fail!
```

### 2. Handling Suicidal Predictions

**CRITICAL:** When "Suicidal" is predicted with >50% confidence:

```java
if ("Suicidal".equals(prediction) && confidence > 0.5) {
    // Show emergency alert
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("CRISIS DETECTED");
    alert.setHeaderText("Immediate Help Available");
    alert.setContentText(
        "If you're in crisis:\n\n" +
        "🆘 Call 988 (Suicide & Crisis Lifeline)\n" +
        "📱 Text HOME to 741741 (Crisis Text Line)\n\n" +
        "You are not alone. Help is available 24/7."
    );
    alert.showAndWait();
}
```

### 3. Model Confidence Interpretation

- **80-100%**: Very reliable prediction
- **60-79%**: Good confidence, likely accurate
- **40-59%**: Moderate confidence, use caution
- **<40%**: Low confidence, may need more context

### 4. Text Length Recommendations

```java
// Minimum text length for accurate predictions
private static final int MIN_TEXT_LENGTH = 10;
private static final int RECOMMENDED_TEXT_LENGTH = 50;

if (text.length() < MIN_TEXT_LENGTH) {
    showWarning("Text too short. Please provide more detail for accurate analysis.");
} else if (text.length() < RECOMMENDED_TEXT_LENGTH) {
    showInfo("Tip: Longer descriptions (50+ characters) improve accuracy.");
}
```

---

## 📋 Testing Strategy

### Unit Testing Checklist

**Java Components:**
- ✅ APIService connectivity test
- ✅ Database CRUD operations
- ✅ Repository pattern methods
- ✅ Recommendation engine logic
- ✅ Input validation

**Python API:**
- ✅ Model loading test
- ✅ Prediction endpoint with various inputs
- ✅ Error handling (empty text, null values)
- ✅ Batch prediction
- ✅ Response format validation

### Integration Testing

```java
@Test
public void testEndToEndPrediction() {
    // 1. Create test user
    User user = new User("Test User", 25, "Reduce stress", 5);
    userRepository.save(user);
    
    // 2. Call API
    String testText = "I feel anxious about upcoming exams";
    PredictionResponse response = apiService.predict(testText);
    
    // 3. Verify response
    assertNotNull(response);
    assertEquals("Anxiety", response.getPrediction());
    assertTrue(response.getConfidence() > 0.5);
    
    // 4. Save to database
    predictionRepository.save(user.getId(), testText, response);
    
    // 5. Verify persistence
    List<Prediction> history = predictionRepository.findByUserId(user.getId());
    assertEquals(1, history.size());
}
```

### Performance Testing

**Expected Response Times:**
- Single prediction: <500ms
- Batch prediction (10 texts): <2000ms
- Database query: <100ms
- UI responsiveness: <200ms

---

## 🚀 Deployment & Submission

### Project Structure

```
mental-health-analysis/
├── python-api/
│   ├── mental_health_model.pkl
│   ├── tfidf_vectorizer.pkl
│   ├── app.py (Flask service)
│   ├── requirements.txt
│   └── README.md
│
├── java-application/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/mentalhealth/
│   │   │   │   ├── controller/
│   │   │   │   ├── model/
│   │   │   │   ├── service/
│   │   │   │   ├── repository/
│   │   │   │   └── Main.java
│   │   │   └── resources/
│   │   │       ├── fxml/
│   │   │       ├── css/
│   │   │       └── database/
│   │   └── test/
│   ├── pom.xml (Maven) or build.gradle
│   └── README.md
│
├── documentation/
│   ├── User_Manual.pdf
│   ├── Technical_Documentation.pdf
│   ├── API_Documentation.pdf
│   └── Architecture_Diagram.png
│
├── demo/
│   ├── demo_video.mp4
│   └── screenshots/
│
└── README.md (Main project README)
```

### requirements.txt for Python API

```txt
Flask==3.0.0
Flask-CORS==4.0.0
scikit-learn==1.6.1
joblib==1.3.2
numpy==1.24.3
```

### Maven Dependencies for Java

```xml
<dependencies>
    <!-- JavaFX -->
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-controls</artifactId>
        <version>21</version>
    </dependency>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-fxml</artifactId>
        <version>21</version>
    </dependency>
    
    <!-- Gson for JSON -->
    <dependency>
        <groupId>com.google.code.gson</groupId>
        <artifactId>gson</artifactId>
        <version>2.10.1</version>
    </dependency>
    
    <!-- SQLite JDBC -->
    <dependency>
        <groupId>org.xerial</groupId>
        <artifactId>sqlite-jdbc</artifactId>
        <version>3.44.1.0</version>
    </dependency>
    
    <!-- Tesseract OCR (Optional) -->
    <dependency>
        <groupId>net.sourceforge.tess4j</groupId>
        <artifactId>tess4j</artifactId>
        <version>5.9.0</version>
    </dependency>
    
    <!-- JUnit for testing -->
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>5.10.1</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

---

## 📚 Documentation Checklist

### User Manual Must Include:
1. ✅ Installation instructions (Java, Python, dependencies)
2. ✅ How to start Flask API
3. ✅ How to run Java application
4. ✅ Feature walkthrough with screenshots
5. ✅ Troubleshooting section
6. ✅ Crisis resources and disclaimers

### Technical Documentation Must Include:
1. ✅ System architecture diagram
2. ✅ Database schema
3. ✅ API endpoints and request/response formats
4. ✅ Design patterns used
5. ✅ Class diagrams
6. ✅ Sequence diagrams for key flows

### Code Documentation:
1. ✅ JavaDoc comments for all public methods
2. ✅ Python docstrings for all functions
3. ✅ Inline comments for complex logic
4. ✅ README files in each directory

---

## 🎯 Success Criteria

### Minimum Viable Product (MVP)
To pass the project, you MUST have:

1. ✅ Working Flask API serving predictions
2. ✅ JavaFX UI with at least 3 screens
3. ✅ End-to-end integration (UI → API → Model → Response)
4. ✅ User profile system
5. ✅ Database persistence
6. ✅ Basic documentation

### Excellent Project (90%+)
To achieve excellent grade:

1. ✅ All 8 features fully implemented
2. ✅ Professional UI/UX design
3. ✅ Comprehensive error handling
4. ✅ Unit tests (60%+ coverage)
5. ✅ Detailed documentation
6. ✅ Demo video showcasing all features
7. ✅ Crisis handling for suicidal predictions
8. ✅ Data encryption implemented

---

## 🎓 Learning Outcomes

This project demonstrates mastery of:

1. **Software Design Patterns**: MVC, Repository, Singleton
2. **API Integration**: REST APIs, HTTP clients, JSON parsing
3. **Database Management**: SQLite, JDBC, SQL queries
4. **ML Deployment**: Serving trained models via API
5. **UI/UX Development**: JavaFX, event handling, charts
6. **Full-Stack Development**: Frontend + Backend + ML
7. **Project Management**: Agile methodology, Git workflow
8. **Documentation**: Technical writing, user manuals

---

## 🔥 Final Recommendations

### DO's ✅
1. **Start with Flask API on Day 1** - Get it running ASAP
2. **Test API with Postman first** - Before Java integration
3. **Use Git properly** - Feature branches, daily commits
4. **Daily standup meetings** - 15 minutes, sync progress
5. **Focus on core features first** - MVP before extras
6. **Test continuously** - Don't wait until Week 4
7. **Document as you go** - Not at the end

### DON'Ts ❌
1. **Don't skip error handling** - It's critical for demos
2. **Don't hardcode values** - Use configuration files
3. **Don't ignore suicidal predictions** - Handle responsibly
4. **Don't leave integration for Week 4** - Do it Week 2
5. **Don't overcomplicate** - Simple solutions work best
6. **Don't work in silos** - Communicate constantly
7. **Don't skip documentation** - You'll forget details

---

## 📞 Emergency Contacts (Include in App)

```java
public class CrisisResources {
    public static final String SUICIDE_HOTLINE = "988";
    public static final String CRISIS_TEXT = "Text HOME to 741741";
    public static final String INTERNATIONAL = "https://findahelpline.com";
    
    public static void showCrisisAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Crisis Resources");
        alert.setHeaderText("Immediate Help Available");
        alert.setContentText(
            "🆘 National Suicide Prevention Lifeline: 988\n" +
            "📱 Crisis Text Line: Text HOME to 741741\n" +
            "🌐 International: findahelpline.com\n\n" +
            "You are not alone. Help is available 24/7."
        );
        alert.showAndWait();
    }
}
```

---

## 🏆 Final Thoughts

With your model already trained at 85% accuracy, you're in an **excellent position** to build a high-quality, innovative project. The hardest part is done!

**Estimated Success Rate: 85-90%**

Focus on:
1. Clean, professional code
2. Robust error handling
3. Excellent documentation
4. Polished UI/UX
5. Responsible handling of sensitive predictions

**This project can easily achieve 90%+ if executed well.**

Good luck with your implementation! 🚀

---

*Document created: January 29, 2026*  
*For: 3-person academic team, 4-week timeline*  
*Model: 85% accuracy, 7 classes, 5K vocabulary*
