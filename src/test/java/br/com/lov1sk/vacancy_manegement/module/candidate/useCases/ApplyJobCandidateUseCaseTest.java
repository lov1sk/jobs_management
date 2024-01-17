package br.com.lov1sk.vacancy_manegement.module.candidate.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import br.com.lov1sk.vacancy_manegement.exceptions.ResourceNotFoundException;
import br.com.lov1sk.vacancy_manegement.modules.candidate.entity.ApplyJobEntity;
import br.com.lov1sk.vacancy_manegement.modules.candidate.entity.CandidateEntity;
import br.com.lov1sk.vacancy_manegement.modules.candidate.repositories.ApplyJobRepository;
import br.com.lov1sk.vacancy_manegement.modules.candidate.repositories.CandidateRepository;
import br.com.lov1sk.vacancy_manegement.modules.candidate.useCases.ApplyJobUseCase;
import br.com.lov1sk.vacancy_manegement.modules.company.entities.JobEntity;
import br.com.lov1sk.vacancy_manegement.modules.company.repositories.JobRepository;


@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {

  // Indica para o mockito que essa dependencia é um serviço e precisa ser injetado
  @InjectMocks
  private ApplyJobUseCase applyJobUseCase;

  // Indica para o mockito que essa dependencia é uma dependencia do serviço injetado pelo @InjectMocks
  @Mock
  private CandidateRepository candidateRepository;

  // Indica para o mockito que essa dependencia é uma dependencia do serviço injetado pelo @InjectMocks
  @Mock
  private JobRepository jobRepository;

  // Indica para o mockito que essa dependencia é uma dependencia do serviço injetado pelo @InjectMocks
  @Mock
  private ApplyJobRepository applyJobRepository;



  // Indica para o JUnit que o metodo é um teste
  @Test 
  // Como que o teste aparece descrito no terminal
  @DisplayName(" Should not be able to apply to a job if candidate id was wrong")
  public void should_not_be_able_to_apply_to_a_job_if_candidate_id_was_wrong(){
    try {
      this.applyJobUseCase.execute(null,null);
    } catch (Exception e) {
      // TODO: handle exception
      assertInstanceOf(ResourceNotFoundException.class, e);
    }
  }



  /**
   * Teste para verificar se quando não achar um 
   * job dentro do banco de dados, ele retorne um erro
   * e não cause inconsistencia posterior na aplicação
   */

  // Indica para o JUnit que o metodo é um teste
  @Test 
  // Como que o teste aparece descrito no terminal
  @DisplayName(" Should not be able to apply to a job if job id was wrong")
  public void should_not_be_able_to_apply_to_a_job_if_job_id_was_wrong(){

    // Crio um id e atribuo aoo um candidato ficticio
    var id = UUID.randomUUID();
    var fakeCandidate = new CandidateEntity();
    fakeCandidate.setId(id);

    // Digo que a chamada ao metodo findById do repositorio ira retornar um candidado ficticio
    when(candidateRepository.findById(id)).thenReturn(Optional.of(fakeCandidate));

    /**
     * Espera-se aqui que dê erro quando for fazer a validação
     * se aquele job realmente existe e que pode ser submetido
     */
    try {
      this.applyJobUseCase.execute(id,null);
    } catch (Exception e) {
      // TODO: handle exception
      assertInstanceOf(ResourceNotFoundException.class, e);
    }
  }

  /**
   * Meu teste 
   *
   * não ok
   */
  // Indica para o JUnit que o metodo é um teste
  @Test 
  @DisplayName(" Should be able to apply to a job ")
  public void should_be_able_to_apply_to_a_job(){

    /**
     * Crio IDs para as entidades fake que vamos usar 
     * nos mocks.
     */
    var fakeCandidateId = UUID.randomUUID();
    var fakeJobId = UUID.randomUUID();
    var fakeApplyJobId = UUID.randomUUID();

    /**
     * Entidade criada para Mock, pois como 
     * é um teste unitario, não existe contato com o
     * banco de dados. Portanto dizemos que quando o
     * caso de uso busca no repositorio essa entidade pelo id
     * dizemos que ele retornou essa entidade fake aqui
     */
    var fakeJobApplication = ApplyJobEntity.builder()
                                           .candidateId(fakeCandidateId)
                                           .jobId(fakeJobId)
                                           .build();

    /**
     * Como não posso atribuir o id, pois o JPA quem gera
     * criei esse objeto apenas para simbolizar o retorno
     * do metodo save
     */
    var fakeJobApplicationCreated = ApplyJobEntity.builder()
                                                  .id(fakeApplyJobId)
                                                  .build();

    /**
     * Novamente, como é teste de unidade, criamos mocks para o retorno
     * do repositorio, dizendo que ele retornou um Entidades ficticias
     */

    // Quando o repositorio para busca de um candidato com esse id, esperamos que ele retorne uma instancia de candidate entity
    when(candidateRepository.findById(fakeCandidateId)).thenReturn(Optional.of(new CandidateEntity()));
    // Quando o repositorio para busca de um job com esse id, esperamos que ele retorne uma instancia de job entity
    when(jobRepository.findById(fakeJobId)).thenReturn(Optional.of(new JobEntity()));
    when(applyJobRepository.save(fakeJobApplication)).thenReturn(fakeJobApplicationCreated);

    /**
     * Execução da funcionalidade de aplicar a uma nava
     */
    var result = this.applyJobUseCase.execute(fakeCandidateId, fakeJobId);
    
    assertNotNull(result.getId());
    

  }

}
