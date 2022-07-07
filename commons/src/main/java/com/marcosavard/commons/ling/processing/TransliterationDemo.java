package com.marcosavard.commons.ling.processing;

import com.marcosavard.commons.debug.Console;

public class TransliterationDemo {

  public static void main(String[] args) {
    demoTransliterate("helios");
    demoTransliterate("helena");
    demoTransliterate("sophos");
    Console.println();

    Console.println("Greek Alphabet:");
    String greek = Transliteration.toGreek("alpha beta gamma delta epsilon");
    Console.println(greek);

    greek = Transliteration.toGreek("zeta eta theta");
    Console.println(greek);

    greek = Transliteration.toGreek("iota kappa lambda");
    Console.println(greek);

  }

  private static void demoTransliterate(String latin) {
    String greek = Transliteration.toGreek(latin);
    latin = Transliteration.toLatin(greek);
    Console.println("{0} : {1}", greek, latin);


  }

}
