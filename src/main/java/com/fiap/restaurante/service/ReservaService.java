package com.fiap.restaurante.service;

import com.fiap.restaurante.domain.Reserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ReservaService {

    Reserva cadastrar(Reserva reserva);

    Page<Reserva> listar(Pageable pageable);

    Reserva atualizarReserva(UUID id, Reserva reserva);

    boolean deletarReserva(UUID id);
}
