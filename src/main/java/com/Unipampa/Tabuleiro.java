package com.Unipampa;

public class Tabuleiro {
    private Peca[] vetor = new Peca[25];
    private static Tabuleiro instance;

    private Tabuleiro() {
        createTabuleiro();
        createLogic();
    }

    private void createLogic() {
        for (int i = 0; i < 5; i++) {
            this.vetor[i].setInfo(CodigoJogo.JOGADOR1);
            this.vetor[i + 20].setInfo(CodigoJogo.JOGADOR2);
        }
        this.vetor[12].setInfo(CodigoJogo.PACOCA);
    }

    private void createTabuleiro() {
        for (int i = 0; i < vetor.length / 5; i++) {
            for (int j = 0; j < vetor.length / 5; j++) {
                this.vetor[5 * i + j] = new Peca(CodigoJogo.VAZIO, j, i);
            }
        }
    }

    public CodigoJogo getPecaInfo(int i) {
        return this.vetor[i].getInfo();
    }

    public static Tabuleiro getInstance() {
        if (instance == null) {
            instance = new Tabuleiro();
        }

        return instance;
    }

    public Peca[] getVetor() {
        return this.vetor;
    }

    public void showMatriz() {
        for (int i = 0; i < this.vetor.length; i++) {
            // for (int j = 0; j < this.vetor.length / 5; j++) {
            if (this.vetor[i].getInfo() == CodigoJogo.VAZIO)
                System.out.print("VAZIO " + this.vetor[i].getPositionX() + " " + this.vetor[i].getPositionY() + " ");
            else if (this.vetor[i].getInfo() == CodigoJogo.JOGADOR1)
                System.out.print("J1 " + this.vetor[i].getPositionX() + " " + this.vetor[i].getPositionY() + " ");
            else if (this.vetor[i].getInfo() == CodigoJogo.JOGADOR2)
                System.out.print("J2 " + this.vetor[i].getPositionX() + " " + this.vetor[i].getPositionY() + " ");
            else if (this.vetor[i].getInfo() == CodigoJogo.PACOCA)
                System.out.print("PACOCA " + this.vetor[i].getPositionX() + " " + this.vetor[i].getPositionY() + " ");
            // }
            // System.out.println();
        }
    }
}
