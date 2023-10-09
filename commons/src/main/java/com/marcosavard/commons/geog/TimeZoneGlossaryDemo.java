package com.marcosavard.commons.geog;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.ling.Language;

import java.util.Locale;

public class TimeZoneGlossaryDemo {

  public static void main(String[] args) {
    Locale[] locales =
        new Locale[] {
          Locale.FRENCH,
          Locale.ENGLISH,
          Locale.GERMAN,
          Locale.ITALIAN,
          Language.SPANISH.toLocale(),
          Language.PORTUGUESE.toLocale(),
          Language.ROMANIAN.toLocale(),
          Language.DUTCH.toLocale(),
          Language.SWEDISH.toLocale()
        };

    TimeZoneGlossary glossary = new TimeZoneGlossary();

    for (Locale locale : locales) {
      String word = glossary.getIndianOceanWord(locale);
      Console.println("{0} : {1}", locale.getLanguage(), word);
    }
  }
}
