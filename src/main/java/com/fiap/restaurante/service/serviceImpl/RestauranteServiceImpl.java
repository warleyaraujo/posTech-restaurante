package com.fiap.restaurante.service.serviceImpl;

import com.fiap.restaurante.domain.Mesa;
import com.fiap.restaurante.domain.Restaurante;
import com.fiap.restaurante.domain.dto.EnderecoDto;
import com.fiap.restaurante.domain.dto.RestauranteDto;
import com.fiap.restaurante.domain.embedded.Endereco;
import com.fiap.restaurante.domain.exceptions.RestauranteNotFoundException;
import com.fiap.restaurante.repository.MesaRepository;
import com.fiap.restaurante.repository.RestauranteRepository;
import com.fiap.restaurante.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RestauranteServiceImpl implements RestauranteService {

    private final RestauranteRepository restauranteRepository;
    private final MesaRepository mesaRepository;

    @Autowired
    public RestauranteServiceImpl (RestauranteRepository restauranteRepository, MesaRepository mesaRepository) {
        this.restauranteRepository = restauranteRepository;
        this.mesaRepository = mesaRepository;
    }

    @Override
    public Restaurante cadastrarRestaurante(RestauranteDto restauranteDto) {
        Restaurante novoRestaurante = new Restaurante(restauranteDto);
        return restauranteRepository.save(novoRestaurante);
    }

    @Override
    public List<Restaurante> listar() {
        return this.restauranteRepository.findAll();
    }

    @Override
    public Restaurante cadastraMesas(UUID id, List<Integer> lugares) {
        Restaurante restaurante = restauranteRepository.findById(id).orElseThrow(RestauranteNotFoundException::new);
        for (Integer lugar: lugares) {
            Mesa mesa = new Mesa();
            mesa.setIdRestaurante(id);
            mesa.setLugares(lugar);
            mesaRepository.save(mesa);
            restaurante.getMesas().add(mesa);
        }
        restauranteRepository.save(restaurante);
        return restaurante;
    }

    @Override
    public Restaurante atualizarRestaurante(UUID id, RestauranteDto restauranteDto) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(RestauranteNotFoundException::new);

        restaurante.setRazaoSocial(restauranteDto.razaoSocial());
        restaurante.setNomeFantasia(restauranteDto.nomeFantasia());
        restaurante.setTipoCozinha(restauranteDto.tipoCozinha());
        restaurante.setCapacidade(restauranteDto.capacidade());

        Optional.ofNullable(restauranteDto.endereco())
                .ifPresent(e -> updateEndereco(restaurante.getEndereco(), e));

        return restauranteRepository.save(restaurante);
    }

    @Override
    public void deletarRestaurante(UUID id) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(RestauranteNotFoundException::new);

        restauranteRepository.delete(restaurante);
    }

    private void updateEndereco(Endereco endereco, EnderecoDto novoEndereco) {
        endereco.setLogradouro(novoEndereco.logradouro());
        endereco.setNumero(novoEndereco.numero());
        endereco.setComplemento(novoEndereco.complemento());
        endereco.setBairro(novoEndereco.bairro());
        endereco.setCidade(novoEndereco.cidade());
        endereco.setUf(novoEndereco.uf());
        endereco.setCep(novoEndereco.cep());
    }
}
