package br.com.lov1sk.vacancy_manegement.module.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
  Classe criada para conter o metodo de coversão dos tipos Objeto para Json (Objeto javascript)
 */
public class ConvertObjectToJson {
    public static String convert(Object obj){
        try{
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
