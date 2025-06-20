package com.Unipampa;

import java.util.ArrayList;
import com.Unipampa.exceptions.*;

public class Tabuleiro implements Observer {
    private ArrayList<Peca> vetor = new ArrayList<Peca>(25);
    private CodigoJogo turno;
    private boolean moverPacoca;
    private int movimentos;
    private static Tabuleiro instance;

    private Tabuleiro() {
        this.turno = CodigoJogo.PRIMEIROTURNO;
        moverPacoca = false;
        movimentos = 1;
        createTabuleiro();
        createLogic();
        changeMovablePeca();
    }

    @Override
    public void atualizar(CodigoJogo turno) {
        this.turno = turno;
        this.moverPacoca = true;
        resetMovimentos();
        changeMovablePeca();
    }

    private void changeMovablePeca() {
        switch (this.turno) {
            case PRIMEIROTURNO:
                for (Peca peca : vetor) {
                    if (peca.getInfo() == CodigoPeca.JOGADOR1 || peca.getInfo() == CodigoPeca.PACOCA)
                        peca.Movible(false);
                    if (peca.getInfo() == CodigoPeca.JOGADOR2)
                        calcPossibleMoves(peca);
                }
                break;

            case VEZJOGADOR1:
                for (Peca peca : vetor) {
                    if (peca.getInfo() == CodigoPeca.JOGADOR1 || peca.getInfo() == CodigoPeca.PACOCA) {
                        peca.Movible(true);
                        calcPossibleMoves(peca);
                    }

                    if (peca.getInfo() == CodigoPeca.JOGADOR2)
                        peca.Movible(false);
                }
                break;

            case VEZJOGADOR2:
                for (Peca peca : vetor) {
                    if (peca.getInfo() == CodigoPeca.JOGADOR2 || peca.getInfo() == CodigoPeca.PACOCA) {
                        peca.Movible(true);
                        calcPossibleMoves(peca);
                    }
                    if (peca.getInfo() == CodigoPeca.JOGADOR1)
                        peca.Movible(false);
                }
                break;

            default:
                break;
        }

    }

    private void createLogic() {
        for (int i = 0; i < 5; i++) {
            this.vetor.get(i).setInfo(CodigoPeca.JOGADOR1);
            this.vetor.get(i + 20).setInfo(CodigoPeca.JOGADOR2);
        }
        this.vetor.get(12).setInfo(CodigoPeca.PACOCA);
        this.vetor.get(12).Movible(true);
        this.moverPacoca = false;
    }

    public void setPeca(Peca peca) {
        this.vetor.set(5 * peca.getPositionY() + peca.getPositionX(), peca);
    }

    private void createTabuleiro() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                this.vetor.addLast(new Peca(CodigoPeca.VAZIO, j, i));
            }
        }
    }

    private void calcPossibleMoves(Peca peca) {
        peca.resetPossiblePosition();

        int x, y, temp;
        x = peca.getPositionX();
        y = peca.getPositionY();
        while (vetor.get(temp = 5 * (y--) + x) != null && vetor.get(temp).getInfo() == CodigoPeca.VAZIO)
            ;
        if (vetor.get(temp = 5 * (y++) + x) != null)
            peca.addPossiblePosition(vetor.get(temp + 5).getPositionX(), vetor.get(temp + 5).getPositionX());

        x = peca.getPositionX();
        y = peca.getPositionY();
        while (vetor.get(temp = 5 * (y++) + x) != null && vetor.get(temp).getInfo() == CodigoPeca.VAZIO)
            ;
        if (vetor.get(temp = 5 * (y--) + x) != null)
            peca.addPossiblePosition(vetor.get(temp + 5).getPositionX(), vetor.get(temp + 5).getPositionX());

        x = peca.getPositionX();
        y = peca.getPositionY();
        while (vetor.get(temp = 5 * y + (x++)) != null && vetor.get(temp).getInfo() == CodigoPeca.VAZIO)
            ;
        if (vetor.get(temp = 5 * y + (x--)) != null)
            peca.addPossiblePosition(vetor.get(temp + 5).getPositionX(), vetor.get(temp + 5).getPositionX());

        x = peca.getPositionX();
        y = peca.getPositionY();
        while (vetor.get(temp = 5 * y + (x--)) != null && vetor.get(temp).getInfo() == CodigoPeca.VAZIO)
            ;
        if (vetor.get(temp = 5 * y + (x++)) != null)
            peca.addPossiblePosition(vetor.get(temp + 5).getPositionX(), vetor.get(temp + 5).getPositionX());

    }

    public void moverPeca(Peca peca, int posX, int posY) {
        if (posX == peca.getPositionX() && posY == peca.getPositionY()) {
            throw new CancelarMovimentoException("Movimento Cancelado"); // Tentando mover a peça para o mesmo lugar
        }

        if (peca.isMovable() == false)
            throw new MovimentoInvalidoException("Você não pode mover esta peça!");

        if (moverPacoca && peca.getInfo() != CodigoPeca.PACOCA)
            throw new MovimentoInvalidoException("Você deve mover a pacoca primeiro");

        Peca pTemp = this.vetor.get(5 * posY + posX);
        if (pTemp.getInfo() != CodigoPeca.VAZIO)
            throw new MovimentoInvalidoException("Não é possível mover para uma casa já ocupada!");

        int[] iTemp = new int[2];
        iTemp[0] = peca.getPositionX();
        iTemp[1] = peca.getPositionY();

        peca.setPosition(posX, posY);
        pTemp.setPosition(iTemp[0], iTemp[1]);

        setPeca(peca);
        setPeca(pTemp);
        moverPacoca = false;
        movimentos--;
    }

    public int getMovimentos() {
        return this.movimentos;
    }

    private void resetMovimentos() {
        this.movimentos = 2;
    }

    public Peca getPeca(int posX, int posY) {
        return this.vetor.get(5 * posY + posX);
    }

    public CodigoPeca getPecaInfo(int i) {
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
            if (this.vetor.get(i).getInfo() == CodigoPeca.VAZIO)
                System.out.print(
                        "VAZIO " + this.vetor.get(i).getPositionX() + " " + this.vetor.get(i).getPositionY() + " ");
            else if (this.vetor.get(i).getInfo() == CodigoPeca.JOGADOR1)
                System.out
                        .print("J1 " + this.vetor.get(i).getPositionX() + " " + this.vetor.get(i).getPositionY() + " ");
            else if (this.vetor.get(i).getInfo() == CodigoPeca.JOGADOR2)
                System.out
                        .print("J2 " + this.vetor.get(i).getPositionX() + " " + this.vetor.get(i).getPositionY() + " ");
            else if (this.vetor.get(i).getInfo() == CodigoPeca.PACOCA)
                System.out.print(
                        "PACOCA " + this.vetor.get(i).getPositionX() + " " + this.vetor.get(i).getPositionY() + " ");
            // }
            // System.out.println();
        }
    }
}
