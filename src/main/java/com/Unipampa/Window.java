package com.Unipampa;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Window extends Application {

    // private static Window instance;

    // private Window() {
    // };

    private Parent createContent() {
        Rectangle box = new Rectangle(20, 20, Color.RED);

        return new Pane(box);
    }

    @Override
    public void start(Stage stage) {

        Scene scene = new Scene(createContent(), 500, 500);
        stage.setTitle("The Pacoca Game");
        stage.setScene(scene);
        stage.show();
    }

    public static void lancar() {
        launch(null);
    }

    // public static Window getInstance() {
    //     if (instance == null) {
    //         instance = new Window();
    //     }
    //     return instance;
    // }
}
