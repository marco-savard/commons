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
      String lang = locale.getLanguage();
      String island = glossary.getIslandWord(locale);
      String islands = glossary.getIslandsWord(locale);
      // Console.println("{0} : {1}/{2}", lang, island, islands);

      String east = glossary.getEastWord(locale);
      String west = glossary.getWestWord(locale);
      // Console.println("{0} : {1}/{2}", lang, east, west);

      String ocean = glossary.getOceanWord(locale);
      String atlantic = glossary.getAtlanticWord(locale);
      String pacific = glossary.getPacificWord(locale);
      String indian = glossary.getIndianOceanWord(locale);
      // Console.println("{0} : {1}/{2}/{3}/{4}", lang, ocean, atlantic, pacific, indian);

      String amazonia = glossary.getAmazonia(locale);
      String indochina = glossary.getIndochina(locale);
      String azores = glossary.getAzores(locale);
      String rockies = glossary.getRockiesWord(locale);
      String places = String.join(" ", amazonia, rockies, indochina, azores);
      Console.println("{0} : {1}", lang, places);

      String hawaii = glossary.getHawaii(locale);
      String newfoundland = glossary.getNewfoundland(locale);
      places = String.join(" ", hawaii, newfoundland);
      //  Console.println("{0} : {1}", lang, places);

      String moscow = glossary.getMoscow(locale);
      String volgograd = glossary.getVolgograd(locale);
      places = String.join(" ", moscow, volgograd);
      // Console.println("{0} : {1}", lang, places);

      String yekaterinburg = glossary.getYekaterinburg(locale);
      String omsk = glossary.getOmsk(locale);
      String krasnoyarsk = glossary.getKrasnoyarsk(locale);
      String novosibirsk = glossary.getNovosibirsk(locale);
      String irkutsk = glossary.getIrkutsk(locale);
      String yakutsk = glossary.getYakutsk(locale);
      String sakhalin = glossary.getSakhalin(locale);

      String places1 = String.join(" ", yekaterinburg, omsk, krasnoyarsk, novosibirsk);
      String places2 = String.join(" ", irkutsk, yakutsk, sakhalin);
      places = places1 + " " + places2;
      // Console.println("{0} : {1}", lang, places);

    }
  }
}
