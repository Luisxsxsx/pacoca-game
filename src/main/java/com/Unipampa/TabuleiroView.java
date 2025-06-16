package com.Unipampa;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
// import javafx.scene.input.MouseEvent;

public class TabuleiroView extends StackPane implements Observer {

    // private Peca pecaSelecionada;
    private Tabuleiro base;
    private GridPane grid;
    public static TabuleiroView instance;

    private TabuleiroView() {
        this.grid = new GridPane(0, 0);
        this.base = Tabuleiro.getInstance();
        this.getChildren().add(this.grid);
        draw();
    }

    @Override
    public void atualizar(Tabuleiro tabuleiro) {
        this.base = tabuleiro;
    }

    private void draw() {
        // Limpa a grid antes de redesenhar para evitar duplicatas, importante para
        // atualizações
        this.grid.getChildren().clear();

        for (Peca peca : base.getVetor()) {
            if (peca.getInfo() == CodigoJogo.JOGADOR1) {
                Rectangle temp = new Rectangle(100, 100, Color.BLUE);
                temp.setOnMouseClicked(getOnMouseClicked());
                this.grid.add(temp, peca.getPositionX(), peca.getPositionY());
                continue;

            } else if (peca.getInfo() == CodigoJogo.JOGADOR2) {
                Rectangle temp = new Rectangle(100, 100, Color.RED);
                temp.setOnMouseClicked(getOnMouseClicked());
                this.grid.add(temp, peca.getPositionX(), peca.getPositionY());
                continue;

            } else if (peca.getInfo() == CodigoJogo.PACOCA) {
                Rectangle temp = new Rectangle(100, 100, Color.BROWN);
                temp.setOnMouseClicked(getOnMouseClicked());
                this.grid.add(temp, peca.getPositionX(), peca.getPositionY());
                continue;

            } else if ((peca.getPositionX() + peca.getPositionY()) % 2 == 0) {
                Rectangle temp = new Rectangle(100, 100, Color.WHITE);
                temp.setOnMouseClicked(getOnMouseClicked());
                this.grid.add(temp, peca.getPositionX(), peca.getPositionY());
                continue;

            } else if ((peca.getPositionX() + peca.getPositionY()) % 2 != 0) {
                Rectangle temp = new Rectangle(100, 100, Color.BLACK);
                temp.setOnMouseClicked(getOnMouseClicked());
                this.grid.add(temp, peca.getPositionX(), peca.getPositionY());
                continue;
            }
        }
    }

    public static TabuleiroView getInstance() {
        if (instance == null) {
            instance = new TabuleiroView();
        }
        return instance;
    }

    public GridPane getGrid() {
        return grid;
    }

}
