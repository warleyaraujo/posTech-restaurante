package com.fiap.restaurante.repository;

import com.fiap.restaurante.domain.Avaliacao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;



import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;

public class AvaliacaoRepositoryTeste {

    @Mock
    private AvaliacaoRepository avaliacaoRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirAvaliar() {
        var avaliacao = gerarAvaliacao();

        when(avaliacaoRepository.save(any(Avaliacao.class))).thenReturn(avaliacao);

        var avaliacaoRegistrada = avaliacaoRepository.save(avaliacao);

        assertThat(avaliacaoRegistrada)
                .isNotNull()
                .isEqualTo(avaliacao);
    }

    @Test
    void devePermitirBuscarAvaliacao() {

        var id = UUID.randomUUID();
        var avaliacao = gerarAvaliacao();
        avaliacao.setId(id);

        when(avaliacaoRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(avaliacao));

        var avaliacaoRecebidaOptional = avaliacaoRepository.findById(id);

        assertThat(avaliacaoRecebidaOptional)
                .isPresent()
                .containsSame(avaliacao);
        avaliacaoRecebidaOptional.ifPresent(avaliacaoRecebida ->{
            assertThat(avaliacaoRecebida.getId()).isEqualTo(avaliacao.getId());
            assertThat(avaliacaoRecebida.getComentario()).isEqualTo(avaliacao.getComentario());
        });
        verify(avaliacaoRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    void devePermitirRemoverAvaliacao() {

        var id = UUID.randomUUID();
        doNothing().when(avaliacaoRepository).deleteById(any(UUID.class));

        avaliacaoRepository.deleteById(id);

        verify(avaliacaoRepository, times(1))
                .deleteById(any(UUID.class));
    }

    @Test
    void devePermitirListarAvaliacoes() {
        var avaliacao1 = gerarAvaliacao();
        var avaliacao2 = gerarAvaliacao();
        var listarAvaliacoes = Arrays.asList(
                avaliacao1,
                avaliacao2);
        when(avaliacaoRepository.findAll()).thenReturn(listarAvaliacoes);

        var avaliacoesRecebidas = avaliacaoRepository.findAll();

        assertThat(avaliacoesRecebidas)
                .hasSize(2)
                .containsExactlyInAnyOrder(avaliacao1, avaliacao2);
        verify(avaliacaoRepository, times(1)).findAll();
    }

    private Avaliacao gerarAvaliacao() {
        return Avaliacao.builder()
                .nota("5")
                .comentario("muito bom")
                .build();
    }
}
