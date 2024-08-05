package com.fiap.restaurante.service;

import com.fiap.restaurante.domain.Avaliacao;
import com.fiap.restaurante.domain.dto.AvaliacaoDto;
import com.fiap.restaurante.domain.exceptions.AvaliacaoNotFoundException;
import com.fiap.restaurante.repository.AvaliacaoRepository;
import com.fiap.restaurante.service.serviceImpl.AvaliacaoServiceImpl;
import com.fiap.restaurante.utils.AvaliacaoHelper;
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
import static org.mockito.Mockito.*;
public class AvaliacaoServiceTest {

    private AvaliacaoService avaliacaoService;

    @Mock
    private AvaliacaoRepository  avaliacaoRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        avaliacaoService = new AvaliacaoServiceImpl(avaliacaoRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class Avaliar {

        @Test
        void devePermitirAvaliar() {
            var avaliacao = AvaliacaoHelper.gerarAvaliacao();

            when(avaliacaoRepository.save(any(Avaliacao.class)))
                    .thenAnswer(i -> i.getArgument(0));

            var avaliacaoRegistrada = avaliacaoService.cadastrar(avaliacaoService.avaliacaoDto(avaliacao));
            assertThat(avaliacaoRegistrada).isInstanceOf(AvaliacaoDto.class).isNotNull();
            assertThat(avaliacaoRegistrada.nota()).isEqualTo(avaliacao.getNota());
            assertThat(avaliacaoRegistrada.comentario()).isEqualTo(avaliacao.getComentario());
            verify(avaliacaoRepository, times(1)).save(any(Avaliacao.class));
        }
    }

    @Nested
    class ListarTodasAvaliacoes {
        @Test
        void devePermitirListarAvaliacoes() {
            // Arrange
            var avaliacao1 = AvaliacaoHelper.gerarAvaliacao();
            var avaliacao2 = AvaliacaoHelper.gerarAvaliacao();
            Page<Avaliacao> lista = new PageImpl<>(Arrays.asList(avaliacao1, avaliacao2));

            when(avaliacaoRepository.findAll(any(Pageable.class))).thenReturn(lista);

            var listaRecebida = avaliacaoService.listarAvaliacoes(Pageable.unpaged());

            assertThat(listaRecebida).hasSizeGreaterThan(1);
            verify(avaliacaoRepository, times(1)).findAll(any(Pageable.class));
        }
    }

    @Nested
    class BuscarAvaliacaoPorId {

        @Test
        void devePermitirBuscarPorId() {
            var id = UUID.randomUUID();
            var avaliacao = AvaliacaoHelper.gerarAvaliacao();
            avaliacao.setId(id);
            var avaliacaoDto = avaliacaoService.avaliacaoDto(avaliacao);

            when(avaliacaoRepository.findById(any(UUID.class)))
                    .thenReturn(Optional.of(avaliacao));

            var avaliacaoRecebida = avaliacaoService
                    .buscarPorId(avaliacao.getId());

            assertThat(avaliacaoRecebida).isEqualTo(avaliacaoDto);
            verify(avaliacaoRepository, times(1)).findById(any(UUID.class));
        }

        @Test
        void deveGerarExcecaoNaoExiste() {
            var id = UUID.randomUUID();

            when(avaliacaoRepository.findById(id)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> avaliacaoService.buscarPorId(id))
                    .isInstanceOf(AvaliacaoNotFoundException.class)
                    .hasMessage("Avaliacao nao encontrada com o ID: " + id);
            verify(avaliacaoRepository, times(1)).findById(id);
        }
    }

    @Nested
    class EditarAvaliacao {

        @Test
        void devePermitirAlterarAvaliacao(){
            //arr
            var id = UUID.randomUUID();
            var avaliacao = AvaliacaoHelper.gerarAvaliacao();
            avaliacao.setId(id);

            var avaliacaoAlterada = avaliacao;
            avaliacaoAlterada.setNota("4");
            avaliacaoAlterada.setComentario("muito bom");

            when(avaliacaoRepository.findById(any(UUID.class)))
                    .thenReturn(Optional.of(avaliacao));

            when(avaliacaoRepository.save(any(Avaliacao.class)))
                    .thenAnswer(i -> i.getArgument(0));


            var avaliacaoRecebida = avaliacaoService.editarAvaliacao(id, avaliacaoService.avaliacaoDto(avaliacaoAlterada));
            var avaliacaoRecebida1 = new Avaliacao(avaliacaoRecebida);

            assertThat(avaliacaoRecebida1).isInstanceOf(Avaliacao.class).isNotNull();


            assertThat(avaliacaoRecebida1.getNota()).isEqualTo(avaliacaoAlterada.getNota());
            assertThat(avaliacaoRecebida1.getComentario()).isEqualTo(avaliacaoAlterada.getComentario());
        }
    }

    @Nested
    class RemoverAvaliacao {

        @Test
        void devePermitirRemoverAvaliacao() {
            var id = UUID.fromString("51fa607a-1e61-11ee-be56-0242ac120002");
            var comentario = AvaliacaoHelper.gerarAvaliacao();
            comentario.setId(id);
            when(avaliacaoRepository.findById(id))
                    .thenReturn(Optional.of(comentario));
            doNothing()
                    .when(avaliacaoRepository).deleteById(id);

            var resultado = avaliacaoService.deletarAvaliacao(id);

            assertThat(resultado).isTrue();
            verify(avaliacaoRepository, times(1)).findById(any(UUID.class));
            verify(avaliacaoRepository, times(1)).delete(any(Avaliacao.class));
        }
    }

}
