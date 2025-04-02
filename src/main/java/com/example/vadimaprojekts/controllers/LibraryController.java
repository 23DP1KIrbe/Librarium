package com.example.vadimaprojekts.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LibraryController implements Initializable {
    @FXML
    private MenuButton profileOrLogout;
    @FXML
    private ImageView book1;
    @FXML
    private ImageView book2;
    @FXML
    private ImageView book3;
    @FXML
    private Label book1Text;
    @FXML
    private Label book2Text;
    @FXML
    private Label book3Text;


    public void initialize(URL url, ResourceBundle resourceBundle) {

        Image image1 = new Image("george.png");
        Image image2 = new Image("atomic.jpg");
        Image image3 = new Image("martianin.jpg");
        if (book1 != null) {
            book1.setImage((image1));
            book1Text.setText("\"1984\" by George Orwell");
        }
        if (book2 != null) {
            book2.setImage((image2));
            book2Text.setText("\"Atomic Habits\" by James Clear");
        }
        if (book3 != null) {
            book3.setImage((image3));
            book3Text.setText("\"The Martian\" by Andy Weir");
        }
    }
}
