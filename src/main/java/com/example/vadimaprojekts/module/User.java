package com.example.vadimaprojekts.module;

import com.example.vadimaprojekts.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private List<String> ReadList;
    private List<String> BuyList;

    public User(String username, String password, List<String> Readlist, List<String> Buylist) {
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

    public List<String> getReadList() {
        if (ReadList == null) ReadList = new ArrayList<>();
        return ReadList;
    }

    public void setReadList(List<String> Readlist) {
        this.ReadList = Readlist;
    }

    public List<String> getBuyList() {
        if (BuyList == null) BuyList = new ArrayList<>();
        return BuyList;
    }

    public void setBuyList(List<String> Buylist) {
        this.BuyList = Buylist;
    }

}
