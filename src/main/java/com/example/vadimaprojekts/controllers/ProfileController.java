package com.example.vadimaprojekts.controllers;

import com.example.vadimaprojekts.module.User;
import com.example.vadimaprojekts.service.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    @FXML
    private Button logoutBtn, profileBtn, buyListBtn, readListBtn;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label userWelcoming;
    private SwitchToSceneService switchToSceneService = new SwitchToSceneService();
    private Session session = Session.getInstance();
    private User user = session.getUser();
    private APIService apiService = new APIService();
    private BookService bookService = new BookService();
    private ImageView[] books = new ImageView[user.getReadList().size()];
    private Label[] labels = new Label[user.getReadList().size()];
    private ImageCacheService imageCache;


    public void setImageCache(ImageCacheService imageCache) {
        this.imageCache = imageCache;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userWelcoming.setText("Hello, " + user.getUsername());
        profileBtn.setStyle("-fx-underline: true; -fx-background-color: transparent");
    }

    @FXML
    public void onreadListBtnClick(ActionEvent event) {
        int bookCount = user.getReadList().size();
        List<String> bookId = new ArrayList<>(user.getReadList());
        anchorPane.getChildren().removeIf(node -> node instanceof ImageView);
        anchorPane.getChildren().removeIf(node -> node instanceof Label);
        int rows = (int) Math.ceil(bookCount / 3.0);
        anchorPane.setPrefHeight(608 + (rows - 1) * 400);
        if(bookCount == 0) {
            Label label = new Label();
            label.setText("No added books to read list!");
            label.setLayoutX(60);
            label.setLayoutY(60);
            label.setStyle("-fx-font-size: 24");
            anchorPane.getChildren().add(label);
        }else{
            books = new ImageView[bookCount];
            labels = new Label[bookCount];
            for (int i = 0; i < bookCount; i++) {
                ImageView imageView = new ImageView();
                Label label = new Label(bookService.getBookData(bookId.get(i)).getTitle());
                imageView.setFitWidth(180);
                imageView.setFitHeight(255);
                label.setWrapText(true);
                label.setStyle("-fx-font-size: 16; -fx-text-alignment: center;");
                label.setAlignment(Pos.CENTER);
                label.setPrefWidth(180);
                label.setPrefHeight(50);

                int row = i / 3;
                int col = i % 3;

                double x = 85 + col * 350;
                double y = 70 + row * 400;

                imageView.setX(x);
                imageView.setY(y);
                label.setLayoutX(x);
                label.setLayoutY(y + 270);

                try {
                    imageView.setImage(imageCache.getImage(bookService.getBookData(bookId.get(i)).getImageLinks()));
                } catch (Exception e) {
                    System.out.println("Nesanaca");
                }

                label.setCursor(Cursor.HAND);
                label.setOnMouseClicked(events -> {
                    try {
                        session.setBook(bookService.getBookDataByTitle(label.getText()));
                        switchToSceneService.switchToBookPage(label.getText());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                books[i] = imageView;
                labels[i] = label;
                anchorPane.getChildren().add(imageView);
                anchorPane.getChildren().add(label);
            }
        }
        buyListBtn.setStyle("-fx-underline:  false; -fx-background-color: transparent");
        readListBtn.setStyle("-fx-underline:  true; -fx-background-color: transparent");
        profileBtn.setStyle("-fx-underline: false; -fx-background-color: transparent");
    }



    @FXML
    public void onbuyListBtnClick(ActionEvent event) {
        int bookCount = user.getBuyList().size();
        List<String> bookId = new ArrayList<>(user.getBuyList());
        anchorPane.getChildren().removeIf(node -> node instanceof ImageView);
        anchorPane.getChildren().removeIf(node -> node instanceof Label);
        int rows = (int) Math.ceil(bookCount / 3.0);
        anchorPane.setPrefHeight(608 + (rows - 1) * 400);
        if(bookCount == 0) {
            Label label = new Label();
            label.setText("No added books to buy list!");
            label.setLayoutX(60);
            label.setLayoutY(60);
            label.setStyle("-fx-font-size: 24");
            anchorPane.getChildren().add(label);
        }else{
            books = new ImageView[bookCount];
            labels = new Label[bookCount];
            for (int i = 0; i < bookCount; i++) {
                ImageView imageView = new ImageView();
                Label label = new Label(bookService.getBookData(bookId.get(i)).getTitle());
                imageView.setFitWidth(180);
                imageView.setFitHeight(255);
                label.setWrapText(true);
                label.setStyle("-fx-font-size: 16; -fx-text-alignment: center;");
                label.setAlignment(Pos.CENTER);
                label.setPrefWidth(180);
                label.setPrefHeight(50);

                int row = i / 3;
                int col = i % 3;

                double x = 85 + col * 350;
                double y = 70 + row * 400;

                imageView.setX(x);
                imageView.setY(y);
                label.setLayoutX(x);
                label.setLayoutY(y + 270);

                try {
                    imageView.setImage(imageCache.getImage(bookService.getBookData(bookId.get(i)).getImageLinks()));
                } catch (Exception e) {
                    System.out.println("Nesanaca");
                }

                label.setCursor(Cursor.HAND);
                label.setOnMouseClicked(events -> {
                    try {
                        session.setBook(bookService.getBookDataByTitle(label.getText()));
                        switchToSceneService.switchToBookPage(label.getText());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                books[i] = imageView;
                labels[i] = label;
                anchorPane.getChildren().add(imageView);
                anchorPane.getChildren().add(label);
            }
        }
        buyListBtn.setStyle("-fx-underline:  true; -fx-background-color: transparent");
        readListBtn.setStyle("-fx-underline:  false; -fx-background-color: transparent");
        profileBtn.setStyle("-fx-underline: false; -fx-background-color: transparent");
    }

    @FXML
    private void onprofileBtnClick(ActionEvent event) {
        anchorPane.getChildren().removeIf(node -> node instanceof ImageView);
        anchorPane.getChildren().removeIf(node -> node instanceof Label);
        Label label = new Label();
        label.setText("Hello, " + user.getUsername());
        label.setLayoutX(60);
        label.setLayoutY(60);
        label.setStyle("-fx-font-size: 24");
        anchorPane.getChildren().add(label);
        buyListBtn.setStyle("-fx-underline:  false; -fx-background-color: transparent");
        readListBtn.setStyle("-fx-underline:  false; -fx-background-color: transparent");
        profileBtn.setStyle("-fx-underline: true; -fx-background-color: transparent");
    }

    @FXML
    private void onlogoutBtnClick(ActionEvent event) throws IOException {
        session.setUser(null);
        switchToSceneService.switchToLogin();
    }

    @FXML
    private void onlibraryBtnClick(ActionEvent event) throws IOException {
        switchToSceneService.switchToLibrary();
    }

}
