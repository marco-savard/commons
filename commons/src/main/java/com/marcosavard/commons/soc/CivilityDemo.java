package com.marcosavard.commons.soc;

import java.util.Locale;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.soc.res.CivilityResources;

public class CivilityDemo {

  // display locale-sensible civilities
  // eg. Mr, Mrs, Dr, Capt,
  public static void main(String[] args) {
    Locale fr = Locale.FRENCH;

    String civil = CivilityResources.getString("Mr");
    Console.println("{0} {1}", civil, "John Smith");

    civil = CivilityResources.getString("Mr", fr);
    Console.println("{0} {1}", civil, "John Smith");
  }

}
