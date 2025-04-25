package com.marcosavard.commons.ui.res;

import com.marcosavard.commons.debug.Console;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessagesDemo {
    public static void main(String[] args) {
        ResourceBundle bundle = ResourceBundle.getBundle("Messages", Locale.US);
        Console.println("Success");
    }

}
