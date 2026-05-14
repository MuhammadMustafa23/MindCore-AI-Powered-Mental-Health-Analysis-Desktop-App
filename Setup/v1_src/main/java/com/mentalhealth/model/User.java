package com.mentalhealth.model;

/**
 * User model - AGREED BY ALL TEAM MEMBERS
 * DO NOT CHANGE field names without team discussion
 */
public class User {
    private int id;
    private String username;
    private String passwordHash;
    private String name;
    private Integer age;
    private String goal;
    private int baselineStress;
    private String createdAt;
    
    // Default constructor
    public User() {}
    
    // Constructor for new user registration
    public User(String username, String passwordHash, String name) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.name = name;
        this.baselineStress = 5;
    }
    
    // Full constructor
    public User(int id, String username, String passwordHash, String name, 
                Integer age, String goal, int baselineStress, String createdAt) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.name = name;
        this.age = age;
        this.goal = goal;
        this.baselineStress = baselineStress;
        this.createdAt = createdAt;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    
    public String getGoal() { return goal; }
    public void setGoal(String goal) { this.goal = goal; }
    
    public int getBaselineStress() { return baselineStress; }
    public void setBaselineStress(int baselineStress) { this.baselineStress = baselineStress; }
    
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}