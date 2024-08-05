package com.fiap.restaurante.utils;

import com.fiap.restaurante.domain.Cliente;
import com.fiap.restaurante.domain.dto.ClienteDto;
import com.fiap.restaurante.domain.dto.EnderecoDto;
import com.fiap.restaurante.domain.embedded.Endereco;
import com.fiap.restaurante.repository.ClienteRepository;
import com.fiap.restaurante.domain.dto.ClienteRequest;

import java.util.UUID;


public abstract class ClienteHelper {
    public static Cliente gerarRegistro() {
        var endereco = new Endereco();
        endereco.setLogradouro("Endereco Automatizado");
        endereco.setNumero("1010");
        endereco.setUf("SP");
        endereco.setCep("00000-000");
        endereco.setBairro("Bairro Auto");
        endereco.setCidade("Cidade Auto");
        endereco.setComplemento("Comple");
        var entity = Cliente.builder()
                .nome("Cliente Automatizado")
                .email("email@email.teste")
                .fone("(11) 99999-9999")
                .endereco(endereco)
                .build();
        return entity;
    }

    public static Cliente gerarRegistroCompleto() {
        var endereco = new Endereco();
        endereco.setLogradouro("Endereco Automatizado");
        endereco.setNumero("1010");
        endereco.setUf("SP");
        endereco.setCep("00000-000");
        endereco.setBairro("Bairro Auto");
        endereco.setCidade("Cidade Auto");
        endereco.setComplemento("Comple");
        var entity = Cliente.builder()
                .id(UUID.randomUUID())
                .nome("Cliente Automatizado")
                .email("email@email.teste")
                .fone("(11) 99999-9999")
                .endereco(endereco)
                .build();
        return entity;
    }

    public static ClienteRequest gerarRegistroRequest() {
        var endereco = new Endereco();
        endereco.setLogradouro("Endereco Automatizado");
        endereco.setNumero("1010");
        endereco.setUf("SP");
        endereco.setCep("00000-000");
        endereco.setBairro("Bairro Auto");
        endereco.setCidade("Cidade Auto");
        endereco.setComplemento("Comple");
        var entity = ClienteRequest.builder()
                .nome("Cliente Automatizado")
                .email("email@email.teste")
                .fone("(11) 99999-9999")
                .endereco(endereco)
                .build();
        return entity;
    }

    public static Cliente registrarCliente(Cliente cliente, ClienteRepository repository) {
        return repository.save(cliente);
    }

    public static ClienteDto clienteToDto(Cliente cliente) {
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
