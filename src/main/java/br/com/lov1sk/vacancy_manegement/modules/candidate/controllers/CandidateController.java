package br.com.lov1sk.vacancy_manegement.modules.candidate.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lov1sk.vacancy_manegement.modules.candidate.dto.ListJobsByFilterRequestDTO;
import br.com.lov1sk.vacancy_manegement.modules.candidate.entity.CandidateEntity;
import br.com.lov1sk.vacancy_manegement.modules.candidate.useCases.ApplyJobUseCase;
import br.com.lov1sk.vacancy_manegement.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.lov1sk.vacancy_manegement.modules.candidate.useCases.ListJobsByFilterUseCase;
import br.com.lov1sk.vacancy_manegement.modules.candidate.useCases.ProfileCandidateUseCase;
import br.com.lov1sk.vacancy_manegement.modules.company.entities.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidate")
// Titulo e subtitulo de um conjunto de endpoints
@Tag(name="Candidate", description = "Metodos relacionados a ações do candidato na plataforma!") 
public class CandidateController {

  @Autowired
  private CreateCandidateUseCase createCandidateUseCase;
  @Autowired
  private ProfileCandidateUseCase profileCandidateUseCase;
  @Autowired
  private ListJobsByFilterUseCase listJobsByFilterUseCase;

  @Autowired
  private ApplyJobUseCase applyJobUseCase;
 

  // Metodo do controller
  @PostMapping("/")  
  // Descrição de cada endpoint
  @Operation(summary = "Cadastro de Candidatos", description = "Endpoint destinado a cadastrar novos candidatos na plataforma") 
  // Possiveis retornos da API
  @ApiResponses({
      // Resposta de sucesso
      @ApiResponse(responseCode = "201"),
  })
  public ResponseEntity<Object> create(
    @Valid /* Esse decorator basicamente serve para validar o proximo dado (neste caso o request body) */
    @RequestBody CandidateEntity candidate /* Recuperação das informações presentes no corpo da requisição "request.body" */
    ){
     try {
      /**
       * Usa o caso de uso para tentar se autenticar,
       * ver se as informações no banco batem
       */
      var result = this.createCandidateUseCase.execute(candidate);    
      // Retorno para o front
      return ResponseEntity.status(HttpStatus.CREATED).body(result);
     } catch (Exception e) {
      /**
       * Estrutura de retorno de erro
       */
      Map<String, String> errorMessage = new HashMap<String,String>();
      errorMessage.put("message", e.getMessage());

      // Retorno para o front
      return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
     }

  }

  // Metodo do controller
  @GetMapping("/")
  // Verificar se usuario tem no token JWT a permissão de candidato
  @PreAuthorize("hasRole('ROLE_CANDIDATE')")
  // Essa rota requer autenticação definida como jwt_auth
  @SecurityRequirement(name = "jwt_auth")
  // Descrição de cada endpoint
  @Operation(summary = "Informações do perfil", description = "Endpoint destinado a retornar informações de perfil de um candidato ") 
  // Possiveis retornos da API
  @ApiResponses({
      // Resposta de sucesso
      @ApiResponse(responseCode = "200", content = {
          @Content(array = @ArraySchema(schema = @Schema(implementation = CandidateEntity.class)))
      }),
  })
  public ResponseEntity<Object> profile(HttpServletRequest request) {
    var candidateId = request.getAttribute("candidateId").toString();
      try {
        var result = profileCandidateUseCase.execute(UUID.fromString(candidateId));
        return ResponseEntity.status(HttpStatus.OK).body(result);
      } catch (Exception e) {
        /**
         * Estrutura de retorno de erro
         */
        Map<String, String> errorMessage = new HashMap<String,String>();
        errorMessage.put("message", e.getMessage());

        // Retorno para o front
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
       
      }
  }
 
  // Metodo do controller
  @GetMapping("/jobs")
  // Verificar se usuario tem no token JWT a permissão de candidato
  @PreAuthorize("hasRole('ROLE_CANDIDATE')")
  // Essa rota requer autenticação definida como jwt_auth
  @SecurityRequirement(name = "jwt_auth")
  // Descrição de cada endpoint
  @Operation(summary = "Filtragem de vagas", description = "Endpoint destinado a filtrar vagas por meio de sua descrição ") 
  // Possiveis retornos da API
  @ApiResponses({
      // Resposta de sucesso
      @ApiResponse(responseCode = "200", content = {
          @Content(array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)))
      }),
  })
  public ResponseEntity<Object> listJobs(@RequestParam ListJobsByFilterRequestDTO filter) {
    var result = listJobsByFilterUseCase.execute(filter);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
  
  @PostMapping("/job/apply")  
  // Verificar se usuario tem no token JWT a permissão de candidato
  @PreAuthorize("hasRole('ROLE_CANDIDATE')")
  @Operation(summary = "Inscrição do candidato para uma vaga", description = "Essa função é responsável por realizar a inscrição do candidato em uma vaga.")
  @SecurityRequirement(name = "jwt_auth")
  public ResponseEntity<Object> applyJob(@RequestBody UUID idJob,HttpServletRequest request){

    var idCandidate = request.getAttribute("candidateId").toString();
    System.out.println(idJob);

    try{
          var result = this.applyJobUseCase.execute(UUID.fromString(idCandidate),idJob);
          return ResponseEntity.ok().body(result);
    }catch(Exception e){
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
