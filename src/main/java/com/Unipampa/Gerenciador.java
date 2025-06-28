package com.Unipampa;

public class Gerenciador implements Observed, Observer {
    private Observer tabObserver;
    private CodigoJogo codigo;
    private CodigoJogo flag;
    private static Gerenciador instance;

    private Gerenciador() {
        this.tabObserver = Tabuleiro.getInstance();
        this.codigo = CodigoJogo.PRIMEIROTURNO;
    }

    public void nextTurno() {
        if (flag == CodigoJogo.PROXIMOTURNO)
            flag = null;

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

    public static Gerenciador getInsance() {
        if (instance == null) {
            instance = new Gerenciador();
        }

        return instance;
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
        if (this.flag == CodigoJogo.VITORIAJ1 ||
                this.flag == CodigoJogo.VITORIAJ2)
            declararVitoria(this.flag);
        else
            nextTurno();
    }

    public void declararVitoria(CodigoJogo player) {

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