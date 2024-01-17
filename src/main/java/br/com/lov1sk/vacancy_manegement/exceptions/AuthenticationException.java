package br.com.lov1sk.vacancy_manegement.exceptions;

public class AuthenticationException extends RuntimeException{
  public AuthenticationException(){
    super("Wrong credentials, please try again");
  }
}