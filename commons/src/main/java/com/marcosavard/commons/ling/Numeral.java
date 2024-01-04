package com.marcosavard.commons.ling;

import java.util.Locale;

public abstract class Numeral {

  public static Numeral of(Locale display) {
    Numeral numeral;

    if ("fr".equals(display.getLanguage())) {
      numeral = new FrNumeral();
    } else {
      numeral = new EnNumeral();
    }

    return numeral;
  }

  public abstract String getDisplayName(int number);

  private static class EnNumeral extends Numeral {
    private static final String[] DIGITS =
        new String[] {
          "zero",
          "one",
          "two",
          "three",
          "four",
          "five",
          "six",
          "seven",
          "eight",
          "nine",
          "ten",
          "eleven",
          "twelve",
          "thirteen",
          "forteen",
          "fifteen",
          "sixteen",
          "seventeen",
          "eighteen",
          "nineteen"
        };

    private static final String[] TENS =
        new String[] {
          "",
          "ten",
          "twenty",
          "thirty",
          "forty",
          "fifty",
          "sixty",
          "seventy",
          "eighty",
          "ninety",
          "one hundred"
        };

    @Override
    public String getDisplayName(int number) {
      String numeral = "?";

      if (number <= 19) {
        numeral = DIGITS[number];
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

    private String upTo100(int number) {
      int tens = (number / 10);
      int units = number % 10;
      String numeral = TENS[tens];
      numeral = (units == 0) ? numeral : numeral + "-" + getDisplayName(units);
      return numeral;
    }

    private String upTo1000(int number) {
      int hundreds = number / 100;
      int units = number % 100;
      String numeral =
          (hundreds == 1) ? "one hundred" : getDisplayName(hundreds) + " " + "hundreds";
      numeral = (units == 0) ? numeral : numeral + " " + getDisplayName(units);
      return numeral;
    }

    private String upTo1_000_000(int number) {
      int thousands = number / 1000;
      int units = number % 1000;
      String numeral =
          (thousands == 1) ? "one thousand" : getDisplayName(thousands) + " " + "thousands";
      numeral = (units == 0) ? numeral : numeral + " " + getDisplayName(units);
      return numeral;
    }

    private String upTo1_000_000_000(int number) {
      int millions = number / 1_000_000;
      int units = number % 1_000_000;
      String numeral = getDisplayName(millions) + " " + "milion";
      numeral = (units == 0) ? numeral : numeral + " " + getDisplayName(units);
      return numeral;
    }
  }

  private static class FrNumeral extends Numeral {
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
    public String getDisplayName(int number) {
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
      numeral = (units == 0) ? numeral : numeral + "-" + getDisplayName(units);
      return numeral;
    }

    private String upTo1000(int number) {
      int hundreds = number / 100;
      int units = number % 100;
      String numeral = (hundreds == 1) ? "cent" : getDisplayName(hundreds) + " " + "cents";
      numeral = (units == 0) ? numeral : numeral + " " + getDisplayName(units);
      return numeral;
    }

    private String upTo1_000_000(int number) {
      int thousands = number / 1000;
      int units = number % 1000;
      String numeral = (thousands == 1) ? "mille" : getDisplayName(thousands) + " " + "milles";
      numeral = (units == 0) ? numeral : numeral + " " + getDisplayName(units);
      return numeral;
    }

    private String upTo1_000_000_000(int number) {
      int millions = number / 1_000_000;
      int units = number % 1_000_000;
      String numeral = getDisplayName(millions) + " " + "million";
      numeral = (units == 0) ? numeral : numeral + " " + getDisplayName(units);
      return numeral;
    }
  }
}
