package com.marcosavard.commons.ling;

public class TransliterationDemo {

  public static void main(String[] args) {
    String greek = Transliteration.toGreek("alpha beta gamma delta epsilon");
    System.out.println(greek);

    greek = Transliteration.toGreek("zeta eta theta");
    System.out.println(greek);

    greek = Transliteration.toGreek("iota kappa lambda");
    System.out.println(greek);

  }

}
