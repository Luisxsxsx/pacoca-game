package com.Unipampa;

public class Tabuleiro {
    private Peca[][] matriz = new Peca[5][5];
    private static Tabuleiro instance;

    private Tabuleiro() {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                this.matriz[i][j] = new Peca(CodigoJogo.VAZIO);
            }
        }
    }

    public static Tabuleiro getInstance() {
        if (instance == null) {
            instance = new Tabuleiro();
        }

        return instance;
    }

    public void showMatriz() {
        for (int i = 0; i < this.matriz.length; i++) {
            for (int j = 0; j < this.matriz.length; j++) {
                if (this.matriz[i][j].getInfo() == CodigoJogo.VAZIO)
                    System.out.print("VAZIO ");
            }
            System.out.println();
        }
    }
}
