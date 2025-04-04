package com.example.vadimaprojekts.service;

public class BookService {
    private String bookText1;
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


    public BookService(String bookText1, String bookText2, String bookText3, String bookText4, String bookText5,
                       String bookText6, String bookText7, String bookText8, String bookText9, String bookImage1,
                       String bookImage2, String bookImage3, String bookImage4, String bookImage5, String bookImage6,
                       String bookImage7, String bookImage8, String bookImage9) {
        this.bookText1 = bookText1;
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

    public void loadBookInfo(){
        APIService apiService = new APIService();

    }
}
