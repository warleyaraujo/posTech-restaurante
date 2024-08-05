package com.fiap.restaurante.repository;

import com.fiap.restaurante.domain.Restaurante;
import com.fiap.restaurante.utils.RestauranteHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RestauranteRepositoryTest {

    @Mock
    private RestauranteRepository repository;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class RegistrarRestaurante {
        @Test
        void devePermitirRegistrarRestaurante() {
            // Arrange
            var registro = RestauranteHelper.gerarRegistro();
            when(repository.save(any(Restaurante.class))).thenReturn(registro);
            // Action
            var registroArmazenado = repository.save(registro);
            // Assert
            assertThat(registroArmazenado).isNotNull().isEqualTo(registro);
            verify(repository, times(1)).save(any(Restaurante.class));
        }
    }

    @Nested
    class RemoverRestaurante {
        @Test
        void devePermitirRemoverRestaurantePorId() {
            // Arrange
            var id = UUID.fromString("1f2b6507-0443-40c5-b5ac-7e89cbdfee19");
            doNothing().when(repository).deleteById(id);
            // Act
            repository.deleteById(id);
            // Assert
            verify(repository, times(1)).deleteById(any(UUID.class));
        }
    }

    @Nested
    class ListarRestaurantes {
        @Test
        void devePermitirListarRestaurantes() {
            // Arrange
            var reg1 = RestauranteHelper.gerarRegistro();
            var reg2 = RestauranteHelper.gerarRegistro();
            var lista = Arrays.asList(reg1, reg2);
            when(repository.findAll()).thenReturn(lista);
            // Action
            var listaRecebida = repository.findAll();
            // Assert
            assertThat(listaRecebida).hasSizeGreaterThan(1);
            verify(repository, times(1)).findAll();
        }
    }
}
