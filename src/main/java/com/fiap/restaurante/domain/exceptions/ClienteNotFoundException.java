package com.fiap.restaurante.domain.exceptions;

public class ClienteNotFoundException extends RuntimeException {

        public ClienteNotFoundException() {}

        public ClienteNotFoundException(String msg) {
            super(msg);
        }

    }

