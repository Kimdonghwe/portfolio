package com.example.test;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

public class RegExTest {
  @Test
  void ti1(){
    String pattern = "[0-9]{3,10}";

    System.out.println(Pattern.matches(pattern,"31"));

  }
}
