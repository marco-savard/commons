package com.marcosavard.commons.astro;

import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import com.marcosavard.commons.time.Dates;

public class MoonEventDemo {

  public static void main(String[] args) {
    Date date = Dates.toDate(LocalDate.of(2019, Month.JUNE, 7));
    MoonPhase phase = MoonEvent.getPhaseOnDate(date);
    System.out.println(" Phase : " + phase.toString());

    Date nextFullMoon = MoonEvent.findNextFullMoon(date);
    System.out.println(" Next full moon : " + nextFullMoon.toString());
  }

}
