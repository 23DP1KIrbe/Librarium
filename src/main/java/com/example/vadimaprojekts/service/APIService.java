package com.example.vadimaprojekts.service;


import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class APIService {
    private String title;
    private String description;
    private List<String> authors;
    private List<JsonElement> industryIdentifiers;
    private List<String> categories;
    private JsonObject imageLinks;
    private String language;


//    public APIService(String id, String title, String description, String author, String industryIdentifiers, String categories, String imageLinks, String language, String saleInfo) {
//        this.id = id;
//        this.title = title;
//        this.description = description;
//        this.author = author;
//        this.industryIdentifiers = industryIdentifiers;
//        this.categories = categories;
//        this.imageLinks = imageLinks;
//        this.language = language;
//        this.saleInfo = saleInfo;
//    }




    public void saveBook() {

        try {
            String isbn = "9783732508686";
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


                JsonArray industryIdentifiersArray = volumeInfo.getAsJsonArray("industryIdentifiers");

                if (industryIdentifiersArray != null && industryIdentifiersArray.size() > 0) {
                    JsonObject firstIndustryObj = industryIdentifiersArray.get(0).getAsJsonObject();
                    String firstIdentifier = firstIndustryObj.get("identifier").getAsString();

                    // Replace the original array with a new array containing only the first identifier
                    JsonArray newIdentifiersArray = new JsonArray();
                    newIdentifiersArray.add(firstIdentifier);
                    volumeInfo.add("industryIdentifiers", newIdentifiersArray);
                }


                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                List<APIService> book = new ArrayList<>();
                Gson gsonu = new Gson();
                APIService apiServis = gsonu.fromJson(volumeInfo, APIService.class);

                try (FileReader readers = new FileReader("./books.json")) {
                    book = gson.fromJson(readers, new TypeToken<List<APIService>>() {}.getType());
                    if (book == null){
                        book = new ArrayList<>();
                    }
                    book.add(apiServis);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try (FileWriter writer = new FileWriter("./books.json", false)) {
                    gson.toJson(book, writer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
