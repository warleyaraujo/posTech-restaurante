package com.fiap.restaurante.domain.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity tratarErroRegraDeNegocio(ValidacaoException ex) {
        TrataMensagem trataMensagem = new TrataMensagem();
        return ResponseEntity.badRequest().body(trataMensagem.TrataMensagemErro(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErroRegraDeNegocio(MethodArgumentNotValidException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ValidacaoForm validacao = new ValidacaoForm();
        validacao.setTimestamp(Instant.now());
        validacao.setStatus(status.value());
        validacao.setError("Erro de  validação");
        for (FieldError field: ex.getBindingResult().getFieldErrors())
            validacao.addMenssagens(field.getField(), field.getDefaultMessage());

        return ResponseEntity.badRequest().body(validacao);
    }

}
