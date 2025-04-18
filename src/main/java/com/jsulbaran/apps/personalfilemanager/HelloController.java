package com.jsulbaran.apps.personalfilemanager;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        System.out.println("button clicked...");
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}