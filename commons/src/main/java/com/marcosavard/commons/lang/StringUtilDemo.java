package com.marcosavard.commons.lang;

public class StringUtilDemo {
  private static final String QUEBEC = "Québec";

  public static void main(String[] args) {
    demoCenterLongString();
    demoStringWithAccents();
  }

  private static void demoCenterLongString() {
    String longString = "United States of America";
    String quoted = StringUtil.quote(longString);

    System.out.println(StringUtil.abbreviate(longString, 20));
    System.out.println(StringUtil.center(longString, 64));
    System.out.println(StringUtil.center(quoted, 64));
    System.out.println(StringUtil.center(StringUtil.unquote(quoted), 64));
    System.out.println();
  }

  private static void demoStringWithAccents() {
    System.out.println(QUEBEC.indexOf("e"));
    System.out.println(StringUtil.indexOfIgnoreAccent(QUEBEC, "e"));
    System.out.println();

    System.out.println(StringUtil.startsWith(QUEBEC, "Que"));
    System.out.println(StringUtil.startsWithIgnoreAccent(QUEBEC, "Que"));
    System.out.println(StringUtil.startsWithIgnoreCase(QUEBEC, "que"));
    System.out.println();

    System.out.println(StringUtil.endsWith(QUEBEC, "ebec"));
    System.out.println(StringUtil.endsWithIgnoreAccent(QUEBEC, "ebec"));
    System.out.println(StringUtil.endsWithIgnoreCase(QUEBEC, "EBEC"));
    System.out.println();
  }
}
