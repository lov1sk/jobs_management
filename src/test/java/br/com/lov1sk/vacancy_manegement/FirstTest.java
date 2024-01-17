package br.com.lov1sk.vacancy_manegement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FirstTest {
  
  public static double calculate(double n, double n2){
    return n + n2;
  }

  @Test
  public void should_Be_Able_To_Calculate_Two_Numbers() {
    var result = calculate(1, 2);
    assertEquals(result, 3);
  }
}
