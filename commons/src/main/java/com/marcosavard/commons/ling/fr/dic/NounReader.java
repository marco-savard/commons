package com.marcosavard.commons.ling.fr.dic;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.marcosavard.commons.io.csv.CsvReader;
import com.marcosavard.commons.ling.processing.Noun;

public class NounReader {
  private CsvReader csvReader;

  public NounReader(CsvReader reader) {
    csvReader = reader;
  }

  public static NounReader of(Class claz, String filename) {
    NounReader reader = new NounReader(CsvReader.of(claz, filename));
    return reader;
  }

  public Map<String, Noun> read(int nb) throws IOException {
    Map<String, Noun> nouns = new HashMap<String, Noun>();

    for (int i = 0; i < nb; i++) {
      String[] entries = csvReader.readNext();
      Noun.Gender gender = Noun.Gender.UNKNOWN;
      Noun.Number number = Noun.Number.UNKNOWN;

      for (int j = 1; j < entries.length; j++) {
        if ("mas".equals(entries[j])) {
          gender = Noun.Gender.MASC;
        } else if ("fem".equals(entries[j])) {
          gender = Noun.Gender.FEM;
        } else if ("sg".equals(entries[j])) {
          number = Noun.Number.SING;
        } else if ("pl".equals(entries[j])) {
          number = Noun.Number.PLUR;
        }
      }

      Noun noun = new Noun(entries[0], gender, number);
      nouns.put(entries[0], noun);
    }

    return nouns;
  }



}
