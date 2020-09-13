package com.marcosavard.commons.ling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.marcosavard.commons.io.csv.CsvReader;

public class NounReader {
  private CsvReader csvReader;

  private NounReader(CsvReader reader) {
    csvReader = reader;
  }

  public static NounReader of(Class claz, String filename) {
    NounReader reader = new NounReader(CsvReader.of(claz, filename));
    return reader;
  }

  public List<Noun> read(int nb) throws IOException {
    List<Noun> nouns = new ArrayList<>();

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
      nouns.add(noun);
    }

    return nouns;
  }



}
