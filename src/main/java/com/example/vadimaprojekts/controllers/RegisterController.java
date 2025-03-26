package com.example.vadimaprojekts.controllers;

<<<<<<< HEAD
=======
import com.example.vadimaprojekts.exceptions.UserExistsException;
>>>>>>> master
import com.example.vadimaprojekts.service.RegisterService;
import com.example.vadimaprojekts.service.SwitchToLoginService;
import com.example.vadimaprojekts.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
<<<<<<< HEAD
=======
import javafx.scene.control.Label;
>>>>>>> master
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegisterController {
    @FXML
    private TextField registerUsername;
    @FXML
    private TextField registerPassword;

<<<<<<< HEAD
=======
    @FXML
    private Label error;

>>>>>>> master

    private RegisterService registerService;
    private UserService userService;
    private SwitchToLoginService switchToLoginService;

    public RegisterController() {
        this.registerService = new RegisterService();
        this.switchToLoginService = new SwitchToLoginService();
    }


    @FXML
    protected void onRegisterButtonClick(ActionEvent event) throws IOException {

        String username = registerUsername.getText();
        String password = registerPassword.getText();

        userService = new UserService(username, password);

<<<<<<< HEAD
        userService.saveUserToJson(userService);
=======
        try {
            userService.saveUserToJson(userService);
        } catch (UserExistsException e) {
            error.setText(e.getMessage());
        }


>>>>>>> master
    }

    @FXML
    protected void onHaveAnAccountClick(ActionEvent event) throws IOException {
        switchToLoginService.switchToLogin();
    }
}
