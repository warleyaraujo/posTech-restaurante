package com.fiap.restaurante.service;

import com.fiap.restaurante.domain.Restaurante;
import com.fiap.restaurante.domain.dto.EnderecoDto;
import com.fiap.restaurante.domain.dto.RestauranteDto;
import com.fiap.restaurante.domain.embedded.Endereco;
import com.fiap.restaurante.repository.RestauranteRepository;
import com.fiap.restaurante.service.serviceImpl.RestauranteServiceImpl;
import com.fiap.restaurante.utils.RestauranteHelper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class RestauranteServiceIT {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private RestauranteServiceImpl restauranteService;

    @Nested
    class RegistrarRestaurante {

        @Test
        void devePermitirCadastrarRestaurante() {
            var restaurante = RestauranteHelper.gerarRegistro();
            var enderecoDto = new EnderecoDto("Logradouro", "123", "Complemento", "Bairro", "Cidade", "SP", "12345-678");
            var restauranteDto = new RestauranteDto("Restaurante Teste", "RESTAURANTE TESTE", "Teste", 0, LocalTime.parse("09:00"), LocalTime.parse("19:00"), enderecoDto);

            var restauranteRegistrado = restauranteService.cadastrarRestaurante(restauranteDto);
            assertThat(restauranteRegistrado).isInstanceOf(Restaurante.class).isNotNull();
            assertThat(restauranteRegistrado.getEndereco()).isInstanceOf(Endereco.class).isNotNull();
            assertThat(restauranteRegistrado.getRazaoSocial())
                    .isNotNull()
                    .isNotEmpty()
                    .isEqualTo(restaurante.getRazaoSocial());
            assertThat(restauranteRegistrado.getNomeFantasia())
                    .isNotNull()
                    .isNotEmpty()
                    .isEqualTo(restaurante.getNomeFantasia());
            assertThat(restauranteRegistrado.getTipoCozinha())
                    .isNotNull()
                    .isNotEmpty()
                    .isEqualTo(restaurante.getTipoCozinha());
            assertThat(restauranteRegistrado.getCapacidade())
                    .isNotNull()
                    .isEqualTo(restaurante.getCapacidade());
            assertThat(restauranteRegistrado.getHoraAbertura())
                    .isNotNull()
                    .isEqualTo(restaurante.getHoraAbertura());
            assertThat(restauranteRegistrado.getHoraEncerramento())
                    .isNotNull()
                    .isEqualTo(restaurante.getHoraEncerramento());
        }
    }

    @Nested
    class RemoverRestaurante {

        @Test
        void devePermitirApagarRestaurante() {
            var restaurante = RestauranteHelper.gerarRegistro();
            var enderecoDto = new EnderecoDto("Logradouro", "123", "Complemento", "Bairro", "Cidade", "SP", "12345-678");
            var restauranteDto = new RestauranteDto("Restaurante Teste", "RESTAURANTE TESTE", "Teste", 0, LocalTime.parse("09:00"), LocalTime.parse("19:00"), enderecoDto);

            var restauranteRegistrado = restauranteService.cadastrarRestaurante(restauranteDto);


            assertThat(restauranteRepository.findById(restauranteRegistrado.getId())).isPresent();

            restauranteService.deletarRestaurante(restauranteRegistrado.getId());

            assertThat(restauranteRepository.findById(restauranteRegistrado.getId())).isEmpty();
        }
    }

    @Nested
    class ListarRestaurantes {

        @Test
        void deveRetornarTodosOsRestaurantes() {
            Restaurante restaurante1 = restauranteService.cadastrarRestaurante(criarRestauranteDto("Restaurante 1"));
            Restaurante restaurante2 = restauranteService.cadastrarRestaurante(criarRestauranteDto("Restaurante 2"));

            List<Restaurante> restaurantes = restauranteService.listar();

            assertThat(restaurantes).contains(restaurante1, restaurante2);
        }
    }

    @Nested
    class AtualizarRestaurante {

        @Test
        void deveAtualizarInformacoesDoRestaurante() {
            Restaurante restaurante = restauranteService.cadastrarRestaurante(criarRestauranteDto("Restaurante Original"));

            RestauranteDto restauranteDtoAtualizado = criarRestauranteDto("Restaurante Atualizado");

            Restaurante restauranteAtualizado = restauranteService.atualizarRestaurante(restaurante.getId(), restauranteDtoAtualizado);

            assertThat(restauranteAtualizado.getRazaoSocial()).isEqualTo(restauranteDtoAtualizado.razaoSocial());
            assertThat(restauranteAtualizado.getNomeFantasia()).isEqualTo(restauranteDtoAtualizado.nomeFantasia());
            assertThat(restauranteAtualizado.getTipoCozinha()).isEqualTo(restauranteDtoAtualizado.tipoCozinha());
        }
    }

    private RestauranteDto criarRestauranteDto(String nome) {
        EnderecoDto enderecoDto = new EnderecoDto("Logradouro", "123", "Complemento", "Bairro", "Cidade", "SP", "12345-678");
        return new RestauranteDto(nome, nome.toUpperCase(), "Tipo de Cozinha", 50, LocalTime.parse("10:00"), LocalTime.parse("22:00"), enderecoDto);
    }
}
