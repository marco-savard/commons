package com.marcosavard.commons.math;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.math.Angle.Unit;

public class AngleDemo {

  public static void main(String[] args) {
    demoAngleCategory();
    demoAngleConversions();
    demoAngleOperations();
    demoAngleSorting();
  }

  private static void demoAngleSorting() {
    Console.println("Sorting angles");

    List<Angle> angles = new ArrayList<>();
    angles.add(Angle.of(135, Unit.DEG));
    angles.add(Angle.of(270, Unit.DEG));
    angles.add(Angle.of(359, Unit.DEG));
    angles.add(Angle.of(45, Unit.DEG));
    angles.add(Angle.of(25, Unit.DEG));
    angles.add(Angle.of(90, Unit.DEG));
    angles.add(Angle.of(0, Unit.DEG));

    Collections.sort(angles);
    for (Angle angle : angles) {
      Console.println("  " + angle);
    }

  }

  private static void demoAngleCategory() {
    Console.println("Angle categories");

    for (int deg = 0; deg <= 360; deg += 45) {
      Angle angle = Angle.of(deg, Unit.DEG);

      Console.println("  {0} : {1}", angle, angle.getCategory());
    }

    Console.println("");
  }

  private static void demoAngleOperations() {
    Angle a1 = Angle.of(45, Unit.DEG);
    Angle a2 = Angle.of(90, Unit.DEG);
    Angle a3 = a1.addTo(a2);

    String msg = MessageFormat.format("{0} + {1} = {2}", a1, a2, a3);
    System.out.println(msg);
    System.out.println();
  }

  private static void demoAngleConversions() {
    System.out.println("angles in degrees");
    for (int value = -360; value <= 450; value += 90) {
      Angle a1 = Angle.of(value, Unit.DEG);
      System.out.println("  " + String.format("%03d", value) + " => " + a1);
    }
    System.out.println();
  }

}
