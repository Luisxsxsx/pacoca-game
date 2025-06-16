package com.Unipampa;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ThePacoca extends Application {

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        root.setCenter(TabuleiroView.getInstance());
        Scene cena = new Scene(root, 500, 500);
        stage.setTitle("The Pacoca Game");
        stage.setScene(cena);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);

    }
}