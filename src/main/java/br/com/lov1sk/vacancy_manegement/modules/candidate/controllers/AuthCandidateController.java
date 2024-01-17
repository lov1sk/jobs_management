package br.com.lov1sk.vacancy_manegement.modules.candidate.controllers;

import org.springframework.web.bind.annotation.RestController;

import br.com.lov1sk.vacancy_manegement.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.lov1sk.vacancy_manegement.modules.candidate.dto.AuthCandidateResponseDTO;
import br.com.lov1sk.vacancy_manegement.modules.candidate.useCases.AuthCandidateUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/candidate")
public class AuthCandidateController {
  @Autowired
  private AuthCandidateUseCase authCandidateUseCase;

  @PostMapping("/auth")
   // Titulo e subtitulo de um conjunto de endpoints
  @Tag(name="Candidate", description = "Metodos relacionados a ações do candidato na plataforma!") 
  // Descrição de cada endpoint
  @Operation(summary = "Login dos Usuarios", description = "Endpoint destinado ao candidato realizar login na plataforma") 
  // Possiveis retornos da API
  @ApiResponses({
      // Resposta de sucesso
      @ApiResponse(responseCode = "200", content = {
          @Content(array = @ArraySchema(schema = @Schema(implementation = AuthCandidateResponseDTO.class)))
      }),
      @ApiResponse(responseCode = "401", description = "Invalid credentials")
  })
  public ResponseEntity<Object> auth(@RequestBody AuthCandidateRequestDTO candidate) {
      try {
        // Resultado da execução do use case
        var result = authCandidateUseCase.execute(candidate);
        
        // Retorno para o front
        return ResponseEntity.status(HttpStatus.OK).body(result);
      } catch (Exception e) {
        /**
         * Estrutura de retorno de erro
         */
        Map<String, String> errorMessage = new HashMap<String,String>();
        errorMessage.put("message", e.getMessage());

        // Retorno para o front
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
      }
      
  }
  
}
