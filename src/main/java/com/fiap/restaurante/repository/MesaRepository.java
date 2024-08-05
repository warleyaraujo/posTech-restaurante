package com.fiap.restaurante.repository;

import com.fiap.restaurante.domain.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, UUID> {
}
