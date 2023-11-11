package com.marcosavard.commons.ui.color;

import com.marcosavard.commons.debug.Console;

import java.awt.*;
import java.util.List;
import java.util.Locale;

public class ColorNameDemo {

  // display locale-sensible color names
  // eg ffffff -> white, blanc, etc.
  // algo to find the correct color (RGB -> HSB -> color names)
  public static void main(String[] args) {
    Locale locale = Locale.FRENCH;

    List<Color> colors = ColorName.getNamedColors();

    for (Color color : colors) {
      String name = ColorName.of(color).toString(locale);
      Console.println(name);
    }
  }
}
