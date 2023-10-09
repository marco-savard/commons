package com.marcosavard.commons.geog;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CountryGlossary extends Glossary {

  public String getAmericanWord(Locale display) {
    String samoa = Country.localeOf("WS").getDisplayCountry(display).toLowerCase();
    String americanSamoa = Country.localeOf("AS").getDisplayCountry(display).toLowerCase();
    String american = americanSamoa.replace(samoa, "");
    american = WordUtil.removeShortWords(american, 2);
    american = toSingular(american, display);
    american = toMasculine(american, display);
    return american;
  }

  // british IO  VG
  public String getBritishWord(Locale display) {
    Locale locale1 = Country.localeOf("VG");
    Locale locale2 = Country.localeOf("VI");
    String text1 = locale1.getDisplayCountry(display);
    String text2 = locale2.getDisplayCountry(display);
    List<String> common = super.findCommon(text1, text2);
    List<String> items = List.of(text1.split("\\s+"));
    List<String> words = new ArrayList<>();

    for (String item : items) {
      if (!common.contains(item)) {
        words.add(item);
      }
    }

    String word = String.join(" ", words).toLowerCase();
    word = toMasculine(toSingular(word, display), display);
    return word;
  }

  // island CX  NF (IM : no)
  public String getIslandWord(Locale display) {
    Locale locale1 = Country.localeOf("CX");
    Locale locale2 = Country.localeOf("NF");
    String text1 = locale1.getDisplayCountry(display);
    String text2 = locale2.getDisplayCountry(display);
    List<String> words = super.findCommon(text1, text2);
    String word = String.join(" ", words).toLowerCase();
    return word;
  }

  // islands AX, CC, CK, FK, MH, MP PN SB TC UM VG VI

}
