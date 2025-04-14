package com.example.vadimaprojekts.service;

import com.example.vadimaprojekts.controllers.BookPageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SwitchToSceneService {
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

    public void switchToLogin() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/vadimaprojekts/login.fxml"));
        stage = StageService.getStage();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToRegister() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/vadimaprojekts/register.fxml"));
        stage = StageService.getStage();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToBookPage(String bookTitle) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/vadimaprojekts/bookPage.fxml"));
        Parent root = loader.load();
        stage = StageService.getStage();

        BookPageController controller = loader.getController();
        controller.loadBook(bookTitle); // pass the book title AFTER loading

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
