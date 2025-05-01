package com.example.vadimaprojekts.controllers;


import com.example.vadimaprojekts.exceptions.NotValidPasswordException;
import com.example.vadimaprojekts.exceptions.NotValidUsernameException;
import com.example.vadimaprojekts.exceptions.UserExistsException;
import com.example.vadimaprojekts.module.User;
import com.example.vadimaprojekts.service.RegisterService;
import com.example.vadimaprojekts.service.SwitchToSceneService;
import com.example.vadimaprojekts.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegisterController {
    @FXML
    private TextField registerUsername;
    @FXML
    private PasswordField registerPassword;


    @FXML
    private Label error;


    private RegisterService registerService = new RegisterService();
    private UserService userService = new UserService();
    private SwitchToSceneService switchToSceneService;

    public RegisterController() {
        switchToSceneService = new SwitchToSceneService();
    }


    @FXML
    protected void onRegisterButtonClick(ActionEvent event) throws IOException {
        String username = registerUsername.getText();
        String password = registerPassword.getText();
        List<String> ReadList = new ArrayList();
        List<String> BuyList = new ArrayList<>();
        boolean usernameValid = true;
        boolean passwordValid = true;

        try {
            registerService.validateUsername(username);
        } catch (NotValidUsernameException e) {
            error.setStyle("-fx-text-fill: red");
            error.setText(e.getMessage());
            usernameValid = false;
        }

        try {
            registerService.validatePassword(password);
        } catch (NotValidPasswordException e) {
            error.setStyle("-fx-text-fill: red");
            error.setText(e.getMessage());
            passwordValid = false;
        }

        if (usernameValid && passwordValid) {
            User user = new User(username, password, ReadList, BuyList);
            try {
                userService.saveUserToJson(user);
            } catch (UserExistsException e) {
                error.setStyle("-fx-text-fill: red");
                error.setText(e.getMessage());
            }
            error.setStyle("-fx-text-fill: green");
            error.setText("Account created successfully");
        }
    }

    @FXML
    protected void onHaveAnAccountClick(ActionEvent event) throws IOException {
        switchToSceneService.switchToLogin();
    }
}
