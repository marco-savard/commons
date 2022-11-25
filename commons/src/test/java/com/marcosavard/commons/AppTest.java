package com.marcosavard.commons;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Arrays;

/**
 * Unit test for simple App.
 */
public class AppTest {

    class Foo {
        static String name = "Foo";
       void print() {
          System.out.println(this.name);
       }
    }

    class Bar extends Foo {
       String name = "Bar";

       static void printName() {
           //super.print();
       }

    }

    public static void main(String[] args) {
        LocalTime time = LocalTime.of(1, 15, 30);
        time.withHour(2);
        time.with(ChronoField.MINUTE_OF_HOUR, 30);

        System.out.println(time);

    }
    public AppTest() {
        String[] array = new String[5];
        Arrays.fill(array, "Hello");
        System.out.println(array[2]);
    }


    String text2 = "b";
    String text3;
}
