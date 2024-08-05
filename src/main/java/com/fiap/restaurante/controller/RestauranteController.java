package com.fiap.restaurante.controller;

import com.fiap.restaurante.domain.Restaurante;
import com.fiap.restaurante.domain.dto.RestauranteDto;
import com.fiap.restaurante.domain.exceptions.RestauranteNotFoundException;
import com.fiap.restaurante.service.RestauranteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/restaurantes", produces = {"application/json"})
@Tag(name = "Cadastro de Restaurantes")
public class RestauranteController {

    private final RestauranteService restauranteService;

    public RestauranteController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    @PostMapping
    @Operation(summary = "Efetua a inclusão de um novo restaurante", method = "POST")
    public ResponseEntity<Restaurante> cadastrarRestaurante(@Valid @RequestBody RestauranteDto restauranteDto) {
        Restaurante novoRestaurante = restauranteService.cadastrarRestaurante(restauranteDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoRestaurante);
    }

    @PostMapping("/{id}/mesas")
    @Operation(summary = "Efetua a inclusão de mesas em um restaurante", method = "POST")
    public ResponseEntity<Restaurante> cadastrarMesa(@PathVariable UUID id, @Valid
                                                     @RequestBody List<Integer> lugares) {
        Restaurante restaurante = restauranteService.cadastraMesas(id, lugares);
        return ResponseEntity.ok().body(restaurante);
    }

    @GetMapping
    @Operation(summary = "Efetua a listagem de todos os restaurantes", method = "GET")
    public ResponseEntity<List<Restaurante>> listarRestaurantes() {
        List<Restaurante> restaurantes = restauranteService.listar();
        return ResponseEntity.ok().body(restaurantes);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Efetua a alteração de um restaurante", method = "PUT")
    public ResponseEntity<Restaurante> atualizarRestaurante(@PathVariable UUID id, @Valid
                                                            @RequestBody RestauranteDto restauranteDto) {
        Restaurante restaurante = restauranteService.atualizarRestaurante(id, restauranteDto);
        return ResponseEntity.ok().body(restaurante);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Efetua a exclusão de um restaurante", method = "DELETE")
    public ResponseEntity<Void> deletarRestaurante(@PathVariable UUID id) {
        try {
            restauranteService.deletarRestaurante(id);
            return ResponseEntity.noContent().build();
        } catch (RestauranteNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

    }


}
