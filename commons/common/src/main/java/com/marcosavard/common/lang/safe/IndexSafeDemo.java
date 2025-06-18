package com.marcosavard.common.lang.safe;

public class IndexSafeDemo {

    public static void main(String[] args) {
        demoIndexSafe();
    }

    private static void demoIndexSafe() {
        String qwerty = "qwerty";
        int idx = -1;
        char c;

        try {
            c = qwerty.charAt(idx);
        } catch (StringIndexOutOfBoundsException e) {
            c = '\0';
        }

        System.out.println("c = " + c);

        c = ((idx >=0 ) && (idx < qwerty.length())) ? qwerty.charAt(idx) : '\0';
        System.out.println("c = " + c);

        c = IndexSafe.of(qwerty).charAt(idx);
        System.out.println("c = " + c);

        System.out.println();

    }
}
