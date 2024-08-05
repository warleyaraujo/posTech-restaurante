package com.fiap.restaurante.repository;

import com.fiap.restaurante.domain.Cliente;
import com.fiap.restaurante.utils.ClienteHelper;
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
class ClienteRepositoryIT {
    @Autowired
    private ClienteRepository repository;

    @Test
    void devePermitirCriarTabela() {
        var totalRegistros = repository.count();
        assertThat(totalRegistros).isNotNegative();
    }

    @Nested
    class RegistrarCliente {
        @Test
        void devePermitirRegistrarCliente() {
            // Arrange
            var registro = ClienteHelper.gerarRegistro();
            // Action
            var entityRecebida = ClienteHelper.registrarCliente(registro, repository);
            // Assert
            assertThat(entityRecebida)
                    .isInstanceOf(Cliente.class)
                    .isNotNull();
            assertThat(entityRecebida.getNome()).isEqualTo("Cliente Automatizado");
            assertThat(entityRecebida.getEmail()).isEqualTo("email@email.teste");
        }
    }

    @Nested
    class RemoverCliente {
        @Test
        void devePermitirRemoverClientePorId() {
            // Arrange
            var registro = ClienteHelper.gerarRegistro();
            var id = ClienteHelper.registrarCliente(registro, repository).getId();
            // Action
            repository.deleteById(id);
            var entityRecebidaOptional = repository.findById(id);
            // Assert
            assertThat(entityRecebidaOptional).isEmpty();
        }
    }

    @Nested
    class Buscarcliente {
        @Test
        void devePermitirBuscarClientePorId() {
            // Arrange
            var registro = ClienteHelper.gerarRegistro();
            ClienteHelper.registrarCliente(registro, repository);
            // Action
            var entityRecebidaOptional = repository.findById(registro.getId());
            // Assert
            assertThat(entityRecebidaOptional).isPresent();
            entityRecebidaOptional.ifPresent(
                    cliente -> {
                        assertThat(cliente.getId()).isEqualTo(registro.getId());
                        assertThat(cliente.getNome()).isEqualTo(registro.getNome());
                        assertThat(cliente.getEmail()).isEqualTo(registro.getEmail());
                    });
        }
    }

    @Nested
    class ListarClientes {
        @Test
        void devePermitirListarClientes() {
            // Assert
            var registro1 = ClienteHelper.gerarRegistro();
            var registro2 = ClienteHelper.gerarRegistro();
            ClienteHelper.registrarCliente(registro1, repository);
            ClienteHelper.registrarCliente(registro2, repository);
            // Action
            var lista = repository.findAll();
            // Assert
            assertThat(lista).hasSizeGreaterThan(1);
        }
    }
}
