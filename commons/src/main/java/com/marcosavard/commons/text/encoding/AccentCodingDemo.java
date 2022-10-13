package com.marcosavard.commons.text.encoding;

import com.marcosavard.commons.debug.Console;

public class AccentCodingDemo {

  public static void main(String[] args) {
    demoAccents();
    demoCedillas();
    demoDiaeresis();
    demoTilde();
    demoLigatures();
    demoHtml();
  }

  private static void demoAccents() {
    demoEncoded("a` la carte");
    demoEncoded("ba^ton");
    demoEncoded("Mu:nich");
    demoEncoded("re'sume'");
    demoEncoded("cre^pe");
    demoEncoded("Dali'");
    demoEncoded("I^le, i^le");
    demoEncoded("Co'rdoba");
    demoEncoded("ro^le");

    Console.println();
  }

  private static void demoCedillas() {
    demoEncoded("fac,ade");
    Console.println();
  }

  private static void demoDiaeresis() {
    demoEncoded("Zoe:");
    demoEncoded("nai:ve");
    demoEncoded("Mu:nich");
    Console.println();
  }

  private static void demoTilde() {
    demoEncoded("can~on");
    demoEncoded("Sa~o Paulo");
    Console.println();
  }

  private static void demoLigatures() {
    demoEncoded("ex aequo");
    Console.println();
  }

  private static void demoHtml() {
    String encoded = "l'i^le d'a` co^te'";
    String decoded = AccentCoding.of(encoded);
    Console.println("{0} -> {1}", decoded, encoded);
  }

  private static void demoEncoded(String encoded) {
    String decoded = AccentCoding.of(encoded);
    encoded = AccentCoding.toAscii(decoded);
    Console.println("{0} -> {1}", decoded, encoded);
  }
}
