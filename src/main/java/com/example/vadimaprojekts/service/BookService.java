package com.example.vadimaprojekts.service;

import com.example.vadimaprojekts.exceptions.UserNotFoundException;
import com.example.vadimaprojekts.module.Book;
import com.example.vadimaprojekts.module.User;

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


    public BookService(String book1Text, String bookText2, String bookText3, String bookText4, String bookText5,
                       String bookText6, String bookText7, String bookText8, String bookText9, String bookImage1,
                       String bookImage2, String bookImage3, String bookImage4, String bookImage5, String bookImage6,
                       String bookImage7, String bookImage8, String bookImage9) {
        this.book1Text = book1Text;
        this.bookText2 = bookText2;
        this.bookText3 = bookText3;
        this.bookText4 = bookText4;
        this.bookText5 = bookText5;
        this.bookText6 = bookText6;
        this.bookText7 = bookText7;
        this.bookText8 = bookText8;
        this.bookText9 = bookText9;
        this.bookImage1 = bookImage1;
        this.bookImage2 = bookImage2;
        this.bookImage3 = bookImage3;
        this.bookImage4 = bookImage4;
        this.bookImage5 = bookImage5;
        this.bookImage6 = bookImage6;
        this.bookImage7 = bookImage7;
        this.bookImage8 = bookImage8;
        this.bookImage9 = bookImage9;
    }
    APIService apiService = new APIService();
    public Book getBookData(String id) throws UserNotFoundException {
        return apiService.booksFromFile().stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("i guess didnt find the book"));

    }
    public String loadBookInfo(String id) throws UserNotFoundException {

        return getBookData(id).getTitle();
    }
}
