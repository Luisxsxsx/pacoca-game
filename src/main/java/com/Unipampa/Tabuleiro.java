package com.Unipampa;

import java.util.ArrayList;
import com.Unipampa.exceptions.*;

public class Tabuleiro {
    private ArrayList<Observer> objObservers;
    private ArrayList<Peca> vetor = new ArrayList<Peca>(25);
    private static Tabuleiro instance;

    private Tabuleiro() {
        createTabuleiro();
        createLogic();
    }

    private void createLogic() {
        for (int i = 0; i < 5; i++) {
            this.vetor.get(i).setInfo(CodigoJogo.JOGADOR1);
            this.vetor.get(i + 20).setInfo(CodigoJogo.JOGADOR2);
        }
        this.vetor.get(12).setInfo(CodigoJogo.PACOCA);
    }

    public void setObserver(Observer observer) {
        this.objObservers.add(observer);
    }

    public void notification() {
        for (Observer observer : objObservers) {
            observer.atualizar(this);
        }
    }

    public void setPeca(Peca peca) {
        this.vetor.set(5 * peca.getPositionY() + peca.getPositionX(), peca);
    }

    private void createTabuleiro() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                this.vetor.addLast(new Peca(CodigoJogo.VAZIO, j, i));
            }
        }
    }

    public void moverPeca(Peca peca, int posX, int posY) {
        if (posX == peca.getPositionX() && posY == peca.getPositionY()) {
            throw new CancelarMovimentoException("Movimento Cancelado"); // Tentando mover a peça para o mesmo lugar
        }

        Peca pTemp = this.vetor.get(5 * posY + posX);
        if (pTemp.getInfo() != CodigoJogo.VAZIO)
            throw new MovimentoInvalidoException("Não é possível mover para uma casa já ocupada!");

        int[] iTemp = new int[2];
        iTemp[0] = peca.getPositionX();
        iTemp[1] = peca.getPositionY();

        peca.setPosition(posX, posY);
        pTemp.setPosition(iTemp[0], iTemp[1]);

        setPeca(peca);
        setPeca(pTemp);

    }

    public Peca getPeca(int posX, int posY) {
        return this.vetor.get(5 * posY + posX);
    }

    public CodigoJogo getPecaInfo(int i) {
        return this.vetor.get(i).getInfo();
    }

    public static Tabuleiro getInstance() {
        if (instance == null) {
            instance = new Tabuleiro();
        }

        return instance;
    }

    public ArrayList<Peca> getVetor() {
        return this.vetor;
    }

    public void showMatriz() {
        System.out.println(this.vetor.size());
        for (int i = 0; i < this.vetor.size(); i++) {
            // for (int j = 0; j < this.vetor.length / 5; j++) {
            if (this.vetor.get(i).getInfo() == CodigoJogo.VAZIO)
                System.out.print(
                        "VAZIO " + this.vetor.get(i).getPositionX() + " " + this.vetor.get(i).getPositionY() + " ");
            else if (this.vetor.get(i).getInfo() == CodigoJogo.JOGADOR1)
                System.out
                        .print("J1 " + this.vetor.get(i).getPositionX() + " " + this.vetor.get(i).getPositionY() + " ");
            else if (this.vetor.get(i).getInfo() == CodigoJogo.JOGADOR2)
                System.out
                        .print("J2 " + this.vetor.get(i).getPositionX() + " " + this.vetor.get(i).getPositionY() + " ");
            else if (this.vetor.get(i).getInfo() == CodigoJogo.PACOCA)
                System.out.print(
                        "PACOCA " + this.vetor.get(i).getPositionX() + " " + this.vetor.get(i).getPositionY() + " ");
            // }
            // System.out.println();
        }
    }
}
