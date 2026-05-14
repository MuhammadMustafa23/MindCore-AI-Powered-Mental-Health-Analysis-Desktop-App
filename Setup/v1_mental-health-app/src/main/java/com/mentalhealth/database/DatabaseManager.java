package com.mentalhealth.database;

import java.sql.*;
import java.nio.file.*;

/**
 * Singleton database manager for SQLite operations
 */
public class DatabaseManager {
    
    private static DatabaseManager instance;
    private static final String DB_FOLDER = "data";
    private static final String DB_NAME = "mental_health.db";
    private static final String DB_PATH = DB_FOLDER + "/" + DB_NAME;
    
    private Connection connection;
    
    /**
     * Private constructor for singleton pattern
     */
    private DatabaseManager() {
        initializeDatabase();
    }
    
    /**
     * Get singleton instance
     */
    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }
    
    /**
     * Initialize database connection and create tables
     */
    private void initializeDatabase() {
        try {
            // Ensure data directory exists
            Path dataPath = Paths.get(DB_FOLDER);
            if (!Files.exists(dataPath)) {
                Files.createDirectories(dataPath);
                System.out.println("📁 Created data directory: " + dataPath.toAbsolutePath());
            }
            
            // Connect to SQLite database (creates file if not exists)
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
    
    /**
     * Create all required database tables
     */
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
                color TEXT,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
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
                FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
            )
            """,
            
            // Create indexes for better query performance
            "CREATE INDEX IF NOT EXISTS idx_predictions_user_id ON predictions(user_id)",
            "CREATE INDEX IF NOT EXISTS idx_predictions_created_at ON predictions(created_at)",
            "CREATE INDEX IF NOT EXISTS idx_mood_entries_user_id ON mood_entries(user_id)",
            "CREATE INDEX IF NOT EXISTS idx_mood_entries_date ON mood_entries(entry_date)"
        };
        
        try (Statement stmt = connection.createStatement()) {
            for (String sql : createStatements) {
                stmt.execute(sql);
            }
            System.out.println("✅ Database tables created/verified");
        }
    }
    
    /**
     * Get the database connection
     */
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                String url = "jdbc:sqlite:" + DB_PATH;
                connection = DriverManager.getConnection(url);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error getting connection: " + e.getMessage());
        }
        return connection;
    }
    
    /**
     * Close the database connection
     */
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
    
    /**
     * Execute a query that returns results
     */
    public ResultSet executeQuery(String sql) throws SQLException {
        Statement stmt = getConnection().createStatement();
        return stmt.executeQuery(sql);
    }
    
    /**
     * Execute an update query (INSERT, UPDATE, DELETE)
     */
    public int executeUpdate(String sql) throws SQLException {
        try (Statement stmt = getConnection().createStatement()) {
            return stmt.executeUpdate(sql);
        }
    }
}