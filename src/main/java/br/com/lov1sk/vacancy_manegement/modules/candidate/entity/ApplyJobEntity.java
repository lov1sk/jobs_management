package br.com.lov1sk.vacancy_manegement.modules.candidate.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import br.com.lov1sk.vacancy_manegement.modules.company.entities.JobEntity;
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

/**
 * Essa Entidade representa a tabela pivo entre os relacionamentos
 * 
 * JOB x Candidato (Relacionamento N x N)
 * 
 * 1 candidato pode ter se aplicado a muitas vagas
 * 1 vaga pode ter muitos candidatos que aplicaram a ela
 * 
 * Portanto, é criado uma tabela pivo no banco de dados para armazenar
 * as informações dos IDs referente aos jobs e aos candiatos
 */
@Entity(name = "apply_jobs")
@Data // Cria Getters e Setters para todos os atributos
@Builder // Disponibiliza um metodo que podemos usar para criar uma instancia de Job passando quantos atributos quisermos
@AllArgsConstructor // Obrigatorio quando usamos o @Builder
@NoArgsConstructor // Obrigatorio quando usamos o @Builder
public class ApplyJobEntity {

  /**
   * Chave primaria
   */
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  /**
   * Referenciação para o JPA que existe um relacionamento
   * e que os campos identificadores são candidate_id e job_id
   */
  @ManyToOne()
  @JoinColumn(name = "candidate_id", insertable = false, updatable = false)
  private CandidateEntity candidateEntity;
  @ManyToOne()
  @JoinColumn(name = "job_id", insertable = false, updatable = false)
  private JobEntity jobEntity;


  /**
   * Armazenando os valores do relacionamento presente
   * entre Job x Candidate
   */
  //Renomeando o nome da coluna
  @Column(name = "candidate_id")
  // Exemplo para a documentação do Swagger
  @Schema(example = "12bibeygb8yg82s2")
  private UUID candidateId;
  //Renomeando o nome da coluna
  @Column(name = "job_id")
  // Exemplo para a documentação do Swagger
  @Schema(example = "12bibeygb8yg82s2")
  private UUID jobId;

   /**
   * Esse campo sera renomeado e tera valor gerado automaticamente
   * quando esse registro for criado
   */
  @Column(name = "created_at")
  @CreationTimestamp
  private LocalDateTime createdAt;
  
}
