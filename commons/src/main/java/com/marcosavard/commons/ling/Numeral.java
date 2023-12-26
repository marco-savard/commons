package com.marcosavard.commons.ling;

public class Numeral {
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

  public String of(int number) {
    String numeral;

    if (number <= 16) {
      numeral = DIGITS[number];
    } else if (number <= 59) {
      numeral = upTo60(number);
    } else if (number <= 99) {
      numeral = upTo100(number);
    } else if (number <= 999) {
      numeral = upTo1000(number);
    } else if (number <= 999_999) {
      numeral = upTo1_000_000(number);
    } else if (number <= 99_999_999) {
      numeral = upTo1_000_000_000(number);
    } else {
      numeral = Integer.toString(number);
    }

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
    numeral = (units == 0) ? numeral : numeral + "-" + of(units);
    return numeral;
  }

  private String upTo1000(int number) {
    int hundreds = number / 100;
    int units = number % 100;
    String numeral = (hundreds == 1) ? "cent" : of(hundreds) + " " + "cents";
    numeral = (units == 0) ? numeral : numeral + " " + of(units);
    return numeral;
  }

  private String upTo1_000_000(int number) {
    int thousands = number / 1000;
    int units = number % 1000;
    String numeral = (thousands == 1) ? "mille" : of(thousands) + " " + "milles";
    numeral = (units == 0) ? numeral : numeral + " " + of(units);
    return numeral;
  }

  private String upTo1_000_000_000(int number) {
    int millions = number / 1_000_000;
    int units = number % 1_000_000;
    String numeral = of(millions) + " " + "million";
    numeral = (units == 0) ? numeral : numeral + " " + of(units);
    return numeral;
  }
}
