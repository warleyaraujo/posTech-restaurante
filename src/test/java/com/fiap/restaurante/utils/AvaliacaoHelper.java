package com.fiap.restaurante.utils;

import com.fiap.restaurante.domain.Avaliacao;
import com.fiap.restaurante.repository.AvaliacaoRepository;

public class AvaliacaoHelper {

    public static Avaliacao gerarAvaliacao() {

        var entity = Avaliacao.builder()
                .nota("5")
                .comentario("muito bom")
                .build();
        return entity;
    }

    public static Avaliacao registrarAvaliacao(Avaliacao avaliacao, AvaliacaoRepository avaliacaoRepository) {
        return avaliacaoRepository.save(avaliacao);
    }
}
