package br.com.lov1sk.vacancy_manegement.providers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JWTCandidateProvider {
  
  @Value("${security.token.secret}") // Pegamos o secret de dentro de application.properties
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

    
    /**
     * Define um algoritmo H256 como base para decodificação de tokens
     * 
     * Retira a palavra bearer que veio do Authorization header
     */
    Algorithm jwtAlgorithm = Algorithm.HMAC256(jwtSecret); // Definimos que o algoritmo do JWT será H256 e passamos nosso secret para validar
    var tokenWithoutBearerKeyword = tokenToValidate.replace("Bearer ", ""); // Como pegamos todas as informações do header authorization precisamos tirar a palavra bearer
   
    /**
     * Tenta validar o token com base no secret,
     * se der erro, retorna nulo para a proxima camada
     */

    try {
      var tokenDecoded = JWT.require(jwtAlgorithm) // Passamos o algoritmo pro jwt usar ao descriptografar
                          .build() // Cria uma nova instancia do token com base no algoritmo passado
                          .verify(tokenWithoutBearerKeyword); // Verificamos se o token que passamos 
      return tokenDecoded;
    
    } catch (JWTVerificationException e) {
      // TODO: handle exception
      System.out.println("Token candidate not decoded");
      return null;
    }
    
  }
}
