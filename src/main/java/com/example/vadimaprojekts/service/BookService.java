package com.example.vadimaprojekts.service;

import com.example.vadimaprojekts.exceptions.UserNotFoundException;
import com.example.vadimaprojekts.module.Book;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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



    public BookService(String book1Text, String bookText2, String bookText3, String bookText4, String bookText5,
                       String bookText6, String bookText7, String bookText8, String bookText9) {
        this.book1Text = book1Text;
        this.bookText2 = bookText2;
        this.bookText3 = bookText3;
        this.bookText4 = bookText4;
        this.bookText5 = bookText5;
        this.bookText6 = bookText6;
        this.bookText7 = bookText7;
        this.bookText8 = bookText8;
        this.bookText9 = bookText9;

    }

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
    public Book getBookData(String id) throws UserNotFoundException {
        return apiService.booksFromFile().stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("i guess didnt find the book"));

    }


    public void updateBookDisplay(List<Book> books, int index, List<Label> labellist, List<ImageView> imagelist) {
        if(index == 1){
            for (int i = 0; i < labellist.size(); i++) {
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
                labellist.get(i).setText(books.get(i+18).getTitle());
            }

            for (int i = 0; i < imagelist.size(); i++) {
                String url = books.get(i+18).getImageLinks();
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
}
