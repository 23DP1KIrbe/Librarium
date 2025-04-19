package com.example.vadimaprojekts.controllers;

import com.example.vadimaprojekts.module.User;
import com.example.vadimaprojekts.service.Session;
import com.example.vadimaprojekts.service.SwitchToSceneService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    @FXML
    private Button logoutBtn, profileBtn, buyListBtn, readListBtn;
    @FXML
    private AnchorPane anchorPane;
    SwitchToSceneService switchToSceneService = new SwitchToSceneService();
    Session session = Session.getInstance();
    User user = session.getUser();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void onreadListBtnClick(ActionEvent event) {
        if(user.getReadList().size() > 3){
            anchorPane.setPrefHeight(anchorPane.getPrefHeight() + 100);
        }

    }

    @FXML
    public void onbuyListBtnClick(ActionEvent event) {
        System.out.println("onbuyListBtnClick");
    }

    @FXML
    private void onlogoutBtnClick(ActionEvent event) throws IOException {
        session.setUser(null);
        switchToSceneService.switchToLogin();
    }

    @FXML
    private void onlibraryBtnClick(ActionEvent event) throws IOException {
        switchToSceneService.switchToLibrary();
    }

}
