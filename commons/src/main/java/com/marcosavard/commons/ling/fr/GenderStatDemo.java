package com.marcosavard.commons.ling.fr;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import com.marcosavard.commons.ling.Noun;
import com.marcosavard.commons.ling.NounReader;
import com.marcosavard.commons.math.Percent;

public class GenderStatDemo {

  public static void main(String[] args) {
    Map<String, Noun> nouns = loadDictionary();
    Map<String, GenderStat> statBySuffix = GenderStat.getGender(nouns, 3);
    printStats(statBySuffix);
  }

  private static Map<String, Noun> loadDictionary() {
    int nb = 96 * 1000;
    NounReader reader = NounReader.of(NounReader.class, "nouns.txt");
    Map<String, Noun> nouns = new TreeMap<>();

    try {
      nouns = reader.read(nb);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return nouns;
  }

  private static void printStats(Map<String, GenderStat> statBySuffix) {
    List<String> list = new ArrayList<>(statBySuffix.keySet());
    Comparator comparator = new StatComparator(statBySuffix);
    list.sort(comparator);

    for (String suffix : list) {
      GenderStat stat = statBySuffix.get(suffix);
      Percent percent = Percent.of(stat.nbMasculine, stat.total);
      String msg = MessageFormat.format("{0} : masc = {1} on {2} ({3})", suffix, stat.nbMasculine,
          stat.total, percent);
      System.out.println(msg);
    }
  }

  private static final class StatComparator implements Comparator<String> {
    private Map<String, GenderStat> statBySuffix;

    public StatComparator(Map<String, GenderStat> statBySuffix) {
      this.statBySuffix = statBySuffix;
    }

    public int compare(String s1, String s2) {
      GenderStat gs1 = statBySuffix.get(s1);
      GenderStat gs2 = statBySuffix.get(s2);
      int comparison = gs2.total - gs1.total;
      return comparison;
    }
  }

}
