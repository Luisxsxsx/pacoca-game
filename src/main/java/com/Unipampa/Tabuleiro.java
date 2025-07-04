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
        if (this.turno != CodigoJogo.VITORIAJ1 ||
                this.turno != CodigoJogo.VEZJOGADOR2) {
            this.moverPacoca = true;
            resetMovimentos();
            changeMovablePeca();
        }
    }

    private void changeMovablePeca() {
        switch (this.turno) {
            case PRIMEIROTURNO:
                for (Peca peca : vetor) {
                    if (peca.getInfo() == CodigoPeca.JOGADOR1 || peca.getInfo() == CodigoPeca.PACOCA)
                        peca.Movable(false);
                    // if (peca.getInfo() == CodigoPeca.JOGADOR2)
                    // calcPossibleMoves(peca);
                }
                break;

            case VEZJOGADOR1:
                for (Peca peca : vetor) {
                    if (peca.getInfo() == CodigoPeca.JOGADOR1 || peca.getInfo() == CodigoPeca.PACOCA) {
                        peca.Movable(true);
                        // calcPossibleMoves(peca);
                    }

                    if (peca.getInfo() == CodigoPeca.JOGADOR2)
                        peca.Movable(false);
                }
                break;

            case VEZJOGADOR2:
                for (Peca peca : vetor) {
                    if (peca.getInfo() == CodigoPeca.JOGADOR2 || peca.getInfo() == CodigoPeca.PACOCA) {
                        peca.Movable(true);
                        // calcPossibleMoves(peca);
                    }
                    if (peca.getInfo() == CodigoPeca.JOGADOR1)
                        peca.Movable(false);
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
        this.vetor.get(12).Movable(true);
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

    private void calcFarthestUp(Peca peca) {
        int startX = peca.getPositionX();
        int startY = peca.getPositionY();

        // 1. Mover para CIMA (diminuindo Y)
        int currentY = startY - 1;
        int farthestUpX = startX;
        int farthestUpY = startY;

        while (currentY >= 0) { // Enquanto estiver dentro do tabuleiro
            Peca targetPeca = getPeca(startX, currentY);
            if (targetPeca != null && targetPeca.getInfo() == CodigoPeca.VAZIO) {
                farthestUpX = startX;
                farthestUpY = currentY;
                currentY--; // Continua subindo
            } else {
                break; // Encontrou uma peça ou fora do limite
            }
        }
        // Adiciona a posição mais distante para cima (se for diferente da posição
        // inicial)
        if (farthestUpY != startY) {
            peca.addPossiblePosition(farthestUpX, farthestUpY);
        }
    }

    private void calcFarthestDown(Peca peca) {

        int startX = peca.getPositionX();
        int startY = peca.getPositionY();

        // 2. Mover para BAIXO (aumentando Y)
        int currentY = startY + 1;
        int farthestDownX = startX;
        int farthestDownY = startY;

        while (currentY < 5) { // Enquanto estiver dentro do tabuleiro
            Peca targetPeca = getPeca(startX, currentY);
            if (targetPeca != null && targetPeca.getInfo() == CodigoPeca.VAZIO) {
                farthestDownX = startX;
                farthestDownY = currentY;
                currentY++; // Continua descendo
            } else {
                break; // Encontrou uma peça ou fora do limite
            }
        }
        if (farthestDownY != startY) {
            peca.addPossiblePosition(farthestDownX, farthestDownY);
        }

    }

    private void calcFarthestLeft(Peca peca) {

        int startX = peca.getPositionX();
        int startY = peca.getPositionY();

        // 3. Mover para ESQUERDA (diminuindo X)
        int currentX = startX - 1;
        int farthestLeftX = startX;
        int farthestLeftY = startY;

        while (currentX >= 0) { // Enquanto estiver dentro do tabuleiro
            Peca targetPeca = getPeca(currentX, startY);
            if (targetPeca != null && targetPeca.getInfo() == CodigoPeca.VAZIO) {
                farthestLeftX = currentX;
                farthestLeftY = startY;
                currentX--; // Continua indo para a esquerda
            } else {
                break; // Encontrou uma peça ou fora do limite
            }
        }
        if (farthestLeftX != startX) {
            peca.addPossiblePosition(farthestLeftX, farthestLeftY);
        }

    }

    private void calcFarthestRight(Peca peca) {

        int startX = peca.getPositionX();
        int startY = peca.getPositionY();
        // 4. Mover para DIREITA (aumentando X)
        int currentX = startX + 1;
        int farthestRightX = startX;
        int farthestRightY = startY;

        while (currentX < 5) { // Enquanto estiver dentro do tabuleiro
            Peca targetPeca = getPeca(currentX, startY);
            if (targetPeca != null && targetPeca.getInfo() == CodigoPeca.VAZIO) {
                farthestRightX = currentX;
                farthestRightY = startY;
                currentX++; // Continua indo para a direita
            } else {
                break; // Encontrou uma peça ou fora do limite
            }
        }
        if (farthestRightX != startX) {
            peca.addPossiblePosition(farthestRightX, farthestRightY);
        }
    }

    private void calcFarthestUpRight(Peca peca) {

        int startX = peca.getPositionX();
        int startY = peca.getPositionY();

        // 4. Mover para DIREITA (aumentando X)
        int currentY = startY - 1;
        int currentX = startX + 1;
        int farthestRightX = startX;
        int farthestRightY = startY;

        while (currentX < 5 && currentY >= 0) { // Enquanto estiver dentro do tabuleiro
            Peca targetPeca = getPeca(currentX, currentY);
            if (targetPeca != null && targetPeca.getInfo() == CodigoPeca.VAZIO) {
                farthestRightX = currentX;
                farthestRightY = currentY;
                currentX++; // Continua indo para a direita
                currentY--; // Continua indo para cima;
            } else {
                break; // Encontrou uma peça ou fora do limite
            }
        }
        if (farthestRightX != startX) {
            peca.addPossiblePosition(farthestRightX, farthestRightY);
        }
    }

    private void calcFarthestUpLeft(Peca peca) {

        int startX = peca.getPositionX();
        int startY = peca.getPositionY();

        // 4. Mover para Esquerda (aumentando X)
        int currentY = startY - 1;
        int currentX = startX - 1;
        int farthestRightX = startX;
        int farthestRightY = startY;

        while (currentX >= 0 && currentY >= 0) { // Enquanto estiver dentro do tabuleiro
            Peca targetPeca = getPeca(currentX, currentY);
            if (targetPeca != null && targetPeca.getInfo() == CodigoPeca.VAZIO) {
                farthestRightX = currentX;
                farthestRightY = currentY;
                currentX--; // Continua indo para a equerda
                currentY--; // Continua indo para cima;
            } else {
                break; // Encontrou uma peça ou fora do limite
            }
        }
        if (farthestRightX != startX) {
            peca.addPossiblePosition(farthestRightX, farthestRightY);
        }
    }

    private void calcFarthestDownLeft(Peca peca) {

        int startX = peca.getPositionX();
        int startY = peca.getPositionY();

        // 4. Mover para Esquerda (aumentando X)
        int currentY = startY + 1;
        int currentX = startX - 1;
        int farthestRightX = startX;
        int farthestRightY = startY;

        while (currentX >= 0 && currentY < 5) { // Enquanto estiver dentro do tabuleiro
            Peca targetPeca = getPeca(currentX, currentY);
            if (targetPeca != null && targetPeca.getInfo() == CodigoPeca.VAZIO) {
                farthestRightX = currentX;
                farthestRightY = currentY;
                currentX--; // Continua indo para a equerda
                currentY++; // Continua indo para baixo;
            } else {
                break; // Encontrou uma peça ou fora do limite
            }
        }
        if (farthestRightX != startX) {
            peca.addPossiblePosition(farthestRightX, farthestRightY);
        }
    }

    private void calcFarthestDownRight(Peca peca) {

        int startX = peca.getPositionX();
        int startY = peca.getPositionY();

        // 4. Mover para Direita (aumentando X)
        int currentY = startY + 1;
        int currentX = startX + 1;
        int farthestRightX = startX;
        int farthestRightY = startY;

        while (currentX < 5 && currentY < 5) { // Enquanto estiver dentro do tabuleiro
            Peca targetPeca = getPeca(currentX, currentY);
            if (targetPeca != null && targetPeca.getInfo() == CodigoPeca.VAZIO) {
                farthestRightX = currentX;
                farthestRightY = currentY;
                currentX++; // Continua indo para a equerda
                currentY++; // Continua indo para baixo;
            } else {
                break; // Encontrou uma peça ou fora do limite
            }
        }
        if (farthestRightX != startX) {
            peca.addPossiblePosition(farthestRightX, farthestRightY);
        }
    }

    public void calcPossibleMoves(Peca peca) {
        peca.resetPossiblePosition();
        calcFarthestUp(peca);
        calcFarthestDown(peca);
        calcFarthestLeft(peca);
        calcFarthestRight(peca);
        calcFarthestDownLeft(peca);
        calcFarthestDownRight(peca);
        calcFarthestUpLeft(peca);
        calcFarthestUpRight(peca);
    }

    public void moverPeca(Peca peca, int posX, int posY) {
        if (posX == peca.getPositionX() && posY == peca.getPositionY()) {
            throw new CancelarMovimentoException("Movimento Cancelado"); // Tentando mover a peça para o mesmo lugar
        }

        if (peca.isMovable() == false)
            throw new MovimentoInvalidoException("Você não pode mover esta peça!");

        if (moverPacoca && peca.getInfo() != CodigoPeca.PACOCA)
            throw new MovimentoInvalidoException("Você deve mover a pacoca primeiro");

        if (peca.getInfo() == CodigoPeca.PACOCA && !moverPacoca)
            throw new MovimentoInvalidoException("Você não pode mover a paçoca!");

        Peca pTemp = this.vetor.get(5 * posY + posX);
        if (pTemp.getInfo() != CodigoPeca.VAZIO)
            throw new MovimentoInvalidoException("Não é possível mover para uma casa já ocupada!");

        int[] iTemp = new int[2];
        iTemp[0] = peca.getPositionX();
        iTemp[1] = peca.getPositionY();

        boolean invalid = true;
        for (int[] possitions : peca.possibleMovePositions()) {
            if (posX == possitions[0] && posY == possitions[1]) {
                invalid = false;
                break;
            }
        }
        if (invalid)
            throw new MovimentoInvalidoException("Você não pode mover a peça selecionada para esta casa!");

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

    public CodigoJogo verificarVitoria() {
        for (int i = 0; i < 5; i++) {
            if (this.vetor.get(i).getInfo() == CodigoPeca.PACOCA)
                return CodigoJogo.VITORIAJ1;
            if (this.vetor.get(i + 20).getInfo() == CodigoPeca.PACOCA)
                return CodigoJogo.VITORIAJ2;
        }
        return null;
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
