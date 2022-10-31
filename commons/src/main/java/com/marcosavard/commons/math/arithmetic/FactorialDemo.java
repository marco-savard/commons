package com.marcosavard.commons.math.arithmetic;

import com.marcosavard.commons.debug.Console;

import java.math.BigInteger;

public class FactorialDemo {

  public static void main(String[] args) {
    Console.println("340! = " + Factorial.of(340));
    Console.println("365! = " + Factorial.of(365));

    BigInteger term1 = Factorial.of(365).getValue();
    BigInteger term2 = Factorial.of(340).getValue();
    BigInteger result = term1.subtract(term2); // not accurate

    Console.println("365! - (365-25)! ~= " + result + " (not accurate)");
    Console.println("365! - (365-25)! = " + Factorial.of(365).minus(Factorial.of(365 - 25)));
    Console.println();

    printSameBirthday(15);
    printSameBirthday(25);
    printSameBirthday(35);
    printSameBirthday(45);
    printSameBirthday(50);
    printSameBirthday(66);
  }

  private static void printSameBirthday(int n) {
    double prob = computeSameBirthdayProbability(n);
    Console.println("This is {0} chance that {1} people have same birthday", Percent.of(prob), n);
  }

  // prob = 1 - 365! / (365^n * (365-n)!)
  private static double computeSameBirthdayProbability(int n) {
    BigInteger factorial = Factorial.of(365).minus(Factorial.of(365 - n)); // 365! - (365-n)!
    BigInteger product = factorial.multiply(BigInteger.valueOf(1000));
    BigInteger divisor = BigInteger.valueOf(365).pow(n - 1);
    BigInteger ratio = product.divide(divisor);
    double prob = 1 - (ratio.doubleValue() / 1000);

    return prob;
  }


}
