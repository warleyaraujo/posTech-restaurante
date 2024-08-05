package com.fiap.restaurante.service;

import com.fiap.restaurante.domain.Cliente;
import com.fiap.restaurante.domain.dto.ClienteDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ClienteService {
    ClienteDto cadastrarCliente(ClienteDto cLienteDto);

    Page<ClienteDto> listarTodos(Pageable page);

    ClienteDto buscarPorId(UUID id);

    ClienteDto editarCliente(UUID id, ClienteDto clienteDto);

    boolean deletarCliente(UUID id);

    public ClienteDto clienteToDto(Cliente cliente);
}
