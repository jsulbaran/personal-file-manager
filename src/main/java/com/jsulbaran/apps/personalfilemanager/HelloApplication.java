package com.jsulbaran.apps.personalfilemanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.ListView;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setFullScreen(true);
        final HelloController controller = new HelloController();
        final FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        fxmlLoader.setController(controller);
        controller.setStage(stage);
        final Scene scene = new Scene(fxmlLoader.load(), 480.0, 640.0);
        stage.setTitle("Administrador de archivos");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}