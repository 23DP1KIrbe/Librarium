package com.example.vadimaprojekts.service;

import com.example.vadimaprojekts.exceptions.UserNotFoundException;
import com.example.vadimaprojekts.exceptions.WrongUsernameOrPasswordException;

public class LoginService {
    private UserService userService;


    public boolean authenticate(String username, String password) throws UserNotFoundException, WrongUsernameOrPasswordException {
        userService = new UserService(username, password);
        if (userService.checkForUsername(username)) {
            if(password.equals(userService.getUsernameData(username).getPassword())){
                return true;
            }

        }
    throw new WrongUsernameOrPasswordException("Wrong username or password!");
    }
}
