package com.marcosavard.commons.geog;

import com.marcosavard.commons.lang.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ContinentName {

  private static final Locale ANTARCTICA_LOCALE = new Locale("en", "AQ");
  private static final Locale INDONESIA_LOCALE = new Locale("in", "ID");

  public static String getDisplayAfrica(Locale displayLanguage) {
    String languageCode = displayLanguage.getLanguage();
    String displayName;

    if ("sl".equals(languageCode)) {
      displayName = "Áfrika";
    } else if ("pl".equals(languageCode)) {
      displayName = "Afryka";
    } else if (Arrays.asList("az", "cs", "hr", "sr", "hu").contains(languageCode)) {
      displayName = "Afrika";
    } else {
      String southAfrica = getSouthAfrica(displayLanguage);
      String southKorea = getSouthKorea(displayLanguage);
      String common = findCommon(southAfrica, southKorea);
      String remaining = removeSubstring(southAfrica, common).trim();
      int idx = StringUtil.stripAccents(remaining.toLowerCase()).indexOf("afr");
      displayName = StringUtil.capitalize(remaining.substring(idx));
    }

    return displayName;
  }

  public static String getDisplayAmerica(Locale displayLanguage) {
    String languageCode = displayLanguage.getLanguage();
    String displayName;

    if ("eo".equals(languageCode)) {
      displayName = "Ameriko";
    } else if ("sl".equals(languageCode)) {
      displayName = "Amêrika";
    } else if (Arrays.asList("hr", "sr").contains(languageCode)) {
      displayName = "Amèrika";
    } else {
      List<Locale> allLocales = Arrays.asList(Locale.getAvailableLocales());
      Locale latinAmerica =
          allLocales.stream().filter(l -> l.toString().equals("es_419")).findFirst().orElse(null);
      String latinAmericaName = latinAmerica.getDisplayCountry(displayLanguage).replace('-', ' ');
      String[] words = latinAmericaName.split(" ");

      if (words.length == 1) {
        displayName = findAmerica(words[0]);
      } else {
        displayName = findAmerica(words);
      }
    }

    return displayName;
  }

  // Antarctica
  public static String getDisplayAntarctica(Locale displayLanguage) {
    String displayName = ANTARCTICA_LOCALE.getDisplayCountry(displayLanguage);
    return displayName;
  }

  //
  // Asia
  //
  public static String getDisplayAsia(Locale displayLanguage) {
    String languageCode = displayLanguage.getLanguage();
    String displayName;

    if ("nl".equals(languageCode)) {
      displayName = "Azië";
    } else if ("sq".equals(languageCode)) {
      displayName = "Azia";
    } else {
      displayName = getAsiaPrefix(displayLanguage) + getAsiaSuffix(displayLanguage);
    }

    return displayName;
  }

  // Australia
  public static String getDisplayAustralia(Locale displayLanguage) {
    List<Locale> locales = Arrays.asList(Locale.getAvailableLocales());
    Locale australia =
        locales.stream().filter(l -> l.toString().equals("en_AU")).findFirst().orElse(null);
    String displayName = australia.getDisplayCountry(displayLanguage);
    return displayName;
  }

  public static String getDisplayEurope(Locale displayLanguage) {
    List<Locale> locales = Arrays.asList(Locale.getAvailableLocales());
    Locale europe =
        locales.stream().filter(l -> l.toString().equals("en_150")).findFirst().orElse(null);
    String displayName = europe.getDisplayCountry(displayLanguage);
    return displayName;
  }

  private static String findAmerica(String word) {
    String lowercase = StringUtil.stripAccents(word).toLowerCase();
    int idx = lowercase.indexOf("ame");
    String displayName = word.substring(idx);
    displayName = StringUtil.capitalize(displayName);
    return displayName;
  }

  private static String findAmerica(String[] words) {
    String found = words[0];

    for (String word : words) {
      String lowercase = StringUtil.stripAccents(word).toLowerCase();
      if (lowercase.startsWith("ame")) {
        found = word;
        break;
      }
    }

    return found;
  }

  private static String getAsiaPrefix(Locale displayLanguage) {
    String prefix = "A";

    if (Arrays.asList("hu", "pt", "sk", "sl").contains(displayLanguage.getLanguage())) {
      prefix = "Á";
    }

    if (Arrays.asList("hr", "sr").contains(displayLanguage.getLanguage())) {
      prefix = "Ȃ";
    }

    if (Arrays.asList("fi").contains(displayLanguage.getLanguage())) {
      prefix = "Aa";
    }

    return prefix;
  }

  private static String getAsiaSuffix(Locale displayLanguage) {
    String indonesia = getIndonesia(displayLanguage);
    String suffix = extractAsiaSuffix(indonesia);

    if (Arrays.asList("ro", "tr", "az").contains(displayLanguage.getLanguage())) {
      suffix = suffix.replace("z", "s");
    }

    if (Arrays.asList("hu").contains(displayLanguage.getLanguage())) {
      suffix = suffix.replace("z", "zs");
    }

    return suffix;
  }

  private static String getIndonesia(Locale displayLanguage) {
    return INDONESIA_LOCALE.getDisplayCountry(displayLanguage);
  }

  private static String extractAsiaSuffix(String indonesia) {
    String nesia = indonesia.substring(4);
    String suffix = nesia.substring(2);
    return suffix;
  }

  private static String getSouthAfrica(Locale displayLanguage) {
    List<Locale> locales = Arrays.asList(Locale.getAvailableLocales());
    Locale locale =
        locales.stream().filter(l -> l.toString().equals("en_ZA")).findFirst().orElse(null);
    return locale.getDisplayCountry(displayLanguage);
  }

  private static String getSouthKorea(Locale displayLanguage) {
    List<Locale> locales = Arrays.asList(Locale.getAvailableLocales());
    Locale locale =
        locales.stream().filter(l -> l.toString().equals("ko_KR")).findFirst().orElse(null);
    return locale.getDisplayCountry(displayLanguage);
  }

  private static String findCommon(String str1, String str2) {
    String[] words1 = str1.split(" ");
    String[] words2 = str2.split(" ");
    String common;
    boolean commonWords = (words1.length == words2.length) && (words1.length > 1);

    if (commonWords) {
      common = findCommonWords(words1, words2);
    } else {
      common = findLonguestCommonSequence(str1, str2);
    }

    return common;
  }

  private static String findCommonWords(String[] words1, String[] words2) {
    List<String> commonWords = new ArrayList<>();

    for (int i = 0; i < words1.length; i++) {
      if (words1[i].equals(words2[i])) {
        commonWords.add(words1[i]);
      }
    }

    return String.join(" ", commonWords);
  }

  private static String findLonguestCommonSequence(String... strings) {
    String commonStr = "";
    String smallStr = "";

    // identify smallest String
    for (String s : strings) {
      if (smallStr.length() < s.length()) {
        smallStr = s;
      }
    }

    String tempCom = "";
    char[] smallStrChars = smallStr.toCharArray();
    for (char c : smallStrChars) {
      tempCom += c;

      for (String s : strings) {
        if (!s.contains(tempCom)) {
          tempCom = "";
          break;
        }
      }

      if (tempCom != "" && tempCom.length() > commonStr.length()) {
        commonStr = tempCom;
      }
    }

    return commonStr;
  }

  private static String removeSubstring(String original, String substring) {
    String remaining = original.replaceAll(substring, "");
    return remaining;
  }
}
