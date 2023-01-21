package com.marcosavard.commons.debug;

public class OutputToggleDemo {

    public static void main(String[] args) {

        try {
            int i = 0 / 0;
        } catch (ArithmeticException ex) {
            ex.printStackTrace();
        }

        System.out.println("Success");

    }


}
