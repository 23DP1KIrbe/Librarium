package com.example.vadimaprojekts.service;

import com.example.vadimaprojekts.module.Book;
import com.example.vadimaprojekts.module.User;

public class Session {
    private User user;
    private Book book;
    private static Session session;

    public Session() {
        user = null;
    }

    public static Session getInstance() {
        if (session == null) {
            session = new Session();
        }
        return session;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Book getBook() {
        return book;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
