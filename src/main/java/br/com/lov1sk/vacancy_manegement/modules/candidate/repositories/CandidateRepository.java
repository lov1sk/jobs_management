package br.com.lov1sk.vacancy_manegement.modules.candidate.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lov1sk.vacancy_manegement.modules.candidate.entity.CandidateEntity;

// A interface JPA recebe como generic a entidade a qual ela se refere e o tipo do ID (primary key) <Entity, Primary Key Type>
public interface CandidateRepository extends JpaRepository<CandidateEntity, UUID> {
  
  /**
   * Metodos proprios de repositorio aqui
   * 
   * Lembrando que o JPA já disponibiliza varios metodos de FindById,Create,Delete e todos
   * os metodos de CRUD. Porem nada impede de inserirmos aqui metodos especificos para 
   * lidar com os dados persistidos
   */
  
  // Metodo que busca pelo username e retorna um Candidato caso exista ou null caso nao exista
  Optional<CandidateEntity> findByUsername(String username);
  
  // Metodo que busca pelo email e retorna um Candidato caso exista ou null caso nao exista
  Optional<CandidateEntity> findByEmail(String email);
  
  // Metodo que busca pelo email ou username, se algum dos dois retornar algo é porque foi achado na base de dados
  Optional<CandidateEntity> findByUsernameOrEmail(String username, String email);

  
}
