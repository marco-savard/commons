package com.marcosavard.commons.lang.reflect;

import java.awt.Button;
import java.awt.Color;

public class ReflectionDemo {
  public static final NamedColor WHITE = new NamedColor("White", 255, 255, 255);

  public static void main(String[] args) {

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
