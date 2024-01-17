package br.com.lov1sk.vacancy_manegement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;

// Classe de configuração, o spring automaticamente fará as configurações necessarias
@Configuration
public class SwaggerConfig {

  @Bean // Uma nova instancia de configuração 
  public OpenAPI openAPI() {
    /**
     * Informações Sobre a documentação da API
     * Title: Nome do projeto
     * description: Descrição do projeto
     * version: versão do projeto
     */
    var apiInfo = new Info().title("Gestão de Vagas")
                            .description("API responsável pela gestão de vagas")
                            .version("1");


    /**
     * Cria-se uma nova instancia de documentação
     * passando as informações que definimos acima para a API
     * 
     * Neste caso tambem é passado o esquema de autenticação usado
     */
    return new OpenAPI().info(apiInfo) // Passamos as informações da API aqui
                        .schemaRequirement("jwt_auth", creaSecurityScheme()); // Passamos o schema da autenticação usada
  }

  /**
   * Metodo destinado a criar um novo schema de autenticação para o swagger
   * @return
   */
  private SecurityScheme creaSecurityScheme() {
    /**
     * Esquema de seguraça que a nossa aplicação possui e que o swagger tem que se basear
     * na hora de fazer requisições.
     * 
     */
    return new SecurityScheme().name("jwt_auth") // Nome da autenticação definida, neste caso sempre jwt_auth
                               .scheme("bearer") // Tipo de token
                               .type(SecurityScheme.Type.HTTP) // Tipo de requisições
                               .bearerFormat("JWT") // Tipo de token
                               .in(In.HEADER); // Onde o token é mantido
  }
} 
