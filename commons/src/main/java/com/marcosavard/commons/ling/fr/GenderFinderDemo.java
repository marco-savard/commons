package com.marcosavard.commons.ling.fr;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import com.marcosavard.commons.ling.processing.Noun;
import com.marcosavard.commons.ling.processing.NounReader;
import com.marcosavard.commons.math.arithmetic.Percent;

public class GenderFinderDemo {

  public static void main(String[] args) {
    Map<String, Noun> allNouns = loadDictionary();
    EndingFinder finder = new EndingFinder();
    List<String> wordsE = finder.getWordsEndingIn(allNouns, "e");
    findGender(allNouns, wordsE);


  }

  private static Map<String, Noun> loadDictionary() {
    int nb = 96 * 1000;
    NounReader reader = NounReader.of(NounReader.class, "nouns.txt");
    Map<String, Noun> nouns = null;
    try {
      nouns = reader.read(nb);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return nouns;
  }

  private static void findGender(Map<String, Noun> nouns, List<String> words) {
    GenderFinder finder = new GenderFinder();
    List<String> exceptions = new ArrayList<String>();
    int nbSuccess = 0;
    int total = 0;

    for (String word : words) {

      int beginning = Math.max(0, word.length() - 2);
      String suffix = word.substring(beginning);
      boolean suffixE = suffix.equals("ae");
      suffixE |= suffix.equals("be");
      suffixE |= suffix.equals("ce");
      suffixE |= suffix.equals("de");

      if (suffixE) {
        Noun noun = nouns.get(word);
        Gender gender = finder.findGender(word);
        boolean expected = noun.isMasculine();

        boolean failed = (gender == Gender.MASCULINE) && !noun.isMasculine();
        failed |= (gender == Gender.FEMININE) && noun.isMasculine();
        nbSuccess += failed ? 0 : 1;
        total++;

        if (failed && exceptions.size() < 200) {
          exceptions.add(word);
        }
      }
    }

    Percent percent = Percent.of(nbSuccess, total);
    String msg = MessageFormat.format("{0} words : {1} not found, {2} correctly found", total,
        (total - nbSuccess), percent);
    System.out.println(msg);

    Collections.sort(exceptions);
    msg = MessageFormat.format("{0} exceptions : {1}", exceptions.size(), exceptions);
    System.out.println(msg);
  }

}
