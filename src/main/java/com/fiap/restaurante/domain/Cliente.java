package com.fiap.restaurante.domain;

import com.fiap.restaurante.domain.dto.ClienteDto;
import com.fiap.restaurante.domain.embedded.Endereco;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Entity
@Table(name = "tb_clientes")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
public class Cliente {

    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    private UUID id;

    @NotEmpty(message = "nome não pode estar vazio")
    @Column
    private String nome;

    @Column
    private String email;

    @Column
    private String fone;

    @Embedded
    private Endereco endereco;

    public Cliente(ClienteDto clienteDto) {
        this.nome = clienteDto.nome();
        this.email = clienteDto.email();
        this.fone = clienteDto.fone();
        this.endereco = new Endereco();
        this.endereco.setLogradouro(clienteDto.endereco().logradouro());
        this.endereco.setNumero(clienteDto.endereco().numero());
        this.endereco.setComplemento(clienteDto.endereco().complemento());
        this.endereco.setBairro(clienteDto.endereco().bairro());
        this.endereco.setCidade(clienteDto.endereco().cidade());
        this.endereco.setUf(clienteDto.endereco().uf());
        this.endereco.setCep(clienteDto.endereco().cep());
    }


}
