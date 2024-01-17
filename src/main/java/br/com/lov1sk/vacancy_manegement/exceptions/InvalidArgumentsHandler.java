package br.com.lov1sk.vacancy_manegement.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // Antes do retorno do controller, ele vai executar essa classe
public class InvalidArgumentsHandler {

  public MessageSource messageSource; // Uma padronização do spring para retonar json

  /**
   * Construtor da classe
   */
  public InvalidArgumentsHandler(MessageSource messageSource){
    this.messageSource = messageSource;
  }
  
  /* Digo que essa classe será uma das classes que o spring pode usar para validar excessoes
   * O parametro que essa anotation recebe é a propria classe
   */
  @ExceptionHandler(MethodArgumentNotValidException.class) 
  public ResponseEntity<List<InvalidArgumentDTO>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
   
   /**
    * Todos os campos que estiverem invalidos, são salvos nessa lista
    */
    List<InvalidArgumentDTO> invalidMessages = new ArrayList<InvalidArgumentDTO>(); 
   
    e.getBindingResult() // Pega o resultado retornado pela exception
    .getFieldErrors() // Pega somente o parametro fields
    .forEach(exception -> {
      String message = messageSource.getMessage(exception, LocaleContextHolder.getLocale());
      InvalidArgumentDTO exceptionMessage = new InvalidArgumentDTO(message, exception.getField());
      invalidMessages.add(exceptionMessage);
    });

    return new ResponseEntity<>(invalidMessages, HttpStatus.BAD_REQUEST); // Especifica a entidade de retorno do controller
  }
}
