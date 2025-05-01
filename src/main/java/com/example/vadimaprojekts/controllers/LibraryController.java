package com.example.vadimaprojekts.controllers;

import com.example.vadimaprojekts.exceptions.UserNotFoundException;
import com.example.vadimaprojekts.module.Book;
import com.example.vadimaprojekts.service.*;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LibraryController implements Initializable {
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private ToggleGroup group1;
    @FXML
    private RadioButton sortAZ, sortZA, sortRating;
    @FXML
    private ImageView book1, book2, book3, book4, book5, book6, book7, book8, book9;
    @FXML
    private Label bookText1, bookText2, bookText3, bookText4, bookText5, bookText6, bookText7, bookText8, bookText9, totalBooks;
    @FXML
    private Button page1, page2, page3;
    @FXML
    private TextField searchField;
    @FXML
    private AnchorPane anchorPane;


    private Session session = Session.getInstance();
    private BookService bookService;
    private List<Label> labellist;
    private List<ImageView> imagelist;
    private List<Book> originalBooks;
    private ImageCacheService imageCache = new ImageCacheService();
    private BookPageController bookPageController = new BookPageController();
    private List<String> imageUrls = Arrays.asList(
            "image_url1", "image_url2", "image_url3", "image_url4",
            "image_url5", "image_url6", "image_url7", "image_url8", "image_url9"
    );

    SwitchToSceneService switchToSceneService = new SwitchToSceneService();


    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup group1 = new ToggleGroup();
        sortAZ.setToggleGroup(group1);
        sortZA.setToggleGroup(group1);
        sortRating.setToggleGroup(group1);

        this.labellist = List.of(
                bookText1, bookText2, bookText3, bookText4, bookText5, bookText6, bookText7, bookText8, bookText9
        );

        this.imagelist = List.of(
                book1, book2, book3, book4, book5, book6, book7, book8, book9
        );
        APIService apiService = new APIService();
        this.originalBooks = apiService.booksFromFile();
        for (Book book : originalBooks) {
            String imageUrl = book.getImageLinks();
            imageCache.preloadImage(imageUrl);
        }

        for (Label label : labellist) {
            label.setCursor(Cursor.HAND);
            label.setOnMouseClicked(event -> {
                try {
                    session.setImageCache(imageCache);
                    session.setBook(bookService.getBookDataByTitle(label.getText()));
                    switchToSceneService.switchToBookPage(label.getText());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        this.bookService = new BookService();
        totalBooks.setText("Total available books: " + originalBooks.size());
        progressIndicator.setVisible(false);
        switchToSceneService.setImageCache(imageCache);
        bookService.setImageCache(imageCache);
        bookService.updateBookDisplay(apiService.booksFromFile(), 1, labellist, imagelist);

        page1.setStyle("-fx-underline: true");

    }


    @FXML
    public void onlogoutButtonClick(ActionEvent event) throws IOException {
        switchToSceneService.switchToLogin();
        session.setUser(null);
    }

    @FXML
    public void ongoToProfileClick(ActionEvent event) throws IOException {
        switchToSceneService.switchToProfile(imageCache);
    }

    @FXML
    public void onsearchButtonClick(ActionEvent event) throws IOException {
        String query = searchField.getText();
        if (query == null || query.trim().isEmpty()) {
            bookService.showSearchBooks(query, anchorPane, labellist, imagelist);
            return;
        }
        SearchController controller = switchToSceneService.switchToSearch(event);
        controller.setImageCache(imageCache);
        controller.datainit(query);
        controller.bookService.setImageCache(imageCache);
        controller.bookService.showSearchBooks(query, controller.anchorPane, controller.labellist, controller.imagelist); // Show the results in SearchController
    }

    @FXML
    public void onsortAZClick(ActionEvent event) throws IOException {
        List<Book> sortedBooks = bookService.sortAZ();
        for (int i = 0; i < labellist.size(); i++) {
            labellist.get(i).setWrapText(true);
            labellist.get(i).setText(sortedBooks.get(i).getTitle());
        }
        for (int i = 0; i < imagelist.size(); i++) {
            String url = sortedBooks.get(i).getImageLinks();
            if (url != null && !url.isEmpty()) {
                imagelist.get(i).setImage(imageCache.getImage(sortedBooks.get(i).getImageLinks()));
            } else {
                imagelist.get(i).setImage(null);
            }
        }
        page1.setStyle("-fx-underline: true");
        page2.setStyle("-fx-underline: false");
        page3.setStyle("-fx-underline: false");
        System.out.println("Sort AZ Clicked");
    }

    @FXML
    public void onsortZAClick(ActionEvent event) throws IOException {
        List<Book> sortedBooks = bookService.sortZA();
        for (int i = 0; i < labellist.size(); i++) {
            labellist.get(i).setWrapText(true);
            labellist.get(i).setText(sortedBooks.get(i).getTitle());
        }

        for (int i = 0; i < imagelist.size(); i++) {
            String url = sortedBooks.get(i).getImageLinks();
            if (url != null && !url.isEmpty()) {
                imagelist.get(i).setImage(imageCache.getImage(sortedBooks.get(i).getImageLinks()));
            } else {
                imagelist.get(i).setImage(null);
            }
        }
        page1.setStyle("-fx-underline: true");
        page2.setStyle("-fx-underline: false");
        page3.setStyle("-fx-underline: false");
        System.out.println("Sort ZA Clicked");
    }

    @FXML
    public void onsortRatingClick(ActionEvent event) throws IOException {
        System.out.println("Sort Rating Clicked");
    }

    @FXML
    public void onPage1Click(ActionEvent event) throws IOException {
        bookService.page1(labellist, imagelist, sortAZ.isSelected(), sortZA.isSelected(), sortRating.isSelected(), imageCache);
        page1.setStyle("-fx-underline: true");
        page2.setStyle("-fx-underline: false");
        page3.setStyle("-fx-underline: false");
    }

    @FXML
    public void onPage2Click(ActionEvent event) throws IOException {
        bookService.page2(labellist, imagelist, sortAZ.isSelected(), sortZA.isSelected(), sortRating.isSelected(), imageCache);
        page1.setStyle("-fx-underline: false");
        page2.setStyle("-fx-underline: true");
        page3.setStyle("-fx-underline: false");
    }

    @FXML
    public void onPage3Click(ActionEvent event) throws IOException {
        bookService.page3(labellist, imagelist, sortAZ.isSelected(), sortZA.isSelected(), sortRating.isSelected(), imageCache);
        page1.setStyle("-fx-underline: false");
        page2.setStyle("-fx-underline: false");
        page3.setStyle("-fx-underline: true");
    }
}
