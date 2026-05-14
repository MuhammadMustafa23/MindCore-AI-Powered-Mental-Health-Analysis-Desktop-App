module com.mentalhealth {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.sql;
    requires java.net.http;

    opens com.mentalhealth to javafx.fxml;
    opens com.mentalhealth.controller to javafx.fxml;
    opens com.mentalhealth.model to com.google.gson;

    exports com.mentalhealth;
    exports com.mentalhealth.controller;
    exports com.mentalhealth.model;
    exports com.mentalhealth.service;
    exports com.mentalhealth.repository;
}