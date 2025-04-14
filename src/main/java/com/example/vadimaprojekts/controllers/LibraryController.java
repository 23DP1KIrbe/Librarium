package com.example.vadimaprojekts.controllers;

import com.example.vadimaprojekts.exceptions.UserNotFoundException;
import com.example.vadimaprojekts.module.Book;
import com.example.vadimaprojekts.service.APIService;
import com.example.vadimaprojekts.service.BookService;
import com.example.vadimaprojekts.service.ImageCacheService;
import com.example.vadimaprojekts.service.SwitchToSceneService;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class LibraryController implements Initializable {
    @FXML
    private ProgressIndicator progressIndicator;
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
    private Button page1;
    @FXML
    private Button page2;
    @FXML
    private Button page3;

    private BookService bookService;
    private List<Label> labellist;
    private List<ImageView> imagelist;
    private List<Book> originalBooks;
    private ImageCacheService imageCache = new ImageCacheService();
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
        BookPageController bookPageController = new BookPageController();

        for (Label label : labellist) {
            label.setCursor(Cursor.HAND);
            label.setOnMouseClicked(event -> {
                bookPageController.setImageCache(imageCache);
                System.out.println(label.getText() + " clicked!");
                try {
                    switchToSceneService.switchToBookPage(label.getText());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        this.bookService = new BookService();
        APIService apiService = new APIService();
        this.originalBooks = apiService.booksFromFile();
        progressIndicator.setVisible(true);

        Task<Void> loadBooksTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                for (Book book : originalBooks) {
                    String imageUrl = book.getImageLinks();
                    imageCache.preloadImage(imageUrl);
                }
                bookService.setImageCache(imageCache);

                for (int i = 0; i < labellist.size(); i++) {
                    int index = i;
                    Book book = bookService.getBookData(String.valueOf(index + 1));

                    Platform.runLater(() -> {
                        labellist.get(index).setWrapText(true);
                        labellist.get(index).setText(book.getTitle());
                        imagelist.get(index).setImage(new Image(book.getImageLinks()));
                    });
                }
                return null;
            }
        };
        loadBooksTask.setOnSucceeded(e -> progressIndicator.setVisible(false));
        loadBooksTask.setOnFailed(e -> progressIndicator.setVisible(false));
        new Thread(loadBooksTask).start();
        page1.setStyle("-fx-underline: true");
    }


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
