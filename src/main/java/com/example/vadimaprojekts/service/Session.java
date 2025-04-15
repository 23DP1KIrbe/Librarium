package com.example.vadimaprojekts.service;

import com.example.vadimaprojekts.module.User;

public class Session {
    private User user;
    private static Session session;

    public Session() {
        user = null;
    }

    public static Session getInstance() {
        if (session == null) {
            session = new Session();
        }
        return session;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
