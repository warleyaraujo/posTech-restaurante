package com.fiap.restaurante.service;

import com.fiap.restaurante.domain.Restaurante;
import com.fiap.restaurante.domain.dto.EnderecoDto;
import com.fiap.restaurante.domain.dto.RestauranteDto;
import com.fiap.restaurante.domain.embedded.Endereco;
import com.fiap.restaurante.repository.MesaRepository;
import com.fiap.restaurante.repository.RestauranteRepository;
import com.fiap.restaurante.service.serviceImpl.RestauranteServiceImpl;
import com.fiap.restaurante.utils.RestauranteHelper;
import org.checkerframework.checker.units.qual.N;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RestauranteServiceTest {

    @Mock
    private RestauranteRepository restauranteRepository;

    @Mock
    private MesaRepository mesaRepository;

    @InjectMocks
    private RestauranteServiceImpl restauranteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    class RegistrarRestaurante {
        @Test
        void cadastrarRestaurante() {
            var id = UUID.randomUUID();
            var restaurante = RestauranteHelper.gerarRegistro();
            restaurante.setId(id);

            when(restauranteRepository.save(any(Restaurante.class)))
                    .thenAnswer(i -> i.getArgument(0));

            var enderecoDto = new EnderecoDto("Logradouro", "123", "Complemento", "Bairro", "Cidade", "SP", "12345-678");
            var restauranteDto = new RestauranteDto("Restaurante Teste", "RESTAURANTE TESTE", "Teste", 0, LocalTime.parse("09:00"), LocalTime.parse("19:00"), enderecoDto);

            Restaurante result = restauranteService.cadastrarRestaurante(restauranteDto);

            assertThat(result).isInstanceOf(Restaurante.class).isNotNull();
            assertThat(result.getEndereco()).isInstanceOf(Endereco.class).isNotNull();
            assertThat(result.getRazaoSocial()).isEqualTo(restauranteDto.razaoSocial());
            assertThat(result.getTipoCozinha()).isEqualTo(restauranteDto.tipoCozinha());
            assertThat(result.getHoraAbertura()).isEqualTo(restauranteDto.horaAbertura());
            assertThat(result.getHoraEncerramento()).isEqualTo(restauranteDto.horaEncerramento());
            verify(restauranteRepository, times(1)).save(any(Restaurante.class));

        }
    }

    @Nested
    class ListarRestaurantes {
        @Test
        void listar() {
            List<Restaurante> restaurantes = new ArrayList<>();
            restaurantes.add(RestauranteHelper.gerarRegistro());
            restaurantes.add(RestauranteHelper.gerarRegistro());

            when(restauranteRepository.findAll()).thenReturn(restaurantes);

            List<Restaurante> result = restauranteService.listar();

            assertEquals(2, result.size());
            verify(restauranteRepository, times(1)).findAll();
        }
    }

    @Nested
    class RegistrarMesas {
        @Test
        void cadastraMesas() {
            UUID id = UUID.randomUUID();
            List<Integer> lugares = List.of(2, 4);

            Restaurante restaurante = RestauranteHelper.gerarRegistro();
            when(restauranteRepository.findById(id)).thenReturn(Optional.of(restaurante));

            restauranteService.cadastraMesas(id, lugares);

            verify(mesaRepository, times(2)).save(any());
            verify(restauranteRepository, times(1)).save(any());
        }
    }

    @Nested
    class EditarRestaurante {
        @Test
        void atualizarRestaurante() {
            UUID restauranteId = UUID.randomUUID();
            RestauranteDto restauranteDto = new RestauranteDto("Nova Razão Social", "Novo Nome Fantasia", "Nova Cozinha", 200,
                    null, null, new EnderecoDto("Novo Logradouro", "456", "Novo Complemento", "Novo Bairro", "Nova Cidade",
                    "SP", "98765-432"));
            Restaurante restaurante = RestauranteHelper.gerarRegistro();
            restaurante.setId(restauranteId);

            when(restauranteRepository.findById(restauranteId)).thenReturn(Optional.of(restaurante));
            when(restauranteRepository.save(any(Restaurante.class))).thenAnswer(i -> i.getArgument(0));

            Restaurante result = restauranteService.atualizarRestaurante(restauranteId, restauranteDto);

            assertEquals(restauranteDto.razaoSocial(), result.getRazaoSocial());
            assertEquals(restauranteDto.nomeFantasia(), result.getNomeFantasia());
            assertEquals(restauranteDto.tipoCozinha(), result.getTipoCozinha());
            assertEquals(restauranteDto.capacidade(), result.getCapacidade());
            assertEquals(restauranteDto.endereco().logradouro(), result.getEndereco().getLogradouro());
            assertEquals(restauranteDto.endereco().numero(), result.getEndereco().getNumero());
            assertEquals(restauranteDto.endereco().complemento(), result.getEndereco().getComplemento());
            assertEquals(restauranteDto.endereco().bairro(), result.getEndereco().getBairro());
            assertEquals(restauranteDto.endereco().cidade(), result.getEndereco().getCidade());
            assertEquals(restauranteDto.endereco().uf(), result.getEndereco().getUf());
            assertEquals(restauranteDto.endereco().cep(), result.getEndereco().getCep());

            verify(restauranteRepository, times(1)).findById(restauranteId);
            verify(restauranteRepository, times(1)).save(any(Restaurante.class));
        }
    }

    @Nested
    class RemoverRestaurante {
        @Test
        void deletarRestaurante() {
            UUID id = UUID.randomUUID();
            Restaurante restaurante = RestauranteHelper.gerarRegistro();
            when(restauranteRepository.findById(id)).thenReturn(Optional.of(restaurante));

            restauranteService.deletarRestaurante(id);

            verify(restauranteRepository, times(1)).delete(any());
        }
    }
}