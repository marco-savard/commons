package com.marcosavard.commons.lang;

import java.text.Normalizer;

public class CharUtil {

  public static final char eacute = '\u00e9';

  public static final String VOYELS = "aeiou";
  public static final String FRENCH_DIACRITICS = "ÀÂÄÇÉÈÊËÎÏÔÖÙÛÜŸàâäçéèêëïîôöùûüÿ";
  public static final String FRENCH_LIGATURES = "ÆŒæœ"; // AE, OE, ae, oe

  public static boolean isAscii(char c) {
    boolean ascii = (c <= 127);
    return ascii;
  }

  public static boolean isDiacritical(char c) {
    boolean diacritical = Character.isLetter(c) && !isAscii(c);
    return diacritical;
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
