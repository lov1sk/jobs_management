package br.com.lov1sk.vacancy_manegement.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lov1sk.vacancy_manegement.exceptions.ResourceNotFoundException;
import br.com.lov1sk.vacancy_manegement.modules.candidate.entity.ApplyJobEntity;
import br.com.lov1sk.vacancy_manegement.modules.candidate.repositories.ApplyJobRepository;
import br.com.lov1sk.vacancy_manegement.modules.candidate.repositories.CandidateRepository;
import br.com.lov1sk.vacancy_manegement.modules.company.repositories.JobRepository;

@Service
public class ApplyJobUseCase {
  
  @Autowired
  private JobRepository jobRepository;
  
  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private ApplyJobRepository applyJobRepository;

  public ApplyJobEntity execute(UUID candidateId, UUID jobId){

    this.candidateRepository.findById(candidateId).orElseThrow(()->{
      throw new ResourceNotFoundException("candidate");
    });

    this.jobRepository.findById(jobId).orElseThrow(()-> {
      throw new ResourceNotFoundException("job");
    });

    var jobApplication = ApplyJobEntity.builder()
                                       .jobId(jobId)
                                       .candidateId(candidateId)
                                       .build();
    
    var jobApplicationCreatedOnDatabase = this.applyJobRepository.save(jobApplication);
    return jobApplicationCreatedOnDatabase;
    

  }

}
