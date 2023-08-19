package com.marcosavard.commons.text;

import com.marcosavard.commons.debug.Console;

import java.util.List;
import java.util.Locale;

public class TimeZoneGlossaryDemo {
  private static final List<String> codes =
      List.of("pt" /*"en", "de", "fr", "it", "es", "pt", "ro", "nl", "sv"*/);

  public static void main(String[] args) {
    TimeZoneGlossary glossary = TimeZoneGlossary.getInstance();

    for (String code : codes) {
      Locale locale = Locale.forLanguageTag(code);
      String time = glossary.getTimeWord(locale);
      String stdTime = glossary.getStandardTimeWord(locale);
      String africa = glossary.getAfricaWord(locale);
      String east = glossary.getEastWord(locale);
      String language = locale.getDisplayLanguage();
      String msg = String.join(" : ", language, time, stdTime, africa, east);
      Console.println("  " + msg);
    }
  }
}
