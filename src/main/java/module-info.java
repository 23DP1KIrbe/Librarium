module com.example.vadimaprojekts {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.desktop;


    opens com.example.vadimaprojekts to javafx.fxml;
    exports com.example.vadimaprojekts.controllers;
    exports com.example.vadimaprojekts.service;
    exports com.example.vadimaprojekts.module;
    exports com.example.vadimaprojekts.exceptions;
    opens com.example.vadimaprojekts.controllers to javafx.fxml;
    opens com.example.vadimaprojekts.service to com.google.gson;
    opens com.example.vadimaprojekts.module to com.google.gson;

}