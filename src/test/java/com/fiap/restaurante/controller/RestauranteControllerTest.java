package com.fiap.restaurante.controller;

import com.callibrity.logging.test.LogTracker;
import com.callibrity.logging.test.LogTrackerStub;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fiap.restaurante.domain.Restaurante;
import com.fiap.restaurante.domain.dto.RestauranteDto;
import com.fiap.restaurante.domain.exceptions.RestauranteNotFoundException;
import com.fiap.restaurante.domain.exceptions.handler.GlobalExceptionHandler;
import com.fiap.restaurante.service.RestauranteService;
import com.fiap.restaurante.utils.RestauranteHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestauranteControllerTest {

    private MockMvc mockMvc;

    @RegisterExtension
    LogTrackerStub logTracker = LogTrackerStub.create().recordForLevel(LogTracker.LogLevel.INFO)
            .recordForType(RestauranteController.class);

    @Mock
    private RestauranteService restauranteService;

    ObjectMapper objectMapper = new ObjectMapper();

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        RestauranteController restauranteController = new RestauranteController(restauranteService);

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        mockMvc = MockMvcBuilders.standaloneSetup(restauranteController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class RegistrarRestaurante {
        @Test
        void devePermitirCadastrarRestaurante() throws Exception {
            Restaurante restauranteRequest = RestauranteHelper.gerarRegistro();
            when(restauranteService.cadastrarRestaurante(any(RestauranteDto.class)))
                    .thenReturn(new Restaurante());

            mockMvc.perform(post("/restaurantes")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(objectMapper, restauranteRequest)))
                    .andExpect(status().isCreated());

            verify(restauranteService, times(1))
                    .cadastrarRestaurante(any(RestauranteDto.class));
        }
    }

    @Nested
    class ListarClientes {

        @Test
        void devePermitirListarRestaurantes() throws Exception {
            when(restauranteService.listar())
                    .thenReturn(Collections.singletonList(new Restaurante()));

            mockMvc.perform(get("/restaurantes")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0]").exists());

            verify(restauranteService, times(1))
                    .listar();
        }

    }

    @Nested
    class ApagarRestaurante {

        @Test
        void devePermitirApagarRestaurante() throws Exception {
            UUID id = UUID.randomUUID();

            doNothing().when(restauranteService).deletarRestaurante(any(UUID.class));

            mockMvc.perform(delete("/restaurantes/{id}", id))
                    .andExpect(status().isNoContent());

            verify(restauranteService, times(1))
                    .deletarRestaurante(any(UUID.class));
        }

        @Test
        void deveGerarExcecao_QuandoApagarRestaurante_IdNaoExistente() throws Exception {
            UUID id = UUID.randomUUID();

            doThrow(new RestauranteNotFoundException("Restaurante não encontrado"))
                    .when(restauranteService).deletarRestaurante(eq(id)); // Lançar exceção quando tentar apagar com este ID

            mockMvc.perform(delete("/restaurantes/{id}", id)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());

            verify(restauranteService, times(1))
                    .deletarRestaurante(eq(id)); // Verificar se o serviço foi chamado com este ID
        }

    }

    public static String asJsonString(final ObjectMapper objectMapper, final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}