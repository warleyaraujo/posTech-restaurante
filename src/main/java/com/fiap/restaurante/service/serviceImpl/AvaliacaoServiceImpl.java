package com.fiap.restaurante.service.serviceImpl;

import com.fiap.restaurante.domain.Avaliacao;
import com.fiap.restaurante.domain.dto.AvaliacaoDto;
import com.fiap.restaurante.domain.exceptions.AvaliacaoNotFoundException;
import com.fiap.restaurante.repository.AvaliacaoRepository;
import com.fiap.restaurante.service.AvaliacaoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AvaliacaoServiceImpl implements AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;

    @Autowired
    public AvaliacaoServiceImpl(AvaliacaoRepository avaliacaoRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
    }

    @Override
    public AvaliacaoDto cadastrar(AvaliacaoDto avaliacaoDto) {
        Avaliacao novaAvaliacao = new Avaliacao(avaliacaoDto);
        avaliacaoRepository.save(novaAvaliacao);
        return avaliacaoDto(novaAvaliacao);
    }

    @Override
    public Page<AvaliacaoDto> listarAvaliacoes(Pageable page) {
        Page<Avaliacao> avaliacoes = avaliacaoRepository.findAll(page);
        return avaliacoes.map(this::avaliacaoDto);
    }

    @Override
    public AvaliacaoDto buscarPorId(UUID id) {
        Avaliacao avaliacao;
        avaliacao = avaliacaoRepository.findById(id)
                .orElseThrow(() -> new AvaliacaoNotFoundException("Avaliacao nao encontrada com o ID: " + id));
        return avaliacaoDto(avaliacao);
    }


    @Override
    public AvaliacaoDto editarAvaliacao(UUID id, AvaliacaoDto avaliacaoDto) {
        Avaliacao avaliacaoExistente = avaliacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Avaliacao nao encontrada com o ID: " + id));

        Avaliacao avaliacaoAtualizada = new Avaliacao(avaliacaoDto);
        avaliacaoAtualizada.setId(avaliacaoExistente.getId());

        avaliacaoRepository.save(avaliacaoAtualizada);

        return avaliacaoDto(avaliacaoAtualizada);
    }

    public boolean deletarAvaliacao(UUID id) {
        var avaliacaoDto = buscarPorId(id);
        Avaliacao avaliacao = new Avaliacao(avaliacaoDto);
        avaliacaoRepository.delete(avaliacao);
        return true;
    }

    @Override
    public AvaliacaoDto avaliacaoDto(Avaliacao avaliacao) {
        return new AvaliacaoDto(
                avaliacao.getId(),
                avaliacao.getNota(),
                avaliacao.getComentario()
        );
    }

}
