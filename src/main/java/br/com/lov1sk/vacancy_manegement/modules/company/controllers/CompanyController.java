package br.com.lov1sk.vacancy_manegement.modules.company.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.lov1sk.vacancy_manegement.modules.company.entities.CompanyEntity;
import br.com.lov1sk.vacancy_manegement.modules.company.entities.JobEntity;
import br.com.lov1sk.vacancy_manegement.modules.company.useCases.CreateCompanyUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestMapping;


// A classe é um controller REST
@RestController
// O Endpoint prefixado começa com "/company"
@RequestMapping("company")
public class CompanyController {
  /**
   * Injeção da dependencia do caso de uso para dentro do
   * controller
   */
  @Autowired
  public CreateCompanyUseCase createCompanyUseCase;
 

  // Metodo do controller
  @PostMapping("/")
  // Titulo e subtitulo de um conjunto de endpoints
  @Tag(name="Company", description = "Metodos relacionados a ações de empresas dentro da plataforma!") 
  // Descrição de cada endpoint
  @Operation(summary = "Cadastro de uma nova empresa", description = "Endpoint destinado a cadastro de uma nova empresa dentro da plataforma") 
  // Possiveis retornos da API
  @ApiResponses({
      // Resposta de sucesso
      @ApiResponse(responseCode = "201", content = {
          @Content(array = @ArraySchema(schema = @Schema(implementation = CompanyEntity.class)))
      }),
      // Responsa de exceção
      @ApiResponse(responseCode = "409", description = "Company with this username/email already exists")
  })
  public ResponseEntity<Object> create(
    @Valid /*Esse decorator basicamente serve para validar o proximo dado (neste caso o request body)*/
    @RequestBody CompanyEntity company){

    /**
     * Tenta criar um novo usuario, se informações como:
     * email e username, forem duplicadas, ele retorna uma 
     * exceção
     */
     try {
       // Execução do caso de uso
       var result = this.createCompanyUseCase.execute(company);    
       // Retorno pro front
       return ResponseEntity.status(HttpStatus.CREATED).body(result);
     } catch (Exception e) {
       /**
        * Estrutura de retorno de erro
        */
      Map<String, String> errorMessage = new HashMap<String,String>();
      errorMessage.put("message", e.getMessage());
        // Retorno pro front
      return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }

  }}
