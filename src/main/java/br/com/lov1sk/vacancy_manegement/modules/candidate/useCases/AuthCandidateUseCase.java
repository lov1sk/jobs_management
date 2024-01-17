package br.com.lov1sk.vacancy_manegement.modules.candidate.useCases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.lov1sk.vacancy_manegement.exceptions.AuthenticationException;
import br.com.lov1sk.vacancy_manegement.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.lov1sk.vacancy_manegement.modules.candidate.dto.AuthCandidateResponseDTO;
import br.com.lov1sk.vacancy_manegement.modules.candidate.repositories.CandidateRepository;

@Service
public class AuthCandidateUseCase {

  @Value("${security.token.secret}")
  private String jwtSecret;

  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO data){

    /**
     * Verificação se existe esse candidato salvo para poder autenticar-se
     * na aplicação
     */
    var candidate = this.candidateRepository
          .findByUsername(data.username())
          .orElseThrow(() ->{ 
              throw new AuthenticationException();
          });


    /**
     * Descriptografia da senha e verificação a senha inserida
     * é a certa
     */
    var isSamePassword = passwordEncoder.matches(data.password(), candidate.getPassword());
    if(!isSamePassword){
      throw new AuthenticationException();
    }

    /**
     * Criação do token JWT
     */
    Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
    var tokenExpirationTime = Instant.now().plus(Duration.ofMinutes(10));
    var token = JWT.create() // Cria Token
                   .withIssuer("gestao_vagas") //Informação adicional
                   .withSubject(candidate.getId().toString()) // Insere no payload a informação de id do candidato
                   .withClaim("roles", Arrays.asList("CANDIDATE")) // Cria uma permissão RBAC
                   .withExpiresAt(tokenExpirationTime)// Adiciona 10 minutos como tempo de expiração
                   .sign(algorithm); // Gera o token com base no algoritmo criado acima H256

    
    /**
     * Retorno para o controller
     */
    return AuthCandidateResponseDTO.builder()
                                   .access_token(token)
                                   .expires_in(tokenExpirationTime.toEpochMilli())
                                   .build();
  }
}
