
package com.fiap.restaurante.domain.dto;

import com.fiap.restaurante.domain.embedded.Endereco;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
public class ClienteRequest {
  private UUID id;
  private String nome;
  private String email;
  private String fone;
  private Endereco endereco;
}
