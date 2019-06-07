package com.marcosavard.commons.money;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

/**
 * Immutable class for monetary calculations.
 * 
 * @author Marco
 *
 */
public class MoneyAmount {
  public static final Currency USD = Currency.getInstance(Locale.US);
  public static final Currency CAD = Currency.getInstance(Locale.CANADA);

  public static final MoneyAmount ZERO_USD = MoneyAmount.of(0.0, USD);
  public static final MoneyAmount ZERO_CAD = MoneyAmount.of(0.0, CAD);

  private static final int MONEY_AMOUNT_SCALE = 8; // 2 cents + 6 digits
  private static final MathContext MONEY_AMOUNT_MATH_CONTEXT = MathContext.DECIMAL128;

  private final BigDecimal value;
  private final Currency currency;

  // return a money amount of some value in a given currency
  public static MoneyAmount of(double value, Currency currency) {
    return MoneyAmount.of(BigDecimal.valueOf(value), currency);
  }

  public static MoneyAmount of(BigDecimal value, Currency currency) {
    verifyAmount(value);
    verifyCurrency(currency);

    if (value.equals(BigDecimal.ZERO) && currency.equals(USD)) {
      return ZERO_USD;
    } else if (value.equals(BigDecimal.ZERO) && currency.equals(CAD)) {
      return ZERO_CAD;
    } else {
      return new MoneyAmount(value, currency);
    }
  }

  public MoneyAmount add(MoneyAmount augend) {
    verifyCurrency(augend, "Adding {0} to {1} is not allowed");
    BigDecimal addition = value.add(augend.value);
    return MoneyAmount.of(addition, currency);
  }

  public MoneyAmount subtract(MoneyAmount difference) {
    verifyCurrency(difference, "Substracting {0} from {1} is not allowed");
    BigDecimal subtraction = value.subtract(difference.value);
    return MoneyAmount.of(subtraction, currency);
  }

  public MoneyAmount multiply(double multiplicant) {
    BigDecimal product = value.multiply(BigDecimal.valueOf(multiplicant));
    return MoneyAmount.of(product, currency);
  }

  public MoneyAmount divide(double divisor) {
    BigDecimal division = value.divide(BigDecimal.valueOf(divisor), MONEY_AMOUNT_MATH_CONTEXT);
    return MoneyAmount.of(division, currency);
  }

  public MoneyAmount negate() {
    BigDecimal inverse = value.negate(MONEY_AMOUNT_MATH_CONTEXT);
    return MoneyAmount.of(inverse, currency);
  }

  public boolean isPositive() {
    int signum = value.signum();
    return (signum >= 0);
  }

  public static MoneyAmount max(MoneyAmount amount1, MoneyAmount amount2) {
    return (amount1.compareTo(amount2) >= 0) ? amount1 : amount2;
  }

  public static MoneyAmount min(MoneyAmount amount1, MoneyAmount amount2) {
    return (amount1.compareTo(amount2) <= 0) ? amount1 : amount2;
  }

  public MoneyAmount convert(double conversionRate, Currency targetCurrency) {
    BigDecimal product = value.multiply(BigDecimal.valueOf(conversionRate));
    return MoneyAmount.of(product, targetCurrency);
  }

  public int compareTo(MoneyAmount that) {
    verifyCurrency(that, "Comparing {0} to {1} is not allowed");

    int comparison = value.compareTo(that.value);
    return comparison;
  }

  public MoneyAmount computeSimpleInterest(double interestRate, int periods) {
    BigDecimal interest = this.value.multiply(BigDecimal.valueOf(interestRate * periods));
    BigDecimal principalWithInterest = this.value.add(interest);
    return MoneyAmount.of(principalWithInterest, currency);
  }

  public MoneyAmount computeCompoundInterest(double interestRate, int frequency, int periods) {
    BigDecimal quotient = BigDecimal.valueOf(interestRate).divide(BigDecimal.valueOf(frequency),
        MONEY_AMOUNT_MATH_CONTEXT);
    BigDecimal base = quotient.add(BigDecimal.valueOf(1.0));
    BigDecimal interest = base.pow(frequency * periods);
    BigDecimal principalWithInterest = this.value.multiply(interest);
    return MoneyAmount.of(principalWithInterest, currency);
  }

  @Override
  public boolean equals(Object that) {
    boolean equal = false;

    if (that instanceof MoneyAmount) {
      MoneyAmount thatAmount = (MoneyAmount) that;
      equal = value.equals(thatAmount.value);
    }

    return equal;
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

  @Override
  public String toString() {
    NumberFormat formatter = NumberFormat.getCurrencyInstance();
    formatter.setCurrency(currency);
    String formatted = formatter.format(value);
    return formatted;
  }

  //
  // private
  //

  private MoneyAmount(BigDecimal bigDecimal, Currency currency) {
    this.value = bigDecimal;
    this.currency = currency;
    this.value.setScale(MONEY_AMOUNT_SCALE, RoundingMode.HALF_EVEN);
  }

  private static void verifyAmount(BigDecimal value) {
    if (value == null) {
      throw new IllegalArgumentException("Null amount is not allowed");
    }
  }

  private static void verifyCurrency(Currency currency) {
    if (currency == null) {
      throw new IllegalArgumentException("Null currency is not allowed");
    }
  }

  private void verifyCurrency(MoneyAmount that, String pattern) {
    if (!this.currency.equals(that.currency)) {
      String msg = MessageFormat.format(pattern, that, this);
      throw new ArithmeticException(msg);
    }
  }



}
