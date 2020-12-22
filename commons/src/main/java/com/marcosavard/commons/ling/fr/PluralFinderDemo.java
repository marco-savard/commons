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

public class PluralFinderDemo {

  public static void main(String[] args) {
    try {
      demoFindPlurals();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void demoFindPlurals() throws IOException {
    int nb = 96 * 1000;
    NounReader reader = NounReader.of(NounReader.class, "nouns.txt");
    Map<String, Noun> nouns = reader.read(nb);
    int singulars = 0, pluralsFound = 0;
    int endingAil = 0, endingAl = 0, endingAu = 0, endingEu = 0, endingOu = 0, endingUm = 0,
        endingUs = 0, endingOthers = 0;

    PluralFinder finder = new PluralFinder();
    List<String> exceptions = new ArrayList<String>();

    for (Noun noun : nouns.values()) {
      if (noun.isSingular()) {
        String text = noun.getText();
        String plural = finder.findPlural(text);
        Noun pluralNoun = nouns.get(plural);
        pluralsFound += (pluralNoun == null) ? 0 : 1;
        singulars++;

        if (pluralNoun == null) {
          if (text.endsWith("ail")) {
            endingAil++;
            exceptions.add(noun.getText());
          } else if (text.endsWith("al")) {
            endingAl++;
            exceptions.add(noun.getText());
          } else if (text.endsWith("au")) {
            endingAu++;
            exceptions.add(noun.getText());
          } else if (text.endsWith("eu")) {
            endingEu++;
          } else if (text.endsWith("ou")) {
            endingOu++;

          } else if (text.endsWith("um")) {
            endingUm++;
            exceptions.add(noun.getText());
          } else if (text.endsWith("us")) {
            endingUs++;
          } else {
            endingOthers++;

          }
        }
      }
    }

    double pluralNotFound = singulars - pluralsFound;
    Percent percent = Percent.of(pluralNotFound, singulars);
    String msg = MessageFormat.format("{0} words : {1} plurals not found ({2})", singulars,
        pluralNotFound, percent);
    System.out.println(msg);

    Collections.sort(exceptions);
    msg = MessageFormat.format("{0} exceptions : {1}", exceptions.size(), exceptions);
    System.out.println(msg);

    msg = MessageFormat.format("  termine en -au: {0}", endingAu);
    System.out.println(msg);

    msg = MessageFormat.format("  termine en -al: {0}", endingAl);
    System.out.println(msg);

    msg = MessageFormat.format("  termine en -eu: {0}", endingEu);
    System.out.println(msg);

    msg = MessageFormat.format("  termine en -ou: {0}", endingOu);
    System.out.println(msg);

    msg = MessageFormat.format("  termine en -ail: {0}", endingAil);
    System.out.println(msg);

    msg = MessageFormat.format("  termine en -us: {0}", endingUs);
    System.out.println(msg);

    msg = MessageFormat.format("  termine en -um: {0}", endingUm);
    System.out.println(msg);

    msg = MessageFormat.format("  termine en -autres: {0}", endingOthers);
    System.out.println(msg);

  }

}
