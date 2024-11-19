package com.bancolombia.exceptions;

public class NoValidoClienteException extends RuntimeException{
    public NoValidoClienteException(String message){
        super(message);
    }
}
