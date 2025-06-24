package com.Unipampa;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class TelaInicial {

    private static String FONT_PATH = "file:..\\font\\Minecraftory.ttf";
    private static Font fonte = new Font(FONT_PATH, 64);

    public static Scene criarTelaInicia() {
        Label titulo = new Label("The Paçoca Game");
        titulo.setFont(fonte);

        Button buttonPlay = new Button("Jogar");
        buttonPlay.setFont(new Font(FONT_PATH, 24));

        VBox menuLayout = new VBox(10);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.getChildren().add(titulo);
        menuLayout.getChildren().add(buttonPlay);

        return new Scene(menuLayout, 564, 500);
    }
}
