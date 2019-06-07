package com.marcosavard.commons.money;

import java.text.MessageFormat;

/**
 * Hello world!
 *
 */
public class MoneyAmountDemo {

  public static void main(String[] args) {
    MoneyAmount m1 = MoneyAmount.of(10.10, MoneyAmount.USD);
    MoneyAmount m2 = MoneyAmount.of(10.20, MoneyAmount.USD);

    MoneyAmount sum = m1.add(m2);
    String msg = MessageFormat.format("{0} + {1} = {2}", m1, m2, sum);
    System.out.println(msg);

    MoneyAmount sumPlusTax = sum.multiply(1.07);
    msg = MessageFormat.format("{0} * 1.07% = {1}", sum, sumPlusTax);
    System.out.println(msg);

    boolean equal = sum.equals(MoneyAmount.of(20.03, MoneyAmount.USD));
    msg = MessageFormat.format("{0} + {1} = $20.30 is : {2}", m1, m2, equal);
    System.out.println(msg);

    MoneyAmount converted = sum.convert(1.25, MoneyAmount.CAD);
    msg = MessageFormat.format("{0} gives {1}", sum, converted);
    System.out.println(msg);

    // compute simple interest
    MoneyAmount principal = MoneyAmount.of(3000, MoneyAmount.USD);
    MoneyAmount earn = principal.computeSimpleInterest(0.025, 10);
    msg = MessageFormat.format("{0} at 2.5%/year during 10 years gives {1}", principal, earn);
    System.out.println(msg);

    // compute compound interest
    principal = MoneyAmount.of(3000, MoneyAmount.USD);
    earn = principal.computeCompoundInterest(0.025, 12, 10);
    msg = MessageFormat.format("{0} at 2.5%/month during 10 years gives {1}", principal, earn);
    System.out.println(msg);

    try {
      sum = m1.add(MoneyAmount.of(1, MoneyAmount.CAD));
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}


