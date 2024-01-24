package com.marcosavard.commons.ling.en;

import com.marcosavard.commons.ling.Numeral;

public class EnNumeral extends Numeral {
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
    String numeral = (hundreds == 1) ? "one hundred" : getDisplayName(hundreds) + " " + "hundreds";
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
