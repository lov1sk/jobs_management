package br.com.lov1sk.vacancy_manegement.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lov1sk.vacancy_manegement.exceptions.ResourceNotFoundException;
import br.com.lov1sk.vacancy_manegement.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.lov1sk.vacancy_manegement.modules.candidate.repositories.CandidateRepository;

@Service
public class ProfileCandidateUseCase {

  @Autowired
  private CandidateRepository candidateRepository;

  public ProfileCandidateResponseDTO execute(UUID id) {
    /**
     * Busca o candidato pelo seu Id
     */
    var candidate = this.candidateRepository
        .findById(id)
        .orElseThrow(() -> {
          throw new ResourceNotFoundException("candidate");
        });

    /**
     * Retona o candidato com as informações de perfil do mesmo
     */
    return ProfileCandidateResponseDTO.builder()
                                      .id(candidate.getId())
                                      .name(candidate.getName())
                                      .username(candidate.getUsername())
                                      .description(candidate.getDescription())
                                      .email(candidate.getEmail())
                                      .build();
                                      
                                      
  }

}
