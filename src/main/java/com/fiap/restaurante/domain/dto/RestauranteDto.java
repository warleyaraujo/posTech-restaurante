package com.fiap.restaurante.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalTime;

public record RestauranteDto(
        @NotBlank(message = "Razão Social é preenchimento obrigatório")
        @Length(min = 5, max = 80, message = "Razão Social dever ser preenchido entre 5 a 80 caracteres")
        String razaoSocial,
        @NotBlank(message = "Nome Fantasia é preenchimento obrigatório")
        @Length(min = 5, max = 60, message = "Nome Fantasia dever ser preenchido entre 5 a 60 caracteres")
        String nomeFantasia,
        @NotBlank(message = "Tipo de Cozinha é preenchimento obrigatório")
        @Length(min = 5, max = 50, message = "Tipo de cozinha dever ser preenchido entre 5 a 50 caracteres")
        String tipoCozinha,
        @NotNull(message = "Capacidade é preenchimento obrigatório")
        Integer capacidade,
        @NotNull(message = "Horário de abertura é obrigatório")
        LocalTime horaAbertura,
        @NotNull(message = "Horário de encerramento é obrigatório")
        LocalTime horaEncerramento,
        @NotNull(message = "Endereço é preenchimento obrigatório")
        EnderecoDto endereco
) {}
