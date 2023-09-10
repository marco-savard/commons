package com.marcosavard.commons.geog;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.lang.StringUtil;
import com.marcosavard.commons.ling.Language;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class CurrencyNameDemo {
  private static final List<String> DOLLARS = List.of("AUD", "CAD", "NZD", "USD");
  private static final List<String> FRANCS = List.of("BEF", "LUF"); // not CHF"

  private static final List<String> FRANKEN = List.of("CHF", "CHW");

  private static final List<String> FLORINS = List.of("SRG", "NLG");

  private static final List<String> KRONES = List.of("DKK", "NOK"); // not "SEK", isl

  private static final List<String> MARKS = List.of("DEM", "BAM");
  private static final List<String> POUNDS = List.of("GBP", "IEP", "GIP"); // not: "FKP" cyp, tur
  private static final List<String> PESOS = List.of("ARS", "CLP", "COP", "MXN", "GWP");
  private static final List<String> RUBLES = List.of("RUB", "BYN"); // not "SEK", isl

  // , , ,, mark, franK, krOOn

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

    // print currency
    for (Locale locale : locales) {

      Console.print(locale.getLanguage() + " : ");
      Console.print(findDollar(locale) + "  ");
      Console.print(findFranc(locale) + "  ");
      Console.print(findFlorin(locale) + "  ");
      Console.print(findKrones(locale) + "  ");
      Console.print(findMark(locale) + "  ");
      Console.print(findPound(locale) + "  ");
      Console.print(findPeso(locale) + "  ");
      Console.print(findRubles(locale) + "  ");
      Console.println();
    }

    // print country
    for (Locale locale : locales) {
      Console.println(locale.getLanguage().toUpperCase());

      // printEuropean(locale);
      // printAsian(locale);
      // printAmerican(locale);
      // printOceania(locale);
      printAfrican(locale);

      /*
            printAustralian(locale);
            printCanadian(locale);




            //  printSouthKorean(locale);
            //  printNorthKorean(locale);
      */
      Console.println();
    }
  }

  private static void printEuropean(Locale locale) {
    Console.println(getPortuguese(locale));
    Console.println(getSpanish(locale));
    Console.println(getItalian(locale));
    Console.println(getFrench(locale));

    Console.println(getDutch(locale));
    Console.println(getGerman(locale));
    Console.println(getBelgian(locale));
    Console.println(getSwiss(locale));
    Console.println(getAustrian(locale));

    Console.println(getDanish(locale));
    Console.println(getIcelandic(locale));
    Console.println(getNorvegian(locale));
    Console.println(getSwedish(locale));
    Console.println(getFinnish(locale));

    Console.println(getEstonian(locale));
    Console.println(getLatvian(locale));
    Console.println(getLithuanian(locale));

    Console.println(getPolish(locale));
    Console.println(getCzech(locale));
    Console.println(getSlovak(locale));
    Console.println(getHungarian(locale));
    Console.println(getRomanian(locale));

    Console.println(getCroatian(locale));
    Console.println(getSerbian(locale));
    Console.println(getBulgarian(locale));
    Console.println(getAlbanian(locale));
    Console.println(getGreek(locale));

    Console.println(getBielorussian(locale));
    Console.println(getUkranian(locale));
    Console.println(getRussian(locale));
    Console.println(getGeorgian(locale));
    Console.println(getArmenian(locale));
  }

  private static void printAsian(Locale locale) {
    // Console.println(getTurkish(locale));
    // Console.println(getAzerbaijani(locale));
    //  Console.println(getIranian(locale));

    // Console.println(getPakistani(locale));
    // Console.println(getIndian(locale));
    // Console.println(getBangladeshi(locale));

    Console.println(getMyanmar(locale));
    Console.println(getThai(locale));
    Console.println(getCambodian(locale));
    Console.println(getLaotian(locale));
    Console.println(getVietnamese(locale));

    Console.println(getChinese(locale));
    Console.println(getNorthKorean(locale));
    Console.println(getSouthKorean(locale));
    Console.println(getJaponese(locale));
    Console.println(getPhilipino(locale));
    Console.println(getIndonesian(locale));
  }

  private static void printAmerican(Locale locale) {
    Console.println(getCanadian(locale));
    Console.println(getMexican(locale));

    Console.println(getColombian(locale));
    Console.println(getVenezualian(locale));
    Console.println(getPeruvian(locale));
    Console.println(getBolivian(locale));
    Console.println(getChilian(locale));
    Console.println(getArgentine(locale));
    Console.println(getUruguayan(locale));
    Console.println(getBrazilian(locale));
  }

  private static void printOceania(Locale locale) {
    Console.println(getAustralian(locale));
    Console.println(getNewZelander(locale));
  }

  private static void printAfrican(Locale locale) {
    Console.println(getEgyptian(locale));
    Console.println(getTunisian(locale));
    Console.println(getAlgerian(locale));
    Console.println(getMoroccan(locale));
  }

  private static String findDollar(Locale locale) {
    return findCurrency(locale, DOLLARS);
  }

  private static String findPound(Locale locale) {
    return findCurrency(locale, POUNDS);
  }

  private static String findPeso(Locale locale) {
    return findCurrency(locale, PESOS);
  }

  private static String findFranc(Locale locale) {
    return findCurrency(locale, FRANCS);
  }

  private static String findFranken(Locale locale) {
    return findCurrency(locale, FRANKEN);
  }

  private static String findFlorin(Locale locale) {
    return findCurrency(locale, FLORINS);
  }

  private static String findKrona(Locale locale) {
    return "krona";
  }

  private static String findKrones(Locale locale) {
    return findCurrency(locale, KRONES);
  }

  private static String findRubles(Locale locale) {
    return findCurrency(locale, RUBLES);
  }

  private static String findMark(Locale locale) {
    return findCurrency(locale, MARKS);
  }

  private static String findCurrency(Locale locale, List<String> codes) {
    List<Currency> currencies = getAvailableCurrencies();
    List<String> previous = null;

    for (String code : codes) {
      Currency currency = findCurrency(currencies, code);
      String currencyName = currency.getDisplayName(locale).toLowerCase();
      List<String> current = List.of(currencyName.replace("-", " ").split("\\s+"));
      previous = findCommon(previous, current);
    }

    return WordUtil.findLongestWord(previous);
  }

  private static List<String> findCommon(List<String> previous, List<String> words) {
    if (words.size() == 1) {
      String word = words.get(0);
      word = (previous == null) ? word : findLonguestCommonSequence(previous.get(0), word);
      return List.of(word);
    } else {
      return findCommonWords(previous, words);
    }
  }

  private static List<String> findCommonWords(List<String> previous, List<String> words) {
    List<String> commonWords = new ArrayList<>();
    if (previous == null) {
      commonWords.addAll(words);
    } else {
      for (String word : words) {
        if (previous.contains(word)) {
          commonWords.add(word);
        }
      }
    }

    return commonWords;
  }

  private static String findCurrencyOld(Locale locale, List<String> codes) {
    String common = null;
    List<Currency> currencies = getAvailableCurrencies();
    for (String code : codes) {
      Currency currency = findCurrency(currencies, code);
      String currencyName = currency.getDisplayName(locale).toLowerCase();
      common = (common == null) ? currencyName : findLonguestCommonSequence(common, currencyName);
      // Console.println(common);
    }

    return common;
  }

  private static String findLonguestCommonSequence(String s1, String s2) {
    String common = WordUtil.findLonguestCommonSequence(s1, s2).trim();
    common = WordUtil.removeShortWords(common, 3);
    return common;
  }

  private static String extractName(Currency currency, Locale locale, String... extras) {
    String extracted = currency.getDisplayName(locale).toLowerCase();
    for (String extra : extras) {
      extracted = extracted.replace(extra, "").trim();
    }

    return extracted;
  }

  // EUROPE
  private static String getPortugueseOld(Locale locale) {
    return Locale.forLanguageTag("pt").getDisplayLanguage(locale);
  }

  private static String getPortuguese(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String esp = findCurrency(currencies, "PTE").getDisplayName(locale).toLowerCase();
    String pesata = findEscudo(locale);
    String remaining = replaceFirstIgnoreAccents(esp, pesata, "").trim();
    return remaining;
  }

  private static String getSpanishOld(Locale locale) {
    return Locale.forLanguageTag("es").getDisplayLanguage(locale);
  }

  private static String getSpanish(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String esp = findCurrency(currencies, "ESP").getDisplayName(locale).toLowerCase();
    String pesata = findPeseta(locale);
    String remaining = replaceFirstIgnoreAccents(esp, pesata, "").trim();
    return remaining;
  }

  private static String getFrenchOld(Locale locale) {
    return Locale.forLanguageTag("fr").getDisplayLanguage(locale);
  }

  private static String getFrench(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String aud = findCurrency(currencies, "FRF").getDisplayName(locale).toLowerCase();
    String cad = findCurrency(currencies, "MGF").getDisplayName(locale).toLowerCase();
    String remaining = extractDelta(aud, cad);
    return remaining;
  }

  private static String getItalianOld(Locale locale) {
    return Locale.forLanguageTag("it").getDisplayLanguage(locale);
  }

  private static String getItalian(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String esp = findCurrency(currencies, "ITL").getDisplayName(locale).toLowerCase();
    String pesata = findLira(locale);
    String remaining = replaceFirstIgnoreAccents(esp, pesata, "").trim();
    return remaining;
  }

  private static String getDutch(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String nlg = findCurrency(currencies, "NLG").getDisplayName(locale).toLowerCase();
    String florin = findFlorin(locale);
    String remaining = nlg.replaceFirst(florin, "").trim();
    return remaining;
  }

  private static String getDutchOld(Locale locale) {
    return Locale.forLanguageTag("nl").getDisplayLanguage(locale);
  }

  private static String getGerman(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String dem = findCurrency(currencies, "DEM").getDisplayName(locale).toLowerCase();
    String mark = findMark(locale);
    String remaining = dem.replaceFirst(mark, "").trim();
    return remaining;
  }

  private static String getBelgian(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String aud = findCurrency(currencies, "BEF").getDisplayName(locale).toLowerCase();
    String cad = findCurrency(currencies, "LUF").getDisplayName(locale).toLowerCase();
    String remaining = extractDelta(aud, cad);
    return remaining;
  }

  private static String getSwiss(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String chf = findCurrency(currencies, "CHF").getDisplayName(locale).toLowerCase();
    String franc = locale.getLanguage().equals("de") ? findFranken(locale) : findFranc(locale);
    String remaining = chf.replaceFirst(franc, "").trim();
    return remaining;
  }

  private static String getAustrian(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String esp = findCurrency(currencies, "ATS").getDisplayName(locale).toLowerCase();
    String pesata = findSchilling(locale);
    String remaining = replaceFirstIgnoreAccents(esp, pesata, "").trim();
    return remaining;
  }

  private static String getDanish(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String nlg = findCurrency(currencies, "DKK").getDisplayName(locale).toLowerCase();
    String florin = findKrones(locale);
    String remaining = nlg.replaceFirst(florin, "").trim();
    return remaining;
  }

  private static String getIcelandic(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String nlg = findCurrency(currencies, "ISK").getDisplayName(locale).toLowerCase();
    String remaining = nlg.replaceFirst(findKrones(locale), "").trim();
    remaining = replaceFirstIgnoreAccents(remaining, findKrona(locale), "").trim();
    return remaining;
  }

  private static String getNorvegian(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String nlg = findCurrency(currencies, "NOK").getDisplayName(locale).toLowerCase();
    String florin = findKrones(locale);
    String remaining = nlg.replaceFirst(florin, "").trim();
    return remaining;
  }

  private static String getSwedish(Locale locale) {
    String language = locale.getLanguage();
    List<Currency> currencies = getAvailableCurrencies();
    String nlg = findCurrency(currencies, "SEK").getDisplayName(locale).toLowerCase();
    String krone;

    if (language.equals("en")) {
      krone = "krona";
    } else {
      krone = findKrones(locale);
    }
    String remaining = nlg.replaceFirst(krone, "").trim();
    return remaining;
  }

  private static String getFinnish(Locale locale) {
    String language = locale.getLanguage();
    List<Currency> currencies = getAvailableCurrencies();
    String nlg = findCurrency(currencies, "FIM").getDisplayName(locale).toLowerCase();
    String mark;

    if (List.of("en", "it", "nl").contains(language)) {
      mark = "markka";
    } else if (language.equals("pt")) {
      mark = "marca";
    } else {
      mark = findMark(locale);
    }
    String remaining = nlg.replaceFirst(mark, "").trim();
    return remaining;
  }

  private static String getEstonian(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String nlg = findCurrency(currencies, "EEK").getDisplayName(locale).toLowerCase();
    String florin = findKrones(locale);
    String remaining = nlg.replaceFirst(florin, "").trim();
    remaining = remaining.replaceFirst("kroon", "").trim();
    return remaining;
  }

  private static String getLatvian(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String nlg = findCurrency(currencies, "LVL").getDisplayName(locale).toLowerCase();
    String florin = "lats";
    String remaining = nlg.replaceFirst(florin, "").trim();
    return remaining;
  }

  private static String getLithuanian(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String nlg = findCurrency(currencies, "LTL").getDisplayName(locale).toLowerCase();
    String florin = findLitas(locale);
    String remaining = nlg.replaceFirst(florin, "").trim();
    return remaining;
  }

  private static String getPolish(Locale locale) {
    String language = locale.getLanguage();
    String polish;

    if (List.of("es", "ro").contains(language)) {
      polish = Locale.forLanguageTag("pl").getDisplayLanguage(locale);
    } else {
      List<Currency> currencies = getAvailableCurrencies();
      String esp = findCurrency(currencies, "PLN").getDisplayName(locale).toLowerCase();
      String zloty = findZloty(locale);
      polish = replaceFirstIgnoreAccents(esp, zloty, "").trim();
    }

    return polish;
  }

  private static String getCzech(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String nlg = findCurrency(currencies, "CZK").getDisplayName(locale).toLowerCase();
    String florin = findKrones(locale);
    String remaining = nlg.replaceFirst(florin, "").trim();
    remaining = remaining.replaceFirst("koruna", "").trim();
    return remaining;
  }

  private static String getSlovak(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String nlg = findCurrency(currencies, "SKK").getDisplayName(locale).toLowerCase();
    String florin = findKrones(locale);
    String remaining = nlg.replaceFirst(florin, "").trim();
    remaining = remaining.replaceFirst("koruna", "").trim();
    return remaining;
  }

  private static String getHungarian(Locale locale) {
    String language = locale.getLanguage();
    String hungarian;

    if (List.of("ro").contains(language)) {
      hungarian = Locale.forLanguageTag("hu").getDisplayLanguage(locale);
    } else {
      List<Currency> currencies = getAvailableCurrencies();
      hungarian = findCurrency(currencies, "HUF").getDisplayName(locale).toLowerCase();
      for (String replaced : List.of("forint", "fiorino", "florim")) {
        hungarian = hungarian.replaceFirst(replaced, "").trim();
      }
    }

    hungarian = WordUtil.removeShortWords(hungarian, 2);
    return hungarian;
  }

  private static String getRomanian(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String nlg = findCurrency(currencies, "RON").getDisplayName(locale).toLowerCase();
    String result = nlg.replaceFirst("leu", "").trim();
    return result;
  }

  private static String getCroatian(Locale locale) {
    String language = locale.getLanguage();
    String result;

    if (List.of("es", "ro").contains(language)) {
      result = Locale.forLanguageTag("hr").getDisplayLanguage(locale);
    } else {
      List<Currency> currencies = getAvailableCurrencies();
      String nlg = findCurrency(currencies, "HRK").getDisplayName(locale).toLowerCase();
      result = nlg.replaceFirst("kuna", "").trim();
    }

    return result;
  }

  private static String getSerbian(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String nlg = findCurrency(currencies, "RSD").getDisplayName(locale).toLowerCase();
    String result = nlg.replaceFirst("dinar", "").trim();
    result = WordUtil.removeShortWords(result, 2);

    return result;
  }

  private static String getBulgarian(Locale locale) {
    String language = locale.getLanguage();
    String result;

    if (List.of("ro").contains(language)) {
      result = Locale.forLanguageTag("bg").getDisplayLanguage(locale);
    } else {
      List<Currency> currencies = getAvailableCurrencies();
      String nlg = findCurrency(currencies, "BGN").getDisplayName(locale).toLowerCase();
      result = nlg.replaceFirst("lev", "").trim();
      result = result.replaceFirst("lew", "").trim();
      result = WordUtil.removeShortWords(result, 2);
    }

    return result;
  }

  private static String getAlbanian(Locale locale) {
    String language = locale.getLanguage();
    String result;

    if (List.of("es", "ro").contains(language)) {
      result = Locale.forLanguageTag("sq").getDisplayLanguage(locale);
    } else {
      List<Currency> currencies = getAvailableCurrencies();
      String nlg = findCurrency(currencies, "ALL").getDisplayName(locale).toLowerCase();
      result = nlg.replaceFirst("lek", "").trim();
      result = WordUtil.removeShortWords(result, 2);
    }

    return result;
  }

  private static String getGreek(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String nlg = findCurrency(currencies, "GRD").getDisplayName(locale).toLowerCase();
    String result = nlg.replaceFirst("drachm", "").trim();
    result = result.replaceFirst("dracm", "").trim();
    result = result.replaceFirst("drahm", "").trim();
    result = WordUtil.removeShortWords(result, 2);
    return result;
    // return Locale.forLanguageTag("el").getDisplayLanguage(locale);
  }

  private static String getBielorussian(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String nlg = findCurrency(currencies, "BYN").getDisplayName(locale).toLowerCase();
    String result = nlg.replaceFirst(findRubles(locale), "").trim();
    result = WordUtil.removeShortWords(result, 2);
    return result;
  }

  private static String getUkranian(Locale locale) {
    String language = locale.getLanguage();
    String result;

    if (List.of("es", "ro").contains(language)) {
      result = Locale.forLanguageTag("uk").getDisplayLanguage(locale);
    } else {
      List<Currency> currencies = getAvailableCurrencies();
      result = findCurrency(currencies, "UAH").getDisplayName(locale).toLowerCase();
      result = result.replaceFirst("hryvnia", "").trim();
      result = result.replaceFirst("hrywnja", "").trim();
      result = result.replaceFirst("grivn", "").trim();
      result = WordUtil.removeShortWords(result, 3);
    }

    return result;
  }

  private static String getRussian(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String nlg = findCurrency(currencies, "RUB").getDisplayName(locale).toLowerCase();
    String result = nlg.replaceFirst(findRubles(locale), "").trim();
    result = WordUtil.removeShortWords(result, 2);
    return result;
  }

  private static String getGeorgian(Locale locale) {
    String language = locale.getLanguage();
    String result;

    if (List.of("es").contains(language)) {
      result = Locale.forLanguageTag("ka").getDisplayLanguage(locale);
    } else {
      List<Currency> currencies = getAvailableCurrencies();
      result = findCurrency(currencies, "GEL").getDisplayName(locale).toLowerCase();
      result = result.replaceFirst("lari", "").trim();
      result = WordUtil.removeShortWords(result, 2);
    }

    return result;
  }

  private static String getArmenian(Locale locale) {
    String language = locale.getLanguage();
    String result;

    if (List.of("es").contains(language)) {
      result = Locale.forLanguageTag("hy").getDisplayLanguage(locale);
    } else {
      List<Currency> currencies = getAvailableCurrencies();
      result = findCurrency(currencies, "AMD").getDisplayName(locale).toLowerCase();
      result = result.replaceFirst("dram", "").trim();
      result = WordUtil.removeShortWords(result, 2);
    }

    return result;
  }

  private static String getTurkish(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String result = findCurrency(currencies, "TRY").getDisplayName(locale).toLowerCase();
    result = result.replaceFirst("lir", "").trim();
    result = result.replaceFirst("livr", "").trim();
    result = WordUtil.removeShortWords(result, 2);
    return result;
    //  return Locale.forLanguageTag("tr").getDisplayLanguage(locale);
  }

  private static String getAzerbaijani(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String result = findCurrency(currencies, "AZN").getDisplayName(locale).toLowerCase();
    result = result.replaceFirst("manat", "").trim();
    result = WordUtil.removeShortWords(result, 2);
    return result;
  }

  private static String getIranian(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String result = findCurrency(currencies, "IRR").getDisplayName(locale).toLowerCase();
    result = result.replaceFirst("rial", "").trim();
    result = result.replaceFirst("riyal", "").trim();
    result = WordUtil.removeShortWords(result, 2);
    return result;
  }

  private static String getPakistani(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String result = findCurrency(currencies, "PKR").getDisplayName(locale).toLowerCase();
    result = result.replaceFirst("roep", "").trim();
    result = result.replaceFirst("rup", "").trim();
    result = result.replaceFirst("roup", "").trim();
    result = WordUtil.removeShortWords(result, 3);
    return result;
  }

  private static String getIndian(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String result = findCurrency(currencies, "INR").getDisplayName(locale).toLowerCase();
    result = result.replaceFirst("roep", "").trim();
    result = result.replaceFirst("rup", "").trim();
    result = result.replaceFirst("roup", "").trim();
    result = WordUtil.removeShortWords(result, 3);
    return result;
  }

  private static String getBangladeshi(Locale locale) {
    String language = locale.getLanguage();
    String result;

    if (List.of("es").contains(language)) {
      result = Locale.forLanguageTag("bn").getDisplayLanguage(locale);
    } else {
      List<Currency> currencies = getAvailableCurrencies();
      result = findCurrency(currencies, "BDT").getDisplayName(locale).toLowerCase();
      result = result.replaceFirst("taka", "").trim();
      result = WordUtil.removeShortWords(result, 3);
    }

    return result;
  }

  private static String getMyanmar(Locale locale) {
    String language = locale.getLanguage();
    String result;

    if (List.of("es").contains(language)) {
      result = Locale.forLanguageTag("my").getDisplayLanguage(locale);
    } else {
      List<Currency> currencies = getAvailableCurrencies();
      result = findCurrency(currencies, "MMK").getDisplayName(locale).toLowerCase();
      result = result.replaceFirst("kyat", "").trim();
      result = result.replaceFirst("quiate", "").trim();
      result = WordUtil.removeShortWords(result, 2);
    }

    return result;
  }

  private static String getChinese(Locale locale) {
    String language = locale.getLanguage();
    String result;

    if (List.of("es", "de").contains(language)) {
      result = Locale.forLanguageTag("zh").getDisplayLanguage(locale);
    } else {
      List<Currency> currencies = getAvailableCurrencies();
      result = findCurrency(currencies, "CNY").getDisplayName(locale).toLowerCase();
      result = result.replaceFirst("yuan", "").trim();
      result = result.replaceFirst("renminbi", "").trim();
      result = WordUtil.removeShortWords(result, 2);
    }

    return result;
  }

  private static String getThai(Locale locale) {
    String language = locale.getLanguage();
    String result;

    if (List.of("es").contains(language)) {
      result = Locale.forLanguageTag("th").getDisplayLanguage(locale);
    } else {
      List<Currency> currencies = getAvailableCurrencies();
      String nlg = findCurrency(currencies, "THB").getDisplayName(locale).toLowerCase();
      result = nlg.replaceFirst("baht", "").trim();
    }

    return result;
  }

  private static String getCambodian(Locale locale) {
    String language = locale.getLanguage();
    String result;

    if (List.of("es").contains(language)) {
      result = "camboyano"; // Locale.forLanguageTag("km").getDisplayLanguage(locale); // ?
    } else {
      List<Currency> currencies = getAvailableCurrencies();
      result = findCurrency(currencies, "KHR").getDisplayName(locale).toLowerCase();
      result = result.replaceFirst("riel", "").trim();
      result = WordUtil.removeShortWords(result, 3);
    }

    return result;
  }

  private static String getLaotian(Locale locale) {
    String language = locale.getLanguage();
    String result;

    if (List.of("es").contains(language)) {
      result = Locale.forLanguageTag("lo").getDisplayLanguage(locale);
    } else {
      List<Currency> currencies = getAvailableCurrencies();
      result = findCurrency(currencies, "LAK").getDisplayName(locale).toLowerCase();
      result = result.replaceFirst("kip", "").trim();
      result = WordUtil.removeShortWords(result, 3);
    }

    return result;
  }

  private static String getVietnamese(Locale locale) {
    String language = locale.getLanguage();
    String result;

    if (List.of("es").contains(language)) {
      result = Locale.forLanguageTag("vi").getDisplayLanguage(locale);
    } else {
      List<Currency> currencies = getAvailableCurrencies();
      result = findCurrency(currencies, "VND").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "dong", "").trim();
      result = WordUtil.removeShortWords(result, 2);
    }

    return result;
  }

  private static String getNorthKorean(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String kpw = extractName(findCurrency(currencies, "KPW"), locale, "won");
    return kpw;
  }

  private static String getSouthKorean(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String krw = extractName(findCurrency(currencies, "KRW"), locale, "won");
    return krw;
  }

  private static String getKorean(Locale locale) {
    return Locale.forLanguageTag("ko").getDisplayLanguage(locale);
  }

  private static String getJaponese(Locale locale) {
    String language = locale.getLanguage();
    String result;

    if (List.of("es").contains(language)) {
      result = Locale.forLanguageTag("ja").getDisplayLanguage(locale);
    } else {
      List<Currency> currencies = getAvailableCurrencies();
      result = findCurrency(currencies, "JPY").getDisplayName(locale).toLowerCase();
      result = result.replaceFirst("yen", "").trim();
      result = result.replaceFirst("iene", "").trim();
      result = WordUtil.removeShortWords(result, 2);
    }

    return result;
  }

  private static String getPhilipino(Locale locale) {
    String language = locale.getLanguage();
    String result;

    if (List.of("").contains(language)) {
      result = Locale.forLanguageTag("fil").getDisplayLanguage(locale);
    } else {
      List<Currency> currencies = getAvailableCurrencies();
      result = findCurrency(currencies, "PHP").getDisplayName(locale).toLowerCase();
      result = result.replaceFirst("peso", "").trim();
      result = result.replaceFirst("piso", "").trim();
      result = WordUtil.removeShortWords(result, 2);
    }

    return result;
  }

  private static String getIndonesian(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String result = findCurrency(currencies, "IDR").getDisplayName(locale).toLowerCase();
    result = replaceFirstIgnoreAccents(result, "roupi", "").trim();
    result = replaceFirstIgnoreAccents(result, "rupi", "").trim();
    result = replaceFirstIgnoreAccents(result, "roepia", "").trim();
    result = WordUtil.removeShortWords(result, 3);
    return result;
  }

  private static String getCanadian(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String aud = findCurrency(currencies, "CAD").getDisplayName(locale).toLowerCase();
    String cad = findCurrency(currencies, "AUD").getDisplayName(locale).toLowerCase();
    String remaining = extractDelta(aud, cad);
    return remaining;
  }

  private static String getMexican(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String result = findCurrency(currencies, "MXN").getDisplayName(locale).toLowerCase();
    result = replaceFirstIgnoreAccents(result, "peso", "").trim();
    return result;
  }

  private static String getColombian(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String result = findCurrency(currencies, "COP").getDisplayName(locale).toLowerCase();
    result = replaceFirstIgnoreAccents(result, "peso", "").trim();
    return result;
  }

  private static String getVenezualian(Locale locale) {
    String language = locale.getLanguage();
    String result;

    if (List.of("ro").contains(language)) {
      result = getVenezualian(Locale.ENGLISH);
    } else {
      List<Currency> currencies = getAvailableCurrencies();
      result = findCurrency(currencies, "VES").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "bolivar", "").trim();
    }

    return result;
  }

  private static String getPeruvian(Locale locale) {
    String language = locale.getLanguage();
    String result;

    if (List.of("ro").contains(language)) {
      result = getPeruvian(Locale.ENGLISH);
    } else {
      List<Currency> currencies = getAvailableCurrencies();
      result = findCurrency(currencies, "PEN").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "sol", "").trim();
      result = replaceFirstIgnoreAccents(result, "novo", "").trim();
    }

    return result;
  }

  private static String getBolivian(Locale locale) {
    String language = locale.getLanguage();
    List<Currency> currencies = getAvailableCurrencies();
    String result = findCurrency(currencies, "BOB").getDisplayName(locale).toLowerCase();

    if (List.of("es", "it").contains(language)) {
      result = result.trim();
    } else if (List.of("ro").contains(language)) {
      result = getBolivian(Locale.ENGLISH);
    } else {
      result = replaceFirstIgnoreAccents(result, "boliviano", "").trim();
      result = WordUtil.removeShortWords(result, 2);
    }

    return result;
  }

  private static String getChilian(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String result = findCurrency(currencies, "CLP").getDisplayName(locale).toLowerCase();
    result = replaceFirstIgnoreAccents(result, "peso", "").trim();
    return result;
  }

  private static String getArgentine(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String result = findCurrency(currencies, "ARS").getDisplayName(locale).toLowerCase();
    result = replaceFirstIgnoreAccents(result, "peso", "").trim();
    return result;
  }

  private static String getUruguayan(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String result = findCurrency(currencies, "UYU").getDisplayName(locale).toLowerCase();
    result = replaceFirstIgnoreAccents(result, "peso", "").trim();
    return result;
  }

  private static String getBrazilian(Locale locale) {
    String language = locale.getLanguage();
    String result;

    if (List.of("ro").contains(language)) {
      result = getBrazilian(Locale.ENGLISH);
    } else {
      List<Currency> currencies = getAvailableCurrencies();
      result = findCurrency(currencies, "BRL").getDisplayName(locale).toLowerCase();
      result = replaceFirstIgnoreAccents(result, "real", "").trim();
    }

    return result;
  }

  private static void printItalian(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String aud = findCurrency(currencies, "ITL").getDisplayName(locale).toLowerCase();
    String cad = findCurrency(currencies, "MTL").getDisplayName(locale).toLowerCase();
    String remaining = extractDelta(aud, cad);
    Console.println(remaining);
  }

  private static String getEgyptian(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String result = findCurrency(currencies, "EGP").getDisplayName(locale).toLowerCase();
    String currency = findPound(locale);
    result = replaceFirstIgnoreAccents(result, currency, "").trim();
    return result;
  }

  private static String getTunisian(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String result = findCurrency(currencies, "TND").getDisplayName(locale).toLowerCase();
    result = replaceFirstIgnoreAccents(result, "dinar", "").trim();
    result = WordUtil.removeShortWords(result, 2);
    return result;
  }

  private static String getAlgerian(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String result = findCurrency(currencies, "DZD").getDisplayName(locale).toLowerCase();
    result = replaceFirstIgnoreAccents(result, "dinar", "").trim();
    result = WordUtil.removeShortWords(result, 2);
    return result;
  }

  private static String getMoroccan(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String result = findCurrency(currencies, "MAD").getDisplayName(locale).toLowerCase();
    result = replaceFirstIgnoreAccents(result, "dirham", "").trim();
    return result;
  }

  private static String getAustralian(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String aud = findCurrency(currencies, "AUD").getDisplayName(locale).toLowerCase();
    String cad = findCurrency(currencies, "CAD").getDisplayName(locale).toLowerCase();
    String remaining = extractDelta(aud, cad);
    return remaining;
  }

  private static String getNewZelander(Locale locale) {
    List<Currency> currencies = getAvailableCurrencies();
    String aud = findCurrency(currencies, "NZD").getDisplayName(locale).toLowerCase();
    String cad = findCurrency(currencies, "CAD").getDisplayName(locale).toLowerCase();
    String remaining = extractDelta(aud, cad);
    remaining = remaining.replace("-", "").trim();
    return remaining;
  }

  private static String findSchilling(Locale locale) {
    String language = locale.getLanguage();
    String schilling;

    if (language.equals("it")) {
      schilling = "scellino";
    } else if (language.equals("es")) {
      schilling = "chelín";
    } else if (language.equals("pt")) {
      schilling = "xelim";
    } else if (language.equals("ro")) {
      schilling = "șiling";
    } else {
      schilling = "schilling";
    }

    return schilling;
  }

  private static String findPeseta(Locale locale) {
    return "peseta";
  }

  private static String findEscudo(Locale locale) {
    return "escudo";
  }

  private static String findLira(Locale locale) {
    String language = locale.getLanguage();
    String lira = "lira";

    if (List.of("fr", "nl", "sv").contains(language)) {
      lira = "lire";
    }

    return lira;
  }

  private static String findLitas(Locale locale) {
    String language = locale.getLanguage();
    String litas = "litas";

    if (List.of("ro").contains(language)) {
      litas = "litu";
    }

    return litas;
  }

  private static String findZloty(Locale locale) {
    String language = locale.getLanguage();
    String zloty;

    if ("es".equals(language)) {
      zloty = "esloti";
    } else if ("ro".equals(language)) {
      zloty = "zlot";
    } else {
      zloty = "zloty";
    }

    return zloty;
  }

  private static String extractDelta(String original, String delta) {
    String dollar = WordUtil.findLonguestCommonWord(original, delta).trim();
    String remaining = WordUtil.removeSubstring(original, dollar).trim().toLowerCase();
    return remaining;
  }

  private static List<Currency> getAvailableCurrencies() {
    List<Currency> currencies =
        Currency.getAvailableCurrencies().stream()
            .sorted(Comparator.comparing(Currency::getCurrencyCode))
            .collect(Collectors.toList());
    return currencies;
  }

  private static Currency findCurrency(List<Currency> currencies, String code) {
    return currencies.stream().filter(c -> c.getCurrencyCode().equals(code)).findAny().orElse(null);
  }

  private static String replaceFirstIgnoreAccents(
      String original, String replaced, String replacement) {
    String asciiOrignal = StringUtil.stripAccents(original);
    String asciiReplaced = StringUtil.stripAccents(replaced);
    int idx = asciiOrignal.indexOf(asciiReplaced);

    if (idx != -1) {
      StringBuilder builder = new StringBuilder();
      builder.append(original, 0, idx);
      builder.append(replacement);
      builder.append(original, idx + replaced.length(), original.length());
      original = builder.toString();
    }

    return original;
  }
}
