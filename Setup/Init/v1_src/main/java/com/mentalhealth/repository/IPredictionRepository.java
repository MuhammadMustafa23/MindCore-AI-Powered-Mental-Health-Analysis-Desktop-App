package com.mentalhealth.repository;

import com.mentalhealth.model.Prediction;
import java.util.List;

/**
 * Prediction Repository Interface
 */
public interface IPredictionRepository {
    
    // Save prediction, returns with generated ID
    Prediction save(Prediction prediction);
    
    // Get all predictions for a user (newest first)
    List<Prediction> findByUserId(int userId);
    
    // Get recent predictions (limit)
    List<Prediction> findByUserIdLimit(int userId, int limit);
    
    // Get prediction count by type for user
    // Returns: {"Anxiety": 5, "Stress": 3, ...}
    java.util.Map<String, Integer> getCountByPredictionType(int userId);
}