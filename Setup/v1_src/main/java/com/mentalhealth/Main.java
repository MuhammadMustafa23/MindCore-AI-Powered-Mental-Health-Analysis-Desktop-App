package com.mentalhealth;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main entry point for Mental Health Analysis Application
 */
public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        
        try {
            // Load the main window
            Parent root = FXMLLoader.load(
                getClass().getResource("/fxml/MainWindow.fxml")
            );
            
            Scene scene = new Scene(root, 1200, 800);
            
            // Load CSS
            scene.getStylesheets().add(
                getClass().getResource("/css/main.css").toExternalForm()
            );
            
            stage.setTitle("Mental Health Analysis - AI Powered");
            stage.setScene(scene);
            stage.setMinWidth(800);
            stage.setMinHeight(600);
            stage.show();
            
            System.out.println("✅ Application started successfully!");
            
        } catch (IOException e) {
            System.err.println("❌ Failed to load application: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Get the primary stage for scene switching
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Switch to a different scene
     */
    public static void switchScene(String fxmlFile) throws IOException {
        Parent root = FXMLLoader.load(
            Main.class.getResource("/fxml/" + fxmlFile)
        );
        primaryStage.getScene().setRoot(root);
    }

    public static void main(String[] args) {
        launch(args);
    }
}