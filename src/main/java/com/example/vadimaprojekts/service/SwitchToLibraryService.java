package com.example.vadimaprojekts.service;

import com.example.vadimaprojekts.controllers.LoginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SwitchToLibraryService {
    private Stage stage;
    private Scene scene;
    private Parent root;



    public void switchToLibrary() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/vadimaprojekts/library.fxml"));
        stage = StageService.getStage();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
