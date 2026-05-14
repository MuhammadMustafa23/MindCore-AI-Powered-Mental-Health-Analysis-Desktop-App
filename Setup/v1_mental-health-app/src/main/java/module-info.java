module com.mentalhealth {
    // JavaFX modules
    requires javafx.controls;
    requires javafx.fxml;
    
    // JSON parsing
    requires com.google.gson;
    
    // Database
    requires java.sql;
    
    // HTTP Client (built into Java 11+)
    requires java.net.http;
    
    // Open packages to JavaFX for reflection (FXML loading)
    opens com.mentalhealth to javafx.fxml;
    opens com.mentalhealth.controller to javafx.fxml;
    opens com.mentalhealth.model to com.google.gson, javafx.fxml;
    
    // Export packages for use
    exports com.mentalhealth;
    exports com.mentalhealth.controller;
    exports com.mentalhealth.model;
    exports com.mentalhealth.service;
    exports com.mentalhealth.repository;
    exports com.mentalhealth.database;
    exports com.mentalhealth.session;
    exports com.mentalhealth.util;
}