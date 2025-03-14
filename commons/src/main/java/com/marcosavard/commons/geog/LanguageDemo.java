package com.marcosavard.commons.geog;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.ling.Language;
import com.marcosavard.commons.text.UnicodeScriptProperties;

import java.util.List;
import java.util.Locale;

public class LanguageDemo {
  private static UnicodeScriptProperties unicodeScriptProperties = new UnicodeScriptProperties();

  public static void main(String[] args) {
    Locale display = Language.FRENCH.toLocale();
    List<String> languages = List.of(Locale.getISOLanguages());
    List<Locale> allLocales = List.of(Locale.getAvailableLocales());
    int count = 1;

    for (String language : languages) {
      Locale locale = Locale.forLanguageTag(language);
      String languageName = locale.getDisplayLanguage(display);
      String script = getScriptName(locale, display);
      String countries = getLanguageLocales(allLocales, language);

      Console.println("{0}) {1} {2} : {3} {4}", count++, language, languageName, script, countries);
    }
  }

  private static String getScriptName(Locale locale, Locale display) {
    String displayName = locale.getDisplayName(locale);
    char firstLetter = displayName.charAt(0);
    Character.UnicodeScript script = Character.UnicodeScript.of(firstLetter);
    String scriptName = unicodeScriptProperties.getDisplayName(script, display);
    return scriptName;
  }

  private static String getLanguageLocales(List<Locale> allLocales, String language) {
    /*
    List<String> locales =
        allLocales.stream()
            .filter(l -> language.equals(l.getLanguage()))
            .map(Locale::getCountry)
            .filter((s -> s.length() == 2))
            .distinct()
            .sorted()
            .toList();*/
    String countries = ""; //String.join(",", locales);
    return countries;
  }
}
