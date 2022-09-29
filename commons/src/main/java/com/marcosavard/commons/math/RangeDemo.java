package com.marcosavard.commons.math;

import com.marcosavard.commons.debug.Console;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RangeDemo {

  public static void main(String[] args) {
    forLoopUsingRange();
    demoBasic();
    demoToString();
    demoBarCode();
    demoCalendar();
  }

  private static void forLoopUsingRange() {
    for (int i = 0; i < 5; i++) {
      Console.print("i = " + i + ", ");
    }
    Console.println();

    // cf. for in Python
    for (int i : Range.of(5)) {
      Console.print("i = " + i + ", ");
    }
    Console.println();
  }

  private static void demoBasic() {
    Range range = Range.of(20);
    Console.println("Range [0..19] : " + range);

    range = range.multiplyBy(2).addTo(19);
    Console.println("Odd numbers in range [20..60] : " + range);

    Range countdown = Range.of(10).reverse();
    Console.println("Countdown : " + countdown);

    int sum = Range.of(10).sum();
    Console.println("Sum of numbers 0..9 : " + sum);

    int factorial = Range.of(1, 6).product().intValue();
    Console.println("6! = " + factorial);

    factorial = Range.of(1, 6).forAll(1, (x1, x2) -> x1 * x2);
    Console.println("6! = " + factorial);
    Console.println();
  }

  private static void demoToString() {
    String str = Range.of(10).addTo(1).toString();
    Range range = Range.of(str);
    Console.println(str);
    Console.println(range);
    Console.println();
  }

  private static void demoBarCode() {
    Range barcode = Range.of(0, 3, 1, 2, 1, 5, 2, 2, 7, 2);
    Range countdown = Range.of(10).addTo(1).reverse();
    Range products = barcode.multiplyBy(countdown);
    int sum = products.sum();
    boolean valid = (sum % 11) == 0;

    Console.println("Barcode : " + barcode);
    Console.println("Barcode product : " + products);
    Console.println("Barcode sum : " + sum);
    Console.println("Barcode " + barcode + " is valid : " + valid);
    Console.println();
  }

  private static void demoCalendar() {
    YearMonth calendar = YearMonth.of(2022, 9);
    LocalDate first = LocalDate.of(calendar.getYear(), calendar.getMonth(), 1);
    int dayOfWeek = first.getDayOfWeek().getValue();

    Range previous = Range.of(dayOfWeek).multiplyBy(0);
    Range current = Range.of(calendar.lengthOfMonth()).addTo(1);
    previous.addAll(current);
    Collection<List<Integer>> weeks = previous.partitionBy(7);
    List<String> lines = format("%2d", weeks);

    Console.println(" S  M  T  W  T  F  S");
    for (String line : lines) {
      Console.println(line.replace(" 0", "  "));
    }
    Console.println();
  }

  private static List<String> format(String pattern, Collection<List<Integer>> items) {
    List<String> lines = new ArrayList<>();

    for (List<Integer> row : items) {
      lines.add(format(pattern, row));
    }

    return lines;
  }

  private static String format(String pattern, List<Integer> values) {
    List<String> line = new ArrayList<>();

    for (int value : values) {
      line.add(String.format(pattern, value));
    }

    return String.join(" ", line);
  }
}
