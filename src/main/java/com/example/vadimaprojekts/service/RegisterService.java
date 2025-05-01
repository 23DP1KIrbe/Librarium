package com.example.vadimaprojekts.service;

import com.example.vadimaprojekts.exceptions.NotValidPasswordException;
import com.example.vadimaprojekts.exceptions.NotValidUsernameException;

public class RegisterService {

    public RegisterService() {
    }

    public void validatePassword(String password) throws NotValidPasswordException {
        String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$";
        if (!password.matches(passwordRegex)) {
            throw new NotValidPasswordException("Not a valid password");
        }
    }

    public void validateUsername(String username) throws NotValidUsernameException {
        String usernameRegex = "^(?=.{8,20}$)(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";
        if (!username.matches(usernameRegex)) {
            throw new NotValidUsernameException("Not a valid username");
        }
    }
}
