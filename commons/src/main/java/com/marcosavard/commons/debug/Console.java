package com.marcosavard.commons.debug;

import java.text.MessageFormat;

public class Console {

  public static void println() {
    System.out.println();
  }

  public static void println(String line) {
    System.out.println(line);
  }

  public static void println(String pattern, Object... parameters) {
    String line = MessageFormat.format(pattern, parameters);
    System.out.println(line);
  }



}
