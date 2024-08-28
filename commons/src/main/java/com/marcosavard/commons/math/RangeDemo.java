package com.marcosavard.commons.math;

import com.marcosavard.commons.debug.Console;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class RangeDemo {

  public static void main(String[] args) {
    // forLoopUsingRange();
    // demoBasic();
    // demoToString();
    // demoBarCode();
    demoCalendar();
  }

  private static void forLoopUsingRange() {
    for (int i = 0; i < 5; i++) {
      Console.print("i = {0}, ", i);
    }
    Console.println();

    // cf. for in Python
    for (int i : Range.of(5)) {
      Console.print("i = {0}, ", i);
    }
    Console.println();

    for (int i = 4; i >= 0; i--) {
      Console.print("i = {0}, ", i);
    }
    Console.println();

    // cf. for in Python
    for (int i : Range.of(5).reverse()) {
      Console.print("i = {0}, ", i);
    }
    Console.println();
    Console.println();
  }

  private static void demoBasic() {
    Iterable<Integer> range = Range.of(20);
    Console.println("Range [0..19] : " + range);

    range = Range.of(20).multiplyBy(2).addTo(21);
    Console.println("Odd numbers in range [20..60] : " + range);

    Range countdown = Range.of(10).reverse();
    Console.println("Countdown to zero : " + countdown);

    Range squares = Range.of(10).addTo(1).forEach((x) -> x * x);
    Console.println("Squares of two= " + squares);

    Range powersOf2 = Range.of(10).forEach((x) -> 1 << x);
    Console.println("Powers of two = " + powersOf2);

    Range cumulation = Range.of(100).addTo(1).forEach(0, (x, y) -> x + y);
    List<Integer> list = cumulation.toList();
    Console.println("Cumulation of numbers 1..100 : " + list);
    int sum = list.get(list.size() - 1);
    Console.println("Sum of numbers 1..100 : " + sum);

    Range factorials = Range.of(6).addTo(1).forEach(1, (x, y) -> x * y);
    Console.println("Factorials [1..6] = " + factorials);

    int factorial = Range.of(6).addTo(1).product().intValue();
    Console.println("6! = " + factorial);
    Console.println();
  }

  private static void demoToString() {
    String str = Range.of(10).addTo(1).toString();
    List<Integer> range = Range.listOf(str);
    Console.println(str);
    Console.println(range);
    Console.println();
  }

  private static void demoBarCode() {
    Range countdown = Range.of(10).addTo(1).reverse();
    Range barcode = Range.of(0, 3, 1, 2, 1, 5, 2, 2, 7, 2);
    Range products = countdown.multiplyBy(barcode);
    int sum = products.sum();
    boolean valid = (sum % 11) == 0;

    Console.println("Barcode : " + barcode);
    Console.println("Countdown : " + countdown);
    Console.println("Barcode product : " + products);
    Console.println("Barcode sum : " + sum);
    Console.println("Barcode " + barcode + " is valid : " + valid);
    Console.println();
  }

  private static void demoCalendar() {
    YearMonth yearMonth = YearMonth.of(2022, 9);
    LocalDate first = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
    int dayOfWeek = first.getDayOfWeek().getValue();

    Range previous = Range.of(dayOfWeek).multiplyBy(0);
    Range current = Range.of(yearMonth.lengthOfMonth()).addTo(1);
    previous = previous.addAll(current);

    List<String> list = previous.toString(x -> (x == 0) ? "  " : String.format("%2d", x));
    Table calendar = Table.of(list, 7);

    Console.println("  S   M   T   W   T   F   S");
    for (List<String> row : calendar.getRows()) {
      Console.println(row);
    }

    Console.println();
  }
}
