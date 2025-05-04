package com.example.vadimaprojekts.controllers;

import com.example.vadimaprojekts.module.Book;
import com.example.vadimaprojekts.service.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    @FXML AnchorPane anchorPane;
    @FXML private RadioButton sortAZ, sortZA;
    @FXML private TextField searchField;
    @FXML private ToggleGroup group1;
    List<Label> labellist = new ArrayList<>();
    List<ImageView> imagelist = new ArrayList<>();
    private ImageCacheService imageCache;
    BookService bookService;
    Session session = Session.getInstance();
    private SwitchToSceneService switchToSceneService = new SwitchToSceneService();
    private APIService apiService = new APIService();

    public SearchController() {
        this.bookService = new BookService();
    }

    public void setImageCache(ImageCacheService imageCache) {
        this.imageCache = imageCache;
    }

    public void datainit(String searchfield) {
        searchField.setText(searchfield);
        ToggleGroup group1 = new ToggleGroup();
        sortAZ.setToggleGroup(group1);
        sortZA.setToggleGroup(group1);
    }

    @FXML
    private void onlogoutButtonClick(ActionEvent actionEvent) throws IOException {
        switchToSceneService.switchToLogin();
        session.setUser(null);
    }

    @FXML
    private void ongoToProfileClick(ActionEvent actionEvent) throws IOException {
        switchToSceneService.switchToProfile(session.getImageCache());
    }

    @FXML
    public void onsearchButtonClick(ActionEvent event) throws IOException {
        bookService.setImageCache(imageCache);
        labellist.clear();
        imagelist.clear();
        bookService.showSearchBooks(searchField.getText(), anchorPane, labellist, imagelist);
    }

    @FXML
    public void onsortAZClick(ActionEvent event) throws IOException {
        if (labellist.isEmpty() || imagelist.isEmpty()) {
            System.out.println("No books to sort.");
            return;
        }

        List<Book> sortedBooks = bookService.sortAZ();

        for (int i = 0; i < sortedBooks.size(); i++) {
            if (i < labellist.size()) {
                labellist.get(i).setText(sortedBooks.get(i).getTitle());
                labellist.get(i).setWrapText(true);

                String url = sortedBooks.get(i).getImageLinks();

                if (url != null && !url.isEmpty()) {
                    imagelist.get(i).setImage(imageCache.getImage(url));
                } else {
                    imagelist.get(i).setImage(null);
                }
            }
        }
    }

    @FXML
    public void onsortZAClick(ActionEvent event) throws IOException {
        if (labellist.isEmpty() || imagelist.isEmpty()) {
            System.out.println("No books to sort.");
            return;
        }

        List<Book> sortedBooks = bookService.sortZA();

        for (int i = 0; i < sortedBooks.size(); i++) {
            if (i < labellist.size()) {
                labellist.get(i).setText(sortedBooks.get(i).getTitle());
                labellist.get(i).setWrapText(true);

                String url = sortedBooks.get(i).getImageLinks();
                if (url != null && !url.isEmpty()) {
                    imagelist.get(i).setImage(imageCache.getImage(url));
                } else {
                    imagelist.get(i).setImage(null);
                }
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageCache = session.getImageCache();
        if (imageCache == null) {
            System.out.println("ImageCache is not initialized yet.");
        } else {
            System.out.println("ImageCache is initialized.");
        }
    }
}
