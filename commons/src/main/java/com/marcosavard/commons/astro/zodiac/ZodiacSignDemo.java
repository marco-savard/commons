package com.marcosavard.commons.astro.zodiac;

import com.marcosavard.commons.debug.Console;

import java.util.Locale;

public class ZodiacSignDemo {
  public static void main(String[] args) {
    Locale display = Locale.FRENCH;

    for (ZodiacSign sign : ZodiacSign.values()) {
      Console.println(sign.getDisplayName(display));
    }
  }
}
