package com.marcosavard.commons.math;

import java.text.MessageFormat;
import com.marcosavard.commons.math.Angle.Unit;

public class AngleDemo {

  public static void main(String[] args) {
    test1();
    test2();
  }

  private static void test1() {
    Angle a1 = Angle.of(45, Unit.DEGREES);
    Angle a2 = Angle.of(90, Unit.DEGREES);
    Angle a3 = a1.addTo(a2);

    String msg = MessageFormat.format("{0} + {1} = {2}", a1, a2, a3);
    System.out.println(msg);
    System.out.println();
  }

  private static void test2() {
    System.out.println("angles in degrees");
    for (int value = -360; value <= 450; value += 90) {
      Angle a1 = Angle.of(value, Unit.DEGREES);
      System.out.println("  " + String.format("%03d", value) + " => " + a1);
    }
    System.out.println();
  }

}
