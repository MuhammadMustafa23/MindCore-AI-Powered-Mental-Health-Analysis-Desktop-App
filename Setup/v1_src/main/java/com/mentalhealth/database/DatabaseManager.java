package com.mentalhealth.database;

import java.sql.*;
import java.nio.file.*;

/**
 * Singleton database manager for SQLite operations
 */
public class DatabaseManager {
    
    private static DatabaseManager instance;
    private static final String DB_PATH = "data/mental_health.db";
    private Connection connection;
    
    private DatabaseManager() {
        initializeDatabase();
    }
    
    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }
    
    private void initializeDatabase() {
        try {
            // Ensure data directory exists
            Files.createDirectories(Paths.get("data"));
            
            // Connect to SQLite
            String url = "jdbc:sqlite:" + DB_PATH;
            connection = DriverManager.getConnection(url);
            
            System.out.println("✅ Database connected: " + DB_PATH);
            
            // Create tables
            createTables();
            
        } catch (Exception e) {
            System.err.println("❌ Database initialization failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void createTables() throws SQLException {
        String[] createStatements = {
            // Users table
            """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT UNIQUE NOT NULL,
                password_hash TEXT NOT NULL,
                name TEXT NOT NULL,
                age INTEGER,
                goal TEXT,
                baseline_stress INTEGER DEFAULT 5,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """,
            
            // Predictions table
            """
            CREATE TABLE IF NOT EXISTS predictions (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER NOT NULL,
                input_text TEXT NOT NULL,
                prediction TEXT NOT NULL,
                confidence REAL NOT NULL,
                severity TEXT,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (user_id) REFERENCES users(id)
            )
            """,
            
            // Mood entries table
            """
            CREATE TABLE IF NOT EXISTS mood_entries (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER NOT NULL,
                mood_score INTEGER NOT NULL CHECK(mood_score >= 1 AND mood_score <= 10),
                notes TEXT,
                entry_date DATE NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (user_id) REFERENCES users(id)
            )
            """,
            
            // Create indexes
            "CREATE INDEX IF NOT EXISTS idx_predictions_user ON predictions(user_id)",
            "CREATE INDEX IF NOT EXISTS idx_predictions_date ON predictions(created_at)",
            "CREATE INDEX IF NOT EXISTS idx_mood_user ON mood_entries(user_id)",
            "CREATE INDEX IF NOT EXISTS idx_mood_date ON mood_entries(entry_date)"
        };
        
        try (Statement stmt = connection.createStatement()) {
            for (String sql : createStatements) {
                stmt.execute(sql);
            }
            System.out.println("✅ Database tables created/verified");
        }
    }
    
    public Connection getConnection() {
        return connection;
    }
    
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✅ Database connection closed");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error closing database: " + e.getMessage());
        }
    }
}