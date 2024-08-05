package com.fiap.restaurante.service;

import com.fiap.restaurante.domain.Mesa;
import com.fiap.restaurante.domain.Reserva;
import com.fiap.restaurante.domain.Restaurante;
import com.fiap.restaurante.repository.MesaRepository;
import com.fiap.restaurante.repository.ReservaRepository;
import com.fiap.restaurante.repository.RestauranteRepository;
import com.fiap.restaurante.service.serviceImpl.ReservaServiceImpl;
import com.fiap.restaurante.utils.ReservaHelper;
import com.fiap.restaurante.utils.RestauranteHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;
    @Mock
    private RestauranteRepository restauranteRepository;
    @Mock
    private MesaRepository mesaRepository;

    private ReservaService reservaService;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        reservaService = new ReservaServiceImpl(reservaRepository,restauranteRepository, mesaRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    void devePermitirRegistrarReserva() {
        var idRestarante = UUID.fromString("c73c7093-9457-443d-9d28-c022f72178e4");
        var idMesa = UUID.fromString("dfce2cfe-dc1c-4e45-9838-a123aad1a241");

        var mesa = new Mesa();
        mesa.setId(idMesa);
        mesa.setIdRestaurante(idRestarante);
        mesa.setLugares(50);
        when(mesaRepository.save(any(Mesa.class))).thenAnswer(i -> i.getArgument(0));
        when(mesaRepository.findById(idMesa)).thenReturn(Optional.of(mesa));

        List<Mesa> mesas = new ArrayList<>();
        mesas.add(mesa);

        var restaurante = RestauranteHelper.gerarRegistro();
        restaurante.setId(idRestarante);
        restaurante.setMesas(mesas);
        when(restauranteRepository.save(any(Restaurante.class))).thenAnswer(i -> i.getArgument(0));
        when(restauranteRepository.findById(idRestarante)).thenReturn(Optional.of(restaurante));

        var reserva = ReservaHelper.gerarReserva();
        reserva.setIdRestaurante(idRestarante);
        when(reservaRepository.save(any(Reserva.class))).thenAnswer(i -> i.getArgument(0));

        var reservaRegistrada = reservaService.cadastrar(reserva);

        assertThat(reservaRegistrada).isInstanceOf(Reserva.class).isNotNull();
        assertThat(reservaRegistrada.getIdRestaurante()).isEqualTo(reserva.getIdRestaurante());
        assertThat(reservaRegistrada.getIdMesa()).isEqualTo(reserva.getIdMesa());

        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }


//    @Test
//    void devePermitirAlterarReserva() {
//        fail("implementar teste padrão e exceções");
//    }

    @Test
    void devePermitirRemoverReserva() {
        var id = UUID.fromString("51fa607a-1e61-11ee-be56-0242ac120002");
        var reserva = ReservaHelper.gerarReserva();
        reserva.setId(id);

        when(reservaRepository.findById(id))
                .thenReturn(Optional.of(reserva));

        doNothing().when(reservaRepository).deleteById(id);

        var resultado = reservaService.deletarReserva(id);

        assertThat(resultado).isTrue();

        verify(reservaRepository, times(1)).findById(any(UUID.class));
        verify(reservaRepository, times(1)).delete(any(Reserva.class));
    }

    @Test
    void deveGerarExcecao_removeIdInexistente() {
        var id = UUID.randomUUID();

        when(reservaRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> reservaService.deletarReserva(id)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Reserva inexistente");

        verify(reservaRepository, times(1)).findById(any(UUID.class));
        verify(reservaRepository, never()).deleteById(any(UUID.class));
    }

    @Test
    void devePermitirListaReserva() {
        Page<Reserva> listaMensagens = new PageImpl<>(Arrays.asList(
                ReservaHelper.gerarReserva(),
                ReservaHelper.gerarReserva()
        ));

        when(reservaRepository.findAll(any(Pageable.class))).thenReturn(listaMensagens);

        var listaObtida = reservaService.listar(Pageable.unpaged());

        assertThat(listaObtida).hasSize(2);
        assertThat(listaObtida.getContent()).asList().allSatisfy(mensagem -> {
            assertThat(mensagem).isNotNull().isInstanceOf(Reserva.class);
        });

        verify(reservaRepository, times(1)).findAll(any(Pageable.class));
    }

}
