package com.jsulbaran.apps.personalfilemanager;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelloController {
    @FXML
    private Label welcomeText;
    private Stage stage;
    private Desktop desktop = Desktop.getDesktop();

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    @FXML
    protected void onHelloButtonClick() {
        System.out.println("button clicked...");
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onOpenFileClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            System.out.println("file : " + file.getAbsolutePath());
            openFile(file);
        }
    }

    private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ex) {
            Logger.getLogger(
                    HelloController.class.getName()).log(
                    Level.SEVERE, null, ex
            );
        }
    }
}