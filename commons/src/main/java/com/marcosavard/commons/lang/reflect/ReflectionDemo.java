package com.marcosavard.commons.lang.reflect;

import com.marcosavard.commons.debug.Console;

import java.awt.*;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class ReflectionDemo {
  public static final NamedColor WHITE = new NamedColor("White", 255, 255, 255);

  public static void main(String[] args) throws URISyntaxException {
    //    instantiate(String[].class);

    // java.awt
    instantiate(Color.class);

    // java.io and java.nio
    instantiate(File.class);
    //  instantiate(Path.class);

    // java.lang
    instantiate(Boolean.class);
    instantiate(Byte.class);
    instantiate(Character.class);
    instantiate(Integer.class);
    instantiate(Long.class);
    instantiate(Float.class);
    instantiate(Double.class);
    instantiate(Short.class);
    instantiate(String.class);

    // java.math
    instantiate(BigDecimal.class);
    instantiate(BigInteger.class);
    instantiate(Random.class);

    // java.net
    instantiate(URI.class);

    // java.time
    instantiate(Date.class);
    instantiate(LocalDate.class);
    instantiate(LocalDateTime.class);
    instantiate(LocalTime.class);
    instantiate(ZonedDateTime.class);
    instantiate(ZoneOffset.class);

    // java.util
    instantiate(ArrayList.class);
    instantiate(HashMap.class);
    instantiate(LinkedList.class);

    // new URL("a");

  }

  private static void instantiate(Class claz) {
    String pattern = "{0} : {1}";
    Object instance = Reflection.instantiate(claz);
    String value =
        (instance instanceof CharSequence) ? "\"" + instance + "\"" : instance.toString();
    Console.println(pattern, claz, value);
  }

  private static void invoke() {
    try {
      Button btn = new Button("Ok");
      btn.setLocation(15, 20);

      Reflection.set(btn, "y", 25);
      Reflection.invoke(btn, "setBackground", WHITE);
      Reflection.invoke(btn, "setEnabled", false);

      System.out.println("label = " + Reflection.get(btn, "label"));
      System.out.println("x = " + Reflection.get(btn, "x"));
      System.out.println("y = " + Reflection.get(btn, "y"));
      System.out.println("background = " + Reflection.get(btn, "background"));
      System.out.println("enabled = " + Reflection.get(btn, "enabled"));
      System.out.println("name = " + Reflection.invoke(btn, "getName"));

      System.out.println("round(3.1416) = " + Reflection.invokeStatic(Math.class, "round", 3.1416));

    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    }
  }

  private static class NamedColor extends Color {

    public NamedColor(String name, int r, int g, int b) {
      super(r, g, b);
    }
  }
}
