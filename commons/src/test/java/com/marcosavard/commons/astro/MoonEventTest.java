package com.marcosavard.commons.astro;

import static org.junit.Assert.assertEquals;
import java.time.LocalDate;
import java.time.Month;
import org.junit.Test;
import junit.framework.Assert;

public class MoonEventTest {

  @Test
  public void testMoonEvent() {
    LocalDate date = LocalDate.of(2020, Month.JANUARY, 9);
    MoonPhase phase = MoonEvent.getPhaseOnDate(date);
    assertEquals(15, phase.getAgeInDays(), 0.5);

    System.out.println(" Phase : " + phase.toString());

    LocalDate nextFullMoon = MoonEvent.findNextNewMoon(date);
    LocalDate expected = LocalDate.of(2020, 1, 22);
    Assert.assertEquals(expected, nextFullMoon);
    System.out.println(" nextFullMoon : " + nextFullMoon.toString());

  }



}
