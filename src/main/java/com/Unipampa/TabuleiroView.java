package com.Unipampa;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.Node;

public class TabuleiroView extends StackPane implements Observer {

    private Peca pecaSelecionada;
    private Node nodeCasaSelecionada;
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
            Rectangle cellRectangle = new Rectangle(100, 100);
            StackPane cellPane = new StackPane(cellRectangle);

            if ((peca.getPositionX() + peca.getPositionY()) % 2 == 0) {
                cellRectangle.setFill(Color.WHITE);
            } else {
                cellRectangle.setFill(Color.BLACK);
            }

            if (peca.getInfo() != CodigoJogo.VAZIO) {
                Rectangle pecaRect = new Rectangle(50, 50); // Um pouco menor que a célula para borda
                if (peca.getInfo() == CodigoJogo.JOGADOR1) {
                    pecaRect.setFill(Color.BLUE);
                } else if (peca.getInfo() == CodigoJogo.JOGADOR2) {
                    pecaRect.setFill(Color.RED);
                } else if (peca.getInfo() == CodigoJogo.PACOCA) {
                    pecaRect.setFill(Color.BROWN);
                }
                cellPane.getChildren().add(pecaRect); // Adiciona a peça visualmente à célula
            }

            // ADICIONANDO LISINTER PARA CADA CELULA //
            final int currentX = peca.getPositionX();
            final int currentY = peca.getPositionY();

            cellPane.setOnMouseClicked(event -> {
                handleCellClick(event, currentX, currentY);
            });

            this.grid.add(cellPane, peca.getPositionX(), peca.getPositionY());
        }
    }

    private void handleCellClick(MouseEvent event, int clickX, int clickY) {
        Peca pecaNaCasa = base.getPeca(clickX, clickY);

        if (this.pecaSelecionada == null) {
            if (pecaNaCasa.getInfo() != CodigoJogo.VAZIO) {
                this.pecaSelecionada = pecaNaCasa;
                this.nodeCasaSelecionada = (Node) event.getSource();

                nodeCasaSelecionada.setStyle("-fx-border-color: yellow; -fx-border-width: 2;");
                System.out.println("Peça selecionada em: [" + clickX + "," + clickY + "]");
            } else {
                System.out.println("Casa selecionada vazia!");
            }
        } else {
            boolean movimentoValido = base.moverPeca(this.pecaSelecionada, clickX, clickY);
            if (movimentoValido) {
                System.out.println("Movimento Valido!");

                nodeCasaSelecionada.setStyle("");
                this.pecaSelecionada = null;

                draw();
            } else {
                System.out.println("Movimento inválido. Tente novamente.");
                // Se o movimento for inválido, você pode querer manter a peça selecionada ou
                // desmarcar
                // Por agora, vamos desmarcar para simplificar
                nodeCasaSelecionada.setStyle("");
                pecaSelecionada = null;
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
