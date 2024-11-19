package com.bancolombia.exceptions;

public class UserNoEncontradoException extends RuntimeException {

    public UserNoEncontradoException(String message) {
        super(message);  // Llama al constructor de la clase padre (RuntimeException)
    }
}
