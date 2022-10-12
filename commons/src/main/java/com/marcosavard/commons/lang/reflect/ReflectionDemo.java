package com.marcosavard.commons.lang.reflect;

import com.marcosavard.commons.debug.Console;

import java.awt.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class ReflectionDemo {
  public static final NamedColor WHITE = new NamedColor("White", 255, 255, 255);

  public static void main(String[] args) {
    demoInstantiateByReflection();
    demoInstantiateAndInvokeByReflection();
    demoStaticInvoke();
  }

  private static void demoInstantiateByReflection() {
    // java.lang
    instantiate(Integer.class);
    instantiate(Double.class);
    instantiate(String.class);
    instantiate(BigDecimal.class);
    instantiate(BigInteger.class);
    instantiate(LocalDate.class);
    instantiate(LocalTime.class);

    // java.util
    instantiate(ArrayList.class);
    instantiate(HashMap.class);
    instantiate(Locale.class);
    Console.println();
  }

  private static void demoInstantiateAndInvokeByReflection() {
    // instantiate by reflection
    Button btn = Reflection.instantiate(Button.class); // Button btn = new Button();

    // invoke by reflection
    Reflection.invoke(btn, "setLabel", "OK"); // btn.setLabel("OK");
    String label = (String) Reflection.invoke(btn, "getLabel");

    // set, get by reflection
    Reflection.set(btn, "enabled", false);
    Reflection.set(btn, "background", WHITE);
    Reflection.set(btn, "location", new Object[] {15, 20});
    boolean enabled = Reflection.is(btn, "enabled");
    Color background = (Color) Reflection.get(btn, "background");
    int x = (Integer) Reflection.get(btn, "x");
    int y = (Integer) Reflection.get(btn, "y");

    // print results
    Console.println("label = {0}", label);
    Console.println("enabled = {0}", enabled);
    Console.println("background = {0}", background);
    Console.println("location = {0}, {1}", x, y);
    Console.println("btn = {0}", btn);
    Console.println();
  }

  private static void demoStaticInvoke() {
    long rounded = (Long) Reflection.invokeStatic(Math.class, "round", Math.PI);
    Console.println("round(3.1416) = {0}", rounded);
  }

  private static void instantiate(Class claz) {
    String pattern = "{0} : {1}";
    Object instance = Reflection.instantiate(claz);
    String value =
        (instance instanceof CharSequence) ? "\"" + instance + "\"" : instance.toString();
    Console.println(pattern, claz, value);
  }

  private static class NamedColor extends Color {

    public NamedColor(String name, int r, int g, int b) {
      super(r, g, b);
    }
  }
}
