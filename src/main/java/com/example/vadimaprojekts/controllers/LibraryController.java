package com.example.vadimaprojekts.controllers;

import com.example.vadimaprojekts.exceptions.UserNotFoundException;
import com.example.vadimaprojekts.module.Book;
import com.example.vadimaprojekts.service.APIService;
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

    SwitchToSceneService switchToSceneService = new SwitchToSceneService();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup group1 = new ToggleGroup();
        sortAZ.setToggleGroup(group1);
        sortZA.setToggleGroup(group1);
        sortRating.setToggleGroup(group1);

        this.labellist = new ArrayList<>();
        labellist.add(bookText1);
        labellist.add(bookText2);
        labellist.add(bookText3);
        labellist.add(bookText4);
        labellist.add(bookText5);
        labellist.add(bookText6);
        labellist.add(bookText7);
        labellist.add(bookText8);
        labellist.add(bookText9);

        this.imagelist = new ArrayList<>();
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
        this.bookService = new BookService(book1Text, book2Text, book3Text, book4Text, book5Text,
                book6Text, book7Text, book8Text, book9Text);
        APIService apiService = new APIService();
        this.originalBooks = apiService.booksFromFile();

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
        progressIndicator.setVisible(true);
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

        loadBooksTask.setOnSucceeded(e -> progressIndicator.setVisible(false));
        loadBooksTask.setOnFailed(e -> progressIndicator.setVisible(false));
        new Thread(loadBooksTask).start();


    }

    private void updateBookDisplay(List<Book> books, int index) {
        if(index == 1){
            for (int i = 0; i < labellist.size(); i++) {
                labellist.get(i).setText(books.get(i).getTitle());
            }

            for (int i = 0; i < imagelist.size(); i++) {
                String url = books.get(i).getImageLinks();
                if (url != null && !url.isEmpty()) {
                    imagelist.get(i).setImage(new Image(url));
                } else {
                    imagelist.get(i).setImage(null);
                }
            }
        }else if(index == 2){
            for (int i = 0; i < labellist.size(); i++) {
                labellist.get(i).setText(books.get(i+9).getTitle());
            }

            for (int i = 0; i < imagelist.size(); i++) {
                String url = books.get(i+9).getImageLinks();
                if (url != null && !url.isEmpty()) {
                    imagelist.get(i).setImage(new Image(url));
                } else {
                    imagelist.get(i).setImage(null);
                }
            }
        }else if(index == 3){
            for (int i = 0; i < labellist.size(); i++) {
                labellist.get(i).setText(books.get(i+18).getTitle());
            }

            for (int i = 0; i < imagelist.size(); i++) {
                String url = books.get(i+18).getImageLinks();
                if (url != null && !url.isEmpty()) {
                    imagelist.get(i).setImage(new Image(url));
                } else {
                    imagelist.get(i).setImage(null);
                }
            }
        }
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
            labellist.get(i).setText(sortedBooks.get(i).getTitle());
        }

        for (int i = 0; i < imagelist.size(); i++) {
            String url = sortedBooks.get(i).getImageLinks();
            if (url != null && !url.isEmpty()) {
                imagelist.get(i).setImage(new Image(url));
            } else {
                imagelist.get(i).setImage(null);
            }
        }
        System.out.println("Sort AZ Clicked");
    }

    @FXML
    public void onsortZAClick(ActionEvent event) throws IOException {
        List<Book> sortedBooks = bookService.sortZA();
        for (int i = 0; i < labellist.size(); i++) {
            labellist.get(i).setText(sortedBooks.get(i).getTitle());
        }

        for (int i = 0; i < imagelist.size(); i++) {
            String url = sortedBooks.get(i).getImageLinks();
            if (url != null && !url.isEmpty()) {
                imagelist.get(i).setImage(new Image(url));
            } else {
                imagelist.get(i).setImage(null);
            }
        }
        System.out.println("Sort ZA Clicked");
    }

    @FXML
    public void onsortRatingClick(ActionEvent event) throws IOException {
        System.out.println("Sort Rating Clicked");
    }

    @FXML
    public void onPage1Click(ActionEvent event) throws IOException {
        if(sortAZ.isSelected()) {
            List<Book> sortedBooks = bookService.sortAZ();
            for (int i = 0; i < labellist.size(); i++) {
                labellist.get(i).setText(sortedBooks.get(i).getTitle());
            }

            for (int i = 0; i < imagelist.size(); i++) {
                String url = sortedBooks.get(i).getImageLinks();
                if (url != null && !url.isEmpty()) {
                    imagelist.get(i).setImage(new Image(url));
                } else {
                    imagelist.get(i).setImage(null);
                }
            }
        }else if(sortZA.isSelected()) {
            List<Book> sortedBooks = bookService.sortZA();
            for (int i = 0; i < labellist.size(); i++) {
                labellist.get(i).setText(sortedBooks.get(i).getTitle());
            }

            for (int i = 0; i < imagelist.size(); i++) {
                String url = sortedBooks.get(i).getImageLinks();
                if (url != null && !url.isEmpty()) {
                    imagelist.get(i).setImage(new Image(url));
                } else {
                    imagelist.get(i).setImage(null);
                }
            }
        } else if (sortRating.isSelected()) {
            System.out.println("Sort Rating Clicked");
        } else {
            updateBookDisplay(originalBooks, 1);
        }
    }

    @FXML
    public void onPage2Click(ActionEvent event) throws IOException {
        if(sortAZ.isSelected()) {
            List<Book> sortedBooks = bookService.sortAZ();
            for (int i = 0; i < imagelist.size(); i++) {
                labellist.get(i).setText(sortedBooks.get(i+9).getTitle());
            }
            for (int i = 0; i < imagelist.size(); i++) {
                String url = sortedBooks.get(i+9).getImageLinks();
                if (url != null && !url.isEmpty()) {
                    imagelist.get(i).setImage(new Image(url));
                } else {
                    imagelist.get(i).setImage(null);
                }
            }
        }else if(sortZA.isSelected()) {
            List<Book> sortedBooks = bookService.sortZA();
            for (int i = 8; i < labellist.size(); i++) {
                labellist.get(i).setText(sortedBooks.get(i+9).getTitle());
            }

            for (int i = 8; i < imagelist.size(); i++) {
                String url = sortedBooks.get(i+9).getImageLinks();
                if (url != null && !url.isEmpty()) {
                    imagelist.get(i).setImage(new Image(url));
                } else {
                    imagelist.get(i).setImage(null);
                }
            }
        } else if (sortRating.isSelected()) {
            System.out.println("Sort Rating Clicked");
        } else {
            updateBookDisplay(originalBooks, 2);
        }
    }

    @FXML
    public void onPage3Click(ActionEvent event) throws IOException {
        if(sortAZ.isSelected()) {
            List<Book> sortedBooks = bookService.sortAZ();
            for (int i = 0; i < labellist.size(); i++) {
                labellist.get(i).setText(sortedBooks.get(i+18).getTitle());
            }

            for (int i = 0; i < imagelist.size(); i++) {
                String url = sortedBooks.get(i+18).getImageLinks();
                if (url != null && !url.isEmpty()) {
                    imagelist.get(i).setImage(new Image(url));
                } else {
                    imagelist.get(i).setImage(null);
                }
            }
        }else if(sortZA.isSelected()) {
            List<Book> sortedBooks = bookService.sortZA();
            for (int i = 0; i < labellist.size(); i++) {
                labellist.get(i).setText(sortedBooks.get(i+18).getTitle());
            }

            for (int i = 0; i < imagelist.size(); i++) {
                String url = sortedBooks.get(i+18).getImageLinks();
                if (url != null && !url.isEmpty()) {
                    imagelist.get(i).setImage(new Image(url));
                } else {
                    imagelist.get(i).setImage(null);
                }
            }
        } else if (sortRating.isSelected()) {
            System.out.println("Sort Rating Clicked");
        } else {
            updateBookDisplay(originalBooks, 3);
        }
    }

}
