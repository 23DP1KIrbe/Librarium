package com.example.vadimaprojekts.controllers;


import com.example.vadimaprojekts.exceptions.UserExistsException;
import com.example.vadimaprojekts.service.RegisterService;
import com.example.vadimaprojekts.service.SwitchToLoginService;
import com.example.vadimaprojekts.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegisterController {
    @FXML
    private TextField registerUsername;
    @FXML
    private TextField registerPassword;


    @FXML
    private Label error;


    private RegisterService registerService;
    private UserService userService;
    private SwitchToLoginService switchToLoginService;

    public RegisterController() {
        this.registerService = new RegisterService();
        this.switchToLoginService = new SwitchToLoginService();
    }


    @FXML
    protected void onRegisterButtonClick(ActionEvent event) throws IOException, UserExistsException {

        String username = registerUsername.getText();
        String password = registerPassword.getText();
        userService = new UserService(username, password);

        try {
            userService.saveUserToJson(userService);
        } catch (UserExistsException e) {
            error.setText(e.getMessage());
        }



    }

    @FXML
    protected void onHaveAnAccountClick(ActionEvent event) throws IOException {
        switchToLoginService.switchToLogin();
    }
}
