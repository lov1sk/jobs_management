package br.com.lov1sk.vacancy_manegement.modules.company.useCases;
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
import br.com.lov1sk.vacancy_manegement.modules.company.dto.AuthCompanyRequestDTO;
import br.com.lov1sk.vacancy_manegement.modules.company.dto.AuthCompanyResponseDTO;
import br.com.lov1sk.vacancy_manegement.modules.company.repositories.CompanyRepository;

@Service
public class AuthCompanyUseCase {

  @Value("${security.token.secret}")
  private String jwtSecret;

  @Autowired
  private CompanyRepository companyRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;


  public AuthCompanyResponseDTO execute(AuthCompanyRequestDTO data) throws Exception{
    /**
     * Verifica se ja existe uma company com esses dados,
     * se sim retorna uma exceção
     */
    var company = this.companyRepository
    .findByUsername(data.username())
    .orElseThrow(() ->{ 
      throw new AuthenticationException();
    });

     /**
     * Verifica se a senha foi informada corretamente,
     * se não retorna uma exceção
     */
    var isSamePassword = passwordEncoder.matches(data.password(),company.getPassword());
    if(!isSamePassword){
      throw new AuthenticationException();
    }

    /**
     * Cria um novo token JWT
     */
    System.out.println(jwtSecret + "jwt auth company use case");
    Algorithm jwtAlgorithm = Algorithm.HMAC256(jwtSecret);
    var tokenExpirationTime = Instant.now().plus(Duration.ofMinutes(10));
    var token = JWT
                .create() // Cria Token
                .withIssuer("gestao_vagas") //Informação adicional
                .withClaim("roles", Arrays.asList("COMPANY")) // Cria uma permissão RBAC
                .withExpiresAt(tokenExpirationTime) // Adiciona 10 minutos como tempo de expiração
                .withSubject(company.getId().toString()) // Cria com o sub: company.id (Identificador da empresa que quer se logar)
                .sign(jwtAlgorithm); // Utiliza do algoritmo criado acima H256
    
    /**
     * Retorna para o controller um objeto contento o token e o tempo
     * de expiração
     */
    return AuthCompanyResponseDTO.builder()
                                 .access_token(token)
                                 .expires_in(tokenExpirationTime.toEpochMilli())
                                 .build();
  }
}
