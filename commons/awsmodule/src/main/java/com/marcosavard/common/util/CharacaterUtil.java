package com.marcosavard.common.util;

import com.marcosavard.common.util.collection.SafeArrayList;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CharacaterUtil {

  public enum Diacritical {
    NONE,
    ACUTE,
    GRAVE,
    CIRC,
    UML,
    CEDILLA,
    TILDE
  };

  public static final char eacute = '\u00e9';

  public static final String VOYELS = "aeiouy";

  public static final String DE_DIACRITICS = "äëïöü";
  public static final String ES_DIACRITICS = "áçéíñóúü";
  public static final String FR_DIACRITICS = "àâäçéèêëïîôöùûüÿ";
  public static final String IT_DIACRITICS = "àèéìòù";

  public static final String LIGATURES = "ÆŒæœ"; // AE, OE, ae, oe

  private static Map<Locale, String> diacriticalsByLocale = null;

  public static Map<Character, List<Character>> getDiacriticals(Locale locale) {
    Map<Character, List<Character>> diacriticals = new LinkedHashMap<>();
    for (int i = 0; i < 256; i++) {
      char ch = (char) i;

      if (Character.isLowerCase(ch) && CharacaterUtil.isDiacritical(ch, locale)) {
        char letter = CharacaterUtil.stripAccent(ch);
        Diacritical diacritical = CharacaterUtil.getDiacritical(ch);
        List<Character> letterDiaciticals = diacriticals.get(letter);

        if (letterDiaciticals == null) {
          letterDiaciticals = new SafeArrayList<>('-');
          diacriticals.put(letter, letterDiaciticals);
        }

        letterDiaciticals.add(diacritical.ordinal() - 1, ch);
      }
    }

    return diacriticals;
  }

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
      diacriticalsByLocale.put(Locale.forLanguageTag("es"), ES_DIACRITICS);
      diacriticalsByLocale.put(Locale.FRENCH, FR_DIACRITICS);
      diacriticalsByLocale.put(Locale.GERMAN, DE_DIACRITICS);
      diacriticalsByLocale.put(Locale.ITALIAN, DE_DIACRITICS);
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

  public static Diacritical getDiacritical(char ch) {
    ch = Character.toLowerCase(ch);
    Diacritical diacritical = Diacritical.NONE;

    if ("ç".indexOf(ch) >= 0) {
      diacritical = Diacritical.CIRC;
    } else if ("áéíóúý".indexOf(ch) >= 0) {
      diacritical = Diacritical.ACUTE;
    } else if ("âêîôû".indexOf(ch) >= 0) {
      diacritical = Diacritical.CIRC;
    } else if ("àèìòù".indexOf(ch) >= 0) {
      diacritical = Diacritical.GRAVE;
    } else if ("äëöïüÿ".indexOf(ch) >= 0) {
      diacritical = Diacritical.UML;
    } else if ("ñ".indexOf(ch) >= 0) {
      diacritical = Diacritical.TILDE;
    }

    return diacritical;
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
