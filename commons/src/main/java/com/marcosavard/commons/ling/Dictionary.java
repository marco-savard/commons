package com.marcosavard.commons.ling;

import com.marcosavard.commons.io.csv.CsvReader;

public class Dictionary {
  private NounReader reader;

  public static Dictionary of(String filename) {
    NounReader reader = new NounReader(CsvReader.of(Dictionary.class, filename));
    Dictionary dictionary = new Dictionary(reader);
    return dictionary;
  }

  private Dictionary(NounReader reader) {
    this.reader = reader;
  }

  public Noun findWord(String word) {
    // TODO Auto-generated method stub
    return null;
  }

}
