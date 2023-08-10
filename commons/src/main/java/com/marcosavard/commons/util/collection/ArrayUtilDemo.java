package com.marcosavard.commons.util.collection;

public class ArrayUtilDemo {

    public static void main(String[] args) {
        String[] array = new String[] {"Alpha", "Beta", "Gamma"};
        String item = "Alpha";
        int idx = -1;

        if (ArrayUtil.contains(array, item)) {
            idx = ArrayUtil.indexOf(array, item);
        }


    }
}
