package com.example.vadimaprojekts.controllers;


import com.example.vadimaprojekts.service.APIService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.vadimaprojekts.service.StageService;


import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/vadimaprojekts/login.fxml"));
        Parent root = fxmlLoader.load();
        APIService apiService = new APIService();
        apiService.saveBook("9783732508686");
        StageService.setStage(stage);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}