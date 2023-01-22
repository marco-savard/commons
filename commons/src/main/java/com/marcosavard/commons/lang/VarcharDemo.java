package com.marcosavard.commons.lang;

import com.marcosavard.commons.debug.Console;

public class VarcharDemo {

    public static void main(String[] args) {
        //char sequence
        Varchar varchar = Varchar.of("test");
        System.out.println(varchar);
        System.out.println(varchar.charAt(0));
        System.out.println(varchar.length());
        System.out.println(varchar.subSequence(0, 3));
        System.out.println();

        //SQL-like functions
        Console.println("left(3) : {0}", varchar.left(3));
        Console.println("like(t*t) : {0}", varchar.like("t*t"));
        Console.println("right(3) : {0}", varchar.right(3));
        Console.println("repeat('a', 3) : {0}", Varchar.repeat('a', 3));
        Console.println("space(3) : {0}", Varchar.space(3));
        Console.println("soundex() : {0}", varchar.soundex());
        Console.println("translate() : \"{0}\"", Varchar.of("testé").translate("é", "e"));
        Console.println("trimLeft() : \"{0}\"", Varchar.of("  test   ").trimLeft());
        Console.println("trimRight() : \"{0}\"", Varchar.of("  test  ").trimRight());



    }
}
