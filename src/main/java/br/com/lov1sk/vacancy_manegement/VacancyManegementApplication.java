package br.com.lov1sk.vacancy_manegement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


// Applicação Spring Boot
@SpringBootApplication 
// Todos os componentes dentro do pacote definido, deve ser gerenciado pelo spring
@ComponentScan(basePackages = "br.com.lov1sk") 
public class VacancyManegementApplication {

	public static void main(String[] args) {
		SpringApplication.run(VacancyManegementApplication.class, args);
	}

}
