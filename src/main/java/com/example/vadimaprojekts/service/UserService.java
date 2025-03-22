package com.example.vadimaprojekts.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class UserService {

    private String username;
    private String password;

    public UserService(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public void saveUserToJson(UserService userService){
        Gson gson = new Gson();
        String json = gson.toJson(userService);


        try (FileWriter writer = new FileWriter("./users.json", true)) {

            System.out.println("Working Directory: " + System.getProperty("users.dir"));
            writer.write(json);
            writer.flush();
            System.out.println("JSON written successfully.");
            System.out.println("JSON data: " + json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public UserService loadUserFromJson(){
        Gson gson = new Gson();
        try (FileReader reader = new FileReader("users.json")) {
            // Deserialize the JSON back into a User object
            UserService user = gson.fromJson(reader, UserService.class);
            return user;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
