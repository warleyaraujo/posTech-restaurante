package com.fiap.restaurante.domain.embedded;

import com.fiap.restaurante.domain.dto.EnderecoDto;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    @Column(nullable = false, length = 80)
    private String logradouro;
    @Column(nullable = false, length = 10)
    private String numero;
    @Column(length = 40)
    private String complemento;
    @Column(nullable = false, length = 60)
    private String bairro;
    @Column(nullable = false, length = 60)
    private String cidade;
    @Column(nullable = false, length = 2)
    private String uf;
    @Column(nullable = false, length = 9)
    private String cep;

    public Endereco(EnderecoDto enderecoDto) {
        this.logradouro = enderecoDto.logradouro();
        this.numero = enderecoDto.numero();
        this.complemento = enderecoDto.complemento();
        this.bairro = enderecoDto.bairro();
        this.cidade = enderecoDto.cidade();
        this.uf = enderecoDto.uf();
        this.cep = enderecoDto.cep();
    }
}
