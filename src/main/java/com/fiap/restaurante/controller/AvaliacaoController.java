package com.fiap.restaurante.controller;


import com.fiap.restaurante.domain.Avaliacao;
import com.fiap.restaurante.domain.dto.AvaliacaoDto;
import com.fiap.restaurante.service.AvaliacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/avaliacao", produces = {"applications/json"})
@Tag(name = "Cadastro de avaliacao")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    @Autowired
    public AvaliacaoController(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Efetua a inclusão de uma nova avaliacao", method = "POST")
    public ResponseEntity<AvaliacaoDto> cadastrar(@Valid @RequestBody AvaliacaoDto avaliacaoDto){
        AvaliacaoDto novaAvaliacao = avaliacaoService.cadastrar(avaliacaoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaAvaliacao);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<AvaliacaoDto>> listarAvaliacoes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        log.info("requisição para listar avaliacoes foi efetuada: Página={}, Tamanho={}", page, size);
        Page<AvaliacaoDto> avaliacoes = avaliacaoService.listarAvaliacoes(pageable);
        return ResponseEntity.ok().body(avaliacoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoDto> buscarAvaliacaoPorId(@PathVariable UUID id) {
        try {
            AvaliacaoDto  avaliacaoDto = avaliacaoService.buscarPorId(id);
            return ResponseEntity.ok(avaliacaoDto);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<AvaliacaoDto> editarAvaliacao(@PathVariable UUID id, @Valid @RequestBody AvaliacaoDto avaliacaoDto) {
        try {
            AvaliacaoDto avaliacaoDto1 = avaliacaoService.editarAvaliacao(id, avaliacaoDto);
            return ResponseEntity.ok(avaliacaoDto1);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletarAvaliacao(@PathVariable UUID id) {
        boolean deletado = avaliacaoService.deletarAvaliacao(id);
        if (deletado) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
