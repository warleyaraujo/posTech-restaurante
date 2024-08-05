package com.fiap.restaurante.domain.exceptions;

public class   AvaliacaoNotFoundException extends RuntimeException{

    public AvaliacaoNotFoundException() {}

    public AvaliacaoNotFoundException(String msg) {
        super(msg);
    }

}
