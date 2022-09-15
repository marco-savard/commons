package com.marcosavard.commons.text.encoding;

import com.marcosavard.commons.debug.Console;

import java.util.Base64;

public class AccentPostponerDemo {

    public static void main(String[] args) {
        demoAccents();
        demoCedillas();
        demoDiaeresis();
        demoTilde();
        demoLigatures();
        demoHtml();
    }

    private static void demoAccents() {
        demoEncoded("a` la carte");
        demoEncoded("ba^ton");
        demoEncoded("Mu:nich");
        demoEncoded("re'sume'");
        demoEncoded("cre^pe");
        demoEncoded("Dali'");
        demoEncoded("I^le, i^le");
        demoEncoded("Co'rdoba");
        demoEncoded("ro^le");

        System.out.println();
    }

    private static void demoCedillas() {
        demoEncoded("fac,ade");
        System.out.println();
    }

    private static void demoDiaeresis() {
        demoEncoded("Zoe:");
        demoEncoded("nai:ve");
        demoEncoded("Mu:nich");
        System.out.println();
    }

    private static void demoTilde() {
        demoEncoded("can~on");
        demoEncoded("Sa~o Paulo");
        System.out.println();
    }

    private static void demoLigatures() {
        demoEncoded("ex aequo");
        System.out.println();
    }

    private static void demoHtml() {
        String encoded = "l'i^le d'a` co^te'";
        String decoded = AccentPostponer.getDecoder().decode(encoded);
        Console.println("{0} -> {1}", decoded, encoded);

        Base64.getEncoder();

        //System.out.println(AccentCode.of(code).toHtml());
    }


    private static void demoEncoded(String encoded) {
        String decoded = AccentPostponer.getDecoder().decode(encoded);
        encoded = AccentPostponer.getEncoder().encode(decoded);
        Console.println("{0} -> {1}", decoded, encoded);
    }
}
