package com.fiap.restaurante.domain;

import com.fiap.restaurante.domain.dto.RestauranteDto;
import com.fiap.restaurante.domain.embedded.Endereco;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "tb_restaurantes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurante {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 80)
    private String razaoSocial;
    @Column(nullable = false, length = 60)
    private String nomeFantasia;
    @Column(nullable = false, length = 50)
    private String tipoCozinha;
    @Column(nullable = false)
    private Integer capacidade;
    @Column(nullable = false)
    private LocalTime horaAbertura;
    @Column(nullable = false)
    private LocalTime horaEncerramento;
    @Embedded
    private Endereco endereco;

    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.PERSIST)
    private List<Mesa> mesas;

    public Restaurante(RestauranteDto restauranteDto) {
        this.razaoSocial = restauranteDto.razaoSocial();
        this.nomeFantasia = restauranteDto.nomeFantasia();
        this.tipoCozinha = restauranteDto.tipoCozinha();
        this.capacidade = restauranteDto.capacidade();
        this.horaAbertura = restauranteDto.horaAbertura();
        this.horaEncerramento = restauranteDto.horaEncerramento();
        this.endereco = new Endereco(restauranteDto.endereco());
    }
}
