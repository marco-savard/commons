package com.marcosavard.commons.geog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class CountryGlossary extends Glossary {

  public String getSouthWord(Locale locale) {
    String southKorea = getSouthKorea(locale).toLowerCase();
    String southSudan = getSouthSudan(locale).toLowerCase();
    String word = WordUtil.findLonguestCommonSequence(southKorea, southSudan);
    word = word.replace("de", "");
    word = word.replace('-', ' ');
    word = WordUtil.removeShortWords(word, 3).trim();
    return word;
  }

  public String getNorthWord(Locale locale) {
    String southKorea = getSouthKorea(locale).toLowerCase();
    String northKorea = getNorthKorea(locale).toLowerCase();
    String south = getSouthWord(locale);
    String korea = southKorea.replace(south, "");
    String word = northKorea.replace(korea, "");
    word = word.replace('-', ' ');
    word = WordUtil.removeShortWords(word, 3).trim();
    return word;
  }

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

  private String getLongest(List<String> strings) {
    String longest = strings.stream().max(Comparator.comparingInt(String::length)).get();
    return longest;
  }

  private String getNorthKorea(Locale locale) {
    String southSudan = Country.localeOf("KP").getDisplayCountry(locale).toLowerCase();
    return southSudan;
  }

  private String getSouthSudan(Locale locale) {
    String southSudan = Country.localeOf("SS").getDisplayCountry(locale).toLowerCase();
    return southSudan;
  }

  private String getSouthAfrica(Locale displayLanguage) {
    List<Locale> locales = Arrays.asList(Locale.getAvailableLocales());
    Locale locale =
        locales.stream().filter(l -> l.toString().equals("en_ZA")).findFirst().orElse(null);
    return locale.getDisplayCountry(displayLanguage);
  }

  private String getSouthKorea(Locale displayLanguage) {
    List<Locale> locales = Arrays.asList(Locale.getAvailableLocales());
    Locale locale =
        locales.stream().filter(l -> l.toString().equals("ko_KR")).findFirst().orElse(null);
    return locale.getDisplayCountry(displayLanguage);
  }
}
