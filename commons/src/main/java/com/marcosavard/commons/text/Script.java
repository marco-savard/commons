package com.marcosavard.commons.text;

import com.marcosavard.commons.geog.CurrencyGlossary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Script {
  private static final int MAX_CHARACTER = 32 * 1024;
  private static List<Script> allScripts = null;

  private static List<Locale> allLocales = Arrays.asList(Locale.getAvailableLocales());

  private static List<String> allLanguages = Arrays.asList(Locale.getISOLanguages());

  private static List<Character.UnicodeScript> scriptList = new ArrayList<>();
  private static List<Integer> scriptStartList = new ArrayList<>();
  private static Map<Character.UnicodeScript, String> localesByUnicodeScript = new HashMap<>();

  private static final ScriptToLocaleMap[] SCRIPT_TO_LOCALE_MAPS =
      new ScriptToLocaleMap[] {
        new ScriptToLocaleMap(Character.UnicodeScript.LATIN, "sr__#Latn"),
        new ScriptToLocaleMap(Character.UnicodeScript.CYRILLIC, "sr__#Cyrl"),
        new ScriptToLocaleMap(Character.UnicodeScript.ARABIC, "pa__#Arab"),
        new ScriptToLocaleMap(Character.UnicodeScript.ADLAM, "ff__#Adlm"),
        new ScriptToLocaleMap(Character.UnicodeScript.BENGALI, "mni__#Beng"),
        new ScriptToLocaleMap(Character.UnicodeScript.DEVANAGARI, "sd__#Deva"),
        new ScriptToLocaleMap(Character.UnicodeScript.GURMUKHI, "pa__#Guru"),
        new ScriptToLocaleMap(Character.UnicodeScript.OL_CHIKI, "sat__#Olck"),
        new ScriptToLocaleMap(Character.UnicodeScript.TIFINAGH, "shi__#Tfng"),
        new ScriptToLocaleMap(Character.UnicodeScript.VAI, "vai__#Vaii"),
        new ScriptToLocaleMap(Character.UnicodeScript.HAN, "zh__#Hans"),
        new ScriptToLocaleMap(Character.UnicodeScript.GREEK, "el"),
        new ScriptToLocaleMap(Character.UnicodeScript.HEBREW, "he"),
        new ScriptToLocaleMap(Character.UnicodeScript.ARMENIAN, "hy"),
        new ScriptToLocaleMap(Character.UnicodeScript.GUJARATI, "gu"),
        new ScriptToLocaleMap(Character.UnicodeScript.TAMIL, "ta"),
        new ScriptToLocaleMap(Character.UnicodeScript.TELUGU, "te"),
        new ScriptToLocaleMap(Character.UnicodeScript.KANNADA, "kn"),
        new ScriptToLocaleMap(Character.UnicodeScript.SINHALA, "si"),
        new ScriptToLocaleMap(Character.UnicodeScript.THAI, "th"),
        new ScriptToLocaleMap(Character.UnicodeScript.LAO, "lo"),
        new ScriptToLocaleMap(Character.UnicodeScript.TIBETAN, "bo"),
        new ScriptToLocaleMap(Character.UnicodeScript.GEORGIAN, "ka"),
        new ScriptToLocaleMap(Character.UnicodeScript.CHEROKEE, "chr"),
        new ScriptToLocaleMap(Character.UnicodeScript.KHMER, "km"),
        new ScriptToLocaleMap(Character.UnicodeScript.MONGOLIAN, "mn"),
        new ScriptToLocaleMap(Character.UnicodeScript.JAVANESE, "jv"),
        new ScriptToLocaleMap(Character.UnicodeScript.ETHIOPIC, "am")
      };

  private static final List<Character.UnicodeScript> ANCIENT_SCRIPTS =
      List.of(
          Character.UnicodeScript.CUNEIFORM,
          Character.UnicodeScript.EGYPTIAN_HIEROGLYPHS,
          Character.UnicodeScript.PHOENICIAN,
          Character.UnicodeScript.OGHAM,
          Character.UnicodeScript.RUNIC,
          Character.UnicodeScript.GOTHIC);

  private static final List<Character.UnicodeScript> JAPANESE_SCRIPTS =
      List.of(Character.UnicodeScript.HIRAGANA, Character.UnicodeScript.KATAKANA);

  private static final List<Character.UnicodeScript> AMERICAN_SCRIPTS =
      List.of(Character.UnicodeScript.CHEROKEE, Character.UnicodeScript.CANADIAN_ABORIGINAL);

  private Character.UnicodeScript unicodeScript;

  public static List<Script> getAllScripts() {
    if (allScripts == null) {
      init();
    }
    return allScripts;
  }

  private static void init() {
    List<Character.UnicodeScript> unicodeScripts = List.of(Character.UnicodeScript.values());
    allScripts = new ArrayList<>();
    initRanges();

    for (ScriptToLocaleMap map : SCRIPT_TO_LOCALE_MAPS) {
      localesByUnicodeScript.put(map.script, map.localeName);
    }

    for (Character.UnicodeScript unicodeScript : unicodeScripts) {
      Script script = new Script(unicodeScript);
      allScripts.add(script);
    }
  }

  private static void initRanges() {
    Character.UnicodeScript previousScript = null;
    int previousIndex = 0;

    for (int i = 0; i < MAX_CHARACTER; i++) {
      Character.UnicodeScript currentScript = Character.UnicodeScript.of(i);

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

  private Script(Character.UnicodeScript unicodeScript) {
    this.unicodeScript = unicodeScript;
  }

  public static List<Script> getAncientScripts() {
    return getAllScripts().stream().filter(s -> s.isAncient()).toList();
  }

  public static List<Script> getJapaneseScripts() {
    return getAllScripts().stream().filter(s -> s.isJapanese()).toList();
  }

  public static List<Script> getAmericanScripts() {
    return getAllScripts().stream().filter(s -> s.isAmerican()).toList();
  }

  public static List<Script> getRtlScripts() {
    return getAllScripts().stream().filter(s -> s.isRtl()).toList();
  }

  public boolean isAncient() {
    return ANCIENT_SCRIPTS.contains(unicodeScript);
  }

  public boolean isJapanese() {
    return JAPANESE_SCRIPTS.contains(unicodeScript);
  }

  public boolean isAmerican() {
    return AMERICAN_SCRIPTS.contains(unicodeScript);
  }

  public boolean isRtl() {
    byte dir = getDirectionality();
    boolean rtl = (dir == Character.DIRECTIONALITY_RIGHT_TO_LEFT);
    rtl = rtl || (dir == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC);
    return rtl;
  }

  public Character.UnicodeScript getUnicodeScript() {
    return unicodeScript;
  }

  public String getDisplayName(Locale display) {
    String displayName;

    if (Character.UnicodeScript.CUNEIFORM.equals(unicodeScript)) {
      displayName = getCuneiformName(display);
    } else if (Character.UnicodeScript.EGYPTIAN_HIEROGLYPHS.equals(unicodeScript)) {
      displayName = getEgyptianHieroglyphsName(display);
    } else if (Character.UnicodeScript.ETHIOPIC.equals(unicodeScript)) {
      CurrencyGlossary glossary = CurrencyGlossary.of(display);
      displayName = glossary.getAdjective("ET", display)[0];
    } else if (Character.UnicodeScript.GOTHIC.equals(unicodeScript)) {
      displayName = getGothicName(display);
    } else if (Character.UnicodeScript.PHOENICIAN.equals(unicodeScript)) {
      displayName = getPhoenicianName(display);
    } else if (Character.UnicodeScript.RUNIC.equals(unicodeScript)) {
      displayName = getRunicName(display);
    } else {
      displayName = getRegularDisplayName(display);
    }

    return displayName;
  }

  private String getRegularDisplayName(Locale display) {
    return getRegularDisplayName(unicodeScript, display);
  }

  private String getRegularDisplayName(Character.UnicodeScript unicodeScript, Locale display) {
    String localeCode = localesByUnicodeScript.get(unicodeScript);
    String displayName = extractName(localeCode, display);
    displayName = (displayName != null) ? displayName : extractFromEnum();
    return displayName;
  }

  private String extractName(String localeCode, Locale displayLocale) {
    Locale locale = findLocale(localeCode);
    boolean isLanguage = allLanguages.contains(localeCode);
    String displayName = (locale == null) ? null : locale.getDisplayName(displayLocale);
    String substring = isLanguage ? displayName : substring(displayName, "(", ")");
    return substring;
  }

  private Locale findLocale(String code) {
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

  private String extractFromEnum() {
    String name = unicodeScript.toString().toLowerCase().replace('_', ' ');
    return name;
  }

  public String getNativeName() {
    String localeCode = localesByUnicodeScript.get(unicodeScript);
    Locale locale =
        allLocales.stream().filter(l -> l.toString().equals(localeCode)).findFirst().orElse(null);
    return getDisplayName(locale);
  }

  public byte getDirectionality() {
    char ch = findLetter();
    byte directionality = Character.getDirectionality(ch);
    return directionality;
  }

  private char findLetter() {
    List<int[]> ranges = getScriptRanges();
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

  public List<int[]> getScriptRanges() {
    List<int[]> ranges = new ArrayList<>();
    List<Integer> indices = new ArrayList<>();

    for (int i = 0; i < scriptList.size(); i++) {
      Character.UnicodeScript script = scriptList.get(i);
      if (script.equals(unicodeScript)) {
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

  public List<String> getLanguages() {
    List<String> languageTags =
        allLanguages.stream().filter(l -> isUsing(Locale.forLanguageTag(l))).toList();
    return languageTags;
  }

  private boolean isUsing(Locale language) {
    String displayName = language.getDisplayName(language);
    char firstLetter = displayName.charAt(0);
    Character.UnicodeScript languageScript = Character.UnicodeScript.of(firstLetter);
    boolean using = unicodeScript.equals(languageScript);
    return using;
  }

  private String getCuneiformName(Locale display) {
    String displayName = getRegularDisplayName(Character.UnicodeScript.CUNEIFORM, display);
    String language = (display == null) ? null : display.getLanguage();

    if ("fr".equals(language)) {
      displayName = "cunéiforme";
    }

    return displayName;
  }

  private String getEgyptianHieroglyphsName(Locale display) {
    String displayName = getRegularDisplayName(Character.UnicodeScript.EGYPTIAN_HIEROGLYPHS, display);
    String language = (display == null) ? null : display.getLanguage();

    if ("fr".equals(language)) {
      displayName = "hiéroglyphes";
    }

    return displayName;
  }

  private String getPhoenicianName(Locale display) {
    String displayName = getRegularDisplayName(Character.UnicodeScript.PHOENICIAN, display);
    String language = (display == null) ? null : display.getLanguage();

    if ("fr".equals(language)) {
      displayName = "phénicien";
    }

    return displayName;
  }

  private String getRunicName(Locale display) {
    String displayName = getRegularDisplayName(Character.UnicodeScript.RUNIC, display);
    String language = (display == null) ? null : display.getLanguage();

    if ("fr".equals(language)) {
      displayName = "runique";
    }

    return displayName;
  }

  private String getGothicName(Locale display) {
    String displayName = getRegularDisplayName(Character.UnicodeScript.GOTHIC, display);
    String language = (display == null) ? null : display.getLanguage();

    if ("fr".equals(language)) {
      displayName = "gothique";
    }

    return displayName;
  }

  //
  // inner class
  //
  private static class ScriptToLocaleMap {
    private Character.UnicodeScript script;
    private String localeName;

    ScriptToLocaleMap(Character.UnicodeScript script, String localeName) {
      this.script = script;
      this.localeName = localeName;
    }
  }
}
