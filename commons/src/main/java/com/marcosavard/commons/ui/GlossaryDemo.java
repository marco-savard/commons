package com.marcosavard.commons.ui;

import com.marcosavard.commons.text.analysis.Levenshtein;
import com.marcosavard.commons.ui.res.UIManagerFacade;
import com.marcosavard.commons.util.collection.NumberSequence;
import com.marcosavard.commons.util.collection.UniqueList;

import javax.swing.*;
import java.text.DateFormatSymbols;
import java.text.MessageFormat;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class GlossaryDemo {
  private static final Locale DE = Locale.GERMAN;
  private static final Locale EN = Locale.ENGLISH;
  private static final Locale FR = Locale.FRENCH;
  private static final Locale IT = Locale.ITALIAN;
  private static final Locale ES = new Locale("ES");
  private static final Locale SV = new Locale("SV");

  public static void main(String[] args) {
    demo1();
  }

  public static void demo1() {
    Map<String, Map<Locale, String>> words = buildUiWords();
    words = buildCalendarWords();

    System.out.println(words);
    Locale lan = ES;
    System.out.println("Compare " + lan + " vs ");
    System.out.println("  IT : " + computeDistance(words, lan, IT));
    System.out.println("  ES : " + computeDistance(words, lan, ES));
    System.out.println("  FR : " + computeDistance(words, lan, FR));
    System.out.println("  EN : " + computeDistance(words, lan, EN));
    System.out.println("  DE : " + computeDistance(words, lan, DE));
    System.out.println("  SV : " + computeDistance(words, lan, SV));
  }

  private static Map<String, Map<Locale, String>> buildCalendarWords() {
    Map<String, Map<Locale, String>> words = new HashMap<>();
    String[] itMonths = new DateFormatSymbols(IT).getMonths();
    String[] esMonths = new DateFormatSymbols(ES).getMonths();
    String[] frMonths = new DateFormatSymbols(FR).getMonths();
    String[] enMonths = new DateFormatSymbols(EN).getMonths();
    String[] deMonths = new DateFormatSymbols(DE).getMonths();
    String[] svMonths = new DateFormatSymbols(SV).getMonths();

    for (int i = 0; i < 12; i++) {
      Map<Locale, String> translations = new HashMap<>();
      translations.put(IT, itMonths[i]);
      translations.put(ES, esMonths[i]);
      translations.put(FR, frMonths[i]);
      translations.put(DE, deMonths[i]);
      translations.put(SV, svMonths[i]);
      words.put(enMonths[i], translations);
    }

    DayOfWeek[] daysOfWeek = DayOfWeek.values();

    for (DayOfWeek dayOfWeek : daysOfWeek) {
      Map<Locale, String> translations = new HashMap<>();
      translations.put(IT, dayOfWeek.getDisplayName(TextStyle.FULL, IT));
      translations.put(ES, dayOfWeek.getDisplayName(TextStyle.FULL, ES));
      translations.put(FR, dayOfWeek.getDisplayName(TextStyle.FULL, FR));
      translations.put(DE, dayOfWeek.getDisplayName(TextStyle.FULL, DE));
      translations.put(SV, dayOfWeek.getDisplayName(TextStyle.FULL, SV));
      words.put(dayOfWeek.getDisplayName(TextStyle.FULL, EN), translations);
    }

    return words;
  }

  private static Map<String, Map<Locale, String>> buildUiWords() {
    Map<String, Map<Locale, String>> words = new HashMap<>();
    UIDefaults defaults = UIManagerFacade.getDefaults();
    List<String> keys = getTextKeys(defaults);
    Set<String> wordKeySet = new HashSet<>();

    for (String key : keys) {
      String value = defaults.getString(key, EN);
      value = sanitize(value);
      value = removeUppercase(value);
      boolean isWord = (value != null) && (value.indexOf(' ') == -1);

      if (value != null) {
        if (isWord) {
          Map<Locale, String> translations = words.get(value);

          if (translations == null) {
            translations = new HashMap<>();
            words.put(value, translations);
          }

          addTranslation(defaults, key, translations, FR);
          addTranslation(defaults, key, translations, IT);
          addTranslation(defaults, key, translations, ES);
          addTranslation(defaults, key, translations, DE);
          addTranslation(defaults, key, translations, SV);
        }
      }
    }

    return words;
  }

  private static void addTranslation(
      UIDefaults defaults, String key, Map<Locale, String> translations, Locale locale) {
    String value = defaults.getString(key, locale);
    value = sanitize(value);
    value = removeUppercase(value);
    translations.put(locale, value);
  }

  private static int computeDistance(Map<String, Map<Locale, String>> words, Locale l1, Locale l2) {
    int totalDistance = 0;

    Set<String> keys = words.keySet();
    for (String key : keys) {
      Map<Locale, String> translations = words.get(key);
      String wordL1 = translations.get(l1);
      String wordL2 = translations.get(l2);
      totalDistance += Levenshtein.distanceOfWords(wordL1, wordL2);
    }

    return totalDistance;
  }

  private static void printTranslations(UIDefaults defaults, String key) {
    String en = defaults.getString(key, Locale.ENGLISH);
    String fr = defaults.getString(key, Locale.FRENCH);
    String es = defaults.getString(key, new Locale("ES"));
    String it = defaults.getString(key, Locale.ITALIAN);

    String msg = MessageFormat.format("{0} {1} {2} {3}", en, fr, es, it);
    System.out.println(msg);
  }

  public static void demo2() {
    UIDefaults defaults = UIManagerFacade.getDefaults();
    List<String> keys = getTextKeys(defaults);
    Locale es = new Locale("ES");
    Locale sv = new Locale("SV");

    for (String key : keys) {
      String value = defaults.getString(key, Locale.ENGLISH);
      value = sanitize(value);
      value = removeUppercase(value);

      if ((value != null) && !value.equals("null")) {
        System.out.println(value);
        printCompare(defaults, key, value, es);
        printCompare(defaults, key, value, Locale.FRENCH);
        printCompare(defaults, key, value, Locale.ITALIAN);
        printCompare(defaults, key, value, Locale.GERMAN);
        printCompare(defaults, key, value, sv);
        System.out.println();
      }
    }
  }

  private static void printCompare(
      UIDefaults defaults, String key, String original, Locale locale) {
    String translation = sanitize(defaults.getString(key, locale));
    String line =
        MessageFormat.format(
            "  {0} : {1}", translation, Levenshtein.distanceOf(original, translation));
    System.out.println(line);
  }

  public static void demo3() {

    UIDefaults defaults = UIManagerFacade.getDefaults();
    List<String> keys = getTextKeys(defaults);
    List<String> enValues = getValues(defaults, keys, Locale.ENGLISH);
    List<String> frValues = getValues(defaults, keys, Locale.FRENCH);
    List<String> itValues = getValues(defaults, keys, Locale.ITALIAN);
    List<String> esValues = getValues(defaults, keys, new Locale("ES"));

    System.out.println(itValues);
    System.out.println(esValues);
    System.out.println(frValues);
    System.out.println(enValues);

    List<List<Integer>> itEsDistances = Levenshtein.distanceOf(itValues, esValues);
    List<List<Integer>> itFrDistances = Levenshtein.distanceOf(itValues, frValues);
    List<List<Integer>> itEnDistances = Levenshtein.distanceOf(itValues, enValues);

    System.out.println(itEsDistances);
    System.out.println(itFrDistances);
    System.out.println(itEnDistances);

    System.out.println(NumberSequence.of(itEsDistances).average());
    System.out.println(NumberSequence.of(itFrDistances).average());
    System.out.println(NumberSequence.of(itEnDistances).average());

    // Sequence.of(itEsDistances).sum();

    // double average = flat.stream().mapToInt(val -> val).average().orElse(0.0);

    // System.out.println(itValues);
    // System.out.println(esValues);

    System.out.println(keys.size());
    System.out.println("Success");
  }

  private static List<String> getTextKeys(UIDefaults defaults) {
    List<Object> properties = new ArrayList<>(defaults.keySet());
    List<String> textProperties = getTextProperties(properties);
    List<String> values = new UniqueList<>();

    for (String key : textProperties) {
      boolean contained = defaults.containsKey(key);

      if (contained) {
        String value = defaults.getString(key, Locale.ENGLISH);

        if (value != null) {
          values.add(key);
        }
      }
    }
    return values;
  }

  private static List<String> getValues(UIDefaults defaults, List<String> keys, Locale locale) {
    List<String> values = new ArrayList<>();

    for (String key : keys) {
      String value = defaults.getString(key, locale);
      value = sanitize(value);
      value = removeUppercase(value);

      if ((value != null) && !value.equals("null")) {
        values.add(value);
      }
    }

    return values;
  }

  private static String removeUppercase(String value) {
    value = (value == null) ? null : (value.equals(value.toUpperCase())) ? null : value;
    return value;
  }

  private static String sanitize(String value) {
    String sanitized = (value == null) ? null : value.replaceAll("[0-9\\(\\)\\{\\}&\\:\\.]", "");
    sanitized = "null".equals(sanitized) ? null : sanitized;
    return sanitized;
  }

  private static List<String> getTextProperties(List<Object> properties) {
    List<String> list =
        properties.stream() //
            .filter(
                k ->
                    k.toString().toLowerCase().endsWith("text")
                        || //
                        k.toString().toLowerCase().endsWith("mnemonic")) //
            .map(Object::toString) //
            .sorted() //
            .collect(Collectors.toList());
    return list;
  }
}
