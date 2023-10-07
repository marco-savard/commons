package com.marcosavard.commons.text.locale;

import com.marcosavard.commons.debug.Console;

import java.util.List;
import java.util.Locale;

public class GlossaryDemo {

  public static void main(String[] args) {
    Locale[] locales =
        new Locale[] {
          // Locale.ENGLISH,
          Locale.FRENCH,
          // new Locale("ff"),
          //  new Locale("ln"),
          //  new Locale("pt")
        };

    for (Locale locale : locales) {
      Glossary glossary = Glossary.of(locale);
      printCategory(glossary, Glossary.Category.MONTH);
      printCategory(glossary, Glossary.Category.WEEK_DAY);
      printCategory(glossary, Glossary.Category.SCRIPT);
      printCategory(glossary, Glossary.Category.EUROPEAN_LANGUAGE);
      printCategory(glossary, Glossary.Category.EUROPEAN_COUNTRY);
      printCategory(glossary, Glossary.Category.CURRENCY);
      printCategory(glossary, Glossary.Category.TIMEZONE);
    }
  }

  private static void printCategory(Glossary glossary, Glossary.Category category) {
    List<String> words = glossary.getWords(category);
    Console.println(String.join(", ", words));
  }
}
