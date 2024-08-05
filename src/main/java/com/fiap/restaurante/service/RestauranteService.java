package com.fiap.restaurante.service;

import com.fiap.restaurante.domain.Restaurante;
import com.fiap.restaurante.domain.dto.RestauranteDto;

import java.util.List;
import java.util.UUID;

public interface RestauranteService {

    Restaurante cadastrarRestaurante(RestauranteDto restauranteDto);

    List<Restaurante> listar();

    Restaurante cadastraMesas(UUID id, List<Integer> lugares);

    Restaurante atualizarRestaurante(UUID id, RestauranteDto restauranteDto);

    void deletarRestaurante(UUID id);
}
