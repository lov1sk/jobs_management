package br.com.lov1sk.vacancy_manegement.exceptions;

public class ResourceNotFoundException extends RuntimeException{
  public ResourceNotFoundException(String resource){
    super("This resource: '" + resource + "', has not found on database");
  }
}
