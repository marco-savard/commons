package com.marcosavard.commons.ling;

import com.marcosavard.commons.ling.en.EnNumeral;
import com.marcosavard.commons.ling.fr.FrNumeral;

import java.util.Locale;

public abstract class Numeral {
  public enum Category {
    CARDINAL,
    ORDINAL
  }

  public static Numeral of(Locale display) {
    Numeral numeral;

    if ("fr".equals(display.getLanguage())) {
      numeral = new FrNumeral();
    } else {
      numeral = new EnNumeral();
    }

    return numeral;
  }

  public String getDisplayName(int number) {
    return getDisplayName(number, Category.CARDINAL);
  }

  public abstract String getDisplayName(int number, Category category);

}
