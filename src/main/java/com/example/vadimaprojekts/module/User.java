package com.example.vadimaprojekts.module;

import com.example.vadimaprojekts.service.UserService;

import java.util.List;

public class User {
    private String username;
    private String password;
    private List<User> ReadList;
    private List<User> BuyList;

    public User(String username, String password, List<User> Readlist, List<User> Buylist) {
        this.username = username;
        this.password = password;
        this.ReadList = Readlist;
        this.BuyList = Buylist;
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

    public List<User> getReadList() {
        return ReadList;
    }

    public void setReadList(List<User> Readlist) {
        this.ReadList = Readlist;
    }

    public List<User> getBuyList() {
        return BuyList;
    }

    public void setBuyList(List<User> Buylist) {
        this.BuyList = Buylist;
    }
}
