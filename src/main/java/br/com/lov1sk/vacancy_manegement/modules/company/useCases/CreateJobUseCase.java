package br.com.lov1sk.vacancy_manegement.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lov1sk.vacancy_manegement.modules.company.entities.JobEntity;
import br.com.lov1sk.vacancy_manegement.modules.company.repositories.JobRepository;

@Service
public class CreateJobUseCase {
  @Autowired
  private JobRepository jobRepository;

  public JobEntity execute(JobEntity jobEntity) {
    var jobCreatedOnDatabase = this.jobRepository.save(jobEntity);
    return jobCreatedOnDatabase;
  }
}
