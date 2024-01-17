package br.com.lov1sk.vacancy_manegement.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.lov1sk.vacancy_manegement.exceptions.UserAlreadyExistsException;
import br.com.lov1sk.vacancy_manegement.modules.company.entities.CompanyEntity;
import br.com.lov1sk.vacancy_manegement.modules.company.repositories.CompanyRepository;

@Service
public class CreateCompanyUseCase {

  @Autowired
  public CompanyRepository companyRepository;
  
  @Autowired
  private PasswordEncoder passwordEncoder;

  public CompanyEntity execute(CompanyEntity companyEntity){
    /**
     * Processo de buscar no banco de dados se ja existe
     * uma company previa, para não dar conflito
     */
    this.companyRepository
    .findByUsernameOrEmail(companyEntity.getUsername(),companyEntity.getEmail())
    .ifPresent((user) ->{
      throw new UserAlreadyExistsException("company");
    });

    /**
     * Processo de encriptografar a senha
     */
    var hashedPassword = passwordEncoder.encode(companyEntity.getPassword());
    companyEntity.setPassword(hashedPassword);

    /**
     * Salva a company no banco de dados e retorna para o controller
     */
    var companySavedOnDatabase = this.companyRepository.save(companyEntity);
    return companySavedOnDatabase;
  }
  
}
