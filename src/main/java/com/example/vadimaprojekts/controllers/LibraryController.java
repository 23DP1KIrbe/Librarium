package com.example.vadimaprojekts.controllers;

import com.example.vadimaprojekts.exceptions.UserNotFoundException;
import com.example.vadimaprojekts.module.Book;
import com.example.vadimaprojekts.service.BookService;
import com.example.vadimaprojekts.service.SwitchToSceneService;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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

    SwitchToSceneService switchToSceneService = new SwitchToSceneService();
    @FXML
    public void onlogoutButtonClick(ActionEvent event) throws IOException {

        switchToSceneService.switchToLogin();
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

    private List<Label> labellist;
    private List<ImageView> imagelist;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        labellist = new ArrayList<>();
        labellist.add(bookText1);
        labellist.add(bookText2);
        labellist.add(bookText3);
        labellist.add(bookText4);
        labellist.add(bookText5);
        labellist.add(bookText6);
        labellist.add(bookText7);
        labellist.add(bookText8);
        labellist.add(bookText9);


        imagelist = new ArrayList<>();
        imagelist.add(book1);
        imagelist.add(book2);
        imagelist.add(book3);
        imagelist.add(book4);
        imagelist.add(book5);
        imagelist.add(book6);
        imagelist.add(book7);
        imagelist.add(book8);
        imagelist.add(book9);


        String book1Text = bookText1.getText();
        String book2Text = bookText2.getText();
        String book3Text = bookText3.getText();
        String book4Text = bookText4.getText();
        String book5Text = bookText5.getText();
        String book6Text = bookText6.getText();
        String book7Text = bookText7.getText();
        String book8Text = bookText8.getText();
        String book9Text = bookText9.getText();
        String bookImage1 = book1.getImage().getUrl();
        String bookImage2 = book2.getImage().getUrl();
        String bookImage3 = book3.getImage().getUrl();
        String bookImage4 = book4.getImage().getUrl();
        String bookImage5 = book5.getImage().getUrl();
        String bookImage6 = book6.getImage().getUrl();
        String bookImage7 = book7.getImage().getUrl();
        String bookImage8 = book8.getImage().getUrl();
        String bookImage9 = book9.getImage().getUrl();
        BookService bookService = new BookService(book1Text, book2Text, book3Text, book4Text, book5Text,
                book6Text, book7Text, book8Text, book9Text, bookImage1,
                bookImage2, bookImage3, bookImage4, bookImage5, bookImage6,
                bookImage7, bookImage8, bookImage9);

//        for (int i = 0; i < labellist.size(); i++) {
//            try {
//                labellist.get(i).setText(bookService.getBookData(String.valueOf(i+1)).getTitle());
//            } catch (UserNotFoundException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//
//        for (int i = 0; i < imagelist.size(); i++) {
//            try {
//                imagelist.get(i).setImage(new Image(bookService.getBookData(String.valueOf(i+1)).getImageLinks()));
//            } catch (UserNotFoundException e) {
//                System.out.println(e.getMessage());
//            }
//        }
        Task<Void> loadBooksTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                for (int i = 0; i < labellist.size(); i++) {
                    try {
                        int index = i;
                        Book book = bookService.getBookData(String.valueOf(index + 1));

                        Platform.runLater(() -> {
                            labellist.get(index).setText(book.getTitle());
                            imagelist.get(index).setImage(new Image(book.getImageLinks()));
                        });

                    } catch (UserNotFoundException e) {
                        System.out.println("Book not found: " + e.getMessage());
                    }
                }
                return null;
            }
        };

        new Thread(loadBooksTask).start(); // starts loading after the scene appears



    }
}
