package com.mentalhealth;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

import com.mentalhealth.database.DatabaseManager;

import java.io.IOException;

/**
 * Main entry point for Mental Health Analysis Application
 */
public class Main extends Application {

    private static Stage primaryStage;
    private static final int DEFAULT_WIDTH = 1200;
    private static final int DEFAULT_HEIGHT = 800;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        
        try {
            // Initialize database
            System.out.println("🔄 Initializing database...");
            DatabaseManager.getInstance();
            System.out.println("✅ Database initialized successfully");
            
            // Load the login screen
            System.out.println("🔄 Loading UI...");
            Parent root = FXMLLoader.load(
                getClass().getResource("/fxml/LoginScreen.fxml")
            );
            
            Scene scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT);
            
            // Load CSS if exists
            var cssResource = getClass().getResource("/css/styles.css");
            if (cssResource != null) {
                scene.getStylesheets().add(cssResource.toExternalForm());
            }
            
            stage.setTitle("Mental Health Analysis - AI Powered");
            stage.setScene(scene);
            stage.setMinWidth(800);
            stage.setMinHeight(600);
            stage.show();
            
            System.out.println("✅ Application started successfully!");
            System.out.println("🌐 Make sure Flask API is running on http://localhost:5000");
            
        } catch (IOException e) {
            System.err.println("❌ Failed to load application: " + e.getMessage());
            e.printStackTrace();
            showErrorAlert("Failed to Start", 
                "Could not load the application.\n\n" + e.getMessage());
        }
    }

    @Override
    public void stop() {
        // Clean up resources
        System.out.println("🔄 Shutting down application...");
        DatabaseManager.getInstance().closeConnection();
        System.out.println("✅ Application closed");
    }

    /**
     * Get the primary stage for scene switching
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Switch to a different scene
     * @param fxmlFile Name of FXML file (e.g., "MainWindow.fxml")
     */
    public static void switchScene(String fxmlFile) throws IOException {
        Parent root = FXMLLoader.load(
            Main.class.getResource("/fxml/" + fxmlFile)
        );
        primaryStage.getScene().setRoot(root);
    }

    /**
     * Show error alert dialog
     */
    public static void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Show info alert dialog
     */
    public static void showInfoAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}