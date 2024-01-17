package br.com.lov1sk.vacancy_manegement.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data // Cria getters e setters para todos os parametros
@AllArgsConstructor // Cria um construtor com todos os parametros

/**
 * É uma classe só para simbolizar o tipo de retorno que terá o handler
 * de excessoes invalidas por meio de argumentos invalidos
 */
public class InvalidArgumentDTO {
  private String message;
  private String field;
}
