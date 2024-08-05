package com.fiap.restaurante.domain.exceptions;

public class ValidacaoException extends RuntimeException{
    public ValidacaoException(String mensagem) {
        super(mensagem);
    }
}
