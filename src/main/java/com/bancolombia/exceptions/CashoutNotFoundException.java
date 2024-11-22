package com.bancolombia.exceptions;

public class CashoutNotFoundException extends RuntimeException {
    public CashoutNotFoundException(String message) {
        super(message);
    }
}
