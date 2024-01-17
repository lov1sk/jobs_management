package br.com.lov1sk.vacancy_manegement.modules.company.controllers;

import org.springframework.web.bind.annotation.RestController;

import br.com.lov1sk.vacancy_manegement.modules.company.dto.CreateJobDTO;
import br.com.lov1sk.vacancy_manegement.modules.company.entities.CompanyEntity;
import br.com.lov1sk.vacancy_manegement.modules.company.entities.JobEntity;
import br.com.lov1sk.vacancy_manegement.modules.company.useCases.CreateJobUseCase;
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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/company/job")
public class JobController {
  @Autowired
  private CreateJobUseCase createJobUseCase;

  // Metodo do controller
  @PostMapping("/")  
  // Essa rota requer autenticação definida como jwt_auth
  @SecurityRequirement(name = "jwt_auth")
  // Titulo e subtitulo de um conjunto de endpoints
  @Tag(name="Company", description = "Metodos relacionados a ações de empresas dentro da plataforma!") 
  // Descrição de cada endpoint
  @Operation(summary = "Cadastro de uma nova vaga", description = "Endpoint destinado a cadastro de uma nova vaga por parte da empresa") 
  // Possiveis retornos da API
  @ApiResponses({
      // Resposta de sucesso
      @ApiResponse(responseCode = "201", content = {
          @Content(array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)))
      })
  })
  public ResponseEntity<Object> create(@Valid @RequestBody CreateJobDTO job, HttpServletRequest request){
    /**
     * Recuperando o atributo company id, identificador
     * de quem esta criando essa vaga, o qual foi salvo
     * como subject do token JWT
     * 
     * Após recuperação do identificador da company, setamos 
     * ele como identificador de quem esta criando esse job
     */
    var companyId = request.getAttribute("companyId").toString();
    var jobEntity = JobEntity
                      .builder() // Utiliza o builder do lombok, não precisamos usar o new para criar um novo objeto
                      .benefits(job.getBenefits()) // Passa os parametros recebidos no front e evidenciados no DTO
                      .description(job.getDescription()) // Passa os parametros recebidos no front e evidenciados no DTO
                      .level(job.getLevel()) // Passa os parametros recebidos no front e evidenciados no DTO
                      .companyId(UUID.fromString(companyId)) // Utiliza o id da requisição
                      .build(); //Termina de criar uma nova instancia 

 try {
  
   var result = this.createJobUseCase.execute(jobEntity);
   return ResponseEntity.status(HttpStatus.CREATED).body(result);
 } catch (Exception e) {
  // TODO: handle exception
  /**
        * Estrutura de retorno de erro
        */
        System.out.println(e.getMessage());
      Map<String, String> errorMessage = new HashMap<String,String>();
      errorMessage.put("message", e.getMessage());
        // Retorno pro front
      return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
 }

  }
}
