package br.com.lov1sk.vacancy_manegement.modules.candidate.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Cria Getters e Setters para todos os atributos
@Builder // Disponibiliza um metodo que podemos usar para criar uma instancia de Job passando quantos atributos quisermos
@AllArgsConstructor // Obrigatorio quando usamos o @Builder
@NoArgsConstructor // Obrigatorio quando usamos o @Builder
/**
 * Quando lidamos com o retorno de dados, onde precisamos "setar" informações
 * usamos classes normais com o lombok
 */
public class ProfileCandidateResponseDTO {
  private UUID id;
  // Implementação de um exemplo para o swagger, sobre como alimentar este campo
  @Schema(example = "john_doe", requiredMode = RequiredMode.REQUIRED)
  private String username;
  // Implementação de um exemplo para o swagger, sobre como alimentar este campo
  @Schema(example = "Sou uma pessoa apaixonada por codigos e por programação", requiredMode = RequiredMode.REQUIRED)
  private String description;
  // Implementação de um exemplo para o swagger, sobre como alimentar este campo
  @Schema(example = "johndoe@gmail.com", requiredMode = RequiredMode.REQUIRED)
  private String email;
  // Implementação de um exemplo para o swagger, sobre como alimentar este campo
  @Schema(example = "John Doe", requiredMode = RequiredMode.REQUIRED)
  private String name;
}
