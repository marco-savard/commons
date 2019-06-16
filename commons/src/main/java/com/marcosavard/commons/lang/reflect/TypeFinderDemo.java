package com.marcosavard.commons.lang.reflect;

// reflect

public class TypeFinderDemo {
  private static final String[] STRINGS = new String[] {"-1", "1", "127", "128", "32767", "32768",
      "2147483647", "2147483648", "0.1", "3.4e+38", "3.5e+38", "1.7E308", "1.8E308", "A", "true",
      "False", "hello", "2019-12-31", "12/31/2019", "12/31/119 12:15 PM"};

  public static void main(String[] args) {
    TypeFinder finder = new TypeFinder();
    finder.addDateFormat("yyyy-MM-dd");

    for (String str : STRINGS) {
      Object value = finder.findTypeOf(str);
      String classname = value.getClass().getSimpleName();
      System.out.println(value + " is a " + classname);
    }
  }

}
