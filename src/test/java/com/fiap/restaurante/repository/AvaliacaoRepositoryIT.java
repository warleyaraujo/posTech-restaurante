package com.fiap.restaurante.repository;

import com.fiap.restaurante.domain.Avaliacao;
import com.fiap.restaurante.utils.AvaliacaoHelper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class AvaliacaoRepositoryIT {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Test
    void devePermitirCriarTabelaAvaliacao() {
        var totalAvaliacoes = avaliacaoRepository.count();
        assertThat(totalAvaliacoes).isNotNegative();
    }

    @Nested
    class Avaliar {
        @Test
        void devePermitirAvaliar() {
            var avaliacao = AvaliacaoHelper.gerarAvaliacao();

            var entityRecebida = AvaliacaoHelper.registrarAvaliacao(avaliacao, avaliacaoRepository);

            assertThat(entityRecebida)
                    .isInstanceOf(Avaliacao.class)
                    .isNotNull();
            assertThat(entityRecebida.getNota()).isEqualTo("5");
            assertThat(entityRecebida.getComentario()).isEqualTo("muito bom");

        }
    }

    @Nested
    class BuscarAvaliacao {
        @Test
        void deveBuscarAvaliacaoPorId() {
            var avaliacao = AvaliacaoHelper.gerarAvaliacao();
            AvaliacaoHelper.registrarAvaliacao(avaliacao, avaliacaoRepository);

            var entityRecebidaOptional = avaliacaoRepository.findById(avaliacao.getId());

            assertThat(entityRecebidaOptional).isPresent();
            entityRecebidaOptional.ifPresent(
                    avaliacao1 -> {
                        assertThat(avaliacao1.getId()).isEqualTo(avaliacao.getId());
                        assertThat(avaliacao1.getNota()).isEqualTo(avaliacao.getNota());
                        assertThat(avaliacao1.getComentario()).isEqualTo(avaliacao.getComentario());
                    });
        }
    }

    @Nested
    class ListarAvaliacoes {
        @Test
        void devePermitirListarAvaliacoes() {

            var avaliacao1 = AvaliacaoHelper.gerarAvaliacao();
            var avaliacao2 = AvaliacaoHelper.gerarAvaliacao();
            AvaliacaoHelper.registrarAvaliacao(avaliacao1, avaliacaoRepository);
            AvaliacaoHelper.registrarAvaliacao(avaliacao2, avaliacaoRepository);

            var lista = avaliacaoRepository.findAll();

            assertThat(lista).hasSizeGreaterThan(1);
        }
    }

    @Nested
    class RemoverAvaliacao {
        @Test
        void devePermitirRemoverAvaliacaoPorId() {

            var avaliacao = AvaliacaoHelper.gerarAvaliacao();
            var id = AvaliacaoHelper.registrarAvaliacao(avaliacao, avaliacaoRepository).getId();

            avaliacaoRepository.deleteById(id);
            var entityRecebidaOptional = avaliacaoRepository.findById(id);

            assertThat(entityRecebidaOptional).isEmpty();
        }
    }

}
