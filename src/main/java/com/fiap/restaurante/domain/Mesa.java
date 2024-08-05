package com.fiap.restaurante.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tb_mesa")
@Data
@NoArgsConstructor
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID idRestaurante;

    private Integer lugares;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mesa mesa = (Mesa) o;
        return Objects.equals(id, mesa.id) && Objects.equals(idRestaurante, mesa.idRestaurante) && Objects.equals(lugares, mesa.lugares);
    }

}
