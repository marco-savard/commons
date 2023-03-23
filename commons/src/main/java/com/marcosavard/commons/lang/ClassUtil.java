package com.marcosavard.commons.lang;

import java.util.ArrayList;
import java.util.List;

public class ClassUtil {
    public static String getHierarchy(Object o) {
        Class claz = o.getClass();
        return getHierarchy(claz);
    }

    public static String getHierarchy(Class claz) {
        List<String> superclasses = new ArrayList<>();

        do {
            String classname = claz.getPackageName() + "." + claz.getSimpleName();
            superclasses.add(classname);
            claz = claz.getSuperclass();
        } while (claz != null);

        String hierachy = String.join(" -> ", superclasses);
        return hierachy;
    }
}
