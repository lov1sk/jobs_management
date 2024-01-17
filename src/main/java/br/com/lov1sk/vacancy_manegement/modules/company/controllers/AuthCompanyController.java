package br.com.lov1sk.vacancy_manegement.modules.company.controllers;

import org.springframework.web.bind.annotation.RestController;

import br.com.lov1sk.vacancy_manegement.modules.candidate.dto.AuthCandidateResponseDTO;
import br.com.lov1sk.vacancy_manegement.modules.company.dto.AuthCompanyRequestDTO;
import br.com.lov1sk.vacancy_manegement.modules.company.dto.AuthCompanyResponseDTO;
import br.com.lov1sk.vacancy_manegement.modules.company.useCases.AuthCompanyUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/company")
public class AuthCompanyController {

  @Autowired 
  private AuthCompanyUseCase authCompanyUseCase;

  @PostMapping("/auth")
  @Tag(name="Company", description = "Metodos relacionados a ações de empresas dentro da plataforma!")
  @Operation(summary = "Login da empresa", description = "Endpoint destinado a realizar login de empresa cadastrada dentro da plataforma")
  // Possiveis retornos da API
  @ApiResponses({
      // Resposta de sucesso
      @ApiResponse(responseCode = "200", content = {
          @Content(array = @ArraySchema(schema = @Schema(implementation = AuthCompanyResponseDTO.class)))
      }),
      @ApiResponse(responseCode = "401", description = "Invalid credentials")
  })
  public ResponseEntity<Object> auth(@RequestBody AuthCompanyRequestDTO auth) {
    try {
      var result = authCompanyUseCase.execute(auth);
      return  ResponseEntity.status(HttpStatus.OK).body(result);
      
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }  
    
  }
  
}
