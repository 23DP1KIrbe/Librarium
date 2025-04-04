package com.example.vadimaprojekts.service;

import com.example.vadimaprojekts.module.User;
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
    private static final String File = "./users.json";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public List<User> usersFromFile(){
        List<User> users = new ArrayList();
        try (FileReader reader = new FileReader(File)) {
            users = gson.fromJson(reader, new TypeToken<List<User>>() {}.getType());
            return (users != null) ? users : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean checkForUsername(String username){
        List<User> users = usersFromFile();
        return users.stream().anyMatch(user -> user.getUsername().equals(username));
    }

    public User getUsernameData(String username) throws UserNotFoundException {
        return usersFromFile().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("No such username"));

    }



    public void saveUserToJson(User user) throws UserExistsException {
        List<User> users = new ArrayList<>();
        try (FileReader reader = new FileReader("./users.json")) {
            users = gson.fromJson(reader, new TypeToken<List<User>>() {}.getType());
            if (users == null){
                users = new ArrayList<>();
            }
            if(checkForUsername(user.getUsername())){
                throw new UserExistsException("Username is already taken!");
            }
            users.add(user);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter("./users.json", false)) {
            gson.toJson(users, writer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
