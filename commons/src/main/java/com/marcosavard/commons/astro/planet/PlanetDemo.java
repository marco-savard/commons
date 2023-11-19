package com.marcosavard.commons.astro.planet;

import com.marcosavard.commons.debug.Console;

import java.util.Locale;

public class PlanetDemo {
  public static void main(String[] args) {
    Locale display = Locale.FRENCH;

    for (Planet planet : Planet.values()) {
      String name = planet.getDisplayName(display);
      Console.println("{0} : {1}", name, planet.getSymbol());
    }
  }
}
