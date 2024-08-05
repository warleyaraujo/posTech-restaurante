package com.fiap.restaurante.repository;

import com.fiap.restaurante.domain.Reserva;
import com.fiap.restaurante.utils.ReservaHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class ReservaRepositoryTest {

    @Mock
    private ReservaRepository reservaReporitory;

    private AutoCloseable openMock;

    @BeforeEach
    void setup() {
        openMock = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMock.close();
    }

    @Test
    void deveRegistrarReserva() {
        var reserva = ReservaHelper.gerarReserva();

        when(reservaReporitory.save(any(Reserva.class))).thenReturn(reserva);

        var mensagemRetornada = reservaReporitory.save(reserva);

        assertThat(mensagemRetornada).isNotNull().isEqualTo(reserva);

        verify(reservaReporitory, times(1)).save(any(Reserva.class));
    }

    @Test
    void deveAlterarReserva() {
        var uuid = UUID.randomUUID();

        doNothing().when(reservaReporitory).deleteById(any(UUID.class));

        reservaReporitory.deleteById(uuid);

        verify(reservaReporitory, times(1)).deleteById(any(UUID.class));
    }



    @Test
    void deveBuscarReserva() {
        var uuid = UUID.randomUUID();
        var reserva = ReservaHelper.gerarReserva();
        reserva.setId(uuid);

        when(reservaReporitory.findById(any(UUID.class))).thenReturn(Optional.of(reserva));

        var mensagemRecebida = reservaReporitory.findById(uuid);

        assertThat(mensagemRecebida).isPresent().containsSame(reserva);

        mensagemRecebida.ifPresent(m->{
            assertThat(m.getId()).isEqualTo(reserva.getId());
        });

        verify(reservaReporitory, times(1)).findById(any(UUID.class));
    }

    @Test
    void deveListarMensagens() {
        var listaReservas = Arrays.asList(ReservaHelper.gerarReserva(),
                                            ReservaHelper.gerarReserva(),
                                            ReservaHelper.gerarReserva());

        when(reservaReporitory.findAll()).thenReturn(listaReservas);

        var mensagensRecebidas = reservaReporitory.findAll();

        assertThat(mensagensRecebidas).hasSize(3);

        verify(reservaReporitory, times(1)).findAll();
    }

}
