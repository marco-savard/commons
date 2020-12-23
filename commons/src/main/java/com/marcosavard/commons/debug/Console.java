package com.marcosavard.commons.debug;

import java.text.MessageFormat;

public class Console {

  public static void println() {
    System.out.println();
  }

  public static void println(Object object) {
    System.out.println(object);
  }

  public static void println(String pattern, Object... parameters) {
    String line = MessageFormat.format(pattern, parameters);
    System.out.println(line);
  }



}
