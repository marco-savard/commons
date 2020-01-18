package com.marcosavard.commons.astro;

import java.time.LocalDate;
import java.time.Month;

public class MoonEventDemo {

  public static void main(String[] args) {
    LocalDate date = LocalDate.of(2019, Month.JUNE, 7);
    MoonPhase phase = MoonEvent.getPhaseOnDate(date);
    System.out.println(" Phase : " + phase.toString());

    LocalDate nextFullMoon = MoonEvent.findNextFullMoon(date);
    System.out.println(" Next full moon : " + nextFullMoon.toString());
  }

}
