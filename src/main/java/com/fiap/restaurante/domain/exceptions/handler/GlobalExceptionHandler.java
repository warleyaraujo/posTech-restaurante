
package com.fiap.restaurante.domain.exceptions.handler;


import com.fiap.restaurante.domain.exceptions.RestauranteNotFoundException;
import com.fiap.restaurante.domain.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex) {
    List<String> errors = new ArrayList<>();
    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.add(error.getDefaultMessage());
    }
    Collections.sort(errors);
    var errorResponse =
        new ErrorResponse("Validation error", errors);
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .contentType(MediaType.APPLICATION_JSON)
        .body(errorResponse);
  }

  @ExceptionHandler(RestauranteNotFoundException.class)
  public ResponseEntity<Object> handleRestauranteNotFoundException(
          RestauranteNotFoundException ex, HttpServletRequest request) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    ErrorResponse errorResponse = new ErrorResponse(
            ex.getMessage(),
            Collections.singletonList(ex.getMessage())
    );
    return ResponseEntity.status(status).body(errorResponse);
  }
}
