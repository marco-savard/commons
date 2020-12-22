package com.marcosavard.commons.ling;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import com.marcosavard.commons.io.csv.CsvReader;

public class LetterFrequency {
  private String[] languageCodes;
  private Map<String, Map<Character, Double>> letterFrequencyByLanguage;

  public LetterFrequency() {
    // read csv
    try {
      CsvReader cr = CsvReader.of(LetterFrequency.class, "letterFrequency.csv") //
          .withHeader(1, ';') //
          .withSeparator('\t');
      languageCodes = cr.readHeaderColumns();
      List<String[]> entries = cr.readAll();
      buildLetterFrequency(entries);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void buildLetterFrequency(List<String[]> entries) {
    letterFrequencyByLanguage = new LinkedHashMap<>();

    for (int i = 1; i < languageCodes.length; i++) {
      String languageCode = languageCodes[i];
      Map<Character, Double> frequencyByLetter = new LinkedHashMap<>();
      letterFrequencyByLanguage.put(languageCode, frequencyByLetter);
    }

    for (String[] entry : entries) {
      char ch = entry[0].charAt(0);
      buildLetterFrequencyForLetter(ch, entry);
    }
  }

  private void buildLetterFrequencyForLetter(char ch, String[] entry) {

    for (int i = 1; i < entry.length; i++) {
      String languageCode = languageCodes[i];
      String percent = entry[i].replace('%', ' ').trim();
      double value = Double.parseDouble(percent);

      Map<Character, Double> frequencyByLetter = letterFrequencyByLanguage.get(languageCode);
      frequencyByLetter.put(ch, value);
    }
  }

  public Map<String, Map<Character, Double>> getFrequencyOfAllLanguages() {
    return letterFrequencyByLanguage;
  }

  public Map<Character, Double> getFrequencyOfLocale(Locale language) {
    return getFrequencyOfLocale(language.getLanguage());
  }

  public Map<Character, Double> getFrequencyOfLocale(String languageCode) {
    Map<Character, Double> frequencyByLetter = letterFrequencyByLanguage.get(languageCode);
    return frequencyByLetter;
  }



}
