package com.Unipampa;

public class Gerenciador implements Observed, Observer {
    private Observer tabObserver;
    private CodigoJogo codigo;
    private CodigoJogo flag;

    public Gerenciador() {
        this.tabObserver = Tabuleiro.getInstance();
        this.codigo = CodigoJogo.VEZJOGADOR2;
    }

    public void nextTurno() {
        switch (codigo) {
            case PRIMEIROTURNO:
                setCodigo(CodigoJogo.VEZJOGADOR1);
                break;

            case VEZJOGADOR1:
                setCodigo(CodigoJogo.VEZJOGADOR2);
                break;

            case VEZJOGADOR2:
                setCodigo(CodigoJogo.VEZJOGADOR1);
                break;

            default:
                setCodigo(CodigoJogo.PRIMEIROTURNO);
                break;
        }
    }

    public void setCodigo(CodigoJogo codigo) {
        this.codigo = codigo;
        notificar();
    }

    public CodigoJogo getCodigo() {
        return this.codigo;
    }

    public Observer getObserver() {
        return this.tabObserver;
    }

    @Override
    public void notificar() {
        tabObserver.atualizar(this.codigo);
    }

    @Override
    public void atualizar(CodigoJogo flag) {
        this.flag = flag;
        nextTurno();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        Gerenciador temp = (Gerenciador) obj;
        return temp.getCodigo() == this.codigo && temp.getObserver() == this.tabObserver;
    }
}