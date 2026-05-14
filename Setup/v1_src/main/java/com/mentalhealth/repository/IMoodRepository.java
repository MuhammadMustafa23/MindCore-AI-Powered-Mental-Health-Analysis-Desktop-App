package com.mentalhealth.repository;

import com.mentalhealth.model.MoodEntry;
import java.util.List;

/**
 * Mood Repository Interface
 */
public interface IMoodRepository {
    
    // Save mood entry
    MoodEntry save(MoodEntry entry);
    
    // Get mood entries for date range
    List<MoodEntry> findByUserIdAndDateRange(int userId, String startDate, String endDate);
    
    // Get last N mood entries
    List<MoodEntry> findByUserIdLimit(int userId, int limit);
    
    // Get average mood for date range
    double getAverageMood(int userId, String startDate, String endDate);
}