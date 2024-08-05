package com.fiap.restaurante.repository;

import com.fiap.restaurante.domain.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, UUID> {
}
