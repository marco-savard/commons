package com.marcosavard.commons.time;

import junit.framework.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;

public class EasterTest {

  @Test
  public void givenYear1818_whenGetEasterDate_thanGivesMarch22() {
    // Easter 1818 occurred on March 22nd (earliest possible date)
    LocalDate easter1818 = Holiday.EASTER.dateOf(1818);
    Assert.assertEquals(easter1818.getMonth(), Month.MARCH);
    Assert.assertEquals(easter1818.getDayOfMonth(), 22);
  }

  @Test
  public void givenYear2000_whenGetEasterDate_thanGivesApril23() {
    // Easter 2000 occurred on April 23rd
    LocalDate easter2000 = Holiday.EASTER.dateOf(2000);
    Assert.assertEquals(easter2000.getMonth(), Month.APRIL);
    Assert.assertEquals(easter2000.getDayOfMonth(), 23);
  }

  @Test
  public void givenYear2008_whenGetEasterDate_thanGivesMarch23() {
    // Easter 2008 occurred on March 23rd
    LocalDate easter2008 = Holiday.EASTER.dateOf(2008);
    Assert.assertEquals(easter2008.getMonth(), Month.MARCH);
    Assert.assertEquals(easter2008.getDayOfMonth(), 23);
  }

  @Test
  public void givenYear2018_whenGetEasterDate_thanGivesAprilFirst() {
    // Easter 2018 occurred on April 1st
    LocalDate easter2018 = Holiday.EASTER.dateOf(2018);
    Assert.assertEquals(easter2018.getMonth(), Month.APRIL);
    Assert.assertEquals(easter2018.getDayOfMonth(), 1);
  }

  @Test
  public void givenYear2019_whenGetEasterDate_thanGivesApril21st() {
    // Easter 2019 occurred on April 21st
    LocalDate easter2019 = Holiday.EASTER.dateOf(2019);
    Assert.assertEquals(easter2019.getMonth(), Month.APRIL);
    Assert.assertEquals(easter2019.getDayOfMonth(), 21);
  }
}
