package br.com.lov1sk.vacancy_manegement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration // Essa anotação diz ao spring que essa classe deve ser executada na inicialização, pois ela tem a finalidade de configurar algo
@EnableMethodSecurity // Habilita o preAuthorize
public class SecurityConfig {

  /**
   * Injeção de dependencia do filtro criado para recuperar o JWT
   * das rotas /company e depois adicionar como middleware para e
   * cadeia de filtragem do spring security
   */
  @Autowired
  private SecurityFilter securityFilter;

  /**
   * Injeção de dependencia do filtro criado para recuperar o JWT
   * das rotas /candidate e depois adicionar como middleware para e
   * cadeia de filtragem do spring security
   */
  @Autowired
  private SecurityCandidateFilter securityCandidateFilter;

  /**
   * Endereços do swagger para vermos a documentação da API
   */
  private static final String[] SWAGGER_LIST = {
    "/swagger-ui/**",
    "/v3/api-docs/**",
    "/swagger-resource/**",
};
 
  /**
   * Metodo com objetivo de reconfigurar as configurações padrões do java
   * spring security, definindo quais rotas são protegidas e quais não são
   */  
  @Bean // Devolve uma nova instancia do filtro para o spring security, pois definimos novas configurações 
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

    http.csrf(csrf -> csrf.disable()) // Desabilita a segurança csrf padrão do spring, pois queremos customiza-lo
      .authorizeHttpRequests(auth -> {
        /**
         * Rotas do swagger, cadastro e login, não precisam ser protegidas!
         */
        auth
        .requestMatchers(SWAGGER_LIST).permitAll() //Essa rota (Endereços do swagger) não precisa de autenticação
        .requestMatchers("/candidate/").permitAll() //Essa rota (Cadastro de candidatos) não precisa de autenticação
        .requestMatchers("/candidate/auth").permitAll() //Essa rota (Login de candidatos) não precisa de autenticação
        .requestMatchers("/company/").permitAll() //Essa rota (Cadastro de empresas) não precisa de autenticação
        .requestMatchers("/company/auth").permitAll(); //Essa rota (Login de empresas) não precisa de autenticação

        /**
         * Todas as outras rotas exigem que o usuario esteja
         * logado enviando o JWT no Header
         * 
         * Para isso o filtro/middleware resgata antes de chegar a requisição
         * no controller e retira do header o token jwt, assim como valida-o
         */
        auth
        .anyRequest()
        .authenticated(); // Todas as outras rotas precisam de autenticação
        
      })
      .addFilterBefore(securityFilter, BasicAuthenticationFilter.class)
      .addFilterBefore(securityCandidateFilter, BasicAuthenticationFilter.class);
      
      
      return http.build(); // Retorna o restante das configurações http sobreescrita por mim
  }


  @Bean // Devolve uma nova instancia do filtro para o spring security, pois definimos novas configurações  
  PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder(); // Apenas uma instancia para podermos criptografar e comparar senhas
  }
}
    
    