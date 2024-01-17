package br.com.lov1sk.vacancy_manegement.modules.candidate.useCases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lov1sk.vacancy_manegement.modules.candidate.dto.ListJobsByFilterRequestDTO;
import br.com.lov1sk.vacancy_manegement.modules.company.entities.JobEntity;
import br.com.lov1sk.vacancy_manegement.modules.company.repositories.JobRepository;

@Service
public class ListJobsByFilterUseCase {
  @Autowired
  private JobRepository jobRepository;

  public List<JobEntity> execute(ListJobsByFilterRequestDTO data){
    return this.jobRepository.findByDescriptionContainingIgnoreCase(data.filter()); 
  }
}
