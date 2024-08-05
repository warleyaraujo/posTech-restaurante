package com.fiap.restaurante.service;


import com.fiap.restaurante.domain.dto.AvaliacaoDto;
import com.fiap.restaurante.service.serviceImpl.AvaliacaoServiceImpl;
import com.fiap.restaurante.utils.AvaliacaoHelper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class AvaliacaoServiceIT {

    @Autowired
    private AvaliacaoServiceImpl avaliacaoService;

    @Nested
    class RegistrarAvaliacao {
        @Test
        void devePermitirRegistrarAvaliacao() {
            var avaliacao = AvaliacaoHelper.gerarAvaliacao();
            var avaliacaoDto = avaliacaoService.avaliacaoDto(avaliacao);

            var avaliacaoRegistrada = avaliacaoService.cadastrar(avaliacaoDto);
            assertThat(avaliacaoRegistrada).isInstanceOf(AvaliacaoDto.class)
                    .isNotNull();
            assertThat(avaliacaoRegistrada.nota())
                    .isNotNull()
                    .isNotEmpty()
                    .isEqualTo(avaliacao.getNota());
            assertThat(avaliacaoRegistrada.comentario())
                    .isNotNull()
                    .isNotEmpty()
                    .isEqualTo(avaliacao.getComentario());
        }
    }

    @Nested
    class ListarAvaliacoes {
        @Test
        void devePermitirListarAvaliacoes() {
            var avaliacao1 = avaliacaoService.avaliacaoDto(AvaliacaoHelper.gerarAvaliacao());
            avaliacaoService.cadastrar(avaliacao1);
            var avaliacao2 = avaliacaoService.avaliacaoDto(AvaliacaoHelper.gerarAvaliacao());
            avaliacaoService.cadastrar(avaliacao2);

            var listaRecebida = avaliacaoService.listarAvaliacoes(Pageable.unpaged());

            assertThat(listaRecebida).hasSizeGreaterThan(1);
        }

    }

    @Nested
    class RemoverAvaliacao {

        @Test
        void devePermitirRemoverAvaliacao() {

            var avaliacao = AvaliacaoHelper.gerarAvaliacao();
            var avaliacaoDto = avaliacaoService.avaliacaoDto(avaliacao);

            var avaliacaoRegistrada = avaliacaoService.cadastrar(avaliacaoDto);

            var resultado = avaliacaoService.deletarAvaliacao(avaliacaoRegistrada.id());

            assertThat(resultado).isTrue();
        }
    }

}
