package com.fiap.restaurante.service.serviceImpl;

import com.fiap.restaurante.domain.Cliente;
import com.fiap.restaurante.domain.dto.ClienteDto;
import com.fiap.restaurante.domain.dto.EnderecoDto;
import com.fiap.restaurante.domain.embedded.Endereco;
import com.fiap.restaurante.domain.exceptions.ClienteNotFoundException;
import com.fiap.restaurante.repository.ClienteRepository;
import com.fiap.restaurante.service.ClienteService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ClienteServiceImpl implements ClienteService {


    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public ClienteDto cadastrarCliente(ClienteDto clienteDto) {
        Cliente novoCliente = new Cliente(clienteDto);
        clienteRepository.save(novoCliente);
        return clienteToDto(novoCliente);
    }

    @Override
    public Page<ClienteDto> listarTodos(Pageable page) {
        Page<Cliente> clientes = clienteRepository.findAll(page);
        return clientes.map(this::clienteToDto);
    }

    @Override
    public ClienteDto buscarPorId(UUID id) {
        Cliente cliente;
        cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente não encontrado com o ID: " + id));
        return clienteToDto(cliente);
    }


    @Override
    public ClienteDto editarCliente(UUID id, ClienteDto clienteDto) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com o ID: " + id));

        Cliente clienteAtualizado = new Cliente(clienteDto);
        clienteAtualizado.setId(clienteExistente.getId());

        clienteRepository.save(clienteAtualizado);

        return clienteToDto(clienteAtualizado);
    }

    public boolean deletarCliente(UUID id) {
            var clienteDto = buscarPorId(id);
            Cliente cliente = new Cliente(clienteDto);
            clienteRepository.delete(cliente);
            return true;
    }
    @Override
    public ClienteDto clienteToDto(Cliente cliente) {
        return new ClienteDto(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getFone(),
                new EnderecoDto(
                        cliente.getEndereco().getLogradouro(),
                        cliente.getEndereco().getNumero(),
                        cliente.getEndereco().getComplemento(),
                        cliente.getEndereco().getBairro(),
                        cliente.getEndereco().getCidade(),
                        cliente.getEndereco().getUf(),
                        cliente.getEndereco().getCep()
                )
        );


    }




}
