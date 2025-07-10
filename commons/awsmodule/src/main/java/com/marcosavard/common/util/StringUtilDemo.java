package com.marcosavard.common.util;

import java.text.MessageFormat;

public class StringUtilDemo {

    public static void main(String[] args) {
        demoEquals();
        demoIndexOf();
        demoStartsWith();
        demoTitleCase();
    }

    private static void demoEquals() {
        //standard Java
        System.out.println("Examples using standard java.lang.String class");
        print("\"Montreal\".equals(\"montreal\")", "Montreal".equals("montreal"));
        print("\"Montreal\".equalsIgnoreCase(\"montreal\")", "Montreal".equalsIgnoreCase("montreal"));
        print("\"Montreal\".equalsIgnoreCase(\"montréal\")", "Montreal".equalsIgnoreCase("montréal"));
        System.out.println();

        //extended Java
        System.out.println("Examples using common.lang.StringUtil class");
        print("StringUtil.equalsIgnoreCaseAndAccents(\"Montreal\", \"montréal\")", StringUtil.equalsIgnoreCaseAndAccents("Montreal","montréal"));
        System.out.println();
    }

    private static void demoIndexOf() {
        //standard Java
        System.out.println("Examples using standard java.lang.String class");
        print("\"Montreal\".indexOf(\"e\")", "Montreal".indexOf("e"));
        print("\"MONTREAL\".indexOf(\"e\")", "MONTREAL".indexOf("e"));
        print("\"MONTREAL\".toLowerCase().indexOf(\"e\")", "MONTREAL".toLowerCase().indexOf("e"));
        print("\"MONTRÉAL\".toLowerCase().indexOf(\"e\")", "MONTRÉAL".toLowerCase().indexOf("e"));
        System.out.println();

        //extended Java
        System.out.println("Examples using common.lang.StringUtil class");
        print("StringUtil.stripAccents(\"MONTRÉAL\").toLowerCase().indexOf(\"e\")", StringUtil.stripAccents("MONTRÉAL").toLowerCase().indexOf("e"));
        print("StringUtil.indexOfIgnoreCase(\"MONTRÉAL\", \"é\")", StringUtil.indexOfIgnoreCase("MONTRÉAL", "é"));
        print("StringUtil.indexOfIgnoreCaseAndsAccents(\"MONTRÉAL\", \"e\")", StringUtil.indexOfIgnoreCaseAndsAccents("MONTRÉAL", "e"));
        System.out.println();
    }

    private static void demoStartsWith() {
        //standard Java
        System.out.println("Examples using standard java.lang.String class");
        print("\"Québec\".startsWith(\"Que\")", "Québec".startsWith("Que"));
        System.out.println();

        //extended Java
        System.out.println("Examples using common.lang.StringUtil class");
        print("StringUtil.stripAccents(\"Québec\").startsWith(\"Que\")", StringUtil.stripAccents("Québec").startsWith("Que"));
        print("StringUtil.startsWithIgnoreAccent(\"Québec\", \"Que\"))", StringUtil.startsWithIgnoreAccent("Québec","Que"));
        print("StringUtil.startWithVowel(\"éléphant\"))", StringUtil.startWithVowel("éléphant"));
        print("StringUtil.startWithVowel(\"yaourt\"))", StringUtil.startWithVowel("yaourt"));
        print("StringUtil.startWithVowel(\"yéti\"))", StringUtil.startWithVowel("yéti"));
        print("StringUtil.startWithVowel(\"ypérite\"))", StringUtil.startWithVowel("ypérite"));
        System.out.println();
    }

    private static void demoTitleCase() {
        //extended Java
        System.out.println("Examples using common.lang.StringUtil class");
        print("StringUtil.capitalize(\"éléphant élan épervier\")", StringUtil.capitalize("éléphant élan épervier"));
        print("StringUtil.capitalizeWords(\"éléphant élan épervier\")", StringUtil.capitalizeWords("éléphant élan épervier"));
        System.out.println();
    }



    private static void print(String desc, Object result) {
        System.out.println(MessageFormat.format("  {0} : {1}", desc, result));
    }
}
