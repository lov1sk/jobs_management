package br.com.lov1sk.vacancy_manegement.modules.company.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lov1sk.vacancy_manegement.modules.company.entities.JobEntity;

public interface JobRepository extends JpaRepository<JobEntity, UUID> {
  
  /**
   * Busca por jobs que tenham as descrições iguais ao filtro passado como parametro
   * 
   * Para o JPA, a palavra Containg = Query SQL "Like"
   * a palavra IgnoreCase ignora a diferenciação entre letras
   * maiusculas e minusculas, retornando tudo.
   */
  List <JobEntity> findByDescriptionContainingIgnoreCase(String filter);
}
