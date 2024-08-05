package com.fiap.restaurante.service;

import com.fiap.restaurante.domain.Cliente;
import com.fiap.restaurante.domain.dto.ClienteDto;
import com.fiap.restaurante.domain.exceptions.ClienteNotFoundException;
import com.fiap.restaurante.repository.ClienteRepository;
import com.fiap.restaurante.service.serviceImpl.ClienteServiceImpl;
import com.fiap.restaurante.utils.ClienteHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ClienteServiceTest {

    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {

        openMocks = MockitoAnnotations.openMocks(this);
        clienteService = new ClienteServiceImpl(clienteRepository);

    }

    @AfterEach
    void tearDow() throws Exception {
        openMocks.close();
    }

    @Nested
    class RegistrarCliente {
        @Test
        void devePermitirCadastrarCliente() {
            var cliente = ClienteHelper.gerarRegistro();

            when(clienteRepository.save(any(Cliente.class)))
                    .thenAnswer(i -> i.getArgument(0));

            var clienteRegistrado = clienteService.cadastrarCliente(clienteService.clienteToDto(cliente));
            assertThat(clienteRegistrado).isInstanceOf(ClienteDto.class).isNotNull();
            assertThat(clienteRegistrado.nome()).isEqualTo(cliente.getNome());
            assertThat(clienteRegistrado.email()).isEqualTo(cliente.getEmail());
            assertThat(clienteRegistrado.fone()).isEqualTo(cliente.getFone());
            verify(clienteRepository, times(1)).save(any(Cliente.class));
       }
    }

    @Nested
    class RemoverCliente {

        @Test
        void devePermitirApagarMensagem() {
            var id = UUID.fromString("51fa607a-1e61-11ee-be56-0242ac120002");
            var mensagem = ClienteHelper.gerarRegistro();
            mensagem.setId(id);
            when(clienteRepository.findById(id))
                    .thenReturn(Optional.of(mensagem));
            doNothing()
                    .when(clienteRepository).deleteById(id);

            var resultado = clienteService.deletarCliente(id);

            assertThat(resultado).isTrue();
            verify(clienteRepository, times(1)).findById(any(UUID.class));
            verify(clienteRepository, times(1)).delete(any(Cliente.class));
        }
    }

    @Nested
    class ListarClientes {
        @Test
        void devePermitirListarClientes() {
            // Arrange
            var reg1 = ClienteHelper.gerarRegistro();
            var reg2 = ClienteHelper.gerarRegistro();
            Page<Cliente> lista = new PageImpl<>(Arrays.asList(reg1, reg2));

            when(clienteRepository.findAll(any(Pageable.class))).thenReturn(lista);
            // Action
            var listaRecebida = clienteService.listarTodos(Pageable.unpaged());
            // Assert
            assertThat(listaRecebida).hasSizeGreaterThan(1);
            verify(clienteRepository, times(1)).findAll(any(Pageable.class));
        }
    }

    @Nested
    class BuscarPorId {
        @Test
        void devePermitirBuscarPorId() {
            var id = UUID.randomUUID();
            var cliente = ClienteHelper.gerarRegistro();
            cliente.setId(id);
            var clienteDto = clienteService.clienteToDto(cliente);

            when(clienteRepository.findById(any(UUID.class)))
                    .thenReturn(Optional.of(cliente));

            var clienteObtido = clienteService
                    .buscarPorId(cliente.getId());

            assertThat(clienteObtido).isEqualTo(clienteDto);
            verify(clienteRepository, times(1)).findById(any(UUID.class));
        }

        @Test
        void deveGerarExcecao_QuandoBuscarPorId_NaoExiste() {
            var id = UUID.randomUUID();

            when(clienteRepository.findById(id)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> clienteService.buscarPorId(id))
                    .isInstanceOf(ClienteNotFoundException.class)
                    .hasMessage("Cliente não encontrado com o ID: " + id);
            verify(clienteRepository, times(1)).findById(id);
        }
    }

    @Nested
    class EditarCliente {

        @Test
        void devePermitirAlterarCliente(){
            //arr
            var id = UUID.randomUUID();
            var cliente = ClienteHelper.gerarRegistro();
            cliente.setId(id);

            var clienteNovo = cliente;
            clienteNovo.setNome("Teste");
            clienteNovo.setFone("5499199999");
            clienteNovo.setEmail("teste2@teste.com");
            clienteNovo.setEndereco(cliente.getEndereco());

            when(clienteRepository.findById(any(UUID.class)))
                    .thenReturn(Optional.of(cliente));

            when(clienteRepository.save(any(Cliente.class)))
                    .thenAnswer(i -> i.getArgument(0));


            var clienteObtidoDto = clienteService.editarCliente(id, clienteService.clienteToDto(clienteNovo));
            var clienteObtido = new Cliente(clienteObtidoDto);
            // Asserts
            assertThat(clienteObtido).isInstanceOf(Cliente.class).isNotNull();


            assertThat(clienteObtido.getNome()).isEqualTo(clienteNovo.getNome());
            assertThat(clienteObtido.getFone()).isEqualTo(clienteNovo.getFone());
            assertThat(clienteObtido.getEmail()).isEqualTo(clienteNovo.getEmail());
        }
    }
}
