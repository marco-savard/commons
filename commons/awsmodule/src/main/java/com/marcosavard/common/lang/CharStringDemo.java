package com.marcosavard.common.lang;

public class CharStringDemo {

  public static void main(String[] args) {
    String str = "éléphant";
    testStringInterface(str);
    testStringInterface(CharString.of(str));
    testCharStringInterface(str);
    
    testUtilOperations(str);
    testI18nOperations(str);

    testCSharpOperations();
    testSqlOperations();
  }


  private static void testUtilOperations(String str) {
	  CharString cs = CharString.of(str); 
	 // cs.padRight(20, ' '); 
	  
	  System.out.println(cs.isBoolean());
	  System.out.println(cs.isInteger());
	  System.out.println(cs.isNumber());
	  System.out.println(cs.isDate());
	  
	  String multiline = cs.wordWrap(80, " ", System.lineSeparator());
	
  }

  private static void testI18nOperations(String str) {
	  CharString cs = CharString.of(str); 
	  String stripped = cs.stripAccents().toLowerCase(); 
  }


  private static void testCSharpOperations() {

    System.out.println(CharString.of('t', 10));

    String joined = String.join(", ", CharString.of("Alpha"), CharString.of("Beta"));
    System.out.println(joined);
  }

  private static void testSqlOperations() {
    // is cs between "a" and "be" or like "?"

  }

  private static void testStringInterface(String str) {
    String result = str.concat("s");
    boolean hasSuffix = str.endsWith("s");
    boolean equal = str.equalsIgnoreCase("ÉLÉPHANT");
    int index = str.indexOf("é");
    index = str.indexOf("é", 2);
    index = str.lastIndexOf("é");
    index = str.lastIndexOf("é", 1);
    int len = str.length();
    result = str.replaceAll("é", "e");
    String[] splitted = str.split("r");
    boolean hasPrefix = str.startsWith("é");
    result = str.substring(3);
    result = str.substring(0, 3);
    result = str.toUpperCase();
    result = str.toLowerCase();
    result = str.trim();
  }

  private static void testStringInterface(CharString str) {
    String result = str.concat("s");
    boolean hasSuffix = str.endsWith("s");
    boolean equal = str.equalsIgnoreCase("ÉLÉPHANT");
    int index = str.indexOf("é");
    index = str.indexOf("é", 2);
    index = str.lastIndexOf("é");
    index = str.lastIndexOf("é", 1);
    int len = str.length();
    result = str.replaceAll("é", "e");
    String[] splitted = str.split("r");
    boolean hasPrefix = str.startsWith("é");
    result = str.substring(3);
    result = str.substring(0, 3);
    result = str.toUpperCase();
    result = str.toLowerCase();
    result = str.trim();
  }

  private static void testCharStringInterface(String str) {
    CharString cs = CharString.of(str);
    String result = cs.stripAccents();
    boolean equal = cs.equalsIgnoreAccents("elephant");
    result = cs.stripBlanks();

    int len = CharString.of(null).length();
    System.out.println("len = " + len);
  }

}
