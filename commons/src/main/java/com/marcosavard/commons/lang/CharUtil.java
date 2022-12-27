package com.marcosavard.commons.lang;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CharUtil {

  public static final char eacute = '\u00e9';

  public static final String VOYELS = "aeiou";
  public static final String FRENCH_DIACRITICS = "ÀÂÄÇÉÈÊËÎÏÔÖÙÛÜŸàâäçéèêëïîôöùûüÿ";
  public static final String LIGATURES = "ÆŒæœ"; // AE, OE, ae, oe

  private static Map<Locale, String> diacriticalsByLocale = null;

  public static boolean isAscii(char c) {
    boolean ascii = (c <= 127);
    return ascii;
  }

  public static boolean isDiacritical(char c, Locale locale) {
    Map<Locale, String> diacriticalsByLocale = getDiacriticalsByLocale();
    String diacriticals = diacriticalsByLocale.get(locale);
    boolean diacritical = (diacriticals != null) ? diacriticals.indexOf(c) >= 0 : isDiacritical(c);
    return diacritical;
  }

  public static boolean isDiacritical(char c) {
    boolean diacritical = Character.isLetter(c) && !isAscii(c);
    return diacritical;
  }

  private static Map<Locale, String> getDiacriticalsByLocale() {
    if (diacriticalsByLocale == null) {
      diacriticalsByLocale = new HashMap<>();
      diacriticalsByLocale.put(Locale.FRENCH, FRENCH_DIACRITICS);
    }

    return diacriticalsByLocale;
  }

  public static boolean isLigature(char c) {
    boolean ligature = LIGATURES.indexOf(c) >= 0;
    return ligature;
  }

  public static boolean isVowel(char c) {
    c = Character.toLowerCase(stripAccent(c));
    boolean voyel = (VOYELS.indexOf(c) >= 0);
    return voyel;
  }

  public static char stripAccent(char c) {
    String normalized = Normalizer.normalize(Character.toString(c), Normalizer.Form.NFD);
    String replaced = normalized.replaceAll("[^\\p{ASCII}]", "");
    char stripped = (replaced.length() == 0) ? '\0' : replaced.charAt(0);
    return stripped;
  }

  public static String getDiacriticalMark(char ch) {
    String diacriticalMark = "";

    if ("ç".indexOf(ch) >= 0) {
      diacriticalMark = "cedil";
    } else if ("áéíóúý".indexOf(ch) >= 0) {
      diacriticalMark = "acute";
    } else if ("âêîôû".indexOf(ch) >= 0) {
      diacriticalMark = "circum";
    } else if ("àèìòù".indexOf(ch) >= 0) {
      diacriticalMark = "grave";
    } else if ("äëöïüÿ".indexOf(ch) >= 0) {
      diacriticalMark = "uml";
    }

    return diacriticalMark;
  }
}
