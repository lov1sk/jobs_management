package br.com.lov1sk.vacancy_manegement.exceptions;

public class UserAlreadyExistsException extends RuntimeException{
  public UserAlreadyExistsException(String resource){
    super("this resource: '" + resource + "', already exists");
  }
}
