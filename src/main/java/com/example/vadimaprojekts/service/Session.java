package com.example.vadimaprojekts.service;

import com.example.vadimaprojekts.module.Book;
import com.example.vadimaprojekts.module.User;

public class Session {
    private User user;
    private Book book;
    private ImageCacheService imageCache;
    private boolean loaded;
    private boolean editingUsername;
    private boolean editingPassword;
    private int currentPage;
    public static Session session;

    public Session() {
        user = null;
        book = null;
        loaded = false;
        editingUsername = false;
        editingPassword = false;
        currentPage = 1;
    }

    public static Session getInstance() {
        if (session == null) {
            session = new Session();
        }
        return session;
    }

    public void setEditingUsername(boolean editingUsername) {
        this.editingUsername = editingUsername;
    }

    public void setEditingPassword(boolean editingPassword) {
        this.editingPassword = editingPassword;
    }

    public boolean getEditingUsername() {
        return editingUsername;
    }

    public boolean getEditingPassword() {
        return editingPassword;
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

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setImageCache(ImageCacheService imageCache) {
        this.imageCache = imageCache;
    }

    public ImageCacheService getImageCache() {
        return imageCache;
    }

    public void clearImageCache() {
        this.imageCache = null;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
