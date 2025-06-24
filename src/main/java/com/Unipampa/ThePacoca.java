package com.Unipampa;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ThePacoca extends Application {

    public Stage mainStage;
    public Scene menuScene;
    public Scene gameScene;
    public Scene victoryScene;

    @Override
    public void start(Stage stage) {
        this.mainStage = stage;
        mainStage.setTitle("The Pacoca Game");
        mainStage.setResizable(false);
        mainStage.setX(433);

        setUpMenuScene();
        setUpGameScene();

        mainStage.setScene(menuScene);
        mainStage.show();
    }

    private void setUpMenuScene() {
        Font customFontTitle;
        Font customFontButton;

        customFontTitle = Font.loadFont("file:fonts/Minercraftory.ttf", 54);
        customFontButton = Font.loadFont("file:fonts/Minercraftory.ttf", 24);

        if (customFontTitle == null) {
            System.err.println("Erro ao carregar a fonte");
            customFontTitle = Font.font("Arial", 64);
            customFontButton = Font.font("Arial", 24);
        }

        Label titulo = new Label("The Paçoca Game");
        titulo.setFont(customFontTitle);

        Button buttonPlay = new Button("Jogar");

        buttonPlay.setFont(customFontButton);
        buttonPlay.setOnAction(e -> showGameScreen());

        VBox menuLayout = new VBox(10);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.getChildren().add(titulo);
        menuLayout.getChildren().add(buttonPlay);

        this.menuScene = new Scene(menuLayout, 700, 500);
    }

    private void setUpGameScene() {
        BorderPane root = new BorderPane();
        root.setCenter(TabuleiroView.getInstance());
        gameScene = new Scene(root, 500, 500);
    }

    private void showGameScreen() {
        mainStage.setScene(gameScene);
    }

    public static void main(String[] args) {
        launch(args);
    }
    // Um método main em JavaFX é opcional;
}