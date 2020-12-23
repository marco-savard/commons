package com.marcosavard.commons.lang;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.debug.StopWatch;

public class CharArrayDemo {

  public static void main(String[] args) {
    demoCharArrayEquality();
    demoCharSequenceOperations();
    demoStringOperations();
    demoCharArrayOperations();
    demoPerformance();
  }



  // compare different ways to create CharArray
  private static void demoCharArrayEquality() {
    CharArray qwerty1 = CharArray.of('q', 'w', 'e', 'r', 't', 'y');
    CharArray qwerty2 = CharArray.of("qwerty".toCharArray());
    Console.println("  {0} equal to {1} : {2}", qwerty1, qwerty2, qwerty1.equals(qwerty2));

    String qwerty3 = "qwerty";
    Console.println("  {0} equal to {1} : {2}", qwerty1, qwerty3, qwerty1.equals(qwerty3));

    CharArray qwerty4 = CharArray.of("qwer", "ty");
    Console.println("  {0} equal to {1} : {2}", qwerty1, qwerty4, qwerty1.equals(qwerty4));
    Console.println();
  }

  private static void demoCharSequenceOperations() {
    CharSequence qwerty1 = CharArray.of('p', 'a', 's', 's', 'w', 'o', 'r', 'd');
    CharSequence qwerty2 = "password";

    Console.println("  qwerty1.charAt(4) : " + qwerty1.charAt(4));
    Console.println("  qwerty2.charAt(4) : " + qwerty2.charAt(4));

    CharSequence subsquence1 = qwerty1.subSequence(4, 8);
    CharSequence subsquence2 = qwerty2.subSequence(4, 8);
    Console.println("  {0} = {1} ", subsquence1, subsquence2);

    Console.println();
  }

  private static void demoStringOperations() {
    CharArray qwerty1 = CharArray.of('p', 'a', 's', 's', 'w', 'o', 'r', 'd');
    String qwerty2 = "password";

    Console.println("  qwerty1.indexOf(\"word\") : " + qwerty1.indexOf("word"));
    Console.println("  qwerty2.indexOf(\"word\") : " + qwerty2.indexOf("word"));

    Console.println("  qwerty1.startsWith(\"pass\") : " + qwerty1.startsWith("pass"));
    Console.println("  qwerty2.startsWith(\"pass\") : " + qwerty2.startsWith("pass"));

    Console.println("  qwerty1.endsWith(\"word\") : " + qwerty1.endsWith("word"));
    Console.println("  qwerty2.endsWith(\"word\") : " + qwerty2.endsWith("word"));

    Console.println("  qwerty1.concat(\"s\") : " + qwerty1.concat("s"));
    Console.println("  qwerty2.concat(\"s\") : " + qwerty2.concat("s"));

    qwerty1 = CharArray.of(' ', 'p', 'a', 's', 's', 'w', 'o', 'r', 'd', ' ');
    qwerty2 = " password ";
    Console.println("  qwerty1.trim() : " + qwerty1.trim());
    Console.println("  qwerty2.trim() : " + qwerty2.trim());

    Console.println();
  }

  private static void demoCharArrayOperations() {
    CharArray password = CharArray.of("qwertyui".toCharArray());
    CharArray transcoded = password.transcode("qwertyui");
    Console.println("  {0} transcoded : {1}", password, transcoded);

    password = CharArray.of("abcdefgh".toCharArray());
    CharArray trimmed = password.removeSequantialCharacters();
    Console.println("  {0} trimmed : {1}", password, trimmed);

    password = CharArray.of("12345678".toCharArray());
    trimmed = password.removeSequantialCharacters();
    Console.println("  {0} trimmed : {1}", password, trimmed);

    password = CharArray.of("abc123".toCharArray());
    trimmed = password.removeSequantialCharacters();
    Console.println("  {0} trimmed : {1}", password, trimmed);
    Console.println();
  }


  private static void demoPerformance() {
    String s1 = "long character string that contains a password inside";
    int nb = 1000 * 1000;

    StopWatch sw1 = new StopWatch();
    sw1.start();

    for (int i = 0; i < nb; i++) {
      int idx1 = s1.indexOf("password");
    }

    sw1.end();
    long time = sw1.getTime();
    System.out.println("time = " + time);

    StopWatch sw2 = new StopWatch();
    sw2.start();
    CharArray c1 = CharArray.of(s1);

    for (int i = 0; i < nb; i++) {
      int idx1 = c1.indexOfOld("password");
    }

    sw2.end();
    long time2 = sw2.getTime();
    System.out.println("time2 = " + time2);


  }



}
