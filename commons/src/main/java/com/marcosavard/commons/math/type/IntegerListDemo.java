package com.marcosavard.commons.math.type;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;

public class IntegerListDemo {

  public static void main(String[] args) {
    demoBasic();

    demoBarCode();

    // demoCalendar();
  }

  private static void demoBasic() {
    IntegerList list = IntegerList.of(20);
    System.out.println("list = " + list);

    list = list.multiplyBy(2).addTo(19);
    System.out.println("odd numbers in [20..60] = " + list);

    List<Integer> countdown = IntegerList.of(10).reverse();
    System.out.println("countdown = " + countdown);

    BinaryOperator<Integer> addition = (x1, x2) -> x1 + x2;
    int sum = IntegerList.of(10).forAll(0, addition);
    System.out.println("sum = " + sum);

    BinaryOperator<Integer> multiplication = (x1, x2) -> x1 * x2;
    int product = IntegerList.of(6).forAll(1, multiplication);
    System.out.println("6! = " + product);

    product = IntegerList.of("1 2 3 4 5 6").forAll(1, multiplication);
    System.out.println("6! = " + product);

    System.out.println();
  }

  private static void demoBarCode() {

    Barcode barcode = Barcode.of(0, 3, 1, 2, 1, 5, 2, 2, 7, 2);
    System.out.println("barcode = " + barcode);

    boolean valid = barcode.validate();
    System.out.println("valid = " + valid);
  }

  private static void demoCalendar() {
    LocalDate date = LocalDate.of(2021, 1, 1);

    List<Integer> month = IntegerList.of(31);
    System.out.println(month);

  }

  private static class Barcode {
    private List<Integer> digits;

    public static Barcode of(Integer... digits) {
      Barcode Barcode = new Barcode(Arrays.asList(digits));
      return Barcode;
    }

    public boolean validate() {
      List<Integer> countdown = IntegerList.of(10).reverse();
      List<Integer> products = IntegerList.of(digits).multiplyBy(IntegerList.of(countdown));
      System.out.println("products = " + products);

      int sum = IntegerList.of(products).forAll(0, Integer::sum);
      System.out.println("sum = " + sum);

      boolean valid = (sum % 11) == 0;
      return valid;
    }

    @Override
    public String toString() {
      String str = digits.toString();
      return str;
    }


    private Barcode(List<Integer> digits) {
      this.digits = digits;
    }


  }
}
