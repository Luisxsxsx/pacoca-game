package com.Unipampa;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.util.ArrayList;

import com.Unipampa.exceptions.CancelarMovimentoException;
import com.Unipampa.exceptions.MovimentoInvalidoException;

public class TabuleiroView extends StackPane implements Observed {

    private ArrayList<Observer> geObserver;
    private Peca pecaSelecionada;
    private Node nodeCasaSelecionada;
    private ArrayList<Peca> pecasPossiveis;
    private ArrayList<Node> nodeCasasPossiveis;
    private Tabuleiro base;
    private GridPane grid; // Grid do tabuleiro
    private CodigoJogo gameSituation;
    private static TabuleiroView instance;

    private TabuleiroView() {
        this.gameSituation = CodigoJogo.PRIMEIROTURNO;
        this.grid = new GridPane(0, 0);
        this.base = Tabuleiro.getInstance();
        this.geObserver = new ArrayList<>();
        this.geObserver.add(Gerenciador.getInsance());
        this.geObserver.add(ThePacoca.getInstance());
        this.getChildren().add(this.grid);
        draw();
    }

    public CodigoJogo getGameSituation() {
        return gameSituation;
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

    private ArrayList<Peca> getPecasPossiveis(Peca pecaNaCasa) {
        ArrayList<Peca> temp = new ArrayList<>();
        for (int[] position : pecaNaCasa.possibleMovePositions()) {
            temp.add(base.getPeca(position[0], position[1]));
            System.out.println("Posições possiveis: X - " + position[0] + "; Y - " + position[1]);
        }

        return temp;
    }

    private ArrayList<Node> getCasasPossiveis() {
        ArrayList<Node> temp = new ArrayList<>();
        for (Peca peca : this.pecasPossiveis) {
            temp.add(this.grid.getChildren().get(5 * peca.getPositionY() + peca.getPositionX()));
        }
        return temp;
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
                this.base.calcPossibleMoves(pecaNaCasa);
                this.pecasPossiveis = getPecasPossiveis(pecaNaCasa);
                this.nodeCasasPossiveis = getCasasPossiveis();

                nodeCasaSelecionada.setStyle("-fx-border-color: yellow; -fx-border-width: 2;");
                for (Node node : nodeCasasPossiveis) {
                    node.setStyle("-fx-border-color: yellow; -fx-border-width: 2;");
                }
                System.out.println(
                        "Peça selecionada em: [" + clickX + "," + clickY + "] " + pecaNaCasa.pecaSelecionada());
            } else {
                userAlert("Casa selecionada vazia!");
            }
        } else {
            try {
                base.moverPeca(this.pecaSelecionada, clickX, clickY);

                if (base.verificarVitoria() == CodigoJogo.VITORIAJ1 ||
                        base.verificarVitoria() == CodigoJogo.VITORIAJ2) {
                    this.gameSituation = base.verificarVitoria();
                    notificar();
                    this.grid.getChildren().clear();
                } else {

                    nodeCasaSelecionada.setStyle("");
                    for (Node node : nodeCasasPossiveis) {
                        node.setStyle("");
                    }
                    this.pecaSelecionada = null;
                    this.nodeCasasPossiveis.clear();
                    this.pecasPossiveis.clear();
                    if (base.getMovimentos() == 0) {
                        this.gameSituation = CodigoJogo.PROXIMOTURNO;
                        notificar();
                    }

                    draw();
                }
            } catch (MovimentoInvalidoException e) {
                System.err.println("Erro de movimento: " + e.getMessage());
                userAlert(e.getMessage());
                nodeCasaSelecionada.setStyle("");
                for (Node node : nodeCasasPossiveis) {
                    node.setStyle("");
                }
                pecaSelecionada = null;
                this.nodeCasasPossiveis.clear();
                this.pecasPossiveis.clear();

            } catch (CancelarMovimentoException e) {
                System.out.println("Cancelando movimento");
                nodeCasaSelecionada.setStyle("");
                for (Node node : nodeCasasPossiveis) {
                    node.setStyle("");
                }
                pecaSelecionada = null;
                this.nodeCasasPossiveis.clear();
                this.pecasPossiveis.clear();

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
        for (Observer observer : this.geObserver) {
            observer.atualizar(this.gameSituation);
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
