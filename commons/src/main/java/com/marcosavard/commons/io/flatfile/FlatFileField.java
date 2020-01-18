package com.marcosavard.commons.io.flatfile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Currency;

public class FlatFileField {
  private int beginIdx, endIdx;
  private Class type = String.class;
  private Object value;

  private boolean trimmed = false;
  private DateTimeFormatter dateTimeFormatter = null;
  private double moneyUnit;
  private Currency moneyCurrency;

  private static FlatFileField of(int beginIdx, int endIdx) {
    FlatFileField flatFileField = new FlatFileField(beginIdx, endIdx);
    return flatFileField;
  }

  private static FlatFileField ofDate(int beginIdx, int endIdx, String pattern) {
    FlatFileField flatFileField = new FlatFileField(beginIdx, endIdx);
    flatFileField.type = LocalDate.class;
    flatFileField.dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
    return flatFileField;
  }

  private static FlatFileField ofMoneyAmount(int beginIdx, int endIdx, double moneyUnit,
      Currency moneyCurrency) {
    FlatFileField flatFileField = new FlatFileField(beginIdx, endIdx);
    flatFileField.type = MoneyAmount.class;
    flatFileField.moneyUnit = moneyUnit;
    flatFileField.moneyCurrency = moneyCurrency;
    return flatFileField;
  }

  private FlatFileField(int beginIdx, int endIdx) {
    this.beginIdx = beginIdx;
    this.endIdx = endIdx;
  }

  int getBeginIndex() {
    return beginIdx;
  }

  int getEndIndex() {
    return endIdx;
  }

  private Object getValue() {
    return this.value;
  }

  void setValue(String value) {
    this.value = trimmed ? value.trim() : value;

    if (type == LocalDate.class) {
      setDateValue(value);
    } else if (type == MoneyAmount.class) {
      setMoneyAmountValue(value);
    } else {
      setStringValue(value);
    }
  }

  private void setDateValue(String value) {
    this.value = LocalDate.parse(value, dateTimeFormatter);
  }

  private void setMoneyAmountValue(String value) {
    this.value = moneyCurrency.getCurrencyCode() + " " + (Long.parseLong(value) * moneyUnit);
  }

  private void setStringValue(String value) {
    this.value = trimmed ? value.trim() : value;
  }

  @Override
  public String toString() {
    return value.toString();
  }

  public FlatFileField trim() {
    trimmed = true;
    return this;
  }

  private static class MoneyAmount {
  }

}
