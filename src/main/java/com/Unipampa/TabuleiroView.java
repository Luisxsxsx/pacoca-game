package com.Unipampa;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import com.Unipampa.exceptions.CancelarMovimentoException;
import com.Unipampa.exceptions.MovimentoInvalidoException;

public class TabuleiroView extends StackPane implements Observed {

    private Observer geObserver;
    private Peca pecaSelecionada;
    private Node nodeCasaSelecionada;
    private Tabuleiro base;
    private GridPane grid; // Grid do tabuleiro
    private static TabuleiroView instance;

    private TabuleiroView() {
        this.grid = new GridPane(0, 0);
        this.base = Tabuleiro.getInstance();
        this.geObserver = Gerenciador.getInsance();
        this.getChildren().add(this.grid);
        draw();
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

            if (peca.getInfo() != CodigoPeca.VAZIO) {
                Rectangle pecaRect = new Rectangle(50, 50); // Um pouco menor que a célula para borda
                if (peca.getInfo() == CodigoPeca.JOGADOR1) {
                    pecaRect.setFill(Color.BLUE);
                } else if (peca.getInfo() == CodigoPeca.JOGADOR2) {
                    pecaRect.setFill(Color.RED);
                } else if (peca.getInfo() == CodigoPeca.PACOCA) {
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
        if (pecaNaCasa.isMovable() == false) {
            userAlert("Você não pode mover esta peça!");
            return;
        }

        if (this.pecaSelecionada == null) {
            if (pecaNaCasa.getInfo() != CodigoPeca.VAZIO) {
                this.pecaSelecionada = pecaNaCasa;
                this.nodeCasaSelecionada = (Node) event.getSource();

                nodeCasaSelecionada.setStyle("-fx-border-color: yellow; -fx-border-width: 2;");
                System.out.println("Peça selecionada em: [" + clickX + "," + clickY + "] "+ pecaNaCasa.pecaSelecionada());
            } else {
                userAlert("Casa selecionada vazia!");
            }
        } else {
            try {
                base.moverPeca(this.pecaSelecionada, clickX, clickY);
                nodeCasaSelecionada.setStyle("");
                this.pecaSelecionada = null;

                if (base.getMovimentos() == 0)
                    notificar();

                draw();
            } catch (MovimentoInvalidoException e) {
                System.err.println("Erro de movimento: " + e.getMessage());
                userAlert(e.getMessage());
                nodeCasaSelecionada.setStyle("");
                pecaSelecionada = null;

            } catch (CancelarMovimentoException e) {
                System.out.println("Cancelando movimento");
                nodeCasaSelecionada.setStyle("");
                pecaSelecionada = null;

            } catch (Exception e) {
                System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void userAlert(String error) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Movimento Inválido");
        alert.setHeaderText(null); // Sem cabeçalho
        alert.setContentText(error);
        alert.showAndWait(); // Mostra a caixa de diálogo e espera o usuário clicar em OK

    }

    @Override
    public void notificar() {
        this.geObserver.atualizar(CodigoJogo.PROXIMOTURNO);
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
