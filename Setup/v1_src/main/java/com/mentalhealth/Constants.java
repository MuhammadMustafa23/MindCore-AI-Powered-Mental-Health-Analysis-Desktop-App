package com.mentalhealth;

/**
 * Shared constants - edit only in standup together
 */
public final class Constants {
    
    // API Configuration
    public static final String API_BASE_URL = "http://localhost:5000";
    public static final int API_TIMEOUT_SECONDS = 30;
    
    // Database
    public static final String DB_PATH = "data/mental_health.db";
    
    // Severity Colors (used by UI)
    public static final String COLOR_NORMAL = "#4CAF50";
    public static final String COLOR_MODERATE = "#FF9800";
    public static final String COLOR_HIGH = "#F44336";
    public static final String COLOR_CRITICAL = "#9C27B0";
    
    // Validation
    public static final int MIN_TEXT_LENGTH = 10;
    public static final int MAX_TEXT_LENGTH = 5000;
    public static final int MIN_PASSWORD_LENGTH = 6;
    
    private Constants() {} // Prevent instantiation
}