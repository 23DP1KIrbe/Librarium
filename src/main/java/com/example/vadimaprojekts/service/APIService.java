package com.example.vadimaprojekts.service;


import com.example.vadimaprojekts.exceptions.UserNotFoundException;
import com.example.vadimaprojekts.module.Book;
import com.example.vadimaprojekts.module.User;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class APIService {
    private static final String File = "./books.json";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private Session session = Session.getInstance();

    public void addTotalReadersToBook(String username) {
        Book sessionBook = Session.getInstance().getBook();
        if (sessionBook == null) return;

        List<Book> books = booksFromFile();

        for (Book book : books) {
            if (book.getTitle().equals(sessionBook.getTitle())) {
                if(book.getTotalReaders().contains(username)){
                    book.getTotalReaders().remove(username);
                    session.setBook(book);
                }else if (!book.getTotalReaders().contains(username)) {
                    book.getTotalReaders().add(username);
                    session.setBook(book);
                }
                break;
            }
        }

        try (FileWriter writer = new FileWriter(File)) {
            gson.toJson(books, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addTotalBuyersToBook(String username) {
        Book sessionBook = Session.getInstance().getBook();
        if (sessionBook == null) return;

        List<Book> books = booksFromFile();

        for (Book book : books) {
            if (book.getTitle().equals(sessionBook.getTitle())) {
                if(book.getTotalBuyers().contains(username)){
                    book.getTotalBuyers().remove(username);
                    session.setBook(book);
                }else if (!book.getTotalBuyers().contains(username)) {
                    book.getTotalBuyers().add(username);
                    session.setBook(book);
                }
                break;
            }
        }

        try (FileWriter writer = new FileWriter(File)) {
            gson.toJson(books, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Book> booksFromFile(){
        List<Book> books = new ArrayList();
        try (FileReader reader = new FileReader(File)) {
            books = gson.fromJson(reader, new TypeToken<List<Book>>() {}.getType());
            return (books != null) ? books : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }

    public String getBookId() {
        int maxId = 0;
        for (Book book : booksFromFile()) {
            if (book != null) {
                try {
                    int id = Integer.parseInt(book.getId());
                    if (id > maxId) {
                        maxId = id;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid book ID format: " + book.getId());
                }
            }
        }
        return String.valueOf(maxId + 1);
    }


    public void saveBook(String isbn) {
        try {

            URL url = new URL("https://www.googleapis.com/books/v1/volumes?q=isbn:"+ isbn);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();


            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
                JsonArray booksArray = jsonResponse.getAsJsonArray("items");
                JsonObject bookInfo = (JsonObject) booksArray.get(0);
                JsonObject volumeInfo =(JsonObject) bookInfo.get("volumeInfo");

                String title = volumeInfo.get("title").getAsString();
                String description = volumeInfo.get("description").getAsString();
                List<String> authors = new ArrayList<>();
                JsonArray authorsArray = volumeInfo.getAsJsonArray("authors");
                for (JsonElement author : authorsArray) {
                    authors.add(author.getAsString());
                }
                List<String> categories = new ArrayList<>();
                JsonArray categoriesArray = volumeInfo.getAsJsonArray("categories");
                for (JsonElement category : categoriesArray) {
                    categories.add(category.getAsString());
                }

                String language = volumeInfo.get("language").getAsString();

                JsonObject imageLink = volumeInfo.getAsJsonObject("imageLinks");
                String selectedImageLink = null;
                if (imageLink != null) {
                    if (imageLink.has("thumbnail")) {
                        selectedImageLink = imageLink.get("thumbnail").getAsString();
                    } else if (imageLink.has("smallThumbnail")) {
                        selectedImageLink = imageLink.get("smallThumbnail").getAsString();
                    }
                }


                List<String> industryIdentifiers = new ArrayList<>();
                if (volumeInfo.has("industryIdentifiers")) {
                    JsonArray industryArray = volumeInfo.getAsJsonArray("industryIdentifiers");
                    for (int i = 0; i < industryArray.size(); i++) {
                        JsonObject identifierObj = industryArray.get(i).getAsJsonObject();
                        if (identifierObj.has("type") && identifierObj.has("identifier")) {
                            String type = identifierObj.get("type").getAsString();
                            if ("ISBN_13".equals(type)) {
                                industryIdentifiers.add(identifierObj.get("identifier").getAsString());
                            }
                        }
                    }
                }

                String id = getBookId();

                List<String> totalReaders = new ArrayList<>();
                List<String> totalBuyers = new ArrayList<>();

                Book book = new Book(title, description, authors, industryIdentifiers, categories, selectedImageLink, language, id, totalReaders, totalBuyers);
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                List<Book> bookList = new ArrayList<>();


                try (FileReader readers = new FileReader("./books.json")) {
                    bookList = gson.fromJson(readers, new TypeToken<List<Book>>() {}.getType());
                    if (bookList == null){
                        bookList = new ArrayList<>();
                    }
                    bookList.add(book);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try (FileWriter writer = new FileWriter("./books.json", false)) {
                    gson.toJson(bookList, writer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
