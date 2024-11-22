package com.bancolombia.exceptions;

public class PaymentRejectedException extends RuntimeException {
    public PaymentRejectedException(String message) {
        super(message);
    }
}
