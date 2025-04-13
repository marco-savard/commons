package com.marcosavard.commons.time;

import com.marcosavard.commons.time.holiday.ReligiousHoliday;
import junit.framework.Assert;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

public class EasterTest {

  @Test
  public void givenYear1818_whenFindEasterDate_thanGivesMarch22() {
    // Easter 1818 occurred on March 22nd (earliest possible date)
    assertDate(1818, Month.MARCH, 22);
  }

  @Test
  public void givenYear2000_whenFindEasterDate_thanGivesApril23() {
    // Easter 2000 occurred on April 23rd
    assertDate(2000, Month.APRIL, 23);
  }

  @Test
  public void givenYear2008_whenFindEasterDate_thanGivesMarch23() {
    // Easter 2008 occurred on March 23rd
    assertDate(2008, Month.MARCH, 23);
  }

  @Test
  public void givenYear2018_whenFindEasterDate_thanGivesAprilFirst() {
    // Easter 2018 occurred on April 1st
    assertDate(2018, Month.APRIL, 1);
  }

  @Test
  public void givenYear2019_whenFindEasterDate_thanGivesApril21st() {
    // Easter 2019 occurred on April 21st
    assertDate(2019, Month.APRIL, 21);
  }

  private void assertDate(int year, Month month, int dayOfMonth) {
    LocalDate easterDate = ReligiousHoliday.findEasterDate(year);
    Assert.assertEquals(month, easterDate.getMonth());
    Assert.assertEquals(dayOfMonth, easterDate.getDayOfMonth());
  }
}
