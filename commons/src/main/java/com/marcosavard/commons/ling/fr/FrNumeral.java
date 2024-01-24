package com.marcosavard.commons.ling.fr;

import com.marcosavard.commons.ling.Numeral;

public class FrNumeral extends Numeral {
  private static final String[] DIGITS =
      new String[] {
        "zero",
        "un",
        "deux",
        "trois",
        "quatre",
        "cinq",
        "six",
        "sept",
        "huit",
        "neuf",
        "dix",
        "onze",
        "douze",
        "treize",
        "quatorze",
        "quinze",
        "seize"
      };

  private static final String[] TENS =
      new String[] {
        "",
        "dix",
        "vingt",
        "trente",
        "quarante",
        "cinquante",
        "soixante",
        "soixante-dix",
        "quatre-vingt",
        "quatre-vingt-dix",
        "cent"
      };

  @Override
  public String getDisplayName(int number, Category category) {
    String numeral;

    if (number <= 16) {
      numeral = upTo17(number);
    } else if (number <= 59) {
      numeral = upTo60(number);
    } else if (number <= 99) {
      numeral = upTo100(number);
    } else if (number <= 999) {
      numeral = upTo1000(number, category);
    } else if (number <= 999_999) {
      numeral = upTo1_000_000(number, category);
    } else if (number <= 99_999_999) {
      numeral = upTo1_000_000_000(number, category);
    } else {
      numeral = Integer.toString(number);
    }

    if (category == Category.ORDINAL) {
      if (number == 1) {
        numeral = "premier";
      } else {
        numeral = addIeme(numeral);
      }
    }

    return numeral;
  }

  private String addIeme(String numeral) {
    String start = numeral.substring(0, numeral.length() - 1);
    numeral = numeral.endsWith("q") ? numeral + "u" : numeral;
    numeral = numeral.endsWith("f") ? start + "v" : numeral;
    numeral = numeral.endsWith("e") ? start + "ième" : numeral + "ième";
    return numeral;
  }

  private String upTo17(int number) {
    String numeral = DIGITS[number];
    return numeral;
  }

  private String upTo60(int number) {
    int tens = number / 10;
    int units = number % 10;
    String numeral = TENS[tens];
    numeral = (units == 1) ? numeral + "-et" : numeral;
    numeral = (units == 0) ? numeral : numeral + "-" + DIGITS[units];
    return numeral;
  }

  private String upTo100(int number) {
    int tens = (number / 20) * 2;
    int units = number % 20;
    String numeral = TENS[tens];
    numeral = (units == 1) ? numeral + "-et" : numeral;
    numeral = (units == 0) ? numeral : numeral + "-" + getDisplayName(units);
    return numeral;
  }

  private String upTo1000(int number, Category category) {
    int hundreds = number / 100;
    int units = number % 100;
    boolean plural = (hundreds > 1) && (category == Category.CARDINAL);
    String numeral = (hundreds == 1) ? "cent" : getDisplayName(hundreds) + " " + "cent";
    numeral = plural ? numeral + "s" : numeral;
    numeral = (units == 0) ? numeral : numeral + " " + getDisplayName(units);
    return numeral;
  }

  private String upTo1_000_000(int number, Category category) {
    int thousands = number / 1000;
    int units = number % 1000;
    boolean plural = (thousands > 1) && (category == Category.CARDINAL);
    String numeral = (thousands == 1) ? "mille" : getDisplayName(thousands) + " " + "mille";
    numeral = plural ? numeral + "s" : numeral;
    numeral = (units == 0) ? numeral : numeral + " " + getDisplayName(units);
    return numeral;
  }

  private String upTo1_000_000_000(int number, Category category) {
    int millions = number / 1_000_000;
    int units = number % 1_000_000;
    boolean plural = (millions > 1) && (category == Category.CARDINAL);
    String numeral = getDisplayName(millions) + " " + "million";
    numeral = plural ? numeral + "s" : numeral;
    numeral = (units == 0) ? numeral : numeral + " " + getDisplayName(units);
    return numeral;
  }
}
