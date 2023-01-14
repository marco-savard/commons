package com.marcosavard.commons.lang;

import com.marcosavard.commons.debug.Console;

public class StringUtilDemo {
  private static final String QUEBEC = "Québec";

  public static void main(String[] args) {
    demoStringWithAccents();
    // demoCenterLongString();
  }

  private static void demoStringWithAccents() {
    // stripAccent
    Console.println("{0} translated : {1}", QUEBEC, StringUtil.translate(QUEBEC, "é", "e"));
    Console.println("{0} stripped : {1}", QUEBEC, StringUtil.stripAccents(QUEBEC));

    // equalsIgnoreCase
    boolean equal = StringUtil.equalsIgnoreAccents("Quebec", QUEBEC);
    Console.println("{0} equals {1} : {2}", "Quebec", QUEBEC, equal);

    // indexOf, lastIndexOf
    Console.println("indexOf e in {0} : {1}", QUEBEC, QUEBEC.indexOf("e"));
    Console.println("indexOf e in {0} : {1}", QUEBEC, StringUtil.indexOfIgnoreAccent(QUEBEC, "e"));
    Console.println();

    // startWith, endWith
    Console.println("Québec starts w/ Que : {0}", StringUtil.startsWith(QUEBEC, "Que"));
    Console.println("Québec starts w/ Que : {0}", StringUtil.startsWithIgnoreAccent(QUEBEC, "Que"));
    Console.println("Québec starts w/ que : {0}", StringUtil.startsWithIgnoreCase(QUEBEC, "que"));
    Console.println();

    Console.println("Québec ends w/ ebec : {0}", StringUtil.endsWith(QUEBEC, "ebec"));
    Console.println("Québec ends w/ ebec : {0}", StringUtil.endsWithIgnoreAccent(QUEBEC, "ebec"));
    Console.println("Québec ends w/ EBEC : {0}", StringUtil.endsWithIgnoreCase(QUEBEC, "EBEC"));
    Console.println();
  }

  private static void demoCenterLongString() {
    String longString = "United States of America";
    String quoted = StringUtil.quote(longString);

    Console.println(StringUtil.abbreviate(longString, 20));
    Console.println(StringUtil.abbreviate(longString, 20, StringUtil.ELLIPSIS));
    Console.println(StringUtil.center(longString, 64));
    Console.println(StringUtil.center(quoted, 64));
    Console.println(StringUtil.center(StringUtil.unquote(quoted), 64));
    Console.println();
  }
}
