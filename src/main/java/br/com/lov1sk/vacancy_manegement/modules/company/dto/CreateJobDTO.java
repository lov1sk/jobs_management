package br.com.lov1sk.vacancy_manegement.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Getters e Setters para todas os atributos
@Builder // Disponibiliza um metodo que podemos usar para criar uma instancia de Job passando quantos atributos quisermos
@AllArgsConstructor // Obrigatorio quando usamos o @Builder
@NoArgsConstructor // Obrigatorio quando usamos o @Builder
public class CreateJobDTO {
  // Implementação de um exemplo para o swagger, sobre como alimentar este campo
  @Schema(example = "VR,VA, Auxilio Transporte, Convênio Medico, Gym Pass e Day-Off.", requiredMode = RequiredMode.REQUIRED)
  private String benefits;
  // Implementação de um exemplo para o swagger, sobre como alimentar este campo
  @Schema(example = "Junior", requiredMode = RequiredMode.REQUIRED)
  private String level;
  // Implementação de um exemplo para o swagger, sobre como alimentar este campo
  @Schema(example = "Vaga para pessoa desenvolvedora júnior", requiredMode = RequiredMode.REQUIRED)
  private String description;
}
