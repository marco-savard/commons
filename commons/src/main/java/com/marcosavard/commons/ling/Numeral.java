package com.marcosavard.commons.ling;

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

  private static class EnNumeral extends Numeral {
    private static final String[] CARDINALS =
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

    private static final String[] ORDINALS =
        new String[] {
          "zero", "first", "second", "third", null, "fifth", null, null, "eighth", "ninth", null,
          null, null, null, null, null, null, null, null, null,
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
    public String getDisplayName(int number, Category category) {
      String numeral = "?";

      if (number <= 19) {
        numeral = upTo19(number, category);
      } else if (number <= 99) {
        numeral = upTo100(number, category);
      } else if (number <= 999) {
        numeral = upTo1000(number, category);
      } else if (number <= 999_999) {
        numeral = upTo1_000_000(number, category);
      } else if (number <= 99_999_999) {
        numeral = upTo1_000_000_000(number, category);
      } else {
        numeral = Integer.toString(number);
      }
      return numeral;
    }

    private String upTo19(int number, Category category) {
      String numeral = (category == Category.ORDINAL) ? ORDINALS[number] : CARDINALS[number];
      numeral = (numeral == null) ? CARDINALS[number] + "th" : numeral;
      return numeral;
    }

    private String upTo100(int number, Category category) {
      int tens = (number / 10);
      int units = number % 10;
      String numeral = TENS[tens];
      numeral = (units == 0) ? numeral : numeral + "-" + getDisplayName(units, category);
      numeral = (units == 0) && (category == Category.ORDINAL) ? addTh(numeral) : numeral;
      return numeral;
    }

    private String addTh(String numeral) {
      String start = numeral.substring(0, numeral.length() - 1);
      String added = numeral.endsWith("y") ? start + "ith" : numeral + "th";
      return added;
    }

    private String upTo1000(int number, Category category) {
      int hundreds = number / 100;
      int units = number % 100;
      String numeral =
          (hundreds == 1) ? "one hundred" : getDisplayName(hundreds) + " " + "hundreds";
      numeral = (units == 0) ? numeral : numeral + " " + getDisplayName(units, category);
      return numeral;
    }

    private String upTo1_000_000(int number, Category category) {
      int thousands = number / 1000;
      int units = number % 1000;
      String numeral =
          (thousands == 1) ? "one thousand" : getDisplayName(thousands) + " " + "thousands";
      numeral = (units == 0) ? numeral : numeral + " " + getDisplayName(units, category);
      return numeral;
    }

    private String upTo1_000_000_000(int number, Category category) {
      int millions = number / 1_000_000;
      int units = number % 1_000_000;
      String numeral = getDisplayName(millions) + " " + "milion";
      numeral = (units == 0) ? numeral : numeral + " " + getDisplayName(units, category);
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
}
