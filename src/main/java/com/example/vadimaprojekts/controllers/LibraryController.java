package com.example.vadimaprojekts.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LibraryController implements Initializable {
    @FXML
    private ToggleGroup group1;
    @FXML
    private RadioButton sortAZ;
    @FXML
    private RadioButton sortZA;
    @FXML
    private RadioButton sortRating;
    @FXML
    private ImageView book1;
    @FXML
    private ImageView book2;
    @FXML
    private ImageView book3;
    @FXML
    private ImageView book4;
    @FXML
    private ImageView book5;
    @FXML
    private ImageView book6;
    @FXML
    private ImageView book7;
    @FXML
    private ImageView book8;
    @FXML
    private ImageView book9;
    @FXML
    private Label bookText1;
    @FXML
    private Label bookText2;
    @FXML
    private Label bookText3;
    @FXML
    private Label bookText4;
    @FXML
    private Label bookText5;
    @FXML
    private Label bookText6;
    @FXML
    private Label bookText7;
    @FXML
    private Label bookText8;
    @FXML
    private Label bookText9;

    @FXML
    public void onlogoutButtonClick(ActionEvent event) throws IOException {
        System.out.println("Logging out");
    }

    @FXML
    public void ongoToProfileClick(ActionEvent event) throws IOException {
        System.out.println("Profile Clicked");
    }

    @FXML
    public void onsearchButtonClick(ActionEvent event) throws IOException {
        System.out.println("Search Clicked");
    }

    @FXML
    public void onsortAZClick(ActionEvent event) throws IOException {
        System.out.println("Sort AZ Clicked");
    }

    @FXML
    public void onsortZAClick(ActionEvent event) throws IOException {
        System.out.println("Sort ZA Clicked");
    }

    @FXML
    public void onsortRatingClick(ActionEvent event) throws IOException {
        System.out.println("Sort Rating Clicked");
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {

        Image image1 = new Image("george.png");
        Image image2 = new Image("atomic.jpg");
        Image image3 = new Image("martianin.jpg");
        if (book1 != null) {
            book1.setImage((image1));
            bookText1.setText("\"1984\" by George Orwell");
        }
        if (book2 != null) {
            book2.setImage((image2));
            bookText2.setText("\"Atomic Habits\" by James Clear");
        }
        if (book3 != null) {
            book3.setImage((image3));
            bookText3.setText("\"The Martian\" by Andy Weir");
        }
    }
}
