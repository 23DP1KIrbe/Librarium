package com.example.vadimaprojekts.service;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SwitchToRegisterService {
    private Stage stage;
    private Scene scene;
    private Parent root;



    public void switchToRegister() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/vadimaprojekts/register.fxml"));
        stage = StageService.getStage();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
