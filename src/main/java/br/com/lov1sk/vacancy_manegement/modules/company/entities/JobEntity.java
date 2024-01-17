package br.com.lov1sk.vacancy_manegement.modules.company.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "jobs") // Essa entidade vira uma tabela dentro do JPA
@Data // Cria Getters e Setters para todos os atributos
@Builder // Disponibiliza um metodo que podemos usar para criar uma instancia de Job passando quantos atributos quisermos
@AllArgsConstructor // Obrigatorio quando usamos o @Builder
@NoArgsConstructor // Obrigatorio quando usamos o @Builder
public class JobEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  // Exemplo que o swagger usa para mostrar na documentação
  @Schema(example = "Vaga destinada a desenvolvedores React & NextJs")
  private String description;
  // Exemplo que o swagger usa para mostrar na documentação
  @Schema(example = "Junior")
  private String level;
  // Exemplo que o swagger usa para mostrar na documentação
  @Schema(example = "VR,VA, Auxilio Transporte, Convênio Medico, Gym Pass e Day-Off.")
  private String benefits;


  /**
   * Relacionamento entre jobs <-> Companies
   * 
   *  Um job pode apenas ter uma unica company
   *  Uma company pode ter diversos Jobs
   * 
   * Ao Criar relacionamentos pensar sempre da classe que estamos criando
   * para a outra classe criada
   * 
   * Ficou basicamente assim, muitos jobs para 1 company
   */
  @ManyToOne()
  @JoinColumn(name = "company_id", insertable = false, updatable = false)
  private CompanyEntity company; // Esse atributo simboliza o relacionamento

  @Column(name = "company_id")
  private UUID companyId; // Esse atrituto salva o id fisicamente

  /**
   * Esse campo sera renomeado e tera valor gerado automaticamente
   * quando esse registro for criado
   */
  @Column(name = "created_at")
  @CreationTimestamp
  private LocalDateTime createdAt;
}
