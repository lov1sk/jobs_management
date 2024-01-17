package br.com.lov1sk.vacancy_manegement.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class AuthCompanyResponseDTO {
  // Exemplo que o swagger usa para mostrar na documentação
  @Schema(example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
  private String access_token; // Token de acesso para o front
  
  // Exemplo que o swagger usa para mostrar na documentação
  @Schema(example = "1516239022")
  private Long expires_in;
  
}
