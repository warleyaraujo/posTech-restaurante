package com.fiap.restaurante.utils;

import com.fiap.restaurante.domain.Reserva;
import com.fiap.restaurante.repository.ReservaRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReservaHelper {

    public static Reserva gerarReserva() {
        return Reserva.builder()
                    .idMesa(UUID.fromString("dfce2cfe-dc1c-4e45-9838-a123aad1a241"))
                    .idRestaurante(UUID.randomUUID())
                    .horario(LocalDateTime.now()).build();
    }

    public static Reserva registrarReserva(Reserva reserva, ReservaRepository repository) {
        return repository.save(reserva);
    }
}
