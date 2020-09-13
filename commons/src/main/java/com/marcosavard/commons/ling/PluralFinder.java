package com.marcosavard.commons.ling;

public class PluralFinder {
  private static final String[] EXCEPTIONS_AL = new String[] { //
      "acétal", "ammonal", "aval", //
      "barbital", "baribal", "butanal", //
      "cal", "cantal", "captal", "caracal", "chacal", "choral", //
      "citral", "corral"};
  private static final String[] EXCEPTIONS_EU = new String[] { //
      "bleu", "pneu"};
  private static final String[] EXCEPTIONS_OU = new String[] { //
      "bijou", "caillou", "chou", "genou", "hibou"};

  private String findPluralAl(String singular) {
    boolean exception = false;

    if (singular.endsWith("goal")) {
      exception = true;
    } else {
      for (String word : EXCEPTIONS_AL) {
        exception = singular.endsWith(word);
        if (exception) {
          break;
        }
      }
    }

    int length = singular.length();
    String plural = exception ? singular + "s" : singular.substring(0, length - 2) + "aux";;
    return plural;
  }

  private String findPluralEu(String singular) {
    boolean exception = false;

    for (String word : EXCEPTIONS_EU) {
      exception = singular.endsWith(word);
      if (exception) {
        break;
      }
    }

    String finale = exception ? "s" : "x";
    return singular + finale;
  }

  private String findPluralOu(String singular) {
    boolean exception = false;

    for (String word : EXCEPTIONS_OU) {
      exception = singular.endsWith(word);
      if (exception) {
        break;
      }
    }

    String finale = exception ? "x" : "s";
    return singular + finale;
  }

  public String findPlural(String singular) {
    int length = singular.length();
    String plural;

    if (singular.endsWith("al")) {
      plural = findPluralAl(singular);
    } else if (singular.endsWith("eau")) {
      plural = singular + "x";
    } else if (singular.endsWith("au")) {
      plural = singular + "x";
    } else if (singular.endsWith("eu")) {
      plural = findPluralEu(singular);
    } else if (singular.endsWith("ou")) {
      plural = findPluralOu(singular);
    } else if (singular.endsWith("us")) {
      plural = singular.substring(0, length - 2) + "i";
    } else if (singular.endsWith("s")) {
      plural = singular;
    } else {
      plural = singular + "s";
    }

    return plural;
  }



}
