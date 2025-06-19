package com.Unipampa;

public class Peca {
    private CodigoPeca info;
    private int[] position;
    private boolean movable;

    public Peca(CodigoPeca info, int x, int y) {
        this.info = info;
        this.position = new int[2];
        this.position[0] = x;
        this.position[1] = y;
        this.movable = false;
    }

    public void Movible(boolean value) {
        this.movable = value;
    }

    public boolean isMovable() {
        return this.movable;
    }

    public void setPosition(int x, int y) {
        this.position[0] = x;
        this.position[1] = y;
    }

    public int getPositionX() {
        return this.position[0];
    }

    public int getPositionY() {
        return this.position[1];
    }

    public CodigoPeca getInfo() {
        return this.info;
    }

    public void setInfo(CodigoPeca info) {
        this.info = info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Peca temp = (Peca) o;

        return this.info == temp.getInfo() && this.position[0] == temp.getPositionX()
                && this.position[1] == temp.getPositionY();
    }
}
