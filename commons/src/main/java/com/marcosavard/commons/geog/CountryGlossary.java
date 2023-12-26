package com.marcosavard.commons.geog;

import com.marcosavard.commons.text.WordUtil;

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
    String samoa = CountryOld.localesOf("WS").get(0).getDisplayCountry(display).toLowerCase();
    String americanSamoa =
        CountryOld.localesOf("AS").get(0).getDisplayCountry(display).toLowerCase();
    String american = americanSamoa.replace(samoa, "");
    american = WordUtil.removeShortWords(american, 2);
    american = toSingular(american, display);
    american = toMasculine(american, display);
    return american;
  }

  // british IO  VG
  public String getBritishWord(Locale display) {
    Locale locale1 = CountryOld.localesOf("VG").get(0);
    Locale locale2 = CountryOld.localesOf("VI").get(0);
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
    Locale locale1 = CountryOld.localesOf("CX").get(0);
    String text1 = locale1.getDisplayCountry(display);
    String island;

    if (oneOf(display, "nl")) {
      island = text1.replace("Christmas", "");
    } else {
      Locale locale2 = CountryOld.localesOf("NF").get(0);
      String text2 = locale2.getDisplayCountry(display);
      List<String> words = super.findCommon(text1, text2);
      island = String.join(" ", words).toLowerCase();
    }

    return island;
  }

  public String getIslandsWord(Locale display) {
    Locale locale1 = CountryOld.localesOf("CK").get(0);
    String text1 = locale1.getDisplayCountry(display).toLowerCase();
    String word = text1.replace("cook", "").trim();
    return word;
  }

  // AX,
  // islands CC, CK, FK, MH, MP PN SB TC UM VG VI

  private String getLongest(List<String> strings) {
    String longest = strings.stream().max(Comparator.comparingInt(String::length)).get();
    return longest;
  }

  private String getNorthKorea(Locale locale) {
    String southSudan = CountryOld.localesOf("KP").get(0).getDisplayCountry(locale).toLowerCase();
    return southSudan;
  }

  private String getSouthSudan(Locale locale) {
    String southSudan = CountryOld.localesOf("SS").get(0).getDisplayCountry(locale).toLowerCase();
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
