package com.mentalhealth.model;

/**
 * MoodEntry model - AGREED BY ALL TEAM MEMBERS
 */
public class MoodEntry {
    private int id;
    private int userId;
    private int moodScore;  // 1-10
    private String notes;
    private String entryDate;  // YYYY-MM-DD
    private String createdAt;
    
    public MoodEntry() {}
    
    public MoodEntry(int userId, int moodScore, String notes, String entryDate) {
        this.userId = userId;
        this.moodScore = moodScore;
        this.notes = notes;
        this.entryDate = entryDate;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public int getMoodScore() { return moodScore; }
    public void setMoodScore(int moodScore) { this.moodScore = moodScore; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    
    public String getEntryDate() { return entryDate; }
    public void setEntryDate(String entryDate) { this.entryDate = entryDate; }
    
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}