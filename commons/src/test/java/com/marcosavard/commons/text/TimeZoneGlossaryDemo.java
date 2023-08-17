package com.marcosavard.commons.text;

import com.marcosavard.commons.debug.Console;

import java.util.List;
import java.util.Locale;

public class TimeZoneGlossaryDemo {
  private static final List<Locale> locales =
          List.of(
                  Locale.ENGLISH,
                  Locale.GERMAN,
                  Locale.FRENCH,
                  Locale.ITALIAN,
                  Locale.forLanguageTag("es"),
                  Locale.forLanguageTag("pt"),
                  Locale.forLanguageTag("ro"),
                  Locale.forLanguageTag("nl"),
                  Locale.forLanguageTag("sv"));

  public static void main(String[] args) {
    TimeZoneGlossary glossary = TimeZoneGlossary.getInstance();

    for (Locale locale : locales) {
      String timeWord = glossary.getTimeWord(locale);
      String language = locale.getDisplayLanguage();
      String msg = String.join(" : ", language, timeWord);
      Console.println("  " + msg);
    }
  }
}
