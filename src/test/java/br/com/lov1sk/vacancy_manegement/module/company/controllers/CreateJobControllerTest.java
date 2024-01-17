package br.com.lov1sk.vacancy_manegement.module.company.controllers;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import br.com.lov1sk.vacancy_manegement.module.utils.ConvertObjectToJson;
import br.com.lov1sk.vacancy_manegement.module.utils.CreateCompanyToken;
import br.com.lov1sk.vacancy_manegement.modules.company.dto.CreateJobDTO;

@RunWith(SpringRunner.class) // Informamos que queremos usar o spring runner (semelhante ao supertest do node), para rodar esses testes
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) // Essa classe vai testar uma aplicação do spring, a porta onde ela sobe é aleatoria
public class CreateJobControllerTest {

    // Esse é o objeto de teste principal
    private MockMvc mvc;

    // Esse contexto é a nossa aplicação em si
    @Autowired
    private WebApplicationContext context;

    /**
     * Executa antes dos testes
     */
    @BeforeEach
    public void setup(){
        mvc = MockMvcBuilders.webAppContextSetup(context) // Cria uma nova aplicação mockada dentro da variavel MVC com o contexto do nosso servidor
                             .apply(SecurityMockMvcConfigurers.springSecurity()) // Adiciona o serviço do spring security, pois usamos autenticação JWT
                             .build(); // Constroi uma "simulação" da nossa aplicação pronta para testar
    }

    @Test
    public void should_be_able_to_create_a_new_job() throws Exception{


      /**
       * Cria uma nova entidade do job
       */
        var createdJobDTO = CreateJobDTO.builder()
        .benefits("BENEFITS_TEST")
        .description("DESCRIPTION_TEST")
        .level("LEVEL_TEST")
        .build();

        var token = CreateCompanyToken.createJWT("650d6e80-a77f-4993-b485-77561ea821e3");
        System.out.println(token);
        // Performa uma chamada HTTP no controller
        var result = mvc.perform(
                          MockMvcRequestBuilders.post("/company/job/") //Define que a chamada http sera do tipo post, no end-point indicado
                                                .contentType(MediaType.APPLICATION_JSON) // O tipo de midia que é trafegado são jsons
                                                .header("Authorization",token)  // Envia a entidade de job criada, depois de transforma-la em JSON
                                                .content(ConvertObjectToJson.convert(createdJobDTO)))
                                                .andExpect(MockMvcResultMatchers.status().isCreated() // por fim, espera que o status code de resposta seja 200 - OK
                        );

        System.out.println(result);
    }

}