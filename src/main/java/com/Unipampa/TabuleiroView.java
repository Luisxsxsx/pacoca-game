package com.Unipampa;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;;

public class TabuleiroView extends StackPane implements Observer {

    // private Peca pecaSelecionada;
    private Tabuleiro base;
    private GridPane grid;
    public static TabuleiroView instance;

    private TabuleiroView() {
        this.grid = new GridPane(0, 0);
        this.base = Tabuleiro.getInstance();
        grid.setGridLinesVisible(true);
        draw();
        setOnMouseClicked(getOnDragDetected());
    }

    @Override
    public void atualizar(Tabuleiro tabuleiro) {
        this.base = tabuleiro;
    }

    private void draw() {
        for (Peca peca : base.getVetor()) {
            if (peca.getInfo() == CodigoJogo.JOGADOR1) {
                this.grid.add(new Rectangle(100, 100, Color.BLUE), peca.getPositionX(), peca.getPositionY());
                continue;
            } else if (peca.getInfo() == CodigoJogo.JOGADOR2) {
                this.grid.add(new Rectangle(100, 100, Color.RED), peca.getPositionX(), peca.getPositionY());
                continue;
            } else if (peca.getInfo() == CodigoJogo.PACOCA) {
                this.grid.add(new Rectangle(100, 100, Color.BROWN), peca.getPositionX(), peca.getPositionY());
                continue;
            } else if ((peca.getPositionX() + peca.getPositionY()) % 2 == 0) {
                this.grid.add(new Rectangle(100, 100, Color.WHITE), peca.getPositionX(), peca.getPositionY());
                continue;
            } else if ((peca.getPositionX() + peca.getPositionY()) % 2 != 0) {
                this.grid.add(new Rectangle(100, 100, Color.BLACK), peca.getPositionX(), peca.getPositionY());
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
