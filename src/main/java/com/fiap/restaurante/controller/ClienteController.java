package com.fiap.restaurante.controller;

import com.fiap.restaurante.domain.Cliente;
import com.fiap.restaurante.domain.dto.ClienteDto;
import com.fiap.restaurante.service.ClienteService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@Slf4j
@RestController
@RequestMapping(value = "/clientes", produces = {"application/json"})
@Tag(name = "Cadastro de Clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }


    @GetMapping
    public ResponseEntity<Page<ClienteDto>> listarClientes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        log.info("requisição para listar mensagens foi efetuada: Página={}, Tamanho={}", page, size);
        Page<ClienteDto> clientes = clienteService.listarTodos(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(clientes);
    }

    @PostMapping
    @Operation(summary = "Efetua a inclusão de um novo cliente", method = "POST")
    public ResponseEntity<ClienteDto> cadastrarCliente(@Valid @RequestBody ClienteDto clienteDto){
        var novoCliente = clienteService.cadastrarCliente(clienteDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDto> buscarClientePorId(@PathVariable UUID id) {
        try {
            ClienteDto clienteDto = clienteService.buscarPorId(id);
            return ResponseEntity.ok(clienteDto);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDto> editarCliente(@PathVariable UUID id, @Valid @RequestBody ClienteDto clienteDto) {
        try {
            ClienteDto clienteEditado = clienteService.editarCliente(id, clienteDto);
            return ResponseEntity.ok(clienteEditado);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletarCliente(@PathVariable UUID id) {
        boolean deletado = clienteService.deletarCliente(id);
        if (deletado) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
