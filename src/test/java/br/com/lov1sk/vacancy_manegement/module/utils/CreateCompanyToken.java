package br.com.lov1sk.vacancy_manegement.module.utils;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class CreateCompanyToken {

  public static String createJWT(String id){
    /**
     * Cria um novo token JWT
     */
    String jwtSecret = "gestao_vagas";
    Algorithm jwtAlgorithm = Algorithm.HMAC256(jwtSecret);
    var tokenExpirationTime = Instant.now().plus(Duration.ofMinutes(10));
    var token = JWT
                .create() // Cria Token
                .withIssuer("gestao_vagas") //Informação adicional
                .withClaim("roles", Arrays.asList("COMPANY")) // Cria uma permissão RBAC
                .withExpiresAt(tokenExpirationTime) // Adiciona 10 minutos como tempo de expiração
                .withSubject(id) // Cria com o sub: company.id (Identificador da empresa que quer se logar)
                .sign(jwtAlgorithm); // Utiliza do algoritmo criado acima H256
    
                System.out.println(token);
    /**
     * Retorna o token criado
     */
     return token;
  }
}
