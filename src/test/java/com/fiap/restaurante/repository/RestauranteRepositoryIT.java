package com.fiap.restaurante.repository;

import com.fiap.restaurante.domain.Restaurante;
import com.fiap.restaurante.utils.RestauranteHelper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@Transactional
class RestauranteRepositoryIT {

    @Autowired
    private RestauranteRepository repository;

    @Test
    void devePermitirCriarTabelaRestaurante() {
        var totalRegistros = repository.count();
        assertThat(totalRegistros).isNotNegative();
    }

    @Nested
    class RegistrarRestaurante {

        @Test
        void devePermitirRegistrarRestaurante() {
            // Arrange
            var registro = RestauranteHelper.gerarRegistro();
            // Action
            var entityRecebida = RestauranteHelper.registrarRestaurante(registro, repository);

            assertThat(entityRecebida)
                    .isInstanceOf(Restaurante.class)
                    .isNotNull();
            assertThat(entityRecebida.getRazaoSocial()).isEqualTo("Restaurante Teste");
            assertThat(entityRecebida.getNomeFantasia()).isEqualTo("RESTAURANTE TESTE");
        }
    }

    @Nested
    class RemoverRestaurante {

        @Test
        void devePermitirRemoverRestaurantePorId() {
            // Arrange
            var registro = RestauranteHelper.gerarRegistro();
            var id = RestauranteHelper.registrarRestaurante(registro, repository).getId();
            // Action
            repository.deleteById(id);
            var entityRecebidaOptional = repository.findById(id);
            // Assert
            assertThat(entityRecebidaOptional).isEmpty();
        }
    }

    @Nested
    class ListarRestaurantes {

        @Test
        void devePermitirListarRestaurantes() {
            // Assert
            var registro1 = RestauranteHelper.gerarRegistro();
            var registro2 = RestauranteHelper.gerarRegistro();
            RestauranteHelper.registrarRestaurante(registro1, repository);
            RestauranteHelper.registrarRestaurante(registro2, repository);
            // Action
            var lista = repository.findAll();
            // Assert
            assertThat(lista).hasSizeGreaterThan(1);
        }
    }

}
