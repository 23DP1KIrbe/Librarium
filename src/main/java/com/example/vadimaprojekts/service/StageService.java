package com.example.vadimaprojekts.service;

import javafx.stage.Stage;

public class StageService {
    private static Stage primaryStage;

    public static void setStage(Stage stage) {
        primaryStage = stage;
    }

    public static Stage getStage() {
        return primaryStage;
    }
}
