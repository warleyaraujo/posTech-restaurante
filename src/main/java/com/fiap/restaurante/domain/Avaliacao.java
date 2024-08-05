package com.fiap.restaurante.domain;

import com.fiap.restaurante.domain.dto.AvaliacaoDto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "tb_avaliacao")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String nota;

    @Column
    private String comentario;

    public Avaliacao(AvaliacaoDto avaliacaoDto) {
        this.nota = avaliacaoDto.nota();
        this.comentario = avaliacaoDto.comentario();
    }
}
