package com.marcosavard.common.math;

import com.marcosavard.common.debug.Console;

import static com.marcosavard.common.math.SafeMath.*;
import static java.lang.Math.*;

public class SafeMathDemo {

    public static void main(String[] args) {
       demoEqual();
       demoRange();
       demoTrim();
       demoTrigonometry();
    }

    private static void demoEqual() {
        double x=0.1, y=0.2, r = 0.3;
        double sum = x + y;

        Console.println("Demo SafeMath.equal()");
        Console.indent();
        Console.println("[UNSAFE] {0} + {1} == {2}", x, y, sum);
        Console.println("[UNSAFE] if ({0} + {1} == {2}) : {3}", x, y, r, sum == r);
        Console.println("[SAFE] if (equal({0} + {1}, {2})) : {3}", x, y, r, equal(sum, r));
        Console.println("[SAFE] if (equal({0} + {1}, {2}, 0.001)) : {3}", x, y, r, equal(sum, r, 0.001));
        Console.unindent();
        Console.println();
    }

    private static void demoRange() {
        int angle = 365;
        Console.println("Demo SafeMath.range()");
        Console.indent();
        Console.println("[SAFE] SafeMath.range({0}, 0, 360) == {1}", angle, SafeMath.range(angle, 0, 360));
        angle = -15;
        Console.println("[SAFE] SafeMath.range({0}, 0, 360) == {1}", angle, SafeMath.range(angle, 0, 360));

        Console.println("[SAFE] SafeMath.range(2 * Math.PI, -Math.PI, Math.PI) == {0}", SafeMath.range(2*Math.PI, -Math.PI, Math.PI));
        Console.unindent();
        Console.println();
    }

    private static void demoTrim() {
        double x=0.1, y=0.2, r = 0.3;
        double sum = x + y;
        double rounded = SafeMath.trim(Math.PI, 0.0001);

        Console.println("Demo SafeMath.trim()");
        Console.indent();
        Console.println("[UNSAFE] {0} + {1} == {2}", x, y, sum);
        Console.println("[SAFE] SafeMath.trim({0} + {1}) == {2}", x, y, r, trim(sum));
        Console.println("[SAFE] SafeMath.trim(Math.PI, 0.0001) : {0}", rounded);
        Console.unindent();
        Console.println();
    }

    private static void demoTrigonometry() {
        double r = 5, theta = 45, phi = 60;

        double x = r * sin(toRadians(phi)) * cos(toRadians(theta));
        double y = r * sin(toRadians(phi)) * sin(toRadians(theta));
        double z = r * cos(toRadians(phi));

        Console.println("Demo SafeMath : converting spherical to rectangular variables");
        Console.indent();
        Console.println("let r={0} theta={1} phi={2}", r, theta, phi);
        Console.println();

        Console.println("[java.math.Math]");
        Console.println("  x = r * sin(toRadians(phi)) * cos(toRadians(theta); x={0}", x);
        Console.println("  y = r * sin(toRadians(phi)) * sin(toRadians(theta); y={0}", y);
        Console.println("  z = r * cos(toRadians(phi); z={0}", z);
        Console.println();

        x = r * sind(phi) * cosd(theta);
        y = r * sind(phi) * sind(theta);
        z = r * cosd(phi);

        Console.println("[math.SafeMath]");
        Console.println("  x = r * sind(phi) * cosd(theta); x={0}", x);
        Console.println("  y = r * sind(phi) * sind(theta); y={0}", y);
        Console.println("  z = r * cosd(phi); z={0}", z);
        Console.println();

        Console.unindent();
        Console.println();
    }


}
