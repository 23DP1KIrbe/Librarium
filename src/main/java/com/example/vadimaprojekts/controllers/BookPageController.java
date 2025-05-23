package com.example.vadimaprojekts.controllers;

import com.example.vadimaprojekts.exceptions.UserNotFoundException;
import com.example.vadimaprojekts.module.Book;
import com.example.vadimaprojekts.service.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BookPageController implements Initializable {
    @FXML
    private ImageView bookImage;
    @FXML
    private Label bookTitle, bookDescription, bookAuthor, readersLabel, buyersLabel;
    @FXML
    private Button buyListBtn;
    @FXML
    private Button readListBtn;
    @FXML
    private Button backToLibrary;
    private String bookVar;
    private Session session = Session.getInstance();
    private APIService apiService = new APIService();
    private UserService userService = new UserService();
    private BookService bookService = new BookService();
    private ImageCacheService imageCache;
    private SwitchToSceneService switchToSceneService = new SwitchToSceneService();


    public void setImageCache(ImageCacheService imageCache) {
        this.imageCache = imageCache;
    }

    public void loadBook(String Title){
        this.bookVar = Title;
        Book book = bookService.getBookDataByTitle(bookVar);
        bookTitle.setText(book.getTitle());
        bookDescription.setWrapText(true);
        bookDescription.setText(book.getDescription());
        bookAuthor.setText(toString().valueOf(book.getAuthors()));
        bookImage.setImage(new Image(book.getImageLinks()));
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        Book book = session.getBook();
        if (session.getUser().getReadList().contains(book.getId())) {
            readListBtn.setText("Remove book from read list");
        }
        if(session.getUser().getBuyList().contains(book.getId())){
            buyListBtn.setText("Remove book from buy list");
        }
        imageCache = session.getImageCache();
        readersLabel.setText("Interested readers: " + session.getBook().getTotalReaders().size());
        buyersLabel.setText("Interested buyers: " + session.getBook().getTotalBuyers().size());
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
    public void onbackToLibraryClick(ActionEvent event) throws IOException {
        session.setBook(null);
        switchToSceneService.switchToLibrary();
    }
    @FXML
    public void onreadListBtnClick(ActionEvent event) throws IOException {
        Book book = session.getBook();
        userService.addBookToReadList(book.getId());
        if(session.getUser().getReadList().contains(book.getId())){
            apiService.addTotalReadersToBook(session.getUser().getUsername());
            readersLabel.setText("Interested readers: " + session.getBook().getTotalReaders().size());
            readListBtn.setText("Remove book from read list");
        }else if(!session.getUser().getReadList().contains(book.getId())){
            apiService.addTotalReadersToBook(session.getUser().getUsername());
            readersLabel.setText("Interested readers: " + session.getBook().getTotalReaders().size());
            readListBtn.setText("Add to read list");
        }
    }
    @FXML
    public void onbuyListBtnClick(ActionEvent event) throws IOException {
        Book book = session.getBook();
        userService.addBookToBuyList(book.getId());
        if(session.getUser().getBuyList().contains(book.getId())){
            apiService.addTotalBuyersToBook(session.getUser().getUsername());
            buyersLabel.setText("Interested buyers: " + session.getBook().getTotalBuyers().size());
            buyListBtn.setText("Remove book from buy list");
        }else if(!session.getUser().getBuyList().contains(book.getId())){
            apiService.addTotalBuyersToBook(session.getUser().getUsername());
            buyersLabel.setText("Interested buyers: " + session.getBook().getTotalBuyers().size());
            buyListBtn.setText("Add to buy list");
        }
    }
}
