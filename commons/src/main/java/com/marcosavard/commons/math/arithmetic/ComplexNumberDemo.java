package com.marcosavard.commons.math.arithmetic;

import java.util.Locale;

public class ComplexNumberDemo {

  public static void main(String[] args) {
    ComplexNumber n1 = ComplexNumber.of(1, 0);
    ComplexNumber n2 = ComplexNumber.IMAGINARY_NUMBER;

    System.out.println("1.5 + 0i = " + n1);
    System.out.println("i = " + n2);

    // demo addition
    System.out.println("1 + 1 = " + n1.addTo(n1));
    System.out.println("1 + (9 + 5i) = " + n1.addTo(ComplexNumber.of(9, 5)));

    // demo multiplication
    System.out.println("i * i = " + n2.multiplyBy(n2));

    // demo equal
    String s2 = n2.toString();
    Number n2b = ComplexNumber.valueOf(s2);
    System.out.println("i == i : " + n2.equals(n2b));

    // i18n
    ComplexNumber n3 = ComplexNumber.of(2.5, 1.5);
    String formatted = n3.toDisplayString(Locale.FRENCH);
    System.out.println("(fr) 2.5 + 1.5i = " + formatted);

    // complex format
    ComplexNumber.ComplexNumberFormat complexNumberFormat =
        ComplexNumber.ComplexNumberFormat.ofFormat("j");
    formatted = complexNumberFormat.format(n3);
    System.out.println("(el) 2.5 + 1.5i = " + formatted);
  }
}
