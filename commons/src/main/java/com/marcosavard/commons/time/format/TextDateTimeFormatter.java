package com.marcosavard.commons.time.format;

import com.marcosavard.commons.text.format.TextNumberFormat;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.util.Locale;

public class TextDateTimeFormatter {
  private DateTimeFormatter dateTimeFormatter;
  private Locale display;

  public static TextDateTimeFormatter ofPattern(String pattern, Locale display) {
    String numeralPattern = toNumeralPattern(pattern);
    TextDateTimeFormatter formatter = new TextDateTimeFormatter(numeralPattern, display);
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

  private TextDateTimeFormatter(String pattern, Locale display) {
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

    NumberFormat ordinalFormat = TextNumberFormat.getOrdinalInstance(display);
    NumberFormat fullTextFormat = TextNumberFormat.getFullTextInstance(display);
    NumberFormat fullTextOrdinalFormat = TextNumberFormat.getFullTextOrdinalInstance(display);

    text = text.replace("-#cccc-", fullTextOrdinalFormat.format(century));
    text = text.replace("-#ccc-",(century == 1) ? fullTextOrdinalFormat.format(century) : fullTextFormat.format(century));
    text = text.replace("-#cc-", fullTextFormat.format(century));
    text = text.replace("-#c-", fullTextFormat.format(century));

    text = text.replace("-#dddd-", fullTextOrdinalFormat.format(dayOfMonth));
    text = text.replace("-#ddd-", (dayOfMonth == 1) ? fullTextOrdinalFormat.format(dayOfMonth) : fullTextFormat.format(dayOfMonth));
    text = text.replace("-#dd-", (dayOfMonth == 1) ? ordinalFormat.format(dayOfMonth) : Integer.toString(dayOfMonth));
    text = text.replace("-#d-",  fullTextFormat.format(dayOfMonth));

    text = text.replace("-#yyyy-", fullTextOrdinalFormat.format(year));
    text = text.replace("-#yyy-", (year == 1) ? fullTextOrdinalFormat.format(year) : fullTextFormat.format(year));
    text = text.replace("-#yy-", ordinalFormat.format(year));
    text = text.replace("-#y-", ordinalFormat.format(year % 100));
    return text;
  }
}
