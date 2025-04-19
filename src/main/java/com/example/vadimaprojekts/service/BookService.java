package com.example.vadimaprojekts.service;

import com.example.vadimaprojekts.exceptions.UserNotFoundException;
import com.example.vadimaprojekts.module.Book;
import com.example.vadimaprojekts.module.User;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookService {
    private String book1Text;
    private String bookText2;
    private String bookText3;
    private String bookText4;
    private String bookText5;
    private String bookText6;
    private String bookText7;
    private String bookText8;
    private String bookText9;

    private String bookImage1;
    private String bookImage2;
    private String bookImage3;
    private String bookImage4;
    private String bookImage5;
    private String bookImage6;
    private String bookImage7;
    private String bookImage8;
    private String bookImage9;
    private ImageCacheService imageCache;
    private SwitchToSceneService switchToSceneService;


    public void setImageCache(ImageCacheService imageCache) {
        this.imageCache = imageCache;
    }

    public List<Book> sortAZ(){
        List<Book> books = apiService.booksFromFile();

        List<Book> sorted = new ArrayList<>(books);
        sorted.sort(Comparator.comparing(Book::getTitle));
        return sorted;
    }

    public List<Book> sortZA(){
        List<Book> books = apiService.booksFromFile();

        List<Book> sorted = new ArrayList<>(books);
        sorted.sort(Comparator.comparing(Book::getTitle).reversed());
        return sorted;
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

    public void page1(List<Label> labellist, List<ImageView> imagelist, boolean a, boolean b, boolean c, ImageCacheService imageCache) {
        if(a == true) {
            List<Book> sortedBooks = sortAZ();
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
        }else if(b == true) {
            List<Book> sortedBooks = sortZA();
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
        } else if (c == true) {
            System.out.println("Sort Rating Clicked");
        } else {
            updateBookDisplay(apiService.booksFromFile(), 1, labellist, imagelist);
        }
    }

    public void page2(List<Label> labellist, List<ImageView> imagelist, boolean a, boolean b, boolean c, ImageCacheService imageCache) {
        if(a == true) {
            List<Book> sortedBooks = sortAZ();
            for (int i = 0; i < labellist.size(); i++) {
                labellist.get(i).setWrapText(true);
                labellist.get(i).setText(sortedBooks.get(i+9).getTitle());
            }

            for (int i = 0; i < imagelist.size(); i++) {
                String url = sortedBooks.get(i+9).getImageLinks();
                if (url != null && !url.isEmpty()) {
                    imagelist.get(i).setImage(imageCache.getImage(sortedBooks.get(i+9).getImageLinks()));
                } else {
                    imagelist.get(i).setImage(null);
                }
            }
        }else if(b == true) {
            List<Book> sortedBooks = sortZA();
            for (int i = 0; i < labellist.size(); i++) {
                labellist.get(i).setWrapText(true);
                labellist.get(i).setText(sortedBooks.get(i+9).getTitle());
            }

            for (int i = 0; i < imagelist.size(); i++) {
                String url = sortedBooks.get(i+9).getImageLinks();
                if (url != null && !url.isEmpty()) {
                    imagelist.get(i).setImage(imageCache.getImage(sortedBooks.get(i+9).getImageLinks()));
                } else {
                    imagelist.get(i).setImage(null);
                }
            }
        } else if (c == true) {
            System.out.println("Sort Rating Clicked");
        } else {
            updateBookDisplay(apiService.booksFromFile(), 2, labellist, imagelist);
        }
    }

    public void page3(List<Label> labellist, List<ImageView> imagelist, boolean a, boolean b, boolean c, ImageCacheService imageCache) {
        if(a == true) {
            List<Book> sortedBooks = sortAZ();
            for (int i = 0; i < labellist.size(); i++) {
                labellist.get(i).setWrapText(true);
                labellist.get(i).setText(sortedBooks.get(i+18).getTitle());
            }

            for (int i = 0; i < imagelist.size(); i++) {
                String url = sortedBooks.get(i+18).getImageLinks();
                if (url != null && !url.isEmpty()) {
                    imagelist.get(i).setImage(imageCache.getImage(sortedBooks.get(i+18).getImageLinks()));
                } else {
                    imagelist.get(i).setImage(null);
                }
            }
        }else if(b == true) {
            List<Book> sortedBooks = sortZA();
            for (int i = 0; i < labellist.size(); i++) {
                labellist.get(i).setWrapText(true);
                labellist.get(i).setText(sortedBooks.get(i+18).getTitle());
            }

            for (int i = 0; i < imagelist.size(); i++) {
                String url = sortedBooks.get(i+18).getImageLinks();
                if (url != null && !url.isEmpty()) {
                    imagelist.get(i).setImage(imageCache.getImage(sortedBooks.get(i+18).getImageLinks()));
                } else {
                    imagelist.get(i).setImage(null);
                }
            }
        } else if (c == true) {
            System.out.println("Sort Rating Clicked");
        } else {
            updateBookDisplay(apiService.booksFromFile(), 3, labellist, imagelist);
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

    public void showSearchBooks(String searchField, AnchorPane anchorPane) {
        List<Book> result = searchBooks(searchField);
        Session session = Session.getInstance();
        User user = session.getUser();

        anchorPane.getChildren().removeIf(node -> node instanceof ImageView);
        anchorPane.getChildren().removeIf(node -> node instanceof Label);
        int rows = (int) Math.ceil(result.size() / 3.0);
        anchorPane.setPrefHeight(608 + (rows - 1) * 400);
        if(result.size() == 0) {
            Label label = new Label();
            label.setText("No matches for your search!");
            label.setLayoutX(60);
            label.setLayoutY(60);
            label.setStyle("-fx-font-size: 24");
            anchorPane.getChildren().add(label);
        }else{
            ImageView[] books = new ImageView[result.size()];
            Label[] labels = new Label[result.size()];
            for (int i = 0; i < result.size(); i++) {
                ImageView imageView = new ImageView();
                Label label = new Label(result.get(i).getTitle());
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
                    imageView.setImage(imageCache.getImage(result.get(i).getImageLinks()));
                } catch (Exception e) {
                    System.out.println("Nesanaca");
                }

                label.setCursor(Cursor.HAND);
                label.setOnMouseClicked(events -> {
                    try {
                        session.setBook(getBookDataByTitle(label.getText()));
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

    }
}
