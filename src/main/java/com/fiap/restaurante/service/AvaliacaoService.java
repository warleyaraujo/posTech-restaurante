package com.fiap.restaurante.service;

import com.fiap.restaurante.domain.Avaliacao;
import com.fiap.restaurante.domain.dto.AvaliacaoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AvaliacaoService {
    AvaliacaoDto cadastrar(AvaliacaoDto avaliacaoDto);

    Page<AvaliacaoDto> listarAvaliacoes(Pageable page);

    AvaliacaoDto buscarPorId(UUID id);

    AvaliacaoDto editarAvaliacao(UUID id, AvaliacaoDto avaliacaoDto);
    boolean deletarAvaliacao(UUID id);
    public AvaliacaoDto avaliacaoDto(Avaliacao avaliacao);

}
