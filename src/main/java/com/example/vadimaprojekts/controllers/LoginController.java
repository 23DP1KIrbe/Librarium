package com.example.vadimaprojekts.controllers;



import com.example.vadimaprojekts.exceptions.UserNotFoundException;
import com.example.vadimaprojekts.exceptions.WrongUsernameOrPasswordException;
import com.example.vadimaprojekts.service.SwitchToSceneService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.example.vadimaprojekts.service.LoginService;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label wrongInfo;

    private LoginService loginService;
    private SwitchToSceneService switchToSceneService;


    public LoginController() {
        this.loginService = new LoginService();
        this.switchToSceneService = new SwitchToSceneService();
    }


    @FXML
    protected void onLoginButtonClick(ActionEvent event) throws IOException, UserNotFoundException, WrongUsernameOrPasswordException {

        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            boolean isAuthenticated = loginService.authenticate(username, password);

            if (isAuthenticated) {
                switchToSceneService.switchToLibrary();
            }
        }catch (WrongUsernameOrPasswordException e){
            wrongInfo.setText(e.getMessage());
        }
    }

    @FXML
    protected void onNoAccountButtonClick(ActionEvent event) throws IOException {
        switchToSceneService.switchToRegister();
    }
}
