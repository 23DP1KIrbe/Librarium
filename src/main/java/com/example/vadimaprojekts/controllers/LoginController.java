package com.example.vadimaprojekts.controllers;


<<<<<<< HEAD
=======
import com.example.vadimaprojekts.exceptions.UserNotFoundException;
import com.example.vadimaprojekts.exceptions.WrongUsernameOrPasswordException;
>>>>>>> master
import com.example.vadimaprojekts.service.SwitchToRegisterService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import com.example.vadimaprojekts.service.LoginService;
import com.example.vadimaprojekts.service.SwitchToLibraryService;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    private LoginService loginService;
    private SwitchToLibraryService switchToLibraryService;
    private SwitchToRegisterService switchToRegisterService;


    public LoginController() {
        this.loginService = new LoginService();
        this.switchToLibraryService = new SwitchToLibraryService();
        this.switchToRegisterService = new SwitchToRegisterService();
<<<<<<< HEAD
=======


>>>>>>> master
    }


    @FXML
<<<<<<< HEAD
    protected void onLoginButtonClick(ActionEvent event) throws IOException {
=======
    protected void onLoginButtonClick(ActionEvent event) throws IOException, UserNotFoundException, WrongUsernameOrPasswordException {
>>>>>>> master
        String username = usernameField.getText();
        String password = passwordField.getText();

        boolean isAuthenticated = loginService.authenticate(username, password);

        if (isAuthenticated) {
            switchToLibraryService.switchToLibrary();
        }
    }

    @FXML
    protected void onNoAccountButtonClick(ActionEvent event) throws IOException {
        switchToRegisterService.switchToRegister();
    }
}
