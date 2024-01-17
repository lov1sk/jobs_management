package br.com.lov1sk.vacancy_manegement.providers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

// Essa classe é injetavel em outros locais da aplicação
@Service
public class JWTCompanyProvider {
  /**
   * Pegamos o secret de dentro de application.properties
   */
  @Value("${security.token.secret}")
  private String jwtSecret;
  
  /**
   * Essa função tem como objetivo receber um token JWT
   * e decodifica-lo com base no nosso secret, o que garantirá
   * a veracidade do token.
   * 
   * @param tokenToValidate
   * @return tokenDecoded
   */
  public DecodedJWT validate(String tokenToValidate){
    System.out.println(jwtSecret + "JWT provider");
    
    Algorithm jwtAlgorithm = Algorithm.HMAC256(jwtSecret); // Definimos que o algoritmo do JWT será H256 e passamos nosso secret para validar
    var tokenWithoutBearerKeyword = tokenToValidate.replace("Bearer ", ""); // Como pegamos todas as informações do header authorization precisamos tirar a palavra bearer
    try {
      var tokenDecoded = JWT
                      .require(jwtAlgorithm) // Passamos o algoritmo pro jwt usar ao descriptografar
                      .build()
                      .verify(tokenWithoutBearerKeyword); // Verificamos se o token que passamos 
      return tokenDecoded;
    } catch (JWTVerificationException e) {
      System.out.println("Jwt exception: " + e.getMessage());
      return null;
    }
  }
}
