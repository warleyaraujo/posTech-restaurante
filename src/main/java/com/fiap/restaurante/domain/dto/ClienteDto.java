package com.fiap.restaurante.domain.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

public record ClienteDto(
        UUID id,
        @NotBlank(message = "Nome é preenchimento obrigatório")
        @Length(min = 5, max = 80, message = "Nome dever ser preenchido entre 5 a 80 caracteres")
        String nome,
        @NotBlank(message = "Email é preenchimento obrigatório")
        @Length(min = 5, max = 80, message = "Email dever ser preenchido entre 5 a 80 caracteres")
        String email,
        @NotBlank(message = "Fone é preenchimento obrigatório")
        @Length(min = 5, max = 80, message = "Fone dever ser preenchido")
        String fone,
        EnderecoDto endereco
) { }
