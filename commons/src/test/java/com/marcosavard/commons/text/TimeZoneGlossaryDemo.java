package com.marcosavard.commons.text;

import com.marcosavard.commons.debug.Console;

import java.util.List;
import java.util.Locale;

public class TimeZoneGlossaryDemo {

  public static void main(String[] args) {
    TimeZoneGlossary glossary = TimeZoneGlossary.getInstance();
    List<Locale> locales =
        List.of(
            Locale.GERMAN,
            Locale.FRENCH,
            Locale.ITALIAN,
            Locale.forLanguageTag("es"),
            Locale.forLanguageTag("pt"),
            Locale.forLanguageTag("ro"),
            Locale.forLanguageTag("nl"),
            Locale.forLanguageTag("sv"));

    for (Locale locale : locales) {
      String timeWord = glossary.getTimeWord(locale);
      String language = locale.getDisplayLanguage();
      String msg = String.join(" : ", language, timeWord);
      Console.println("  " + msg);
    }
  }
}
