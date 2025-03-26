package com.example.vadimaprojekts.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.example.vadimaprojekts.exceptions.UserExistsException;
import com.example.vadimaprojekts.exceptions.UserNotFoundException;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



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


    public List usersFromFile(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<UserService> users = new ArrayList();
        try (FileReader reader = new FileReader("./users.json")) {
            users = gson.fromJson(reader, new TypeToken<List<UserService>>() {}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean checkForUsername(String username){
        List<UserService> users = usersFromFile();
        return users.stream().anyMatch(user -> user.getUsername().equals(username));
    }

    public UserService getUsernameData(String username) throws UserNotFoundException {
        List<UserService> users = usersFromFile();
        return users.stream().filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("No such username"));

    }


    public void saveUserToJson(UserService userService) throws UserExistsException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<UserService> users = new ArrayList<>();


        try (FileReader reader = new FileReader("./users.json")) {
            users = gson.fromJson(reader, new TypeToken<List<UserService>>() {}.getType());
            if (users == null){
                users = new ArrayList<>();
            }
            if(userService.checkForUsername(userService.getUsername())){
                throw new UserExistsException("User exists");
            }
            users.add(userService);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter("./users.json", false)) {
            gson.toJson(users, writer);

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
