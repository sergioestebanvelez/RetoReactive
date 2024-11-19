package com.bancolombia.exceptions;

public class CashoutNoEncontradoException extends RuntimeException {
    public CashoutNoEncontradoException(String message) {
        super(message);
    }
}
