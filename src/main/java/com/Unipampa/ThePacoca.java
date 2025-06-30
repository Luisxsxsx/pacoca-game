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

public class ThePacoca extends Application implements Observer {

    private Stage mainStage;
    private Scene menuScene;
    private Scene gameScene;
    private Scene victoryScene;
    private static ThePacoca instance;
    private CodigoJogo vitoria = null;

    @Override
    public void start(Stage stage) {
        this.mainStage = stage;
        mainStage.setTitle("The Pacoca Game");
        mainStage.setResizable(false);
        mainStage.setX(433);

        instance = this;

        setUpMenuScene();
        setUpGameScene();

        mainStage.setScene(menuScene);
        mainStage.show();
    }

    @Override
    public void atualizar(CodigoJogo gameSituation) {
        if (gameSituation == CodigoJogo.VITORIAJ1
                || gameSituation == CodigoJogo.VITORIAJ2)
            showVictoryScreen(gameSituation);
    }

    public void showVictoryScreen(CodigoJogo resultadoVitoria) {
        this.vitoria = resultadoVitoria; // Armazena o tipo de vitória
        setUpVictoryScene(); // Configura a cena de vitória com base no resultado
        mainStage.setScene(victoryScene); // Muda para a cena de vitória
    }

    public static ThePacoca getInstance() {
        if (instance == null) {
            instance = new ThePacoca();
        }
        return instance;
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
        if (TabuleiroView.getInstance().getGameSituation() == CodigoJogo.VITORIAJ1
                || TabuleiroView.getInstance().getGameSituation() == CodigoJogo.VITORIAJ2) {
            this.vitoria = TabuleiroView.getInstance().getGameSituation();
            setUpVictoryScene();
            this.mainStage.setScene(victoryScene);
        }
    }

    private void setUpVictoryScene() {
        Font customFontTitle;
        Font customFontButton;

        customFontTitle = Font.loadFont("file:fonts/Minercraftory.ttf", 54);
        customFontButton = Font.loadFont("file:fonts/Minercraftory.ttf", 24);

        if (customFontTitle == null) {
            System.err.println("Erro ao carregar a fonte");
            customFontTitle = Font.font("Arial", 64);
            customFontButton = Font.font("Arial", 24);
        }

        Label titulo = null;
        if (this.vitoria == CodigoJogo.VITORIAJ1) {
            titulo = new Label("O Jogador 1 Venceu!");
            titulo.setFont(customFontTitle);
        }
        if (this.vitoria == CodigoJogo.VITORIAJ2) {
            titulo = new Label("O Jogador 2 Venceu!");
            titulo.setFont(customFontTitle);
        }

        Button buttonClose = new Button("Fechar");

        buttonClose.setFont(customFontButton);
        buttonClose.setOnAction(e -> closeGame());

        VBox menuLayout = new VBox(10);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.getChildren().add(titulo);
        menuLayout.getChildren().add(buttonClose);

        this.victoryScene = new Scene(menuLayout, 700, 500);
    }

    private void closeGame() {
        this.mainStage.close();
    }

    private void showGameScreen() {
        mainStage.setScene(gameScene);
    }

    public static void main(String[] args) {
        launch(args);
    }
    // Um método main em JavaFX é opcional;
}