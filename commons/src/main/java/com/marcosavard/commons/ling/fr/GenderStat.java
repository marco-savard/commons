package com.marcosavard.commons.ling.fr;

import java.util.Map;
import java.util.TreeMap;
import com.marcosavard.commons.ling.processing.Noun;

public class GenderStat {
  int nbMasculine = 0;
  int total = 0;

  public static Map<String, GenderStat> getGender(Map<String, Noun> nouns, int len) {
    Map<String, GenderStat> statBySuffix = new TreeMap<>();

    for (Noun noun : nouns.values()) {
      if (noun.isSingular()) {
        String text = noun.getText();
        int beginning = Math.max(0, text.length() - len);
        String suffix = text.substring(beginning);
        GenderStat stat = statBySuffix.get(suffix);

        if (stat == null) {
          stat = new GenderStat();
          statBySuffix.put(suffix, stat);
        }

        stat.nbMasculine += noun.isMasculine() ? 1 : 0;
        stat.total++;
      }
    }

    return statBySuffix;
  }

}
