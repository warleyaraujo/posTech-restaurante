package com.fiap.restaurante.domain.exceptions;

public class RestauranteNotFoundException extends RuntimeException {

    public RestauranteNotFoundException() {}

    public RestauranteNotFoundException(String msg) {
        super(msg);
    }

}
