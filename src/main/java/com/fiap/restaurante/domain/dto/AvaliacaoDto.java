package com.fiap.restaurante.domain.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

public record AvaliacaoDto(
        UUID id,
        @NotBlank
        @Size(min = 1, message = "Nota deve ser preenchido com 1 a 5")
        String nota,
        @Length(min = 5, max = 80, message = "Comentario deve ser preenchido entre 5 a 80 caracteres")
        String comentario

) { }
