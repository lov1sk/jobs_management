package br.com.lov1sk.vacancy_manegement.modules.company.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity(name = "companies")
@Data
public class CompanyEntity {
  
  // Constraint Primary Key
  @Id
  // O Valor desse campo é um UUID gerado automatico
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  // Exemplo que o swagger usa para mostrar na documentação
  @Schema(example = "John Doe")
  private String name;

  // Validação com base que o username não pode receber valores com espaços em branco
  @Pattern(regexp = "\\S+", message = "username must be without spaces")
  // Validação que esse campo só recebe informações do padrão de email
  @Schema(example = "john_doe")
  private String username;

  // Validação que esse campo só recebe informações do padrão de email
  @Email(message = "Please provide a valid email address")
  // Validação que esse campo não é nulo
  @NotEmpty(message = "This field is required")
  // Exemplo que o swagger usa para mostrar na documentação
  @Schema(example = "johndoe@gmail.com")
  private String email;

  // Validação que esse campo deve receber valores com pelo menos 6 caracteres
  @Length(min = 6, message = "Your password must be at least 6 characters")
  // Exemplo que o swagger usa para mostrar na documentação
  @Schema(example = "123456abc")
  private String password;

  // Exemplo que o swagger usa para mostrar na documentação
  @Schema(example = "https://www.example.com.br")
  private String website;

  // Exemplo que o swagger usa para mostrar na documentação
  @Schema(example = "Empresa focada em desenvolvimento de software ...")
  private String description;
  /**
   * Esse campo sera renomeado e tera valor gerado automaticamente
   * quando esse registro for criado
   */
  @Column(name = "created_at")
  @CreationTimestamp
  private LocalDateTime createdAt;
}
