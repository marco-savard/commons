package com.marcosavard.commons.math.type;

import java.util.Arrays;
import java.util.List;

public class IntegerListDemo {

  public static void main(String[] args) {
    IntegerList list = IntegerList.of(20);
    System.out.println("list = " + list);

    list = list.multiplyBy(2).addTo(19);
    System.out.println("odd numbers in [20..60] = " + list);

    List<Integer> countdown = IntegerList.of(10).reverse();
    System.out.println("countdown = " + countdown);
    System.out.println();

    demoBarCode();
  }

  private static void demoBarCode() {

    Barcode barcode = Barcode.of(0, 3, 1, 2, 1, 5, 2, 2, 7, 2);
    System.out.println("barcode = " + barcode);

    boolean valid = barcode.validate();
    System.out.println("valid = " + valid);
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

      int sum = IntegerList.of(products).addAll();
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
