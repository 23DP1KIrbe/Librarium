package com.example.vadimaprojekts.service;

import com.example.vadimaprojekts.exceptions.UserNotFoundException;
import com.example.vadimaprojekts.exceptions.WrongUsernameOrPasswordException;
import com.example.vadimaprojekts.module.User;

import java.util.ArrayList;
import java.util.List;

public class LoginService {

    private UserService userService;

    public LoginService() {
        this.userService = new UserService();
    }

    public boolean authenticate(String username, String password) throws UserNotFoundException, WrongUsernameOrPasswordException {
        if (userService.checkForUsername(username)) {
            if(password.equals(userService.getUsernameData(username).getPassword())){
                return true;
            }

        }
    throw new WrongUsernameOrPasswordException("Wrong username or password!");
    }
}
