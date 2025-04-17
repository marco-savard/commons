package com.marcosavard.commons.text;

import com.marcosavard.commons.geog.CurrencyGlossary;

import java.lang.Character.UnicodeScript;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class UnicodeScriptProperties {
  private static final int MAX_CHARACTER = 32 * 1024;

  private static final ScriptToLocaleMap[] SCRIPT_TO_LOCALE_MAPS =
      new ScriptToLocaleMap[] {
        new ScriptToLocaleMap(UnicodeScript.LATIN, "sr__#Latn"),
        new ScriptToLocaleMap(UnicodeScript.CYRILLIC, "sr__#Cyrl"),
        new ScriptToLocaleMap(UnicodeScript.ARABIC, "pa__#Arab"),
        new ScriptToLocaleMap(UnicodeScript.ADLAM, "ff__#Adlm"),
        new ScriptToLocaleMap(UnicodeScript.BENGALI, "mni__#Beng"),
        new ScriptToLocaleMap(UnicodeScript.DEVANAGARI, "sd__#Deva"),
        new ScriptToLocaleMap(UnicodeScript.GURMUKHI, "pa__#Guru"),
        new ScriptToLocaleMap(UnicodeScript.OL_CHIKI, "sat__#Olck"),
        new ScriptToLocaleMap(UnicodeScript.TIFINAGH, "shi__#Tfng"),
        new ScriptToLocaleMap(UnicodeScript.VAI, "vai__#Vaii"),
        new ScriptToLocaleMap(UnicodeScript.HAN, "zh__#Hans"),
        new ScriptToLocaleMap(UnicodeScript.GREEK, "el"),
        new ScriptToLocaleMap(UnicodeScript.HEBREW, "he"),
        new ScriptToLocaleMap(UnicodeScript.ARMENIAN, "hy"),
        new ScriptToLocaleMap(UnicodeScript.GUJARATI, "gu"),
        new ScriptToLocaleMap(UnicodeScript.TAMIL, "ta"),
        new ScriptToLocaleMap(UnicodeScript.TELUGU, "te"),
        new ScriptToLocaleMap(UnicodeScript.KANNADA, "kn"),
        new ScriptToLocaleMap(UnicodeScript.SINHALA, "si"),
        new ScriptToLocaleMap(UnicodeScript.THAI, "th"),
        new ScriptToLocaleMap(UnicodeScript.LAO, "lo"),
        new ScriptToLocaleMap(UnicodeScript.TIBETAN, "bo"),
        new ScriptToLocaleMap(UnicodeScript.GEORGIAN, "ka"),
        new ScriptToLocaleMap(UnicodeScript.CHEROKEE, "chr"),
        new ScriptToLocaleMap(UnicodeScript.KHMER, "km"),
        new ScriptToLocaleMap(UnicodeScript.MONGOLIAN, "mn"),
        new ScriptToLocaleMap(UnicodeScript.JAVANESE, "jv"),
        new ScriptToLocaleMap(UnicodeScript.ETHIOPIC, "am")
      };

  private static final List<UnicodeScript> AMERICAN_SCRIPTS =
      List.of(UnicodeScript.CHEROKEE, UnicodeScript.CANADIAN_ABORIGINAL);

  private static final List<UnicodeScript> ANCIENT_SCRIPTS =
      List.of(
          UnicodeScript.CUNEIFORM,
          UnicodeScript.EGYPTIAN_HIEROGLYPHS,
          UnicodeScript.PHOENICIAN,
          UnicodeScript.OGHAM,
          UnicodeScript.RUNIC,
          UnicodeScript.GOTHIC);

  private static final List<UnicodeScript> JAPANESE_SCRIPTS =
      List.of(UnicodeScript.HIRAGANA, UnicodeScript.KATAKANA);

  // object fields
  private Map<UnicodeScript, String> localesByUnicodeScript = new HashMap<>();
  private List<Locale> allLocales;
  private List<String> allLanguages;
  private List<UnicodeScript> scriptList = new ArrayList<>();
  private List<Integer> scriptStartList = new ArrayList<>();

  public UnicodeScriptProperties() {
    allLocales = Arrays.asList(Locale.getAvailableLocales());
    allLanguages = Arrays.asList(Locale.getISOLanguages());
    initialize();

    for (ScriptToLocaleMap map : SCRIPT_TO_LOCALE_MAPS) {
      localesByUnicodeScript.put(map.script, map.localeName);
    }
  }

  public String getDisplayName(UnicodeScript script, Locale display) {
    String displayName;

    if (UnicodeScript.CUNEIFORM.equals(script)) {
      displayName = getCuneiformName(display);
    } else if (UnicodeScript.EGYPTIAN_HIEROGLYPHS.equals(script)) {
      displayName = getEgyptianHieroglyphsName(display);
    } else if (UnicodeScript.ETHIOPIC.equals(script)) {
      CurrencyGlossary glossary = CurrencyGlossary.of(display);
      displayName = glossary.getAdjective("ET", display)[0];
    } else if (UnicodeScript.GOTHIC.equals(script)) {
      displayName = getGothicName(display);
    } else if (UnicodeScript.PHOENICIAN.equals(script)) {
      displayName = getPhoenicianName(display);
    } else if (UnicodeScript.RUNIC.equals(script)) {
      displayName = getRunicName(display);
    } else {
      displayName = getRegularDisplayName(script, display);
    }

    return displayName;
  }

  private String getRegularDisplayName(UnicodeScript script, Locale display) {
    String localeCode = localesByUnicodeScript.get(script);
    String displayName = extractName(localeCode, display);
    displayName = (displayName != null) ? displayName : extractFromEnum(script);
    return displayName;
  }

  private String getCuneiformName(Locale display) {
    String displayName = getRegularDisplayName(UnicodeScript.CUNEIFORM, display);
    String language = (display == null) ? null : display.getLanguage();

    if ("fr".equals(language)) {
      displayName = "cunéiforme";
    }

    return displayName;
  }

  private String getEgyptianHieroglyphsName(Locale display) {
    String displayName = getRegularDisplayName(UnicodeScript.EGYPTIAN_HIEROGLYPHS, display);
    String language = (display == null) ? null : display.getLanguage();

    if ("fr".equals(language)) {
      displayName = "hiéroglyphes";
    }

    return displayName;
  }

  private String getPhoenicianName(Locale display) {
    String displayName = getRegularDisplayName(UnicodeScript.PHOENICIAN, display);
    String language = (display == null) ? null : display.getLanguage();

    if ("fr".equals(language)) {
      displayName = "phénicien";
    }

    return displayName;
  }

  private String getRunicName(Locale display) {
    String displayName = getRegularDisplayName(UnicodeScript.RUNIC, display);
    String language = (display == null) ? null : display.getLanguage();

    if ("fr".equals(language)) {
      displayName = "runique";
    }

    return displayName;
  }

  private String getGothicName(Locale display) {
    String displayName = getRegularDisplayName(UnicodeScript.GOTHIC, display);
    String language = (display == null) ? null : display.getLanguage();

    if ("fr".equals(language)) {
      displayName = "gothique";
    }

    return displayName;
  }

  public String getNativeName(UnicodeScript script) {
    String localeCode = localesByUnicodeScript.get(script);
    Locale locale =
        allLocales.stream().filter(l -> l.toString().equals(localeCode)).findFirst().orElse(null);
    return getDisplayName(script, locale);
  }

  public byte getDirectionality(UnicodeScript script) {
    char ch = findLetter(script);
    byte directionality = Character.getDirectionality(ch);
    return directionality;
  }

  private char findLetter(UnicodeScript script) {
    List<int[]> ranges = getScriptRanges(script);
    char foundLetter = 0;

    if (ranges.size() > 0) {
      for (int[] range : ranges) {
        if (range.length >= 2) {
          for (int i = range[0]; i < range[1]; i++) {
            char ch = (char) i;
            if (Character.isLetter(ch)) {
              foundLetter = ch;
              break;
            }
          }

          if (foundLetter != 0) {
            break;
          }
        }
      }
    }

    return foundLetter;
  }

  public static List<String> getLanguagesForScript(
      List<String> allLanguages, Character.UnicodeScript script, Locale displayLocale) {
    List<String> languageTags = List.of();
     //   allLanguages.stream().filter(l -> isUsing(script, Locale.forLanguageTag(l))).toList();
    return languageTags;
  }

  private static boolean isUsing(Character.UnicodeScript script, Locale language) {
    String displayName = language.getDisplayName(language);
    char firstLetter = displayName.charAt(0);
    Character.UnicodeScript languageScript = Character.UnicodeScript.of(firstLetter);
    boolean using = script.equals(languageScript);
    return using;
  }

  public List<UnicodeScript> getScriptList() {
    return scriptList;
  }

  public List<Integer> getScriptStartList() {
    return scriptStartList;
  }

  public List<int[]> getScriptRanges(UnicodeScript givenScript) {
    List<int[]> ranges = new ArrayList<>();
    List<Integer> indices = new ArrayList<>();

    for (int i = 0; i < scriptList.size(); i++) {
      UnicodeScript script = scriptList.get(i);
      if (script.equals(givenScript)) {
        indices.add(i);
      }
    }

    for (int i : indices) {
      if (i < scriptStartList.size() - 1) {
        int start = scriptStartList.get(i);
        int end = scriptStartList.get(i + 1) - 1;
        int[] range = new int[] {start, end};
        ranges.add(range);
      }
    }

    return ranges;
  }

  //
  // private methods
  //
  private void initialize() {
    UnicodeScript previousScript = null;
    int previousIndex = 0;

    for (int i = 0; i < MAX_CHARACTER; i++) {
      UnicodeScript currentScript = UnicodeScript.of(i);

      if (!currentScript.equals(previousScript)) {
        if (previousScript != null) {
          scriptList.add(previousScript);
          scriptStartList.add(previousIndex);
        }

        previousIndex = i;
        previousScript = currentScript;
      }
    }
  }

  private String extractName(String localeCode, Locale displayLocale) {
    Locale locale = findLocale(allLocales, localeCode);
    boolean isLanguage = allLanguages.contains(localeCode);
    String displayName = (locale == null) ? null : locale.getDisplayName(displayLocale);
    String substring = isLanguage ? displayName : substring(displayName, "(", ")");
    return substring;
  }

  private Locale findLocale(List<Locale> allLocales, String code) {
    Locale foundLocale =
        (code == null)
            ? null
            : allLocales.stream().filter(l -> code.equals(l.toString())).findFirst().orElse(null);
    return foundLocale;
  }

  private static String substring(String givenString, String prefix, String suffix) {
    int begin = (givenString == null) ? -1 : givenString.indexOf(prefix);
    int end = (givenString == null) ? -1 : givenString.lastIndexOf(suffix);
    String substring = (begin == -1) || (end == -1) ? null : givenString.substring(begin + 1, end);
    return substring;
  }

  private String extractFromEnum(UnicodeScript script) {
    String name = script.toString().toLowerCase().replace('_', ' ');
    return name;
  }

  public List<UnicodeScript> findScriptsByDirectionality(byte directionality) {
    int count = Character.UnicodeScript.values().length;
    List<UnicodeScript> foundScripts = new ArrayList<>();

    for (int i = 0; i < count; i++) {
      Character.UnicodeScript script = Character.UnicodeScript.values()[i];
      if (directionality == getDirectionality(script)) {
        foundScripts.add(script);
      }
    }

    return foundScripts;
  }

  public List<UnicodeScript> getAmericanScripts() {
    return AMERICAN_SCRIPTS;
  }

  public List<UnicodeScript> getAncientScripts() {
    return ANCIENT_SCRIPTS;
  }

  public List<UnicodeScript> getJapaneseScripts() {
    return JAPANESE_SCRIPTS;
  }


  //
  // inner class
  //
  private static class ScriptToLocaleMap {
    private UnicodeScript script;
    private String localeName;

    ScriptToLocaleMap(UnicodeScript script, String localeName) {
      this.script = script;
      this.localeName = localeName;
    }
  }
}
