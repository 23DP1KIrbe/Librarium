package com.example.vadimaprojekts.module;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Book {
    private String title;
    private String description;
    private List<String> authors;
    private List<String> industryIdentifiers;
    private List<String> categories;
    private String imageLinks;
    private String language;
    private String id;
    private List<String> totalReaders;
    private List<String> totalBuyers;

    public Book(String title, String description, List<String> authors, List<String> industryIdentifiers, List<String> categories, String imageLinks, String language, String id, List<String> totalReaders, List<String> totalBuyers) {
        this.title = title;
        this.description = description;
        this.authors = authors;
        this.industryIdentifiers = industryIdentifiers;
        this.categories = categories;
        this.imageLinks = imageLinks;
        this.language = language;
        this.id = id;
        this.totalReaders = totalReaders;
        this.totalBuyers = totalBuyers;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public List<String> getIndustryIdentifiers() {
        return industryIdentifiers;
    }

    public List<String> getCategories() {
        return categories;
    }

    public String getImageLinks() {
        return imageLinks;
    }

    public String getLanguage() {
        return language;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public void setIndustryIdentifiers(List<String> industryIdentifiers) {
        this.industryIdentifiers = industryIdentifiers;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public void setImageLinks(String imageLinks) {
        this.imageLinks = imageLinks;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public void setTotalReaders(List<String> totalReaders) {
        this.totalReaders = totalReaders;
    }

    public void setTotalBuyers(List<String> totalBuyers) {
        this.totalBuyers = totalBuyers;
    }

    public List<String> getTotalReaders() {
        return totalReaders;
    }

    public List<String> getTotalBuyers() {
        return totalBuyers;
    }
}
