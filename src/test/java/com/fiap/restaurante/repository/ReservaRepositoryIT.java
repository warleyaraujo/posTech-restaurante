package com.fiap.restaurante.repository;

import com.fiap.restaurante.domain.Reserva;
import com.fiap.restaurante.utils.ReservaHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class ReservaRepositoryIT {

    @Autowired
    private ReservaRepository reservaRepository;

    @Test
    void devePermitirCriarTabela() {
        var totalRegistros = reservaRepository.count();
        assertThat(totalRegistros).isNotNegative();
    }

    @Test
    void deveRegistrarReserva() {
        var id = UUID.randomUUID();
        var reserva = ReservaHelper.gerarReserva();
        reserva.setId(id);

        var reservaRecebida = ReservaHelper.registrarReserva(reserva, reservaRepository);

        assertThat(reservaRecebida).isInstanceOf(Reserva.class).isNotNull();
        assertThat(reservaRecebida.getId()).isEqualTo(reserva.getId());
    }

    @Test
    void deveListarReserva() {
        var registro1 = ReservaHelper.gerarReserva();
        var registro2 = ReservaHelper.gerarReserva();
        ReservaHelper.registrarReserva(registro1, reservaRepository);
        ReservaHelper.registrarReserva(registro2, reservaRepository);

        var lista = reservaRepository.findAll();

        assertThat(lista).hasSizeGreaterThan(1);
    }

    @Test
    void deveBuscarReserva() {
        var registro = ReservaHelper.gerarReserva();
        ReservaHelper.registrarReserva(registro, reservaRepository);

        var reservaRecebidaOptional = reservaRepository.findById(registro.getId());

        assertThat(reservaRecebidaOptional).isPresent();
        reservaRecebidaOptional.ifPresent(
                reserva -> {
                    assertThat(reserva.getId()).isEqualTo(registro.getId());
                    assertThat(reserva.getIdMesa()).isEqualTo(registro.getIdMesa());
                    assertThat(reserva.getIdRestaurante()).isEqualTo(registro.getIdRestaurante());
                });
    }

    @Test
    void deveExcluirMensagem() {
        var registro = ReservaHelper.gerarReserva();

        var id = ReservaHelper.registrarReserva(registro, reservaRepository).getId();

        reservaRepository.deleteById(id);

        var reservaRecebidaOptional = reservaRepository.findById(id);

        assertThat(reservaRecebidaOptional).isEmpty();
    }

}
