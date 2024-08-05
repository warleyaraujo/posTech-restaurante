package com.fiap.restaurante.repository;

import com.fiap.restaurante.domain.Cliente;
import com.fiap.restaurante.utils.ClienteHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ClienteRepositoryTest {
    @Mock
    private ClienteRepository repository;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class RegistrarCliente {
        @Test
        void devePermitirRegistrarCliente() {
            // Arrange
            var registro = ClienteHelper.gerarRegistro();
            when(repository.save(any(Cliente.class))).thenReturn(registro);
            // Action
            var registroArmazenado = repository.save(registro);
            // Assert
            assertThat(registroArmazenado).isNotNull().isEqualTo(registro);
            verify(repository, times(1)).save(any(Cliente.class));
        }
    }

    @Nested
    class RemoverCliente {
        @Test
        void devePermitirRemoverClientePorId() {
            // Arrange
            var id = UUID.fromString("1c5bc49b-f933-40d8-ada9-072615d81367");
            doNothing().when(repository).deleteById(id);
            // Act
            repository.deleteById(id);
            // Assert
            verify(repository, times(1)).deleteById(any(UUID.class));
        }
    }

    @Nested
    class Buscarcliente {
        @Test
        void devePermitirBuscarClientePorId() {
            // Arrange
            var id = UUID.fromString("2c888bee-5498-4f06-abd5-091f1f22a605");
            var entity = ClienteHelper.gerarRegistro();
            when(repository.findById(any(UUID.class))).thenReturn(Optional.of(entity));
            // Act
            var entityRecebidaOpcional = repository.findById(id);

            // Assert
            Assertions.assertThat(entityRecebidaOpcional).isPresent().containsSame(entity);
            entityRecebidaOpcional.ifPresent(entityRecebida -> {
                Assertions.assertThat(entityRecebida.getNome()).isEqualTo(entity.getNome());
                Assertions.assertThat(entityRecebida.getFone()).isEqualTo(entity.getFone());
            });
            verify(repository, times(1)).findById(any(UUID.class));
        }
    }

    @Nested
    class ListarClientes {
        @Test
        void devePermitirListarClientes() {
            // Arrange
            var reg1 = ClienteHelper.gerarRegistro();
            var reg2 = ClienteHelper.gerarRegistro();
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
