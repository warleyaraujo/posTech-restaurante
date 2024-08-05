package com.fiap.restaurante.utils;

import com.fiap.restaurante.domain.Mesa;
import com.fiap.restaurante.domain.Restaurante;
import com.fiap.restaurante.domain.embedded.Endereco;
import com.fiap.restaurante.repository.RestauranteRepository;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RestauranteHelper {

    public static Restaurante gerarRegistro() {
        var mesa = new Mesa();
        mesa.setIdRestaurante(UUID.fromString("1f2b6507-0443-40c5-b5ac-7e89cbdfee19"));
        mesa.setLugares(123);

        List<Mesa> mesas = new ArrayList<>();
        mesas.add(mesa);

        var endereco = new Endereco();
        endereco.setLogradouro("Endereco Automatizado");
        endereco.setNumero("1010");
        endereco.setUf("SP");
        endereco.setCep("00000-000");
        endereco.setBairro("Bairro Auto");
        endereco.setCidade("Cidade Auto");
        endereco.setComplemento("Comple");

        var entity = Restaurante.builder()
                .razaoSocial("Restaurante Teste")
                .nomeFantasia("RESTAURANTE TESTE")
                .tipoCozinha("Teste")
                .capacidade(0)
                .horaAbertura(LocalTime.parse("09:00:00"))
                .horaEncerramento(LocalTime.parse("19:00:00"))
                .endereco(endereco)
                .mesas(mesas)
                .build();
        return entity;
    }

    public static Restaurante registrarRestaurante(Restaurante restaurante, RestauranteRepository repository) {
        return repository.save(restaurante);
    }
}