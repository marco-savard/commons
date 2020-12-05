package com.marcosavard.commons.ling.fr;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import com.marcosavard.commons.ling.Noun;
import com.marcosavard.commons.ling.NounReader;
import com.marcosavard.commons.math.Percent;

public class GenderFinderDemo {

  public static void main(String[] args) {
    try {
      Map<String, Noun> nouns = loadDictionary();
      findGender(nouns);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static Map<String, Noun> loadDictionary() throws IOException {
    int nb = 96 * 1000;
    NounReader reader = NounReader.of(NounReader.class, "nouns.txt");
    Map<String, Noun> nouns = reader.read(nb);
    return nouns;
  }

  private static void findGender(Map<String, Noun> nouns) {
    GenderFinder finder = new GenderFinder();
    List<String> exceptions = new ArrayList<String>();
    int nbSuccess = 0;
    int total = 0;

    for (Noun noun : nouns.values()) {
      if (noun.isSingular()) {
        String text = noun.getText();
        boolean result = finder.isMasculine(text);
        boolean expected = noun.isMasculine();
        nbSuccess += (result == expected) ? 1 : 0;
        total++;

        if ((result != expected) && exceptions.size() < 800) {
          exceptions.add(text);
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
