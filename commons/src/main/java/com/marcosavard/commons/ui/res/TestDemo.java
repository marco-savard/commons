package com.marcosavard.commons.ui.res;

import com.marcosavard.commons.debug.Console;

import java.util.Locale;
import java.util.ResourceBundle;

public class TestDemo {


    public static void main(String[] args) {
        Locale locale = Locale.ENGLISH;
        ResourceBundle bundle = ResourceBundle.getBundle("com.marcosavard.commons.ui.res");


        bundle.getKeys();

        Console.println("Success");
    }
}
