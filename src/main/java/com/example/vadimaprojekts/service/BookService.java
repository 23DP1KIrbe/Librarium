package com.example.vadimaprojekts.service;

import com.example.vadimaprojekts.controllers.BookPageController;
import com.example.vadimaprojekts.module.Book;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookService {
    private ImageCacheService imageCache;
    private SwitchToSceneService switchToSceneService;
    private BookPageController bookPageController;
    private List<Book> lastSearchResults = new ArrayList<>();
    private Session session = Session.getInstance();

    public List<Book> getLastSearchResults() {
        return lastSearchResults;
    }

    public void setLastSearchResults(List<Book> results) {
        this.lastSearchResults = results;
    }

    public void clearLastSearchResults() {
        this.lastSearchResults.clear();
    }

    public void setImageCache(ImageCacheService imageCache) {
        this.imageCache = imageCache;
        this.switchToSceneService = new SwitchToSceneService();
        this.bookPageController = new BookPageController();
    }

    public List<Book> sortAZ() {
        List<Book> books = lastSearchResults.isEmpty()
                ? apiService.booksFromFile()
                : new ArrayList<>(lastSearchResults);

        books.sort(Comparator.comparing(Book::getTitle));
        return books;
    }

    public List<Book> sortZA() {
        List<Book> books = lastSearchResults.isEmpty()
                ? apiService.booksFromFile()
                : new ArrayList<>(lastSearchResults);

        books.sort(Comparator.comparing(Book::getTitle).reversed());
        return books;
    }

    APIService apiService = new APIService();
    public Book getBookData(String id) {
        return apiService.booksFromFile().stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElse(null);

    }

    public Book getBookDataByTitle(String title) {
        return apiService.booksFromFile().stream()
                .filter(book -> book.getTitle().equals(title))
                .findFirst()
                .orElse(null);
    }


    public void updateBookDisplay(List<Book> books, int index, List<Label> labellist, List<ImageView> imagelist) {
        if(index == 1){
            for (int i = 0; i < labellist.size(); i++) {
                labellist.get(i).setWrapText(true);
                labellist.get(i).setText(books.get(i).getTitle());
            }

            for (int i = 0; i < imagelist.size(); i++) {
                String url = books.get(i).getImageLinks();
                if (url != null && !url.isEmpty()) {
                    imagelist.get(i).setImage(imageCache.getImage(books.get(i).getImageLinks()));
                } else {
                    imagelist.get(i).setImage(null);
                }
            }
        }else if(index == 2){
            for (int i = 0; i < labellist.size(); i++) {
                labellist.get(i).setWrapText(true);
                labellist.get(i).setText(books.get(i+9).getTitle());
            }

            for (int i = 0; i < imagelist.size(); i++) {
                String url = books.get(i+9).getImageLinks();
                if (url != null && !url.isEmpty()) {
                    imagelist.get(i).setImage(imageCache.getImage(books.get(i+9).getImageLinks()));
                } else {
                    imagelist.get(i).setImage(null);
                }
            }
        }else if(index == 3){
            for (int i = 0; i < labellist.size(); i++) {
                labellist.get(i).setWrapText(true);
                labellist.get(i).setText(books.get(i+18).getTitle());
            }

            for (int i = 0; i < imagelist.size(); i++) {
                String url = books.get(i).getImageLinks();
                if (url != null && !url.isEmpty()) {
                    imagelist.get(i).setImage(imageCache.getImage(books.get(i+18).getImageLinks()));
                } else {
                    imagelist.get(i).setImage(null);
                }
            }
        }
    }


    public List<Book> searchBooks(String searchField) {
        List<Book> allBooks = new ArrayList<>();
        for(int i = 0; i < apiService.booksFromFile().size(); i++){
            Pattern pattern = Pattern.compile(searchField, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(apiService.booksFromFile().get(i).getTitle());
            boolean matchFound = matcher.find();
            if(matchFound) {
                allBooks.add(apiService.booksFromFile().get(i));
            } else {

            }
        }
        return allBooks;
    }

    public void showSearchBooks(String searchField, AnchorPane anchorPane, List<Label> labellist, List<ImageView> imagelist) throws IOException {
        if(searchField.isEmpty()) {
            switchToSceneService.switchToLibrary();
        }
        Session session = Session.getInstance();
        List<Book> result = searchBooks(searchField);
        setLastSearchResults(result);
        bookPageController.setImageCache(imageCache);

        anchorPane.getChildren().removeIf(node -> node instanceof ImageView || node instanceof Label);

        int rows = (int) Math.ceil(result.size() / 3.0);
        anchorPane.setPrefHeight(608 + (rows - 1) * 400);

        if (result.isEmpty()) {
            Label label = new Label("No matches for your search!");
            label.setLayoutX(60);
            label.setLayoutY(60);
            label.setStyle("-fx-font-size: 24");
            anchorPane.getChildren().add(label);
        } else {
            for (int i = 0; i < result.size(); i++) {
                Book book = result.get(i);
                ImageView imageView = new ImageView();
                Label label = new Label(book.getTitle());

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

                imageView.setImage(imageCache.getImage(book.getImageLinks()));
                label.setCursor(Cursor.HAND);
                label.setOnMouseClicked(event -> {
                    bookPageController.setImageCache(imageCache);
                    try {
                        session.setBook(getBookDataByTitle(label.getText()));
                        switchToSceneService.switchToBookPage(label.getText());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                labellist.add(label);
                imagelist.add(imageView);

                anchorPane.getChildren().add(imageView);
                anchorPane.getChildren().add(label);
            }
        }
    }

    public List<Book> sliderFiltering(int readerValue, int buyerValue) {
        List<Book> allBooks = apiService.booksFromFile();
        List<Book> filteredBooks = new ArrayList<>();
        if(readerValue > 0 && buyerValue == 0) {
            for (Book book : allBooks) {
                if(book.getTotalReaders().size() >= readerValue) {
                    filteredBooks.add(book);
                }
            }
        }else if(readerValue == 0 && buyerValue > 0) {
            for (Book book : allBooks) {
                if(book.getTotalBuyers().size() >= buyerValue) {
                    filteredBooks.add(book);
                }
            }
        }else if(readerValue > 0 && buyerValue > 0) {
            for (Book book : allBooks) {
                if(book.getTotalBuyers().size() >= buyerValue && book.getTotalReaders().size() >= readerValue) {
                    filteredBooks.add(book);
                }
            }
        }
        return filteredBooks;
    }

    public void showBooks(List<Label> labellist, List<ImageView> imagelist, boolean a, boolean b, int readerValue, int buyerValue) {
        int currentPage = session.getCurrentPage();
        List<Book> FilteredBooks = sliderFiltering(readerValue, buyerValue);
        if ((FilteredBooks == null || FilteredBooks.isEmpty()) && (readerValue > 0 || buyerValue > 0)) {
            for (int i = 0; i < labellist.size(); i++) {
                FilteredBooks.add(new Book("", "", Collections.singletonList(""), Collections.singletonList(""), Collections.singletonList(""), "", "", "", Collections.singletonList(""), Collections.singletonList("")));

            }
            for (int i = 0; i < labellist.size(); i++) {
                String title = FilteredBooks.get(i + ((currentPage - 1) * 9)).getTitle();
                if (title != null && !title.isEmpty()) {
                    labellist.get(i).setWrapText(true);
                    labellist.get(i).setText(FilteredBooks.get(i + ((currentPage - 1) * 9)).getTitle());
                } else {
                    labellist.get(i).setText("");
                }
            }

            for (int i = 0; i < imagelist.size(); i++) {
                String url = FilteredBooks.get(i).getImageLinks();
                if (url != null && !url.isEmpty()) {
                    imagelist.get(i).setImage(imageCache.getImage(FilteredBooks.get(i + ((currentPage - 1) * 9)).getImageLinks()));
                } else {
                    imagelist.get(i).setImage(null);
                }
            }
        }else if(readerValue == 0 && buyerValue == 0) {
            if(a == true) {
                List<Book> sortedBooks = sortAZ();
                if(sortedBooks.size() % 9 != 0){
                    int aprekins = (sortedBooks.size() % 9)-9;
                    for(int i = 0; i > aprekins; i--) {
                        sortedBooks.add(new Book("", "", Collections.singletonList(""), Collections.singletonList(""), Collections.singletonList(""),"","","", Collections.singletonList(""), Collections.singletonList("")));
                    }
                }
                for (int i = 0; i < labellist.size(); i++) {
                    String title = sortedBooks.get(i+((currentPage-1)*9)).getTitle();
                    if(title != null && !title.isEmpty()) {
                        labellist.get(i).setWrapText(true);
                        labellist.get(i).setText(sortedBooks.get(i+((currentPage-1)*9)).getTitle());
                    }else{
                        labellist.get(i).setText("");
                    }
                }

                for (int i = 0; i < imagelist.size(); i++) {
                    String url = sortedBooks.get(i).getImageLinks();
                    if (url != null && !url.isEmpty()) {
                        imagelist.get(i).setImage(imageCache.getImage(sortedBooks.get(i+((currentPage-1)*9)).getImageLinks()));
                    } else {
                        imagelist.get(i).setImage(null);
                    }
                }
            }else if(b == true) {
                List<Book> sortedBooks = sortZA();
                if(sortedBooks.size() % 9 != 0){
                    int aprekins = (sortedBooks.size() % 9)-9;
                    for(int i = 0; i > aprekins; i--) {
                        sortedBooks.add(new Book("", "", Collections.singletonList(""), Collections.singletonList(""), Collections.singletonList(""),"","","", Collections.singletonList(""), Collections.singletonList("")));
                    }
                }
                for (int i = 0; i < labellist.size(); i++) {
                    String title = sortedBooks.get(i+((currentPage-1)*9)).getTitle();
                    if(title != null && !title.isEmpty()) {
                        labellist.get(i).setWrapText(true);
                        labellist.get(i).setText(sortedBooks.get(i+((currentPage-1)*9)).getTitle());
                    }else{
                        labellist.get(i).setText("");
                    }
                }

                for (int i = 0; i < imagelist.size(); i++) {
                    String url = sortedBooks.get(i).getImageLinks();
                    if (url != null && !url.isEmpty()) {
                        imagelist.get(i).setImage(imageCache.getImage(sortedBooks.get(i+((currentPage-1)*9)).getImageLinks()));
                    } else {
                        imagelist.get(i).setImage(null);
                    }
                }
            }else{
                List<Book> allBooks = apiService.booksFromFile();
                if(allBooks.size() % 9 != 0){
                    int aprekins = (allBooks.size() % 9)-9;
                    for(int i = 0; i > aprekins; i--) {
                        allBooks.add(new Book("", "", Collections.singletonList(""), Collections.singletonList(""), Collections.singletonList(""),"","","", Collections.singletonList(""), Collections.singletonList("")));
                    }
                }
                for (int i = 0; i < labellist.size(); i++) {
                    String title = allBooks.get(i+((currentPage-1)*9)).getTitle();
                    if(title != null && !title.isEmpty()) {
                        labellist.get(i).setWrapText(true);
                        labellist.get(i).setText(allBooks.get(i+((currentPage-1)*9)).getTitle());
                    }else{
                        labellist.get(i).setText("");
                    }
                }

                for (int i = 0; i < imagelist.size(); i++) {
                    String url = allBooks.get(i).getImageLinks();
                    if (url != null && !url.isEmpty()) {
                        imagelist.get(i).setImage(imageCache.getImage(allBooks.get(i+((currentPage-1)*9)).getImageLinks()));
                    } else {
                        imagelist.get(i).setImage(null);
                    }
                }
            }
        }else{
            if(a == true) {
                FilteredBooks.sort(Comparator.comparing(Book::getTitle));
                if(FilteredBooks.size() % 9 != 0){
                    int aprekins = (FilteredBooks.size() % 9)-9;
                    for(int i = 0; i > aprekins; i--) {
                        FilteredBooks.add(new Book("", "", Collections.singletonList(""), Collections.singletonList(""), Collections.singletonList(""),"","","", Collections.singletonList(""), Collections.singletonList("")));
                    }
                }
                for (int i = 0; i < labellist.size(); i++) {
                    String title = FilteredBooks.get(i+((currentPage-1)*9)).getTitle();
                    if(title != null && !title.isEmpty()) {
                        labellist.get(i).setWrapText(true);
                        labellist.get(i).setText(FilteredBooks.get(i+((currentPage-1)*9)).getTitle());
                    }else{
                        labellist.get(i).setText("");
                    }
                }

                for (int i = 0; i < imagelist.size(); i++) {
                    String url = FilteredBooks.get(i).getImageLinks();
                    if (url != null && !url.isEmpty()) {
                        imagelist.get(i).setImage(imageCache.getImage(FilteredBooks.get(i+((currentPage-1)*9)).getImageLinks()));
                    } else {
                        imagelist.get(i).setImage(null);
                    }
                }
            }else if(b == true) {
                FilteredBooks.sort(Comparator.comparing(Book::getTitle).reversed());
                if(FilteredBooks.size() % 9 != 0){
                    int aprekins = (FilteredBooks.size() % 9)-9;
                    for(int i = 0; i > aprekins; i--) {
                        FilteredBooks.add(new Book("", "", Collections.singletonList(""), Collections.singletonList(""), Collections.singletonList(""),"","","", Collections.singletonList(""), Collections.singletonList("")));
                    }
                }
                for (int i = 0; i < labellist.size(); i++) {
                    String title = FilteredBooks.get(i+((currentPage-1)*9)).getTitle();
                    if(title != null && !title.isEmpty()) {
                        labellist.get(i).setWrapText(true);
                        labellist.get(i).setText(FilteredBooks.get(i+((currentPage-1)*9)).getTitle());
                    }else{
                        labellist.get(i).setText("");
                    }
                }

                for (int i = 0; i < imagelist.size(); i++) {
                    String url = FilteredBooks.get(i).getImageLinks();
                    if (url != null && !url.isEmpty()) {
                        imagelist.get(i).setImage(imageCache.getImage(FilteredBooks.get(i+((currentPage-1)*9)).getImageLinks()));
                    } else {
                        imagelist.get(i).setImage(null);
                    }
                }
            }else{
                if(FilteredBooks.size() % 9 != 0){
                    int aprekins = (FilteredBooks.size() % 9)-9;
                    for(int i = 0; i > aprekins; i--) {
                        FilteredBooks.add(new Book("", "", Collections.singletonList(""), Collections.singletonList(""), Collections.singletonList(""),"","","", Collections.singletonList(""), Collections.singletonList("")));
                    }
                }
                for (int i = 0; i < labellist.size(); i++) {
                    String title = FilteredBooks.get(i+((currentPage-1)*9)).getTitle();
                    if(title != null && !title.isEmpty()) {
                        labellist.get(i).setWrapText(true);
                        labellist.get(i).setText(FilteredBooks.get(i+((currentPage-1)*9)).getTitle());
                    }else{
                        labellist.get(i).setText("");
                    }
                }

                for (int i = 0; i < imagelist.size(); i++) {
                    String url = FilteredBooks.get(i).getImageLinks();
                    if (url != null && !url.isEmpty()) {
                        imagelist.get(i).setImage(imageCache.getImage(FilteredBooks.get(i+((currentPage-1)*9)).getImageLinks()));
                    } else {
                        imagelist.get(i).setImage(null);
                    }
                }
            }
        }
    }
}
