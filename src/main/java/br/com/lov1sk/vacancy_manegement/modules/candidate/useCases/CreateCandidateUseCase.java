package br.com.lov1sk.vacancy_manegement.modules.candidate.useCases;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.lov1sk.vacancy_manegement.exceptions.UserAlreadyExistsException;
import br.com.lov1sk.vacancy_manegement.modules.candidate.entity.CandidateEntity;
import br.com.lov1sk.vacancy_manegement.modules.candidate.repositories.CandidateRepository;

@Service
public class CreateCandidateUseCase {
  @Autowired //Aqui estamos dizendo que queremos que o spring gerencie e injete essa dependencia 
  private CandidateRepository candidateRepository; // Aqui estamos injetando uma dependencia

  @Autowired //Aqui estamos dizendo que queremos que o spring gerencie e injete essa dependencia 
  private PasswordEncoder passwordEncoder; // Injeção da dependencia de encodificação de senhas

  public CandidateEntity execute(CandidateEntity candidateEntity) {
     /**
      * Valida se um usuario com esse username ja existe
      */
    this.candidateRepository
    .findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
    .ifPresent((user) ->{
      throw new UserAlreadyExistsException("candidate");
    });

    /**
     * Processo de encriptografar a senha
     */
    var hashedPassword = passwordEncoder.encode(candidateEntity.getPassword());
    candidateEntity.setPassword(hashedPassword);

    /**
     * Processo de salvar no banco de dados o candidato
     */
    var candidateSavedOnDatabase = this.candidateRepository.save(candidateEntity);
    return candidateSavedOnDatabase;
  }
}
