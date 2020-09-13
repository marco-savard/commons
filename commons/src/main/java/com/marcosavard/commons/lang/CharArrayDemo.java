package com.marcosavard.commons.lang;

import com.marcosavard.commons.debug.StopWatch;

public class CharArrayDemo {

  public static void main(String[] args) {
    demoUsage();
    // demoPerformance();
  }

  private static void demoUsage() {
    CharArray c6 = CharArray.of("qwertyui".toCharArray());
    CharArray c6b = c6.transcode("qwertyui");
    System.out.println("c6 = " + c6);
    System.out.println("c6b = " + c6b);

    CharArray c5 = CharArray.of("12345678".toCharArray());
    CharArray c5b = c5.removeSequantialCharacters();
    System.out.println("c5 = " + c5);
    System.out.println("c5b = " + c5b);

    String s1 = "password";
    CharArray c1 = CharArray.of(s1.toCharArray());

    System.out.println("equal = " + s1.equals("password"));
    System.out.println("equal = " + c1.equals("password"));

    int idx1 = s1.indexOf("word");
    int idx2 = c1.indexOf("word");

    System.out.println("idx = " + idx1);
    System.out.println("idx = " + idx2);

    CharSequence cs1 = s1.subSequence(4, 8);
    CharSequence cs2 = c1.subSequence(4, 8);

    System.out.println("cs1 = " + cs1);
    System.out.println("cs2 = " + cs2);

    String s2 = "pass" + "word";
    CharArray c2 = CharArray.of("pass", "word");

    System.out.println("s2 = " + s2);
    System.out.println("c2 = " + c2);

    CharArray c3 = CharArray.of("pass");
    CharArray c4 = c3.concat("word");
    System.out.println("c4 = " + c4);

    boolean startWithPass1 = s1.startsWith("pass");
    boolean startWithPass2 = c1.startsWith("pass");
    System.out.println("startWithPass2 = " + startWithPass2);

    boolean endsWithWord = c1.endsWith("word");
    System.out.println("endsWithWord = " + endsWithWord);
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
