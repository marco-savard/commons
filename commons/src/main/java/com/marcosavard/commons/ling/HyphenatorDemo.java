package com.marcosavard.commons.ling;

public class HyphenatorDemo {

  public static void main(String[] args) {
    String[] words = new String[] { //
        "monday", "tuesday", "wednesday", "friday", "saturday", "sunday", //
        "january", "february", "march", "april", "may", "june", "july", "august", "september", //
        "ecole", "honeymoon", "elephant", "monstruous", //
        "console", "hyphen", "center", "centre", //
        "hyphen", "magnetic", "aquatic", "anachronism", //
        "of", "to", "a", "my", "you" //
    };
    Hyphenator hyphenator = new Hyphenator();

    for (String word : words) {
      String hyphenated = hyphenator.hyphenate(word);
      System.out.println(hyphenated);
    }
  }

}
