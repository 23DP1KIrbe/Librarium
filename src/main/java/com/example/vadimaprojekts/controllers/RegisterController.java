package com.example.vadimaprojekts.controllers;


import com.example.vadimaprojekts.exceptions.UserExistsException;
import com.example.vadimaprojekts.module.User;
import com.example.vadimaprojekts.service.RegisterService;
import com.example.vadimaprojekts.service.SwitchToSceneService;
import com.example.vadimaprojekts.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegisterController {
    @FXML
    private TextField registerUsername;
    @FXML
    private TextField registerPassword;


    @FXML
    private Label error;


    private RegisterService registerService;
    private UserService userService = new UserService();
    private SwitchToSceneService switchToSceneService;

    public RegisterController() {
        switchToSceneService = new SwitchToSceneService();
    }


    @FXML
    protected void onRegisterButtonClick(ActionEvent event) throws IOException, UserExistsException {

        String username = registerUsername.getText();
        String password = registerPassword.getText();
        List<String> ReadList = new ArrayList();
        List<String> BuyList = new ArrayList<>();
        User user = new User(username, password, ReadList, BuyList);

        try {
            userService.saveUserToJson(user);
        } catch (UserExistsException e) {
            error.setText(e.getMessage());
        }



    }

    @FXML
    protected void onHaveAnAccountClick(ActionEvent event) throws IOException {
        switchToSceneService.switchToLogin();
    }
}
