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
    private Session session = Session.getInstance();

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

    public void editUserToJson(String username) throws UserExistsException {
        User sessionUser = Session.getInstance().getUser();
        if (sessionUser == null) return;
        if(checkForUsername(username)){
            throw new UserExistsException("Username is already taken!");
        }
        List<User> users = usersFromFile();

        for (User user : users) {
            if (user.getUsername().equals(sessionUser.getUsername())) {
                user.setUsername(username);
                break;
            }
        }
        try (FileWriter writer = new FileWriter(File)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void addBookToReadList(String bookId) {
        User sessionUser = Session.getInstance().getUser();
        if (sessionUser == null) return;

        List<User> users = usersFromFile();

        for (User user : users) {
            if (user.getUsername().equals(sessionUser.getUsername())) {
                if(user.getReadList().contains(bookId)){
                    user.getReadList().remove(bookId);
                    session.setUser(user);
                }else if (!user.getReadList().contains(bookId)) {
                    user.getReadList().add(bookId);
                    session.setUser(user);
                }
                break;
            }
        }

        try (FileWriter writer = new FileWriter(File)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addBookToBuyList(String bookId) {
        User sessionUser = Session.getInstance().getUser();
        if (sessionUser == null) return;

        List<User> users = usersFromFile();

        for (User user : users) {
            if (user.getUsername().equals(sessionUser.getUsername())) {
                if(user.getBuyList().contains(bookId)){
                    user.getBuyList().remove(bookId);
                    session.setUser(user);
                }else if (!user.getBuyList().contains(bookId)) {
                    user.getBuyList().add(bookId);
                    session.setUser(user);
                }
                break;
            }
        }
        try (FileWriter writer = new FileWriter(File)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
