package com.marcosavard.commons.util.collection;

import com.marcosavard.commons.debug.Console;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ListDemo {

  private static final Class[] CLASSES = new Class[] {Object.class, Runnable.class};

  public static void main(String[] args) {
    Locale[] array = Locale.getAvailableLocales();
    convertArrayToList(array);
    convertArrayToList2(array);
    listCountries(array);
    listNotEmptyCountries(array);
    listNotEmptyCountries2(array);
    listUniqueCountries(array);
    listUniqueCountries2(array);
    listSortedCountries(array);
    listSortedCountries2(array);
  }


  private static void convertArrayToList(Locale[] array) {
    List<Locale> locales = new ArrayList<>();

    if (array != null) {
      locales = Arrays.asList(array);
    }

    Console.println(locales);
  }

  private static void convertArrayToList2(Locale[] array) {
    List<Locale> locales = ListUtil.toList(array);
    Locale[] array2 = locales.toArray(new Locale[0]);

    Console.println(locales);
  }

  private static void listCountries(Locale[] array) {
    List<String> countryList = new ArrayList<>();

    for (Locale locale : array) {
      String language = locale.getCountry();
      countryList.add(language);
    }

    Console.println("{0} items : {1}", countryList.size(), countryList);
  }

  private static void listNotEmptyCountries(Locale[] array) {
    List<String> countryList = new ArrayList<>();

    for (Locale locale : array) {
      String country = locale.getCountry();
      if (country != null && !country.isEmpty()) {
        countryList.add(country);
      }
    }

    Console.println("{0} items : {1}", countryList.size(), countryList);
  }

  private static void listNotEmptyCountries2(Locale[] array) {
    List<String> countryList = new NullSafeList<>();

    for (Locale locale : array) {
      String country = locale.getCountry();
      countryList.add(country);
    }

    Console.println("{0} items : {1}", countryList.size(), countryList);
  }

  private static void listUniqueCountries(Locale[] array) {
    List<String> countryList = new ArrayList<>();

    for (Locale locale : array) {
      String country = locale.getCountry();
      if (country != null && !country.isEmpty()) {
        if (!countryList.contains(country)) {
          countryList.add(country);
        }
      }
    }

    Console.println("{0} items : {1}", countryList.size(), countryList);
  }

  private static void listUniqueCountries2(Locale[] array) {
    List<String> countryList = new UniqueList<>();

    for (Locale locale : array) {
      String country = locale.getCountry();
      countryList.add(country);
    }

    Console.println("{0} items : {1}", countryList.size(), countryList);
  }

  private static void listSortedCountries(Locale[] array) {
    List<String> countryList = new ArrayList<>();

    for (Locale locale : array) {
      String country = locale.getCountry();
      if (country != null && !country.isEmpty()) {
        if (!countryList.contains(country)) {
          countryList.add(country);
        }
      }
    }

    Collections.sort(countryList);
    Console.println("{0} items : {1}", countryList.size(), countryList);
  }

  private static void listSortedCountries2(Locale[] array) {
    List<String> countryList = new SortedList<>();

    for (Locale locale : array) {
      String country = locale.getCountry();
      countryList.add(country);
    }

    Console.println("{0} items : {1}", countryList.size(), countryList);
  }
}
