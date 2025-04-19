package com.example.vadimaprojekts.controllers;


import com.example.vadimaprojekts.exceptions.UserNotFoundException;
import com.example.vadimaprojekts.module.Book;
import com.example.vadimaprojekts.service.APIService;
import com.example.vadimaprojekts.service.BookService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import com.example.vadimaprojekts.service.StageService;


import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    APIService apiService = new APIService();
    public Book getBookData(String id) throws UserNotFoundException {
        return apiService.booksFromFile().stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("No such username"));

    }
    public void loadBookInfo() throws UserNotFoundException {
        for(int i = 1; i<22; i++){
            System.out.println(getBookData(String.valueOf(i)).getIndustryIdentifiers());
        }


    }
    @Override
    public void start(Stage stage) throws IOException, UserNotFoundException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/vadimaprojekts/login.fxml"));
        Parent root = fxmlLoader.load();
        APIService apiService = new APIService();

        Image icon = new Image(Objects.requireNonNull(getClass().getResource("/loggo.png")).toExternalForm());
        stage.getIcons().add(icon);
        stage.setTitle("Librarium");
        StageService.setStage(stage);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}