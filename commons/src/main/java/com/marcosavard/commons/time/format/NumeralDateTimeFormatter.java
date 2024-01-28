package com.marcosavard.commons.time.format;

import com.marcosavard.commons.ling.Numeral;
import com.marcosavard.commons.ling.RomanNumeral;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.util.Locale;

public class NumeralDateTimeFormatter {
  private DateTimeFormatter dateTimeFormatter;
  private Locale display;

  public static NumeralDateTimeFormatter ofPattern(String pattern, Locale display) {
    String numeralPattern = toNumeralPattern(pattern);
    NumeralDateTimeFormatter formatter = new NumeralDateTimeFormatter(numeralPattern, display);
    return formatter;
  }

  private static String toNumeralPattern(String pattern) {
    pattern = pattern.replace(" #cccc", " '-#cccc-'");
    pattern = pattern.replace("#cccc ", "'-#cccc-' ");
    pattern = pattern.replace(" #ccc", " '-#ccc-'");
    pattern = pattern.replace("#ccc ", "'-#ccc-' ");
    pattern = pattern.replace(" #cc", " '-#cc-'");
    pattern = pattern.replace("#cc ", "'-#cc-' ");
    pattern = pattern.replace(" #c", " '-#c-'");
    pattern = pattern.replace("#c ", "'-#c-' ");

    pattern = pattern.replace(" #dddd", " '-#dddd-'");
    pattern = pattern.replace("#dddd ", "'-#dddd-' ");
    pattern = pattern.replace(" #ddd", " '-#ddd-'");
    pattern = pattern.replace("#ddd ", "'-#ddd-' ");
    pattern = pattern.replace(" #dd", " '-#dd-'");
    pattern = pattern.replace("#dd ", "'-#dd-' ");
    pattern = pattern.replace(" #d", " '-#d-'");
    pattern = pattern.replace("#d ", "'-#d-' ");

    pattern = pattern.replace(" #yyyy", " '-#yyyy-'");
    pattern = pattern.replace("#yyyy ", "'-#yyyy-' ");
    pattern = pattern.replace(" #yyy", " '-#yyy-'");
    pattern = pattern.replace("#yyy ", "'-#yyy-' ");
    pattern = pattern.replace(" #yy", " '-#yy-'");
    pattern = pattern.replace("#yy ", "'-#yy-' ");
    pattern = pattern.replace(" #y", " '-#y-'");
    pattern = pattern.replace("#y ", "'-#y-' ");
    return pattern;
  }

  private NumeralDateTimeFormatter(String pattern, Locale display) {
    this.display = display;
    this.dateTimeFormatter = DateTimeFormatter.ofPattern(pattern, display);
  }

  public String format(Temporal temporal) {
    String text = dateTimeFormatter.format(temporal);
    int dayOfMonth = 0, year = 0, century = 0;

    if (temporal.isSupported(ChronoField.DAY_OF_MONTH)) {
      dayOfMonth = temporal.get(ChronoField.DAY_OF_MONTH);
    }

    if (temporal.isSupported(ChronoField.YEAR)) {
      year = temporal.get(ChronoField.YEAR);
      century = (year + 99) / 100;
    }

    Numeral numeral = Numeral.of(this.display);
    Numeral romanNumeral = new RomanNumeral();

    text = text.replace("-#cccc-", numeral.getDisplayName(century, Numeral.Category.ORDINAL));
    text = text.replace("-#ccc-", numeral.getDisplayName(century, toCategory(dayOfMonth)));
    text = text.replace("-#cc-", numeral.getDisplayName(century));
    text = text.replace("-#c-", romanNumeral.getDisplayName(century));

    text = text.replace("-#dddd-", numeral.getDisplayName(dayOfMonth, Numeral.Category.ORDINAL));
    text = text.replace("-#ddd-", numeral.getDisplayName(dayOfMonth, toCategory(dayOfMonth)));
    text = text.replace("-#dd-", numeral.getDisplayName(dayOfMonth));
    text = text.replace("-#d-", romanNumeral.getDisplayName(dayOfMonth));

    text = text.replace("-#yyyy-", numeral.getDisplayName(year, Numeral.Category.ORDINAL));
    text = text.replace("-#yyy-", numeral.getDisplayName(year, toCategory(year)));
    text = text.replace("-#yy-", numeral.getDisplayName(year));
    text = text.replace("-#y-", romanNumeral.getDisplayName(year));
    return text;
  }

  private Numeral.Category toCategory(int number) {
    Numeral.Category cat = (number == 1) ? Numeral.Category.ORDINAL : Numeral.Category.CARDINAL;
    return cat;
  }
}
