package com.marcosavard.commons.ling.processing;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class NounReaderDemo {

  public static void main(String[] args) {
    try {
      // findPlural();
      listPlurals();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void findPlural() {
    // PluralFinder finder = new PluralFinder();
    // String plural = finder.findPlural("amical");
    // System.out.println(plural);

  }

  private static void listPlurals() throws IOException {
    int nb = 70 * 1000;
    int nbIrregular = 0;
    // PluralFinder finder = new PluralFinder();
    NounReader reader = NounReader.of(NounReader.class, "nouns.txt");
    /*
     * List<Noun> nouns = reader.read(nb);
     * 
     * for (Noun noun : nouns) { if (noun.isSingular()) { String pluralText =
     * finder.findPlural(noun.getText());
     * 
     * Noun plural = findByName(nouns, pluralText);
     * 
     * if (plural == null) { String text = "  .." + noun.getText() + ", "; System.out.println(text);
     * nbIrregular++; } } }
     */
    String text = "nbIrregular : " + nbIrregular;
    System.out.println(text);


  }

  private static Noun findByName(List<Noun> nouns, String pluralText) {

    Noun plural = new Noun(pluralText, Noun.Gender.UNKNOWN, Noun.Number.PLUR);

    int idx = Collections.binarySearch(nouns, plural);
    Noun foundNoun = (idx < 0) ? null : nouns.get(idx);

    if (foundNoun == null) {
      for (Noun noun : nouns) {
        if (noun.isPlural() && pluralText.equals(noun.getText())) {
          foundNoun = noun;
          break;
        }
      }
    }

    return foundNoun;
  }

}
