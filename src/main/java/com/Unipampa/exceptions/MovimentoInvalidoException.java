package com.Unipampa.exceptions;

public class MovimentoInvalidoException extends RuntimeException {
    public MovimentoInvalidoException(String message) {
        super(message);
    }
}